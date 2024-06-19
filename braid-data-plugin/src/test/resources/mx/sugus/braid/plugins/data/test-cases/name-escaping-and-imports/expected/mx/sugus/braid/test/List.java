package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class List {
    private final VoidStructure aVoid;

    private List(Builder builder) {
        this.aVoid = builder.aVoid;
    }

    public VoidStructure aVoid() {
        return this.aVoid;
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
        List that = (List) obj;
        return Objects.equals(this.aVoid, that.aVoid);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (aVoid != null ? aVoid.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "List{"
            + "void: " + aVoid + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private VoidStructure aVoid;

        Builder() {
        }

        Builder(List data) {
            this.aVoid = data.aVoid;
        }

        /**
         * <p>Sets the value for <code>aVoid</code></p>
         */
        public Builder aVoid(VoidStructure aVoid) {
            this.aVoid = aVoid;
            return this;
        }

        public List build() {
            return new List(this);
        }
    }
}
