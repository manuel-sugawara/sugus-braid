package mx.sugus.braid.plugins.syntax;

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
        var memberNameNew = memberName.withPrefix("new");
        var memberInnerTypeShape = memberInnerType(state, member);
        var memberInnerType = Utils.toJavaTypeName(state, memberInnerTypeShape);
        var memberType = Utils.toJavaTypeName(state, member);
        var getterName = Utils.toGetterName(state, member);
        builder.addStatement("$T $L = node.$L()", memberType, memberName, getterName);
        var type = Utils.aggregateType(state, member);
        builder.addStatement("$T $L = null", memberType, memberNameNew);
        if (type == SymbolConstants.AggregateType.LIST) {
            builder.forStatement("int idx = 0; idx < $L.size(); idx++", memberName, b -> {
                // XXX, do we need to be adjusted this for @sparse lists?.
                b.addStatement("$T value = $L.get(idx)", memberInnerType, memberName);
                var acceptBlock = acceptBlock(state, memberInnerTypeShape, "value");
                b.addStatement("$T newValue = $C", memberInnerType, acceptBlock);
                b.ifStatement("$L == null && !value.equals(newValue)", memberNameNew, valueChanged -> {
                    valueChanged.addStatement("$L = new $T<>($L.size())",
                                              memberNameNew,
                                              Utils.concreteClassFor(SymbolConstants.AggregateType.LIST), memberName);
                    valueChanged.addStatement("$L.addAll($L.subList(0, idx))", memberNameNew, memberName);
                });
                b.ifStatement("$L != null", memberNameNew, then -> {
                    then.addStatement("$L.add(newValue)", memberNameNew);
                });
            });
        } else if (type == SymbolConstants.AggregateType.SET) {
            builder.forStatement("$T value : $L", memberInnerType, memberName, b -> {
                var acceptBlock = acceptBlock(state, memberInnerTypeShape, "value");
                b.addStatement("$T newValue = $C", memberInnerType, acceptBlock);
                b.ifStatement("$L == null && !value.equals(newValue)", memberNameNew, valueChanged -> {
                    valueChanged.addStatement("$L = new $T<>($L.size())",
                                              memberNameNew, Utils.concreteClassFor(SymbolConstants.AggregateType.SET)
                        , memberName);
                    // XXX This assumes that the set is ordered, for now is true but this will change
                    valueChanged.forStatement("$T innerValue : $L", memberInnerType, memberName, copyMembers -> {
                        copyMembers.ifStatement("innerValue == value", done -> done.addStatement("break"));
                        copyMembers.addStatement("$L.add(innerValue)", memberNameNew);
                    });
                });
                b.ifStatement("$L != null", memberNameNew, then -> {
                    then.addStatement("$L.add(newValue)", memberNameNew);
                });
            });
        } else {
            // XXX add support for maps
            throw new UnsupportedOperationException("Unknown aggregate type: " + type);
        }
        builder.ifStatement("$L != null", memberNameNew, then -> {
            if (isBuilderNull) {
                then.addStatement("builder = node.toBuilder()");
            } else {
                then.ifStatement("builder == null", builderIsNull -> builderIsNull.addStatement("builder = node.toBuilder()"));
            }
            var setterName = Utils.toSetterName(state, member);
            then.addStatement("builder.$L($L)", setterName, memberNameNew);
        });
    }

    Shape memberInnerType(CodegenState state, MemberShape member) {
        var targetId = member.getTarget();
        var target = state.model().expectShape(targetId);
        var listShape = target.asListShape().orElseThrow();
        var listMemberTarget = listShape.getMember().getTarget();
        return state.model().expectShape(listMemberTarget);
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
