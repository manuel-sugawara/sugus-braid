package mx.sugus.braid.plugins.data.producers;

import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeProducerTask;
import mx.sugus.braid.core.plugin.TypeSyntaxResult;
import mx.sugus.braid.traits.JavaTrait;
import software.amazon.smithy.model.shapes.ShapeType;

public final class EnumJavaProducer implements ShapeProducerTask<TypeSyntaxResult> {
    public static final Identifier ID = Identifier.of(EnumJavaProducer.class);

    public EnumJavaProducer() {
    }

    @Override
    public Identifier taskId() {
        return ID;
    }

    @Override
    public Class<TypeSyntaxResult> output() {
        return TypeSyntaxResult.class;
    }

    @Override
    public ShapeType type() {
        return ShapeType.ENUM;
    }

    @Override
    public TypeSyntaxResult produce(ShapeCodegenState directive) {
        var shape = directive.shape();
        if (shape.hasTrait(JavaTrait.class)) {
            return null;
        }
        var spec = new EnumData().build(directive);
        return TypeSyntaxResult.builder()
                               .syntax(spec)
                               .build();
    }
}
