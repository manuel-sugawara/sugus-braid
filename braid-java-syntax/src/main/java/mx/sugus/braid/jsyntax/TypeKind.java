package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Kind of the supported Java types</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum TypeKind {
    /**
     * <p>A primitive type</p>
     */
    PRIMITIVE("primitive"),
    /**
     * <p>A Java Class type</p>
     */
    CLASS("class"),
    /**
     * <p>An array type</p>
     */
    ARRAY("array"),
    /**
     * <p>A generic Java class</p>
     */
    PARAMETERIZED("parameterized"),
    /**
     * <p>A type variable</p>
     */
    TYPE_VARIABLE("type-variable"),
    /**
     * <p>A wildcard type bound</p>
     */
    WILDCARD("wildcard"),
    /**
     * <p>Unknown enum constant</p>
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    TypeKind(String value) {
        this.value = value;
    }

    /**
     * <p>Returns the corresponding enum constant from the given value.</p>
     * <p>If the value is unknown it returns <code>UNKNOWN_TO_VERSION</code>.</p>
     */
    public static TypeKind from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "primitive":
                return PRIMITIVE;
            case "class":
                return CLASS;
            case "array":
                return ARRAY;
            case "parameterized":
                return PARAMETERIZED;
            case "type-variable":
                return TYPE_VARIABLE;
            case "wildcard":
                return WILDCARD;
            default:
                return UNKNOWN_TO_VERSION;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
