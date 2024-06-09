package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public interface SyntaxNode {

    /**
     * <p>Calls the appropriate visitor method for the given node</p>
     */
    <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor);
}
