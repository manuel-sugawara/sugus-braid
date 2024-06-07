package mx.sugus.braid.plugins.data.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mx.sugus.braid.plugins.data.utils.SymbolConstants.AggregateType;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.core.util.PathUtil;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.traits.JavaTrait;
import mx.sugus.braid.traits.OrderedTrait;
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
import software.amazon.smithy.model.shapes.IntegerShape;
import software.amazon.smithy.model.shapes.ListShape;
import software.amazon.smithy.model.shapes.LongShape;
import software.amazon.smithy.model.shapes.MapShape;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.OperationShape;
import software.amazon.smithy.model.shapes.ResourceShape;
import software.amazon.smithy.model.shapes.ServiceShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeVisitor;
import software.amazon.smithy.model.shapes.ShortShape;
import software.amazon.smithy.model.shapes.StringShape;
import software.amazon.smithy.model.shapes.StructureShape;
import software.amazon.smithy.model.shapes.TimestampShape;
import software.amazon.smithy.model.shapes.UnionShape;
import software.amazon.smithy.model.traits.ErrorTrait;
import software.amazon.smithy.model.traits.UniqueItemsTrait;

public class BraidSymbolProvider implements SymbolProvider, ShapeVisitor<Symbol> {
    private final Model model;
    private final ShapeToJavaName shapeToJavaName;
    private final ShapeToJavaType shapeToJavaType;
    private final String packageName;

    public BraidSymbolProvider(Model model, ShapeToJavaName shapeToJavaName, ShapeToJavaType shapeToJavaType, String packageName) {
        this.model = model;
        this.shapeToJavaName = shapeToJavaName;
        this.shapeToJavaType = shapeToJavaType;
        this.packageName = packageName;
    }

    @Override
    public Symbol toSymbol(Shape shape) {
        var sym = shape.accept(this);
        if (sym == null) {
            return null;
        }
        var builder = sym.toBuilder();
        builder.putProperty(SymbolProperties.SIMPLE_NAME, shapeToJavaName.toJavaName(shape));
        return builder.build();

    }

    @Override
    public String toMemberName(MemberShape shape) {
        return shape.accept(this).getProperty(SymbolProperties.SIMPLE_NAME).orElseThrow().toString();
    }

    // --- Service ---
    @Override
    public Symbol operationShape(OperationShape shape) {
        return null;
    }

    @Override
    public Symbol resourceShape(ResourceShape shape) {
        return null;
    }

    @Override
    public Symbol serviceShape(ServiceShape shape) {
        return null;
    }

