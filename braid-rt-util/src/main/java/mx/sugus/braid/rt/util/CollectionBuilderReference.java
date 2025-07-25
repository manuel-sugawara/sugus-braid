package mx.sugus.braid.rt.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Specialized BuilderReference for Java collections that provides copy-on-write optimization
 * for common collection types used in code generation pipelines.
 * <p>
 * This interface provides factory methods for creating BuilderReference instances optimized
 * for specific collection types:
 * <ul>
 *   <li><strong>Maps</strong>: Both ordered (LinkedHashMap) and unordered (HashMap) variants</li>
 *   <li><strong>Sets</strong>: Both ordered (LinkedHashSet) and unordered (HashSet) variants</li>
 *   <li><strong>Lists</strong>: ArrayList-based implementation</li>
 * </ul>
 * 
 * <h3>Usage in Code Generation</h3>
 * <p>Typical usage pattern in a builder class:
 * <pre>{@code
 * public class ConfigBuilder {
 *     private final CollectionBuilderReference<Map<String, String>> properties = 
 *         CollectionBuilderReference.forOrderedMap();
 *     
 *     public ConfigBuilder fromTemplate(Config template) {
 *         properties.setPersistent(template.getProperties());
 *         return this;
 *     }
 *     
 *     public ConfigBuilder addProperty(String key, String value) {
 *         properties.asTransient().put(key, value); // Lazy copy on first modification
 *         return this;
 *     }
 *     
 *     public Config build() {
 *         return new Config(properties.asPersistent());
 *     }
 * }
 * }</pre>
 * 
 * <h3>Performance Benefits</h3>
 * <p>For code generation scenarios where many builders are created from templates:
 * <ul>
 *   <li><strong>Template reuse</strong>: Zero copy cost when builders aren't modified</li>
 *   <li><strong>Incremental building</strong>: Only pay copy cost when actually making changes</li>
 *   <li><strong>Memory efficiency</strong>: Share immutable data between multiple builders</li>
 * </ul>
 * 
 * <p><strong>Thread Safety</strong>: Not thread-safe. Each instance should be used by a single thread.
 * However, the persistent (immutable) collections returned by {@code asPersistent()} are thread-safe.
 *
 * @param <T> The collection type (Map, Set, List, etc.)
 */
public interface CollectionBuilderReference<T> extends BuilderReference<T, T> {

    /**
     * Creates a builder reference to an unordered map.
     *
     * @param <K> Type of key of the map.
     * @param <V> Type of value of the map.
     * @return Returns the created map.
     */
    static <K, V> CollectionBuilderReference<Map<K, V>> forUnorderedMap() {
        return new UnorderedMapBuilderReference<>();
    }

    /**
     * Creates a builder reference to an unordered map borrowing from the given argument.
     *
     * @param <K> Type of key of the map.
     * @param <V> Type of value of the map.
     * @return Returns the created map.
     */
    static <K, V> CollectionBuilderReference<Map<K, V>> fromPersistentUnorderedMap(Map<K, V> persistent) {
        return new UnorderedMapBuilderReference<>(persistent);
    }

    /**
     * Creates a builder reference to an ordered map.
     *
     * @param <K> Type of key of the map.
     * @param <V> Type of value of the map.
     * @return Returns the created map.
     */
    static <K, V> CollectionBuilderReference<Map<K, V>> forOrderedMap() {
        return new OrderedMapBuilderReference<>();
    }

    /**
     * Creates a builder reference to an ordered map borrowing from the given map.
     *
     * @param <K> Type of key of the map.
     * @param <V> Type of value of the map.
     * @return Returns the created map.
     */
    static <K, V> CollectionBuilderReference<Map<K, V>> fromPersistentOrderedMap(Map<K, V> persistent) {
        return new OrderedMapBuilderReference<>(persistent);
    }

    /**
     * Creates a builder reference for a list (ArrayList-based).
     * <p>
     * Provides efficient random access and good performance for most operations.
     * Order is always preserved.
     *
     * @param <T> Type of elements in the list
     * @return A new builder reference starting in empty state
     */
    static <T> CollectionBuilderReference<List<T>> forList() {
        return new ListBuilderReference<>();
    }

    /**
     * Creates a builder reference for a list, initialized with existing data.
     * <p>
     * The provided list will be used as the initial persistent state. Element order
     * will be preserved.
     *
     * @param <T> Type of elements in the list
     * @param persistent the initial persistent list data, may be {@code null} for empty
     * @return A new builder reference initialized with the provided data
     */
    static <T> CollectionBuilderReference<List<T>> fromPersistentList(List<T> persistent) {
        return new ListBuilderReference<>(persistent);
    }

    /**
     * Creates a builder reference to an unordered set.
     *
     * @param <T> Type of value in the set.
     * @return Returns the created set.
     */
    static <T> CollectionBuilderReference<Set<T>> forUnorderedSet() {
        return new UnorderedSetBuilderReference<>();
    }

    /**
     * Creates a builder reference to an unordered set.
     *
     * @param <T> Type of value in the set.
     * @return Returns the created set.
     */
    static <T> CollectionBuilderReference<Set<T>> fromPersistentUnorderedSet(Set<T> persistent) {
        return new UnorderedSetBuilderReference<>(persistent);
    }

    /**
     * Creates a builder reference to an ordered set.
     *
     * @param <T> Type of value in the set.
     * @return Returns the created set.
     */
    static <T> CollectionBuilderReference<Set<T>> forOrderedSet() {
        return new OrderedSetBuilderReference<>();
    }

