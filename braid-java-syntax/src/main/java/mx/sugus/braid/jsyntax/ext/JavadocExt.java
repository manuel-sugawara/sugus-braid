package mx.sugus.braid.jsyntax.ext;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class JavadocExt {

    public static String document(String source) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(source);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
