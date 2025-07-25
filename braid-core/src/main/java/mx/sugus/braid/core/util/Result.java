package mx.sugus.braid.core.util;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A Result type for representing operations that can either succeed or fail without using exceptions.
 *
 * <p>This class provides a functional approach to error handling, similar to Result types found
 * in languages like Rust or Scala. It encapsulates either a successful result of type {@code T} or a failure value of type
 * {@code E}, but never both.
 *
 * <p>Example usage:
 * <pre>{@code
 * Result<String, String> result = someOperation();
 * if (result.isSuccessful()) {
 *     String value = result.unwrap();
 *     // Use the successful value
 * } else {
 *     String error = result.failure();
 *     // Handle the error
 * }
 * }</pre>
 *
 * @param <T> The type of the successful result
 * @param <E> The type of the failure value
 */
public final class Result<T, E> {
    private final T result;
    private final E failure;

    Result(T result, E failure) {
        this.result = result;
        this.failure = failure;
        if (result == null && failure == null) {
            throw new IllegalArgumentException("result and failure cannot be both null");
        }
        if (result != null && failure != null) {
            throw new IllegalArgumentException("result and failure cannot be both not null");
        }
    }

    /**
     * Unwraps the successful result or throws an exception if this Result represents a failure.
     *
     * <p>If the Result contains a failure value that is a RuntimeException, it will be thrown
     * directly. Otherwise, the failure value will be wrapped in a {@link FailedResult} exception.
     *
     * @return The successful result value
     * @throws RuntimeException if the Result represents a failure and the failure is a RuntimeException
     * @throws FailedResult     if the Result represents a failure and the failure is not a RuntimeException
     */
    public T unwrap() {
        if (failure != null) {
            if (failure instanceof RuntimeException t) {
                throw t;
            }
            throw new FailedResult(failure);
        }
        return result;
    }

    /**
     * Returns the failure value if this Result represents a failure.
     *
     * @return The failure value
     * @throws NoSuchElementException if this Result represents a success
     */
    public E failure() {
        if (failure == null) {
            throw new NoSuchElementException("Not in a failed state, try using isSuccessful"
                                             + " to test and unwrap to get the result");
        }
        return failure;
    }

    /**
     * Checks whether this Result represents a successful operation.
     *
     * @return {@code true} if this Result contains a successful value, {@code false} if it contains a failure
     */
    public boolean isSuccessful() {
        return result != null;
    }

    /**
     * Creates a new Result representing a successful operation.
     *
     * @param <T>    The type of the successful result
     * @param <E>    The type of potential failure values
     * @param result The successful result value, must not be null
     * @return A new Result containing the successful value
     * @throws NullPointerException if result is null
     */
    public static <T, E> Result<T, E> success(T result) {
        return new Result<>(Objects.requireNonNull(result, "result"), null);
    }

    /**
     * Creates a new Result representing a failed operation.
     *
     * @param <T>   The type of potential successful results
     * @param <E>   The type of the failure value
     * @param error The failure value, must not be null
     * @return A new Result containing the failure value
     * @throws NullPointerException if error is null
     */
    public static <T, E> Result<T, E> failure(E error) {
        return new Result<>(null, Objects.requireNonNull(error, "error"));
    }

    /**
     * Exception thrown when unwrapping a failed Result that contains a non-RuntimeException failure.
     *
     * <p>This exception wraps the original failure value and provides access to it through
     * the {@link #failure()} method, allowing callers to retrieve the original failure information if needed.
     */
    public static class FailedResult extends RuntimeException {
        private final Object failure;

        /**
         * Creates a new FailedResult exception wrapping the given failure value.
         *
         * @param failure The original failure value
         */
        FailedResult(Object failure) {
            super(failure.toString());
            this.failure = failure;
        }

        /**
         * Returns the original failure value that caused this exception.
         *
         * @return The original failure value
         */
        public Object failure() {
            return failure;
        }
    }
}
