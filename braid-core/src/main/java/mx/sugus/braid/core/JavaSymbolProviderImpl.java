package mx.sugus.braid.core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import mx.sugus.braid.core.symbol.BraidSymbolProvider;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.core.util.PathUtil;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.traits.JavaTrait;
import software.amazon.smithy.codegen.core.ReservedWords;
import software.amazon.smithy.codegen.core.ReservedWordsBuilder;
import software.amazon.smithy.codegen.core.Symbol;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.BigDecimalShape;
import software.amazon.smithy.model.shapes.BigIntegerShape;
import software.amazon.smithy.model.shapes.BlobShape;
import software.amazon.smithy.model.shapes.BooleanShape;
import software.amazon.smithy.model.shapes.ByteShape;
import software.amazon.smithy.model.shapes.DocumentShape;
import software.amazon.smithy.model.shapes.DoubleShape;
import software.amazon.smithy.model.shapes.EnumShape;
import software.amazon.smithy.model.shapes.FloatShape;
import software.amazon.smithy.model.shapes.IntEnumShape;
import software.amazon.smithy.model.shapes.IntegerShape;
import software.amazon.smithy.model.shapes.ListShape;
import software.amazon.smithy.model.shapes.LongShape;
import software.amazon.smithy.model.shapes.MapShape;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.OperationShape;
import software.amazon.smithy.model.shapes.ResourceShape;
import software.amazon.smithy.model.shapes.ServiceShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.shapes.ShapeVisitor;
import software.amazon.smithy.model.shapes.ShortShape;
import software.amazon.smithy.model.shapes.StringShape;
import software.amazon.smithy.model.shapes.StructureShape;
import software.amazon.smithy.model.shapes.TimestampShape;
import software.amazon.smithy.model.shapes.UnionShape;
import software.amazon.smithy.model.traits.ErrorTrait;
import software.amazon.smithy.model.traits.UniqueItemsTrait;
import software.amazon.smithy.utils.StringUtils;

// TODO: this class needs ❤️ is full of ghosts in the shell and stuff left behind from past lives
public final class JavaSymbolProviderImpl implements JavaSymbolProvider, ShapeVisitor<Symbol> {

    public static final ReservedWords RESERVED_WORDS = buildReservedWords();
    public static final Symbol BOOLEAN = fromClass(Boolean.class);
    public static final Symbol BYTE = fromClass(Byte.class);
    public static final Symbol SHORT = fromClass(Short.class);
    public static final Symbol INTEGER = fromClass(Integer.class);
    public static final Symbol LONG = fromClass(Long.class);
    public static final Symbol FLOAT = fromClass(Float.class);
    public static final Symbol DOUBLE = fromClass(Double.class);
    public static final Symbol BIG_INTEGER = fromClass(BigInteger.class);
    public static final Symbol BIG_DECIMAL = fromClass(BigDecimal.class);
    public static final Symbol STRING = fromClass(String.class);

    private final JavaCodegenSettings settings;
    private final Model model;
    private final ServiceShape service;
    private final ReservedWords escaper;
    private final ShapeToJavaName shapeToJavaName;
    private final ShapeToJavaType shapeToJavaType;

    private JavaSymbolProviderImpl(Model model, JavaCodegenSettings settings) {
        this.settings = settings;
        this.model = model;
        // TODO: resolve this for service codegen
        this.service = null;
        this.escaper = RESERVED_WORDS;
        this.shapeToJavaName = new ShapeToJavaName(model, escaper, settings.packageName());
        this.shapeToJavaType = new ShapeToJavaType(shapeToJavaName, model);
    }

    @Override
    public Symbol toSymbol(Shape shape) {
        var sym = shape.accept(this);
        if (sym == null) {
            return null;
        }
        var builder = sym.toBuilder();
        if (sym.getProperty(TypeName.class.getName()).isEmpty()) {
            builder.putProperty(TypeName.class.getName(), shapeToJavaType.toJavaType(shape));
        }
        if (sym.getProperty(Name.class.getName()).isEmpty()) {
            builder.putProperty(Name.class.getName(), toJavaName2(shape));
        }
        return builder.build();
    }

    @Override
    public String toMemberName(MemberShape shape) {
        return toJavaName(shape).toString();
    }

    @Override
    public Symbol blobShape(BlobShape shape) {
        // FIXME, add support for this
        throw new UnsupportedOperationException("unsupported shape with type: " + shape.getType());
    }

    @Override
    public Symbol booleanShape(BooleanShape booleanShape) {
        return BOOLEAN;
    }

    @Override
    public Symbol listShape(ListShape listShape) {
        if (listShape.hasTrait(UniqueItemsTrait.class)) {
            return setOf(listShape.getMember().accept(this));
        }
        return listOf(listShape.getMember().accept(this));
    }

    @Override
    public Symbol mapShape(MapShape mapShape) {
        return mapOf(mapShape.getKey().accept(this), mapShape.getValue().accept(this));
    }

    @Override
    public Symbol documentShape(DocumentShape documentShape) {
        return null;
    }

    @Override
    public Symbol byteShape(ByteShape byteShape) {
        return BYTE;
    }

    @Override
    public Symbol shortShape(ShortShape shortShape) {
        return SHORT;
    }

    @Override
    public Symbol integerShape(IntegerShape integerShape) {
        return INTEGER;
    }

    @Override
    public Symbol longShape(LongShape longShape) {
        return LONG;
    }

    @Override
    public Symbol floatShape(FloatShape floatShape) {
        return FLOAT;
    }

