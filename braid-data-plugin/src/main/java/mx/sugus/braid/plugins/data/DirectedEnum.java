package mx.sugus.braid.plugins.data;

import java.util.Collections;
import java.util.List;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.EnumSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.TypeSyntax;

public interface DirectedEnum extends DirectiveToTypeSyntax {
    default ClassName className(ShapeCodegenState state) {
        return ClassName.toClassName(state.symbolProvider().toJavaTypeName(state.shape()));
    }

    EnumSyntax.Builder typeSpec(ShapeCodegenState state);

    default List<FieldSyntax> extraFields(ShapeCodegenState state) {
        return Collections.emptyList();
    }

    default List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
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

        for (var method : constructors(state)) {
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
