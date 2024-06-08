package mx.sugus.braid.core.plugin;

/**
 * Represents a codegen task to transform a produced instance of the type T for the task with the id given by
 * {@link #transformsId()}
 *
 * @param <T> The input and output type of the transformer.
 */
public interface ShapeTaskTransformer<T> {

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
     * @param state The state containing all the data needed for the transformer.
     * @return The transformed type.
     */
    T transform(T type, ShapeCodegenState state);
}
