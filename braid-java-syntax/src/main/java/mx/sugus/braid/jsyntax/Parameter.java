package mx.sugus.braid.jsyntax;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a parameter of a method.</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class Parameter implements SyntaxNode {
    private final String name;
    private final TypeName type;
    private final Boolean varargs;
    private int _hashCode = 0;

    private Parameter(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.type = Objects.requireNonNull(builder.type, "type");
        this.varargs = Objects.requireNonNull(builder.varargs, "varargs");
    }

    public String name() {
        return this.name;
    }

    public TypeName type() {
        return this.type;
    }

    public Boolean varargs() {
        return this.varargs;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

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
            && this.varargs.equals(that.varargs);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + name.hashCode();
            hashCode = 31 * hashCode + type.hashCode();
            hashCode = 31 * hashCode + varargs.hashCode();
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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private TypeName type;
        private Boolean varargs;

        Builder() {
            this.varargs = false;
        }

        Builder(Parameter data) {
            this.name = data.name;
            this.type = data.type;
            this.varargs = data.varargs;
        }

        /**
         * <p>Sets the value for <code>name</code></p>
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * <p>Sets the value for <code>type</code></p>
         */
        public Builder type(TypeName type) {
            this.type = type;
            return this;
        }

        public Builder type(Class<?> clazz) {
            this.type = TypeName.from(clazz);
            return this;
        }

        /**
         * <p>Sets the value for <code>varargs</code></p>
         */
        public Builder varargs(Boolean varargs) {
            this.varargs = varargs;
            return this;
        }

        public Parameter build() {
            return new Parameter(this);
        }
    }
}
