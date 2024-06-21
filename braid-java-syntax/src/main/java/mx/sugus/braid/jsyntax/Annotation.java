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
    private final String member;
    private final CodeBlock value;
    private final Map<String, MemberValue> members;
    private int _hashCode = 0;

    private Annotation(Builder builder) {
        this.type = Objects.requireNonNull(builder.type, "type");
        this.member = builder.member;
        this.value = builder.value;
        this.members = Objects.requireNonNull(builder.members.asPersistent(), "members");
    }

    /**
     * <p>The class for the annotation</p>
     */
    public ClassName type() {
        return this.type;
    }

    /**
     * <p>A single member name</p>
     */
    public String member() {
        return this.member;
    }

    /**
     * <p>A single member value</p>
     */
    public CodeBlock value() {
        return this.value;
    }

    /**
     * <p>The members of the annotation</p>
     */
    public Map<String, MemberValue> members() {
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
            && Objects.equals(this.member, that.member)
            && Objects.equals(this.value, that.value)
            && this.members.equals(that.members);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + type.hashCode();
            hashCode = 31 * hashCode + (member != null ? member.hashCode() : 0);
            hashCode = 31 * hashCode + (value != null ? value.hashCode() : 0);
            hashCode = 31 * hashCode + members.hashCode();
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "Annotation{"
            + "type: " + type
            + ", member: " + member
            + ", value: " + value
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

    public static final class Builder {
        private ClassName type;
        private String member;
        private CodeBlock value;
        private CollectionBuilderReference<Map<String, MemberValue>> members;

        Builder() {
            this.members = CollectionBuilderReference.forOrderedMap();
        }

        Builder(Annotation data) {
            this.type = data.type;
            this.member = data.member;
            this.value = data.value;
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
         * <p>Sets the value for <code>member</code></p>
         * <p>A single member name</p>
         */
        public Builder member(String member) {
            this.member = member;
            return this;
        }

        /**
         * <p>Sets the value for <code>value</code></p>
         * <p>A single member value</p>
         */
        public Builder value(CodeBlock value) {
            this.value = value;
            return this;
        }

        /**
         * <p>Sets the value for <code>members</code></p>
         * <p>The members of the annotation</p>
         */
        public Builder members(Map<String, MemberValue> members) {
            this.members.clear();
            this.members.asTransient().putAll(members);
            return this;
        }

        public Builder putMember(String key, MemberValue member) {
            this.members.asTransient().put(key, member);
            return this;
        }

        public Annotation build() {
            return new Annotation(this);
        }
    }
}
