package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Kinds of methods</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum MethodKind {
    CONCRETE("concrete"),
    ABSTRACT("abstract"),
    CONSTRUCTOR("constructor"),
    /**
     * <p>Unknown enum constant</p>
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    MethodKind(String value) {
        this.value = value;
    }

    /**
     * <p>Returns the corresponding enum constant from the given value.</p>
     * <p>If the value is unknown it returns <code>UNKNOWN_TO_VERSION</code>.</p>
     */
    public static MethodKind from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "concrete":
                return CONCRETE;
            case "abstract":
                return ABSTRACT;
            case "constructor":
                return CONSTRUCTOR;
            default:
                return UNKNOWN_TO_VERSION;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
