package mx.sugus.braid.jsyntax.writer;

import java.io.StringWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.SyntaxNode;

/**
 * Renders a given syntax node into an string.
 */
public final class CodeRenderer {
    private CodeRenderer() {
    }

    /**
     * Renders the given syntax node to a string. It does not include any import statements.
     *
     * @param node The syntax node to render
     * @return The rendered string of the syntax
     */
    public static String render(SyntaxNode node) {
        var simpleNames = new ImportableNames().importableNames("", node);
        var stringWriter = new StringWriter();
        var codeWriter = new CodeWriter(stringWriter);
        var visitor = new CodeWriterWalkVisitor(codeWriter, "", simpleNames);
        node.accept(visitor);
        return stringWriter.toString();
    }

    /**
     * Renders the given syntax node to a string. It renders a class preface consisting of the package declaration and any import
     * statements. It uses the given {@code packageImplicitNames} to correctly decide when a type should be fully qualified and
     * when it can be imported.
     *
     * @param containingPackage    The name of the package
     * @param node                 The syntax node to render.
     * @return The rendered string of the syntax
     */
    public static String render(String containingPackage, SyntaxNode node) {
        var simpleNames = new ImportableNames().importableNames(containingPackage, node);
        var stringWriter = new StringWriter();
        var codeWriter = new CodeWriter(stringWriter);
        var visitor = new CodeWriterWalkVisitor(codeWriter, containingPackage, simpleNames);
        renderPreface(codeWriter, containingPackage, simpleNames);
        node.accept(visitor);
        return stringWriter.toString();
    }

    private static void renderPreface(CodeWriter codeWriter, String containingPackage, Map<String, ClassName> simpleNames) {
        if (!containingPackage.isBlank()) {
            codeWriter.write("package ");
            codeWriter.write(containingPackage);
            codeWriter.writeln(";");
            codeWriter.newLine();
        }
        var importsAdded = false;
        var sorted = simpleNames.values()
                                .stream()
                                .sorted(Comparator.comparing(CodeRenderer::qualifiedName))
                                .toList();
        for (var className : sorted) {
            var classPackage = className.packageName();
            if ("java.lang".equals(classPackage)) {
                continue;
            }
            if (containingPackage.equals(classPackage)) {
                continue;
            }
            codeWriter.write("import ")
                      .write(classPackage)
                      .write(".")
                      .write(className.name())
                      .writeln(";");
            importsAdded = true;
        }
        if (importsAdded) {
            codeWriter.newLine();
        }
    }

    static String qualifiedName(ClassName c) {
        if (c.packageName() == null) {
            return c.name();
        }
        return c.packageName() + "." + c.name();
    }
}
