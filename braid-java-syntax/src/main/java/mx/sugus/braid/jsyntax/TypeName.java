package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * An marker interface for all java types.
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface TypeName extends SyntaxNode {

    TypeKind kind();

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    Builder toBuilder();

    /**
     * Creates a new TypeName instance out of the given class.
     */
    public static TypeName from(Class<?> kclass) {
        return mx.sugus.braid.jsyntax.ext.TypeNameExt.from(kclass);
    }

    interface Builder extends SyntaxNode.Builder {

        /**
         * Builds a new instance of {@link TypeName}
         * 
         * @return The new instance of of {@link TypeName}
         */
        TypeName build();
    }
}
