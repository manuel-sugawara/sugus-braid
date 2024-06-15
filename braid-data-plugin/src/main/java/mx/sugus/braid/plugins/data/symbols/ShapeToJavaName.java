package mx.sugus.braid.plugins.data.symbols;

import mx.sugus.braid.core.util.Name;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public final class ShapeToJavaName {
    private final String packageOverride;

    public ShapeToJavaName(String packageOverride) {
        this.packageOverride = packageOverride;
    }

    public Name toJavaName(Shape shape, Model model) {
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
        return Name.of(simpleName, kind);
    }

    public String toJavaPackage(Shape shape) {
        if (packageOverride != null) {
            return packageOverride;
        }
        return shape.getId().getNamespace();
    }
}
