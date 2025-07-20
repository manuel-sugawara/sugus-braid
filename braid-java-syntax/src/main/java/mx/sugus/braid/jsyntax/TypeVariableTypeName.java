package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents a type variable name.
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class TypeVariableTypeName implements TypeName {
    private final String name;
    private final List<TypeName> bounds;

    private TypeVariableTypeName(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.bounds = Objects.requireNonNull(builder.bounds.asPersistent(), "bounds");
    }

    public TypeKind kind() {
        return TypeKind.TYPE_VARIABLE;
    }

    /**
     * 
     * @return The value of the {@code name} member
     */
    public String name() {
        return this.name;
    }

    /**
     * 
     * @return The value of the {@code bounds} member
     */
    public List<TypeName> bounds() {
        return this.bounds;
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
        return visitor.visitTypeVariableTypeName(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        TypeVariableTypeName that = (TypeVariableTypeName) other;
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
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static TypeVariableTypeName from(String name) {
        return TypeVariableTypeName.builder().name(name).build();
    }

    /**
     * A class to build instances of TypeVariableTypeName
     */
    public static final class Builder implements TypeName.Builder {
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
         * Sets the value for {@code name}.
         * 
         * @param name The value to be set.
         * @return This instance for chain calling.
         */
        public Builder name(String name) {
            this.name = Objects.requireNonNull(name, "name");
            return this;
        }

        /**
         * Sets the value for {@code bounds}.
         * 
         * @param bounds The value to be set.
         * @return This instance for chain calling.
         */
        public Builder bounds(List<TypeName> bounds) {
            this.bounds.clear();
            this.bounds.asTransient().addAll(bounds);
            return this;
        }

        /**
         * Adds a value to {@code bounds}.
         * 
         * @param bounds The value tp add
         * @return This instance for chain calling.
         */
        public Builder addBound(TypeName bound) {
            this.bounds.asTransient().add(bound);
            return this;
        }

        /**
         * Creates a new TypeName instance out of the given class.
         */
        public Builder addBound(Class<?> kclass) {
            this.bounds.asTransient().add(TypeName.from(kclass));
            return this;
        }

        /**
         * Returns a new instance of {@link TypeVariableTypeName}
         * 
         * @return A new instance of {@link TypeVariableTypeName}
         */
        public TypeVariableTypeName build() {
            return new TypeVariableTypeName(this);
        }
    }
}
