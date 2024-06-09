package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a wildcard type name.</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class WildcardTypeName implements TypeName {
    private final ClassName rawType;
    private final List<TypeName> upperBounds;
    private final List<TypeName> lowerBounds;
    private int _hashCode = 0;

    private WildcardTypeName(Builder builder) {
        this.rawType = builder.rawType;
        this.upperBounds = builder.upperBounds.asPersistent();
        this.lowerBounds = builder.lowerBounds.asPersistent();
    }

    public TypeKind kind() {
        return TypeKind.WILDCARD;
    }

    public ClassName rawType() {
        return this.rawType;
    }

    public List<TypeName> upperBounds() {
        return this.upperBounds;
    }

    public List<TypeName> lowerBounds() {
        return this.lowerBounds;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
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
         * <p>Sets the value for <code>rawType</code></p>
         */
        public Builder rawType(ClassName rawType) {
            this.rawType = rawType;
            return this;
        }

        /**
         * <p>Sets the value for <code>upperBounds</code></p>
         */
        public Builder upperBounds(List<TypeName> upperBounds) {
            this.upperBounds.clear();
            this.upperBounds.asTransient().addAll(upperBounds);
            return this;
        }

        /**
         * <p>Adds a single value for <code>upperBounds</code></p>
         */
        public Builder addUpperBound(TypeName upperBound) {
            this.upperBounds.asTransient().add(upperBound);
            return this;
        }

        /**
         * <p>Creates a new TypeName instance out of the given class.</p>
         */
        public Builder addUpperBound(Class<?> kclass) {
            this.upperBounds.asTransient().add(TypeName.from(kclass));
            return this;
        }

        /**
         * <p>Sets the value for <code>lowerBounds</code></p>
         */
        public Builder lowerBounds(List<TypeName> lowerBounds) {
            this.lowerBounds.clear();
            this.lowerBounds.asTransient().addAll(lowerBounds);
            return this;
        }

        /**
         * <p>Adds a single value for <code>lowerBounds</code></p>
         */
        public Builder addLowerBound(TypeName lowerBound) {
            this.lowerBounds.asTransient().add(lowerBound);
            return this;
        }

        /**
         * <p>Creates a new TypeName instance out of the given class.</p>
         */
        public Builder addLowerBound(Class<?> kclass) {
            this.lowerBounds.asTransient().add(TypeName.from(kclass));
            return this;
        }

        public WildcardTypeName build() {
            return new WildcardTypeName(this);
        }
    }
}
