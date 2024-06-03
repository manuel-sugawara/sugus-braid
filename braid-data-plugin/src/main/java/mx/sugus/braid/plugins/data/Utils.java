package mx.sugus.braid.plugins.data;

import java.util.ArrayList;
import java.util.List;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.jsyntax.Annotation;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.EnumSyntax;
import mx.sugus.braid.jsyntax.FormatterLiteral;
import mx.sugus.braid.jsyntax.FormatterNode;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.TypeSyntax;
import mx.sugus.braid.rt.util.annotations.Generated;

public final class Utils {
    private static final ClassName GENERATED = ClassName.from(Generated.class);

    public static TypeSyntax addGeneratedBy(TypeSyntax src, Identifier id) {
        var generatedBy = findGeneratedAnnotation(src);
        if (generatedBy == null) {
            generatedBy = generatedBy(id);
        } else {
            var value = removeBrackets((CodeBlock) generatedBy.value());
            var newValue = CodeBlock.from("{$C, $S}", value, id.toString());
            generatedBy = generatedBy.toBuilder()
                                     .value(newValue)
                                     .build();
        }
        return insertOrReplaceGeneratedBy(src, generatedBy);
    }

    public static Annotation generatedBy(Identifier id) {
        return Annotation.builder(GENERATED)
                         .value(CodeBlock.from("$S", id.toString()))
                         .build();
    }

    private static TypeSyntax insertOrReplaceGeneratedBy(TypeSyntax src, Annotation generatedBy) {
        var newAnnotations = new ArrayList<Annotation>(src.annotations().size());
        for (var annotation : src.annotations()) {
            if (annotation.type().equals(GENERATED)) {
                newAnnotations.add(generatedBy);
            } else {
                newAnnotations.add(annotation);
            }
        }
        return insertOrReplaceGeneratedBy(src, newAnnotations);
    }

    private static TypeSyntax insertOrReplaceGeneratedBy(TypeSyntax src, List<Annotation> annotations) {
        if (src instanceof ClassSyntax c) {
            return c.toBuilder().annotations(annotations).build();
        }
        if (src instanceof EnumSyntax e) {
            return e.toBuilder().annotations(annotations).build();
        }
        if (src instanceof InterfaceSyntax i) {
            return i.toBuilder().annotations(annotations).build();
        }
        throw new IllegalArgumentException("unsupported TypeSyntax kind: " + src.getClass().getName());
    }

    private static Annotation findGeneratedAnnotation(TypeSyntax src) {
        for (var annotation : src.annotations()) {
            if (annotation.type().equals(GENERATED)) {
                return annotation;
            }
        }
        return null;
    }

    private static CodeBlock removeBrackets(CodeBlock block) {
        var parts = block.parts();
        var newParts = new ArrayList<FormatterNode>();
        for (var part : parts) {
            if (part instanceof FormatterLiteral literal) {
                var value =literal.value();
                if (value.equals("{") || value.equals("}")) {
                    continue;
                }
            }
            newParts.add(part);
        }
        return CodeBlock.builder().parts(newParts).build();
    }
}
