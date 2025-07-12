package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureOne {
    private final Integer intMember;

    private StructureOne(Builder builder) {
        this.intMember = builder.intMember;
    }

    /**
     * 
     * @return The value of the {@code intMember} member
     */
    public Integer intMember() {
        return this.intMember;
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
        StructureOne that = (StructureOne) obj;
        return Objects.equals(this.intMember, that.intMember);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (intMember != null ? intMember.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "StructureOne{"
            + "intMember: " + intMember + "}";
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
     * A class to build instances of StructureOne
     */
    public static final class Builder {
        private Integer intMember;

        Builder() {
        }

        Builder(StructureOne data) {
            this.intMember = data.intMember;
        }

        /**
         * Sets the value for {@code intMember}.
         * 
         * @param intMember The value to be set.
         * @return This instance for chain calling.
         */
        public Builder intMember(Integer intMember) {
            this.intMember = intMember;
            return this;
        }

        /**
         * Returns a new instance of {@link StructureOne}
         * 
         * @return A new instance of {@link StructureOne}
         */
        public StructureOne build() {
            return new StructureOne(this);
        }
    }
}
