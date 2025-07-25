package mx.sugus.braid.rt.util;

/**
 * An abstract class that provides the basic logic to convert between transient and persistent state.
 * <p>
 * This class implements a copy-on-write optimization pattern where data structures can exist in two states:
 * <ul>
 *   <li><strong>Persistent</strong>: Immutable, memory-efficient representation suitable for long-term storage</li>
 *   <li><strong>Transient</strong>: Mutable representation optimized for modifications during build operations</li>
 * </ul>
 * <p>
 * The implementation lazily converts between states only when needed, avoiding unnecessary copies
 * when builders are created but never modified - a common pattern in code generation pipelines.
 * 
 * <h3>Performance Characteristics</h3>
 * <ul>
 *   <li><strong>Create builder, no modifications</strong>: O(1) - no copying occurs</li>
 *   <li><strong>First modification</strong>: O(n) - lazy copy from persistent to transient</li>
 *   <li><strong>Finalize to persistent</strong>: O(n) - convert transient to immutable form</li>
 * </ul>
 * 
 * <h3>Example Usage</h3>
 * <pre>{@code
 * // Start with existing data
 * var builderRef = CollectionBuilderReference.fromPersistentOrderedMap(existingMap);
 * 
 * // No copying yet - still sharing the original map
 * if (needsModification) {
 *     // First modification triggers lazy copy
 *     builderRef.asTransient().put("new-key", value);
 * }
 * 
 * // Convert back to immutable form
 * Map<String, Value> result = builderRef.asPersistent();
 * }</pre>
 *
 * @param <P> The persistent (immutable) representation of the data structure
 * @param <T> The transient (mutable) representation of the data structure
 */
public abstract class AbstractBuilderReference<P, T> implements BuilderReference<P, T> {
    protected P asPersistent;
    protected T asTransient;

    /**
     * Creates a new reference builder with the given persistent instance.
     *
     * @param asPersistent The persistent instance.
     */
    protected AbstractBuilderReference(P asPersistent) {
        this.asPersistent = asPersistent;
        this.asTransient = null;
    }

    /**
     * Creates a new empty reference builder.
     */
    protected AbstractBuilderReference() {
        this.asPersistent = null;
        this.asTransient = null;
    }

    @Override
    public P asPersistent() {
        if (asPersistent == null) {
            if (asTransient == null) {
                return emptyPersistent();
            }
            asPersistent = transientToPersistent(asTransient);
            asTransient = null;
        }
        return asPersistent;
    }

    @Override
    public T asTransient() {
        if (asTransient == null) {
            if (asPersistent == null) {
                asTransient = emptyTransient();
                return asTransient;
            }
            asTransient = persistentToTransient(asPersistent);
            asPersistent = null;
        }
        return asTransient;
    }

    @Override
    public BuilderReference<P, T> clear() {
        var tmp = asTransient();
        if (tmp != null) {
            asTransient = clearTransient(tmp);
        }
        return this;
    }

    @Override
    public void setPersistent(P persistent) {
        this.asPersistent = persistent;
        this.asTransient = null; // Invalidate transient state
    }


    /**
     * Returns an empty representation of the persistent state.
     * <p>
     * By default returns {@code null}, but should be overridden to return
     * an appropriate empty immutable instance (e.g., {@code Collections.emptyMap()}
     * for maps) when a more meaningful empty value exists.
     * 
     * @return An empty representation of the persistent state, or {@code null} if no meaningful empty state exists
     */
    protected P emptyPersistent() {
        return null;
    }

    /**
     * Returns an empty instance of the transient type.
     * <p>
     * This should return a new, empty mutable instance that can be used
     * for building. For example, {@code new HashMap<>()} for maps or
     * {@code new ArrayList<>()} for lists.
     *
     * @return A new empty instance of the transient type, never {@code null}
     */
    protected abstract T emptyTransient();

    /**
     * Converts the transient instance to a persistent (immutable) one.
     * <p>
     * This method should create an immutable view or copy of the transient data.
     * For collections, this typically means wrapping with {@code Collections.unmodifiableXxx()}
     * or using {@code Map.copyOf()}, {@code List.copyOf()}, etc.
     * 
     * @param source The transient instance to convert, never {@code null}
     * @return The converted persistent instance, never {@code null}
     */
    protected abstract P transientToPersistent(T source);

    /**
     * Converts the persistent instance to a transient (mutable) one.
     * <p>
     * This method should create a mutable copy of the persistent data that can
     * be safely modified. For collections, this typically means creating a new
     * mutable collection and copying all elements.
     * 
     * @param source The persistent instance to convert, never {@code null}
     * @return The converted transient instance, never {@code null}
     */
    protected abstract T persistentToTransient(P source);

    /**
     * Clears the given transient instance, removing all content while preserving the instance.
     * <p>
     * The semantics of clearing depends on the data type:
     * <ul>
     *   <li><strong>Collections</strong>: Remove all elements using {@code clear()}</li>
     *   <li><strong>Builders</strong>: Reset to initial empty state</li>
     *   <li><strong>Other types</strong>: Implementation-specific reset behavior</li>
     * </ul>
     * <p>
     * This method should mutate the existing instance rather than creating a new one,
     * allowing the same object reference to be reused.
     *
     * @param source The transient instance to clear, never {@code null}
     * @return The same cleared instance (for method chaining), never {@code null}
     */
    protected abstract T clearTransient(T source);

}
