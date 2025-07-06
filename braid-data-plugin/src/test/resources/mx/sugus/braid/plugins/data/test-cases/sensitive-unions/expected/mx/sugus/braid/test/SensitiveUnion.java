package mx.sugus.braid.test;

import java.util.NoSuchElementException;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
@SuppressWarnings("unchecked")
public final class SensitiveUnion {
    private final Object variantValue;
    private final VariantTag variantTag;

    private SensitiveUnion(Builder builder) {
        this.variantValue = builder.getValue();
        this.variantTag = builder.variantTag;
    }

    public String stringSecretMember() {
        if (this.variantTag == VariantTag.STRING_SECRET_MEMBER) {
            return (String) this.variantValue;
        }
        throw new NoSuchElementException("Union element `stringSecretMember` not set, currently set `" + this.variantTag + "`");
    }

    public Integer intSecretMember() {
        if (this.variantTag == VariantTag.INT_SECRET_MEMBER) {
            return (Integer) this.variantValue;
        }
        throw new NoSuchElementException("Union element `intSecretMember` not set, currently set `" + this.variantTag + "`");
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
        if (!(other instanceof SensitiveUnion)) {
            return false;
        }
        SensitiveUnion that = (SensitiveUnion) other;
        return this.variantTag == that.variantTag && this.variantValue.equals(that.variantValue);
    }

    @Override
    public int hashCode() {
        return this.variantTag.hashCode() + 31 * this.variantValue.hashCode();
    }

    @Override
    public String toString() {
        return "<*** REDACTED ***>";
    }

    /**
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

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

    public static final class Builder {
        private Object variantValue;
        private VariantTag variantTag;

        Builder() {
            this.variantTag = null;
            this.variantValue = VariantTag.UNKNOWN_TO_VERSION;
        }

        Builder(SensitiveUnion data) {
            this.variantTag = data.variantTag;
            this.variantValue = data.variantValue;
        }

        /**
         * Sets the value for {@code stringSecretMember}
         */
        public Builder stringSecretMember(String stringSecretMember) {
            this.variantTag = VariantTag.STRING_SECRET_MEMBER;
            this.variantValue = stringSecretMember;
            return this;
        }

        /**
         * Sets the value for {@code intSecretMember}
         */
        public Builder intSecretMember(Integer intSecretMember) {
            this.variantTag = VariantTag.INT_SECRET_MEMBER;
            this.variantValue = intSecretMember;
            return this;
        }

        Object getValue() {
            return this.variantValue;
        }

        public SensitiveUnion build() {
            return new SensitiveUnion(this);
        }
    }
}
