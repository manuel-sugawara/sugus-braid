package mx.sugus.braid.core.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.core.util.TopologicalSort;
import software.amazon.smithy.build.SmithyBuildException;
import software.amazon.smithy.model.node.ObjectNode;

public final class DefaultBaseModuleConfig {

    private DefaultBaseModuleConfig() {
    }

    public static CodegenModuleConfig newBaseConfig() {
        return CodegenModuleConfig.builder().build();
    }

    public static CodegenModuleConfig buildDependants(PluginLoader loader, ObjectNode basePluginConfig) {
        var pluginsEnabled = pluginsEnabled(basePluginConfig);
        var pluginsLoaded = loader.loadPluginsAndRequirements(pluginsEnabled.keySet());
        if (!pluginsLoaded.isFullyResolved()) {
            throw new RuntimeException("Unresolved plugins: " + pluginsLoaded.unresolved());
        }
        var configBuilder = CodegenModuleConfig.builder();
        var sortedPlugins = sortPlugins(pluginsLoaded.resolved());
        for (var plugin : sortedPlugins) {
            var pluginConfig = pluginsEnabled.get(plugin.provides());
            configBuilder.merge(plugin.moduleConfig(pluginConfig));
        }
        return configBuilder.build();
    }

    private static List<SmithyGeneratorPlugin> sortPlugins(Map<Identifier, SmithyGeneratorPlugin> pluginsLoaded) {
        var sorting = TopologicalSort.of(Identifier.class);
        for (var kvp : pluginsLoaded.entrySet()) {
            var pluginId = kvp.getKey();
            sorting.addVertex(pluginId);
            for (var required : kvp.getValue().requires()) {
                sorting.addEdge(required, pluginId);
            }
        }
        var sortingResult = sorting.sort();
        if (!sortingResult.isSuccessful()) {
            throw new SmithyBuildException(String.format("Plugins dependencies contains a cycle: %s", sortingResult.failure()));
        }
        var sortedIds = sortingResult.unwrap();
        var result = new ArrayList<SmithyGeneratorPlugin>();
        for (var pluginId : sortedIds) {
            var plugin = pluginsLoaded.get(pluginId);
            result.add(Objects.requireNonNull(plugin));
        }
        return result;
    }

    static Map<Identifier, ObjectNode> pluginsEnabled(ObjectNode node) {
        var pluginsNode = node.getObjectMember("plugins").orElse(null);
        if (pluginsNode != null) {
            var pluginsEnabled = new HashMap<Identifier, ObjectNode>();
            pluginsNode.getMembers().forEach((k, v) -> {
                pluginsEnabled.put(Identifier.of(k.getValue()), v.expectObjectNode());
            });
            return pluginsEnabled;
        }
        return Collections.emptyMap();
    }
}
