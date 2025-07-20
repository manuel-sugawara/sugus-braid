package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
@SuppressWarnings("unchecked")
public abstract class SensitiveUnion {

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
    public abstract <T extends SensitiveUnion> T asMember(Class<T> memberType);

    public enum VariantTag {
        STRING_SECRET_MEMBER("stringSecretMember"),
        INT_SECRET_MEMBER("intSecretMember"),
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

    @Generated("mx.sugus.braid.plugins.data#DataPlugin")
    public static final class StringSecretMemberMember extends SensitiveUnion {
        private final String stringSecretMember;

        private StringSecretMemberMember(String stringSecretMember) {
            this.stringSecretMember = Objects.requireNonNull(stringSecretMember, "stringSecretMember");
        }

        /**
         * 
         * @return The value of the {@code stringSecretMember} member
         */
        public String stringSecretMember() {
            return this.stringSecretMember;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.stringSecretMember;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.STRING_SECRET_MEMBER;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T extends SensitiveUnion> T asMember(Class<T> memberType) {
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
            StringSecretMemberMember that = (StringSecretMemberMember) other;
            return this.stringSecretMember.equals(that.stringSecretMember);
        }

        @Override
        public int hashCode() {
            return this.stringSecretMember.hashCode();
        }
    }

    @Generated("mx.sugus.braid.plugins.data#DataPlugin")
    public static final class IntSecretMemberMember extends SensitiveUnion {
        private final int intSecretMember;

        private IntSecretMemberMember(int intSecretMember) {
            this.intSecretMember = intSecretMember;
        }

        /**
         * 
         * @return The value of the {@code intSecretMember} member
         */
        public int intSecretMember() {
            return this.intSecretMember;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) (Object) this.intSecretMember;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.INT_SECRET_MEMBER;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T extends SensitiveUnion> T asMember(Class<T> memberType) {
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
            IntSecretMemberMember that = (IntSecretMemberMember) other;
            return this.intSecretMember == that.intSecretMember;
        }

        @Override
        public int hashCode() {
            return intSecretMember;
        }
    }

    /**
     * Unknown variant type.
     */
    @Generated("mx.sugus.braid.plugins.data#DataPlugin")
    public static final class $UnknownVariant extends SensitiveUnion {
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
        public <T extends SensitiveUnion> T asMember(Class<T> memberType) {
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

        Builder(SensitiveUnion data) {
            this.variantTag = data.variantTag();
            this.variantValue = data.variantValue();
        }

        /**
         * Sets the value for {@code stringSecretMember}
         */
        public Builder stringSecretMember(String stringSecretMember) {
            this.variantTag = VariantTag.STRING_SECRET_MEMBER;
            this.variantValue = Objects.requireNonNull(stringSecretMember);
            return this;
        }

        /**
         * Sets the value for {@code intSecretMember}
         */
        public Builder intSecretMember(int intSecretMember) {
            this.variantTag = VariantTag.INT_SECRET_MEMBER;
            this.variantValue = Objects.requireNonNull(intSecretMember);
            return this;
        }

        Object getValue() {
            return this.variantValue;
        }

        public SensitiveUnion build() {
            switch (this.variantTag) {
                case STRING_SECRET_MEMBER:
                    return new StringSecretMemberMember((String) getValue());
                case INT_SECRET_MEMBER:
                    return new IntSecretMemberMember((int) getValue());
                default:
                    if (this.variantValue == null) {
                        throw new NullPointerException("no value set");
                    }
                    return new $UnknownVariant((String) this.variantValue);
            }
        }
    }
}
