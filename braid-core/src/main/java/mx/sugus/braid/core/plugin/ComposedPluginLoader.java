package mx.sugus.braid.core.plugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

/**
 * Composes two plugin loaders, and loads plugins by using the first and then the second if there are still unresolved plugins.
 */
public final class ComposedPluginLoader implements PluginLoader {
    private final PluginLoader left;
    private final PluginLoader right;

    /**
     * Creates a new composed plugin loader using the loaders given.
     *
     * @param left  The left plugin loader
     * @param right The right plugin loader
     */
    public ComposedPluginLoader(PluginLoader left, PluginLoader right) {
        this.left = Objects.requireNonNull(left, "left");
        this.right = Objects.requireNonNull(right, "right");
    }

    @Override
    public LoadResult loadPlugins(Set<Identifier> ids) {
        var leftResult = left.loadPlugins(ids);
        if (leftResult.isFullyResolved()) {
            return leftResult;
        }
        var rightResult = right.loadPlugins(leftResult.unresolved());
        var plugins = new HashMap<>(leftResult.resolved());
        plugins.putAll(rightResult.resolved());
        return new LoadResult(rightResult.unresolved(), plugins);
    }
}
