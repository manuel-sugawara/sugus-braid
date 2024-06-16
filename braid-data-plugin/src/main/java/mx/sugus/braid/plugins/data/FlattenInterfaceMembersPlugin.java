package mx.sugus.braid.plugins.data;

import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.DefaultModelTransformerTask;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.plugins.data.model.FlattenInterfaceMembers;
import software.amazon.smithy.model.node.ObjectNode;

public final class FlattenInterfaceMembersPlugin implements SmithyGeneratorPlugin<ObjectNode> {
    public static final Identifier ID = Identifier.of(FlattenInterfaceMembersPlugin.class);

    public FlattenInterfaceMembersPlugin() {
    }

    @Override
    public Identifier provides() {
        return ID;
    }

    @Override
    public ObjectNode fromNode(ObjectNode node) {
        return node;
    }

    @Override
    public CodegenModuleConfig moduleConfig(ObjectNode node) {
        return CodegenModuleConfig
            .builder()
            .addModelTransformer(DefaultModelTransformerTask
                                     .builder()
                                     .taskId(ID)
                                     .transform(FlattenInterfaceMembers::transform)
                                     .build())
            .build();
    }
}
