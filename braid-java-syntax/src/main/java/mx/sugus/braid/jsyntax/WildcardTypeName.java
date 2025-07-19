package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents a wildcard type name.
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class WildcardTypeName implements TypeName {
    private final ClassName rawType;
    private final List<TypeName> upperBounds;
    private final List<TypeName> lowerBounds;
    private int _hashCode = 0;

    private WildcardTypeName(Builder builder) {
        this.rawType = builder.rawType;
        this.upperBounds = Objects.requireNonNull(builder.upperBounds.asPersistent(), "upperBounds");
        this.lowerBounds = Objects.requireNonNull(builder.lowerBounds.asPersistent(), "lowerBounds");
    }

    public TypeKind kind() {
        return TypeKind.WILDCARD;
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
     * @return The value of the {@code upperBounds} member
     */
    public List<TypeName> upperBounds() {
        return this.upperBounds;
    }

    /**
     * 
     * @return The value of the {@code lowerBounds} member
     */
    public List<TypeName> lowerBounds() {
        return this.lowerBounds;
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
        return visitor.visitWildcardTypeName(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WildcardTypeName that = (WildcardTypeName) obj;
        return Objects.equals(this.rawType, that.rawType)
            && this.upperBounds.equals(that.upperBounds)
            && this.lowerBounds.equals(that.lowerBounds);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + this.kind().hashCode();
            hashCode = 31 * hashCode + (rawType != null ? rawType.hashCode() : 0);
            hashCode = 31 * hashCode + upperBounds.hashCode();
            hashCode = 31 * hashCode + lowerBounds.hashCode();
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "WildcardTypeName{"
            + "kind: " + kind()
            + ", rawType: " + rawType
            + ", upperBounds: " + upperBounds
            + ", lowerBounds: " + lowerBounds + "}";
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
     * A class to build instances of WildcardTypeName
     */
    public static final class Builder implements TypeName.Builder {
        private ClassName rawType;
        private CollectionBuilderReference<List<TypeName>> upperBounds;
        private CollectionBuilderReference<List<TypeName>> lowerBounds;

        Builder() {
            this.upperBounds = CollectionBuilderReference.forList();
            this.lowerBounds = CollectionBuilderReference.forList();
        }

        Builder(WildcardTypeName data) {
            this.rawType = data.rawType;
            this.upperBounds = CollectionBuilderReference.fromPersistentList(data.upperBounds);
            this.lowerBounds = CollectionBuilderReference.fromPersistentList(data.lowerBounds);
        }

        /**
         * Sets the value for {@code rawType}.
         * 
         * @param rawType The value to be set.
         * @return This instance for chain calling.
         */
        public Builder rawType(ClassName rawType) {
            this.rawType = rawType;
            return this;
        }

        /**
         * Sets the value for {@code upperBounds}.
         * 
         * @param upperBounds The value to be set.
         * @return This instance for chain calling.
         */
        public Builder upperBounds(List<TypeName> upperBounds) {
            this.upperBounds.clear();
            this.upperBounds.asTransient().addAll(upperBounds);
            return this;
        }

        /**
         * Adds a value to {@code upperBounds}.
         * 
         * @param upperBounds The value tp add
         * @return This instance for chain calling.
         */
        public Builder addUpperBound(TypeName upperBound) {
            this.upperBounds.asTransient().add(upperBound);
            return this;
        }

        /**
         * Creates a new TypeName instance out of the given class.
         */
        public Builder addUpperBound(Class<?> kclass) {
            this.upperBounds.asTransient().add(TypeName.from(kclass));
            return this;
        }

        /**
         * Sets the value for {@code lowerBounds}.
         * 
         * @param lowerBounds The value to be set.
         * @return This instance for chain calling.
         */
        public Builder lowerBounds(List<TypeName> lowerBounds) {
            this.lowerBounds.clear();
            this.lowerBounds.asTransient().addAll(lowerBounds);
            return this;
        }

        /**
         * Adds a value to {@code lowerBounds}.
         * 
         * @param lowerBounds The value tp add
         * @return This instance for chain calling.
         */
        public Builder addLowerBound(TypeName lowerBound) {
            this.lowerBounds.asTransient().add(lowerBound);
            return this;
        }

        /**
         * Creates a new TypeName instance out of the given class.
         */
        public Builder addLowerBound(Class<?> kclass) {
            this.lowerBounds.asTransient().add(TypeName.from(kclass));
            return this;
        }

        /**
         * Returns a new instance of {@link WildcardTypeName}
         * 
         * @return A new instance of {@link WildcardTypeName}
         */
        public WildcardTypeName build() {
            return new WildcardTypeName(this);
        }
    }
}
