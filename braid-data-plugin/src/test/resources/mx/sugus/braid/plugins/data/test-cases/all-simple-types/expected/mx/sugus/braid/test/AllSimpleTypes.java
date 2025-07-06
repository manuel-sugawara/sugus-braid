package mx.sugus.braid.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class AllSimpleTypes {
    private final Byte aByte;
    private final Short aShort;
    private final Integer anInt;
    private final Long aLong;
    private final BigInteger bigInteger;
    private final Float aFloat;
    private final Double aDouble;
    private final BigDecimal bigDecimal;
    private final String string;
    private final Instant instant;
    private int _hashCode = 0;

    private AllSimpleTypes(Builder builder) {
        this.aByte = builder.aByte;
        this.aShort = builder.aShort;
        this.anInt = builder.anInt;
        this.aLong = builder.aLong;
        this.bigInteger = builder.bigInteger;
        this.aFloat = builder.aFloat;
        this.aDouble = builder.aDouble;
        this.bigDecimal = builder.bigDecimal;
        this.string = builder.string;
        this.instant = builder.instant;
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
     * @return The value of the {@code bigInteger} member
     */
    public BigInteger bigInteger() {
        return this.bigInteger;
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
     * @return The value of the {@code bigDecimal} member
     */
    public BigDecimal bigDecimal() {
        return this.bigDecimal;
    }

    /**
     * 
     * @return The value of the {@code string} member
     */
    public String string() {
        return this.string;
    }

    /**
     * 
     * @return The value of the {@code instant} member
     */
    public Instant instant() {
        return this.instant;
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
        AllSimpleTypes that = (AllSimpleTypes) obj;
        return Objects.equals(this.aByte, that.aByte)
            && Objects.equals(this.aShort, that.aShort)
            && Objects.equals(this.anInt, that.anInt)
            && Objects.equals(this.aLong, that.aLong)
            && Objects.equals(this.bigInteger, that.bigInteger)
            && Objects.equals(this.aFloat, that.aFloat)
            && Objects.equals(this.aDouble, that.aDouble)
            && Objects.equals(this.bigDecimal, that.bigDecimal)
            && Objects.equals(this.string, that.string)
            && Objects.equals(this.instant, that.instant);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + (aByte != null ? aByte.hashCode() : 0);
            hashCode = 31 * hashCode + (aShort != null ? aShort.hashCode() : 0);
            hashCode = 31 * hashCode + (anInt != null ? anInt.hashCode() : 0);
            hashCode = 31 * hashCode + (aLong != null ? aLong.hashCode() : 0);
            hashCode = 31 * hashCode + (bigInteger != null ? bigInteger.hashCode() : 0);
            hashCode = 31 * hashCode + (aFloat != null ? aFloat.hashCode() : 0);
            hashCode = 31 * hashCode + (aDouble != null ? aDouble.hashCode() : 0);
            hashCode = 31 * hashCode + (bigDecimal != null ? bigDecimal.hashCode() : 0);
            hashCode = 31 * hashCode + (string != null ? string.hashCode() : 0);
            hashCode = 31 * hashCode + (instant != null ? instant.hashCode() : 0);
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "AllSimpleTypes{"
            + "byte: " + aByte
            + ", short: " + aShort
            + ", int: " + anInt
            + ", long: " + aLong
            + ", bigInteger: " + bigInteger
            + ", float: " + aFloat
            + ", double: " + aDouble
            + ", bigDecimal: " + bigDecimal
            + ", string: " + string
            + ", instant: " + instant + "}";
    }

    /**
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Byte aByte;
        private Short aShort;
        private Integer anInt;
        private Long aLong;
        private BigInteger bigInteger;
        private Float aFloat;
        private Double aDouble;
        private BigDecimal bigDecimal;
        private String string;
        private Instant instant;

        Builder() {
        }

        Builder(AllSimpleTypes data) {
            this.aByte = data.aByte;
            this.aShort = data.aShort;
            this.anInt = data.anInt;
            this.aLong = data.aLong;
            this.bigInteger = data.bigInteger;
            this.aFloat = data.aFloat;
            this.aDouble = data.aDouble;
            this.bigDecimal = data.bigDecimal;
            this.string = data.string;
            this.instant = data.instant;
        }

        /**
         * Sets the value for {@code aByte}.
         * 
         * @param aByte The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aByte(Byte aByte) {
            this.aByte = aByte;
            return this;
        }

        /**
         * Sets the value for {@code aShort}.
         * 
         * @param aShort The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aShort(Short aShort) {
            this.aShort = aShort;
            return this;
        }

        /**
         * Sets the value for {@code anInt}.
         * 
         * @param anInt The value to be set.
         * @return This instance for chain calling.
         */
        public Builder anInt(Integer anInt) {
            this.anInt = anInt;
            return this;
        }

        /**
         * Sets the value for {@code aLong}.
         * 
         * @param aLong The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aLong(Long aLong) {
            this.aLong = aLong;
            return this;
        }

        /**
         * Sets the value for {@code bigInteger}.
         * 
         * @param bigInteger The value to be set.
         * @return This instance for chain calling.
         */
        public Builder bigInteger(BigInteger bigInteger) {
            this.bigInteger = bigInteger;
            return this;
        }

        /**
         * Sets the value for {@code aFloat}.
         * 
         * @param aFloat The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aFloat(Float aFloat) {
            this.aFloat = aFloat;
            return this;
        }

        /**
         * Sets the value for {@code aDouble}.
         * 
         * @param aDouble The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aDouble(Double aDouble) {
            this.aDouble = aDouble;
            return this;
        }

        /**
         * Sets the value for {@code bigDecimal}.
         * 
         * @param bigDecimal The value to be set.
         * @return This instance for chain calling.
         */
        public Builder bigDecimal(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
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
         * Sets the value for {@code instant}.
         * 
         * @param instant The value to be set.
         * @return This instance for chain calling.
         */
        public Builder instant(Instant instant) {
            this.instant = instant;
            return this;
        }

        /**
         * Returns a new instance of {@link AllSimpleTypes}
         * 
         * @return A new instance of {@link AllSimpleTypes}
         */
        public AllSimpleTypes build() {
            return new AllSimpleTypes(this);
        }
    }
}
