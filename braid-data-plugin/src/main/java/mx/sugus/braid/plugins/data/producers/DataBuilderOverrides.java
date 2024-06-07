package mx.sugus.braid.plugins.data.producers;

import static mx.sugus.braid.core.util.Utils.coalesce;
import static mx.sugus.braid.plugins.data.producers.StructureCodegenUtils.toParameters;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.traits.NewBuilderOverridesTrait;
import software.amazon.smithy.model.shapes.MemberShape;

public final class DataBuilderOverrides implements DirectedClass {

    static final DataBuilderOverrides INSTANCE = new DataBuilderOverrides();

    private DataBuilderOverrides() {
    }

    @Override
    public ClassSyntax.Builder typeSpec(ShapeCodegenState state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<FieldSyntax> fieldsFor(ShapeCodegenState state, MemberShape member) {
        return List.of();
    }

    @Override
    public List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        return builderMethods(state);
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
                                              .returns(StructureCodegenUtils.BUILDER_TYPE);
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

