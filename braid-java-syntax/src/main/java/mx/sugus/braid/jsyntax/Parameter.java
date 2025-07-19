package mx.sugus.braid.jsyntax;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents a parameter of a method.
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class Parameter implements SyntaxNode {
    private final String name;
    private final TypeName type;
    private final boolean varargs;
    private int _hashCode = 0;

    private Parameter(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.type = Objects.requireNonNull(builder.type, "type");
        this.varargs = builder.varargs;
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
     * @return The value of the {@code type} member
     */
    public TypeName type() {
        return this.type;
    }

    /**
     * 
     * @return The value of the {@code varargs} member
     */
    public boolean varargs() {
        return this.varargs;
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
        return visitor.visitParameter(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Parameter that = (Parameter) obj;
        return this.name.equals(that.name)
            && this.type.equals(that.type)
            && this.varargs == that.varargs;
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + name.hashCode();
            hashCode = 31 * hashCode + type.hashCode();
            hashCode = 31 * hashCode + Boolean.hashCode(varargs);
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "Parameter{"
            + "name: " + name
            + ", type: " + type
            + ", varargs: " + varargs + "}";
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
     * A class to build instances of Parameter
     */
    public static final class Builder implements SyntaxNode.Builder {
        private String name;
        private TypeName type;
        private boolean varargs;

        Builder() {
            this.varargs = false;
        }

        Builder(Parameter data) {
            this.name = data.name;
            this.type = data.type;
            this.varargs = data.varargs;
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
         * Sets the value for {@code type}.
         * 
         * @param type The value to be set.
         * @return This instance for chain calling.
         */
        public Builder type(TypeName type) {
            this.type = Objects.requireNonNull(type, "type");
            return this;
        }

        public Builder type(Class<?> clazz) {
            this.type = TypeName.from(clazz);
            return this;
        }

        /**
         * Sets the value for {@code varargs}.
         * 
         * @param varargs The value to be set.
         * @return This instance for chain calling.
         */
        public Builder varargs(boolean varargs) {
            this.varargs = Objects.requireNonNull(varargs, "varargs");
            return this;
        }

        /**
         * Returns a new instance of {@link Parameter}
         * 
         * @return A new instance of {@link Parameter}
         */
        public Parameter build() {
            return new Parameter(this);
        }
    }
}
