package mx.sugus.braid.core.util;

import java.util.function.Supplier;

/**
 * A value holder that gets lazily computed.
 *
 * @param <T> The type of the value.
 */
public final class Lazy<T> {
    private final Object lock = new Object();
    private final Supplier<T> initializer;
    private volatile T value;

    public Lazy(Supplier<T> initializer) {
        this.initializer = initializer;
    }

    /**
     * Returns the value computing it if needed.
     *
     * @return the value.
     */
    public T get() {
        T result = value;
        if (result == null) {
            synchronized (lock) {
                result = value;
                if (result == null) {
                    result = initializer.get();
                    value = result;
                }
            }
        }
        return value;
    }
}
