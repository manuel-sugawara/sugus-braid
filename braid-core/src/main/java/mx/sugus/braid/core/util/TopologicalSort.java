package mx.sugus.braid.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Topological sort using Kahn's algorithm. The final order is deterministic, tie breaking is done first by the least amount of
 * outgoing edges and then using the `tieBreaker` comparator.
 *
 * @param <T> The type of the sorted elements.
 */
public final class TopologicalSort<T> {
    private final Map<T, Set<T>> incoming = new HashMap<>();
    private final Map<T, Set<T>> outgoing = new HashMap<>();
    private final Comparator<T> tieBreaker;

    /**
     * Creates a new instance using the given comparator to break ties.
     *
     * @param tieBreaker The comparator used to break ties.
     */
    public TopologicalSort(Comparator<T> tieBreaker) {
        this.tieBreaker = tieBreaker;
    }

    /**
     * Adds a new element to the graph without recording any relationship.
     *
     * @param vertex The vertex to add
     * @return This instance for chaining calls
     */
    public TopologicalSort<T> addVertex(T vertex) {
        // record that the vertex exists without any known relationship yet.
        outgoing.computeIfAbsent(vertex, v -> new HashSet<>());
        incoming.computeIfAbsent(vertex, v -> new HashSet<>());
        return this;
    }

    /**
     * Adds a new pair of elements with a relationship from ⟶ to with the elements. This implies that {@code from} will appear
     * <em>before</em> the {@code to} element in the final result.
     *
     * @param from The from element
     * @param to   The to element
     * @return This instance for chaining calls
     */
    public TopologicalSort<T> addEdge(T from, T to) {
        // record `from -> to` in the outgoing matrix
        outgoing.computeIfAbsent(from, v -> new HashSet<>()).add(to);
        outgoing.computeIfAbsent(to, v -> new HashSet<>());

        // record `to <- from` in the incoming matrix
        incoming.computeIfAbsent(to, v -> new HashSet<>()).add(from);
        incoming.computeIfAbsent(from, v -> new HashSet<>());

        return this;
    }

    /**
     * Returns a topological sort of the graph using Kahn's algorithm. The final order is deterministic, tie breaking is done
     * first by the least amount of outgoing edges and then using the `tieBreaker` comparator.
     * <p>
     * The state of the algorithm is cleared after the sort method gets called.
     *
     * @return A topological sort of the elements
     */
    public Result<List<T>, List<T>> sort() {
        List<T> result = new ArrayList<>();
        TreeSet<T> withoutIncoming = withoutIncoming();
        while (!withoutIncoming.isEmpty()) {
            T toVertex = withoutIncoming.pollFirst();
            result.add(toVertex);
            for (T fromVertex : outgoing.get(toVertex)) {
                Set<T> fromVertexIncoming = incoming.get(fromVertex);
                fromVertexIncoming.remove(toVertex);
                if (fromVertexIncoming.isEmpty()) {
                    withoutIncoming.add(fromVertex);
                    incoming.remove(fromVertex);
                }
            }
        }
        if (!incoming.isEmpty()) {
            var cyclePath = buildCycle();
            incoming.clear();
            outgoing.clear();
            return Result.failure(cyclePath);
        }
        outgoing.clear();
        return Result.success(result);
    }

    private TreeSet<T> withoutIncoming() {
        var withoutIncoming = new TreeSet<>(Comparator.comparing(this::outgoingEdgesCount)
                                                      .thenComparing(tieBreaker));
        for (var kvp : incoming.entrySet()) {
            if (kvp.getValue().isEmpty()) {
                withoutIncoming.add(kvp.getKey());
            }
        }
        for (var vertexWithoutIncoming : withoutIncoming) {
            incoming.remove(vertexWithoutIncoming);
        }
        return withoutIncoming;
    }

    private int outgoingEdgesCount(T vertex) {
        return outgoing.get(vertex).size();
    }

    private List<T> buildCycle() {
        var result = new ArrayList<T>();
        var visited = new HashSet<T>();
        var oneKvp = incoming.entrySet().iterator().next();
        var vertex = oneKvp.getKey();
        var neighbors = oneKvp.getValue();
        while (true) {
            if (visited.contains(vertex)) {
                result.add(vertex);
                break;
            }
            visited.add(vertex);
            result.add(vertex);
            vertex = neighbors.iterator().next();
            neighbors = incoming.get(vertex);
        }
        // reverse the list to show the actual path leading to a cycle.
        Collections.reverse(result);
        return result;
    }

    /**
     * Returns a new instance to start building the graph and get the result. The tiebreaker used is the the natural order of the
     * class.
     *
     * @param <T> The type of the elements that implement {@link Comparable}.
     * @return A new instance
     */
    public static <T extends Comparable<T>> TopologicalSort<T> of(Class<T> type) {
        return new TopologicalSort<T>(Comparator.naturalOrder());
    }
}
