package mx.sugus.braid.jsyntax.ext;

import mx.sugus.braid.jsyntax.CodeBlock;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public final class JavadocExt {

    private JavadocExt() {
    }

    public static CodeBlock document(String source) {
        var parser = Parser.builder().build();
        var document = parser.parse(source);
        var renderer = HtmlRenderer.builder().build();
        var rendered = renderer.render(document);
        rendered = rendered.replace("*/", "*&#47;");
        return CodeBlock.from("$L", rendered);
    }
}
