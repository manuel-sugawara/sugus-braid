package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class DefaultValues {
    private final Boolean aBoolean;
    private final Byte aByte;
    private final Short aShort;
    private final Integer anInt;
    private final Long aLong;
    private final Float aFloat;
    private final Double aDouble;
    private final String string;
    private int _hashCode = 0;

    private DefaultValues(Builder builder) {
        this.aBoolean = Objects.requireNonNull(builder.aBoolean, "aBoolean");
        this.aByte = Objects.requireNonNull(builder.aByte, "aByte");
        this.aShort = Objects.requireNonNull(builder.aShort, "aShort");
        this.anInt = Objects.requireNonNull(builder.anInt, "anInt");
        this.aLong = Objects.requireNonNull(builder.aLong, "aLong");
        this.aFloat = Objects.requireNonNull(builder.aFloat, "aFloat");
        this.aDouble = Objects.requireNonNull(builder.aDouble, "aDouble");
        this.string = Objects.requireNonNull(builder.string, "string");
    }

    /**
     * 
     * @return The value of the {@code boolean} member
     */
    public Boolean aBoolean() {
        return this.aBoolean;
    }

    /**
     * 
     * @return The value of the {@code byte} member
     */
    public Byte aByte() {
        return this.aByte;
    }

    /**
     * 
     * @return The value of the {@code short} member
     */
    public Short aShort() {
        return this.aShort;
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
     * @return The value of the {@code long} member
     */
    public Long aLong() {
        return this.aLong;
    }

    /**
     * 
     * @return The value of the {@code float} member
     */
    public Float aFloat() {
        return this.aFloat;
    }

    /**
     * 
     * @return The value of the {@code double} member
     */
    public Double aDouble() {
        return this.aDouble;
    }

    /**
     * 
     * @return The value of the {@code string} member
     */
    public String string() {
        return this.string;
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
        DefaultValues that = (DefaultValues) obj;
        return this.aBoolean.equals(that.aBoolean)
            && this.aByte.equals(that.aByte)
            && this.aShort.equals(that.aShort)
            && this.anInt.equals(that.anInt)
            && this.aLong.equals(that.aLong)
            && this.aFloat.equals(that.aFloat)
            && this.aDouble.equals(that.aDouble)
            && this.string.equals(that.string);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + aBoolean.hashCode();
            hashCode = 31 * hashCode + aByte.hashCode();
            hashCode = 31 * hashCode + aShort.hashCode();
            hashCode = 31 * hashCode + anInt.hashCode();
            hashCode = 31 * hashCode + aLong.hashCode();
            hashCode = 31 * hashCode + aFloat.hashCode();
            hashCode = 31 * hashCode + aDouble.hashCode();
            hashCode = 31 * hashCode + string.hashCode();
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "DefaultValues{"
            + "boolean: " + aBoolean
            + ", byte: " + aByte
            + ", short: " + aShort
            + ", int: " + anInt
            + ", long: " + aLong
            + ", float: " + aFloat
            + ", double: " + aDouble
            + ", string: " + string + "}";
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
     * A class to build instances of DefaultValues
     */
    public static final class Builder {
        private Boolean aBoolean;
        private Byte aByte;
        private Short aShort;
        private Integer anInt;
        private Long aLong;
        private Float aFloat;
        private Double aDouble;
        private String string;

        Builder() {
            this.aBoolean = true;
            this.aByte = 1;
            this.aShort = 2;
            this.anInt = 3;
            this.aLong = 21474836470L;
            this.aFloat = 3.14159F;
            this.aDouble = 2.71828D;
            this.string = "Hello";
        }

        Builder(DefaultValues data) {
            this.aBoolean = data.aBoolean;
            this.aByte = data.aByte;
            this.aShort = data.aShort;
            this.anInt = data.anInt;
            this.aLong = data.aLong;
            this.aFloat = data.aFloat;
            this.aDouble = data.aDouble;
            this.string = data.string;
        }

        /**
         * Sets the value for {@code boolean}.
         * 
         * @param aBoolean The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aBoolean(Boolean aBoolean) {
            this.aBoolean = aBoolean;
            return this;
        }

        /**
         * Sets the value for {@code byte}.
         * 
         * @param aByte The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aByte(Byte aByte) {
            this.aByte = aByte;
            return this;
        }

        /**
         * Sets the value for {@code short}.
         * 
         * @param aShort The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aShort(Short aShort) {
            this.aShort = aShort;
            return this;
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
         * Sets the value for {@code long}.
         * 
         * @param aLong The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aLong(Long aLong) {
            this.aLong = aLong;
            return this;
        }

        /**
         * Sets the value for {@code float}.
         * 
         * @param aFloat The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aFloat(Float aFloat) {
            this.aFloat = aFloat;
            return this;
        }

        /**
         * Sets the value for {@code double}.
         * 
         * @param aDouble The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aDouble(Double aDouble) {
            this.aDouble = aDouble;
            return this;
        }

        /**
         * Sets the value for {@code string}.
         * 
         * @param string The value to be set.
         * @return This instance for chain calling.
         */
        public Builder string(String string) {
            this.string = string;
            return this;
        }

        /**
         * Returns a new instance of {@link DefaultValues}
         * 
         * @return A new instance of {@link DefaultValues}
         */
        public DefaultValues build() {
            return new DefaultValues(this);
        }
    }
}
