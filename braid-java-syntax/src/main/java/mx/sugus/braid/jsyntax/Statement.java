package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a Java statement.</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface Statement extends SyntaxNode {

    /**
     * <p>The concrete type of statement.</p>
     */
    StatementKind stmtKind();

    /**
     * Creates a new {@link Builder} to modify a copy of this instance
     */
    Builder toBuilder();

    interface Builder extends SyntaxNode.Builder {

        /**
         * Builds a new instance of {@link Statement}
         */
        Statement build();
    }
}
