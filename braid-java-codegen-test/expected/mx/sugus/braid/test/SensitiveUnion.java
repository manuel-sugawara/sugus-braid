package mx.sugus.braid.test;

import java.util.NoSuchElementException;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
@SuppressWarnings("unchecked")
public final class SensitiveUnion {
    private final Object value;
    private final Type type;

    private SensitiveUnion(Builder builder) {
        this.value = builder.getValue();
        this.type = builder.type;
    }

    public String stringSecretMember() {
        if (this.type == Type.STRING_SECRET_MEMBER) {
            return (String) this.value;
        }
        throw new NoSuchElementException("Union element `stringSecretMember` not set, currently set `" + this.type + "`");
    }

    public Integer intSecretMember() {
        if (this.type == Type.INT_SECRET_MEMBER) {
            return (Integer) this.value;
        }
        throw new NoSuchElementException("Union element `intSecretMember` not set, currently set `" + this.type + "`");
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
        if (!(other instanceof SensitiveUnion)) {
            return false;
        }
        SensitiveUnion that = (SensitiveUnion) other;
        return this.type == that.type && this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return this.type.hashCode() + 31 * this.value.hashCode();
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

    public enum Type {
        STRING_SECRET_MEMBER("stringSecretMember"),
        INT_SECRET_MEMBER("intSecretMember"),
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

        Builder(SensitiveUnion data) {
            this.type = data.type;
            this.value = data.value;
        }

        /**
         * <p>Sets the value for <code>stringSecretMember</code></p>
         */
        public Builder stringSecretMember(String stringSecretMember) {
            this.type = Type.STRING_SECRET_MEMBER;
            this.value = stringSecretMember;
            return this;
        }

        /**
         * <p>Sets the value for <code>intSecretMember</code></p>
         */
        public Builder intSecretMember(Integer intSecretMember) {
            this.type = Type.INT_SECRET_MEMBER;
            this.value = intSecretMember;
            return this;
        }

        Object getValue() {
            return this.value;
        }

        public SensitiveUnion build() {
            return new SensitiveUnion(this);
        }
    }
}
