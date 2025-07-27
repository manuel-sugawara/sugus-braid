package mx.sugus.braid.core.plugin;

import java.util.Collection;

public interface NonShapeMultiProducerTask<T> extends ProducerTask<T> {
    /**
     * Produces a new instance of type T for the given state.
     *
     * @param state The state containing all the data needed for the producer.
     * @return A new instance of type T for the given state.
     */
    Collection<T> produce(CodegenState state);
}
