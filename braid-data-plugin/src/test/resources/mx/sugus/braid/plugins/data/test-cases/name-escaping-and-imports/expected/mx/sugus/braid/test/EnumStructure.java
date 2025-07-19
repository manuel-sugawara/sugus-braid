package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class EnumStructure {
    private final Integer anInt;
    private final String aVoid;

    private EnumStructure(Builder builder) {
        this.anInt = builder.anInt;
        this.aVoid = builder.aVoid;
    }

    /**
     * 
     * @return The value of the {@code int} member
     */
    public Integer anInt() {
        return this.anInt;
    }

    /**
     * 
     * @return The value of the {@code void} member
     */
    public String aVoid() {
        return this.aVoid;
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
        EnumStructure that = (EnumStructure) obj;
        return Objects.equals(this.anInt, that.anInt)
            && Objects.equals(this.aVoid, that.aVoid);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (anInt != null ? anInt.hashCode() : 0);
        hashCode = 31 * hashCode + (aVoid != null ? aVoid.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "Enum{"
            + "int: " + anInt
            + ", void: " + aVoid + "}";
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
     * A class to build instances of EnumStructure
     */
    public static final class Builder {
        private Integer anInt;
        private String aVoid;

        Builder() {
        }

        Builder(EnumStructure data) {
            this.anInt = data.anInt;
            this.aVoid = data.aVoid;
        }

        /**
         * Sets the value for {@code int}.
         * 
         * @param anInt The value to be set.
         * @return This instance for chain calling.
         */
        public Builder anInt(Integer anInt) {
            this.anInt = anInt;
            return this;
        }

        /**
         * Sets the value for {@code void}.
         * 
         * @param aVoid The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aVoid(String aVoid) {
            this.aVoid = aVoid;
            return this;
        }

        /**
         * Returns a new instance of {@link EnumStructure}
         * 
         * @return A new instance of {@link EnumStructure}
         */
        public EnumStructure build() {
            return new EnumStructure(this);
        }
    }
}
