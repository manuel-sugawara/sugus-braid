package mx.sugus.braid.test;

import java.util.List;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>A simple structure shape two</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureShape2 {
    private final String name;
    private final List<StructureShape1> shapeOnes;

    private StructureShape2(Builder builder) {
        this.name = builder.name;
        this.shapeOnes = Objects.requireNonNull(builder.shapeOnes.asPersistent(), "shapeOnes");
    }

    public String name() {
        return this.name;
    }

    public List<StructureShape1> shapeOnes() {
        return this.shapeOnes;
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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

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
         * <p>Sets the value for <code>name</code></p>
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * <p>Sets the value for <code>shapeOnes</code></p>
         */
        public Builder shapeOnes(List<StructureShape1> shapeOnes) {
            this.shapeOnes.clear();
            this.shapeOnes.asTransient().addAll(shapeOnes);
            return this;
        }

        /**
         * <p>Adds a single value for <code>shapeOnes</code></p>
         */
        public Builder addShapeOne(StructureShape1 shapeOne) {
            this.shapeOnes.asTransient().add(shapeOne);
            return this;
        }

        /**
         * <p>Adds to <code>shapeOnes</code> building the value using the given arguments</p>
         */
        public Builder addFromAdderOverride(String value) {
            this.shapeOnes.asTransient().add(StructureShape1.builder().name(value).build());
            return this;
        }

        /**
         * <p>Adds to <code>shapeOnes</code> building the value using the given arguments</p>
         */
        public Builder addFromAdderOverride(String value, Integer intValue) {
            this.shapeOnes.asTransient().add(StructureShape1.builder().name(value).intValue(intValue).build());
            return this;
        }

        /**
         * <p>Creates a new structure with the given value.</p>
         */
        public Builder addShapeOne(String stringValue, Integer intValue) {
            this.shapeOnes.asTransient().add(StructureShape1.from(stringValue, intValue));
            return this;
        }

        /**
         * <p>Adds the values</p>
         */
        public Builder addsAllFromStructure2(StructureShape2 value) {
            this.shapeOnes.asTransient().addAll(value.shapeOnes());
            return this;
        }

        /**
         * <p>Adds the given values to <code>shapeOnes</code></p>
         */
        public Builder addShapeOnes(StructureShape1 value1, StructureShape1 value2) {
            this.shapeOnes.asTransient().add(value1);
            this.shapeOnes.asTransient().add(value2);
            return this;
        }

        public StructureShape2 build() {
            return new StructureShape2(this);
        }
    }
}
