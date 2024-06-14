package mx.sugus.braid.rt.util;

/**
 * An abstract class that provides the basic logic to convert between transient and persistent state.
 *
 * @param <P> The persistent representation of the class
 * @param <T> The transient representation of the class
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
    }


    /**
     * Returns an empty representation of the persistent state. By default {@code null}, but can be overridden if a more
     * meaningful value exists for the persistent type.
     *
     * @return An empty representation of the persistent state
     */
    protected P emptyPersistent() {
        return null;
    }

    /**
     * Returns an empty instance of the transient type.
     *
     * @return An empty instance of the transient type.
     */
    protected abstract T emptyTransient();

    /**
     * Converts the transient instance to a persistent one.
     *
     * @param source The transient instance to convert
     * @return The converted persistent instance.
     */
    protected abstract P transientToPersistent(T source);

    /**
     * Converts the persistent instance to a transient one.
     *
     * @param source The persistent instance to convert.
     * @return The converted transient instance.
     */
    protected abstract T persistentToTransient(P source);

    /**
     * Clears the given transient instance. The semantics of clearing depends on the class being cleared, for collections this
     * means removing all the element present on it, for non-collections this might just drop the original and create a new
     * builder.
     *
     * @param source The transient instance to clear
     * @return The cleared instance
     */
    protected abstract T clearTransient(T source);

}
