package mx.sugus.braid.plugins.data.transformers;

import static mx.sugus.braid.core.util.Utils.coalesce;
import static mx.sugus.braid.plugins.data.producers.CodegenUtils.toParameters;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.jsyntax.transforms.AddMethodsTransform;
import mx.sugus.braid.jsyntax.transforms.MethodMatcher;
import mx.sugus.braid.jsyntax.transforms.TypeMatcher;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.CodegenUtils;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.traits.NewBuilderOverridesTrait;

public final class ClassBuilderOverridesTransform implements ShapeTaskTransformer<TypeSyntaxResult> {
    public static final Identifier ID = Identifier.of(ClassBuilderOverridesTransform.class);

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
        var syntax = (ClassSyntax) result.syntax();
        var symbolProvider = state.symbolProvider();
        var methods = builderMethods(state);
        if (methods.isEmpty()) {
            return result;
        }
        syntax = (ClassSyntax)
            AddMethodsTransform.builder()
                               .addAfter()
                               .methodMatcher(MethodMatcher.byName("builder"))
                               .typeMatcher(TypeMatcher.byName(Utils.toJavaName(state, state.shape()).toString()))
                               .methods(methods)
                               .build()
                               .transform(syntax);
        return result.toBuilder().syntax(syntax).build();

    }

    private List<MethodSyntax> builderMethods(ShapeCodegenState state) {
        if (!state.shape().hasTrait(NewBuilderOverridesTrait.class)) {
            return List.of();
        }
        var result = new ArrayList<MethodSyntax>();
        var builderOverrides = state.shape().expectTrait(NewBuilderOverridesTrait.class);
        for (var override : builderOverrides.getValues()) {
            var javadoc = coalesce(override.getJavadoc(), "Creates a new builder");
            var overrideBuilder = MethodSyntax.builder("builder")
                                              .javadoc(JavadocExt.document(javadoc))
                                              .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                              .returns(CodegenUtils.builderType());
            overrideBuilder.parameters(toParameters(override.getArgs()));
            var body = new BodyBuilder();
            for (var value : override.getBody()) {
                body.addStatement("$L", value);
            }
            overrideBuilder.body(body.build());
            result.add(overrideBuilder.build());
        }
        return result;
    }
}
