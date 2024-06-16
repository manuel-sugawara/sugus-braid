package mx.sugus.braid.plugins.data.producers;

import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeProducerTask;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
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
        var syntax = new UnionData().buildCompilationUnit(directive);
        return TypeSyntaxResult.builder()
                               .syntax(syntax)
                               .build();
    }
}
