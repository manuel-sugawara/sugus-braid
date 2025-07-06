package mx.sugus.braid.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.NoSuchElementException;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * A union of all simple types.
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
@SuppressWarnings("unchecked")
public final class AnySimpleType {
    private final Object variantValue;
    private final VariantTag variantTag;

    private AnySimpleType(Builder builder) {
        this.variantValue = builder.getValue();
        this.variantTag = builder.variantTag;
    }

    /**
     * byte variant
     */
    public Byte aByte() {
        if (this.variantTag == VariantTag.BYTE) {
            return (Byte) this.variantValue;
        }
        throw new NoSuchElementException("Union element `byte` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * short variant
     */
    public Short aShort() {
        if (this.variantTag == VariantTag.SHORT) {
            return (Short) this.variantValue;
        }
        throw new NoSuchElementException("Union element `short` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * int variant
     */
    public Integer anInt() {
        if (this.variantTag == VariantTag.INT) {
            return (Integer) this.variantValue;
        }
        throw new NoSuchElementException("Union element `int` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * long variant
     */
    public Long aLong() {
        if (this.variantTag == VariantTag.LONG) {
            return (Long) this.variantValue;
        }
        throw new NoSuchElementException("Union element `long` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * bigInteger variant
     */
    public BigInteger bigInteger() {
        if (this.variantTag == VariantTag.BIG_INTEGER) {
            return (BigInteger) this.variantValue;
        }
        throw new NoSuchElementException("Union element `bigInteger` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * float variant
     */
    public Float aFloat() {
        if (this.variantTag == VariantTag.FLOAT) {
            return (Float) this.variantValue;
        }
        throw new NoSuchElementException("Union element `float` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * double variant
     */
    public Double aDouble() {
        if (this.variantTag == VariantTag.DOUBLE) {
            return (Double) this.variantValue;
        }
        throw new NoSuchElementException("Union element `double` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * bigDecimal variant
     */
    public BigDecimal bigDecimal() {
        if (this.variantTag == VariantTag.BIG_DECIMAL) {
            return (BigDecimal) this.variantValue;
        }
        throw new NoSuchElementException("Union element `bigDecimal` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * string variant
     */
    public String string() {
        if (this.variantTag == VariantTag.STRING) {
            return (String) this.variantValue;
        }
        throw new NoSuchElementException("Union element `string` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * instant variant
     */
    public Instant instant() {
        if (this.variantTag == VariantTag.INSTANT) {
            return (Instant) this.variantValue;
        }
        throw new NoSuchElementException("Union element `instant` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * Returns the enum value representing which member of this object is populated.
     * <p>
     * This will be {@link Type#UNKNOWN_TO_VERSION} if no members are set.
     * 
     * @return The enum value representing which member of this object is populated
     */
    public VariantTag variantTag() {
        return this.variantTag;
    }

    /**
     * Returns the untyped value of the union.
     * <p>
     * Use {@link #type()} to get the member currently set.
     * 
     * @return The untyped value of the union.
     */
    public Object variantValue() {
        return this.variantValue;
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
        if (!(other instanceof AnySimpleType)) {
            return false;
        }
        AnySimpleType that = (AnySimpleType) other;
        return this.variantTag == that.variantTag && this.variantValue.equals(that.variantValue);
    }

    @Override
    public int hashCode() {
        return this.variantTag.hashCode() + 31 * this.variantValue.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("AnySimpleType{variantTag: ");
        buf.append(this.variantTag);
        switch (this.variantTag) {
            case BYTE:
                buf.append(", byte: ").append(this.variantValue);
                break;
            case SHORT:
                buf.append(", short: ").append(this.variantValue);
                break;
            case INT:
                buf.append(", int: ").append(this.variantValue);
                break;
            case LONG:
                buf.append(", long: ").append(this.variantValue);
                break;
            case BIG_INTEGER:
                buf.append(", bigInteger: ").append(this.variantValue);
                break;
            case FLOAT:
                buf.append(", float: ").append(this.variantValue);
                break;
            case DOUBLE:
                buf.append(", double: ").append(this.variantValue);
                break;
            case BIG_DECIMAL:
                buf.append(", bigDecimal: ").append(this.variantValue);
                break;
            case STRING:
                buf.append(", string: ").append(this.variantValue);
                break;
            case INSTANT:
                buf.append(", instant: ").append(this.variantValue);
                break;
        }
        return buf.append("}").toString();
    }

    /**
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    public enum VariantTag {
        BYTE("byte"),
        SHORT("short"),
        INT("int"),
        LONG("long"),
        BIG_INTEGER("bigInteger"),
        FLOAT("float"),
        DOUBLE("double"),
        BIG_DECIMAL("bigDecimal"),
        STRING("string"),
        INSTANT("instant"),
        UNKNOWN_TO_VERSION(null);

        private final String value;

        VariantTag(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static final class Builder {
        private Object variantValue;
        private VariantTag variantTag;

        Builder() {
            this.variantTag = null;
            this.variantValue = VariantTag.UNKNOWN_TO_VERSION;
        }

        Builder(AnySimpleType data) {
            this.variantTag = data.variantTag;
            this.variantValue = data.variantValue;
        }

        /**
         * Sets the value for {@code aByte}
         * <p>
         * byte variant
         */
        public Builder aByte(Byte aByte) {
            this.variantTag = VariantTag.BYTE;
            this.variantValue = aByte;
            return this;
        }

        /**
         * Sets the value for {@code aShort}
         * <p>
         * short variant
         */
        public Builder aShort(Short aShort) {
            this.variantTag = VariantTag.SHORT;
            this.variantValue = aShort;
            return this;
        }

        /**
         * Sets the value for {@code anInt}
         * <p>
         * int variant
         */
        public Builder anInt(Integer anInt) {
            this.variantTag = VariantTag.INT;
            this.variantValue = anInt;
            return this;
        }

        /**
         * Sets the value for {@code aLong}
         * <p>
         * long variant
         */
        public Builder aLong(Long aLong) {
            this.variantTag = VariantTag.LONG;
            this.variantValue = aLong;
            return this;
        }

        /**
         * Sets the value for {@code bigInteger}
         * <p>
         * bigInteger variant
         */
        public Builder bigInteger(BigInteger bigInteger) {
            this.variantTag = VariantTag.BIG_INTEGER;
            this.variantValue = bigInteger;
            return this;
        }

        /**
         * Sets the value for {@code aFloat}
         * <p>
         * float variant
         */
        public Builder aFloat(Float aFloat) {
            this.variantTag = VariantTag.FLOAT;
            this.variantValue = aFloat;
            return this;
        }

        /**
         * Sets the value for {@code aDouble}
         * <p>
         * double variant
         */
        public Builder aDouble(Double aDouble) {
            this.variantTag = VariantTag.DOUBLE;
            this.variantValue = aDouble;
            return this;
        }

        /**
         * Sets the value for {@code bigDecimal}
         * <p>
         * bigDecimal variant
         */
        public Builder bigDecimal(BigDecimal bigDecimal) {
            this.variantTag = VariantTag.BIG_DECIMAL;
            this.variantValue = bigDecimal;
            return this;
        }

        /**
         * Sets the value for {@code string}
         * <p>
         * string variant
         */
        public Builder string(String string) {
            this.variantTag = VariantTag.STRING;
            this.variantValue = string;
            return this;
        }

        /**
         * Sets the value for {@code instant}
         * <p>
         * instant variant
         */
        public Builder instant(Instant instant) {
            this.variantTag = VariantTag.INSTANT;
            this.variantValue = instant;
            return this;
        }

        Object getValue() {
            return this.variantValue;
        }

        public AnySimpleType build() {
            return new AnySimpleType(this);
        }
    }
}
