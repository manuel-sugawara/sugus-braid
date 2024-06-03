package mx.sugus.braid.jsyntax;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class FormatterString implements FormatterNode {
    private final String value;

    private FormatterString(Builder builder) {
        this.value = builder.value;
    }

    public SyntaxFormatterNodeKind kind() {
        return SyntaxFormatterNodeKind.STRING;
    }

    public String value() {
        return this.value;
    }

    /**
     * Returns a new builder to modify a copy of this instance
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
        FormatterString that = (FormatterString) obj;
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
        return "FormatterString{"
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
        private String value;

        Builder() {
        }

        Builder(FormatterString data) {
            this.value = data.value;
        }

        /**
         * <p>Sets the value for <code>value</code></p>
         */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public FormatterString build() {
            return new FormatterString(this);
        }
    }
}
