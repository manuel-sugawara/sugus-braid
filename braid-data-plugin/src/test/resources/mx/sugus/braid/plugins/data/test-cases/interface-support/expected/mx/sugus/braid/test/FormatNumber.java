package mx.sugus.braid.test;

import java.math.BigDecimal;
import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Format number</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class FormatNumber implements Format {
    private final BigDecimal value;

    private FormatNumber(Builder builder) {
        this.value = builder.value;
    }

    public FormatKind kind() {
        return FormatKind.NUMBER;
    }

    /**
     * <p>The number value</p>
     */
    public BigDecimal value() {
        return this.value;
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
        FormatNumber that = (FormatNumber) obj;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.kind().hashCode();
        hashCode = 31 * hashCode + (value != null ? value.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "FormatNumber{"
            + "kind: " + kind()
            + ", value: " + value + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private BigDecimal value;

        Builder() {
        }

        Builder(FormatNumber data) {
            this.value = data.value;
        }

        /**
         * <p>Sets the value for <code>value</code></p>
         * <p>The number value</p>
         */
        public Builder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public FormatNumber build() {
            return new FormatNumber(this);
        }
    }
}
