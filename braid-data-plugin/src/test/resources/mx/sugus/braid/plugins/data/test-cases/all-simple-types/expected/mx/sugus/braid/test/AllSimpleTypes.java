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

    public Byte aByte() {
        return this.aByte;
    }

    public Short aShort() {
        return this.aShort;
    }

    public Integer anInt() {
        return this.anInt;
    }

    public Long aLong() {
        return this.aLong;
    }

    public BigInteger bigInteger() {
        return this.bigInteger;
    }

    public Float aFloat() {
        return this.aFloat;
    }

    public Double aDouble() {
        return this.aDouble;
    }

    public BigDecimal bigDecimal() {
        return this.bigDecimal;
    }

    public String string() {
        return this.string;
    }

    public Instant instant() {
        return this.instant;
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
     * <p>Creates a new builder</p>
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
         * <p>Sets the value for <code>aByte</code></p>
         */
        public Builder aByte(Byte aByte) {
            this.aByte = aByte;
            return this;
        }

        /**
         * <p>Sets the value for <code>aShort</code></p>
         */
        public Builder aShort(Short aShort) {
            this.aShort = aShort;
            return this;
        }

        /**
         * <p>Sets the value for <code>anInt</code></p>
         */
        public Builder anInt(Integer anInt) {
            this.anInt = anInt;
            return this;
        }

        /**
         * <p>Sets the value for <code>aLong</code></p>
         */
        public Builder aLong(Long aLong) {
            this.aLong = aLong;
            return this;
        }

        /**
         * <p>Sets the value for <code>bigInteger</code></p>
         */
        public Builder bigInteger(BigInteger bigInteger) {
            this.bigInteger = bigInteger;
            return this;
        }

        /**
         * <p>Sets the value for <code>aFloat</code></p>
         */
        public Builder aFloat(Float aFloat) {
            this.aFloat = aFloat;
            return this;
        }

        /**
         * <p>Sets the value for <code>aDouble</code></p>
         */
        public Builder aDouble(Double aDouble) {
            this.aDouble = aDouble;
            return this;
        }

        /**
         * <p>Sets the value for <code>bigDecimal</code></p>
         */
        public Builder bigDecimal(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
            return this;
        }

        /**
         * <p>Sets the value for <code>string</code></p>
         */
        public Builder string(String string) {
            this.string = string;
            return this;
        }

        /**
         * <p>Sets the value for <code>instant</code></p>
         */
        public Builder instant(Instant instant) {
            this.instant = instant;
            return this;
        }

        public AllSimpleTypes build() {
            return new AllSimpleTypes(this);
        }
    }
}
