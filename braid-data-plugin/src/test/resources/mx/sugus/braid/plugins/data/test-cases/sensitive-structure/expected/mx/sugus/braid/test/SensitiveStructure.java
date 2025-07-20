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

    /**
     * 
     * @return The value of the {@code stringSecretMember} member
     */
    public String stringSecretMember() {
        return this.stringSecretMember;
    }

    /**
     * 
     * @return The value of the {@code intSecretMember} member
     */
    public Integer intSecretMember() {
        return this.intSecretMember;
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
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        SensitiveStructure that = (SensitiveStructure) other;
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
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A class to build instances of SensitiveStructure
     */
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
         * Sets the value for {@code stringSecretMember}.
         * 
         * @param stringSecretMember The value to be set.
         * @return This instance for chain calling.
         */
        public Builder stringSecretMember(String stringSecretMember) {
            this.stringSecretMember = stringSecretMember;
            return this;
        }

        /**
         * Sets the value for {@code intSecretMember}.
         * 
         * @param intSecretMember The value to be set.
         * @return This instance for chain calling.
         */
        public Builder intSecretMember(Integer intSecretMember) {
            this.intSecretMember = intSecretMember;
            return this;
        }

        /**
         * Returns a new instance of {@link SensitiveStructure}
         * 
         * @return A new instance of {@link SensitiveStructure}
         */
        public SensitiveStructure build() {
            return new SensitiveStructure(this);
        }
    }
}
