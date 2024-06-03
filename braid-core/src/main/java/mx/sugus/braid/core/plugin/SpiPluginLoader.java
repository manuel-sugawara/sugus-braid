package mx.sugus.braid.core.plugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * A Plugin loader using Java Service Provider interface.
 */
public class SpiPluginLoader implements PluginLoader {

    @Override
    public LoadResult loadPlugins(Set<Identifier> ids) {
        var resolved = Container.LOADED;
        var unresolved = new HashSet<>(ids);
        var plugins = new HashMap<Identifier, SmithyGeneratorPlugin>();
        for (var id : ids) {
            if (resolved.containsKey(id)) {
                plugins.put(id, resolved.get(id));
                unresolved.remove(id);
            }
        }
        return new LoadResult(unresolved, plugins);
    }

    static class Container {
        // Cache for the loaded plugins.
        static Map<Identifier, SmithyGeneratorPlugin> LOADED = loadPlugins();

        static Map<Identifier, SmithyGeneratorPlugin> loadPlugins() {
            var plugins = new HashMap<Identifier, SmithyGeneratorPlugin>();
            for (var plugin : serviceLoaderLoadPlugins()) {
                plugins.put(plugin.provides(), plugin);
            }
            return Collections.unmodifiableMap(plugins);
        }

        static ServiceLoader<SmithyGeneratorPlugin> serviceLoaderLoadPlugins() {
            return ServiceLoader.load(SmithyGeneratorPlugin.class,
                                      SmithyGeneratorPlugin.class.getClassLoader());
        }
    }
}
