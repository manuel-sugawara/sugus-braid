package mx.sugus.braid.plugins.data.utils;

import mx.sugus.braid.core.util.Name;
import software.amazon.smithy.codegen.core.ReservedWords;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public class ShapeToJavaName {
    private final Model model;
    private final ReservedWords escaper;
    private final String packageOverride;

    public ShapeToJavaName(Model model, ReservedWords escaper, String packageOverride) {
        this.model = model;
        this.escaper = escaper;
        this.packageOverride = packageOverride;
    }

    public Name toJavaName(Shape shape) {
        var kind = Name.Convention.PASCAL_CASE;
        var simpleName = shape.getId().getName();
        if (shape.getType() == ShapeType.MEMBER) {
            var member = shape.asMemberShape().orElseThrow();
            var targetShape = model.expectShape(member.getContainer());
            if (targetShape.getType() == ShapeType.ENUM) {
                kind = Name.Convention.SCREAM_CASE;
            } else {
                kind = Name.Convention.CAMEL_CASE;
            }
            simpleName = member.getMemberName();
        }
        var name = Name.of(simpleName, kind);
        var nameAsString = name.toString();
        var escaped = escaper.escape(nameAsString);
        if (escaped.equals(nameAsString)) {
            return name;
        }
        return Name.of(escaped, kind);
    }

    public String toJavaPackage(Shape shape) {
        if (packageOverride != null) {
            return packageOverride;
        }
        return shape.getId().getNamespace();
    }
}
