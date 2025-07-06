package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.NoSuchElementException;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Possible values for an annotation member, either an expression or
 * a list of expressions. Annotation members are missing for the time
 * being.
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
@SuppressWarnings("unchecked")
public final class MemberValue {
    private final Object variantValue;
    private final VariantTag variantTag;

    private MemberValue(Builder builder) {
        this.variantValue = builder.getValue();
        this.variantTag = builder.variantTag;
    }

    /**
     * An expression member, equivalent to the {@code ConditionalExpression} production
     * in the java spec.
     */
    public Expression expression() {
        if (this.variantTag == VariantTag.EXPRESSION) {
            return (Expression) this.variantValue;
        }
        throw new NoSuchElementException("Union element `expression` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * An array initializer member, equivalent to the {@code ElementValueArrayInitializer}
     * production in the java spec.
     */
    public List<Expression> arrayExpression() {
        if (this.variantTag == VariantTag.ARRAY_EXPRESSION) {
            return (List<Expression>) this.variantValue;
        }
        throw new NoSuchElementException("Union element `arrayExpression` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * Returns the enum value representing which member of this object is populated.
     * <p>
     * This will be {@link Type#UNKNOWN_TO_VERSION} if no members are set.
     * 
     * @return The enum value representing which member of this object is populated
     */
    public VariantTag variantTag() {
        return this.variantTag;
    }

    /**
     * Returns the untyped value of the union.
     * <p>
     * Use {@link #type()} to get the member currently set.
     * 
     * @return The untyped value of the union.
     */
    public Object variantValue() {
        return this.variantValue;
    }

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
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
        return this.variantTag == that.variantTag && this.variantValue.equals(that.variantValue);
    }

    @Override
    public int hashCode() {
        return this.variantTag.hashCode() + 31 * this.variantValue.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("MemberValue{variantTag: ");
        buf.append(this.variantTag);
        switch (this.variantTag) {
            case EXPRESSION:
                buf.append(", expression: ").append(this.variantValue);
                break;
            case ARRAY_EXPRESSION:
                buf.append(", arrayExpression: ").append(this.variantValue);
                break;
        }
        return buf.append("}").toString();
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
     * Creates a new {@code MemberValue} for the expression variant
     */
    public static MemberValue forExpression(String format, Object... args) {
        return builder().expression(CodeBlock.from(format, args)).build();
    }

    /**
     * Creates a new {@code MemberValue} for the expression variant
     */
    public static MemberValue forExpression(CodeBlock codeBlock) {
        return builder().expression(codeBlock).build();
    }

    /**
     * Creates a new {@code MemberValue} for the array expression variant
     */
    public static MemberValue forArrayExpression(CodeBlock... values) {
        return builder().arrayExpression(java.util.Arrays.asList(values)).build();
    }

    public enum VariantTag {
        EXPRESSION("expression"),
        ARRAY_EXPRESSION("arrayExpression"),
        UNKNOWN_TO_VERSION(null);

        private final String value;

        VariantTag(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static final class Builder {
        private Object variantValue;
        private VariantTag variantTag;

        Builder() {
            this.variantTag = null;
            this.variantValue = VariantTag.UNKNOWN_TO_VERSION;
        }

        Builder(MemberValue data) {
            this.variantTag = data.variantTag;
            switch (data.variantTag) {
                case ARRAY_EXPRESSION:
                    this.variantValue = CollectionBuilderReference.fromPersistentList(data.arrayExpression());
                    break;
                default:
                    this.variantValue = data.variantValue;
            }
        }

        /**
         * Sets the value for {@code expression}
         * <p>
         * An expression member, equivalent to the {@code ConditionalExpression} production
         * in the java spec.
         */
        public Builder expression(Expression expression) {
            this.variantTag = VariantTag.EXPRESSION;
            this.variantValue = expression;
            return this;
        }

        private CollectionBuilderReference<List<Expression>> arrayExpression() {
            if (this.variantTag != VariantTag.ARRAY_EXPRESSION) {
                this.variantTag = VariantTag.ARRAY_EXPRESSION;
                CollectionBuilderReference<List<Expression>> arrayExpression = CollectionBuilderReference.forList();
                this.variantValue = arrayExpression;
                return arrayExpression;
            } else {
                return (CollectionBuilderReference<List<Expression>>) this.variantValue;
            }
        }

        /**
         * Sets the value for {@code arrayExpression}
         * <p>
         * An array initializer member, equivalent to the {@code ElementValueArrayInitializer}
         * production in the java spec.
         */
        public Builder arrayExpression(List<Expression> arrayExpression) {
            CollectionBuilderReference<List<Expression>> tmp = arrayExpression();
            tmp.clear();
            tmp.asTransient().addAll(arrayExpression);
            return this;
        }

        /**
         * Adds a single value for {@code arrayExpression}
         */
        public Builder addArrayExpression(Expression arrayExpression) {
            arrayExpression().asTransient().add(arrayExpression);
            return this;
        }

        Object getValue() {
            switch (this.variantTag) {
                case EXPRESSION:
                    return this.variantValue;
                case ARRAY_EXPRESSION:
                    return arrayExpression().asPersistent();
                default:
                    return this.variantValue;
            }
        }

        public MemberValue build() {
            return new MemberValue(this);
        }
    }
}
