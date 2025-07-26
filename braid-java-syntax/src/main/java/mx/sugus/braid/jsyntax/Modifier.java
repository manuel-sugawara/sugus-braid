package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public enum Modifier {
    /**
     * The modifier {@code public}
     */
    PUBLIC("public"),
    /**
     * The modifier {@code protected}
     */
    PROTECTED("protected"),
    /**
     * The modifier {@code private}
     */
    PRIVATE("private"),
    /**
     * The modifier {@code abstract}
     */
    ABSTRACT("abstract"),
    /**
     * The modifier {@code default}
     */
    DEFAULT("default"),
    /**
     * The modifier {@code static}
     */
    STATIC("static"),
    /**
     * The modifier {@code sealed}
     */
    SEALED("sealed"),
    /**
     * The modifier {@code non-sealed}
     */
    NON_SEALED("non-sealed"),
    /**
     * The modifier {@code final}
     */
    FINAL("final"),
    /**
     * The modifier {@code transient}
     */
    TRANSIENT("transient"),
    /**
     * The modifier {@code volatile}
     */
    VOLATILE("volatile"),
    /**
     * The modifier {@code synchronized}
     */
    SYNCHRONIZED("synchronized"),
    /**
     * The modifier {@code native}
     */
    NATIVE("native"),
    /**
     * The modifier {@code strictfp}
     */
    STRICTFP("strictfp"),
    /**
     * Unknown enum constant
     */
    UNKNOWN_TO_VERSION(null);

    private final String value;

    Modifier(String value) {
        this.value = value;
    }

    /**
     * Returns the corresponding enum constant from the given value.
     * <p>
     * If the value is unknown it returns {@code UNKNOWN_TO_VERSION}.
     */
    public static Modifier from(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "public":
                return PUBLIC;
            case "protected":
                return PROTECTED;
            case "private":
                return PRIVATE;
            case "abstract":
                return ABSTRACT;
            case "default":
                return DEFAULT;
            case "static":
                return STATIC;
            case "sealed":
                return SEALED;
            case "non-sealed":
                return NON_SEALED;
            case "final":
                return FINAL;
            case "transient":
                return TRANSIENT;
            case "volatile":
                return VOLATILE;
            case "synchronized":
                return SYNCHRONIZED;
            case "native":
                return NATIVE;
            case "strictfp":
                return STRICTFP;
            default:
                return UNKNOWN_TO_VERSION;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
