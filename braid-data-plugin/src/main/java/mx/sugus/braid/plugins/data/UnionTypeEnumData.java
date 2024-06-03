package mx.sugus.braid.plugins.data;

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

public final class UnionTypeEnumData implements DirectedEnum {
    static final ClassName TYPE_NAME = ClassName.builder().name("Type").build();

    @Override
    public ClassName className(ShapeCodegenState state) {
        return TYPE_NAME;
    }

    @Override
    public EnumSyntax.Builder typeSpec(ShapeCodegenState state) {
        var typeEnum = EnumSyntax.builder(TYPE_NAME.name())
                                 .addModifier(Modifier.PUBLIC);
        var shape = state.shape().asUnionShape().orElseThrow();
        var symbolProvider = state.symbolProvider();
        for (var member : shape.getAllMembers().values()) {
            var enumName = symbolProvider.toJavaName(member, Name.Convention.SCREAM_CASE).toString();
            var name = member.getMemberName();
            typeEnum.addEnumConstant(EnumConstant.builder()
                                                 .name(enumName)
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
        return List.of(FieldSyntax.builder()
                                  .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                                  .name("value")
                                  .type(String.class)
                                  .build());
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
                           .body(b -> b.addStatement("return value"))
                           .build();
    }
}
