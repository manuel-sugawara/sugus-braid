package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface Javadoc extends SyntaxNode {

    /**
     * Creates a new {@link Builder} to modify a copy of this instance
     */
    Builder toBuilder();

    interface Builder extends SyntaxNode.Builder {

        /**
         * Builds a new instance of {@link Javadoc}
         */
        Javadoc build();
    }
}
