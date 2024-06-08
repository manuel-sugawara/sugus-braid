package mx.sugus.braid.plugins.data;

import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.DefaultModelTransformerTask;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.plugins.data.model.AddOrderedToMapAndSets;
import software.amazon.smithy.model.node.ObjectNode;

public class OrderedCollectionsByDefaultPlugin implements SmithyGeneratorPlugin {
    public static final Identifier ID = Identifier.of(OrderedCollectionsByDefaultPlugin.class);

    public OrderedCollectionsByDefaultPlugin() {
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
                                     .taskId(Identifier.of(AddOrderedToMapAndSets.class))
                                     .transform(AddOrderedToMapAndSets::transform)
                                     .build())
            .build();
    }
}
