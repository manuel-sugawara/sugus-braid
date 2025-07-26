package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Set;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Base type for other Java types.
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface TypeSyntax extends SyntaxNode {

    TypeSyntaxKind kind();

    /**
     * The javadoc for the type.
     */
    Javadoc javadoc();

    /**
     * The simple name for the type.
     */
    String name();

    /**
     * A list of modifiers for this type.
     */
    Set<Modifier> modifiers();

    /**
     * A list of annotations for this type.
     */
    List<Annotation> annotations();

    /**
     * A list of super interfaces for this type.
     */
    List<TypeName> superInterfaces();

    /**
     * A list of fields for this type.
     */
    List<FieldSyntax> fields();

    /**
     * A list of methods for this type.
     */
    List<BaseMethodSyntax> methods();

    /**
     * A list of inner types enclosed by this type.
     */
    List<TypeSyntax> innerTypes();

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    Builder toBuilder();

    interface Builder extends SyntaxNode.Builder {

        /**
         * The javadoc for the type.
         */
        Builder javadoc(Javadoc javadoc);

        /**
         * The simple name for the type.
         */
        Builder name(String name);

        /**
         * A list of modifiers for this type.
         */
        Builder modifiers(Set<Modifier> modifiers);

        /**
         * A list of annotations for this type.
         */
        Builder annotations(List<Annotation> annotations);

        /**
         * A list of super interfaces for this type.
         */
        Builder superInterfaces(List<TypeName> superInterfaces);

        /**
         * A list of fields for this type.
         */
        Builder fields(List<FieldSyntax> fields);

        /**
         * A list of methods for this type.
         */
        Builder methods(List<BaseMethodSyntax> methods);

        /**
         * A list of inner types enclosed by this type.
         */
        Builder innerTypes(List<TypeSyntax> innerTypes);

        /**
         * Builds a new instance of {@link TypeSyntax}
         * 
         * @return The new instance of of {@link TypeSyntax}
         */
        TypeSyntax build();
    }
}
