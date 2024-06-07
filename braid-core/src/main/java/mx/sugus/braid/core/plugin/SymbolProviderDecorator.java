package mx.sugus.braid.core.plugin;

import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;

/**
 * Decorates the symbol provider.
 */
public interface SymbolProviderDecorator {

    /**
     * Decorates the given symbol provider.
     *
     * @param model     the model
     * @param decorated the symbol provider to decorate
     * @return the decorated symbol provider
     */
    SymbolProvider decorate(Model model, SymbolProvider decorated);
}
