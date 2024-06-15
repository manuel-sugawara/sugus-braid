package mx.sugus.braid.plugins.data.dependencies;

import mx.sugus.braid.core.util.Name;
import software.amazon.smithy.model.shapes.Shape;

/**
 * Encapsulates the logic needed to escape name when the names clash with a reserved word. This can be done by adding suffixes of
 * prefixes and the logic might change depending on the shape type. The escaped name is expected to be safe for use in codegen.
 */
public interface ReservedWordsEscaper {
    /**
     * If the name is a reserved words escapes it into a new name that is not. This can be done by adding suffixes of prefixes and
     * the logic might change depending on the shape type. The returned name is expected to be safe for use in codegen.
     *
     * @param name  The name to ve transformed
     * @param shape The shape for the name
     * @return A transformed if needed or the original name if not.
     */
    Name escape(Name name, Shape shape);
}
