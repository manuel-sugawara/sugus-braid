package mx.sugus.braid.core.plugin;

public interface NonShapeProducerTask<T> {
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
     * Produces a new instance of type T for the given state.
     *
     * @param state The state containing all the data needed for the producer.
     * @return A new instance of type T for the given state.
     */
    T produce(CodegenState state);
}
