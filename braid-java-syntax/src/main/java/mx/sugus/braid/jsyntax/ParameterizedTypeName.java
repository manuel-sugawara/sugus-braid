package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents a parametrized java type.
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class ParameterizedTypeName implements TypeName {
    private final ClassName rawType;
    private final List<TypeName> typeArguments;

    private ParameterizedTypeName(Builder builder) {
        this.rawType = Objects.requireNonNull(builder.rawType, "rawType");
        this.typeArguments = Objects.requireNonNull(builder.typeArguments.asPersistent(), "typeArguments");
    }

    public TypeKind kind() {
        return TypeKind.PARAMETERIZED;
    }

    /**
     * 
     * @return The value of the {@code rawType} member
     */
    public ClassName rawType() {
        return this.rawType;
    }

    /**
     * 
     * @return The value of the {@code typeArguments} member
     */
    public List<TypeName> typeArguments() {
        return this.typeArguments;
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
        return visitor.visitParameterizedTypeName(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ParameterizedTypeName that = (ParameterizedTypeName) obj;
        return this.rawType.equals(that.rawType)
            && this.typeArguments.equals(that.typeArguments);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.kind().hashCode();
        hashCode = 31 * hashCode + rawType.hashCode();
        hashCode = 31 * hashCode + typeArguments.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "ParameterizedTypeName{"
            + "kind: " + kind()
            + ", rawType: " + rawType
            + ", typeArguments: " + typeArguments + "}";
    }

    /**
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static ParameterizedTypeName from(ClassName base, TypeName... params) {
        ParameterizedTypeName.Builder builder = builder()
                                                    .rawType(base);
            for (TypeName param : params) {
                builder.addTypeArgument(param);
            }
            return builder.build();
    }

    public static ParameterizedTypeName from(Class<?> kclass, TypeName... params) {
        ParameterizedTypeName.Builder builder = builder()
                                                    .rawType(ClassName.from(kclass));
            for (TypeName param : params) {
                builder.addTypeArgument(param);
            }
            return builder.build();
    }

    public static ParameterizedTypeName from(Class<?> kclass, Class<?>... params) {
        ParameterizedTypeName.Builder builder = builder()
                                                    .rawType(ClassName.from(kclass));
            for (Class<?> param : params) {
                builder.addTypeArgument(TypeName.from(param));
            }
            return builder.build();
    }

    /**
     * A class to build instances of ParameterizedTypeName
     */
    public static final class Builder implements TypeName.Builder {
        private ClassName rawType;
        private CollectionBuilderReference<List<TypeName>> typeArguments;

        Builder() {
            this.typeArguments = CollectionBuilderReference.forList();
        }

        Builder(ParameterizedTypeName data) {
            this.rawType = data.rawType;
            this.typeArguments = CollectionBuilderReference.fromPersistentList(data.typeArguments);
        }

        /**
         * Sets the value for {@code rawType}.
         * 
         * @param rawType The value to be set.
         * @return This instance for chain calling.
         */
        public Builder rawType(ClassName rawType) {
            this.rawType = Objects.requireNonNull(rawType, "rawType");
            return this;
        }

        /**
         * Sets the value for {@code typeArguments}.
         * 
         * @param typeArguments The value to be set.
         * @return This instance for chain calling.
         */
        public Builder typeArguments(List<TypeName> typeArguments) {
            this.typeArguments.clear();
            this.typeArguments.asTransient().addAll(typeArguments);
            return this;
        }

        /**
         * Adds a value to {@code typeArguments}.
         * 
         * @param typeArguments The value tp add
         * @return This instance for chain calling.
         */
        public Builder addTypeArgument(TypeName typeArgument) {
            this.typeArguments.asTransient().add(typeArgument);
            return this;
        }

        /**
         * Creates a new TypeName instance out of the given class.
         */
        public Builder addTypeArgument(Class<?> kclass) {
            this.typeArguments.asTransient().add(TypeName.from(kclass));
            return this;
        }

        /**
         * Returns a new instance of {@link ParameterizedTypeName}
         * 
         * @return A new instance of {@link ParameterizedTypeName}
         */
        public ParameterizedTypeName build() {
            return new ParameterizedTypeName(this);
        }
    }
}
