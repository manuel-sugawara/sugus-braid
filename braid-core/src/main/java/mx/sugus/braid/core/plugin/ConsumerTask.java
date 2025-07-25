package mx.sugus.braid.core.plugin;

/**
 * A code generation task that consumes artifacts produced by the generation pipeline.
 *
 * <p>Consumers represent the final stage of the code generation pipeline, responsible
 * for taking artifacts that have been produced and optionally transformed, and writing them to their final destination (typically
 * files, but could be other outputs).
 *
 * <p>The consumer pipeline flow:
 * <ol>
 *   <li>Artifacts are produced by {@link ShapeProducerTask}s or {@link NonShapeProducerTask}s</li>
 *   <li>Artifacts may be modified by transformers</li>
 *   <li>Consumers receive the final artifacts and write them to output</li>
 * </ol>
 *
 * <p>Consumers are matched to producers based on the artifact type they handle, allowing
 * multiple consumers to process the same type of artifact in different ways (e.g., one
 * consumer might write Java files while another generates documentation).
 *
 * <p>Consumers should be designed to be stateless and thread-safe as they may be
 * called concurrently for different artifacts.
 *
 * @param <T> The type of artifact that this consumer processes
 * @see ShapeProducerTask
 * @see NonShapeProducerTask
 * @see ShapeTaskTransformer
 * @see NonShapeTaskTransformer
 */
public interface ConsumerTask<T> {

    /**
     * Returns the unique identifier for this consumer task.
     *
     * <p>This identifier is used for tracking and debugging the consumption process
     * and for distinguishing between different consumer implementations.
     *
     * @return The unique identifier for this task
     */
    Identifier taskId();

    /**
     * Returns the class of the artifact type that this consumer processes.
     *
     * <p>This class is used by the pipeline to route artifacts to the appropriate
     * consumers based on their type. Multiple consumers can handle the same type, allowing for different output formats or
     * destinations.
     *
     * @return The class of the artifact type that this consumer handles
     */
    Class<T> input();

    /**
     * Processes and writes the given artifact to its final destination.
     *
     * <p>This is where the consumer performs its final work, typically writing
     * the artifact to files, databases, or other output destinations. The consumer should use the provided state to access the
     * file manifest, settings, and other contextual information needed for output.
     *
     * <p>Common consumer implementations include file writers, documentation
     * generators, and validation tools.
     *
     * @param artifact The artifact to consume and process
     * @param state    The generation state containing context information for output
     */
    void consume(T artifact, CodegenState state);
}
