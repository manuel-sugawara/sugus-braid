package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Enum with one constant, no value
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum EnumWithValue23 {
    /**
     * Screaming case
     */
    ONE("1"),
    /**
     * Two
     */
    TWO("2"),
    /**
     * Unknown enum constant
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    EnumWithValue23(String value) {
        this.value = value;
    }

    /**
     * Returns the corresponding enum constant from the given value.
     * <p>
     * If the value is unknown it returns {@code UNKNOWN_TO_VERSION}.
     */
    public static EnumWithValue23 from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "1":
                return ONE;
            case "2":
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
