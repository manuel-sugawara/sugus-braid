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
        this.aBoolean = builder.aBoolean;
        this.aByte = builder.aByte;
        this.aShort = builder.aShort;
        this.anInt = builder.anInt;
        this.aLong = builder.aLong;
        this.aFloat = builder.aFloat;
        this.aDouble = builder.aDouble;
        this.string = builder.string;
    }

    public Boolean aBoolean() {
        return this.aBoolean;
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

    public Float aFloat() {
        return this.aFloat;
    }

    public Double aDouble() {
        return this.aDouble;
    }

    public String string() {
        return this.string;
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
        DefaultValues that = (DefaultValues) obj;
        return Objects.equals(this.aBoolean, that.aBoolean)
            && Objects.equals(this.aByte, that.aByte)
            && Objects.equals(this.aShort, that.aShort)
            && Objects.equals(this.anInt, that.anInt)
            && Objects.equals(this.aLong, that.aLong)
            && Objects.equals(this.aFloat, that.aFloat)
            && Objects.equals(this.aDouble, that.aDouble)
            && Objects.equals(this.string, that.string);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + (aBoolean != null ? aBoolean.hashCode() : 0);
            hashCode = 31 * hashCode + (aByte != null ? aByte.hashCode() : 0);
            hashCode = 31 * hashCode + (aShort != null ? aShort.hashCode() : 0);
            hashCode = 31 * hashCode + (anInt != null ? anInt.hashCode() : 0);
            hashCode = 31 * hashCode + (aLong != null ? aLong.hashCode() : 0);
            hashCode = 31 * hashCode + (aFloat != null ? aFloat.hashCode() : 0);
            hashCode = 31 * hashCode + (aDouble != null ? aDouble.hashCode() : 0);
            hashCode = 31 * hashCode + (string != null ? string.hashCode() : 0);
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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

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
            this.aDouble = 2.71828;
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
         * <p>Sets the value for <code>aBoolean</code></p>
         */
        public Builder aBoolean(Boolean aBoolean) {
            this.aBoolean = aBoolean;
            return this;
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
         * <p>Sets the value for <code>string</code></p>
         */
        public Builder string(String string) {
            this.string = string;
            return this;
        }

        public DefaultValues build() {
            return new DefaultValues(this);
        }
    }
}
