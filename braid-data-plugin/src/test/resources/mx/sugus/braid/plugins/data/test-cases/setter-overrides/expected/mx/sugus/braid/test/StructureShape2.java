package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>A simple structure shape two</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureShape2 {
    private final String name;
    private final StructureShape1 shapeOne;

    private StructureShape2(Builder builder) {
        this.name = builder.name;
        this.shapeOne = builder.shapeOne;
    }

    public String name() {
        return this.name;
    }

    public StructureShape1 shapeOne() {
        return this.shapeOne;
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
     * <p>Creates a new builder</p>
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
         * <p>Sets the value for <code>name</code></p>
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * <p>Sets the value for <code>shapeOne</code></p>
         */
        public Builder shapeOne(StructureShape1 shapeOne) {
            this.shapeOne = shapeOne;
            return this;
        }

        public Builder shapeOne(String stringValue, Integer intValue) {
            this.shapeOne = StructureShape1.builder().name(stringValue).intValue(intValue).build();
            return this;
        }

        public StructureShape2 build() {
            return new StructureShape2(this);
        }
    }
}
