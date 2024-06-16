package mx.sugus.braid.plugins.data.producers;

import java.util.Collections;
import java.util.List;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.TypeSyntax;
import software.amazon.smithy.model.shapes.MemberShape;

public interface DirectedClass extends DirectiveToTypeSyntax {
    @Override
    default ClassName className(ShapeCodegenState state) {
        return ClassName.toClassName(Utils.toJavaTypeName(state, state.shape()));
    }

    ClassSyntax.Builder typeSpec(ShapeCodegenState state);

    List<FieldSyntax> fieldsFor(ShapeCodegenState state, MemberShape member);

    default List<FieldSyntax> extraFields(ShapeCodegenState state) {
        return Collections.emptyList();
    }

    default List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
        return Collections.emptyList();
    }

    default List<MethodSyntax> methodsFor(ShapeCodegenState state, MemberShape member) {
        return Collections.emptyList();
    }

    default List<AbstractMethodSyntax> abstractMethodsFor(ShapeCodegenState state, MemberShape member) {
        return Collections.emptyList();
    }

    default List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        return Collections.emptyList();
    }

    default List<AbstractMethodSyntax> extraAbstractMethods(ShapeCodegenState state) {
        return Collections.emptyList();
    }

    default List<DirectiveToTypeSyntax> innerTypes(ShapeCodegenState state) {
        return Collections.emptyList();
    }

    @Override
    default TypeSyntax build(ShapeCodegenState state) {
        var builder = typeSpec(state);
        for (var member : state.shape().members()) {
            for (var field : fieldsFor(state, member)) {
                builder.addField(field);
            }
        }
        for (var field : extraFields(state)) {
            builder.addField(field);
        }

        for (var method : constructors(state)) {
            builder.addMethod(method);
        }
        for (var member : state.shape().members()) {
            for (var method : methodsFor(state, member)) {
                builder.addMethod(method);
            }
        }
        for (var member : state.shape().members()) {
            for (var method : abstractMethodsFor(state, member)) {
                builder.addMethod(method);
            }
        }
        for (var method : extraMethods(state)) {
            builder.addMethod(method);
        }
        for (var method : extraAbstractMethods(state)) {
            builder.addMethod(method);
        }
        for (var inner : innerTypes(state)) {
            var innerType = inner.build(state);
            builder.addInnerType(innerType);
        }
        return builder.build();
    }
}
