package mx.sugus.braid.plugins.data.producers;

import java.util.ArrayList;
import java.util.List;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.util.Lazy;
import mx.sugus.braid.jsyntax.Annotation;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.MemberValue;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.Modifier;
import mx.sugus.braid.jsyntax.Parameter;
import mx.sugus.braid.traits.Argument;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.traits.Trait;

public final class CodegenUtils {

    static final ClassName BUILDER_TYPE = ClassName.from("Builder");
    static final Lazy<MethodSyntax> TO_STRING_FOR_SENSITIVE = new Lazy<>(
        () -> toStringTemplate()
            .addStatement("return $S", "<*** REDACTED ***>")
            .build()
    );
    static final Lazy<Annotation> SUPPRESS_UNCHECKED = new Lazy<>(
        () -> Annotation.fromStringValue(SuppressWarnings.class, "unchecked"));

    static final Lazy<Annotation> OVERRIDE = new Lazy<>(
        () -> Annotation.builder(Override.class).build());

    public static ClassName builderType() {
        return BUILDER_TYPE;
    }

    public static <T extends Trait> T getTargetTrait(Class<T> kclass, ShapeCodegenState state, MemberShape member) {
        var target = state.model().expectShape(member.getTarget());
        return target.getTrait(kclass).orElse(null);
    }

    public static <T extends Trait> T getTargetListMemberTrait(Class<T> kclass, ShapeCodegenState state, MemberShape member) {
        var memberShape = getTargetListMember(state, member);
        if (memberShape != null) {
            return memberShape.getTrait(kclass).orElse(null);
        }
        return null;
    }

    public static Shape getTargetListMember(ShapeCodegenState state, MemberShape member) {
        var target = state.model().expectShape(member.getTarget());
        if (target.isListShape()) {
            var listTarget = target.asListShape().orElseThrow().getMember().getTarget();
            return state.model().expectShape(listTarget);
        }
        return null;
    }

    public static List<Parameter> toParameters(List<Argument> args) {
        var result = new ArrayList<Parameter>();
        for (var param : args) {
            var type = param.getType();
            if (type.endsWith("...")) {
                result.add(Parameter.builder()
                                    .type(ClassName.parse(type.substring(0, type.length() - 3)))
                                    .name(param.getName())
                                    .varargs(true)
                                    .build());
            } else {
                result.add(Parameter.builder()
                                    .type(ClassName.parse(type))
                                    .name(param.getName())
                                    .build());
            }
        }
        return result;
    }

    public static MethodSyntax toStringForSensitive() {
        return TO_STRING_FOR_SENSITIVE.get();
    }

    public static MethodSyntax.Builder toStringTemplate() {
        return MethodSyntax.builder("toString")
                           .addAnnotation(override())
                           .addModifier(Modifier.PUBLIC)
                           .returns(String.class);
    }

    public static MethodSyntax.Builder equalsTemplate() {
        var builder = MethodSyntax.builder("equals")
                                  .addAnnotation(override())
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(boolean.class)
                                  .addParameter(Object.class, "other");
        builder.ifStatement("this == other", b -> b.addStatement("return true"));
        builder.ifStatement("other == null || getClass() != other.getClass()",
                            then -> then.addStatement("return false"));
        return builder;
    }

    public static MethodSyntax.Builder hashCodeTemplate() {
        return MethodSyntax.builder("hashCode")
                           .addAnnotation(override())
                           .addModifier(Modifier.PUBLIC)
                           .returns(int.class);
    }

    public static Annotation suppressUnchecked() {
        return SUPPRESS_UNCHECKED.get();
    }

    public static Annotation override() {
        return OVERRIDE.get();
    }
}
