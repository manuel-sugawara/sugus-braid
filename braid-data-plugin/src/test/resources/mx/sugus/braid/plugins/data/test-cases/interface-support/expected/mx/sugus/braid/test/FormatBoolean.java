package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Format boolean</p>
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
     * <p>The boolean value</p>
     */
    public Boolean value() {
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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Boolean value;

        Builder() {
        }

        Builder(FormatBoolean data) {
            this.value = data.value;
        }

        /**
         * <p>Sets the value for <code>value</code></p>
         * <p>The boolean value</p>
         */
        public Builder value(Boolean value) {
            this.value = value;
            return this;
        }

        public FormatBoolean build() {
            return new FormatBoolean(this);
        }
    }
}
