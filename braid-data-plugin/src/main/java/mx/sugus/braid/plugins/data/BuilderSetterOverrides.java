package mx.sugus.braid.plugins.data;

import static mx.sugus.braid.plugins.data.StructureCodegenUtils.getTargetTrait;
import static mx.sugus.braid.plugins.data.StructureCodegenUtils.toParameters;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.traits.SetterOverride;
import mx.sugus.braid.traits.SetterOverridesTrait;
import software.amazon.smithy.model.shapes.MemberShape;

public final class BuilderSetterOverrides implements DirectedClass {
    static final BuilderSetterOverrides INSTANCE = new BuilderSetterOverrides();

    private BuilderSetterOverrides() {
    }

    @Override
    public ClassName className(ShapeCodegenState state) {
        return StructureCodegenUtils.BUILDER_TYPE;
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
    public List<MethodSyntax> methodsFor(ShapeCodegenState state, MemberShape member) {
        var setterOverrides = getTargetTrait(SetterOverridesTrait.class, state, member);
        if (setterOverrides == null) {
            return List.of();
        }
        var result = new ArrayList<MethodSyntax>();
        for (var override : setterOverrides.getValues()) {
            result.add(settersOverride(state, member, override));
        }
        return result;
    }

    private MethodSyntax settersOverride(ShapeCodegenState state, MemberShape member, SetterOverride override) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberName(member);
        var builder = MethodSyntax.builder(name)
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(className(state));
        builder.parameters(toParameters(override.getArgs()));
        return builder.addStatement("this.$1L = $2L", name, override.getBody())
                      .addStatement("return this")
                      .build();
    }
}

