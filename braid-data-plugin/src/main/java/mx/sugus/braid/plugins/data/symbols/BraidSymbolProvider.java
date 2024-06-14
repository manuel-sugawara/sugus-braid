package mx.sugus.braid.plugins.data.symbols;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.core.util.PathUtil;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.writer.CodeWriter;
import mx.sugus.braid.plugins.data.symbols.SymbolConstants.AggregateType;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.traits.ConstTrait;
import mx.sugus.braid.traits.JavaTrait;
import mx.sugus.braid.traits.OrderedTrait;
import mx.sugus.braid.traits.UseBuilderReferenceTrait;
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
import software.amazon.smithy.model.traits.DefaultTrait;
import software.amazon.smithy.model.traits.UniqueItemsTrait;

public class BraidSymbolProvider implements SymbolProvider, ShapeVisitor<Symbol> {
    private final Model model;
    private final ShapeToJavaName shapeToJavaName;
    private final ShapeToJavaType shapeToJavaType;

    public BraidSymbolProvider(Model model, ShapeToJavaName shapeToJavaName, ShapeToJavaType shapeToJavaType) {
        this.model = model;
        this.shapeToJavaName = shapeToJavaName;
        this.shapeToJavaType = shapeToJavaType;
    }

    @Override
    public Symbol toSymbol(Shape shape) {
        var sym = shape.accept(this);
        if (sym == null) {
            return null;
        }
        var builder = sym.toBuilder();
        builder.putProperty(SymbolProperties.SIMPLE_NAME, shapeToJavaName.toJavaName(shape, model));
        builder.putProperty(SymbolProperties.SHAPE_TYPE, shape.getType());
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
        var name = shapeToJavaName.toJavaName(shape, model);
        var packageName = shapeToJavaName.toJavaPackage(shape);
        var builder = Symbol.builder()
                            .name(name.toString())
                            .namespace(packageName, ".")
                            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.structureShape(shape))
                            .definitionFile(shapeClassPath(packageName, name));
        return builder.build();
    }

    @Override
    public Symbol unionShape(UnionShape shape) {
        var name = shapeToJavaName.toJavaName(shape, model);
        var packageName = shapeToJavaName.toJavaPackage(shape);
        var builder = Symbol.builder()
                            .name(name.toString())
                            .namespace(packageName, ".")
                            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.unionShape(shape))
                            .definitionFile(shapeClassPath(packageName, name));
        return builder.build();
    }

    @Override
    public Symbol enumShape(EnumShape shape) {
        var name = shapeToJavaName.toJavaName(shape, model);
        var packageName = shapeToJavaName.toJavaPackage(shape);
        var builder = Symbol.builder()
                            .name(name.toString())
                            .namespace(packageName, ".")
                            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.enumShape(shape))
                            .definitionFile(shapeClassPath(packageName, name));
        return builder.build();
    }

    private String shapeClassPath(String packageName, Name name) {
        return PathUtil.from(PathUtil.from(packageName.split("\\.")), name.toString() + ".java");
    }

    @Override
    public Symbol memberShape(MemberShape shape) {
        var targetShape = model.expectShape(shape.getTarget());
        var targetSymbol = targetShape.accept(this);
        var builderReference = targetShape.getTrait(UseBuilderReferenceTrait.class).orElse(null);
        var simpleName = shapeToJavaName.toJavaName(shape, model);
        var builder = targetSymbol
            .toBuilder()
            .putProperty(SymbolProperties.SIMPLE_NAME, simpleName)
            .putProperty(SymbolProperties.SETTER_NAME, simpleName)
            .putProperty(SymbolProperties.GETTER_NAME, simpleName)
            .putProperty(SymbolProperties.IS_REQUIRED, shape.isRequired())
            .putProperty(SymbolProperties.BUILDER_REFERENCE, builderReference)
            .putProperty(SymbolProperties.IS_CONSTANT, shape.hasTrait(ConstTrait.class))
            .putProperty(SymbolProperties.BUILDER_EMPTY_INIT, SymbolCodegen::builderEmptyInitializer)
            .putProperty(SymbolProperties.BUILDER_EMPTY_INIT_EXPRESSION, SymbolCodegen::builderEmptyInitializerExpression)
            .putProperty(SymbolProperties.BUILDER_DATA_INIT, SymbolCodegen::builderDataInitializer)
            .putProperty(SymbolProperties.BUILDER_DATA_INIT_EXPRESSION, SymbolCodegen::builderDataInitializerExpression)
            .putProperty(SymbolProperties.BUILDER_UNION_DATA_INIT_EXPRESSION, SymbolCodegen::builderUnionDataInitializerExpression)
            .putProperty(SymbolProperties.DATA_BUILDER_INIT, SymbolCodegen::dataBuilderInitializer)
            .putProperty(SymbolProperties.BUILDER_SETTER_FOR_MEMBER, SymbolCodegen::builderSetterForMember)
            .putProperty(SymbolProperties.DEFAULT_VALUE, getDefaultValue(shape, targetShape));
        var aggregateType = targetSymbol.getProperty(SymbolProperties.AGGREGATE_TYPE).orElse(AggregateType.NONE);
        if (aggregateType != AggregateType.NONE) {
            var targetType = targetSymbol.getProperty(SymbolProperties.JAVA_TYPE).orElseThrow();
            builder.putProperty(SymbolProperties.BUILDER_JAVA_TYPE,
                                ParameterizedTypeName.from(CollectionBuilderReference.class, targetType));
            var prefix = aggregateType == AggregateType.MAP ? "put" : "add";
            builder.putProperty(SymbolProperties.ADDER_NAME, simpleName.toSingularSpelling().withPrefix(prefix));
            builder.putProperty(SymbolProperties.MULTI_ADDER_NAME, simpleName.withPrefix(prefix));
        } else if (builderReference != null) {
            var targetType = targetSymbol.getProperty(SymbolProperties.JAVA_TYPE).orElseThrow();
            var targetTypeClass = ClassName.toClassName(targetType);
            var builderType = targetTypeClass.toBuilder().name(targetTypeClass.name() + ".Builder").build();
            builderType = builderReferenceBuilderType(builderReference, builderType);
            var builderReferenceType = builderReferenceType(builderReference, targetType);
            var fromPersistent = fromPersistent(builderReference);
            builder.putProperty(SymbolProperties.BUILDER_JAVA_TYPE,
                                ParameterizedTypeName.from(BuilderReference.class, targetType, builderType));
            builder.putProperty(SymbolProperties.BUILDER_REFERENCE_JAVA_TYPE, builderReferenceType);
            builder.putProperty(SymbolProperties.BUILDER_REFERENCE_BUILDER_JAVA_TYPE, builderType);
            builder.putProperty(SymbolProperties.BUILDER_REFERENCE_FROM_PERSISTENT, fromPersistent);
        }
        return builder.build();
    }

    private ClassName builderReferenceType(UseBuilderReferenceTrait trait, TypeName targetType) {
        var builderTypeId = trait.builderType();
        if (builderTypeId != null) {
            return ClassName.from(builderTypeId.getNamespace(), builderTypeId.getName());
        }
        var targetClass = ClassName.toClassName(targetType);
        return targetClass.toBuilder().name(targetClass.name() + "." + targetClass.name() + "BuilderReference").build();
    }

    private ClassName builderReferenceBuilderType(UseBuilderReferenceTrait trait, ClassName defaultBuilderType) {
        var builderTypeId = trait.builderType();
        if (builderTypeId != null) {
            return ClassName.from(builderTypeId.getNamespace(), builderTypeId.getName());
        }
        return defaultBuilderType;
    }

    private String fromPersistent(UseBuilderReferenceTrait trait) {
        var fromPersistentId = trait.fromPersistent();
        if (fromPersistentId != null) {
            return fromPersistentId.getMember().orElseThrow();
        }
        return "from";
    }

    @Override
    public Symbol listShape(ListShape shape) {
        if (shape.hasTrait(UniqueItemsTrait.class)) {
            return setShape(shape);
        }
        return fromClass(List.class)
            .addReference(shape.getMember().accept(this))
            .putProperty(SymbolProperties.AGGREGATE_TYPE, AggregateType.LIST)
            .putProperty(SymbolProperties.JAVA_TYPE, shapeToJavaType.listShape(shape))
            .build();
    }

    private Symbol setShape(ListShape shape) {
        var isOrdered = shape.hasTrait(OrderedTrait.class);
        return fromClass(Set.class)
            .addReference(shape.getMember().accept(this))
            .putProperty(SymbolProperties.AGGREGATE_TYPE, AggregateType.SET)
            .putProperty(SymbolProperties.IS_ORDERED, isOrdered)
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
            .putProperty(SymbolProperties.IS_ORDERED, isOrdered)
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

    private static Symbol.Builder fromClass(Class<?> clazz) {
        return Symbol.builder()
                     .name(clazz.getSimpleName())
                     .namespace(clazz.getPackageName(), ".");
    }

    static String getDefaultValue(MemberShape member, Shape target) {
        var defaultValue = member.getTrait(DefaultTrait.class).orElse(null);
        if (defaultValue == null) {
            return null;
        }
        var defaultValueNode = defaultValue.toNode();
        var shapeType = target.getType();
        switch (shapeType) {
            case BYTE, SHORT, INTEGER, DOUBLE:
                return defaultValueNode.expectNumberNode().getValue().toString();
            case LONG:
                return defaultValueNode.expectNumberNode().getValue().toString() + "L";
            case FLOAT:
                return defaultValueNode.expectNumberNode().getValue().toString() + "F";
            case STRING:
                return CodeWriter.escapeJavaString(defaultValueNode.expectStringNode().getValue());
            case BOOLEAN:
                return Boolean.toString(defaultValueNode.expectBooleanNode().getValue());
            default:
                throw new UnsupportedOperationException("Unsupported type: " + shapeType + " for default value");
        }
    }

}
