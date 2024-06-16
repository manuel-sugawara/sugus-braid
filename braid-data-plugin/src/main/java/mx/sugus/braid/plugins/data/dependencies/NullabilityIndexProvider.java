package mx.sugus.braid.plugins.data.dependencies;

import software.amazon.smithy.model.Model;

/**
 * Creates a nullability index for the given model.
 */
public interface NullabilityIndexProvider {
    /**
     * Creates a nullability index out of the given model.
     *
     * @param model The model for the index.
     * @return A nullability index out of the given model.
     */
    NullabilityIndex of(Model model);
}
