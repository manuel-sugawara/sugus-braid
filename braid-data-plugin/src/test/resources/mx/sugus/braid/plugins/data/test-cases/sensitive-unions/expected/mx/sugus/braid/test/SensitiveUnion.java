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
     * <p>Returns an enum value representing which member of this object is populated.</p>
     * <p>This will be {@link Type#UNKNOWN_TO_VERSION} if no members are set.</p>
     */
    public VariantTag variantTag() {
        return this.variantTag;
    }

    /**
     * <p>Returns the untyped value of the union.</p>
     * <p>Use {@link #type()} to get the member currently set.</p>
     */
    public Object variantValue() {
        return this.variantValue;
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
     * <p>Creates a new builder</p>
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
         * <p>Sets the value for <code>stringSecretMember</code></p>
         */
        public Builder stringSecretMember(String stringSecretMember) {
            this.variantTag = VariantTag.STRING_SECRET_MEMBER;
            this.variantValue = stringSecretMember;
            return this;
        }

        /**
         * <p>Sets the value for <code>intSecretMember</code></p>
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
