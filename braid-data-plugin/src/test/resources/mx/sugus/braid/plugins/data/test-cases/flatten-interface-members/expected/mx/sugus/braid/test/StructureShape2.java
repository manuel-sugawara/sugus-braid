package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>A simple structure that implements base</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureShape2 implements StructureBase {
    private final Integer intValue;
    private final String stringValue;

    private StructureShape2(Builder builder) {
        this.intValue = builder.intValue;
        this.stringValue = Objects.requireNonNull(builder.stringValue, "stringValue");
    }

    public Integer intValue() {
        return this.intValue;
    }

    public String stringValue() {
        return this.stringValue;
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
        StructureShape2 that = (StructureShape2) obj;
        return Objects.equals(this.intValue, that.intValue)
            && this.stringValue.equals(that.stringValue);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (intValue != null ? intValue.hashCode() : 0);
        hashCode = 31 * hashCode + stringValue.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "StructureShape2{"
            + "intValue: " + intValue
            + ", stringValue: " + stringValue + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements StructureBase.Builder {
        private Integer intValue;
        private String stringValue;

        Builder() {
        }

        Builder(StructureShape2 data) {
            this.intValue = data.intValue;
            this.stringValue = data.stringValue;
        }

        /**
         * <p>Sets the value for <code>intValue</code></p>
         */
        public Builder intValue(Integer intValue) {
            this.intValue = intValue;
            return this;
        }

        /**
         * <p>Sets the value for <code>stringValue</code></p>
         */
        public Builder stringValue(String stringValue) {
            this.stringValue = stringValue;
            return this;
        }

        public StructureShape2 build() {
            return new StructureShape2(this);
        }
    }
}
