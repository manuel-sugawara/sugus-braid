package mx.sugus.braid.rt.util;

/**
 * A builder reference is for builders to keep references of classes that can be in two different states, either transient
 * (mutable) or persistent (immutable). The classes are automatically transition between those states as needed and if needed
 * avoiding having to create unnecessary copies but allowing copying on write to allow to mutate the instance.
 *
 * @param <P> The persistent representation of the class
 * @param <T> The transient representation of the class
 */
public interface BuilderReference<P, T> {

    /**
     * Returns the reference as persistent.
     *
     * @return the reference as persistent
     */
    P asPersistent();

    /**
     * Returns the reference as transient.
     *
     * @return the reference as transient.
     */
    T asTransient();

    /**
     * Clears the reference converting it to an "empty" state.
     *
     * @return This instance for method chaining.
     */
    BuilderReference<P, T> clear();

    /**
     * Sets the persistent value.
     *
     * @param persistent the persistent value to be set.
     */
    void setPersistent(P persistent);
}
