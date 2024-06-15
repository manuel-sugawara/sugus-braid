package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a parametrized java type.</p>
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

    public ClassName rawType() {
        return this.rawType;
    }

    public List<TypeName> typeArguments() {
        return this.typeArguments;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

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
     * <p>Creates a new builder</p>
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

    public static final class Builder {
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
         * <p>Sets the value for <code>rawType</code></p>
         */
        public Builder rawType(ClassName rawType) {
            this.rawType = rawType;
            return this;
        }

        /**
         * <p>Sets the value for <code>typeArguments</code></p>
         */
        public Builder typeArguments(List<TypeName> typeArguments) {
            this.typeArguments.clear();
            this.typeArguments.asTransient().addAll(typeArguments);
            return this;
        }

        /**
         * <p>Adds a single value for <code>typeArguments</code></p>
         */
        public Builder addTypeArgument(TypeName typeArgument) {
            this.typeArguments.asTransient().add(typeArgument);
            return this;
        }

        /**
         * <p>Creates a new TypeName instance out of the given class.</p>
         */
        public Builder addTypeArgument(Class<?> kclass) {
            this.typeArguments.asTransient().add(TypeName.from(kclass));
            return this;
        }

        public ParameterizedTypeName build() {
            return new ParameterizedTypeName(this);
        }
    }
}
