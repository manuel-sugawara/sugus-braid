package mx.sugus.braid.plugins.data.producers;

import static mx.sugus.braid.plugins.data.dependencies.DataPluginDependencies.RESERVED_WORDS_ESCAPER;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import mx.sugus.braid.core.plugin.CodegenState;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.jsyntax.Annotation;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.EnumSyntax;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.MemberValue;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.TypeSyntax;
import mx.sugus.braid.plugins.data.symbols.SymbolConstants;
import mx.sugus.braid.plugins.data.symbols.SymbolProperties;
import mx.sugus.braid.rt.util.annotations.Generated;
import mx.sugus.braid.traits.UseBuilderReferenceTrait;
import software.amazon.smithy.codegen.core.Symbol;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;

public final class Utils {
    private static final ClassName GENERATED = ClassName.from(Generated.class);

    private Utils() {
    }

    public static TypeSyntax addGeneratedBy(TypeSyntax src, Identifier id) {
        var generatedBy = findGeneratedAnnotation(src);
        if (generatedBy == null) {
            generatedBy = generatedBy(id);
        } else {
            var memberValue = generatedBy.members().get("value");
            memberValue = memberValue.toBuilder()
                                     .addArrayExpression(CodeBlock.from("$S", id.toString()))
                                     .build();
            generatedBy = generatedBy.toBuilder()
                                     .putMember("value", memberValue)
                                     .build();
        }
        return insertOrReplaceGeneratedBy(src, generatedBy);
    }

    public static Annotation generatedBy(Identifier id) {
        return Annotation.builder(GENERATED)
                         .putMember("value", MemberValue.forArrayExpression(CodeBlock.from("$S", id.toString())))
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
        return src.toBuilder().annotations(annotations).build();
    }

    private static Annotation findGeneratedAnnotation(TypeSyntax src) {
        for (var annotation : src.annotations()) {
            if (annotation.type().equals(GENERATED)) {
                return annotation;
            }
        }
        return null;
    }

    public static Name toSetterName(CodegenState state, MemberShape member) {
        var symbol = state.symbolProvider().toSymbol(member);
        return symbol.getProperty(SymbolProperties.SETTER_NAME).orElseThrow();
    }

    public static Name toGetterName(CodegenState state, MemberShape member) {
        var symbol = state.symbolProvider().toSymbol(member);
        return symbol.getProperty(SymbolProperties.GETTER_NAME).orElseThrow();
    }

    public static Name toAdderName(CodegenState state, MemberShape member) {
        var symbol = state.symbolProvider().toSymbol(member);
        return symbol.getProperty(SymbolProperties.ADDER_NAME).orElseThrow();
    }

    public static Name toMultiAdderName(CodegenState state, MemberShape member) {
        var symbol = state.symbolProvider().toSymbol(member);
        return symbol.getProperty(SymbolProperties.MULTI_ADDER_NAME).orElseThrow();
    }

    public static Name toJavaName(CodegenState state, Shape shape) {
        return state.symbolProvider()
                    .toSymbol(shape)
                    .getProperty(SymbolProperties.JAVA_NAME)
                    .orElseThrow(() -> new NoSuchElementException(shape.toString()));
    }

    public static Name toSourceName(CodegenState state, Shape shape, Name.Convention kind) {
        var symbol = state.symbolProvider().toSymbol(shape);
        var name = symbol.getProperty(SymbolProperties.SIMPLE_NAME)
                         .orElseThrow(() -> new NoSuchElementException(shape.toString()))
                         .toNameConvention(kind);
        return escapeNameIfNeeded(state, name, shape);
    }

    public static Name toJavaName(CodegenState state, Shape shape, Name.Convention kind) {
        var name = state.symbolProvider()
                        .toSymbol(shape)
                        .getProperty(SymbolProperties.JAVA_NAME)
                        .orElseThrow(() -> new NoSuchElementException(shape.toString()))
                        .toNameConvention(kind);
        return escapeNameIfNeeded(state, name, shape);
    }

    public static Name toJavaSingularName(CodegenState state, Shape shape) {
        return escapeNameIfNeeded(state, toJavaName(state, shape).toSingularSpelling(), shape);
    }

    public static TypeName toJavaTypeName(CodegenState state, Shape shape) {
        return state.symbolProvider().toSymbol(shape).getProperty(SymbolProperties.JAVA_TYPE).orElseThrow();
    }

    public static TypeName toJavaTypeName(CodegenState state, Symbol shape) {
        return shape.getProperty(SymbolProperties.JAVA_TYPE).orElseThrow();
    }

    public static boolean isNullable(CodegenState state, MemberShape shape) {
        return !isRequired(state, shape);
    }

    public static boolean isRequired(CodegenState state, MemberShape shape) {
        var symbol = state.symbolProvider().toSymbol(shape);
        return symbol.getProperty(SymbolProperties.IS_REQUIRED).orElse(false);
    }

    public static boolean isExplicitlyRequired(CodegenState state, MemberShape shape) {
        var symbol = state.symbolProvider().toSymbol(shape);
        return symbol.getProperty(SymbolProperties.IS_EXPLICITLY_REQUIRED).orElse(false);
    }

