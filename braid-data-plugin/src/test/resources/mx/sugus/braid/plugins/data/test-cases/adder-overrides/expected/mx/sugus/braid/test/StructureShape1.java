package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * A simple structure shape one
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureShape1 {
    private final String name;
    private final Integer intValue;

    private StructureShape1(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.intValue = builder.intValue;
    }

    /**
     * 
     * @return The value of the {@code name} member
     */
    public String name() {
        return this.name;
    }

    /**
     * 
     * @return The value of the {@code intValue} member
     */
    public Integer intValue() {
        return this.intValue;
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
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new structure with the given value.
     */
    public static StructureShape1 from(String stringValue, Integer intValue) {
        return builder().name(stringValue).intValue(intValue).build();
    }

    /**
     * A class to build instances of StructureShape1
     */
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
         * Sets the value for {@code name}.
         * 
         * @param name The value to be set.
         * @return This instance for chain calling.
         */
        public Builder name(String name) {
            this.name = Objects.requireNonNull(name, "name");
            return this;
        }

        /**
         * Sets the value for {@code intValue}.
         * 
         * @param intValue The value to be set.
         * @return This instance for chain calling.
         */
        public Builder intValue(Integer intValue) {
            this.intValue = intValue;
            return this;
        }

        /**
         * Returns a new instance of {@link StructureShape1}
         * 
         * @return A new instance of {@link StructureShape1}
         */
        public StructureShape1 build() {
            return new StructureShape1(this);
        }
    }
}
