package mx.sugus.braid.core.plugin;

/**
 * Represents a codegen task to consume instances of type T previously produced and potentially transformed by other configured
 * producers and transformers.
 *
 * @param <T> The type that this consumer consumes.
 */
public interface ConsumerTask<T> {

    /**
     * Returns the identifier for the task.
     *
     * @return The identifier for the task.
     */
    Identifier taskId();

    /**
     * Returns the class of the type that this plugin consumes.
     *
     * @return The class of the type that this plugin consumes.
     */
    Class<T> input();

    /**
     * Consumes the given instance.
     *
     * @param type      The instance to consume.
     * @param directive The directive containing all the data needed for the transformer.
     */
    void consume(T type, CodegenState directive);
}
