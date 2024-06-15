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
    private final Object value;
    private final Type type;

    private AnySimpleType(Builder builder) {
        this.value = builder.getValue();
        this.type = builder.type;
    }

    /**
     * <p>byte variant</p>
     */
    public Byte aByte() {
        if (this.type == Type.BYTE) {
            return (Byte) this.value;
        }
        throw new NoSuchElementException("Union element `byte` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>short variant</p>
     */
    public Short aShort() {
        if (this.type == Type.SHORT) {
            return (Short) this.value;
        }
        throw new NoSuchElementException("Union element `short` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>int variant</p>
     */
    public Integer anInt() {
        if (this.type == Type.INT) {
            return (Integer) this.value;
        }
        throw new NoSuchElementException("Union element `int` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>long variant</p>
     */
    public Long aLong() {
        if (this.type == Type.LONG) {
            return (Long) this.value;
        }
        throw new NoSuchElementException("Union element `long` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>bigInteger variant</p>
     */
    public BigInteger bigInteger() {
        if (this.type == Type.BIG_INTEGER) {
            return (BigInteger) this.value;
        }
        throw new NoSuchElementException("Union element `bigInteger` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>float variant</p>
     */
    public Float aFloat() {
        if (this.type == Type.FLOAT) {
            return (Float) this.value;
        }
        throw new NoSuchElementException("Union element `float` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>double variant</p>
     */
    public Double aDouble() {
        if (this.type == Type.DOUBLE) {
            return (Double) this.value;
        }
        throw new NoSuchElementException("Union element `double` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>bigDecimal variant</p>
     */
    public BigDecimal bigDecimal() {
        if (this.type == Type.BIG_DECIMAL) {
            return (BigDecimal) this.value;
        }
        throw new NoSuchElementException("Union element `bigDecimal` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>string variant</p>
     */
    public String string() {
        if (this.type == Type.STRING) {
            return (String) this.value;
        }
        throw new NoSuchElementException("Union element `string` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>instant variant</p>
     */
    public Instant instant() {
        if (this.type == Type.INSTANT) {
            return (Instant) this.value;
        }
        throw new NoSuchElementException("Union element `instant` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>Returns an enum value representing which member of this object is populated.</p>
     * <p>This will be {@link Type#UNKNOWN_TO_VERSION} if no members are set.</p>
     */
    public Type type() {
        return this.type;
    }

    /**
     * <p>Returns the untyped value of the union.</p>
     * <p>Use {@link #type()} to get the member currently set.</p>
     */
    public Object value() {
        return this.value;
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
        return this.type == that.type && this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return this.type.hashCode() + 31 * this.value.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("AnySimpleType{type: ");
        buf.append(this.type);
        switch (this.type) {
            case BYTE:
                buf.append(", byte: ").append(this.value);
                break;
            case SHORT:
                buf.append(", short: ").append(this.value);
                break;
            case INT:
                buf.append(", int: <*** REDACTED ***>");
                break;
            case LONG:
                buf.append(", long: ").append(this.value);
                break;
            case BIG_INTEGER:
                buf.append(", bigInteger: ").append(this.value);
                break;
            case FLOAT:
                buf.append(", float: ").append(this.value);
                break;
            case DOUBLE:
                buf.append(", double: ").append(this.value);
                break;
            case BIG_DECIMAL:
                buf.append(", bigDecimal: ").append(this.value);
                break;
            case STRING:
                buf.append(", string: ").append(this.value);
                break;
            case INSTANT:
                buf.append(", instant: ").append(this.value);
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

    public enum Type {
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

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static final class Builder {
        private Object value;
        private Type type;

        Builder() {
            this.type = null;
            this.value = Type.UNKNOWN_TO_VERSION;
        }

        Builder(AnySimpleType data) {
            this.type = data.type;
            this.value = data.value;
        }

        /**
         * <p>Sets the value for <code>aByte</code></p>
         * <p>byte variant</p>
         */
        public Builder aByte(Byte aByte) {
            this.type = Type.BYTE;
            this.value = aByte;
            return this;
        }

        /**
         * <p>Sets the value for <code>aShort</code></p>
         * <p>short variant</p>
         */
        public Builder aShort(Short aShort) {
            this.type = Type.SHORT;
            this.value = aShort;
            return this;
        }

        /**
         * <p>Sets the value for <code>anInt</code></p>
         * <p>int variant</p>
         */
        public Builder anInt(Integer anInt) {
            this.type = Type.INT;
            this.value = anInt;
            return this;
        }

        /**
         * <p>Sets the value for <code>aLong</code></p>
         * <p>long variant</p>
         */
        public Builder aLong(Long aLong) {
            this.type = Type.LONG;
            this.value = aLong;
            return this;
        }

        /**
         * <p>Sets the value for <code>bigInteger</code></p>
         * <p>bigInteger variant</p>
         */
        public Builder bigInteger(BigInteger bigInteger) {
            this.type = Type.BIG_INTEGER;
            this.value = bigInteger;
            return this;
        }

        /**
         * <p>Sets the value for <code>aFloat</code></p>
         * <p>float variant</p>
         */
        public Builder aFloat(Float aFloat) {
            this.type = Type.FLOAT;
            this.value = aFloat;
            return this;
        }

        /**
         * <p>Sets the value for <code>aDouble</code></p>
         * <p>double variant</p>
         */
        public Builder aDouble(Double aDouble) {
            this.type = Type.DOUBLE;
            this.value = aDouble;
            return this;
        }

        /**
         * <p>Sets the value for <code>bigDecimal</code></p>
         * <p>bigDecimal variant</p>
         */
        public Builder bigDecimal(BigDecimal bigDecimal) {
            this.type = Type.BIG_DECIMAL;
            this.value = bigDecimal;
            return this;
        }

        /**
         * <p>Sets the value for <code>string</code></p>
         * <p>string variant</p>
         */
        public Builder string(String string) {
            this.type = Type.STRING;
            this.value = string;
            return this;
        }

        /**
         * <p>Sets the value for <code>instant</code></p>
         * <p>instant variant</p>
         */
        public Builder instant(Instant instant) {
            this.type = Type.INSTANT;
            this.value = instant;
            return this;
        }

        Object getValue() {
            return this.value;
        }

        public AnySimpleType build() {
            return new AnySimpleType(this);
        }
    }
}
