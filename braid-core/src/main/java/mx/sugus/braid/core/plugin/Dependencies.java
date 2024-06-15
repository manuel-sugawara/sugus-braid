package mx.sugus.braid.core.plugin;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import mx.sugus.braid.rt.util.AbstractBuilderReference;
import mx.sugus.braid.rt.util.CollectionBuilderReference;

/**
 * A keyed set of dependencies.
 */
public final class Dependencies {

    private final Map<DependencyKey<?>, Object> map;

    Dependencies(Builder builder) {
        this.map = builder.map.asPersistent();
    }

    /**
     * Returns the configured dependency for the given key. It might return a null value if not found and not default value is
     * configured.
     *
     * @param key The key for the dependency
     * @param <T> The type of the dependency
     * @return The configured dependency for the given key.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(DependencyKey<T> key) {
        T result = (T) map.get(key);
        if (result == null) {
            return key.computeDefault(this);
        }
        return result;
    }

    /**
     * Returns the configured dependency for the given key. Throws if the key is not present or if there's not default value for
     * it.
     *
     * @param key The key for the dependency
     * @param <T> The type of the dependency
     * @return The configured dependency for the given key.
     */
    @SuppressWarnings("unchecked")
    public <T> T expect(DependencyKey<T> key) {
        T result = (T) map.get(key);
        if (result == null) {
            result = key.computeDefault(this);
        }
        if (result == null) {
            throw new NoSuchElementException(key.toString());
        }
        return result;
    }

    /**
     * Returns an optional configured dependency for the given key.
     *
     * @param key The key for the dependency
     * @param <T> The type of the dependency
     * @return The configured optional dependency for the given key.
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getOptional(DependencyKey<T> key) {
        T result = (T) map.get(key);
        if (result == null) {
            result = key.computeDefault(this);
        }
        return Optional.ofNullable(result);
    }

    /**
     * Convert this instance to builder.
     *
     * @return The new builder.
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Returns a new dependencies builder.
     *
     * @return a new dependencies builder.
     */
    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        final CollectionBuilderReference<Map<DependencyKey<?>, Object>> map;

        Builder() {
            this.map = CollectionBuilderReference.forUnorderedMap();
        }

        Builder(Dependencies map) {
            this.map = CollectionBuilderReference.fromPersistentUnorderedMap(map.map);
        }

        /**
         * Puts a new dependency with the given key and value.
         *
         * @param key   the key for the dependency
         * @param value the value for the dependency
         * @param <T>   the type of the dependency
         * @return this instance for method chaining
         */
        public <T> Builder put(DependencyKey<T> key, T value) {
            map.asTransient().put(key, Objects.requireNonNull(value, "value"));
            return this;
        }

        /**
         * Puts all the dependencies from the given dependency. If any of the keys already exists in the builder it will be
         * overwritten.
         *
         * @param dependencies the dependencies to merge from
         * @return this instance for method chaining
         */
        public Builder putAll(Dependencies dependencies) {
            map.asTransient().putAll(dependencies.map);
            return this;
        }

        /**
         * Returns a new {@link Dependencies} instance with the configured dependencies.
         *
         * @return a new {@link Dependencies} instance with the configured dependencies.
         */
        public Dependencies build() {
            return new Dependencies(this);
        }
    }

    public static class DependenciesReferenceBuilder extends AbstractBuilderReference<Dependencies, Dependencies.Builder> {
        private static final Dependencies EMPTY = builder().build();

        @Override
        protected Dependencies emptyPersistent() {
            return EMPTY;
        }

        @Override
        protected Builder emptyTransient() {
            return builder();
        }

        @Override
        protected Dependencies transientToPersistent(Builder source) {
            return source.build();
        }

        @Override
        protected Builder persistentToTransient(Dependencies source) {
            return source.toBuilder();
        }

        @Override
        protected Builder clearTransient(Builder source) {
            return builder();
        }
    }
}
