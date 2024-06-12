package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureOne {
    private final Integer intMember;

    private StructureOne(Builder builder) {
        this.intMember = builder.intMember;
    }

    public Integer intMember() {
        return this.intMember;
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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer intMember;

        Builder() {
        }

        Builder(StructureOne data) {
            this.intMember = data.intMember;
        }

        /**
         * <p>Sets the value for <code>intMember</code></p>
         */
        public Builder intMember(Integer intMember) {
            this.intMember = intMember;
            return this;
        }

        public StructureOne build() {
            return new StructureOne(this);
        }
    }
}
