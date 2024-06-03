package mx.sugus.braid.plugins.data;

import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeProducerTask;
import mx.sugus.braid.core.plugin.TypeSyntaxResult;
import software.amazon.smithy.model.shapes.ShapeType;

public class UnionJavaProducer implements ShapeProducerTask<TypeSyntaxResult> {
    public static final Identifier ID = Identifier.of(UnionJavaProducer.class);

    public UnionJavaProducer() {
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
        return ShapeType.UNION;
    }

    @Override
    public TypeSyntaxResult produce(ShapeCodegenState directive) {
        var syntax = new UnionData().build(directive);
        return TypeSyntaxResult.builder()
                               .syntax(syntax)
                               .build();
    }
}
