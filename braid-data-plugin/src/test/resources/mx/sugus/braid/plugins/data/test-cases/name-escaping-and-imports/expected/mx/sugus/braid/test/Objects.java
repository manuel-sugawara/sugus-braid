package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class Objects {
    private final String object;

    private Objects(Builder builder) {
        this.object = builder.object;
    }

    public String object() {
        return this.object;
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
        Objects that = (Objects) obj;
        // NOTE: cannot import java.util.Objects as it will clash with this class name.
        return java.util.Objects.equals(this.object, that.object);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (object != null ? object.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "Objects{"
            + "object: " + object + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String object;

        Builder() {
        }

        Builder(Objects data) {
            this.object = data.object;
        }

        /**
         * <p>Sets the value for <code>object</code></p>
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }

        public Objects build() {
            return new Objects(this);
        }
    }
}
