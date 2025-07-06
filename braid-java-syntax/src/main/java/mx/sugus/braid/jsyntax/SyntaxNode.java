package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public interface SyntaxNode {

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    Builder toBuilder();

    /**
     * Calls the appropriate visitor method for the given node
     */
    <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor);

    interface Builder {

        /**
         * Builds a new instance of {@link SyntaxNode}
         * 
         * @return The new instance of of {@link SyntaxNode}
         */
        SyntaxNode build();
    }
}
