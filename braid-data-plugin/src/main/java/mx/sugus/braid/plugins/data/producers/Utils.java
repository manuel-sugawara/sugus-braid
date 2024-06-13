package mx.sugus.braid.plugins.data.producers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import mx.sugus.braid.core.BraidCodegenPlugin;
import mx.sugus.braid.core.plugin.CodegenState;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.jsyntax.Annotation;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.EnumSyntax;
import mx.sugus.braid.jsyntax.FormatterLiteral;
import mx.sugus.braid.jsyntax.FormatterNode;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.TypeSyntax;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.plugins.data.symbols.SymbolConstants;
import mx.sugus.braid.plugins.data.symbols.SymbolProperties;
import mx.sugus.braid.rt.util.annotations.Generated;
import mx.sugus.braid.traits.ConstTrait;
import mx.sugus.braid.traits.UseBuilderReferenceTrait;
import software.amazon.smithy.codegen.core.ReservedWords;
import software.amazon.smithy.codegen.core.ReservedWordsBuilder;
import software.amazon.smithy.codegen.core.Symbol;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

public final class Utils {
    // XXX remove this from here and unify the logic as a dependency
    public static final ReservedWords RESERVED_WORDS = buildReservedWords();
    private static final ClassName GENERATED = ClassName.from(Generated.class);

