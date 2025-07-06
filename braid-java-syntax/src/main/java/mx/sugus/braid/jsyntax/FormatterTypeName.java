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

    /**
     * 
     * @return The value of the {@code value} member
     */
    public TypeName value() {
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
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements FormatterNode.Builder {
        private TypeName value;

        Builder() {
        }

        Builder(FormatterTypeName data) {
            this.value = data.value;
        }

        /**
         * Sets the value for {@code value}.
         * 
         * @param value The value to be set.
         * @return This instance for chain calling.
         */
        public Builder value(TypeName value) {
            this.value = value;
            return this;
        }

        public Builder value(Class<?> clazz) {
            this.value = TypeName.from(clazz);
            return this;
        }

        /**
         * Returns a new instance of {@link FormatterTypeName}
         * 
         * @return A new instance of {@link FormatterTypeName}
         */
        public FormatterTypeName build() {
            return new FormatterTypeName(this);
        }
    }
}
