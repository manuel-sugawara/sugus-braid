package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.AbstractBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureTwo {
    private final Float floatMember;
    private final String stringMember;

    private StructureTwo(Builder builder) {
        this.floatMember = builder.floatMember;
        this.stringMember = builder.stringMember;
    }

    /**
     * 
     * @return The value of the {@code floatMember} member
     */
    public Float floatMember() {
        return this.floatMember;
    }

    /**
     * 
     * @return The value of the {@code stringMember} member
     */
    public String stringMember() {
        return this.stringMember;
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
        StructureTwo that = (StructureTwo) other;
        return Objects.equals(this.floatMember, that.floatMember)
            && Objects.equals(this.stringMember, that.stringMember);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (floatMember != null ? floatMember.hashCode() : 0);
        hashCode = 31 * hashCode + (stringMember != null ? stringMember.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "StructureTwo{"
            + "floatMember: " + floatMember
            + ", stringMember: " + stringMember + "}";
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
     * A class to build instances of StructureTwo
     */
    public static final class Builder {
        private Float floatMember;
        private String stringMember;

        Builder() {
        }

        Builder(StructureTwo data) {
            this.floatMember = data.floatMember;
            this.stringMember = data.stringMember;
        }

        /**
         * Sets the value for {@code floatMember}.
         * 
         * @param floatMember The value to be set.
         * @return This instance for chain calling.
         */
        public Builder floatMember(Float floatMember) {
            this.floatMember = floatMember;
            return this;
        }

        /**
         * Sets the value for {@code stringMember}.
         * 
         * @param stringMember The value to be set.
         * @return This instance for chain calling.
         */
        public Builder stringMember(String stringMember) {
            this.stringMember = stringMember;
            return this;
        }

        /**
         * Returns a new instance of {@link StructureTwo}
         * 
         * @return A new instance of {@link StructureTwo}
         */
        public StructureTwo build() {
            return new StructureTwo(this);
        }
    }

    public static class StructureTwoBuilderReference extends AbstractBuilderReference<StructureTwo, Builder> {

        StructureTwoBuilderReference(StructureTwo source) {
            super(source);
        }

        @Override
        protected Builder emptyTransient() {
            return StructureTwo.builder();
        }

        @Override
        protected StructureTwo transientToPersistent(Builder builder) {
            return builder.build();
        }

        @Override
        protected Builder persistentToTransient(StructureTwo source) {
            return source.toBuilder();
        }

        @Override
        protected Builder clearTransient(Builder builder) {
            return StructureTwo.builder();
        }

        public static StructureTwoBuilderReference from(StructureTwo source) {
            return new StructureTwoBuilderReference(source);
        }
    }
}
