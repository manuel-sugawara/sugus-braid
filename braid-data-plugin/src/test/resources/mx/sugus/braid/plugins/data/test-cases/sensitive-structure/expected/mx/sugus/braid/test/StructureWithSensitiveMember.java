package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureWithSensitiveMember {
    private final String stringSecretMember;

    private StructureWithSensitiveMember(Builder builder) {
        this.stringSecretMember = builder.stringSecretMember;
    }

    /**
     * 
     * @return The value of the {@code stringSecretMember} member
     */
    public String stringSecretMember() {
        return this.stringSecretMember;
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StructureWithSensitiveMember that = (StructureWithSensitiveMember) obj;
        return Objects.equals(this.stringSecretMember, that.stringSecretMember);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (stringSecretMember != null ? stringSecretMember.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "StructureWithSensitiveMember{"
            + "stringSecretMember: <*** REDACTED ***>" + "}";
    }

    /**
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String stringSecretMember;

        Builder() {
        }

        Builder(StructureWithSensitiveMember data) {
            this.stringSecretMember = data.stringSecretMember;
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
         * Returns a new instance of {@link StructureWithSensitiveMember}
         * 
         * @return A new instance of {@link StructureWithSensitiveMember}
         */
        public StructureWithSensitiveMember build() {
            return new StructureWithSensitiveMember(this);
        }
    }
}
