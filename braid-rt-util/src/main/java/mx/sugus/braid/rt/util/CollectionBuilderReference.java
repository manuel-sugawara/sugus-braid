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
 * Builder reference for collections.
 *
 * @param <T> The collection type.
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
     * Creates a builder reference to a list.
     *
     * @param <T> Type of value in the list.
     * @return Returns the created list.
     */
    static <T> CollectionBuilderReference<List<T>> forList() {
        return new ListBuilderReference<>();
    }

    /**
     * Creates a builder reference to a list.
     *
     * @param <T> Type of value in the list.
     * @return Returns the created list.
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
     * A builder reference for maps.
     *
     * @param <K> The type of the key.
     * @param <V> The type of the value.
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
            if (asTransient != null) {
                asTransient.clear();
            }
            return asTransient;
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
            if (asTransient != null) {
                asTransient.clear();
            }
            return asTransient;
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
            if (asTransient != null) {
                asTransient.clear();
            }
            return asTransient;
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
            if (asTransient != null) {
                asTransient.clear();
            }
            return asTransient;
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
            if (asTransient != null) {
                asTransient.clear();
            }
            return asTransient;
        }
    }
}
