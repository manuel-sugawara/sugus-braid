package mx.sugus.braid.core.util;

import java.util.NoSuchElementException;
import java.util.Objects;

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

    public T unwrap() {
        if (failure != null) {
            if (failure instanceof RuntimeException t) {
                throw t;
            }
            throw new FailedResult(failure);
        }
        return result;
    }

    public E failure() {
        if (failure == null) {
            throw new NoSuchElementException("Not in a failed state, try using isSuccessful"
                                             + " to test and unwrap to get the result");
        }
        return failure;
    }

    public boolean isSuccessful() {
        return result != null;
    }

    public static <T, E> Result<T, E> success(T result) {
        return new Result<>(Objects.requireNonNull(result, "result"), null);
    }

    public static <T, E> Result<T, E> failure(E error) {
        return new Result<>(null, Objects.requireNonNull(error, "error"));
    }

    public static class FailedResult extends RuntimeException {
        private final Object failure;

        FailedResult(Object failure) {
            super(failure.toString());
            this.failure = failure;
        }

        public Object failure() {
            return failure;
        }
    }
}
