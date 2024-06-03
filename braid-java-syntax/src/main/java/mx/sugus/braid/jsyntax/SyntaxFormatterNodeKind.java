package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum SyntaxFormatterNodeKind {
    LITERAL("literal"),
    STRING("string"),
    TYPE_NAME("type-name"),
    BLOCK("block"),
    /**
     * <p>Unknown enum constant</p>
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    SyntaxFormatterNodeKind(String value) {
        this.value = value;
    }

    /**
     * <p>Returns the corresponding enum constant from the given value.</p>
     * <p>If the value is unknown it returns <code>UNKNOWN_TO_VERSION</code>.</p>
     */
    public static SyntaxFormatterNodeKind from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "literal":
                return LITERAL;
            case "string":
                return STRING;
            case "type-name":
                return TYPE_NAME;
            case "block":
                return BLOCK;
            default:
                return UNKNOWN_TO_VERSION;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
