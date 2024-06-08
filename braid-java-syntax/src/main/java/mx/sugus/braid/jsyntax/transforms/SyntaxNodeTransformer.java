package mx.sugus.braid.jsyntax.transforms;

import mx.sugus.braid.jsyntax.SyntaxNode;

/**
 * Represents a syntax node transformation.
 */
public interface SyntaxNodeTransformer {

    /**
     * Transforms the given syntax node and returns the result.
     *
     * @param node the node to transform
     * @return The transformed node.
     */
    SyntaxNode transform(SyntaxNode node);
}
