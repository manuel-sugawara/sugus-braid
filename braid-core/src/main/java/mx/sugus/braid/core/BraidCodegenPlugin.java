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

public final class BraidCodegenPlugin implements SmithyBuildPlugin {

    @Override
    public String getName() {
        return "braid-codegen";
    }

    @Override
    public void execute(PluginContext context) {
        var settingsNode = context.getSettings();
        var settings = BrideCodegenSettings.from(settingsNode);
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

    private PluginLoader pluginLoader() {
        return new ComposedPluginLoader(new ClassPathPluginLoader(), new SpiPluginLoader());
    }

    private CodegenModuleConfig mergeDefaults(BrideCodegenSettings settings, CodegenModuleConfig configured) {
        return CodegenModuleConfig.builder()
                                  .putDependency(DefaultDependencies.SETTINGS, settings)
                                  .merge(configured)
                                  .build();
    }

    private static SymbolProvider createSymbolProvider(Model model, BrideCodegenSettings settings) {
        return shape -> {
            throw new UnsupportedOperationException(
                "shape to symbol not supported for shape type: " + shape.getType()
                + ", shape id: " + shape.getId());
        };
    }
}
