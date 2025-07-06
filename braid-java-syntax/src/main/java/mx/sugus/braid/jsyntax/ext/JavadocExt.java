package mx.sugus.braid.jsyntax.ext;

import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.Javadoc;

public final class JavadocExt {

    private JavadocExt() {
    }

    public static Javadoc document(String source) {
        return Javadoc.builder().body(CodeBlock.from("$L", source)).build();
    }

    public static Javadoc document(String source, Object... args) {
        return Javadoc.builder().body(CodeBlock.from(source, args)).build();
    }

}
