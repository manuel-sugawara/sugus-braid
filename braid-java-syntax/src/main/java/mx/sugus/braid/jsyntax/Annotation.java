package mx.sugus.braid.jsyntax;

import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represent an <code>annotation</code>.</p>
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
     * <p>The class for the annotation</p>
     */
    public ClassName type() {
        return this.type;
    }

    /**
     * <p>The members of the annotation</p>
     */
    public Map<java.lang.String, MemberValue> members() {
        return this.members;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitAnnotation(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Annotation that = (Annotation) obj;
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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder(ClassName type) {
        return builder().type(type);
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder(Class<?> kclass) {
        return builder().type(ClassName.from(kclass));
    }

    /**
     * <p>Creates a new annotation with a single string member value.</p>
     */
    public static Annotation fromStringValue(Class<?> kclass, String value) {
        return builder().type(ClassName.from(kclass))
                .putMember("value", MemberValue.forExpression(CodeBlock.from("$S", value)))
                .build();
    }

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
         * <p>Sets the value for <code>type</code></p>
         * <p>The class for the annotation</p>
         */
        public Builder type(ClassName type) {
            this.type = type;
            return this;
        }

        /**
         * <p>Sets the value for <code>members</code></p>
         * <p>The members of the annotation</p>
         */
        public Builder members(Map<java.lang.String, MemberValue> members) {
            this.members.clear();
            this.members.asTransient().putAll(members);
            return this;
        }

        public Builder putMember(java.lang.String key, MemberValue member) {
            this.members.asTransient().put(key, member);
            return this;
        }

        public Annotation build() {
            return new Annotation(this);
        }
    }
}
