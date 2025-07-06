package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Enum with one constant, no value
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum EnumWithoutValue22 {
    /**
     * Pascal case
     */
    ONE("One"),
    /**
     * Two
     */
    TWO("Two"),
    /**
     * Unknown enum constant
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    EnumWithoutValue22(String value) {
        this.value = value;
    }

    /**
     * Returns the corresponding enum constant from the given value.
     * <p>
     * If the value is unknown it returns {@code UNKNOWN_TO_VERSION}.
     */
    public static EnumWithoutValue22 from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "One":
                return ONE;
            case "Two":
                return TWO;
            default:
                return UNKNOWN_TO_VERSION;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
