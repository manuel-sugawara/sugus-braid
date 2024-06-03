package mx.sugus.braid.core.util;

import java.util.function.Supplier;

/**
 * Miscellaneous utility functions.
 */
public final class Utils {
    private Utils() {
    }

    /**
     * Returns the first not null element.
     *
     * @param <T> The return type
     */
    public static <T> T coalesce(T left, T right) {
        if (left != null) {
            return left;
        }
        return right;
    }

    /**
     * Returns the first element if not null, otherwise returns the result of calling the supplier.
     *
     * @param <T> The return type
     */
    public static <T> T coalesce(T left, Supplier<T> orElse) {
        if (left != null) {
            return left;
        }
        return orElse.get();
    }
}
