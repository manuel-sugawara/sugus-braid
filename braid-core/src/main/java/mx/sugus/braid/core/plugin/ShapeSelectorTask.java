package mx.sugus.braid.core.plugin;

import java.util.Collection;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;

/**
 * Selects from a model the shapes used for code generation.
 */
public interface ShapeSelectorTask {

    /**
     * Returns the id of the task
     *
     * @return The id of the task
     */
    Identifier taskId();

    /**
     * Returns the selected shapes for code generation from the given model.
     *
     * @param model The source model
     * @return The selected shapes for code generation
     */
    Collection<Shape> select(Model model);
}