    public static TypeSyntax addGeneratedBy(TypeSyntax src, Identifier id) {
        var generatedBy = findGeneratedAnnotation(src);
        if (generatedBy == null) {
            generatedBy = generatedBy(id);
        } else {
            var value = removeBrackets(generatedBy.value());
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
                var value = literal.value();
                if ("{".equals(value) || "}".equals(value)) {
                    continue;
                }
            }
            newParts.add(part);
        }
        return CodeBlock.builder().parts(newParts).build();
    }

    public static Name toJavaName(Symbol symbol) {
        var name = symbol.getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow();
        return validateName(name, symbol);
    }

    public static Name toSetterName(Symbol symbol) {
        var name = symbol.getProperty(SymbolProperties.SETTER_NAME).orElseThrow();
        return validateName(name, symbol);
    }

    public static Name toGetterName(Symbol symbol) {
        var name = symbol.getProperty(SymbolProperties.GETTER_NAME).orElseThrow();
        return validateName(name, symbol);
    }

    public static Name toAdderName(Symbol symbol) {
        var name = symbol.getProperty(SymbolProperties.ADDER_NAME).orElseThrow();
        return validateName(name, symbol);
    }

    public static Name toMultiAdderName(Symbol symbol) {
        var name = symbol.getProperty(SymbolProperties.MULTI_ADDER_NAME).orElseThrow();
        return validateName(name, symbol);
    }

    public static Name toJavaName(CodegenState state, Shape shape) {
        return state.symbolProvider().toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow(() -> new NoSuchElementException(shape.toString()));
    }

    public static Name toJavaName(CodegenState state, Shape shape, Name.Convention kind) {
        var name = state.symbolProvider().toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
                        .toNameConvention(kind);
        return validateName(name, shape);
    }

    public static Name toJavaName(CodegenState state, Shape shape, Name.Convention kind, String prefix) {
        var name = state.symbolProvider().toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
                        .toNameConvention(kind)
                        .withPrefix(prefix);
        return validateName(name, shape);
    }

    public static Name toJavaName(CodegenState state, Shape shape, String prefix) {
        var name = state.symbolProvider().toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
                        .withPrefix(prefix);
        return validateName(name, shape);
    }

    public static Name toJavaSingularName(CodegenState state, Shape shape, Name.Convention kind) {
        var name = state.symbolProvider().toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
                        .toSingularSpelling()
                        .toNameConvention(kind);
        return validateName(name, shape);
    }

    public static Name toJavaSingularName(CodegenState state, Shape shape, Name.Convention kind, String prefix) {
        var name = state.symbolProvider().toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
                        .toSingularSpelling()
                        .withPrefix(prefix)
                        .toNameConvention(kind);
        return validateName(name, shape);
    }

    public static Name toJavaSingularName(CodegenState state, Shape shape) {
        var name = state.symbolProvider().toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
                        .toSingularSpelling();
        return validateName(name, shape);
    }

    public static Name toJavaSingularName(CodegenState state, Shape shape, String prefix) {
        var name = state.symbolProvider().toSymbol(shape).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow()
                        .toSingularSpelling()
                        .withPrefix(prefix);
        return validateName(name, shape);
    }

    public static Name validateName(Name name, Shape shape) {
        if (RESERVED_WORDS.isReserved(name.toString())) {
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

    public static ShapeType toShapeType(Symbol symbol) {
        return symbol.getProperty(SymbolProperties.SHAPE_TYPE).orElseThrow();
    }

    public static Name validateName(Name name, Symbol symbol) {
        if (RESERVED_WORDS.isReserved(name.toString())) {
            var type = toShapeType(symbol);
            if (type == ShapeType.MEMBER ||
                (type.getCategory() != ShapeType.Category.AGGREGATE && type.getCategory() != ShapeType.Category.SERVICE)) {
                name = name.prefixWithArticle();
            } else {
                name = name.withSuffix(type.name());
            }
        }
        return name;
    }

    public static TypeName toJavaTypeName(CodegenState state, Shape shape) {
        return state.symbolProvider().toSymbol(shape).getProperty(SymbolProperties.JAVA_TYPE).orElseThrow();
    }

    public static TypeName toJavaTypeName(CodegenState state, Symbol shape) {
        return shape.getProperty(SymbolProperties.JAVA_TYPE).orElseThrow();
    }


    public static boolean isMemberNullable(CodegenState state, MemberShape shape) {
        var aggregateType = aggregateType(state, shape);
        if (aggregateType != SymbolConstants.AggregateType.NONE) {
            return false;
        }
        return shape.isOptional();
    }

    public static boolean isMemberRequired(CodegenState state, MemberShape shape) {
        return shape.isRequired() || shape.hasTrait(ConstTrait.class);
    }

    public static boolean isRequired(CodegenState state, MemberShape shape) {
        return shape.isRequired() || shape.hasTrait(ConstTrait.class);
    }

    public static String emptyReferenceBuilder(SymbolConstants.AggregateType type) {
        return SymbolConstants.emptyReferenceBuilder(type);
    }

    public static String initReferenceBuilder(SymbolConstants.AggregateType type) {
        return SymbolConstants.initReferenceBuilder(type);
    }

    public static TypeName concreteClassFor(SymbolConstants.AggregateType type) {
        return SymbolConstants.concreteClassFor(type);
    }

    public static Name toMemberJavaName(CodegenState state, MemberShape shape) {
        return toJavaName(state, shape);
    }

    public static SymbolConstants.AggregateType aggregateType(CodegenState state, Shape shape) {
        var symbol = state.symbolProvider().toSymbol(shape);
        return aggregateType(symbol);
    }

    public static SymbolConstants.AggregateType aggregateType(Symbol symbol) {
        return symbol.getProperty(SymbolProperties.AGGREGATE_TYPE).orElse(SymbolConstants.AggregateType.NONE);
    }

    public static boolean isRequired(Symbol symbol) {
        return symbol.getProperty(SymbolProperties.IS_REQUIRED).orElse(false);
    }

    public static boolean isConstant(Symbol symbol) {
        return symbol.getProperty(SymbolProperties.IS_CONSTANT).orElse(false);
    }

    public static boolean isOrdered(Symbol symbol) {
        return symbol.getProperty(SymbolProperties.IS_ORDERED).orElse(false);
    }

    public static String defaultValue(Symbol symbol) {
        return symbol.getProperty(SymbolProperties.DEFAULT_VALUE).orElse(null);
    }

    public static UseBuilderReferenceTrait builderReference(Symbol symbol) {
        return symbol.getProperty(SymbolProperties.BUILDER_REFERENCE).orElse(null);
    }

    public static Block dataInitFromBuilder(Symbol symbol) {
        var initFunction = symbol.getProperty(SymbolProperties.DATA_BUILDER_INIT).orElse(x -> BodyBuilder.emptyBlock());
        return initFunction.apply(symbol);
    }

    public static Block builderInitFromEmpty(Symbol symbol) {
        var initFunction = symbol.getProperty(SymbolProperties.BUILDER_EMPTY_INIT).orElse(x -> BodyBuilder.emptyBlock());
        return initFunction.apply(symbol);
    }

    public static Block builderInitFromData(Symbol symbol) {
        var initFunction = symbol.getProperty(SymbolProperties.BUILDER_DATA_INIT).orElse(x -> BodyBuilder.emptyBlock());
        return initFunction.apply(symbol);
    }

    public static Block builderSetter(Symbol symbol) {
        var initFunction = symbol.getProperty(SymbolProperties.BUILDER_SETTER_FOR_MEMBER).orElse(x -> BodyBuilder.emptyBlock());
        return initFunction.apply(symbol);
    }

    public static TypeName toJavaTypeName(Symbol symbol) {
        return symbol.getProperty(SymbolProperties.JAVA_TYPE).orElseThrow();
    }

    public static TypeName toBuilderTypeName(Symbol symbol) {
        return symbol.getProperty(SymbolProperties.BUILDER_JAVA_TYPE).orElseGet(() -> toJavaTypeName(symbol));
    }


    public static TypeName toRefrenceBuilderTypeName(Symbol symbol) {
        return symbol.getProperty(SymbolProperties.BUILDER_REFERENCE_JAVA_TYPE).orElseGet(() -> toJavaTypeName(symbol));
    }

    public static TypeName toRefrenceBuilderBuilderTypeName(Symbol symbol) {
        return symbol.getProperty(SymbolProperties.BUILDER_REFERENCE_BUILDER_JAVA_TYPE).orElseGet(() -> toJavaTypeName(symbol));
    }

    private static ReservedWords buildReservedWords() {
        return
            new ReservedWordsBuilder()
                .loadWords(Objects.requireNonNull(BraidCodegenPlugin.class.getResource("java-reserved-words.txt")),
                           Function.identity())
                .loadWords(Objects.requireNonNull(BraidCodegenPlugin.class.getResource("java-system-type-names.txt")),
                           Function.identity())
                .build();
    }
}
