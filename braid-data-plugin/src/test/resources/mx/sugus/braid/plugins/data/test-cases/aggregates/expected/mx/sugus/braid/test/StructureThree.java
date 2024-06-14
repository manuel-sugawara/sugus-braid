package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureThree {
    private final StructureOne structureOne;

    private StructureThree(Builder builder) {
        this.structureOne = builder.structureOne;
    }

    public StructureOne structureOne() {
        return this.structureOne;
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
        StructureThree that = (StructureThree) obj;
        return Objects.equals(this.structureOne, that.structureOne);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (structureOne != null ? structureOne.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "StructureThree{"
            + "structureOne: " + structureOne + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private StructureOne structureOne;

        Builder() {
        }

        Builder(StructureThree data) {
            this.structureOne = data.structureOne;
        }

        /**
         * <p>Sets the value for <code>structureOne</code></p>
         */
        public Builder structureOne(StructureOne structureOne) {
            this.structureOne = structureOne;
            return this;
        }

        public StructureThree build() {
            return new StructureThree(this);
        }
    }
}
