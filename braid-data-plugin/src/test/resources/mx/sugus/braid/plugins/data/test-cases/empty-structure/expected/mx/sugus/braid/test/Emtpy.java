package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class Emtpy {

    private Emtpy(Builder builder) {
    }

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
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
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        Builder() {
        }

        Builder(Emtpy data) {
        }

        /**
         * Returns a new instance of {@link Emtpy}
         * 
         * @return A new instance of {@link Emtpy}
         */
        public Emtpy build() {
            return new Emtpy(this);
        }
    }
}
