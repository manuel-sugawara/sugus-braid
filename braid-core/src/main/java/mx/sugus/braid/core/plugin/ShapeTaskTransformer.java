package mx.sugus.braid.core.plugin;

/**
 * A code generation task that transforms artifacts produced by specific producer tasks.
 *
 * <p>Shape transformers provide a mechanism to modify or enhance artifacts generated
 * by producers before they are consumed by output writers. Transformers are linked to specific producer tasks through the
 * {@link #transformsId()} method, ensuring they only process artifacts from intended sources.
 *
 * <p>The transformation pipeline flow:
 * <ol>
 *   <li>A {@link ShapeProducerTask} generates an artifact</li>
 *   <li>All registered transformers for that producer are applied in sequence</li>
 *   <li>Each transformer receives the current artifact state and returns a modified version</li>
 *   <li>The final transformed artifact is passed to consumers</li>
 * </ol>
 *
 * <p>Transformers can return {@code null} to abort the pipeline for a particular
 * artifact, preventing it from being processed by subsequent transformers or consumers.
 * This is useful for conditional code generation or filtering.
 *
 * <p>Common transformation use cases include:
 * <ul>
 *   <li>Adding methods or fields to generated classes</li>
 *   <li>Modifying naming conventions</li>
 *   <li>Adding annotations or metadata</li>
 *   <li>Filtering artifacts based on conditions</li>
 * </ul>
 *
 * @param <T> The type of artifact that this transformer processes (both input and output)
 * @see ShapeProducerTask
 * @see ConsumerTask
 */
public interface ShapeTaskTransformer<T> {

    /**
     * Returns the unique identifier for this transformer task.
     *
     * <p>This identifier is used for tracking and debugging the transformation process
     * and for distinguishing between different transformer implementations.
     *
     * @return The unique identifier for this task
     */
    Identifier taskId();

    /**
     * Returns the identifier of the producer task that this transformer processes.
     *
     * <p>This creates a link between the producer and transformer, ensuring that
     * transformers only process artifacts from their intended sources. Multiple transformers can target the same producer, and
     * they will be applied in sequence.
     *
     * @return The identifier of the producer task that this transformer targets
     */
    Identifier transformsId();

    /**
     * Transforms the given artifact and returns the modified version.
     *
     * <p>This is where the transformer performs its work, modifying or enhancing
     * the artifact produced by the target producer. Transformers can add methods, modify properties, apply annotations, or
     * perform any other modifications needed for the final output.
     *
     * <p>Returning {@code null} will cause the artifact to be removed from the
     * pipeline, preventing it from being processed by subsequent transformers or consumers. This can be useful for conditional
     * generation or filtering.
     *
     * @param artifact The artifact to transform
     * @param state    The generation state containing the shape and context information
     * @return The transformed artifact, or {@code null} to remove from pipeline
     */
    T transform(T artifact, ShapeCodegenState state);
}
