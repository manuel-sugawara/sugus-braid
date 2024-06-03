package mx.sugus.braid.rt.util;

public abstract class AbstractBuilderReference<P, T> implements BuilderReference<P, T> {
    protected P asPersistent;
    protected T asTransient;

    protected AbstractBuilderReference(P asPersistent) {
        this.asPersistent = asPersistent;
        this.asTransient = null;
    }

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

    protected P emptyPersistent() {
        return null;
    }

    protected abstract T emptyTransient();

    protected abstract P transientToPersistent(T source);

    protected abstract T persistentToTransient(P source);

    protected abstract T clearTransient(T source);

}
