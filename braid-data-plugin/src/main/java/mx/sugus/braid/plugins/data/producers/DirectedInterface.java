package mx.sugus.braid.plugins.data.producers;

import java.util.Collections;
import java.util.List;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.TypeSyntax;
import software.amazon.smithy.model.shapes.MemberShape;

public interface DirectedInterface extends DirectiveToTypeSyntax {
    TypeName className(ShapeCodegenState state);

    InterfaceSyntax.Builder typeSpec(ShapeCodegenState state);

    default List<FieldSyntax> extraFields(ShapeCodegenState state) {
        return Collections.emptyList();
    }

    default List<AbstractMethodSyntax> abstractMethodsFor(ShapeCodegenState state, MemberShape member) {
        return Collections.emptyList();
    }

    default List<AbstractMethodSyntax> extraAbstractMethods(ShapeCodegenState state) {
        return Collections.emptyList();
    }

    default List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        return Collections.emptyList();
    }


    default List<DirectiveToTypeSyntax> innerTypes(ShapeCodegenState state) {
        return Collections.emptyList();
    }

    @Override
    default TypeSyntax build(ShapeCodegenState state) {
        var builder = typeSpec(state);
        for (var field : extraFields(state)) {
            builder.addField(field);
        }
        for (var member : state.shape().members()) {
            for (var method : abstractMethodsFor(state, member)) {
                builder.addMethod(method);
            }
        }
        for (var method : extraAbstractMethods(state)) {
            builder.addMethod(method);
        }
        for (var method : extraMethods(state)) {
            builder.addMethod(method);
        }
        for (var inner : innerTypes(state)) {
            var innerType = inner.build(state);
            builder.addInnerType(innerType);
        }
        return builder.build();
    }
}
