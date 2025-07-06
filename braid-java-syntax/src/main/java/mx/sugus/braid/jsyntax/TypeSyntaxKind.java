package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Kind of the supported Java types
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum TypeSyntaxKind {
    /**
     * A class type
     */
    CLASS("class"),
    /**
     * A interface
     */
    INTERFACE("interface"),
    /**
     * A enum type
     */
    ENUM("enum"),
    /**
     * Unknown enum constant
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    TypeSyntaxKind(String value) {
        this.value = value;
    }

    /**
     * Returns the corresponding enum constant from the given value.
     * <p>
     * If the value is unknown it returns {@code UNKNOWN_TO_VERSION}.
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
