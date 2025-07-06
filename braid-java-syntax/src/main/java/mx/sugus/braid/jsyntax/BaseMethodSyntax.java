package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Set;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Base type for all methods.
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface BaseMethodSyntax extends SyntaxNode {

    /**
     * The concrete kind for this method
     */
    MethodKind kind();

    /**
     * The javadoc for the type.
     */
    Javadoc javadoc();

    /**
     * A list of annotations for this method
     */
    List<Annotation> annotations();

    /**
     * A list of modifiers for this method
     */
    Set<Modifier> modifiers();

    /**
     * A list of parameters method
     */
    List<Parameter> parameters();

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
         * A list of annotations for this method
         */
        Builder annotations(List<Annotation> annotations);

        /**
         * A list of modifiers for this method
         */
        Builder modifiers(Set<Modifier> modifiers);

        /**
         * A list of parameters method
         */
        Builder parameters(List<Parameter> parameters);

        /**
         * Builds a new instance of {@link BaseMethodSyntax}
         * 
         * @return The new instance of of {@link BaseMethodSyntax}
         */
        BaseMethodSyntax build();
    }
}
