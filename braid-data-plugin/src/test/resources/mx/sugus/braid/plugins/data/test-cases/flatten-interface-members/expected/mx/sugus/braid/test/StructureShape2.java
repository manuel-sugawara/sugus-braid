package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * A simple structure that implements base
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureShape2 implements StructureBase {
    private final Integer intValue;
    private final String stringValue;

    private StructureShape2(Builder builder) {
        this.intValue = builder.intValue;
        this.stringValue = Objects.requireNonNull(builder.stringValue, "stringValue");
    }

    /**
     * 
     * @return The value of the {@code intValue} member
     */
    public Integer intValue() {
        return this.intValue;
    }

    /**
     * 
     * @return The value of the {@code stringValue} member
     */
    public String stringValue() {
        return this.stringValue;
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
        StructureShape2 that = (StructureShape2) obj;
        return Objects.equals(this.intValue, that.intValue)
            && this.stringValue.equals(that.stringValue);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (intValue != null ? intValue.hashCode() : 0);
        hashCode = 31 * hashCode + stringValue.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "StructureShape2{"
            + "intValue: " + intValue
            + ", stringValue: " + stringValue + "}";
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
     * A class to build instances of StructureShape2
     */
    public static final class Builder implements StructureBase.Builder {
        private Integer intValue;
        private String stringValue;

        Builder() {
        }

        Builder(StructureShape2 data) {
            this.intValue = data.intValue;
            this.stringValue = data.stringValue;
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
         * Sets the value for {@code stringValue}.
         * 
         * @param stringValue The value to be set.
         * @return This instance for chain calling.
         */
        public Builder stringValue(String stringValue) {
            this.stringValue = stringValue;
            return this;
        }

        /**
         * Returns a new instance of {@link StructureShape2}
         * 
         * @return A new instance of {@link StructureShape2}
         */
        public StructureShape2 build() {
            return new StructureShape2(this);
        }
    }
}
