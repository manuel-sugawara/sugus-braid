package mx.sugus.braid.plugins.syntax;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.ImplementsKnowledgeIndex;
import mx.sugus.braid.core.plugin.CodegenState;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.NonShapeProducerTask;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.CompilationUnit;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.plugins.data.symbols.SymbolConstants;
import mx.sugus.braid.traits.InterfaceTrait;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.StructureShape;

public final class SyntaxRewriteVisitorJavaProducer implements NonShapeProducerTask<TypeSyntaxResult> {
    public static final Identifier ID = Identifier.of(SyntaxRewriteVisitorJavaProducer.class);
    private final String syntaxNode;

    SyntaxRewriteVisitorJavaProducer(String syntaxNode) {
        this.syntaxNode = syntaxNode;
    }

    @Override
    public Identifier taskId() {
        return ID;
    }

    @Override
    public Class<TypeSyntaxResult> output() {
        return TypeSyntaxResult.class;
    }

    @Override
    public TypeSyntaxResult produce(CodegenState state) {
        return TypeSyntaxResult.builder().syntax(compilationUnit(state)).build();
    }

    CompilationUnit compilationUnit(CodegenState state) {
        var syntaxShape = state.model().expectShape(ShapeId.from(syntaxNode));
        var typeName = ClassName.toClassName(Utils.toJavaTypeName(state, syntaxShape));
        return CompilationUnit.builder().packageName(typeName.packageName()).type(typeSyntax(state)).build();
    }

    ClassSyntax typeSyntax(CodegenState state) {
        var builder = typeSyntaxBuilder(state);
        var isaKnowledgeIndex = ImplementsKnowledgeIndex.of(state.model());
        var syntaxNodeShapeId = ShapeId.from(syntaxNode);
        var syntaxNodeShape = state.model().expectShape(syntaxNodeShapeId).asStructureShape().orElseThrow();
        var shapes = isaKnowledgeIndex.recursiveImplementers(syntaxNodeShape);
        for (var shape : shapes) {
            if (shape.hasTrait(InterfaceTrait.class)) {
                continue;
            }
            builder.addMethod(visitForStructure(state, shape)
                                  .addAnnotation(Override.class)
                                  .addModifier(Modifier.PUBLIC)
                                  .build());
        }
        return builder.build();
    }

    ClassSyntax.Builder typeSyntaxBuilder(CodegenState state) {
        var syntaxShape = state.model().expectShape(ShapeId.from(syntaxNode));
        var syntaxNodeClass = ClassName.toClassName(Utils.toJavaTypeName(state, syntaxShape));
        var syntaxNodeRawClass = ClassName.toClassName(syntaxNodeClass);
        var rewriteVisitorClass = ClassName.from(syntaxNodeRawClass.packageName(),
                                                 syntaxNodeRawClass.name() + "RewriteVisitor");
        var visitorClass = ClassName.from(syntaxNodeRawClass.packageName(),
                                          syntaxNodeRawClass.name() + "Visitor");

        var visitorClass2 = ParameterizedTypeName.from(visitorClass, syntaxNodeClass);

        return ClassSyntax.builder(rewriteVisitorClass.name())
                          .addAnnotation(Utils.generatedBy(SyntaxModelPlugin.ID))
                          .addModifier(Modifier.PUBLIC)
                          .addSuperInterface(visitorClass2);
    }