    /**
     * Creates a builder reference to an ordered set.
     *
     * @param <T> Type of value in the set.
     * @return Returns the created set.
     */
    static <T> CollectionBuilderReference<Set<T>> fromPersistentOrderedSet(Set<T> persistent) {
        return new OrderedSetBuilderReference<>(persistent);
    }

    /**
     * BuilderReference implementation for unordered maps using HashMap as the transient representation
     * and Collections.unmodifiableMap as the persistent representation.
     * <p>
     * This implementation provides O(1) average-case performance for put/get operations
     * but does not preserve insertion order.
     *
     * @param <K> The type of the map keys
     * @param <V> The type of the map values
     */
    class UnorderedMapBuilderReference<K, V>
        extends AbstractBuilderReference<Map<K, V>, Map<K, V>>
        implements CollectionBuilderReference<Map<K, V>> {

        UnorderedMapBuilderReference() {
        }

        UnorderedMapBuilderReference(Map<K, V> persistent) {
            super(persistent);
        }

        @Override
        protected Map<K, V> emptyPersistent() {
            return Collections.emptyMap();
        }

        @Override
        protected Map<K, V> emptyTransient() {
            return new HashMap<>();
        }

        @Override
        protected Map<K, V> transientToPersistent(Map<K, V> source) {
            return Collections.unmodifiableMap(source);
        }

        @Override
        protected Map<K, V> persistentToTransient(Map<K, V> source) {
            return new HashMap<>(source);
        }

        @Override
        protected Map<K, V> clearTransient(Map<K, V> source) {
            source.clear();
            return source;
        }
    }

    /**
     * A builder reference for maps that keep insert order as iteration order, backed up by {@link LinkedHashMap}.
     *
     * @param <K> The type of the key.
     * @param <V> The type of the value.
     */
    class OrderedMapBuilderReference<K, V>
        extends AbstractBuilderReference<Map<K, V>, Map<K, V>>
        implements CollectionBuilderReference<Map<K, V>> {

        OrderedMapBuilderReference() {
        }

        OrderedMapBuilderReference(Map<K, V> persistent) {
            super(persistent);
        }

        @Override
        protected Map<K, V> emptyPersistent() {
            return Collections.emptyMap();
        }

        @Override
        protected Map<K, V> emptyTransient() {
            return new LinkedHashMap<>();
        }

        @Override
        protected Map<K, V> transientToPersistent(Map<K, V> source) {
            return Collections.unmodifiableMap(source);
        }

        @Override
        protected Map<K, V> persistentToTransient(Map<K, V> source) {
            return new LinkedHashMap<>(source);
        }

        @Override
        protected Map<K, V> clearTransient(Map<K, V> source) {
            source.clear();
            return source;
        }
    }

    /**
     * A builder reference for lists.
     *
     * @param <T> The type of the list member.
     */
    class ListBuilderReference<T>
        extends AbstractBuilderReference<List<T>, List<T>>
        implements CollectionBuilderReference<List<T>> {

        ListBuilderReference() {
        }

        ListBuilderReference(List<T> persistent) {
            super(persistent);
        }

        @Override
        protected List<T> emptyPersistent() {
            return Collections.emptyList();
        }

        @Override
        protected List<T> emptyTransient() {
            return new ArrayList<>();
        }

        @Override
        protected List<T> transientToPersistent(List<T> source) {
            return Collections.unmodifiableList(source);
        }

        @Override
        protected List<T> persistentToTransient(List<T> source) {
            return new ArrayList<>(source);
        }

        @Override
        protected List<T> clearTransient(List<T> source) {
            source.clear();
            return source;
        }
    }

    /**
     * A builder reference for sets.
     *
     * @param <T> The type of the set member.
     */
    class UnorderedSetBuilderReference<T>
        extends AbstractBuilderReference<Set<T>, Set<T>>
        implements CollectionBuilderReference<Set<T>> {

        UnorderedSetBuilderReference() {
        }

        UnorderedSetBuilderReference(Set<T> persistent) {
            super(persistent);
        }

        @Override
        protected Set<T> emptyPersistent() {
            return Collections.emptySet();
        }

        @Override
        protected Set<T> emptyTransient() {
            return new HashSet<>();
        }

        @Override
        protected Set<T> transientToPersistent(Set<T> source) {
            return Collections.unmodifiableSet(source);
        }

        @Override
        protected Set<T> persistentToTransient(Set<T> source) {
            return new HashSet<>(source);
        }

        @Override
        protected Set<T> clearTransient(Set<T> source) {
            source.clear();
            return source;
        }
    }

    /**
     * A builder reference for sets that keep insert order as iteration order, backed up by {@link LinkedHashSet}.
     *
     * @param <T> The type of the set member.
     */
    class OrderedSetBuilderReference<T>
        extends AbstractBuilderReference<Set<T>, Set<T>>
        implements CollectionBuilderReference<Set<T>> {

        OrderedSetBuilderReference() {
        }

        OrderedSetBuilderReference(Set<T> persistent) {
            super(persistent);
        }

        @Override
        protected Set<T> emptyPersistent() {
            return Collections.emptySet();
        }

        @Override
        protected Set<T> emptyTransient() {
            return new LinkedHashSet<>();
        }

        @Override
        protected Set<T> transientToPersistent(Set<T> source) {
            return Collections.unmodifiableSet(source);
        }

        @Override
        protected Set<T> persistentToTransient(Set<T> source) {
            return new LinkedHashSet<>(source);
        }

        @Override
        protected Set<T> clearTransient(Set<T> source) {
            source.clear();
            return source;
        }
    }
}
