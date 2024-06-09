package mx.sugus.braid.plugins.data.producers;

import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.transforms.AddMethodsTransform;
import mx.sugus.braid.jsyntax.transforms.MethodMatcher;
import mx.sugus.braid.jsyntax.transforms.TypeMatcher;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;

public class InterfaceFromFactoryOverridesTransform implements ShapeTaskTransformer<TypeSyntaxResult> {

    public static final Identifier ID = Identifier.of(InterfaceFromFactoryOverridesTransform.class);

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
        var syntax = (InterfaceSyntax) result.syntax();
        var methods = ClassFromFactoryOverridesTransform.fromFactories(state);
        if (methods.isEmpty()) {
            return result;
        }
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(state.shape());
        syntax = (InterfaceSyntax)
            AddMethodsTransform.builder()
                               .addAfter()
                               .methodMatcher(MethodMatcher.any())
                               .typeMatcher(TypeMatcher.byName(Utils.toJavaName(symbol).toString()))
                               .methods(methods)
                               .build()
                               .transform(syntax);
        return result.toBuilder().syntax(syntax).build();

    }
}
