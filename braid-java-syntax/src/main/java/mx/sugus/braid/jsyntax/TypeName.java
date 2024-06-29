package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>An marker interface for all java types.</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface TypeName extends SyntaxNode {

    TypeKind kind();

    /**
     * Creates a new {@link Builder} to modify a copy of this instance
     */
    Builder toBuilder();

    /**
     * <p>Creates a new TypeName instance out of the given class.</p>
     */
    public static TypeName from(Class<?> kclass) {
        return mx.sugus.braid.jsyntax.ext.TypeNameExt.from(kclass);
    }

    interface Builder extends SyntaxNode.Builder {

        /**
         * Builds a new instance of {@link TypeName}
         */
        TypeName build();
    }
}
