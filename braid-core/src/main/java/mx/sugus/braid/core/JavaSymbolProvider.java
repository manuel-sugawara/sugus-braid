package mx.sugus.braid.core;

import java.util.NoSuchElementException;
import mx.sugus.braid.core.symbol.SymbolProperties;
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
        return toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow(() -> new NoSuchElementException(shape.toString()));
        //return toSymbol(shape).getProperty(Name.class.getName(), Name.class).orElseThrow();
    }

    default Name toJavaName(Shape shape, Name.Convention kind) {
        var name = toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
                                  .toNameConvention(kind);
        return validateName(name, shape);
    }

    default Name toJavaName(Shape shape, Name.Convention kind, String prefix) {
        var name = toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
                                  .toNameConvention(kind)
                                  .withPrefix(prefix);
        return validateName(name, shape);
    }

    default Name toJavaName(Shape shape, String prefix) {
        var name = toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
                                  .withPrefix(prefix);
        return validateName(name, shape);

    }

    default Name toJavaSingularName(Shape shape, Name.Convention kind) {
        var name = toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
                                  .toSingularSpelling()
                                  .toNameConvention(kind);
        return validateName(name, shape);
    }

    default Name toJavaSingularName(Shape shape, Name.Convention kind, String prefix) {
        var name = toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
                                  .toSingularSpelling()
                                  .withPrefix(prefix)
                                  .toNameConvention(kind);
        return validateName(name, shape);
    }

    default Name toJavaSingularName(Shape shape) {
        var name = toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
                                  .toSingularSpelling();
        return validateName(name, shape);
    }

    default Name toJavaSingularName(Shape shape, String prefix) {
        var name = toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
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
        return toSymbol(shape).getProperty(SymbolProperties.JAVA_TYPE).orElseThrow();
    }

    default TypeName toJavaTypeName(Symbol shape) {
        try {
            //return shape.getProperty(TypeName.class.getName(), TypeName.class).orElseThrow();
            return shape.getProperty(SymbolProperties.JAVA_TYPE).orElseThrow();
        } catch (Exception e) {
            System.out.printf("================>>>> failed type name for symbol: %s\n", shape);
            throw e;
        }
    }

    default boolean isMemberNullable(MemberShape shape) {
        var aggregateType = aggregateType(shape);
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

    default TypeName concreteClassFor(SymbolConstants.AggregateType type) {
        return SymbolConstants.concreteClassFor(type);
    }

    default Name toMemberJavaName(MemberShape shape) {
        return toJavaName(shape);
    }

    default SymbolConstants.AggregateType aggregateType(Shape shape) {
        var symbol = toSymbol(shape);
        if (false) {
            return symbol.getProperty(SymbolConstants.AGGREGATE_TYPE, SymbolConstants.AggregateType.class)
                         .orElse(SymbolConstants.AggregateType.NONE);
        }
        return symbol.getProperty(SymbolProperties.AGGREGATE_TYPE).orElse(SymbolConstants.AggregateType.NONE);
    }
}
