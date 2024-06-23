package mx.sugus.braid.plugins.data.producers;

import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.EnumConstant;
import mx.sugus.braid.jsyntax.EnumSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;

public final class UnionVariantTagEnumData implements DirectedEnum {
    static final ClassName VARIANT_TAG_NAME = ClassName.builder().name("VariantTag").build();

    @Override
    public ClassName className(ShapeCodegenState state) {
        return VARIANT_TAG_NAME;
    }

    @Override
    public EnumSyntax.Builder typeSpec(ShapeCodegenState state) {
        var typeEnum = EnumSyntax.builder(VARIANT_TAG_NAME.name())
                                 .addModifier(Modifier.PUBLIC);
        var shape = state.shape().asUnionShape().orElseThrow();
        for (var member : shape.getAllMembers().values()) {
            var unionVariant = Utils.toSourceName(state, member, Name.Convention.SCREAM_CASE).toString();
            var name = member.getMemberName();
            typeEnum.addEnumConstant(EnumConstant.builder()
                                                 .name(unionVariant)
                                                 .body(CodeBlock.from("$S", name))
                                                 .build());
        }
        typeEnum.addEnumConstant(EnumConstant.builder()
                                             .name("UNKNOWN_TO_VERSION")
                                             .body(CodeBlock.from("null"))
                                             .build());
        return typeEnum;
    }

    @Override
    public List<FieldSyntax> extraFields(ShapeCodegenState state) {
        return List.of(FieldSyntax.from(String.class, "value"));
    }

    @Override
    public List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
        return EnumData.CONSTRUCTORS;
    }

    @Override
    public List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        return List.of(toStringMethod(state));
    }

    MethodSyntax toStringMethod(ShapeCodegenState state) {
        return MethodSyntax.builder("toString")
                           .addAnnotation(Override.class)
                           .addModifier(Modifier.PUBLIC)
                           .returns(String.class)
                           .addStatement("return value")
                           .build();
    }
}
