package mx.sugus.braid.plugins.data.dependencies;

import mx.sugus.braid.core.util.Name;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

/**
 * Default implementation for {@link ShapeToJavaName}.
 */
public final class DefaultShapeToJavaName implements ShapeToJavaName {
    private final String packageOverride;
    private final ReservedWordsEscaper escaper;

    public DefaultShapeToJavaName(String packageOverride, ReservedWordsEscaper escaper) {
        this.packageOverride = packageOverride;
        this.escaper = escaper;
    }

    @Override
    public Name toName(Shape shape, Model model) {
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

    @Override
    public Name toJavaName(Shape shape, Model model) {
        return escape(toName(shape, model), shape);
    }

    @Override
    public String toJavaPackage(Shape shape) {
        if (packageOverride != null) {
            return packageOverride;
        }
        return shape.getId().getNamespace();
    }

    private Name escape(Name name, Shape shape) {
        return escaper.escape(name, shape);
    }
}
