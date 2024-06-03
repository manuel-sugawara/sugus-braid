package mx.sugus.braid.core;

import mx.sugus.braid.core.plugin.ClassPathPluginLoader;
import mx.sugus.braid.core.plugin.CodegenModule;
import mx.sugus.braid.core.plugin.ComposedPluginLoader;
import mx.sugus.braid.core.plugin.DefaultBaseModuleConfig;
import mx.sugus.braid.core.plugin.PluginLoader;
import mx.sugus.braid.core.plugin.SpiPluginLoader;
import software.amazon.smithy.build.PluginContext;
import software.amazon.smithy.build.SmithyBuildPlugin;

public final class BraidCodegenPlugin implements SmithyBuildPlugin {

    @Override
    public String getName() {
        return "braid-codegen";
    }

    @Override
    public void execute(PluginContext context) {
        var settingsNode = context.getSettings();
        var settings = JavaCodegenSettings.from(settingsNode);
        var module = new CodegenModule(DefaultBaseModuleConfig.buildDependants(pluginLoader(), settings.settingsNode()));
        var model = module.earlyPreprocessModel(context.getModel());
        SmithyCodegenDirector.builder()
                             .model(model)
                             .settings(settings)
                             .fileManifest(context.getFileManifest())
                             .module(module)
                             .build()
                             .execute();
    }

    private PluginLoader pluginLoader() {
        return new ComposedPluginLoader(new ClassPathPluginLoader(), new SpiPluginLoader());
    }
}
