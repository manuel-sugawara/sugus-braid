package mx.sugus.braid.jsyntax;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class FormatterTypeName implements FormatterNode {
    private final TypeName value;

    private FormatterTypeName(Builder builder) {
        this.value = builder.value;
    }

    public SyntaxFormatterNodeKind kind() {
        return SyntaxFormatterNodeKind.TYPE_NAME;
    }

    public TypeName value() {
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
        FormatterTypeName that = (FormatterTypeName) obj;
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
        return "FormatterTypeName{"
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
        private TypeName value;

        Builder() {
        }

        Builder(FormatterTypeName data) {
            this.value = data.value;
        }

        /**
         * <p>Sets the value for <code>value</code></p>
         */
        public Builder value(TypeName value) {
            this.value = value;
            return this;
        }

        public Builder value(Class<?> clazz) {
            this.value = TypeName.from(clazz);
            return this;
        }

        public FormatterTypeName build() {
            return new FormatterTypeName(this);
        }
    }
}
