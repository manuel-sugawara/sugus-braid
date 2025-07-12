package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Format boolean
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class FormatBoolean implements Format {
    private final Boolean value;

    private FormatBoolean(Builder builder) {
        this.value = builder.value;
    }

    public FormatKind kind() {
        return FormatKind.BOOLEAN;
    }

    /**
     * The boolean value
     * 
     * @return The value of the {@code value} member
     */
    public Boolean value() {
        return this.value;
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
        FormatBoolean that = (FormatBoolean) obj;
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
        return "FormatBoolean{"
            + "kind: " + kind()
            + ", value: " + value + "}";
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
     * A class to build instances of FormatBoolean
     */
    public static final class Builder implements Format.Builder {
        private Boolean value;

        Builder() {
        }

        Builder(FormatBoolean data) {
            this.value = data.value;
        }

        /**
         * Sets the value for {@code value}.
         * 
         * @param value The value to be set.
         * @return This instance for chain calling.
         */
        public Builder value(Boolean value) {
            this.value = value;
            return this;
        }

        /**
         * Returns a new instance of {@link FormatBoolean}
         * 
         * @return A new instance of {@link FormatBoolean}
         */
        public FormatBoolean build() {
            return new FormatBoolean(this);
        }
    }
}
