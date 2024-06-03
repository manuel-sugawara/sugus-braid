package mx.sugus.braid.core.plugin;

/**
 * A shape reducer task is run before all the producers a single result that can be retrieved.
 *
 * @param <T> The result for the reducer.
 */
public interface ShapeReducer<T> {
    /**
     * Returns the identifier for the task.
     *
     * @return The identifier for the task.
     */
    Identifier taskId();

    /**
     * Returns the initial state for this reducer.
     *
     * @return The initial state for this reducer.
     */
    ReducerState<T> init();

    /**
     * State for this reducer.
     *
     * @param <T> The result for the reducer.
     */
    interface ReducerState<T> {
        /**
         * Consumes the shape contained.
         *
         * @param directive The directive containing all the data needed for the reducer.
         */
        void consume(ShapeCodegenState directive);

        /**
         * Finalizes the reduction step and returns the resulting value.
         *
         * @return The resulting value.
         */
        T finalizeJob();
    }
}
