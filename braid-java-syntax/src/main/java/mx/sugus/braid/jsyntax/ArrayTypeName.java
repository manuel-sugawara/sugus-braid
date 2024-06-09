package mx.sugus.braid.jsyntax;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a java array type.</p>
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

    public TypeName componentType() {
        return this.componentType;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitArrayTypeName(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ArrayTypeName that = (ArrayTypeName) obj;
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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private TypeName componentType;

        Builder() {
        }

        Builder(ArrayTypeName data) {
            this.componentType = data.componentType;
        }

        /**
         * <p>Sets the value for <code>componentType</code></p>
         */
        public Builder componentType(TypeName componentType) {
            this.componentType = componentType;
            return this;
        }

        public Builder componentType(Class<?> clazz) {
            this.componentType = TypeName.from(clazz);
            return this;
        }

        public ArrayTypeName build() {
            return new ArrayTypeName(this);
        }
    }
}
