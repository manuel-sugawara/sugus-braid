package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents a Java statement.
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface Statement extends SyntaxNode {

    /**
     * The concrete type of statement.
     */
    StatementKind stmtKind();

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    Builder toBuilder();

    interface Builder extends SyntaxNode.Builder {

        /**
         * Builds a new instance of {@link Statement}
         * 
         * @return The new instance of of {@link Statement}
         */
        Statement build();
    }
}