    MethodSyntax.Builder visitForStructure(CodegenState state, StructureShape shape) {
        var name = Utils.toJavaName(state, shape, Name.Convention.CAMEL_CASE).withPrefix("visit");
        var type = Utils.toJavaTypeName(state, shape);
        var builder = MethodSyntax.builder(name.toString())
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(type)
                                  .addParameter(type, "node");
        if (!hasVisitableMembers(state, shape)) {
            builder.addStatement("return node");
            return builder;
        }
        builder.addStatement("$T.Builder builder = null", type);
        var model = state.model();
        builder.body(body -> {
            var isBuilderNull = true;
            for (var member : shape.members()) {
                var memberShape = model.expectShape(member.getTarget());
                if (SyntaxVisitorJavaProducer.shapeImplements(syntaxNode, model, memberShape)) {
                    addSingleSyntaxNode(state, member, body, isBuilderNull);
                    isBuilderNull = false;
                }
                if (isCollectionOfSyntaxNode(state, member)) {
                    addCollectionOfSyntaxNode(state, member, body, isBuilderNull);
                    isBuilderNull = false;
                }
            }
            body.ifStatement("builder != null", then ->
                then.addStatement("return builder.build()"));
            body.addStatement("return node");
        });
        return builder;
    }

    void addCollectionOfSyntaxNode(CodegenState state, MemberShape member, BodyBuilder builder, boolean isBuilderNull) {
        var memberName = Utils.toJavaName(state, member);
        var memberType = Utils.toJavaTypeName(state, member);
        var getterName = Utils.toGetterName(state, member);
        builder.addStatement("$T $L = node.$L()", memberType, memberName, getterName);
        var type = Utils.aggregateType(state, member);

        if (type == SymbolConstants.AggregateType.LIST) {
            addRewriteForList(state, member, builder, isBuilderNull);
        } else if (type == SymbolConstants.AggregateType.SET) {
            addRewriteForSet(state, member, builder, isBuilderNull);
        } else if (type == SymbolConstants.AggregateType.MAP) {
            addRewriteForMap(state, member, builder, isBuilderNull);
        } else {
            // XXX add support for maps
            throw new UnsupportedOperationException("Unsupported aggregate type: " + type);
        }
    }


    void addRewriteForList(CodegenState state, MemberShape member, BodyBuilder builder, boolean isBuilderNull) {
        var memberName = Utils.toJavaName(state, member);
        var memberInnerTypeShape = memberInnerType(state, member);
        var memberInnerType = Utils.toJavaTypeName(state, memberInnerTypeShape);
        var memberChanged = memberName.withSuffix("changed");
        var memberSize = memberName.withSuffix("size");
        var addMethodName = Utils.toAdderName(state, member);
        var setMethodName = Utils.toSetterName(state, member);
        builder.addStatement("boolean $L = false", memberChanged);
        builder.addStatement("int $L = $L.size()", memberSize, memberName);
        builder.forStatement("int idx = 0; idx < $L; idx++", memberSize, b -> {
            b.addStatement("$T value = $L.get(idx)", memberInnerType, memberName);
            var acceptBlock = acceptBlock(state, memberInnerTypeShape, "value");
            b.addStatement("$T newValue = $C", memberInnerType, acceptBlock);
            b.ifStatement("!$L && value != newValue", memberChanged, valueChanged -> {
                valueChanged.addStatement("$L = true", memberChanged);
                if (isBuilderNull) {
                    valueChanged.addStatement("builder = node.toBuilder()");
                } else {
                    valueChanged.ifStatement("builder == null", builderIsNull -> {
                        builderIsNull.addStatement("builder = node.toBuilder()");
                    });
                }
                valueChanged.addStatement("builder.$L($T.emptyList())", setMethodName, Collections.class);
                valueChanged.forStatement("int innerIdx = 0; innerIdx < idx; innerIdx++", memberSize, copyMembers -> {
                    copyMembers.addStatement("builder.$L($L.get(innerIdx))", addMethodName, memberName);
                });
            });
            b.ifStatement("$L", memberChanged, then -> {
                then.addStatement("builder.$L(newValue)", addMethodName);
            });
        });
    }

