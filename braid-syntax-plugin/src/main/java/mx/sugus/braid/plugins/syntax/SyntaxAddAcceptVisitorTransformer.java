package mx.sugus.braid.plugins.syntax;

import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.core.plugin.TypeSyntaxResult;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.TypeVariableTypeName;
import mx.sugus.braid.jsyntax.ext.TypeNameExt;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.traits.InterfaceTrait;
import software.amazon.smithy.model.shapes.ShapeId;

public final class SyntaxAddAcceptVisitorTransformer implements ShapeTaskTransformer<TypeSyntaxResult> {
    private static final Identifier ID = Identifier.of(SyntaxAddAcceptVisitorTransformer.class);
    private final String syntaxNode;

    public SyntaxAddAcceptVisitorTransformer(String syntaxNode) {
        this.syntaxNode = syntaxNode;
    }

    @Override
    public Identifier taskId() {
        return ID;
    }

    @Override
    public Identifier transformsId() {
        return StructureJavaProducer.ID;
    }

    @Override
    public TypeSyntaxResult transform(TypeSyntaxResult result, ShapeCodegenState state) {
        var shape = state.shape();
        if (shape.hasTrait(InterfaceTrait.class)) {
            return result;
        }
        if (!SyntaxVisitorJavaProducer.shapeImplements(syntaxNode(), state.model(), shape)) {
            return result;
        }
        var syntaxShape = state.model().expectShape(ShapeId.from(syntaxNode));
        var syntaxNodeClass = ClassName.toClassName(Utils.toJavaTypeName(state, syntaxShape));
        var visitorClass = ClassName.from(syntaxNodeClass.packageName(), syntaxNodeClass.name() + "Visitor");
        var visitor = ParameterizedTypeName.from(visitorClass, TypeVariableTypeName.from("VisitorR"));
        var name = Utils.toJavaName(state, shape);
        var syntax = ((ClassSyntax) result.syntax())
            .toBuilder()
            .addMethod(acceptMethod(visitor, name))
            .build();
        return result.toBuilder()
                     .syntax(Utils.addGeneratedBy(syntax, SyntaxModelPlugin.ID))
                     .build();
    }

    public String syntaxNode() {
        return syntaxNode;
    }

    private MethodSyntax acceptMethod(ParameterizedTypeName visitor, Name name) {
        return MethodSyntax.builder("accept")
                           .addAnnotation(Override.class)
                           .addModifier(Modifier.PUBLIC)
                           .returns(TypeVariableTypeName.builder()
                                                        .name("VisitorR")
                                                        .addBound(TypeNameExt.OBJECT)
                                                        .build())
                           .addTypeParam(TypeVariableTypeName.from("VisitorR"))
                           .addParameter(visitor, "visitor")
                           .addStatement("return visitor.visit$L(this)", name)
                           .build();
    }
}
