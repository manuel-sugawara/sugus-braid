package mx.sugus.braid.jsyntax;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a java primitive type.</p>
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

    public TypePrimitiveName name() {
        return this.name;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private TypePrimitiveName name;

        Builder() {
        }

        Builder(PrimitiveTypeName data) {
            this.name = data.name;
        }

        /**
         * <p>Sets the value for <code>name</code></p>
         */
        public Builder name(TypePrimitiveName name) {
            this.name = name;
            return this;
        }

        public PrimitiveTypeName build() {
            return new PrimitiveTypeName(this);
        }
    }
}
