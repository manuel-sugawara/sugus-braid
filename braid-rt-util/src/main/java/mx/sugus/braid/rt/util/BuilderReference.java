package mx.sugus.braid.rt.util;

/**
 * A builder reference provides efficient state management for data structures that need to transition
 * between mutable (transient) and immutable (persistent) representations.
 * <p>
 * This interface implements a <strong>copy-on-write optimization</strong> pattern specifically designed
 * for code generation pipelines where:
 * <ul>
 *   <li>Configuration objects are frequently created from existing templates</li>
 *   <li>Many builders are created but only some are actually modified</li>
 *   <li>Memory efficiency is important due to large object graphs</li>
 * </ul>
 * 
 * <h3>State Management</h3>
 * <p>Objects managed by BuilderReference exist in one of two states:
 * <ul>
 *   <li><strong>Persistent</strong>: Immutable, shareable, memory-efficient representation</li>
 *   <li><strong>Transient</strong>: Mutable representation optimized for modifications</li>
 * </ul>
 * 
 * <p>Transitions between states are automatic and lazy:
 * <ul>
 *   <li>Accessing transient state from persistent data triggers a copy</li>
 *   <li>Finalizing transient state creates an immutable view</li>
 *   <li>No transitions occur until explicitly requested</li>
 * </ul>
 * 
 * <h3>Thread Safety</h3>
 * <p><strong>Not thread-safe</strong>. BuilderReference instances should not be shared across threads
 * without external synchronization. However, persistent representations returned by
 * {@link #asPersistent()} are typically immutable and thread-safe.
 * 
 * <h3>Example Usage Pattern</h3>
 * <pre>{@code
 * // Code generation scenario: building configuration from template
 * BuilderReference<ImmutableConfig, MutableConfig> configRef = 
 *     BuilderReference.fromPersistent(baseTemplate);
 * 
 * // Conditional modification (common in code generation)
 * if (needsCustomization) {
 *     MutableConfig builder = configRef.asTransient(); // Lazy copy occurs here
 *     builder.addProperty("custom", value);
 *     builder.setFlag(true);
 * }
 * 
 * // Finalize (no additional copy if never modified)
 * ImmutableConfig finalConfig = configRef.asPersistent();
 * }</pre>
 *
 * @param <P> The persistent (immutable) representation of the data structure
 * @param <T> The transient (mutable) representation of the data structure
 * @see CollectionBuilderReference
 */
public interface BuilderReference<P, T> {

    /**
     * Returns the data in its persistent (immutable) form.
     * <p>
     * If the data is currently in transient state, this method will convert it
     * to persistent form and invalidate the transient representation to prevent
     * further mutations from affecting the returned persistent instance.
     * 
     * <p><strong>Performance note</strong>: This operation may trigger a conversion
     * from transient to persistent form, which typically has O(n) cost for collections.
     *
     * @return the persistent representation, never {@code null} (returns empty representation if empty)
     */
    P asPersistent();

    /**
     * Returns the data in its transient (mutable) form.
     * <p>
     * If the data is currently in persistent state, this method will create a mutable
     * copy and invalidate the persistent representation. Subsequent calls to this method
     * will return the same transient instance until {@link #asPersistent()} is called.
     * 
     * <p><strong>Performance note</strong>: The first call may trigger a copy operation
     * from persistent to transient form, which typically has O(n) cost for collections.
     * 
     * <p><strong>Mutation safety</strong>: The returned instance is safe to mutate and
     * will not affect any previously returned persistent representations.
     *
     * @return the transient representation, never {@code null}
     */
    T asTransient();

    /**
     * Clears the reference, removing all content and converting it to an empty state.
     * <p>
     * This method will:
     * <ul>
     *   <li>Clear all content from the current transient representation (if any)</li>
     *   <li>Preserve the transient instance for efficient reuse</li>
     *   <li>Invalidate any persistent representation</li>
     * </ul>
     * 
     * <p>This is more efficient than creating a new BuilderReference when you need
     * to start over with an empty state.
     *
     * @return this instance for method chaining
     */
    BuilderReference<P, T> clear();

    /**
     * Sets the persistent representation directly, invalidating any transient state.
     * <p>
     * This method is useful when you have an existing persistent instance that you
     * want to use as the starting point for this reference. Any existing transient
     * state will be discarded.
     * 
     * <p><strong>Ownership note</strong>: The provided persistent instance should be
     * immutable or effectively immutable to maintain the safety guarantees of this interface.
     *
     * @param persistent the persistent value to be set, may be {@code null} for empty state
     */
    void setPersistent(P persistent);
}