    // --- Aggregates ---
    @Override
    public Symbol structureShape(StructureShape shape) {
        if (shape.hasTrait(JavaTrait.class)) {
            var className = shape.expectTrait(JavaTrait.class).getValue();
            var typeName = ClassName.parse(className);
            return Symbol.builder()
                         .name(typeName.name())
                         .namespace(typeName.packageName(), ".")
                         .putProperty(SymbolProperties.JAVA_TYPE, typeName)
                         .build();
        }
        var name = shapeToJavaName.toJavaName(shape);
        var builder = Symbol.builder()
                            .name(name.toString())
                            .namespace(packageName, ".")
                            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.structureShape(shape))
                            .definitionFile(shapeClassPath(packageName, name));
        if (shape.hasTrait(ErrorTrait.class)) {
            builder.putProperty("extends", fromClass(RuntimeException.class));
        }
        return builder.build();
    }

    @Override
    public Symbol unionShape(UnionShape shape) {
        var name = shapeToJavaName.toJavaName(shape);
        var builder = Symbol.builder()
                            .name(name.toString())
                            .namespace(packageName, ".")
                            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.unionShape(shape))
                            .definitionFile(shapeClassPath(packageName, name));
        if (shape.hasTrait(ErrorTrait.class)) {
            builder.putProperty("extends", fromClass(RuntimeException.class));
        }
        return builder.build();
    }

    @Override
    public Symbol enumShape(EnumShape shape) {
        var name = shapeToJavaName.toJavaName(shape);
        var builder = Symbol.builder()
                            .name(name.toString())
                            .namespace(packageName, ".")
                            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.enumShape(shape))
                            .definitionFile(shapeClassPath(packageName, name));
        if (shape.hasTrait(ErrorTrait.class)) {
            // XXX legacy and unused, keeping here to remind me that this
            //  needs to be fixed eventually.
            builder.putProperty("extends", fromClass(RuntimeException.class));
        }
        return builder.build();
    }

    private String shapeClassPath(String packageName, Name name) {
        return PathUtil.from(PathUtil.from(packageName.split("\\.")), name.toString() + ".java");
    }

    @Override
    public Symbol memberShape(MemberShape shape) {
        var targetShape = model.expectShape(shape.getTarget());
        var targetSymbol = targetShape.accept(this);
        var builder = targetSymbol
            .toBuilder()
            .putProperty(SymbolProperties.SIMPLE_NAME, shapeToJavaName.toJavaName(shape))
            .putProperty(SymbolProperties.EMPTY_BUILDER_INIT, BraidSymbolProvider::emptyBuilderInitializer)
            .putProperty(SymbolProperties.DATA_BUILDER_INIT, BraidSymbolProvider::dataBuilderInitializer);

        return builder.build();
    }

    @Override
    public Symbol listShape(ListShape shape) {
        var isOrdered = shape.hasTrait(OrderedTrait.class);
        if (shape.hasTrait(UniqueItemsTrait.class)) {
            return setShape(shape);
        }
        return fromClass(List.class)
            .addReference(shape.getMember().accept(this))
            .putProperty(SymbolProperties.AGGREGATE_TYPE, AggregateType.LIST)
            .putProperty(SymbolProperties.ORDERED, isOrdered)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.listShape(shape))
            .build();
    }

    private Symbol setShape(ListShape shape) {
        var isOrdered = shape.hasTrait(OrderedTrait.class);
        return fromClass(Set.class)
            .addReference(shape.getMember().accept(this))
            .putProperty(SymbolProperties.AGGREGATE_TYPE, AggregateType.SET)
            .putProperty(SymbolProperties.ORDERED, isOrdered)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.listShape(shape))
            .build();
    }


    @Override
    public Symbol mapShape(MapShape shape) {
        var isOrdered = shape.hasTrait(OrderedTrait.class);
        return fromClass(Map.class)
            .addReference(shape.getKey().accept(this))
            .addReference(shape.getValue().accept(this))
            .putProperty(SymbolProperties.AGGREGATE_TYPE, AggregateType.MAP)
            .putProperty(SymbolProperties.ORDERED, isOrdered)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.mapShape(shape))
            .build();
    }

    // --- Simple types ---
    @Override
    public Symbol blobShape(BlobShape shape) {
        // XXX no support for blob shape yet.
        throw new UnsupportedOperationException();
    }

    @Override
    public Symbol documentShape(DocumentShape shape) {
        // XXX no support for document shape yet.
        throw new UnsupportedOperationException();
    }

    @Override
    public Symbol booleanShape(BooleanShape shape) {
        return fromClass(Boolean.class)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.booleanShape(shape))
            .build();
    }

    @Override
    public Symbol byteShape(ByteShape shape) {
        return fromClass(Byte.class)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.byteShape(shape))
            .build();
    }

    @Override
    public Symbol shortShape(ShortShape shape) {
        return fromClass(Short.class)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.shortShape(shape))
            .build();
    }

    @Override
    public Symbol integerShape(IntegerShape shape) {
        return fromClass(Integer.class)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.integerShape(shape))
            .build();
    }

    @Override
    public Symbol longShape(LongShape shape) {
        return fromClass(Long.class)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.longShape(shape))
            .build();
    }

    @Override
    public Symbol floatShape(FloatShape shape) {
        return fromClass(Float.class)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.floatShape(shape))
            .build();
    }

    @Override
    public Symbol doubleShape(DoubleShape shape) {
        return fromClass(Double.class)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.doubleShape(shape))
            .build();
    }

    @Override
    public Symbol bigIntegerShape(BigIntegerShape shape) {
        return fromClass(BigInteger.class)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.bigIntegerShape(shape))
            .build();
    }

    @Override
    public Symbol bigDecimalShape(BigDecimalShape shape) {
        return fromClass(BigDecimal.class)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.bigDecimalShape(shape))
            .build();
    }

    @Override
    public Symbol stringShape(StringShape shape) {
        return fromClass(String.class)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.stringShape(shape))
            .build();
    }

    @Override
    public Symbol timestampShape(TimestampShape shape) {
        return fromClass(Instant.class)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.timestampShape(shape))
            .build();
    }

    private static Block emptyBuilderInitializer(Symbol symbol) {
        var type = symbol.getProperty(SymbolProperties.AGGREGATE_TYPE).orElse(AggregateType.NONE);
        var name = symbol.getProperty(SymbolProperties.SIMPLE_NAME);
        if (type == AggregateType.NONE) {
            var builderReference = symbol.getProperty(SymbolProperties.BUILDER_REFERENCE).orElse(null);
            if (builderReference == null) {
                return BodyBuilder.emptyBlock();
            }
            var fromPersistent = builderReference.fromPersistent();
            var implementingClass = ClassName.from(fromPersistent.getNamespace(), fromPersistent.getName());
            return BodyBuilder.create()
                              .addStatement("this.$L = $T.$L($L)", name, implementingClass,
                                            fromPersistent.getMember().orElseThrow(), "null")
                              .build();
        }
        var builder = BodyBuilder.create();
        var isOrdered = symbol.getProperty(SymbolProperties.ORDERED).orElse(false);
        if (isOrdered) {
            switch (type) {
                case MAP -> builder.addStatement("this.$L = $T.forOrderedMap()", name, CollectionBuilderReference.class);
                case SET -> builder.addStatement("this.$L = $T.forOrderedSet()", name, CollectionBuilderReference.class);
                case LIST -> builder.addStatement("this.$L = $T.forList()", name, CollectionBuilderReference.class);
                default -> throw new UnsupportedOperationException("unsupported aggregate type: " + type);
            }
        } else {
            switch (type) {
                case MAP -> builder.addStatement("this.$L = $T.forUnorderedMap()", name, CollectionBuilderReference.class);
                case SET -> builder.addStatement("this.$L = $T.forUnorderedSet()", name, CollectionBuilderReference.class);
                case LIST -> builder.addStatement("this.$L = $T.forList()", name, CollectionBuilderReference.class);
                default -> throw new UnsupportedOperationException("unsupported aggregate type: " + type);
            }
        }
        return BodyBuilder.emptyBlock();
    }

    private static Block dataBuilderInitializer(Symbol symbol) {
        var type = symbol.getProperty(SymbolProperties.AGGREGATE_TYPE).orElse(AggregateType.NONE);
        var name = symbol.getProperty(SymbolProperties.SIMPLE_NAME);
        if (type == AggregateType.NONE) {
            var builderReference = symbol.getProperty(SymbolProperties.BUILDER_REFERENCE).orElse(null);
            if (builderReference == null) {
                return BodyBuilder.create()
                                  .addStatement("this.$1L = data.$1L", name)
                                  .build();
            }
            var fromPersistent = builderReference.fromPersistent();
            var implementingClass = ClassName.from(fromPersistent.getNamespace(), fromPersistent.getName());
            return BodyBuilder.create()
                              .addStatement("this.$L = $T.$L(data.$L)", name, implementingClass,
                                            fromPersistent.getMember().orElseThrow(), name)
                              .build();
        }
        var builder = BodyBuilder.create();
        var isOrdered = symbol.getProperty(SymbolProperties.ORDERED).orElse(false);
        if (isOrdered) {
            switch (type) {
                case MAP -> builder.addStatement("this.$1L = $2T.fromPersistentOrderedMap(data.$1L)",
                                                 name, CollectionBuilderReference.class);
                case SET -> builder.addStatement("this.$1L = $2T.fromPersistentOrderedSet(data.$1L)",
                                                 name, CollectionBuilderReference.class);
                case LIST -> builder.addStatement("this.$1L = $2T.fromPersistentList(data.$1L)",
                                                  name, CollectionBuilderReference.class);
                default -> throw new UnsupportedOperationException("unsupported aggregate type: " + type);
            }
        } else {
            switch (type) {
                case MAP -> builder.addStatement("this.$1L = $2T.fromPersistentUnorderedMap(data.$1L)",
                                                 name, CollectionBuilderReference.class);
                case SET -> builder.addStatement("this.$1L = $2T.fromPersistentUnorderedSet(data.$1L)",
                                                 name, CollectionBuilderReference.class);
                case LIST -> builder.addStatement("this.$1L = $2T.fromPersistentList(data.$1L)",
                                                  name, CollectionBuilderReference.class);
                default -> throw new UnsupportedOperationException("unsupported aggregate type: " + type);
            }
        }
        return BodyBuilder.emptyBlock();
    }

    private static Symbol.Builder fromClass(Class<?> clazz) {
        return Symbol.builder()
                     .name(clazz.getSimpleName())
                     .namespace(clazz.getPackageName(), ".");
    }
}
