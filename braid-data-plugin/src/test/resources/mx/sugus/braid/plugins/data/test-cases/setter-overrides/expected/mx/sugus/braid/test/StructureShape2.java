package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * A simple structure shape two
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureShape2 {
    private final String name;
    private final StructureShape1 shapeOne;

    private StructureShape2(Builder builder) {
        this.name = builder.name;
        this.shapeOne = builder.shapeOne;
    }

    /**
     * 
     * @return The value of the {@code name} member
     */
    public String name() {
        return this.name;
    }

    /**
     * 
     * @return The value of the {@code shapeOne} member
     */
    public StructureShape1 shapeOne() {
        return this.shapeOne;
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
        StructureShape2 that = (StructureShape2) obj;
        return Objects.equals(this.name, that.name)
            && Objects.equals(this.shapeOne, that.shapeOne);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (name != null ? name.hashCode() : 0);
        hashCode = 31 * hashCode + (shapeOne != null ? shapeOne.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "StructureShape2{"
            + "name: " + name
            + ", shapeOne: " + shapeOne + "}";
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
        private String name;
        private StructureShape1 shapeOne;

        Builder() {
        }

        Builder(StructureShape2 data) {
            this.name = data.name;
            this.shapeOne = data.shapeOne;
        }

        /**
         * Sets the value for {@code name}.
         * 
         * @param name The value to be set.
         * @return This instance for chain calling.
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the value for {@code shapeOne}.
         * 
         * @param shapeOne The value to be set.
         * @return This instance for chain calling.
         */
        public Builder shapeOne(StructureShape1 shapeOne) {
            this.shapeOne = shapeOne;
            return this;
        }

        public Builder shapeOne(String stringValue, Integer intValue) {
            this.shapeOne = StructureShape1.builder().name(stringValue).intValue(intValue).build();
            return this;
        }

        /**
         * Returns a new instance of {@link StructureShape2}
         * 
         * @return A new instance of {@link StructureShape2}
         */
        public StructureShape2 build() {
            return new StructureShape2(this);
        }
    }
}
