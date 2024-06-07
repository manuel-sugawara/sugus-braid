package mx.sugus.braid.plugins.data.producers;

import java.util.ArrayList;
import java.util.List;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.Parameter;
import mx.sugus.braid.traits.Argument;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.traits.Trait;

public class StructureCodegenUtils {

    static final ClassName BUILDER_TYPE = ClassName.from("Builder");

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
}
