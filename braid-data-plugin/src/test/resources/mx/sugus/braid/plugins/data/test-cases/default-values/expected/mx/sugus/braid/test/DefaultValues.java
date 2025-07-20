package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class DefaultValues {
    private final boolean aBoolean;
    private final byte aByte;
    private final short aShort;
    private final int anInt;
    private final long aLong;
    private final float aFloat;
    private final double aDouble;
    private final String string;
    private int _hashCode = 0;

    private DefaultValues(Builder builder) {
        this.aBoolean = builder.aBoolean;
        this.aByte = builder.aByte;
        this.aShort = builder.aShort;
        this.anInt = builder.anInt;
        this.aLong = builder.aLong;
        this.aFloat = builder.aFloat;
        this.aDouble = builder.aDouble;
        this.string = Objects.requireNonNull(builder.string, "string");
    }

    /**
     * 
     * @return The value of the {@code boolean} member
     */
    public boolean aBoolean() {
        return this.aBoolean;
    }

    /**
     * 
     * @return The value of the {@code byte} member
     */
    public byte aByte() {
        return this.aByte;
    }

    /**
     * 
     * @return The value of the {@code short} member
     */
    public short aShort() {
        return this.aShort;
    }

    /**
     * 
     * @return The value of the {@code int} member
     */
    public int anInt() {
        return this.anInt;
    }

    /**
     * 
     * @return The value of the {@code long} member
     */
    public long aLong() {
        return this.aLong;
    }

    /**
     * 
     * @return The value of the {@code float} member
     */
    public float aFloat() {
        return this.aFloat;
    }

    /**
     * 
     * @return The value of the {@code double} member
     */
    public double aDouble() {
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
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        DefaultValues that = (DefaultValues) other;
        return this.aBoolean == that.aBoolean
            && this.aByte == that.aByte
            && this.aShort == that.aShort
            && this.anInt == that.anInt
            && this.aLong == that.aLong
            && Float.compare(this.aFloat, that.aFloat) == 0
            && Double.compare(this.aDouble, that.aDouble) == 0
            && this.string.equals(that.string);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + Boolean.hashCode(aBoolean);
            hashCode = 31 * hashCode + aByte;
            hashCode = 31 * hashCode + aShort;
            hashCode = 31 * hashCode + anInt;
            hashCode = 31 * hashCode + Long.hashCode(aLong);
            hashCode = 31 * hashCode + Float.hashCode(aFloat);
            hashCode = 31 * hashCode + Double.hashCode(aDouble);
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
        private boolean aBoolean;
        private byte aByte;
        private short aShort;
        private int anInt;
        private long aLong;
        private float aFloat;
        private double aDouble;
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
        public Builder aBoolean(boolean aBoolean) {
            this.aBoolean = aBoolean;
            return this;
        }

        /**
         * Sets the value for {@code byte}.
         * 
         * @param aByte The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aByte(byte aByte) {
            this.aByte = aByte;
            return this;
        }

        /**
         * Sets the value for {@code short}.
         * 
         * @param aShort The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aShort(short aShort) {
            this.aShort = aShort;
            return this;
        }

        /**
         * Sets the value for {@code int}.
         * 
         * @param anInt The value to be set.
         * @return This instance for chain calling.
         */
        public Builder anInt(int anInt) {
            this.anInt = anInt;
            return this;
        }

        /**
         * Sets the value for {@code long}.
         * 
         * @param aLong The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aLong(long aLong) {
            this.aLong = aLong;
            return this;
        }

        /**
         * Sets the value for {@code float}.
         * 
         * @param aFloat The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aFloat(float aFloat) {
            this.aFloat = aFloat;
            return this;
        }

        /**
         * Sets the value for {@code double}.
         * 
         * @param aDouble The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aDouble(double aDouble) {
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
