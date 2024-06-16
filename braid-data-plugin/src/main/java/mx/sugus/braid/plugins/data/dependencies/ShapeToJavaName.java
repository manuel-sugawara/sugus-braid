package mx.sugus.braid.plugins.data.dependencies;

import mx.sugus.braid.core.util.Name;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;

/**
 * Encapsulates the logic to convert a shape to an idiomatic java name.¬
 */
public interface ShapeToJavaName {
    /**
     * Converts the given shape to an idiomatic name, without any validation.
     *
     * @param shape The shape to covert.
     * @param model The model containing the shape.
     * @return A java name for the given shape.
     */
    Name toName(Shape shape, Model model);

    /**
     * Converts the given shape to an idiomatic name, the name is expected to be usable within java, meaning that reserved words
     * should be converted into a usable name.
     *
     * @param shape The shape to covert.
     * @param model The model containing the shape.
     * @return A java name for the given shape.
     */
    Name toJavaName(Shape shape, Model model);

    /**
     * Returns the configured java package for the given shape.
     *
     * @param shape The shape to get the package for.
     * @return A java package for the given shape.
     */
    String toJavaPackage(Shape shape);
}
