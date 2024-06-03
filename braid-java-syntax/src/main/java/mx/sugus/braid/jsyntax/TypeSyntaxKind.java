package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Kind of the supported Java types</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum TypeSyntaxKind {
    /**
     * <p>A class type</p>
     */
    CLASS("class"),
    /**
     * <p>A interface</p>
     */
    INTERFACE("interface"),
    /**
     * <p>A enum type</p>
     */
    ENUM("enum"),
    /**
     * <p>Unknown enum constant</p>
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    TypeSyntaxKind(String value) {
        this.value = value;
    }

    /**
     * <p>Returns the corresponding enum constant from the given value.</p>
     * <p>If the value is unknown it returns <code>UNKNOWN_TO_VERSION</code>.</p>
     */
    public static TypeSyntaxKind from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "class":
                return CLASS;
            case "interface":
                return INTERFACE;
            case "enum":
                return ENUM;
            default:
                return UNKNOWN_TO_VERSION;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
