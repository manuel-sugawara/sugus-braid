package mx.sugus.braid.plugins.data;

import java.util.Collection;
import java.util.List;
import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.jsyntax.Annotation;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.plugins.data.config.DataPluginConfig;
import mx.sugus.braid.plugins.data.dependencies.DataPluginDependencies;
import mx.sugus.braid.plugins.data.producers.EnumJavaProducer;
import mx.sugus.braid.plugins.data.producers.StructureInterfaceJavaProducer;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;
import mx.sugus.braid.plugins.data.producers.UnionJavaProducer;
import mx.sugus.braid.plugins.data.symbols.DataSymbolProviderDecorator;
import mx.sugus.braid.plugins.data.transformers.BuilderAdderOverridesTransform;
import mx.sugus.braid.plugins.data.transformers.BuilderSetterOverridesTransform;
import mx.sugus.braid.plugins.data.transformers.ClassAddBuilderReferenceTransform;
import mx.sugus.braid.plugins.data.transformers.ClassBuilderOverridesTransform;
import mx.sugus.braid.plugins.data.transformers.InterfaceFromFactoryOverridesTransform;
import mx.sugus.braid.plugins.data.transformers.StructureFromFactoryOverridesTransform;
import mx.sugus.braid.plugins.data.transformers.UnionFromFactoryOverridesTransform;
import mx.sugus.braid.rt.util.annotations.Generated;
import software.amazon.smithy.model.node.ObjectNode;

/**
 * The main plugin for generating Java data classes from Smithy models.
 *
 * <p>This plugin provides comprehensive Java code generation for Smithy data structures including:
 * <ul>
 *   <li><strong>Structures</strong> - Generated as Java records or classes with builders</li>
 *   <li><strong>Unions</strong> - Generated as sealed interfaces with variant implementations</li>
 *   <li><strong>Enums</strong> - Generated as Java enums with proper value mappings</li>
 *   <li><strong>Interfaces</strong> - Generated as Java interfaces with implementations</li>
 * </ul>
 *
 * <p>The plugin integrates with the Braid code generation framework by:
 * <ul>
 *   <li>Registering shape producers for each supported Smithy shape type</li>
 *   <li>Providing symbol provider decorations for type mapping</li>
 *   <li>Adding transformers for builder patterns, factory methods, and overrides</li>
 *   <li>Managing dependencies and configuration through {@link DataPluginConfig}</li>
 * </ul>
 *
 * <p>The generated Java code includes:
 * <ul>
 *   <li>Immutable data structures with proper equals/hashCode/toString</li>
 *   <li>Builder patterns for complex types</li>
 *   <li>Factory methods for convenience construction</li>
 *   <li>Null-safety annotations based on configuration</li>
 *   <li>Generated annotations for tooling support</li>
 * </ul>
 *
 * <p>Example usage in a Smithy build:
 * <pre>{@code
 * {
 *   "version": "1.0",
 *   "plugins": {
 *     "mx.sugus.braid.plugins.data": {
 *       "nullabilityCheckMode": "NON_CLIENT_OPTIONAL",
 *       "packageName": "com.example.generated"
 *     }
 *   }
 * }
 * }</pre>
 *
 * @see DataPluginConfig for configuration options
 * @see JavaSyntaxPlugin for Java syntax generation support
 */
public final class DataPlugin implements SmithyGeneratorPlugin<DataPluginConfig> {
    /**
     * The unique identifier for this plugin.
     */
    public static final Identifier ID = Identifier.of(DataPlugin.class);

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
     * Returns the collection of plugin identifiers that this plugin depends on.
     *
     * <p>This plugin requires the {@link JavaSyntaxPlugin} to be loaded first,
     * as it provides the underlying Java syntax tree generation capabilities.
     *
     * @return collection containing the JavaSyntaxPlugin identifier
     */
    @Override
    public Collection<Identifier> requires() {
        return List.of(JavaSyntaxPlugin.ID);
    }

    /**
     * Creates a plugin configuration from the provided JSON node.
     *
     * <p>If the node is null, returns a default configuration with all
     * settings at their default values. Otherwise, deserializes the node
     * into a {@link DataPluginConfig} instance.
     *
     * @param node the JSON node containing plugin configuration, or null for defaults
     * @return the plugin configuration
     */
    @Override
    public DataPluginConfig fromNode(ObjectNode node) {
        if (node == null) {
            return DataPluginConfig.builder().build();
        }
        return DataPluginConfig.fromNode(node);
    }

    /**
     * Builds the code generation module configuration for this plugin.
     *
     * <p>This method registers all the necessary components for Java data class generation:
     * <ul>
     *   <li><strong>Producers</strong> - Generate Java code for structures, unions, enums, and interfaces</li>
     *   <li><strong>Transformers</strong> - Apply builder patterns, factory methods, and overrides</li>
     *   <li><strong>Symbol Provider</strong> - Maps Smithy types to Java types</li>
     *   <li><strong>Dependencies</strong> - Injects plugin configuration into the generation context</li>
     * </ul>
     *
     * @param config the plugin configuration to use for code generation
     * @return the configured code generation module
     */
    @Override
    public CodegenModuleConfig moduleConfig(DataPluginConfig config) {
        return CodegenModuleConfig
            .builder()
            .addProducer(new StructureJavaProducer())
            .addProducer(new StructureInterfaceJavaProducer())
            .addProducer(new EnumJavaProducer())
            .addProducer(new UnionJavaProducer())
            .addTransformer(new BuilderSetterOverridesTransform())
            .addTransformer(new BuilderAdderOverridesTransform())
            .addTransformer(new ClassBuilderOverridesTransform())
            .addTransformer(new StructureFromFactoryOverridesTransform())
            .addTransformer(new UnionFromFactoryOverridesTransform())
            .addTransformer(new InterfaceFromFactoryOverridesTransform())
            .addTransformer(new ClassAddBuilderReferenceTransform())
            .addSymbolProviderDecorator(DataSymbolProviderDecorator.get())
            .putDependency(DataPluginDependencies.DATA_PLUGIN_CONFIG, config)
            .build();
    }
}
