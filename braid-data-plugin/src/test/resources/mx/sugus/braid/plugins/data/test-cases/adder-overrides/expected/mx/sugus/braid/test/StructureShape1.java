package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>A simple structure shape one</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureShape1 {
    private final String name;
    private final Integer intValue;

    private StructureShape1(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.intValue = builder.intValue;
    }

    public String name() {
        return this.name;
    }

    public Integer intValue() {
        return this.intValue;
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
        StructureShape1 that = (StructureShape1) obj;
        return this.name.equals(that.name)
            && Objects.equals(this.intValue, that.intValue);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + name.hashCode();
        hashCode = 31 * hashCode + (intValue != null ? intValue.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "StructureShape1{"
            + "name: " + name
            + ", intValue: " + intValue + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * <p>Creates a new structure with the given value.</p>
     */
    public static StructureShape1 from(String stringValue, Integer intValue) {
        return builder().name(stringValue).intValue(intValue).build();
    }

    public static final class Builder {
        private String name;
        private Integer intValue;

        Builder() {
        }

        Builder(StructureShape1 data) {
            this.name = data.name;
            this.intValue = data.intValue;
        }

        /**
         * <p>Sets the value for <code>name</code></p>
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * <p>Sets the value for <code>intValue</code></p>
         */
        public Builder intValue(Integer intValue) {
            this.intValue = intValue;
            return this;
        }

        public StructureShape1 build() {
            return new StructureShape1(this);
        }
    }
}
