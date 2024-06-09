package mx.sugus.braid.core.plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Loads a set of plugins using their identifiers, including the ones required by the original set.
 */
public interface PluginLoader {

    /**
     * Attempts to load all the plugins in the given set.
     *
     * @param identifiers The set of identifiers to load.
     * @return The load result.
     */
    LoadResult loadPlugins(Set<Identifier> identifiers);

    /**
     * Loads the plugins and their requirements recursively until all are satisfied or one or more plugins cannot be loaded or
     * found.
     *
     * @param requested The initial set of identifiers to load.
     * @return The load result.
     */
    default LoadResult loadPluginsAndRequirements(Set<Identifier> requested) {
        var result = loadPlugins(requested);
        var allRequired = requiredButNotLoaded(result);
        while (!allRequired.isEmpty()) {
            result = loadPlugins(allRequired).merge(result);
            var newAllRequired = requiredButNotLoaded(result);

            // Ww are not done and didn't make progress, bail out.
            if (newAllRequired.equals(allRequired)) {
                break;
            }
            // Refresh requirements and try again.
            allRequired = newAllRequired;
        }
        return result;
    }

    /**
     * Returns the set of plugins that required but not yet loaded.
     *
     * @param result The last load result
     * @return The set of plugins that required but not yet loaded.
     */
    static Set<Identifier> requiredButNotLoaded(LoadResult result) {
        var allRequired = new HashSet<Identifier>();
        for (var kvp : result.resolved.entrySet()) {
            allRequired.addAll(kvp.getValue().requires());
        }
        allRequired.removeAll(result.resolved.keySet());
        return allRequired;
    }

    record LoadResult(Set<Identifier> unresolved, Map<Identifier, SmithyGeneratorPlugin> resolved) {
        public boolean isFullyResolved() {
            return unresolved.isEmpty();
        }

        public LoadResult merge(LoadResult other) {
            var newResolved = new HashMap<>(resolved);
            newResolved.putAll(other.resolved);
            var newUnresolved = new HashSet<>(unresolved);
            newUnresolved.addAll(other.unresolved);
            newUnresolved.removeAll(newResolved.keySet());
            return new LoadResult(newUnresolved, newResolved);
        }

        @Override
        public String toString() {
            return "{unresolved: "
                   + unresolved
                   + ", resolved: "
                   + resolved.keySet()
                   + "}";
        }
    }
}