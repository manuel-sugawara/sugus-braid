package mx.sugus.braid.plugins.syntax;

import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.ImplementsKnowledgeIndex;
import mx.sugus.braid.plugins.data.symbols.SymbolConstants;
import mx.sugus.braid.core.plugin.CodegenState;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.NonShapeProducerTask;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.traits.InterfaceTrait;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.StructureShape;

public final class SyntaxWalkVisitorJavaProducer implements NonShapeProducerTask<TypeSyntaxResult> {
    public static final Identifier ID = Identifier.of(SyntaxWalkVisitorJavaProducer.class);
    private final String syntaxNode;

    SyntaxWalkVisitorJavaProducer(String syntaxNode) {
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
        var isaKnowledgeIndex = ImplementsKnowledgeIndex.of(state.model());
        var syntaxNodeShapeId = ShapeId.from(syntaxNode);
        var syntaxNodeShape = state.model().expectShape(syntaxNodeShapeId).asStructureShape().orElseThrow();
        var syntaxNodeType = Utils.toJavaTypeName(state, syntaxNodeShape);
        var shapes = isaKnowledgeIndex.recursiveImplementers(syntaxNodeShape);
        for (var shape : shapes) {
            if (!shape.hasTrait(InterfaceTrait.class)) {
                builder.addMethod(visitForStructure(state, shape)
                                      .addAnnotation(Override.class)
                                      .addModifier(Modifier.PUBLIC)
                                      .returns(syntaxNodeType)
                                      .build());
            }
        }
        return TypeSyntaxResult.builder().syntax(builder.build()).build();
    }

    ClassSyntax.Builder typeSyntax(CodegenState state) {
        var syntaxShape = state.model().expectShape(ShapeId.from(syntaxNode));
        var syntaxNodeClass = ClassName.toClassName(Utils.toJavaTypeName(state, syntaxShape));
        var syntaxNodeRawClass = ClassName.toClassName(syntaxNodeClass);
        var walkVisitorClass = ClassName.from(syntaxNodeRawClass.packageName(),
                                              syntaxNodeRawClass.name() + "WalkVisitor");
        var visitorClass = ClassName.from(syntaxNodeRawClass.packageName(),
                                          syntaxNodeRawClass.name() + "Visitor");

        var visitorClass2 = ParameterizedTypeName.from(visitorClass, syntaxNodeClass);

        return ClassSyntax.builder(walkVisitorClass.name())
                          .addAnnotation(Utils.generatedBy(SyntaxModelPlugin.ID))
                          .addModifier(Modifier.PUBLIC)
                          .addSuperInterface(visitorClass2);
    }

    MethodSyntax.Builder visitForStructure(CodegenState state, StructureShape shape) {
        var name = shape.getId().getName();
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(shape);
        var type = Utils.toJavaTypeName(symbol);
        var syntaxShape = state.model().expectShape(ShapeId.from(syntaxNode));
        var syntaxNodeClass = ClassName.toClassName(Utils.toJavaTypeName(state, syntaxShape));
        var builder = MethodSyntax.builder("visit" + name)
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(syntaxNodeClass)
                                  .addParameter(type, "node");

        var model = state.model();
        builder.body(body -> {
            for (var member : shape.members()) {
                var memberShape = model.expectShape(member.getTarget());
                if (SyntaxVisitorJavaProducer.shapeImplements(syntaxNode, model, memberShape)) {
                    addSingleSyntaxNode(state, member, body);
                }
                if (isCollectionOfSyntaxNode(state, member)) {
                    addCollectionOfSyntaxNode(state, member, body);
                }
            }
            body.addStatement("return node");
        });
        return builder;
    }

    void addCollectionOfSyntaxNode(CodegenState state, MemberShape member, BodyBuilder builder) {
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(member);
        var memberName = Utils.toJavaName(symbol);
        var memberInnerTypeShape = memberInnerType(state, member);
        var memberInnerType = Utils.toJavaTypeName(symbolProvider.toSymbol(memberInnerTypeShape));
        var memberType = Utils.toJavaTypeName(state, member);
        builder.addStatement("$T $L = node.$L()", memberType, memberName, Utils.toGetterName(symbol));
        var type = Utils.aggregateType(state, member);
        if (type == SymbolConstants.AggregateType.LIST) {
            builder.forStatement("int idx = 0; idx < $L.size(); idx++", memberName, b -> {
                b.addStatement("$T value = $L.get(idx)", memberInnerType, memberName);
                b.addStatement("value.accept(this)", memberInnerType);
            });
        } else if (type == SymbolConstants.AggregateType.SET) {
            builder.forStatement("$T value : $L", memberInnerType, memberName, b -> {
                b.addStatement("value.accept(this)", memberInnerType);
            });
        } else {
            throw new UnsupportedOperationException("Unknown aggregate type: " + type);
        }
    }

    Shape memberInnerType(CodegenState state, MemberShape member) {
        var targetId = member.getTarget();
        var target = state.model().expectShape(targetId);
        var listShape = target.asListShape().orElseThrow();
        var listMemberTarget = listShape.getMember().getTarget();
        return state.model().expectShape(listMemberTarget);
    }

    boolean isCollectionOfSyntaxNode(CodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(member);
        var type = Utils.aggregateType(symbol);
        if (type == SymbolConstants.AggregateType.LIST || type == SymbolConstants.AggregateType.SET) {
            var target = state.model().expectShape(member.getTarget());
            var listShape = target.asListShape().orElseThrow();
            var targetShape = state.model().expectShape(listShape.getMember().getTarget());
            if (SyntaxVisitorJavaProducer.shapeImplements(syntaxNode, state.model(), targetShape)) {
                return true;
            }
            return targetShape.getId().toString().equals(syntaxNode);
        }
        return false;
    }

    void addSingleSyntaxNode(CodegenState state, MemberShape member, BodyBuilder builder) {
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(member);
        var memberName = Utils.toJavaName(symbol);
        var memberType = Utils.toJavaTypeName(symbol);

        if (Utils.isMemberNullable(state, member)) {
            builder.addStatement("$T $L = node.$L()", memberType, memberName, Utils.toGetterName(symbol));
            builder.ifStatement("$L != null", memberName, b -> b.addStatement("$L.accept(this)", memberName));
        } else {
            builder.addStatement("node.$L().accept(this)", Utils.toGetterName(symbol));
        }
    }
}
