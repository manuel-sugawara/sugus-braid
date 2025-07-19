package mx.sugus.braid.jsyntax;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents a Java enum value.
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
     * The javadoc for the enum constant.
     * 
     * @return The value of the {@code javadoc} member
     */
    public Javadoc javadoc() {
        return this.javadoc;
    }

    /**
     * The name for the constant.
     * 
     * @return The value of the {@code name} member
     */
    public String name() {
        return this.name;
    }

    /**
     * An optional body for the constant.
     * 
     * @return The value of the {@code body} member
     */
    public EnumBody body() {
        return this.body;
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
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A class to build instances of EnumConstant
     */
    public static final class Builder implements SyntaxNode.Builder {
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
         * Sets the value for {@code javadoc}.
         * 
         * @param javadoc The value to be set.
         * @return This instance for chain calling.
         */
        public Builder javadoc(Javadoc javadoc) {
            this.javadoc = javadoc;
            return this;
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
         * Sets the value for {@code body}.
         * 
         * @param body The value to be set.
         * @return This instance for chain calling.
         */
        public Builder body(EnumBody body) {
            this.body = body;
            return this;
        }

        /**
         * Returns a new instance of {@link EnumConstant}
         * 
         * @return A new instance of {@link EnumConstant}
         */
        public EnumConstant build() {
            return new EnumConstant(this);
        }
    }
}
