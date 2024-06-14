package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Enum with one constant, no value</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum EnumWithValue11 {
    /**
     * <p>Lower case</p>
     */
    ONE("1"),
    /**
     * <p>Unknown enum constant</p>
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    EnumWithValue11(String value) {
        this.value = value;
    }

    /**
     * <p>Returns the corresponding enum constant from the given value.</p>
     * <p>If the value is unknown it returns <code>UNKNOWN_TO_VERSION</code>.</p>
     */
    public static EnumWithValue11 from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "1":
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
