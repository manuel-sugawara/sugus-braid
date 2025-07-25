package mx.sugus.braid.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class ResultTest {

    @Test
    void testSuccessCreation() {
        var result = Result.success("test value");

        assertTrue(result.isSuccessful());
        assertEquals("test value", result.unwrap());
    }

    @Test
    void testFailureCreation() {
        var result = Result.failure("error message");

        assertFalse(result.isSuccessful());
        assertEquals("error message", result.failure());
    }

    @Test
    void testSuccessCreationThrowsOnNullValue() {
        assertThrows(NullPointerException.class, () -> Result.success(null));
    }

    @Test
    void testFailureCreationThrowsOnNullError() {
        assertThrows(NullPointerException.class, () -> Result.failure(null));
    }

    @Test
    void testUnwrapOnSuccess() {
        var result = Result.<String, String>success("successful result");

        assertEquals("successful result", result.unwrap());
    }

    @Test
    void testUnwrapOnFailureWithRuntimeException() {
        var exception = new IllegalArgumentException("test error");
        var result = Result.<String, RuntimeException>failure(exception);

        var thrown = assertThrows(IllegalArgumentException.class, result::unwrap);
        assertEquals("test error", thrown.getMessage());
        assertSame(exception, thrown);
    }

    @Test
    void testUnwrapOnFailureWithNonRuntimeException() {
        var result = Result.<String, String>failure("test error");

        var thrown = assertThrows(Result.FailedResult.class, result::unwrap);
        assertEquals("test error", thrown.getMessage());
        assertEquals("test error", thrown.failure());
    }

    @Test
    void testFailureOnSuccess() {
        var result = Result.<String, String>success("test value");

        assertThrows(NoSuchElementException.class, result::failure);
    }

    @Test
    void testFailureOnFailure() {
        var result = Result.<String, String>failure("error message");

        assertEquals("error message", result.failure());
    }

    @Test
    void testIsSuccessfulOnSuccess() {
        var result = Result.<String, String>success("test value");

        assertTrue(result.isSuccessful());
    }

    @Test
    void testIsSuccessfulOnFailure() {
        var result = Result.<String, String>failure("error message");

        assertFalse(result.isSuccessful());
    }

    @Test
    void testFailedResultException() {
        var originalFailure = "original error";
        var failedResult = new Result.FailedResult(originalFailure);

        assertEquals("original error", failedResult.getMessage());
        assertEquals(originalFailure, failedResult.failure());
    }

    @Test
    void testFailedResultWithComplexObject() {
        var errorObject = new TestError("code", "message", 500);
        var failedResult = new Result.FailedResult(errorObject);

        assertEquals(errorObject.toString(), failedResult.getMessage());
        assertSame(errorObject, failedResult.failure());
    }

    @Test
    void testUnwrapWithCustomRuntimeException() {
        var customException = new CustomRuntimeException("custom error");
        var result = Result.<String, CustomRuntimeException>failure(customException);

        var thrown = assertThrows(CustomRuntimeException.class, result::unwrap);
        assertEquals("custom error", thrown.getMessage());
        assertSame(customException, thrown);
    }

    @Test
    void testSuccessWithDifferentTypes() {
        var intResult = Result.<Integer, String>success(42);
        var boolResult = Result.<Boolean, Exception>success(true);

        assertTrue(intResult.isSuccessful());
        assertEquals(Integer.valueOf(42), intResult.unwrap());

        assertTrue(boolResult.isSuccessful());
        assertEquals(Boolean.TRUE, boolResult.unwrap());
    }

    @Test
    void testFailureWithDifferentTypes() {
        var stringResult = Result.<String, Integer>failure(404);
        var exceptionResult = Result.<String, Exception>failure(new RuntimeException("test"));

        assertFalse(stringResult.isSuccessful());
        assertEquals(Integer.valueOf(404), stringResult.failure());

        assertFalse(exceptionResult.isSuccessful());
        assertTrue(exceptionResult.failure() instanceof RuntimeException);
    }

    // Helper classes for testing
    private static class TestError {
        private final String code;
        private final String message;
        private final int statusCode;

        TestError(String code, String message, int statusCode) {
            this.code = code;
            this.message = message;
            this.statusCode = statusCode;
        }

        @Override
        public String toString() {
            return String.format("TestError{code='%s', message='%s', statusCode=%d}", code, message, statusCode);
        }
    }

    private static class CustomRuntimeException extends RuntimeException {
        CustomRuntimeException(String message) {
            super(message);
        }
    }
}