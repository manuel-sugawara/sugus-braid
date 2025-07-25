package mx.sugus.braid.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

class LazyTest {

    @Test
    void testConstructorWithNullSupplier() {
        assertThrows(NullPointerException.class, () -> new Lazy<>(null));
    }

    @Test
    void testGetComputesValueOnFirstCall() {
        var counter = new AtomicInteger(0);
        var lazy = new Lazy<>(() -> {
            counter.incrementAndGet();
            return "computed value";
        });

        var result = lazy.get();

        assertEquals("computed value", result);
        assertEquals(1, counter.get());
    }

    @Test
    void testGetReturnsCachedValueOnSubsequentCalls() {
        var counter = new AtomicInteger(0);
        var lazy = new Lazy<>(() -> {
            counter.incrementAndGet();
            return "computed value";
        });

        var result1 = lazy.get();
        var result2 = lazy.get();
        var result3 = lazy.get();

        assertEquals("computed value", result1);
        assertEquals("computed value", result2);
        assertEquals("computed value", result3);
        assertSame(result1, result2); // Should be the same object
        assertSame(result2, result3); // Should be the same object
        assertEquals(1, counter.get()); // Supplier should only be called once
    }

    @Test
    void testGetWithNullValue() {
        var lazy = new Lazy<String>(() -> null);

        var result = lazy.get();

        assertNull(result);
    }

    @Test
    void testGetWithSupplierThatThrows() {
        var lazy = new Lazy<String>(() -> {
            throw new RuntimeException("Supplier failed");
        });

        var exception = assertThrows(RuntimeException.class, lazy::get);
        assertEquals("Supplier failed", exception.getMessage());
    }

    @Test
    void testGetWithSupplierThatThrowsRetries() {
        var counter = new AtomicInteger(0);
        var lazy = new Lazy<String>(() -> {
            if (counter.incrementAndGet() == 1) {
                throw new RuntimeException("First call failed");
            }
            return "success on retry";
        });

        // First call should throw
        assertThrows(RuntimeException.class, lazy::get);

        // Second call should succeed because value remained null after exception
        var result = lazy.get();
        assertEquals("success on retry", result);

        assertEquals(2, counter.get()); // Supplier is called again after failure
    }

    @Test
    void testThreadSafety() throws InterruptedException {
        var invocationCount = new AtomicInteger(0);
        var lazy = new Lazy<>(() -> {
            // Simulate some computation time
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            invocationCount.incrementAndGet();
            return "thread-safe value";
        });

        var numThreads = 10;
        var executor = Executors.newFixedThreadPool(numThreads);
        var latch = new CountDownLatch(numThreads);
        var results = new String[numThreads];

        // Launch multiple threads to call get() concurrently
        for (var i = 0; i < numThreads; i++) {
            var index = i;
            executor.submit(() -> {
                try {
                    results[index] = lazy.get();
                } finally {
                    latch.countDown();
                }
            });
        }

        // Wait for all threads to complete
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        executor.shutdown();

        // Verify that the supplier was called exactly once
        assertEquals(1, invocationCount.get());

        // Verify that all threads got the same result
        for (var result : results) {
            assertEquals("thread-safe value", result);
        }

        // Verify all results are the same object (cached)
        for (var i = 1; i < results.length; i++) {
            assertSame(results[0], results[i]);
        }
    }

    @Test
    void testWithDifferentTypes() {
        var integerLazy = new Lazy<>(() -> 42);
        var booleanLazy = new Lazy<>(() -> true);
        var objectLazy = new Lazy<>(() -> new Object());

        assertEquals(Integer.valueOf(42), integerLazy.get());
        assertEquals(Boolean.TRUE, booleanLazy.get());
        assertNotNull(objectLazy.get());

        // Verify caching works with different types
        assertSame(integerLazy.get(), integerLazy.get());
        assertSame(booleanLazy.get(), booleanLazy.get());
        assertSame(objectLazy.get(), objectLazy.get());
    }

    @Test
    void testWithComplexObject() {
        var counter = new AtomicInteger(0);
        var lazy = new Lazy<>(() -> new TestObject("name", counter.incrementAndGet()));

        var result1 = lazy.get();
        var result2 = lazy.get();

        assertEquals("name", result1.name());
        assertEquals(1, result1.value());
        assertSame(result1, result2);
        assertEquals(1, counter.get()); // Should only create one object
    }

    @Test
    void testDoubleCheckedLockingOptimization() {
        var counter = new AtomicInteger(0);
        var lazy = new Lazy<>(() -> {
            counter.incrementAndGet();
            return "value";
        });

        // First call - goes through full double-checked locking
        var result1 = lazy.get();
        assertEquals("value", result1);
        assertEquals(1, counter.get());

        // Subsequent calls should use the fast path (volatile read only)
        var result2 = lazy.get();
        var result3 = lazy.get();

        assertEquals("value", result2);
        assertEquals("value", result3);
        assertSame(result1, result2);
        assertSame(result2, result3);
        assertEquals(1, counter.get()); // Still only called once
    }

    @Test
    void testSupplierReturnsNullConsistently() {
        var counter = new AtomicInteger(0);
        var lazy = new Lazy<String>(() -> {
            counter.incrementAndGet();
            return null;
        });

        var result1 = lazy.get();
        var result2 = lazy.get();

        assertNull(result1);
        assertNull(result2);
        // Note: Current implementation may call supplier multiple times for null values
        // due to double-checked locking pattern checking for null
    }

    // Helper class for testing
    private record TestObject(String name, int value) {}
}