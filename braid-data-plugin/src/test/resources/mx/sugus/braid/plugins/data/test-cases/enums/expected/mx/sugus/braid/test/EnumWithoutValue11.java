package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Enum with one constant, no value
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum EnumWithoutValue11 {
    /**
     * Lower case
     */
    ONE("one"),
    /**
     * Unknown enum constant
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    EnumWithoutValue11(String value) {
        this.value = value;
    }

    /**
     * Returns the corresponding enum constant from the given value.
     * <p>
     * If the value is unknown it returns {@code UNKNOWN_TO_VERSION}.
     */
    public static EnumWithoutValue11 from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "one":
                return ONE;
            default:
                return UNKNOWN_TO_VERSION;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
