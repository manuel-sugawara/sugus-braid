package mx.sugus.braid.jsyntax;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a Java enum value.</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class EnumConstant implements SyntaxNode {
    private final Javadoc javadoc;
    private final String name;
    private final EnumBody body;
    private int _hashCode = 0;

    private EnumConstant(Builder builder) {
        this.javadoc = builder.javadoc;
        this.name = Objects.requireNonNull(builder.name, "name");
        this.body = builder.body;
    }

    /**
     * <p>The javadoc for the enum constant.</p>
     */
    public Javadoc javadoc() {
        return this.javadoc;
    }

    /**
     * <p>The name for the constant.</p>
     */
    public String name() {
        return this.name;
    }

    /**
     * <p>An optional body for the constant.</p>
     */
    public EnumBody body() {
        return this.body;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitEnumConstant(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EnumConstant that = (EnumConstant) obj;
        return Objects.equals(this.javadoc, that.javadoc)
            && this.name.equals(that.name)
            && Objects.equals(this.body, that.body);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + (javadoc != null ? javadoc.hashCode() : 0);
            hashCode = 31 * hashCode + name.hashCode();
            hashCode = 31 * hashCode + (body != null ? body.hashCode() : 0);
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "EnumConstant{"
            + "javadoc: " + javadoc
            + ", name: " + name
            + ", body: " + body + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Javadoc javadoc;
        private String name;
        private EnumBody body;

        Builder() {
        }

        Builder(EnumConstant data) {
            this.javadoc = data.javadoc;
            this.name = data.name;
            this.body = data.body;
        }

        /**
         * <p>Sets the value for <code>javadoc</code></p>
         * <p>The javadoc for the enum constant.</p>
         */
        public Builder javadoc(Javadoc javadoc) {
            this.javadoc = javadoc;
            return this;
        }

        public Builder javadoc(String format, Object... args) {
            this.javadoc = CodeBlock.from(format, args);
            return this;
        }

        /**
         * <p>Sets the value for <code>name</code></p>
         * <p>The name for the constant.</p>
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * <p>Sets the value for <code>body</code></p>
         * <p>An optional body for the constant.</p>
         */
        public Builder body(EnumBody body) {
            this.body = body;
            return this;
        }

        public EnumConstant build() {
            return new EnumConstant(this);
        }
    }
}
