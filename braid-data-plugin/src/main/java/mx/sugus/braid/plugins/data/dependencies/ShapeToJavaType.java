package mx.sugus.braid.plugins.data.dependencies;

import mx.sugus.braid.jsyntax.TypeName;
import software.amazon.smithy.model.shapes.Shape;

/**
 * Converts the given shape to a java type.
 */
public interface ShapeToJavaType {
    /**
     * Returns a java type representation for the given shape.
     *
     * @param shape The shape to convert to a java type
     * @return A java type representation for the given shape.
     */
    TypeName toJavaType(Shape shape);
}
