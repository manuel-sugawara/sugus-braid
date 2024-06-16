package mx.sugus.braid.plugins.data.producers;

import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.CompilationUnit;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.TypeSyntax;

public interface DirectiveToTypeSyntax {
    TypeSyntax build(ShapeCodegenState state);

    TypeName className(ShapeCodegenState state);

    default CompilationUnit buildCompilationUnit(ShapeCodegenState state) {
        var className = ClassName.toClassName(className(state));
        return CompilationUnit.builder()
                              .packageName(className.packageName())
                              .type(build(state))
                              .build();
    }
}
