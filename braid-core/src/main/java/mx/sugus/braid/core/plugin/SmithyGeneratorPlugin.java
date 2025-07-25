package mx.sugus.braid.core.plugin;

import java.util.Collection;
import java.util.Collections;
import software.amazon.smithy.model.node.ObjectNode;

/**
 * The core plugin interface for extending the Braid code generation framework.
 *
 * <p>Plugins are the primary extension mechanism for customizing code generation behavior.
 * Each plugin is uniquely identified and can declare dependencies on other plugins to ensure proper initialization order. Plugins
 * contribute configuration elements that are merged to form the final {@link CodegenModuleConfig} used during code generation.
 *
 * <p>Plugin lifecycle:
 * <ol>
 *   <li>Plugin discovery through SPI or classpath scanning</li>
 *   <li>Dependency resolution and topological ordering</li>
 *   <li>Configuration parsing via {@link #fromNode(ObjectNode)}</li>
 *   <li>Module configuration contribution via {@link #moduleConfig(Object)}</li>
 *   <li>Integration into the final codegen pipeline</li>
 * </ol>
 *
 * <p>Plugins can contribute various elements to the code generation process:
 * <ul>
 *   <li>Model transformers for preprocessing Smithy models</li>
 *   <li>Shape producers for generating artifacts from specific shape types</li>
 *   <li>Transformers for modifying generated artifacts</li>
 *   <li>Consumers for writing artifacts to output</li>
 *   <li>Symbol provider decorators for customizing type mapping</li>
 * </ul>
 *
 * @param <C> The configuration type that this plugin expects from the build settings
 */
public interface SmithyGeneratorPlugin<C> {

    /**
     * Returns the unique identifier for this plugin.
     *
     * <p>This identifier is used for plugin discovery, dependency resolution,
     * and avoiding conflicts between different plugins. It should be unique across all plugins in the system.
     *
     * @return The unique identifier for this plugin
     */
    Identifier provides();

    /**
     * Returns the collection of plugin identifiers that this plugin depends on.
     *
     * <p>These dependencies will be loaded and initialized before this plugin
     * is processed, ensuring that required functionality is available. The default implementation returns an empty collection,
     * indicating no dependencies.
     *
     * @return The collection of plugin identifiers that this plugin requires
     */
    default Collection<Identifier> requires() {
        return Collections.emptyList();
    }

    /**
     * Parses plugin configuration from the build settings node.
     *
     * <p>This method is responsible for converting the raw configuration from
     * the Smithy build file into a strongly-typed configuration object. The configuration object will be passed to
     * {@link #moduleConfig(Object)} to generate the actual plugin behavior.
     *
     * @param node The configuration node from the build settings
     * @return The parsed configuration object
     * @throws software.amazon.smithy.model.node.ExpectationNotMetException if configuration is invalid
     */
    C fromNode(ObjectNode node);

    /**
     * Generates the module configuration that defines this plugin's behavior.
     *
     * <p>This is where the plugin contributes its producers, transformers, consumers,
     * model processors, and other components to the code generation pipeline. The returned configuration will be merged with
     * other plugin configurations to form the final generation setup.
     *
     * @param config The parsed configuration from {@link #fromNode(ObjectNode)}
     * @return The module configuration defining this plugin's contributions
     */
    CodegenModuleConfig moduleConfig(C config);
}
