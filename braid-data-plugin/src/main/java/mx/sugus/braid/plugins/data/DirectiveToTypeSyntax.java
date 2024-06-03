package mx.sugus.braid.plugins.data;

import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.TypeSyntax;

public interface DirectiveToTypeSyntax {
    TypeSyntax build(ShapeCodegenState directive);
}
