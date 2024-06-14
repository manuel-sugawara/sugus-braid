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

    public Integer intMember() {
        return this.intMember;
    }

    public String stringMember() {
        return this.stringMember;
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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

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
         * <p>Sets the value for <code>intMember</code></p>
         */
        public Builder intMember(Integer intMember) {
            this.intMember = intMember;
            return this;
        }

        /**
         * <p>Sets the value for <code>stringMember</code></p>
         */
        public Builder stringMember(String stringMember) {
            this.stringMember = stringMember;
            return this;
        }

        public StructureTwo build() {
            return new StructureTwo(this);
        }
    }
}
