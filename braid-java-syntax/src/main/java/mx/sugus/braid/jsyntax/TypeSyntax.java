package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Set;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Base type for other Java types.</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface TypeSyntax extends SyntaxNode {

    TypeSyntaxKind kind();

    /**
     * <p>The javadoc for the type.</p>
     */
    Javadoc javadoc();

    /**
     * <p>The simple name for the type.</p>
     */
    String name();

    /**
     * <p>A list of modifiers for this type.</p>
     */
    Set<Modifier> modifiers();

    /**
     * <p>A list of annotations for this type.</p>
     */
    List<Annotation> annotations();

    /**
     * <p>A list of super interfaces for this type.</p>
     */
    List<TypeName> superInterfaces();

    /**
     * <p>A list of fields for this type.</p>
     */
    List<FieldSyntax> fields();

    /**
     * <p>A list of methods for this type.</p>
     */
    List<BaseMethodSyntax> methods();

    /**
     * <p>A list of inner types enclosed by this type.</p>
     */
    List<TypeSyntax> innerTypes();
}
