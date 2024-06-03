package mx.sugus.braid.plugins.serde.node;

import java.util.Collection;
import java.util.List;
import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.plugins.data.DataPlugin;
import software.amazon.smithy.model.node.ObjectNode;

public final class NodeSerdePlugin implements SmithyGeneratorPlugin {
    public static final Identifier ID = Identifier.of(NodeSerdePlugin.class);

    @Override
    public Identifier provides() {
        return ID;
    }

    @Override
    public Collection<Identifier> requires() {
        return List.of(DataPlugin.ID);
    }

    @Override
    public CodegenModuleConfig moduleConfig(ObjectNode node) {
        return CodegenModuleConfig
            .builder()
            .addTransformer(new ClassAddToNodeTransformer())
            .addTransformer(new ClassAddFromNodeTransformer())
            .addTransformer(new InterfaceAddToNodeTransformer())
            .addTransformer(new InterfaceAddFromNodeTransformer())
            .build();
    }
}
