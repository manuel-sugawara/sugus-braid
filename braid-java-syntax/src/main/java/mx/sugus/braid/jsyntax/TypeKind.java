package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Kind of the supported Java types
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum TypeKind {
    /**
     * A primitive type
     */
    PRIMITIVE("primitive"),
    /**
     * A Java Class type
     */
    CLASS("class"),
    /**
     * An array type
     */
    ARRAY("array"),
    /**
     * A generic Java class
     */
    PARAMETERIZED("parameterized"),
    /**
     * A type variable
     */
    TYPE_VARIABLE("type-variable"),
    /**
     * A wildcard type bound
     */
    WILDCARD("wildcard"),
    /**
     * Unknown enum constant
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    TypeKind(String value) {
        this.value = value;
    }

    /**
     * Returns the corresponding enum constant from the given value.
     * <p>
     * If the value is unknown it returns {@code UNKNOWN_TO_VERSION}.
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
