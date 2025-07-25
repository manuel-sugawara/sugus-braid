package mx.sugus.braid.core;

import mx.sugus.braid.core.plugin.ClassPathPluginLoader;
import mx.sugus.braid.core.plugin.CodegenModule;
import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.ComposedPluginLoader;
import mx.sugus.braid.core.plugin.DefaultBaseModuleConfig;
import mx.sugus.braid.core.plugin.DefaultDependencies;
import mx.sugus.braid.core.plugin.PluginLoader;
import mx.sugus.braid.core.plugin.SpiPluginLoader;
import software.amazon.smithy.build.PluginContext;
import software.amazon.smithy.build.SmithyBuildPlugin;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;

/**
 * The main Smithy build plugin for the Braid code generation framework.
 *
 * <p>This plugin serves as the entry point for the Braid code generation system when
 * integrated with Smithy's build process. It orchestrates the initialization and execution of the complete code generation
 * pipeline.
 *
 * <p>The plugin performs the following key operations:
 * <ol>
 *   <li>Parses configuration settings from the Smithy build configuration</li>
 *   <li>Loads and composes plugin modules using both classpath and SPI discovery</li>
 *   <li>Creates and configures the {@link BraidCodegenDirector} with the loaded modules</li>
 *   <li>Delegates the actual code generation execution to the director</li>
 * </ol>
 *
 * <p>Configuration is provided through the Smithy build configuration file and should
 * include settings for service identification, package naming, and plugin-specific options.
 *
 * @see BraidCodegenDirector
 * @see BraidCodegenSettings
 */
public final class BraidCodegenPlugin implements SmithyBuildPlugin {

    /**
     * Returns the name of this plugin as it appears in Smithy build configuration.
     *
     * @return The plugin name "braid-codegen"
     */
    @Override
    public String getName() {
        return "braid-codegen";
    }

    /**
     * Executes the code generation process using the provided plugin context.
     *
     * <p>This method orchestrates the complete code generation workflow:
     * <ol>
     *   <li>Extracts and parses configuration settings from the context</li>
     *   <li>Loads and configures plugin modules using discovered loaders</li>
     *   <li>Creates a {@link BraidCodegenDirector} with the configured modules</li>
     *   <li>Delegates execution to the director</li>
     * </ol>
     *
     * @param context The Smithy build plugin context containing the model, settings, and file manifest
     */
    @Override
    public void execute(PluginContext context) {
        var settingsNode = context.getSettings();
        var settings = BraidCodegenSettings.from(settingsNode);
        var configured = DefaultBaseModuleConfig.buildDependants(pluginLoader(), settings.settingsNode());
        var moduleConfig = mergeDefaults(settings, configured);
        var module = new CodegenModule(moduleConfig);
        BraidCodegenDirector.builder()
                            .model(context.getModel())
                            .settings(settings)
                            .fileManifest(context.getFileManifest())
                            .module(module)
                            .symbolProviderFactory(BraidCodegenPlugin::createSymbolProvider)
                            .build()
                            .execute();
    }

    /**
     * Creates a composed plugin loader that searches both classpath and SPI mechanisms.
     *
     * @return A plugin loader that combines classpath and SPI discovery
     */
    private PluginLoader pluginLoader() {
        return new ComposedPluginLoader(new ClassPathPluginLoader(), new SpiPluginLoader());
    }

    /**
     * Merges the base configuration with plugin-contributed settings.
     *
     * <p>Combines the core settings dependency with plugin-contributed configuration
     * to create the final module configuration used for code generation.
     *
     * @param settings   The base Braid codegen settings
     * @param configured The plugin-contributed configuration
     * @return The merged module configuration
     */
    private CodegenModuleConfig mergeDefaults(BraidCodegenSettings settings, CodegenModuleConfig configured) {
        return CodegenModuleConfig.builder()
                                  .putDependency(DefaultDependencies.SETTINGS, settings)
                                  .merge(configured)
                                  .build();
    }

    /**
     * Creates a default symbol provider that throws for any shape type.
     *
     * <p>This placeholder symbol provider is used when no specific symbol provider
     * is configured. It ensures that plugins must provide appropriate symbol providers for the shapes they intend to process.
     *
     * @param model    The Smithy model (unused in this implementation)
     * @param settings The codegen settings (unused in this implementation)
     * @return A symbol provider that throws {@link UnsupportedOperationException} for all shapes
     */
    private static SymbolProvider createSymbolProvider(Model model, BraidCodegenSettings settings) {
        return shape -> {
            throw new UnsupportedOperationException(
                "shape to symbol not supported for shape type: " + shape.getType()
                + ", shape id: " + shape.getId());
        };
    }
}
