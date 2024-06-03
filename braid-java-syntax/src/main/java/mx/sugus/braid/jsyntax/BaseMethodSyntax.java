package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Set;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Base type for all methods.</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface BaseMethodSyntax extends SyntaxNode {

    /**
     * <p>The concrete kind for this method</p>
     */
    MethodKind kind();

    /**
     * <p>The javadoc for the type.</p>
     */
    Javadoc javadoc();

    /**
     * <p>A list of annotations for this method</p>
     */
    List<Annotation> annotations();

    /**
     * <p>A list of modifiers for this method</p>
     */
    Set<Modifier> modifiers();

    /**
     * <p>A list of parameters method</p>
     */
    List<Parameter> parameters();
}
