package mx.sugus.braid.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.NoSuchElementException;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>A union of all simple types, but int is sensitive</p>
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
     * <p>byte variant</p>
     */
    public Byte aByte() {
        if (this.variantTag == VariantTag.BYTE) {
            return (Byte) this.variantValue;
        }
        throw new NoSuchElementException("Union element `byte` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * <p>short variant</p>
     */
    public Short aShort() {
        if (this.variantTag == VariantTag.SHORT) {
            return (Short) this.variantValue;
        }
        throw new NoSuchElementException("Union element `short` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * <p>int variant</p>
     */
    public Integer anInt() {
        if (this.variantTag == VariantTag.INT) {
            return (Integer) this.variantValue;
        }
        throw new NoSuchElementException("Union element `int` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * <p>long variant</p>
     */
    public Long aLong() {
        if (this.variantTag == VariantTag.LONG) {
            return (Long) this.variantValue;
        }
        throw new NoSuchElementException("Union element `long` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * <p>bigInteger variant</p>
     */
    public BigInteger bigInteger() {
        if (this.variantTag == VariantTag.BIG_INTEGER) {
            return (BigInteger) this.variantValue;
        }
        throw new NoSuchElementException("Union element `bigInteger` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * <p>float variant</p>
     */
    public Float aFloat() {
        if (this.variantTag == VariantTag.FLOAT) {
            return (Float) this.variantValue;
        }
        throw new NoSuchElementException("Union element `float` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * <p>double variant</p>
     */
    public Double aDouble() {
        if (this.variantTag == VariantTag.DOUBLE) {
            return (Double) this.variantValue;
        }
        throw new NoSuchElementException("Union element `double` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * <p>bigDecimal variant</p>
     */
    public BigDecimal bigDecimal() {
        if (this.variantTag == VariantTag.BIG_DECIMAL) {
            return (BigDecimal) this.variantValue;
        }
        throw new NoSuchElementException("Union element `bigDecimal` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * <p>string variant</p>
     */
    public String string() {
        if (this.variantTag == VariantTag.STRING) {
            return (String) this.variantValue;
        }
        throw new NoSuchElementException("Union element `string` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * <p>instant variant</p>
     */
    public Instant instant() {
        if (this.variantTag == VariantTag.INSTANT) {
            return (Instant) this.variantValue;
        }
        throw new NoSuchElementException("Union element `instant` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * <p>Returns an enum value representing which member of this object is populated.</p>
     * <p>This will be {@link Type#UNKNOWN_TO_VERSION} if no members are set.</p>
     */
    public VariantTag variantTag() {
        return this.variantTag;
    }

    /**
     * <p>Returns the untyped value of the union.</p>
     * <p>Use {@link #type()} to get the member currently set.</p>
     */
    public Object variantValue() {
        return this.variantValue;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
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
                buf.append(", int: <*** REDACTED ***>");
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
     * <p>Creates a new builder</p>
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
         * <p>Sets the value for <code>aByte</code></p>
         * <p>byte variant</p>
         */
        public Builder aByte(Byte aByte) {
            this.variantTag = VariantTag.BYTE;
            this.variantValue = aByte;
            return this;
        }

        /**
         * <p>Sets the value for <code>aShort</code></p>
         * <p>short variant</p>
         */
        public Builder aShort(Short aShort) {
            this.variantTag = VariantTag.SHORT;
            this.variantValue = aShort;
            return this;
        }

        /**
         * <p>Sets the value for <code>anInt</code></p>
         * <p>int variant</p>
         */
        public Builder anInt(Integer anInt) {
            this.variantTag = VariantTag.INT;
            this.variantValue = anInt;
            return this;
        }

        /**
         * <p>Sets the value for <code>aLong</code></p>
         * <p>long variant</p>
         */
        public Builder aLong(Long aLong) {
            this.variantTag = VariantTag.LONG;
            this.variantValue = aLong;
            return this;
        }

        /**
         * <p>Sets the value for <code>bigInteger</code></p>
         * <p>bigInteger variant</p>
         */
        public Builder bigInteger(BigInteger bigInteger) {
            this.variantTag = VariantTag.BIG_INTEGER;
            this.variantValue = bigInteger;
            return this;
        }

        /**
         * <p>Sets the value for <code>aFloat</code></p>
         * <p>float variant</p>
         */
        public Builder aFloat(Float aFloat) {
            this.variantTag = VariantTag.FLOAT;
            this.variantValue = aFloat;
            return this;
        }

        /**
         * <p>Sets the value for <code>aDouble</code></p>
         * <p>double variant</p>
         */
        public Builder aDouble(Double aDouble) {
            this.variantTag = VariantTag.DOUBLE;
            this.variantValue = aDouble;
            return this;
        }

        /**
         * <p>Sets the value for <code>bigDecimal</code></p>
         * <p>bigDecimal variant</p>
         */
        public Builder bigDecimal(BigDecimal bigDecimal) {
            this.variantTag = VariantTag.BIG_DECIMAL;
            this.variantValue = bigDecimal;
            return this;
        }

        /**
         * <p>Sets the value for <code>string</code></p>
         * <p>string variant</p>
         */
        public Builder string(String string) {
            this.variantTag = VariantTag.STRING;
            this.variantValue = string;
            return this;
        }

        /**
         * <p>Sets the value for <code>instant</code></p>
         * <p>instant variant</p>
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
