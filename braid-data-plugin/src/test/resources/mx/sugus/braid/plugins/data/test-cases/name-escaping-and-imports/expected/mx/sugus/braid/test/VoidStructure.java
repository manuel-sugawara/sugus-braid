package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class VoidStructure {
    private final EnumStructure anEnum;

    private VoidStructure(Builder builder) {
        this.anEnum = builder.anEnum;
    }

    /**
     * 
     * @return The value of the {@code enum} member
     */
    public EnumStructure anEnum() {
        return this.anEnum;
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
        VoidStructure that = (VoidStructure) other;
        return Objects.equals(this.anEnum, that.anEnum);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (anEnum != null ? anEnum.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "Void{"
            + "enum: " + anEnum + "}";
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
     * A class to build instances of VoidStructure
     */
    public static final class Builder {
        private EnumStructure anEnum;

        Builder() {
        }

        Builder(VoidStructure data) {
            this.anEnum = data.anEnum;
        }

        /**
         * Sets the value for {@code enum}.
         * 
         * @param anEnum The value to be set.
         * @return This instance for chain calling.
         */
        public Builder anEnum(EnumStructure anEnum) {
            this.anEnum = anEnum;
            return this;
        }

        /**
         * Returns a new instance of {@link VoidStructure}
         * 
         * @return A new instance of {@link VoidStructure}
         */
        public VoidStructure build() {
            return new VoidStructure(this);
        }
    }
}
