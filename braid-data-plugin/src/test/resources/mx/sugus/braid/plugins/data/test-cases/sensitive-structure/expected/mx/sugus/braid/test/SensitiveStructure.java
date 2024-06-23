package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class SensitiveStructure {
    private final String stringSecretMember;
    private final Integer intSecretMember;

    private SensitiveStructure(Builder builder) {
        this.stringSecretMember = builder.stringSecretMember;
        this.intSecretMember = builder.intSecretMember;
    }

    public String stringSecretMember() {
        return this.stringSecretMember;
    }

    public Integer intSecretMember() {
        return this.intSecretMember;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SensitiveStructure that = (SensitiveStructure) obj;
        return Objects.equals(this.stringSecretMember, that.stringSecretMember)
            && Objects.equals(this.intSecretMember, that.intSecretMember);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (stringSecretMember != null ? stringSecretMember.hashCode() : 0);
        hashCode = 31 * hashCode + (intSecretMember != null ? intSecretMember.hashCode() : 0);
        return hashCode;
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

    public static final class Builder {
        private String stringSecretMember;
        private Integer intSecretMember;

        Builder() {
        }

        Builder(SensitiveStructure data) {
            this.stringSecretMember = data.stringSecretMember;
            this.intSecretMember = data.intSecretMember;
        }

        /**
         * <p>Sets the value for <code>stringSecretMember</code></p>
         */
        public Builder stringSecretMember(String stringSecretMember) {
            this.stringSecretMember = stringSecretMember;
            return this;
        }

        /**
         * <p>Sets the value for <code>intSecretMember</code></p>
         */
        public Builder intSecretMember(Integer intSecretMember) {
            this.intSecretMember = intSecretMember;
            return this;
        }

        public SensitiveStructure build() {
            return new SensitiveStructure(this);
        }
    }
}
