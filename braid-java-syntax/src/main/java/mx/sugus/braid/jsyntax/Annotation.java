package mx.sugus.braid.jsyntax;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represent an <code>annotation</code>.</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class Annotation implements SyntaxNode {
    private final ClassName type;
    private final String member;
    private final CodeBlock value;
    private int _hashCode = 0;

    private Annotation(Builder builder) {
        this.type = Objects.requireNonNull(builder.type, "type");
        this.member = builder.member;
        this.value = builder.value;
    }

    public ClassName type() {
        return this.type;
    }

    public String member() {
        return this.member;
    }

    public CodeBlock value() {
        return this.value;
    }

    /**
     * Returns a new builder to modify a copy of this instance
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
            && Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + type.hashCode();
            hashCode = 31 * hashCode + (member != null ? member.hashCode() : 0);
            hashCode = 31 * hashCode + (value != null ? value.hashCode() : 0);
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "Annotation{"
            + "type: " + type
            + ", member: " + member
            + ", value: " + value + "}";
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

        Builder() {
        }

        Builder(Annotation data) {
            this.type = data.type;
            this.member = data.member;
            this.value = data.value;
        }

        /**
         * <p>Sets the value for <code>type</code></p>
         */
        public Builder type(ClassName type) {
            this.type = type;
            return this;
        }

        /**
         * <p>Sets the value for <code>member</code></p>
         */
        public Builder member(String member) {
            this.member = member;
            return this;
        }

        /**
         * <p>Sets the value for <code>value</code></p>
         */
        public Builder value(CodeBlock value) {
            this.value = value;
            return this;
        }

        public Annotation build() {
            return new Annotation(this);
        }
    }
}
