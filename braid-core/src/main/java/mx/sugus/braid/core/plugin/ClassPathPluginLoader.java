package mx.sugus.braid.core.plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import software.amazon.smithy.build.SmithyBuildException;

/**
 * A plugin loader that uses attempts to load a plugin by directly instantiating it using its identifier as a class name. Skips
 * plugins for which identifier cannot be resolved as a class name.
 */
public final class ClassPathPluginLoader implements PluginLoader {

    @Override
    public LoadResult loadPlugins(Set<Identifier> ids) {
        var plugins = new HashMap<Identifier, SmithyGeneratorPlugin>();
        for (Identifier identifier : ids) {
            SmithyGeneratorPlugin plugin = tryLoadPlugin(identifier);
            if (plugin != null) {
                plugins.put(plugin.provides(), plugin);
            }
        }
        var unresolved = new HashSet<>(ids);
        for (var id : ids) {
            if (plugins.containsKey(id)) {
                unresolved.remove(id);
            }
        }
        return new LoadResult(unresolved, plugins);
    }

    private SmithyGeneratorPlugin tryLoadPlugin(Identifier value) {
        var className = value.namespace() + "." + value.name();
        Class<?> clazz = tryLoad(className);
        if (clazz == null) {
            return null;
        }
        if (!SmithyGeneratorPlugin.class.isAssignableFrom(clazz)) {
            throw new SmithyBuildException("Unable to load plugin with class `"
                                           + className
                                           + "`. It does not implement "
                                           + SmithyGeneratorPlugin.class.getCanonicalName());
        }
        try {
            return (SmithyGeneratorPlugin) clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new SmithyBuildException("Unable to load plugin with class `"
                                           + className
                                           + "`. The class has no nullary constructor", e);
        } catch (InvocationTargetException e) {
            throw new SmithyBuildException("Unable to load plugin with class `"
                                           + className
                                           + "`. Initialization failed: " + e.getMessage(), e);
        }
    }

    static Class<?> tryLoad(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
