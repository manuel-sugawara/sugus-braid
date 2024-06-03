package mx.sugus.braid.core.plugin;

import software.amazon.smithy.model.Model;

/**
 * A task that transforms the model for code generation.
 */
public interface ModelTransformerTask {

    /**
     * Returns the id of the task
     *
     * @return The id of the task
     */
    Identifier taskId();

    /**
     * Transforms the given model and returns the transformed model.
     *
     * @param in The model to transform
     * @return The transformed model¬
     */
    Model transform(Model in);
}