    @Override
    public Symbol doubleShape(DoubleShape doubleShape) {
        return DOUBLE;
    }

    @Override
    public Symbol bigIntegerShape(BigIntegerShape bigIntegerShape) {
        return BIG_INTEGER;
    }

    @Override
    public Symbol bigDecimalShape(BigDecimalShape bigDecimalShape) {
        return BIG_DECIMAL;
    }

    @Override
    public Symbol operationShape(OperationShape operationShape) {
        return Symbol.builder()
                     .name(shapeName(operationShape))
                     .namespace(settings.packageName(), ".")
                     .build();
    }

    @Override
    public Symbol resourceShape(ResourceShape resourceShape) {
        return null;
    }

    @Override
    public Symbol serviceShape(ServiceShape serviceShape) {
        return null;
    }

    @Override
    public Symbol stringShape(StringShape stringShape) {
        return STRING;
    }

    @Override
    public Symbol structureShape(StructureShape structureShape) {
        if (structureShape.hasTrait(JavaTrait.class)) {
            var className = structureShape.expectTrait(JavaTrait.class).getValue();
            return SymbolConstants.fromLiteralClassName(className);
        }
        var name = shapeToJavaName.toJavaName(structureShape);
        var builder = Symbol.builder()
                            .name(name.toString())
                            .namespace(settings.packageName(), ".")
                            .definitionFile(shapeClassPath(structureShape));
        if (structureShape.hasTrait(ErrorTrait.class)) {
            builder.putProperty("extends", fromClass(RuntimeException.class));
        }
        return builder.build();
    }

    @Override
    public Symbol unionShape(UnionShape shape) {
        var name = shapeName(shape);
        return createSymbolBuilder(name, settings.packageName())
            .definitionFile(shapeClassPath(shape))
            .build();
    }

    @Override
    public Symbol memberShape(MemberShape memberShape) {
        return toSymbol(model.expectShape(memberShape.getTarget()))
            .toBuilder()
            .putProperty(Name.class.getName(), toJavaName2(memberShape))
            .build();
    }

    @Override
    public Symbol timestampShape(TimestampShape timestampShape) {
        return fromClass(Instant.class);
    }

    @Override
    public Symbol enumShape(EnumShape shape) {
        var name = shapeName(shape);
        return createSymbolBuilder(name, settings.packageName())
            .definitionFile(shapeClassPath(shape))
            .build();
    }

    @Override
    public Symbol intEnumShape(IntEnumShape shape) {
        var name = shapeName(shape);
        return createSymbolBuilder(name, settings.packageName())
            .definitionFile(shapeClassPath(shape))
            .build();
    }

    private String shapeName(Shape shape) {
        if (service != null) {
            var name = StringUtils.capitalize(shape.getId().getName(service));
            return escaper.escape(name);
        }
        return escaper.escape(StringUtils.capitalize(shape.getId().getName()));
    }

    private String shapeClassPath(Shape shape) {
        return PathUtil.from(PathUtil.from(settings.packageParts()), shapeName(shape) + ".java");
    }

    private Name toJavaName2(Shape shape) {
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
        return validateName(Name.of(simpleName, kind), shape);
    }

    private static ReservedWords buildReservedWords() {
        return
            new ReservedWordsBuilder()
                .loadWords(Objects.requireNonNull(JavaSymbolProviderImpl.class.getResource("java-reserved-words.txt")),
                           Function.identity())
                .loadWords(Objects.requireNonNull(JavaSymbolProviderImpl.class.getResource("java-system-type-names.txt")),
                           Function.identity())
                .build();
    }

    public static Symbol mapOf(Symbol key, Symbol value) {
        return builderFor(Map.class)
            .addReference(key)
            .addReference(value)
            .putProperty(SymbolConstants.AGGREGATE_TYPE, SymbolConstants.AggregateType.MAP)
            .build();
    }

    public static Symbol listOf(Symbol value) {
        return builderFor(List.class)
            .addReference(value)
            .putProperty(SymbolConstants.AGGREGATE_TYPE, SymbolConstants.AggregateType.LIST)
            .build();
    }

    public static Symbol setOf(Symbol value) {
        return builderFor(Set.class)
            .addReference(value)
            .putProperty(SymbolConstants.AGGREGATE_TYPE, SymbolConstants.AggregateType.SET)
            .build();
    }

    public static SymbolProvider create(Model model, JavaCodegenSettings settings) {
        var escaper = RESERVED_WORDS;
        var shapeToJavaName = new ShapeToJavaName(model, escaper, settings.packageName());
        var shapeToJavaType = new ShapeToJavaType(shapeToJavaName, model);
        if (false) {
            return new JavaSymbolProviderImpl(model, settings);
        }
        return new BraidSymbolProvider(model, shapeToJavaName, shapeToJavaType, settings.packageName());
    }

    private static Symbol.Builder createSymbolBuilder(String typeName, String namespace) {
        return createSymbolBuilder(typeName).namespace(namespace, ".");
    }

    private static Symbol.Builder createSymbolBuilder(String typeName) {
        return Symbol.builder().name(typeName);
    }

    private static Symbol fromClass(Class<?> clazz) {
        return Symbol.builder()
                     .name(clazz.getSimpleName())
                     .namespace(clazz.getPackageName(), ".")
                     .build();
    }

    private static Symbol.Builder builderFor(Class<?> clazz) {
        return Symbol.builder()
                     .name(clazz.getSimpleName())
                     .namespace(clazz.getPackageName(), ".");
    }
}
