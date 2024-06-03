package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public interface SyntaxNode {

    <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor);
}
