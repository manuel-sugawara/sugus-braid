package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureTwo {
    private final Integer intMember;
    private final String stringMember;

    private StructureTwo(Builder builder) {
        this.intMember = builder.intMember;
        this.stringMember = builder.stringMember;
    }

    /**
     * 
     * @return The value of the {@code intMember} member
     */
    public Integer intMember() {
        return this.intMember;
    }

    /**
     * 
     * @return The value of the {@code stringMember} member
     */
    public String stringMember() {
        return this.stringMember;
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
        StructureTwo that = (StructureTwo) obj;
        return Objects.equals(this.intMember, that.intMember)
            && Objects.equals(this.stringMember, that.stringMember);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (intMember != null ? intMember.hashCode() : 0);
        hashCode = 31 * hashCode + (stringMember != null ? stringMember.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "StructureTwo{"
            + "intMember: " + intMember
            + ", stringMember: " + stringMember + "}";
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
     * A class to build instances of StructureTwo
     */
    public static final class Builder {
        private Integer intMember;
        private String stringMember;

        Builder() {
        }

        Builder(StructureTwo data) {
            this.intMember = data.intMember;
            this.stringMember = data.stringMember;
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
         * Sets the value for {@code stringMember}.
         * 
         * @param stringMember The value to be set.
         * @return This instance for chain calling.
         */
        public Builder stringMember(String stringMember) {
            this.stringMember = stringMember;
            return this;
        }

        /**
         * Returns a new instance of {@link StructureTwo}
         * 
         * @return A new instance of {@link StructureTwo}
         */
        public StructureTwo build() {
            return new StructureTwo(this);
        }
    }
}
