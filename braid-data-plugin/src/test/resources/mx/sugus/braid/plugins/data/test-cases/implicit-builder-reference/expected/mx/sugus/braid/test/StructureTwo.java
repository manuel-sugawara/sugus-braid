package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.AbstractBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureTwo {
    private final Float floatMember;
    private final String stringMember;

    private StructureTwo(Builder builder) {
        this.floatMember = builder.floatMember;
        this.stringMember = builder.stringMember;
    }

    public Float floatMember() {
        return this.floatMember;
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
        return Objects.equals(this.floatMember, that.floatMember)
            && Objects.equals(this.stringMember, that.stringMember);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (floatMember != null ? floatMember.hashCode() : 0);
        hashCode = 31 * hashCode + (stringMember != null ? stringMember.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "StructureTwo{"
            + "floatMember: " + floatMember
            + ", stringMember: " + stringMember + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Float floatMember;
        private String stringMember;

        Builder() {
        }

        Builder(StructureTwo data) {
            this.floatMember = data.floatMember;
            this.stringMember = data.stringMember;
        }

        /**
         * <p>Sets the value for <code>floatMember</code></p>
         */
        public Builder floatMember(Float floatMember) {
            this.floatMember = floatMember;
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

    public static class StructureTwoBuilderReference extends AbstractBuilderReference<StructureTwo, Builder> {

        StructureTwoBuilderReference(StructureTwo source) {
            super(source);
        }

        @Override
        protected Builder emptyTransient() {
            return StructureTwo.builder();
        }

        @Override
        protected StructureTwo transientToPersistent(Builder builder) {
            return builder.build();
        }

        @Override
        protected Builder persistentToTransient(StructureTwo source) {
            return source.toBuilder();
        }

        @Override
        protected Builder clearTransient(Builder builder) {
            return StructureTwo.builder();
        }

        public static StructureTwoBuilderReference from(StructureTwo source) {
            return new StructureTwoBuilderReference(source);
        }
    }
}
