package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Possible values for an annotation member, either an expression or
 * a list of expressions. Annotation members are missing for the time
 * being.
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
@SuppressWarnings("unchecked")
public abstract class MemberValue {

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    public Builder toBuilder() {
        return new Builder(this);
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

    /**
     * Returns the enum value representing which member of this object is populated.
     * <p>
     * This will be {@link Type#UNKNOWN_TO_VERSION} if no members are set.
     * 
     * @return The enum value representing which member of this object is populated
     */
    public abstract VariantTag variantTag();

    public abstract <T> T variantValue();

    /**
     * Returns the specific member type.
     * 
     * @return The specific member type
     */
    public abstract <T extends MemberValue> T asMember(Class<T> memberType);

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

    /**
     * An expression member, equivalent to the {@code ConditionalExpression} production
     * in the java spec.
     */
    @Generated("mx.sugus.braid.plugins.data#DataPlugin")
    public static final class ExpressionMember extends MemberValue {
        private final Expression expression;

        private ExpressionMember(Expression expression) {
            this.expression = Objects.requireNonNull(expression, "expression");
        }

        /**
         * An expression member, equivalent to the {@code ConditionalExpression} production
         * in the java spec.
         * 
         * @return The value of the {@code expression} member
         */
        public Expression expression() {
            return this.expression;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.expression;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.EXPRESSION;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T extends MemberValue> T asMember(Class<T> memberType) {
            if (memberType != getClass()) {
                throw new ClassCastException("Member of class: " + getClass().getName() + " cannot be casted to: " + memberType.getName());
            }
            return (T) this;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            ExpressionMember that = (ExpressionMember) other;
            return this.expression.equals(that.expression);
        }

        @Override
        public int hashCode() {
            return this.expression.hashCode();
        }
    }

    /**
     * An array initializer member, equivalent to the {@code ElementValueArrayInitializer}
     * production in the java spec.
     */
    @Generated("mx.sugus.braid.plugins.data#DataPlugin")
    public static final class ArrayExpressionMember extends MemberValue {
        private final List<Expression> arrayExpression;

        private ArrayExpressionMember(List<Expression> arrayExpression) {
            this.arrayExpression = Objects.requireNonNull(arrayExpression, "arrayExpression");
        }

        /**
         * An array initializer member, equivalent to the {@code ElementValueArrayInitializer}
         * production in the java spec.
         * 
         * @return The value of the {@code arrayExpression} member
         */
        public List<Expression> arrayExpression() {
            return this.arrayExpression;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.arrayExpression;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.ARRAY_EXPRESSION;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T extends MemberValue> T asMember(Class<T> memberType) {
            if (memberType != getClass()) {
                throw new ClassCastException("Member of class: " + getClass().getName() + " cannot be casted to: " + memberType.getName());
            }
            return (T) this;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            ArrayExpressionMember that = (ArrayExpressionMember) other;
            return this.arrayExpression.equals(that.arrayExpression);
        }

        @Override
        public int hashCode() {
            return this.arrayExpression.hashCode();
        }
    }

    /**
     * Unknown variant type.
     */
    @Generated("mx.sugus.braid.plugins.data#DataPlugin")
    public static final class $UnknownVariant extends MemberValue {
        private final String unknownVariantName;

        private $UnknownVariant(String name) {
            this.unknownVariantName = Objects.requireNonNull(name);
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.UNKNOWN_TO_VERSION;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.unknownVariantName;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T extends MemberValue> T asMember(Class<T> memberType) {
            if (memberType != getClass()) {
                throw new ClassCastException("Member of class: " + getClass().getName() + " cannot be casted to: " + memberType.getName());
            }
            return (T) this;
        }
    }

    public static final class Builder {
        private Object variantValue;
        private VariantTag variantTag;

        Builder() {
            this.variantTag = VariantTag.UNKNOWN_TO_VERSION;
            this.variantValue = null;
        }

        Builder(MemberValue data) {
            this.variantTag = data.variantTag();
            switch (this.variantTag) {
                case ARRAY_EXPRESSION:
                    this.variantValue = CollectionBuilderReference.fromPersistentList(data.variantValue());
                    break;
                default:
                    this.variantValue = data.variantValue();
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
            this.variantValue = Objects.requireNonNull(expression);
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
            tmp.asTransient().addAll(Objects.requireNonNull(arrayExpression));
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
            switch (this.variantTag) {
                case EXPRESSION:
                    return new ExpressionMember((Expression) getValue());
                case ARRAY_EXPRESSION:
                    return new ArrayExpressionMember((List<Expression>) getValue());
                default:
                    if (this.variantValue == null) {
                        throw new NullPointerException("no value set");
                    }
                    return new $UnknownVariant((String) this.variantValue);
            }
        }
    }
}
