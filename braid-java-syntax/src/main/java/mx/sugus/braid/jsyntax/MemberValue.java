package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.NoSuchElementException;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Possible values for an annotation member, either an expression or
 * a list of expressions. Annotation members are missing for the time
 * being.</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
@SuppressWarnings("unchecked")
public final class MemberValue {
    private final Object value;
    private final Type type;

    private MemberValue(Builder builder) {
        this.value = builder.getValue();
        this.type = builder.type;
    }

    /**
     * <p>An expression member, equivalent to the <code>ConditionalExpression</code> production
     * in the java spec.</p>
     */
    public Expression expression() {
        if (this.type == Type.EXPRESSION) {
            return (Expression) this.value;
        }
        throw new NoSuchElementException("Union element `expression` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>An array initializer member, equivalent to the <code>ElementValueArrayInitializer</code>
     * production in the java spec.</p>
     */
    public List<Expression> arrayExpression() {
        if (this.type == Type.ARRAY_EXPRESSION) {
            return (List<Expression>) this.value;
        }
        throw new NoSuchElementException("Union element `arrayExpression` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>Returns an enum value representing which member of this object is populated.</p>
     * <p>This will be {@link Type#UNKNOWN_TO_VERSION} if no members are set.</p>
     */
    public Type type() {
        return this.type;
    }

    /**
     * <p>Returns the untyped value of the union.</p>
     * <p>Use {@link #type()} to get the member currently set.</p>
     */
    public Object value() {
        return this.value;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MemberValue)) {
            return false;
        }
        MemberValue that = (MemberValue) other;
        return this.type == that.type && this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return this.type.hashCode() + 31 * this.value.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("MemberValue{type: ");
        buf.append(this.type);
        switch (this.type) {
            case EXPRESSION:
                buf.append(", expression: ").append(this.value);
                break;
            case ARRAY_EXPRESSION:
                buf.append(", arrayExpression: ").append(this.value);
                break;
        }
        return buf.append("}").toString();
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * <p>Creates a new <code>MemberValue</code> for the expression variant</p>
     */
    public static MemberValue forExpression(String format, Object... args) {
        return builder().expression(CodeBlock.from(format, args)).build();
    }

    /**
     * <p>Creates a new <code>MemberValue</code> for the expression variant</p>
     */
    public static MemberValue forExpression(CodeBlock codeBlock) {
        return builder().expression(codeBlock).build();
    }

    /**
     * <p>Creates a new <code>MemberValue</code> for the array expression variant</p>
     */
    public static MemberValue forArrayExpression(CodeBlock... values) {
        return builder().arrayExpression(java.util.Arrays.asList(values)).build();
    }

    public enum Type {
        EXPRESSION("expression"),
        ARRAY_EXPRESSION("arrayExpression"),
        UNKNOWN_TO_VERSION(null);

        private final String value;

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static final class Builder {
        private Object value;
        private Type type;

        Builder() {
            this.type = null;
            this.value = Type.UNKNOWN_TO_VERSION;
        }

        Builder(MemberValue data) {
            this.type = data.type;
            switch (data.type) {
                case ARRAY_EXPRESSION:
                    this.value = CollectionBuilderReference.fromPersistentList(data.arrayExpression());
                    break;
                default:
                    this.value = data.value;
            }
        }

        /**
         * <p>Sets the value for <code>expression</code></p>
         * <p>An expression member, equivalent to the <code>ConditionalExpression</code> production
         * in the java spec.</p>
         */
        public Builder expression(Expression expression) {
            this.type = Type.EXPRESSION;
            this.value = expression;
            return this;
        }

        private CollectionBuilderReference<List<Expression>> arrayExpression() {
            if (this.type != Type.ARRAY_EXPRESSION) {
                this.type = Type.ARRAY_EXPRESSION;
                CollectionBuilderReference<List<Expression>> arrayExpression = CollectionBuilderReference.forList();
                this.value = arrayExpression;
                return arrayExpression;
            } else {
                return (CollectionBuilderReference<List<Expression>>) this.value;
            }
        }

        /**
         * <p>Sets the value for <code>arrayExpression</code></p>
         * <p>An array initializer member, equivalent to the <code>ElementValueArrayInitializer</code>
         * production in the java spec.</p>
         */
        public Builder arrayExpression(List<Expression> arrayExpression) {
            CollectionBuilderReference<List<Expression>> tmp = arrayExpression();
            tmp.clear();
            tmp.asTransient().addAll(arrayExpression);
            return this;
        }

        /**
         * <p>Adds a single value for <code>arrayExpression</code></p>
         */
        public Builder addArrayExpression(Expression arrayExpression) {
            arrayExpression().asTransient().add(arrayExpression);
            return this;
        }

        Object getValue() {
            switch (this.type) {
                case EXPRESSION:
                    return this.value;
                case ARRAY_EXPRESSION:
                    return arrayExpression().asPersistent();
                default:
                    return this.value;
            }
        }

        public MemberValue build() {
            return new MemberValue(this);
        }
    }
}
