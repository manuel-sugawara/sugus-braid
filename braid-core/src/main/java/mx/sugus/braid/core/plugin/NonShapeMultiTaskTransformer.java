package mx.sugus.braid.core.plugin;

import java.util.Collection;

/**
 * Represents a codegen task to transform a produced instance of the type T for the task with the id given by
 * {@link #transformsId()}
 *
 * @param <T> The input and output type of the transformer.
 */
public interface NonShapeMultiTaskTransformer<T> {

    /**
     * Returns the identifier for the task.
     *
     * @return The identifier for the task.
     */
    Identifier taskId();

    /**
     * Returns the identifier for the task that this transformer consumes from.
     *
     * @return The identifier for the task that this transformer consumes from.
     */
    Identifier transformsId();

    /**
     * Transforms the give type and returns the transformed instance.
     *
     * @param type      The type to transform.
     * @param directive The directive containing all the data needed for the transformer.
     * @return The transformed type.
     */
    Collection<T> transform(Collection<T> type, CodegenState directive);
}
