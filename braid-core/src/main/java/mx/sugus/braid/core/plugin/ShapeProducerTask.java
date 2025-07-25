package mx.sugus.braid.core.plugin;

import software.amazon.smithy.model.shapes.ShapeType;

/**
 * A code generation task that produces artifacts from Smithy shapes of a specific type.
 *
 * <p>Shape producers are the foundation of the code generation pipeline. Each producer
 * is responsible for converting Smithy shapes of a particular {@link ShapeType} into intermediate representations or final
 * artifacts that can be further processed by transformers and eventually consumed by output writers.
 *
 * <p>The producer operates as part of a larger pipeline:
 * <ol>
 *   <li>The producer receives a {@link ShapeCodegenState} containing the shape and context</li>
 *   <li>It generates an artifact of type {@code T} from the shape</li>
 *   <li>The artifact can be modified by registered {@link ShapeTaskTransformer}s</li>
 *   <li>Finally, the artifact is written by appropriate {@link ConsumerTask}s</li>
 * </ol>
 *
 * <p>Producers should be designed to be stateless and thread-safe as they may be
 * called concurrently for different shapes.
 *
 * @param <T> The type of artifact produced by this task
 * @see ShapeTaskTransformer
 * @see ConsumerTask
 */
public interface ShapeProducerTask<T> {

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

    /**
     * Returns the Smithy shape type that this producer can process.
     *
     * <p>The producer will only be invoked for shapes of this type. This allows
     * specialization of different producers for different shape types (e.g., one producer for structures, another for enums).
     *
     * @return The shape type that this producer handles
     */
    ShapeType type();

    /**
     * Generates an artifact from the given shape and context.
     *
     * <p>This is the core method where the producer performs its work, transforming
     * a Smithy shape into an intermediate representation or final artifact. The producer should use the provided state to access
     * the model, symbol provider, and other contextual information needed for generation.
     *
     * <p>Returning {@code null} will cause the artifact to be skipped in the pipeline,
     * which can be useful for conditional generation.
     *
     * @param state The generation state containing the shape and context information
     * @return A new artifact instance, or {@code null} to skip processing
     */
    T produce(ShapeCodegenState state);
}
