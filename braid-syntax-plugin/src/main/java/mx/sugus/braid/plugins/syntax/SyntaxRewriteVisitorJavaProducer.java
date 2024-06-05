package mx.sugus.braid.plugins.syntax;

import java.util.Objects;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.ImplementsKnowledgeIndex;
import mx.sugus.braid.core.SymbolConstants;
import mx.sugus.braid.core.plugin.CodegenState;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.NonShapeProducerTask;
import mx.sugus.braid.core.plugin.TypeSyntaxResult;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.plugins.data.Utils;
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
        var builder = typeSyntax(state);
        var symbolProvider = state.symbolProvider();
        var isaKnowledgeIndex = ImplementsKnowledgeIndex.of(state.model());
        var syntaxNodeShapeId = ShapeId.from(syntaxNode);
        var syntaxNodeShape = state.model().expectShape(syntaxNodeShapeId).asStructureShape().orElseThrow();
        var syntaxNodeType = symbolProvider.toJavaTypeName(syntaxNodeShape);
        var shapes = isaKnowledgeIndex.recursiveImplementers(syntaxNodeShape);
        for (var shape : shapes) {
            if (shape.hasTrait(InterfaceTrait.class)) {
                continue;
            }
            builder.addMethod(visitForStructure(state, shape)
                                  .addAnnotation(Override.class)
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(syntaxNodeType)
                                  .build());
        }
        return TypeSyntaxResult.builder().syntax(builder.build()).build();
    }

    ClassSyntax.Builder typeSyntax(CodegenState state) {
        var syntaxNodeClass = state.toJavaTypeNameClass(syntaxNode);
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
        var name = shape.getId().getName();
        var symbolProvider = state.symbolProvider();
        var type = symbolProvider.toJavaTypeName(shape);
        var syntaxNodeClass = state.toJavaTypeNameClass(syntaxNode);
        var builder = MethodSyntax.builder("visit" + name)
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(syntaxNodeClass)
                                  .addParameter(type, "node");
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
        var symbolProvider = state.symbolProvider();
        var memberName = symbolProvider.toMemberJavaName(member);
        var memberNameNew = memberName.withPrefix("new");
        var memberInnerTypeShape = memberInnerType(state, member);
        var memberInnerType = symbolProvider.toJavaTypeName(memberInnerTypeShape);
        var memberType = symbolProvider.toJavaTypeName(member);
        builder.addStatement("$T $L = node.$L()", memberType, memberName, memberName);
        var type = symbolProvider.aggregateType(member);
        builder.addStatement("$T $L = null", memberType, memberNameNew);
        if (type == SymbolConstants.AggregateType.LIST) {
            builder.forStatement("int idx = 0; idx < $L.size(); idx++", memberName, b -> {
                // XXX, This need to be adjusted this for @sparse lists.
                b.addStatement("$T value = $L.get(idx)", memberInnerType, memberName);
                b.addStatement("$1T newValue = ($1T) value.accept(this)", memberInnerType);
                b.ifStatement("$L == null && !value.equals(newValue)", memberNameNew, valueChanged -> {
                    valueChanged.addStatement("$L = new $T<>($L.size())",
                                              memberNameNew, symbolProvider.concreteClassFor(SymbolConstants.AggregateType.LIST), memberName);
                    valueChanged.addStatement("$L.addAll($L.subList(0, idx))", memberNameNew, memberName);
                });
                b.ifStatement("$L != null", memberNameNew, then -> {
                    then.addStatement("$L.add(newValue)", memberNameNew);
                });
            });
        } else if (type == SymbolConstants.AggregateType.SET) {
            builder.forStatement("$T value : $L", memberInnerType, memberName, b -> {
                b.addStatement("$1T newValue = ($1T) value.accept(this)", memberInnerType);
                b.ifStatement("$L == null && !value.equals(newValue)", memberNameNew, valueChanged -> {
                    valueChanged.addStatement("$L = new $T<>($L.size())",
                                              memberNameNew, symbolProvider.concreteClassFor(SymbolConstants.AggregateType.SET), memberName);
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
            then.addStatement("builder.$L($L)", memberName, memberNameNew);
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
        var symbolProvider = state.symbolProvider();
        var type = symbolProvider.aggregateType(member);
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
        var symbolProvider = state.symbolProvider();
        var memberName = symbolProvider.toMemberJavaName(member);
        var memberNameNew = memberName.withSuffix("new");
        var memberType = symbolProvider.toJavaTypeName(member);
        builder.addStatement("$1T $2L = node.$2L()", memberType, memberName);
        if (symbolProvider.isMemberNullable(member)) {
            builder.addStatement("$T $L = null", memberType, memberNameNew);
            builder.ifStatement("$L != null", memberName, b -> b.addStatement("$L = ($T) $L.accept(this)",
                                                                              memberNameNew,
                                                                              memberType,
                                                                              memberName));
        } else {
            builder.addStatement("$T $L = ($T) $L.accept(this)", memberType, memberNameNew, memberType, memberName);
        }
        builder.ifStatement(CodeBlock.from("!$T.equals($L, $L)", Objects.class, memberName, memberNameNew), notEqual -> {
            if (isBuilderNull) {
                notEqual.addStatement("builder = node.toBuilder()");
            } else {
                notEqual.ifStatement("builder == null",
                                     builderIsNull -> builderIsNull.addStatement("builder = node.toBuilder()"));
            }
            notEqual.addStatement("builder.$L($L)", memberName, memberNameNew);
        });
    }
}
