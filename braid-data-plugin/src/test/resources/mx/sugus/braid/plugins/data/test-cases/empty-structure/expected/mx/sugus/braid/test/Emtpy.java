package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class Emtpy {

    private Emtpy(Builder builder) {
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Emtpy that = (Emtpy) obj;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        return hashCode;
    }

    @Override
    public String toString() {
        return "Emtpy{" + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        Builder() {
        }

        Builder(Emtpy data) {
        }

        public Emtpy build() {
            return new Emtpy(this);
        }
    }
}
