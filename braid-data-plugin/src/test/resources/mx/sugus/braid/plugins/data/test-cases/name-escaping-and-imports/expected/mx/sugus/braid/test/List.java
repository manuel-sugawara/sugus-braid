package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class List {
    private final VoidStructure aVoid;

    private List(Builder builder) {
        this.aVoid = builder.aVoid;
    }

    /**
     * 
     * @return The value of the {@code void} member
     */
    public VoidStructure aVoid() {
        return this.aVoid;
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
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A class to build instances of List
     */
    public static final class Builder {
        private VoidStructure aVoid;

        Builder() {
        }

        Builder(List data) {
            this.aVoid = data.aVoid;
        }

        /**
         * Sets the value for {@code aVoid}.
         * 
         * @param aVoid The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aVoid(VoidStructure aVoid) {
            this.aVoid = aVoid;
            return this;
        }

        /**
         * Returns a new instance of {@link List}
         * 
         * @return A new instance of {@link List}
         */
        public List build() {
            return new List(this);
        }
    }
}
