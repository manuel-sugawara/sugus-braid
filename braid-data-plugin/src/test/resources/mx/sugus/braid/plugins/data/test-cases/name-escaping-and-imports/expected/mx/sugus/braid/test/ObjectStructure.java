package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class ObjectStructure {
    private final String object;

    private ObjectStructure(Builder builder) {
        this.object = builder.object;
    }

    /**
     * 
     * @return The value of the {@code object} member
     */
    public String object() {
        return this.object;
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
        ObjectStructure that = (ObjectStructure) obj;
        return Objects.equals(this.object, that.object);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (object != null ? object.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "Object{"
            + "object: " + object + "}";
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
     * A class to build instances of ObjectStructure
     */
    public static final class Builder {
        private String object;

        Builder() {
        }

        Builder(ObjectStructure data) {
            this.object = data.object;
        }

        /**
         * Sets the value for {@code object}.
         * 
         * @param object The value to be set.
         * @return This instance for chain calling.
         */
        public Builder object(String object) {
            this.object = object;
            return this;
        }

        /**
         * Returns a new instance of {@link ObjectStructure}
         * 
         * @return A new instance of {@link ObjectStructure}
         */
        public ObjectStructure build() {
            return new ObjectStructure(this);
        }
    }
}
