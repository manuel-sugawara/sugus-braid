package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * This enum is sensitive
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum SensitiveEnum {
    SECRET1("secret one"),
    SECRET2("secret two"),
    /**
     * Unknown enum constant
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    SensitiveEnum(String value) {
        this.value = value;
    }

    /**
     * Returns the corresponding enum constant from the given value.
     * <p>
     * If the value is unknown it returns {@code UNKNOWN_TO_VERSION}.
     */
    public static SensitiveEnum from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "secret one":
                return SECRET1;
            case "secret two":
                return SECRET2;
            default:
                return UNKNOWN_TO_VERSION;
        }
    }

    @Override
    public String toString() {
        return "<*** REDACTED ***>";
    }
}
