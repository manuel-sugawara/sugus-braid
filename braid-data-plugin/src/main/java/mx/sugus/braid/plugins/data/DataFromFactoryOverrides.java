package mx.sugus.braid.plugins.data;

import static mx.sugus.braid.plugins.data.StructureCodegenUtils.toParameters;
import static mx.sugus.braid.core.util.Utils.coalesce;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.traits.BuilderOverride;
import mx.sugus.braid.traits.FromFactoriesTrait;
import software.amazon.smithy.model.shapes.MemberShape;

public final class DataFromFactoryOverrides implements DirectedClass {

    static final DataFromFactoryOverrides INSTANCE = new DataFromFactoryOverrides();

    private DataFromFactoryOverrides() {
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
        return fromFactories(state);
    }

    private List<MethodSyntax> fromFactories(ShapeCodegenState state) {
        var shape = state.shape();
        if (shape.hasTrait(FromFactoriesTrait.class)) {
            var result = new ArrayList<MethodSyntax>();
            var fromFactories = shape.expectTrait(FromFactoriesTrait.class);
            for (var override : fromFactories.getValues()) {
                result.add(fromFactory(state, override));
            }
            return result;
        }
        return List.of();
    }

    static MethodSyntax fromFactory(ShapeCodegenState state, BuilderOverride override) {
        var symbolProvider = state.symbolProvider();
        var shapeType = symbolProvider.toJavaTypeName(state.shape());
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

