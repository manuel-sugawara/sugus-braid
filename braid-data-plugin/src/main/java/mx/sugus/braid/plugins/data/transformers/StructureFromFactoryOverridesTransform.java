package mx.sugus.braid.plugins.data.transformers;

import static mx.sugus.braid.core.util.Utils.coalesce;
import static mx.sugus.braid.plugins.data.producers.CodegenUtils.toParameters;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.CompilationUnit;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.jsyntax.transforms.AddMethodsTransform;
import mx.sugus.braid.jsyntax.transforms.MethodMatcher;
import mx.sugus.braid.jsyntax.transforms.TypeMatcher;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.traits.BuilderOverride;
import mx.sugus.braid.traits.FromFactoriesTrait;

public class StructureFromFactoryOverridesTransform implements ShapeTaskTransformer<TypeSyntaxResult> {

    public static final Identifier ID = Identifier.of(StructureFromFactoryOverridesTransform.class);

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
        var syntax = result.syntax();
        var methods = fromFactories(state);
        if (methods.isEmpty()) {
            return result;
        }
        syntax = (CompilationUnit)
            AddMethodsTransform.builder()
                               .addAfter()
                               .methodMatcher(MethodMatcher.byName("builder"))
                               .typeMatcher(TypeMatcher.byName(Utils.toJavaName(state, state.shape()).toString()))
                               .methods(methods)
                               .build()
                               .transform(syntax);
        return result.toBuilder().syntax(syntax).build();
    }

    static List<MethodSyntax> fromFactories(ShapeCodegenState state) {
        var shape = state.shape();
        if (!shape.hasTrait(FromFactoriesTrait.class)) {
            return List.of();
        }
        var result = new ArrayList<MethodSyntax>();
        var fromFactories = shape.expectTrait(FromFactoriesTrait.class);
        for (var override : fromFactories.getValues()) {
            result.add(fromFactory(state, override));
        }
        return result;
    }

    static MethodSyntax fromFactory(ShapeCodegenState state, BuilderOverride override) {
        var shapeType = Utils.toJavaTypeName(state, state.shape());
        var builder = MethodSyntax.builder(coalesce(override.getName(), "from"))
                                  .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                  .returns(shapeType)
                                  .parameters(toParameters(override.getArgs()));
        var javadoc = override.getJavadoc();
        if (javadoc != null) {
            builder.javadoc(JavadocExt.document(javadoc));
        }
        builder.body(b -> {
            for (var stmt : override.getBody()) {
                b.addStatement(stmt);
            }
        });
        return builder.build();
    }

}
