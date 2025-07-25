package mx.sugus.braid.plugins.data;

import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.DefaultModelTransformerTask;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.plugins.data.model.AddOrderedToMapAndSets;
import software.amazon.smithy.model.node.ObjectNode;

/**
 * A Smithy generator plugin that automatically adds ordered traits to map and set collections.
 *
 * <p>This plugin applies a model transformation that adds the {@code @ordered} trait to all
 * map and set shapes in the Smithy model that don't already have explicit ordering traits.
 * This ensures consistent ordering behavior for collections in generated Java code, using
 * ordered implementations like {@code LinkedHashMap} and {@code LinkedHashSet}.
 *
 * <p>The transformation targets:
 * <ul>
 *   <li><strong>Map shapes</strong> - Adds {@code @ordered} to ensure key insertion order is preserved</li>
 *   <li><strong>Set shapes</strong> - Adds {@code @ordered} to ensure element insertion order is preserved</li>
 * </ul>
 *
 * <p>Benefits of ordered collections:
 * <ul>
 *   <li>Deterministic iteration order for testing and debugging</li>
 *   <li>Consistent serialization output across multiple runs</li>
 *   <li>Better user experience with predictable data ordering</li>
 *   <li>Easier diff comparison of serialized data</li>
 * </ul>
 *
 * <p>The plugin runs during the early model preprocessing phase to ensure that all
 * subsequent code generation sees the collections as ordered. This affects:
 * <ul>
 *   <li>Java type selection (LinkedHashMap vs HashMap)</li>
 *   <li>Builder implementations</li>
 *   <li>Serialization behavior</li>
 * </ul>
 *
 * <p>Example transformation:
 * <pre>{@code
 * // Before transformation:
 * structure Example {
 *     tags: mapReference    // Unordered map
 *     categories: listReference     // Unordered set
 * }
 *
 * // After transformation:
 * structure Example {
 *     @ordered
 *     tags: mapReference    // Now ordered
 *     @ordered
 *     categories: listReference      // Now ordered
 * }
 * }</pre>
 *
 * <p>This plugin requires no configuration and applies the transformation to all
 * applicable shapes in the model.
 *
 * @see AddOrderedToMapAndSets for the actual transformation implementation
 */
public class OrderedCollectionsByDefaultPlugin implements SmithyGeneratorPlugin<ObjectNode> {
    /**
     * The unique identifier for this plugin.
     */
    public static final Identifier ID = Identifier.of(OrderedCollectionsByDefaultPlugin.class);

    /**
     * Creates a new instance of the OrderedCollectionsByDefaultPlugin.
     */
    public OrderedCollectionsByDefaultPlugin() {
    }

    /**
     * Returns the unique identifier for this plugin.
     *
     * @return the plugin identifier
     */
    @Override
    public Identifier provides() {
        return ID;
    }

    /**
     * Returns the configuration node as-is since this plugin requires no configuration.
     *
     * @param node the configuration node (unused)
     * @return the same configuration node
     */
    @Override
    public ObjectNode fromNode(ObjectNode node) {
        return node;
    }

    /**
     * Builds the code generation module configuration with the ordered collections transformer.
     *
     * @param node the configuration node (unused)
     * @return the module configuration with the ordered collections transformer registered
     */
    @Override
    public CodegenModuleConfig moduleConfig(ObjectNode node) {
        return newBaseConfig();
    }

    /**
     * Creates the base configuration for this plugin with the ordered collections transformer.
     *
     * <p>This method registers a model transformer that applies the {@code @ordered} trait
     * to all map and set shapes during the early model preprocessing phase. The transformer
     * ensures that generated Java code uses ordered collection implementations.
     *
     * @return a module configuration with the AddOrderedToMapAndSets transformer
     */
    static CodegenModuleConfig newBaseConfig() {
        return CodegenModuleConfig
            .builder()
            .addModelTransformer(DefaultModelTransformerTask
                                     .builder()
                                     .taskId(Identifier.of(AddOrderedToMapAndSets.class))
                                     .transform(AddOrderedToMapAndSets::transform)
                                     .build())
            .build();
    }
}
