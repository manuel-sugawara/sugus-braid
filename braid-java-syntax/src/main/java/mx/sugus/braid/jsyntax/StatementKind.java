package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>The type of statement
 * Kinds of methods</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum StatementKind {
    FORMAT("format"),
    BLOCK("block"),
    ABSTRACT_CONTROL_FLOW("control-flow"),
    IF_STATEMENT("if"),
    FOR_STATEMENT("for"),
    SWITCH_STATEMENT("switch"),
    /**
     * <p>Unknown enum constant</p>
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    StatementKind(String value) {
        this.value = value;
    }

    /**
     * <p>Returns the corresponding enum constant from the given value.</p>
     * <p>If the value is unknown it returns <code>UNKNOWN_TO_VERSION</code>.</p>
     */
    public static StatementKind from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "format":
                return FORMAT;
            case "block":
                return BLOCK;
            case "control-flow":
                return ABSTRACT_CONTROL_FLOW;
            case "if":
                return IF_STATEMENT;
            case "for":
                return FOR_STATEMENT;
            case "switch":
                return SWITCH_STATEMENT;
            default:
                return UNKNOWN_TO_VERSION;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
