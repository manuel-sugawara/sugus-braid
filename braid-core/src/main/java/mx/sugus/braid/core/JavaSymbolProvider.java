package mx.sugus.braid.core;

import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.traits.ConstTrait;
import software.amazon.smithy.codegen.core.Symbol;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public interface JavaSymbolProvider extends SymbolProvider {
    default Name toJavaName(Shape shape) {
        return toSymbol(shape).getProperty(Name.class.getName(), Name.class).orElseThrow(null);
    }

    default Name toJavaName(Shape shape, Name.Convention kind) {
        var name = toSymbol(shape).getProperty(Name.class.getName(), Name.class).orElseThrow()
                                  .toNameConvention(kind);
        return validateName(name, shape);
    }

    default Name toJavaName(Shape shape, String prefix) {
        var name = toSymbol(shape).getProperty(Name.class.getName(), Name.class).orElseThrow()
                                  .withPrefix(prefix);
        return validateName(name, shape);

    }

    default Name toJavaSingularName(Shape shape, Name.Convention kind) {
        var name = toSymbol(shape).getProperty(Name.class.getName(), Name.class).orElseThrow()
                                  .toSingularSpelling()
                                  .toNameConvention(kind);
        return validateName(name, shape);
    }

    default Name toJavaSingularName(Shape shape, Name.Convention kind, String prefix) {
        var name = toSymbol(shape).getProperty(Name.class.getName(), Name.class).orElseThrow()
                                  .toSingularSpelling()
                                  .withPrefix(prefix)
                                  .toNameConvention(kind);
        return validateName(name, shape);
    }

    default Name toJavaSingularName(Shape shape) {
        var name = toSymbol(shape).getProperty(Name.class.getName(), Name.class).orElseThrow()
                                  .toSingularSpelling();
        return validateName(name, shape);
    }

    default Name toJavaSingularName(Shape shape, String prefix) {
        var name = toSymbol(shape).getProperty(Name.class.getName(), Name.class).orElseThrow()
                                  .toSingularSpelling()
                                  .withPrefix(prefix);
        return validateName(name, shape);
    }

    default Name validateName(Name name, Shape shape) {
        if (JavaSymbolProviderImpl.RESERVED_WORDS.isReserved(name.toString())) {
            var type = shape.getType();
            if (type == ShapeType.MEMBER ||
                (type.getCategory() != ShapeType.Category.AGGREGATE && type.getCategory() != ShapeType.Category.SERVICE)) {
                name = name.prefixWithArticle();
            } else {
                name = name.withSuffix(type.name());
            }
        }
        return name;
    }

    default TypeName toJavaTypeName(Shape shape) {
        return toSymbol(shape).getProperty(TypeName.class.getName(), TypeName.class).orElseThrow();
    }

    default TypeName toJavaTypeName(Symbol shape) {
        return shape.getProperty(TypeName.class.getName(), TypeName.class).orElseThrow();
    }

    default boolean isMemberNullable(MemberShape shape) {
        var aggregateType = SymbolConstants.aggregateType(toSymbol(shape));
        if (aggregateType != SymbolConstants.AggregateType.NONE) {
            return false;
        }
        return shape.isOptional();
    }

    default boolean isMemberRequired(MemberShape shape) {
        return shape.isRequired() || shape.hasTrait(ConstTrait.class);
    }

    default String emptyReferenceBuilder(SymbolConstants.AggregateType type) {
        return SymbolConstants.emptyReferenceBuilder(type);
    }

    default String initReferenceBuilder(SymbolConstants.AggregateType type) {
        return SymbolConstants.initReferenceBuilder(type);
    }

    default Name toMemberJavaName(MemberShape shape) {
        return toJavaName(shape);
    }

    default Name toShapeJavaName(Shape shape) {
        var name = shape.getId().getName();
        return Name.of(name);
    }
}
