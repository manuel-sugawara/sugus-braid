package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum FormatKind {
    STRING("STRING"),
    NUMBER("NUMBER"),
    BOOLEAN("BOOLEAN"),
    /**
     * Unknown enum constant
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    FormatKind(String value) {
        this.value = value;
    }

    /**
     * Returns the corresponding enum constant from the given value.
     * <p>
     * If the value is unknown it returns {@code UNKNOWN_TO_VERSION}.
     */
    public static FormatKind from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "STRING":
                return STRING;
            case "NUMBER":
                return NUMBER;
            case "BOOLEAN":
                return BOOLEAN;
            default:
                return UNKNOWN_TO_VERSION;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