    void addRewriteForSet(CodegenState state, MemberShape member, BodyBuilder builder, boolean isBuilderNull) {
        var memberName = Utils.toJavaName(state, member);
        var memberInnerTypeShape = memberInnerType(state, member);
        var memberInnerType = Utils.toJavaTypeName(state, memberInnerTypeShape);
        var memberChanged = memberName.withSuffix("changed");
        var addMethodName = Utils.toAdderName(state, member);
        var setMethodName = Utils.toSetterName(state, member);
        builder.addStatement("boolean $L = false", memberChanged);
        builder.forStatement("$T value : $L", memberInnerType, memberName, b -> {
            var acceptBlock = acceptBlock(state, memberInnerTypeShape, "value");
            b.addStatement("$T newValue = $C", memberInnerType, acceptBlock);
            b.ifStatement("!$L && value != newValue", memberChanged, valueChanged -> {
                valueChanged.addStatement("$L = true", memberChanged);
                if (isBuilderNull) {
                    valueChanged.addStatement("builder = node.toBuilder()");
                } else {
                    valueChanged.ifStatement("builder == null", builderIsNull -> {
                        builderIsNull.addStatement("builder = node.toBuilder()");
                    });
                }
                valueChanged.addStatement("builder.$L($T.emptySet())", setMethodName, Collections.class);
                valueChanged.forStatement("$T innerValue : $L", memberInnerType, memberName, copyMembers -> {
                    copyMembers.ifStatement("innerValue == value", done -> done.addStatement("break"));
                    copyMembers.addStatement("builder.$L(innerValue)", addMethodName);
                });
            });
            b.ifStatement("$L", memberChanged, then -> {
                then.addStatement("builder.$L(newValue)", addMethodName);
            });
        });
    }

    void addRewriteForMap(CodegenState state, MemberShape member, BodyBuilder builder, boolean isBuilderNull) {
        var memberName = Utils.toJavaName(state, member);
        var memberChanged = memberName.withSuffix("changed");
        var memberInnerTypeShape = memberInnerType(state, member);
        var memberInnerType = Utils.toJavaTypeName(state, memberInnerTypeShape);
        var entryType = ParameterizedTypeName.builder()
                                             .rawType(ClassName.from(Map.Entry.class))
                                             .addTypeArgument(String.class)
                                             .addTypeArgument(memberInnerType)
                                             .build();
        var putMethodName = Utils.toAdderName(state, member);
        var setMethodName = Utils.toSetterName(state, member);
        builder.addStatement("boolean $L = false", memberChanged);
        builder.forStatement("$T kvp : $L.entrySet()", entryType, memberName, b -> {
            b.addStatement("$T value = kvp.getValue()", memberInnerType);
            var acceptBlock = acceptBlock(state, memberInnerTypeShape, "value");
            b.addStatement("$T newValue = $C", memberInnerType, acceptBlock);
            b.ifStatement("!$L && value != newValue", memberChanged, valueChanged -> {
                valueChanged.addStatement("$L = true", memberChanged);
                if (isBuilderNull) {
                    valueChanged.addStatement("builder = node.toBuilder()");
                } else {
                    valueChanged.ifStatement("builder == null", builderIsNull -> {
                        builderIsNull.addStatement("builder = node.toBuilder()");
                    });
                }
                valueChanged.addStatement("builder.$L($T.emptyMap())", setMethodName, Collections.class);
                valueChanged.forStatement("$T innerKvp : $L.entrySet()", entryType, memberName, copyMembers -> {
                    copyMembers.ifStatement("innerKvp.getValue() == value", done -> done.addStatement("break"));
                    copyMembers.addStatement("builder.$L(innerKvp.getKey(), innerKvp.getValue())", putMethodName);
                });
            });
            b.ifStatement("$L", memberChanged, then -> {
                then.addStatement("builder.$L(kvp.getKey(), newValue)", putMethodName);
            });
        });
    }

    Shape memberInnerType(CodegenState state, MemberShape member) {
        var type = Utils.aggregateType(state, member);
        var targetId = member.getTarget();
        var target = state.model().expectShape(targetId);
        if (type == SymbolConstants.AggregateType.LIST || type == SymbolConstants.AggregateType.SET) {
            var listShape = target.asListShape().orElseThrow();
            var listMemberTarget = listShape.getMember().getTarget();
            return state.model().expectShape(listMemberTarget);
        }
        if (type == SymbolConstants.AggregateType.MAP) {
            var mapShape = target.asMapShape().orElseThrow();
            var valueMemberTarget = mapShape.getValue().getTarget();
            return state.model().expectShape(valueMemberTarget);
        }
        throw new IllegalArgumentException("unknown aggregate type");
    }

