package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface Expression extends SyntaxNode {

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    Builder toBuilder();

    interface Builder extends SyntaxNode.Builder {

        /**
         * Builds a new instance of {@link Expression}
         * 
         * @return The new instance of of {@link Expression}
         */
        Expression build();
    }
}
