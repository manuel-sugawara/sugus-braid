package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
// NOTE: Enum is "reserved" (as in the java.lang package) and thus it gets escaped.
public final class EnumStructure {
    // NOTE: int is reserved, thus it gets escaped
    private final Integer anInt;
    private final String aVoid;

    private EnumStructure(Builder builder) {
        this.anInt = builder.anInt;
        this.aVoid = builder.aVoid;
    }

    public Integer anInt() {
        return this.anInt;
    }

    public String aVoid() {
        return this.aVoid;
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
        EnumStructure that = (EnumStructure) obj;
        return java.util.Objects.equals(this.anInt, that.anInt)
            && java.util.Objects.equals(this.aVoid, that.aVoid);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (anInt != null ? anInt.hashCode() : 0);
        hashCode = 31 * hashCode + (aVoid != null ? aVoid.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "Enum{"
            + "int: " + anInt
            + ", void: " + aVoid + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer anInt;
        private String aVoid;

        Builder() {
        }

        Builder(EnumStructure data) {
            this.anInt = data.anInt;
            this.aVoid = data.aVoid;
        }

        /**
         * <p>Sets the value for <code>anInt</code></p>
         */
        public Builder anInt(Integer anInt) {
            this.anInt = anInt;
            return this;
        }

        /**
         * <p>Sets the value for <code>aVoid</code></p>
         */
        public Builder aVoid(String aVoid) {
            this.aVoid = aVoid;
            return this;
        }

        public EnumStructure build() {
            return new EnumStructure(this);
        }
    }
}
