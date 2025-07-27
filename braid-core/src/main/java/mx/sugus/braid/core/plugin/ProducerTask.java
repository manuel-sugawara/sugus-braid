package mx.sugus.braid.core.plugin;

import software.amazon.smithy.model.shapes.ShapeType;

/**
 * A code generation task that produces base type.
 */
public interface ProducerTask<T> {

    /**
     * Returns the unique identifier for this producer task.
     *
     * <p>This identifier is used to link transformers to specific producers and
     * for distinguishing between different producer implementations.
     *
     * @return The unique identifier for this task
     */
    Identifier taskId();

    /**
     * Returns the class of the artifact type that this producer generates.
     *
     * <p>This class is used by consumers to determine which artifacts they can
     * process and by the pipeline for type-safe artifact routing.
     *
     * @return The class of the artifact type produced by this task
     */
    Class<T> output();
}
