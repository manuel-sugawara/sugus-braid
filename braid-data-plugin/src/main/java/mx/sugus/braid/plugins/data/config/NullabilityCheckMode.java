package mx.sugus.braid.plugins.data.config;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Possibles modes to check if a member is nullable.</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum NullabilityCheckMode {
    /**
     * <p>All optional, all members are consider nullable.</p>
     */
    ALL_OPTIONAL("all-optional"),
    /**
     * <p>Client mode, equivalent to Smithy CheckMode#CLIENT.</p>
     */
    CLIENT("client"),
    /**
     * <p>Client careful mode, equivalent to Smithy CheckMode#CLIENT_CAREFUL.</p>
     */
    CLIENT_CAREFUL("client-careful"),
    /**
     * <p>Server mode, equivalent to Smithy CheckMode#SERVER.</p>
     */
    SERVER("server"),
    /**
     * <p>Unknown enum constant</p>
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    NullabilityCheckMode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * <p>Returns the corresponding enum constant from the given value.</p>
     * <p>If the value is unknown it returns {@code UNKNOWN_TO_VERSION}.</p>
     */
    public static NullabilityCheckMode from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "all-optional":
                return ALL_OPTIONAL;
            case "client":
                return CLIENT;
            case "client-careful":
                return CLIENT_CAREFUL;
            case "server":
                return SERVER;
            default:
                return UNKNOWN_TO_VERSION;
        }
    }
}
