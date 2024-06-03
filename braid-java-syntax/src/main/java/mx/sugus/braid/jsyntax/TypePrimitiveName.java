package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum TypePrimitiveName {
    VOID("void"),
    BOOLEAN("boolean"),
    BYTE("byte"),
    SHORT("short"),
    INT("int"),
    LONG("long"),
    CHAR("char"),
    FLOAT("float"),
    DOUBLE("double"),
    /**
     * <p>Unknown enum constant</p>
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    TypePrimitiveName(String value) {
        this.value = value;
    }

    /**
     * <p>Returns the corresponding enum constant from the given value.</p>
     * <p>If the value is unknown it returns <code>UNKNOWN_TO_VERSION</code>.</p>
     */
    public static TypePrimitiveName from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "void":
                return VOID;
            case "boolean":
                return BOOLEAN;
            case "byte":
                return BYTE;
            case "short":
                return SHORT;
            case "int":
                return INT;
            case "long":
                return LONG;
            case "char":
                return CHAR;
            case "float":
                return FLOAT;
            case "double":
                return DOUBLE;
            default:
                return UNKNOWN_TO_VERSION;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
