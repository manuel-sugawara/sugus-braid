package mx.sugus.braid.plugins.data;

import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.DefaultModelTransformerTask;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.core.transforms.FlattenInterfaceMembers;
import mx.sugus.braid.core.transforms.SynthesizeServiceTransform;
import software.amazon.smithy.model.node.ObjectNode;

public final class FlattenInterfaceMembersPlugin implements SmithyGeneratorPlugin {
    public static final Identifier ID = Identifier.of(FlattenInterfaceMembersPlugin.class);

    public FlattenInterfaceMembersPlugin() {
    }

    @Override
    public Identifier provides() {
        return ID;
    }

    @Override
    public CodegenModuleConfig moduleConfig(ObjectNode node) {
        return newBaseConfig();
    }

    static CodegenModuleConfig newBaseConfig() {
        return CodegenModuleConfig
            .builder()
            .addModelTransformer(DefaultModelTransformerTask
                                     .builder()
                                     .taskId(Identifier.of(SynthesizeServiceTransform.class))
                                     .transform(FlattenInterfaceMembers::transform)
                                     .build())
            .build();
    }
}
