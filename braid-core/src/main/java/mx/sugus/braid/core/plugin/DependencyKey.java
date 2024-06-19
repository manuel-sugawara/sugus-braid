package mx.sugus.braid.core.plugin;

import java.util.Objects;
import java.util.function.Function;

/**
 * Represents keys to identify dependencies.
 *
 * @param <T> The type of the dependency
 */
public final class DependencyKey<T> {

    private final String name;
    private final Function<Dependencies, T> computeDependency;

    DependencyKey(String name) {
        this.name = Objects.requireNonNull(name, "name");
        this.computeDependency = x -> null;
    }

    DependencyKey(String name, Function<Dependencies, T> computeDependency) {
        this.name = Objects.requireNonNull(name, "name");
        this.computeDependency = computeDependency;
    }

    /**
     * Returns the default value for this key
     */
    public T computeDefault(Dependencies dependencies) {
        return computeDependency.apply(dependencies);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Creates a new dependency key with the given name
     *
     * @param name Name for the dependency
     * @param <T>  the type of the dependency
     * @return a new property with the given name
     */
    public static <T> DependencyKey<T> from(String name) {
        return new DependencyKey<>(name);
    }

    /**
     * Creates a new dependency key with the given name
     *
     * @param name              Name for the dependency
     * @param computeDependency A supplier for the default value
     * @param <T>               the type of the dependency
     * @return a new property with the given name
     */
    public static <T> DependencyKey<T> from(String name, Function<Dependencies, T> computeDependency) {
        return new DependencyKey<>(name, computeDependency);
    }
}
