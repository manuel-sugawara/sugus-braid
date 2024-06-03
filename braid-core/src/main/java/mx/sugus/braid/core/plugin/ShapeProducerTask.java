package mx.sugus.braid.core.plugin;

import software.amazon.smithy.model.shapes.ShapeType;

/**
 * Represents a codegen task to produce instances of type T for a shape of the configured type.
 *
 * @param <T> The produced type
 */
public interface ShapeProducerTask<T> {

    /**
     * Returns the identifier for the task.
     *
     * @return The identifier for the task.
     */
    Identifier taskId();

    /**
     * Returns the class of the type that this instance produce.
     *
     * @return The class of the type that this instance produce.
     */
    Class<T> output();

    /**
     * Returns the shape type that this instance uses to produce.
     *
     * @return The shape type that this instance uses to produce.
     */
    ShapeType type();

    /**
     * Produces a new instance of type T for the given directive.
     *
     * @param directive The directive containing all the data needed for the producer.
     * @return A new instance of type T for the given directive.
     */
    T produce(ShapeCodegenState directive);
}
