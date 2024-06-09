package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a type variable name.</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class TypeVariableTypeName implements TypeName {
    private final String name;
    private final List<TypeName> bounds;

    private TypeVariableTypeName(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.bounds = builder.bounds.asPersistent();
    }

    public TypeKind kind() {
        return TypeKind.TYPE_VARIABLE;
    }

    public String name() {
        return this.name;
    }

    public List<TypeName> bounds() {
        return this.bounds;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitTypeVariableTypeName(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TypeVariableTypeName that = (TypeVariableTypeName) obj;
        return this.name.equals(that.name)
            && this.bounds.equals(that.bounds);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.kind().hashCode();
        hashCode = 31 * hashCode + name.hashCode();
        hashCode = 31 * hashCode + bounds.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "TypeVariableTypeName{"
            + "kind: " + kind()
            + ", name: " + name
            + ", bounds: " + bounds + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static TypeVariableTypeName from(String name) {
        return TypeVariableTypeName.builder().name(name).build();
    }

    public static final class Builder {
        private String name;
        private CollectionBuilderReference<List<TypeName>> bounds;

        Builder() {
            this.bounds = CollectionBuilderReference.forList();
        }

        Builder(TypeVariableTypeName data) {
            this.name = data.name;
            this.bounds = CollectionBuilderReference.fromPersistentList(data.bounds);
        }

        /**
         * <p>Sets the value for <code>name</code></p>
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * <p>Sets the value for <code>bounds</code></p>
         */
        public Builder bounds(List<TypeName> bounds) {
            this.bounds.clear();
            this.bounds.asTransient().addAll(bounds);
            return this;
        }

        /**
         * <p>Adds a single value for <code>bounds</code></p>
         */
        public Builder addBound(TypeName bound) {
            this.bounds.asTransient().add(bound);
            return this;
        }

        /**
         * <p>Creates a new TypeName instance out of the given class.</p>
         */
        public Builder addBound(Class<?> kclass) {
            this.bounds.asTransient().add(TypeName.from(kclass));
            return this;
        }

        public TypeVariableTypeName build() {
            return new TypeVariableTypeName(this);
        }
    }
}