    public static boolean isImplicitlyRequired(CodegenState state, MemberShape shape) {
        var symbol = state.symbolProvider().toSymbol(shape);
        var isImplicitlyRequired = symbol.getProperty(SymbolProperties.IS_IMPLICITLY_REQUIRED).orElse(false);
        if (!isImplicitlyRequired) {
            return isRequired(state, shape);
        }
        return true;
    }

    public static TypeName concreteClassFor(SymbolConstants.AggregateType type) {
        return SymbolConstants.concreteClassFor(type);
    }

    public static SymbolConstants.AggregateType aggregateType(CodegenState state, Shape shape) {
        var symbol = state.symbolProvider().toSymbol(shape);
        return symbol.getProperty(SymbolProperties.AGGREGATE_TYPE).orElse(SymbolConstants.AggregateType.NONE);
    }

    public static TypeName toBuilderTypeName(ShapeCodegenState state, Shape shape) {
        var symbol = state.symbolProvider().toSymbol(shape);
        return symbol.getProperty(SymbolProperties.BUILDER_JAVA_TYPE).orElseGet(() -> toJavaTypeName(state, shape));
    }

    public static TypeName toRefrenceBuilderBuilderTypeName(ShapeCodegenState state, Shape shape) {
        var symbol = state.symbolProvider().toSymbol(shape);
        return symbol.getProperty(SymbolProperties.BUILDER_REFERENCE_BUILDER_JAVA_TYPE)
                     .orElseGet(() -> toJavaTypeName(state, shape));
    }

    public static boolean isConstant(CodegenState state, Shape shape) {
        var symbol = state.symbolProvider().toSymbol(shape);
        return symbol.getProperty(SymbolProperties.IS_CONSTANT).orElse(false);
    }

    public static boolean isOrdered(CodegenState state, Shape shape) {
        var symbol = state.symbolProvider().toSymbol(shape);
        return symbol.getProperty(SymbolProperties.IS_ORDERED).orElse(false);
    }

    public static CodeBlock defaultValue(CodegenState state, MemberShape shape) {
        var symbol = state.symbolProvider().toSymbol(shape);
        var defaultFunction = symbol.getProperty(SymbolProperties.DEFAULT_VALUE).orElse(null);
        return defaultFunction.apply(state, shape);
    }

    public static UseBuilderReferenceTrait builderReference(ShapeCodegenState state, Shape shape) {
        var symbol = state.symbolProvider().toSymbol(shape);
        return symbol.getProperty(SymbolProperties.BUILDER_REFERENCE).orElse(null);
    }

    public static Block dataInitFromBuilder(ShapeCodegenState state, MemberShape member) {
        var symbol = state.symbolProvider().toSymbol(member);
        var initFunction = symbol.getProperty(SymbolProperties.DATA_BUILDER_INIT).orElseThrow();
        return initFunction.apply(state, member);
    }

    public static Block builderInitFromEmpty(ShapeCodegenState state, MemberShape member) {
        var symbol = state.symbolProvider().toSymbol(member);
        var initFunction = symbol.getProperty(SymbolProperties.BUILDER_EMPTY_INIT).orElseThrow();
        return initFunction.apply(state, member);
    }

    public static Block builderInitFromData(ShapeCodegenState state, MemberShape member) {
        var symbol = state.symbolProvider().toSymbol(member);
        var initFunction = symbol.getProperty(SymbolProperties.BUILDER_DATA_INIT).orElseThrow();
        return initFunction.apply(state, member);
    }

    public static CodeBlock builderInitFromDataExpression(ShapeCodegenState state, MemberShape member) {
        var symbol = state.symbolProvider().toSymbol(member);
        var initFunction = symbol.getProperty(SymbolProperties.BUILDER_DATA_INIT_EXPRESSION).orElseThrow();
        return initFunction.apply(state, member);
    }

    public static CodeBlock builderUnionInitFromDataExpression(ShapeCodegenState state, MemberShape member) {
        var symbol = state.symbolProvider().toSymbol(member);
        var initFunction = symbol.getProperty(SymbolProperties.BUILDER_UNION_DATA_INIT_EXPRESSION).orElseThrow();
        return initFunction.apply(state, member);
    }

    public static Block builderSetter(ShapeCodegenState state, MemberShape member) {
        var symbol = state.symbolProvider().toSymbol(member);
        var initFunction = symbol.getProperty(SymbolProperties.BUILDER_SETTER_FOR_MEMBER).orElseThrow();
        return initFunction.apply(state, member);
    }

    public static CodeBlock toBuilderInitExpression(ShapeCodegenState state, MemberShape member) {
        var symbol = state.symbolProvider().toSymbol(member);
        var initFunction = symbol.getProperty(SymbolProperties.BUILDER_EMPTY_INIT_EXPRESSION).orElseThrow();
        return initFunction.apply(state, member);
    }

    private static Name escapeNameIfNeeded(CodegenState state, Name name, Shape shape) {
        return state.dependencies()
                    .expect(RESERVED_WORDS_ESCAPER)
                    .escape(name, shape);
    }
}
