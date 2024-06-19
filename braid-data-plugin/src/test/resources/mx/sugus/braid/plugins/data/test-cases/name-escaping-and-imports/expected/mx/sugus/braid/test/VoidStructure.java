package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class VoidStructure {
    private final EnumStructure anEnum;

    private VoidStructure(Builder builder) {
        this.anEnum = builder.anEnum;
    }

    public EnumStructure anEnum() {
        return this.anEnum;
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
        VoidStructure that = (VoidStructure) obj;
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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private EnumStructure anEnum;

        Builder() {
        }

        Builder(VoidStructure data) {
            this.anEnum = data.anEnum;
        }

        /**
         * <p>Sets the value for <code>anEnum</code></p>
         */
        public Builder anEnum(EnumStructure anEnum) {
            this.anEnum = anEnum;
            return this;
        }

        public VoidStructure build() {
            return new VoidStructure(this);
        }
    }
}