    boolean isCollectionOfSyntaxNode(CodegenState state, MemberShape member) {
        var targetId = member.getTarget();
        var target = state.model().expectShape(targetId);
        var type = Utils.aggregateType(state, member);
        if (type == SymbolConstants.AggregateType.LIST || type == SymbolConstants.AggregateType.SET) {
            var listShape = target.asListShape().orElseThrow();
            var targetShape = state.model().expectShape(listShape.getMember().getTarget());
            if (SyntaxVisitorJavaProducer.shapeImplements(syntaxNode, state.model(), targetShape)) {
                return true;
            }
            return targetShape.getId().toString().equals(syntaxNode);
        }
        if (type == SymbolConstants.AggregateType.MAP) {
            var mapShape = target.asMapShape().orElseThrow();
            var targetShape = state.model().expectShape(mapShape.getValue().getTarget());
            if (SyntaxVisitorJavaProducer.shapeImplements(syntaxNode, state.model(), targetShape)) {
                return true;
            }
            return targetShape.getId().toString().equals(syntaxNode);
        }
        return false;
    }

    void addSingleSyntaxNode(CodegenState state, MemberShape member, BodyBuilder builder, boolean isBuilderNull) {
        var memberName = Utils.toJavaName(state, member);
        var memberNameNew = memberName.withSuffix("new");
        var memberType = Utils.toJavaTypeName(state, member);
        builder.addStatement("$T $L = node.$L()", memberType, memberName, Utils.toGetterName(state, member));
        var targetShape = state.model().expectShape(member.getTarget());
        var acceptBlock = acceptBlock(state, targetShape, memberName.toString());
        if (Utils.isNullable(state, member)) {
            builder.addStatement("$T $L = null", memberType, memberNameNew);
            builder.ifStatement("$L != null", memberName, b -> b.addStatement("$L = $C",
                                                                              memberNameNew,
                                                                              acceptBlock));
        } else {
            builder.addStatement("$T $L = $C", memberType, memberNameNew, acceptBlock);
        }
        builder.ifStatement(CodeBlock.from("!$T.equals($L, $L)", Objects.class, memberName, memberNameNew), notEqual -> {
            if (isBuilderNull) {
                notEqual.addStatement("builder = node.toBuilder()");
            } else {
                notEqual.ifStatement("builder == null",
                                     builderIsNull -> builderIsNull.addStatement("builder = node.toBuilder()"));
            }
            var setterName = Utils.toSetterName(state, member);
            notEqual.addStatement("builder.$L($L)", setterName, memberNameNew);
        });
    }

    private CodeBlock acceptBlock(CodegenState state, Shape targetShape, String memberName) {
        var memberType = Utils.toJavaTypeName(state, targetShape);
        CodeBlock acceptBlock;
        if (targetShape.hasTrait(InterfaceTrait.class)) {
            acceptBlock = CodeBlock.from("($T) $L.accept(this)", memberType, memberName);
        } else {
            var targetVisitName = Utils.toJavaName(state, targetShape).withPrefix("visit").toCamelCase();
            acceptBlock = CodeBlock.from("$L($L)", targetVisitName, memberName);
        }
        return acceptBlock;
    }

    private boolean hasVisitableMembers(CodegenState state, StructureShape shape) {
        var model = state.model();
        for (var member : shape.members()) {
            var memberShape = model.expectShape(member.getTarget());
            if (SyntaxVisitorJavaProducer.shapeImplements(syntaxNode, model, memberShape)) {
                return true;
            }
            if (isCollectionOfSyntaxNode(state, member)) {
                return true;
            }
        }
        return false;
    }
}
