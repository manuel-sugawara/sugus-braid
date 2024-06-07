package mx.sugus.braid.plugins.syntax;

import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.ImplementsKnowledgeIndex;
import mx.sugus.braid.core.plugin.CodegenState;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.NonShapeProducerTask;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.TypeSyntax;
import mx.sugus.braid.jsyntax.TypeVariableTypeName;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.traits.InterfaceTrait;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.StructureShape;

public final class SyntaxVisitorJavaProducer implements NonShapeProducerTask<TypeSyntaxResult> {
    public static final Identifier ID = Identifier.of(SyntaxVisitorJavaProducer.class);
    static final TypeVariableTypeName T_TYPE_ARG = TypeVariableTypeName.from("T");
    private final String syntaxNode;

    SyntaxVisitorJavaProducer(String syntaxNode) {
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
        return TypeSyntaxResult.builder().syntax(generate(state)).build();
    }

    InterfaceSyntax.Builder typeSyntax(CodegenState state) {
        var syntaxShape = state.model().expectShape(ShapeId.from(syntaxNode));
        var syntaxNodeClass = ClassName.toClassName(Utils.toJavaTypeName(state, syntaxShape));
        return InterfaceSyntax.builder(syntaxNodeClass.name() + "Visitor")
                              .addAnnotation(Utils.generatedBy(SyntaxModelPlugin.ID))
                              .addModifier(Modifier.PUBLIC)
                              .addTypeParam(T_TYPE_ARG);
    }

    ClassName visitorClass(CodegenState state) {
        var syntaxShape = state.model().expectShape(ShapeId.from(syntaxNode));
        var syntaxNodeClass = ClassName.toClassName(Utils.toJavaTypeName(state, syntaxShape));
        return ClassName.from(syntaxNodeClass.packageName(), syntaxNodeClass.name() + "Visitor");
    }

    TypeSyntax generate(CodegenState state) {
        var builder = typeSyntax(state);
        var isaKnowledgeIndex = ImplementsKnowledgeIndex.of(state.model());
        var syntaxNodeShapeId = ShapeId.from(syntaxNode);
        var syntaxNode = state.model().expectShape(syntaxNodeShapeId).asStructureShape().orElseThrow();
        var shapes = isaKnowledgeIndex.recursiveImplementers(syntaxNode);
        for (var shape : shapes) {
            if (!shape.hasTrait(InterfaceTrait.class)) {
                builder.addMethod(abstractVisitForStructure(state, shape));
            }
        }
        builder.addInnerType(defaultVisitor(state));
        return builder.build();
    }

    ClassSyntax defaultVisitor(CodegenState state) {
        var superInterface = ParameterizedTypeName.from(visitorClass(state), T_TYPE_ARG);
        var builder = ClassSyntax.builder("Default")
                                 .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.ABSTRACT)
                                 .addSuperInterface(superInterface)
                                 .addTypeParam(T_TYPE_ARG);
        var syntaxShape = state.model().expectShape(ShapeId.from(syntaxNode));
        var syntaxNodeClass = ClassName.toClassName(Utils.toJavaTypeName(state, syntaxShape));
        builder.addMethod(AbstractMethodSyntax.builder()
                                              .name("getDefault")
                                              .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                                              .addParameter(syntaxNodeClass, "node")
                                              .returns(T_TYPE_ARG)
                                              .build());
        var isaKnowledgeIndex = ImplementsKnowledgeIndex.of(state.model());
        var syntaxNodeShapeId = ShapeId.from(syntaxNode);
        var syntaxNode = state.model().expectShape(syntaxNodeShapeId).asStructureShape().orElseThrow();
        var shapes = isaKnowledgeIndex.recursiveImplementers(syntaxNode);
        for (var shape : shapes) {
            if (!shape.hasTrait(InterfaceTrait.class)) {
                builder.addMethod(concreteVisitForStructure(state, shape));
            }
        }
        return builder.build();
    }

    AbstractMethodSyntax abstractVisitForStructure(CodegenState state, StructureShape shape) {
        var name = state.symbolProvider().toSymbol(shape).getName();
        var symbolProvider = state.symbolProvider();
        var type = Utils.toJavaTypeName(state, shape);
        return AbstractMethodSyntax.builder("visit" + name)
                                   .returns(T_TYPE_ARG)
                                   .addParameter(type, "node")
                                   .build();
    }

    MethodSyntax concreteVisitForStructure(CodegenState state, StructureShape shape) {
        var name = state.symbolProvider().toSymbol(shape).getName();
        var symbolProvider = state.symbolProvider();
        var type = Utils.toJavaTypeName(state, shape);
        return MethodSyntax.builder("visit" + name)
                           .addModifier(Modifier.PUBLIC)
                           .addAnnotation(Override.class)
                           .returns(T_TYPE_ARG)
                           .addParameter(type, "node")
                           .body(b -> b.addStatement("return getDefault(node)"))
                           .build();
    }

    static boolean shapeImplements(String syntaxNodeId, Model model, Shape ashape) {
        if (ashape instanceof StructureShape shape) {
            var syntaxNodeShapeId = ShapeId.from(syntaxNodeId);
            var syntaxNode = model.expectShape(syntaxNodeShapeId).asStructureShape().orElseThrow();
            var isaKnowledgeIndex = ImplementsKnowledgeIndex.of(model);
            return isaKnowledgeIndex.recursiveImplementers(syntaxNode).contains(shape);
        }
        return false;
    }
}
