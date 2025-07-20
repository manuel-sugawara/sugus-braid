package mx.sugus.braid.jsyntax;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents a java array type.
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class ArrayTypeName implements TypeName {
    private final TypeName componentType;

    private ArrayTypeName(Builder builder) {
        this.componentType = Objects.requireNonNull(builder.componentType, "componentType");
    }

    public TypeKind kind() {
        return TypeKind.ARRAY;
    }

    /**
     * 
     * @return The value of the {@code componentType} member
     */
    public TypeName componentType() {
        return this.componentType;
    }

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Accepts a {@link SyntaxNodeVisitor<VisitorR>} visitor
     * 
     * @param visitor The visitor to accept
     * @param <VisitorR> The result type from the visitor
     * @return The result from the visitor
     */
    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitArrayTypeName(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        ArrayTypeName that = (ArrayTypeName) other;
        return this.componentType.equals(that.componentType);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.kind().hashCode();
        hashCode = 31 * hashCode + componentType.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "ArrayTypeName{"
            + "kind: " + kind()
            + ", componentType: " + componentType + "}";
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
     * A class to build instances of ArrayTypeName
     */
    public static final class Builder implements TypeName.Builder {
        private TypeName componentType;

        Builder() {
        }

        Builder(ArrayTypeName data) {
            this.componentType = data.componentType;
        }

        /**
         * Sets the value for {@code componentType}.
         * 
         * @param componentType The value to be set.
         * @return This instance for chain calling.
         */
        public Builder componentType(TypeName componentType) {
            this.componentType = Objects.requireNonNull(componentType, "componentType");
            return this;
        }

        public Builder componentType(Class<?> clazz) {
            this.componentType = TypeName.from(clazz);
            return this;
        }

        /**
         * Returns a new instance of {@link ArrayTypeName}
         * 
         * @return A new instance of {@link ArrayTypeName}
         */
        public ArrayTypeName build() {
            return new ArrayTypeName(this);
        }
    }
}
