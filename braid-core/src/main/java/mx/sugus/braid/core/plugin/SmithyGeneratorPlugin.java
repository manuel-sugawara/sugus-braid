package mx.sugus.braid.core.plugin;

import java.util.Collection;
import java.util.Collections;
import software.amazon.smithy.model.node.ObjectNode;

/**
 * Plugins are the extension mechanism for the codegen. A plugin is identified by a name that is used to enable it in the
 * {@code smithy-build.json} file. Plugins can declare that depend on others and therefore the required plugins are also loaded
 * and initialized <em>before</em> it. Plugins contribute to the {@link CodegenModuleConfig} to produce create a new one with its
 * elements added to it.
 *
 * @param <C> The configuration type.
 */
public interface SmithyGeneratorPlugin<C> {

    /**
     * Returns the identifier that identify this plugin.
     *
     * @return The identifier that identify this plugin.
     */
    Identifier provides();

    /**
     * Returns the collection of plugin identifiers that this plugin requires.
     *
     * @return The collection of plugin identifiers that this plugin requires.
     */
    default Collection<Identifier> requires() {
        return Collections.emptyList();
    }

    /**
     * Returns the configuration type from the give {@link ObjectNode}.
     */
    C fromNode(ObjectNode node);

    /**
     * Returns the plugin configuration settings.
     *
     * @param config The configuration settings for the node.
     * @return The plugin configuration settings.
     */
    CodegenModuleConfig moduleConfig(C config);
}
