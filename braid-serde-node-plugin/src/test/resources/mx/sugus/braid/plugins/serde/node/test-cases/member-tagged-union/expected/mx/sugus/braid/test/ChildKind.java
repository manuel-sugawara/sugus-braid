package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum ChildKind {
    FOO("foo"),
    BAR("bar"),
    BAZ("baz"),
    /**
     * Unknown enum constant
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    ChildKind(String value) {
        this.value = value;
    }

    /**
     * Returns the corresponding enum constant from the given value.
     * <p>
     * If the value is unknown it returns {@code UNKNOWN_TO_VERSION}.
     */
    public static ChildKind from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "foo":
                return FOO;
            case "bar":
                return BAR;
            case "baz":
                return BAZ;
            default:
                return UNKNOWN_TO_VERSION;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
