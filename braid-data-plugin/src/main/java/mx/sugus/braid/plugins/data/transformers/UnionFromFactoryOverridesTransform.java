package mx.sugus.braid.plugins.data.transformers;

import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.plugins.data.producers.UnionJavaProducer;

public class UnionFromFactoryOverridesTransform extends StructureFromFactoryOverridesTransform {
    public static final Identifier ID = Identifier.of(UnionFromFactoryOverridesTransform.class);

    @Override
    public Identifier taskId() {
        return ID;
    }

    @Override
    public Identifier transformsId() {
        return UnionJavaProducer.ID;
    }

}
