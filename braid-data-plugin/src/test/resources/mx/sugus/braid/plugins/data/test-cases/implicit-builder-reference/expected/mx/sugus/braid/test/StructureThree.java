package mx.sugus.braid.test;

import java.util.Objects;
import java.util.function.Consumer;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureThree {
    private final StructureOne structureOne;
    private final StructureTwo structureTwo;

    private StructureThree(Builder builder) {
        this.structureOne = builder.structureOne;
        this.structureTwo = builder.structureTwo.asPersistent();
    }

    /**
     * 
     * @return The value of the {@code structureOne} member
     */
    public StructureOne structureOne() {
        return this.structureOne;
    }

    /**
     * 
     * @return The value of the {@code structureTwo} member
     */
    public StructureTwo structureTwo() {
        return this.structureTwo;
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
        StructureThree that = (StructureThree) obj;
        return Objects.equals(this.structureOne, that.structureOne)
            && Objects.equals(this.structureTwo, that.structureTwo);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (structureOne != null ? structureOne.hashCode() : 0);
        hashCode = 31 * hashCode + (structureTwo != null ? structureTwo.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "StructureThree{"
            + "structureOne: " + structureOne
            + ", structureTwo: " + structureTwo + "}";
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
     * A class to build instances of StructureThree
     */
    public static final class Builder {
        private StructureOne structureOne;
        private BuilderReference<StructureTwo, StructureTwo.Builder> structureTwo;

        Builder() {
            this.structureTwo = StructureTwo.StructureTwoBuilderReference.from(null);
        }

        Builder(StructureThree data) {
            this.structureOne = data.structureOne;
            this.structureTwo = StructureTwo.StructureTwoBuilderReference.from(data.structureTwo);
        }

        /**
         * Sets the value for {@code structureOne}.
         * 
         * @param structureOne The value to be set.
         * @return This instance for chain calling.
         */
        public Builder structureOne(StructureOne structureOne) {
            this.structureOne = structureOne;
            return this;
        }

        public Builder structureTwo(Consumer<StructureTwo.Builder> mutator) {
            mutator.accept(this.structureTwo.asTransient());
            return this;
        }

        /**
         * Sets the value for {@code structureTwo}.
         * 
         * @param structureTwo The value to be set.
         * @return This instance for chain calling.
         */
        public Builder structureTwo(StructureTwo structureTwo) {
            this.structureTwo.setPersistent(structureTwo);
            return this;
        }

        /**
         * Returns a new instance of {@link StructureThree}
         * 
         * @return A new instance of {@link StructureThree}
         */
        public StructureThree build() {
            return new StructureThree(this);
        }
    }
}
