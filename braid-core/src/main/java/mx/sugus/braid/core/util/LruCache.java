package mx.sugus.braid.core.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A utility implementation of a bounded LRU cache using {@link LinkedHashMap}, not thread safe.
 *
 * @param <K> The type of the key
 * @param <V> The type fo the value
 */
public final class LruCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LruCache(int capacity) {
        super(capacity, 0.75f, true); // true for access-order
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
