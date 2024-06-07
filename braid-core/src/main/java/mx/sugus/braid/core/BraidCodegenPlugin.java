package mx.sugus.braid.core;

import java.util.Objects;
import java.util.function.Function;
import mx.sugus.braid.core.plugin.ClassPathPluginLoader;
import mx.sugus.braid.core.plugin.CodegenModule;
import mx.sugus.braid.core.plugin.ComposedPluginLoader;
import mx.sugus.braid.core.plugin.DefaultBaseModuleConfig;
import mx.sugus.braid.core.plugin.PluginLoader;
import mx.sugus.braid.core.plugin.SpiPluginLoader;
import mx.sugus.braid.core.symbol.BraidSymbolProvider;
import software.amazon.smithy.build.PluginContext;
import software.amazon.smithy.build.SmithyBuildPlugin;
import software.amazon.smithy.codegen.core.ReservedWords;
import software.amazon.smithy.codegen.core.ReservedWordsBuilder;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;

public final class BraidCodegenPlugin implements SmithyBuildPlugin {
    public static final ReservedWords RESERVED_WORDS = buildReservedWords();

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
        BraidCodegenDirector.builder()
                            .model(model)
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

    private static SymbolProvider createSymbolProvider(Model model, JavaCodegenSettings settings) {
        var escaper = RESERVED_WORDS;
        var shapeToJavaName = new ShapeToJavaName(model, escaper, settings.packageName());
        var shapeToJavaType = new ShapeToJavaType(shapeToJavaName, model);
        return new BraidSymbolProvider(model, shapeToJavaName, shapeToJavaType, settings.packageName());
    }

    private static ReservedWords buildReservedWords() {
        return
            new ReservedWordsBuilder()
                .loadWords(Objects.requireNonNull(BraidCodegenPlugin.class.getResource("java-reserved-words.txt")),
                           Function.identity())
                .loadWords(Objects.requireNonNull(BraidCodegenPlugin.class.getResource("java-system-type-names.txt")),
                           Function.identity())
                .build();
    }
}
