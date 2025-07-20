package mx.sugus.braid.test;

import java.util.List;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * A simple structure shape two
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureShape2 {
    private final String name;
    private final List<StructureShape1> shapeOnes;

    private StructureShape2(Builder builder) {
        this.name = builder.name;
        this.shapeOnes = Objects.requireNonNull(builder.shapeOnes.asPersistent(), "shapeOnes");
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
     * @return The value of the {@code shapeOnes} member
     */
    public List<StructureShape1> shapeOnes() {
        return this.shapeOnes;
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
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        StructureShape2 that = (StructureShape2) other;
        return Objects.equals(this.name, that.name)
            && this.shapeOnes.equals(that.shapeOnes);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (name != null ? name.hashCode() : 0);
        hashCode = 31 * hashCode + shapeOnes.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "StructureShape2{"
            + "name: " + name
            + ", shapeOnes: " + shapeOnes + "}";
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
     * A class to build instances of StructureShape2
     */
    public static final class Builder {
        private String name;
        private CollectionBuilderReference<List<StructureShape1>> shapeOnes;

        Builder() {
            this.shapeOnes = CollectionBuilderReference.forList();
        }

        Builder(StructureShape2 data) {
            this.name = data.name;
            this.shapeOnes = CollectionBuilderReference.fromPersistentList(data.shapeOnes);
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
         * Sets the value for {@code shapeOnes}.
         * 
         * @param shapeOnes The value to be set.
         * @return This instance for chain calling.
         */
        public Builder shapeOnes(List<StructureShape1> shapeOnes) {
            this.shapeOnes.clear();
            this.shapeOnes.asTransient().addAll(shapeOnes);
            return this;
        }

        /**
         * Adds a value to {@code shapeOnes}.
         * 
         * @param shapeOnes The value tp add
         * @return This instance for chain calling.
         */
        public Builder addShapeOne(StructureShape1 shapeOne) {
            this.shapeOnes.asTransient().add(shapeOne);
            return this;
        }

        /**
         * Adds to {@code shapeOnes} building the value using the given arguments
         */
        public Builder addFromAdderOverride(String value) {
            this.shapeOnes.asTransient().add(StructureShape1.builder().name(value).build());
            return this;
        }

        /**
         * Adds to {@code shapeOnes} building the value using the given arguments
         */
        public Builder addFromAdderOverride(String value, Integer intValue) {
            this.shapeOnes.asTransient().add(StructureShape1.builder().name(value).intValue(intValue).build());
            return this;
        }

        /**
         * Creates a new structure with the given value.
         */
        public Builder addShapeOne(String stringValue, Integer intValue) {
            this.shapeOnes.asTransient().add(StructureShape1.from(stringValue, intValue));
            return this;
        }

        /**
         * Adds the values
         */
        public Builder addsAllFromStructure2(StructureShape2 value) {
            this.shapeOnes.asTransient().addAll(value.shapeOnes());
            return this;
        }

        /**
         * Adds the given values to {@code shapeOnes}
         */
        public Builder addShapeOnes(StructureShape1 value1, StructureShape1 value2) {
            this.shapeOnes.asTransient().add(value1);
            this.shapeOnes.asTransient().add(value2);
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
