package mx.sugus.braid.core;

import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.ext.TypeNameExt;
import mx.sugus.braid.traits.JavaTrait;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.BigDecimalShape;
import software.amazon.smithy.model.shapes.BigIntegerShape;
import software.amazon.smithy.model.shapes.BlobShape;
import software.amazon.smithy.model.shapes.BooleanShape;
import software.amazon.smithy.model.shapes.ByteShape;
import software.amazon.smithy.model.shapes.DoubleShape;
import software.amazon.smithy.model.shapes.EnumShape;
import software.amazon.smithy.model.shapes.FloatShape;
import software.amazon.smithy.model.shapes.IntegerShape;
import software.amazon.smithy.model.shapes.ListShape;
import software.amazon.smithy.model.shapes.LongShape;
import software.amazon.smithy.model.shapes.MapShape;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.OperationShape;
import software.amazon.smithy.model.shapes.ServiceShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeVisitor;
import software.amazon.smithy.model.shapes.ShortShape;
import software.amazon.smithy.model.shapes.StringShape;
import software.amazon.smithy.model.shapes.StructureShape;
import software.amazon.smithy.model.shapes.TimestampShape;
import software.amazon.smithy.model.shapes.UnionShape;
import software.amazon.smithy.model.traits.UniqueItemsTrait;

public class ShapeToJavaType extends ShapeVisitor.Default<TypeName> {
    private final ShapeToJavaName shapeToJavaName;
    private final Model model;

    public ShapeToJavaType(ShapeToJavaName shapeToJavaName, Model model) {
        this.shapeToJavaName = shapeToJavaName;
        this.model = model;
    }

    public TypeName toJavaType(Shape shape) {
        return shape.accept(this);
    }

    @Override
    protected TypeName getDefault(Shape shape) {
        throw new UnsupportedOperationException("Unsupported shape: " + shape);
    }

    @Override
    public TypeName operationShape(OperationShape shape) {
        return from(shape);
    }

    @Override
    public TypeName serviceShape(ServiceShape shape) {
        return from(shape);
    }

    @Override
    public TypeName blobShape(BlobShape shape) {
        // XXX figure out what to do about blob shapes
        throw new UnsupportedOperationException("Unsupported shape: " + shape);
    }

    @Override
    public TypeName booleanShape(BooleanShape shape) {
        return TypeNameExt.BOOLEAN;
    }

    @Override
    public TypeName listShape(ListShape shape) {
        if (shape.hasTrait(UniqueItemsTrait.class)) {
            var target = model.expectShape(shape.getMember().getTarget());
            return TypeNameExt.setOf(target.accept(this));
        }
        var target = model.expectShape(shape.getMember().getTarget());
        return TypeNameExt.listOf(target.accept(this));
    }

    @Override
    public TypeName byteShape(ByteShape shape) {
        return TypeNameExt.BYTE;
    }

    @Override
    public TypeName shortShape(ShortShape shape) {
        return TypeNameExt.SHORT;
    }

    @Override
    public TypeName integerShape(IntegerShape shape) {
        return TypeNameExt.INT;
    }

    @Override
    public TypeName longShape(LongShape shape) {
        return TypeNameExt.LONG;
    }

    @Override
    public TypeName floatShape(FloatShape shape) {
        return TypeNameExt.FLOAT;
    }

    @Override
    public TypeName doubleShape(DoubleShape shape) {
        return TypeNameExt.DOUBLE;
    }

    @Override
    public TypeName bigIntegerShape(BigIntegerShape shape) {
        return TypeNameExt.BIG_INTEGER;
    }

    @Override
    public TypeName bigDecimalShape(BigDecimalShape shape) {
        return TypeNameExt.BIG_DECIMAL;
    }

    @Override
    public TypeName stringShape(StringShape shape) {
        return TypeNameExt.STRING;
    }

    @Override
    public TypeName structureShape(StructureShape shape) {
        if (shape.hasTrait(JavaTrait.class)) {
            return ClassName.parse(shape.expectTrait(JavaTrait.class).getValue());
        }
        return from(shape);
    }

    @Override
    public TypeName unionShape(UnionShape shape) {
        return from(shape);
    }

    @Override
    public TypeName enumShape(EnumShape shape) {
        return from(shape);
    }

    @Override
    public TypeName mapShape(MapShape shape) {
        var key = model.expectShape(shape.getKey().getTarget());
        var value = model.expectShape(shape.getValue().getTarget());
        return TypeNameExt.mapOf(key.accept(this), value.accept(this));
    }

    @Override
    public TypeName memberShape(MemberShape shape) {
        var target = model.expectShape(shape.getTarget());
        return target.accept(this);
    }

    @Override
    public TypeName timestampShape(TimestampShape shape) {
        return TypeNameExt.INSTANT;
    }

    ClassName from(Shape shape) {
        var simpleName = shapeToJavaName.toJavaName(shape);
        var packageName = shapeToJavaName.toJavaPackage(shape);
        return ClassName.builder()
                        .name(simpleName.toString())
                        .packageName(packageName)
                        .build();
    }
}
