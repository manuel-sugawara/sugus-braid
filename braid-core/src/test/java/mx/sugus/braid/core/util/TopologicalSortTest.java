package mx.sugus.braid.core.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class TopologicalSortTest {

    @Test
    public void testSucceeds() {
        var result = TopologicalSort.of(String.class)
                                    .addEdge("05", "11")
                                    .addEdge("11", "02")
                                    .addEdge("11", "09")
                                    .addEdge("11", "10")
                                    .addEdge("07", "11")
                                    .addEdge("07", "08")
                                    .addEdge("08", "09")
                                    .addEdge("03", "08")
                                    .addEdge("03", "10")
                                    .sort();
        assertThat(result.isSuccessful(), equalTo(true));
        assertThat(result.unwrap(), equalTo(Arrays.asList("05", "03", "07", "08", "11", "02", "09", "10")));
    }

    @Test
    public void testFails() {
        var result = TopologicalSort.of(String.class)
                                    .addEdge("05", "11")
                                    .addEdge("11", "02")
                                    .addEdge("11", "09")
                                    .addEdge("11", "10")
                                    .addEdge("07", "11")
                                    .addEdge("07", "08")
                                    .addEdge("08", "09")
                                    .addEdge("03", "08")
                                    .addEdge("03", "10")
                                    // Introduce a cycle
                                    .addEdge("09", "05")
                                    .sort();
        assertThat(result.isSuccessful(), equalTo(false));
        System.out.println(result.failure());
        assertThrows(RuntimeException.class, result::unwrap);
    }
}

