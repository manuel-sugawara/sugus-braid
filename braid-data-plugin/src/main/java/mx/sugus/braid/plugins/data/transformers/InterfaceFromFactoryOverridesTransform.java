package mx.sugus.braid.plugins.data.transformers;

import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.CompilationUnit;
import mx.sugus.braid.jsyntax.transforms.AddMethodsTransform;
import mx.sugus.braid.jsyntax.transforms.MethodMatcher;
import mx.sugus.braid.jsyntax.transforms.TypeMatcher;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.StructureInterfaceJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;

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
        var syntax = result.syntax();
        var methods = StructureFromFactoryOverridesTransform.fromFactories(state);
        if (methods.isEmpty()) {
            return result;
        }
        syntax = (CompilationUnit)
            AddMethodsTransform.builder()
                               .addAfter()
                               .methodMatcher(MethodMatcher.any())
                               .typeMatcher(TypeMatcher.byName(Utils.toJavaName(state, state.shape()).toString()))
                               .methods(methods)
                               .build()
                               .transform(syntax);
        return result.toBuilder().syntax(syntax).build();

    }
}
