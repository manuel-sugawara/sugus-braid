package mx.sugus.braid.jsyntax;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents a java primitive type.
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class PrimitiveTypeName implements TypeName {
    private final TypePrimitiveName name;

    private PrimitiveTypeName(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
    }

    public TypeKind kind() {
        return TypeKind.PRIMITIVE;
    }

    /**
     * 
     * @return The value of the {@code name} member
     */
    public TypePrimitiveName name() {
        return this.name;
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
        return visitor.visitPrimitiveTypeName(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PrimitiveTypeName that = (PrimitiveTypeName) obj;
        return this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.kind().hashCode();
        hashCode = 31 * hashCode + name.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "PrimitiveTypeName{"
            + "kind: " + kind()
            + ", name: " + name + "}";
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
     * A class to build instances of PrimitiveTypeName
     */
    public static final class Builder implements TypeName.Builder {
        private TypePrimitiveName name;

        Builder() {
        }

        Builder(PrimitiveTypeName data) {
            this.name = data.name;
        }

        /**
         * Sets the value for {@code name}.
         * 
         * @param name The value to be set.
         * @return This instance for chain calling.
         */
        public Builder name(TypePrimitiveName name) {
            this.name = Objects.requireNonNull(name, "name");
            return this;
        }

        /**
         * Returns a new instance of {@link PrimitiveTypeName}
         * 
         * @return A new instance of {@link PrimitiveTypeName}
         */
        public PrimitiveTypeName build() {
            return new PrimitiveTypeName(this);
        }
    }
}
