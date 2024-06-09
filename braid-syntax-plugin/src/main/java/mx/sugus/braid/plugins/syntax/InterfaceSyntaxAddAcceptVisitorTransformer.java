package mx.sugus.braid.plugins.syntax;

import java.util.List;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.TypeVariableTypeName;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.jsyntax.transforms.AddMethodsTransform;
import mx.sugus.braid.jsyntax.transforms.MethodMatcher;
import mx.sugus.braid.jsyntax.transforms.TypeMatcher;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.StructureInterfaceJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import software.amazon.smithy.model.shapes.ShapeId;

public final class InterfaceSyntaxAddAcceptVisitorTransformer implements ShapeTaskTransformer<TypeSyntaxResult> {
    private static final Identifier ID = Identifier.of(SyntaxAddAcceptVisitorTransformer.class);
    private final String syntaxNode;

    public InterfaceSyntaxAddAcceptVisitorTransformer(String syntaxNode) {
        this.syntaxNode = syntaxNode;
    }

    @Override
    public Identifier taskId() {
        return ID;
    }

    @Override
    public Identifier transformsId() {
        return StructureInterfaceJavaProducer.ID;
    }

    @Override
    public TypeSyntaxResult transform(TypeSyntaxResult result, ShapeCodegenState state) {
        var methods = acceptMethods(state);
        if (methods.isEmpty()) {
            return result;
        }
        var shape = state.shape();
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(shape);
        var syntax = (InterfaceSyntax)
            AddMethodsTransform.builder()
                               .addAfter()
                               .methodMatcher(MethodMatcher.any())
                               .typeMatcher(TypeMatcher.byName(Utils.toJavaName(symbol).toString()))
                               .methods(methods)
                               .build()
                               .transform(result.syntax());
        return result.toBuilder()
                     .syntax(Utils.addGeneratedBy(syntax, SyntaxModelPlugin.ID))
                     .build();
    }

    private List<AbstractMethodSyntax> acceptMethods(ShapeCodegenState state) {
        var shape = state.shape();
        if (!shape.getId().toString().equals(syntaxNode)) {
            return List.of();
        }
        return List.of(acceptMethod(state));
    }

    private AbstractMethodSyntax acceptMethod(ShapeCodegenState state) {
        var syntaxShape = state.model().expectShape(ShapeId.from(syntaxNode));
        var syntaxNodeClass = ClassName.toClassName(Utils.toJavaTypeName(state, syntaxShape));
        var visitorClass = ClassName.from(syntaxNodeClass.packageName(), syntaxNodeClass.name() +
                                                                         "Visitor");
        var visitorResultTypeVar = TypeVariableTypeName.builder()
                                                       .name("VisitorR")
                                                       .build();
        var visitor = ParameterizedTypeName.from(visitorClass, visitorResultTypeVar);
        return AbstractMethodSyntax.builder()
                                   .name("accept")
                                   .javadoc(JavadocExt.document("Calls the appropriate visitor method for the given node"))
                                   .returns(visitorResultTypeVar)
                                   .addTypeParam(visitorResultTypeVar)
                                   .addParameter(visitor, "visitor")
                                   .build();
    }

    private String syntaxNode() {
        return syntaxNode;
    }
}
