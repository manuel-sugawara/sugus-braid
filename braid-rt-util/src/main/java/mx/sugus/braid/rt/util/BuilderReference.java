package mx.sugus.braid.rt.util;

public interface BuilderReference<P, T> {

    P asPersistent();

    T asTransient();

    BuilderReference<P, T> clear();

    void setPersistent(P persistent);
}
