package mx.sugus.braid.plugins.syntax;

import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.TypeVariableTypeName;
import mx.sugus.braid.jsyntax.ext.TypeNameExt;
import mx.sugus.braid.jsyntax.transforms.AddMethodsTransform;
import mx.sugus.braid.jsyntax.transforms.MethodMatcher;
import mx.sugus.braid.jsyntax.transforms.TypeMatcher;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import software.amazon.smithy.model.shapes.ShapeId;

public record SyntaxAddAcceptVisitorTransformer(String syntaxNode) implements ShapeTaskTransformer<TypeSyntaxResult> {
    private static final Identifier ID = Identifier.of(SyntaxAddAcceptVisitorTransformer.class);

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
        var methods = acceptMethods(state);
        if (methods.isEmpty()) {
            return result;
        }
        var shape = state.shape();
        var syntax = result.syntax();
        var classSyntax = (ClassSyntax)
            AddMethodsTransform.builder()
                               .addBefore()
                               .methodMatcher(MethodMatcher.byName("equals"))
                               .typeMatcher(TypeMatcher.byName(Utils.toJavaName(state, shape).toString()))
                               .methods(methods)
                               .build()
                               .transform(syntax.type());
        return result.toBuilder()
                     .syntax(syntax.toBuilder().type(Utils.addGeneratedBy(classSyntax, SyntaxModelPlugin.ID)).build())
                     .build();
    }

    private List<MethodSyntax> acceptMethods(ShapeCodegenState state) {
        var shape = state.shape();
        if (!SyntaxVisitorJavaProducer.shapeImplements(syntaxNode(), state.model(), shape)) {
            return List.of();
        }
        var syntaxShape = state.model().expectShape(ShapeId.from(syntaxNode));
        var syntaxNodeClass = ClassName.toClassName(Utils.toJavaTypeName(state, syntaxShape));
        var visitorClass = ClassName.from(syntaxNodeClass.packageName(), syntaxNodeClass.name() + "Visitor");
        var visitor = ParameterizedTypeName.from(visitorClass, TypeVariableTypeName.from("VisitorR"));
        var name = Utils.toJavaName(state, shape);
        return List.of(acceptMethod(visitor, name));
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
