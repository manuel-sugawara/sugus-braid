package mx.sugus.braid.jsyntax;

import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represent an {@code annotation}.
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class Annotation implements SyntaxNode {
    private final ClassName type;
    private final Map<java.lang.String, MemberValue> members;

    private Annotation(Builder builder) {
        this.type = Objects.requireNonNull(builder.type, "type");
        this.members = Objects.requireNonNull(builder.members.asPersistent(), "members");
    }

    /**
     * The class for the annotation
     * 
     * @return The value of the {@code type} member
     */
    public ClassName type() {
        return this.type;
    }

    /**
     * The members of the annotation
     * 
     * @return The value of the {@code members} member
     */
    public Map<java.lang.String, MemberValue> members() {
        return this.members;
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
        return visitor.visitAnnotation(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Annotation that = (Annotation) other;
        return this.type.equals(that.type)
            && this.members.equals(that.members);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + type.hashCode();
        hashCode = 31 * hashCode + members.hashCode();
        return hashCode;
    }

    @Override
    public java.lang.String toString() {
        return "Annotation{"
            + "type: " + type
            + ", members: " + members + "}";
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
     * Creates a new builder
     */
    public static Builder builder(ClassName type) {
        return builder().type(type);
    }

    /**
     * Creates a new builder
     */
    public static Builder builder(Class<?> kclass) {
        return builder().type(ClassName.from(kclass));
    }

    /**
     * Creates a new annotation with a single string member value.
     */
    public static Annotation fromStringValue(Class<?> kclass, String value) {
        return builder().type(ClassName.from(kclass))
                .putMember("value", MemberValue.forExpression(CodeBlock.from("$S", value)))
                .build();
    }

    /**
     * A class to build instances of Annotation
     */
    public static final class Builder implements SyntaxNode.Builder {
        private ClassName type;
        private CollectionBuilderReference<Map<java.lang.String, MemberValue>> members;

        Builder() {
            this.members = CollectionBuilderReference.forOrderedMap();
        }

        Builder(Annotation data) {
            this.type = data.type;
            this.members = CollectionBuilderReference.fromPersistentOrderedMap(data.members);
        }

        /**
         * Sets the value for {@code type}.
         * 
         * @param type The value to be set.
         * @return This instance for chain calling.
         */
        public Builder type(ClassName type) {
            this.type = Objects.requireNonNull(type, "type");
            return this;
        }

        /**
         * Sets the value for {@code members}.
         * 
         * @param members The value to be set.
         * @return This instance for chain calling.
         */
        public Builder members(Map<java.lang.String, MemberValue> members) {
            this.members.clear();
            this.members.asTransient().putAll(members);
            return this;
        }

        /**
         * Puts a new entry to the {@code members} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param member The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putMember(java.lang.String key, MemberValue member) {
            this.members.asTransient().put(key, member);
            return this;
        }

        /**
         * Returns a new instance of {@link Annotation}
         * 
         * @return A new instance of {@link Annotation}
         */
        public Annotation build() {
            return new Annotation(this);
        }
    }
}
