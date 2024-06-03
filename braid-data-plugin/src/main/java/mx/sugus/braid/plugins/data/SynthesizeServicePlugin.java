package mx.sugus.braid.plugins.data;

import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.DefaultModelTransformerTask;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.core.transforms.SynthesizeServiceTransform;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeIdSyntaxException;

public final class SynthesizeServicePlugin implements SmithyGeneratorPlugin {

    public static final Identifier ID = Identifier.of(SynthesizeServicePlugin.class);

    static ShapeId configuredServiceId(ObjectNode node) {
        var serviceId = node.getMember("service").map(x -> x.asStringNode().map(StringNode::getValue).orElse(null))
                            .orElse(null);
        if (serviceId == null) {
            throw new IllegalArgumentException("SynthesizeServicePlugin requires a service configuration set");
        }
        try {
            return ShapeId.from(serviceId);
        } catch (ShapeIdSyntaxException e) {
            throw new IllegalArgumentException("SynthesizeServicePlugin `service` configuration is not a valid shape id", e);
        }
    }

    static CodegenModuleConfig newBaseConfig(ShapeId serviceId) {
        return CodegenModuleConfig
            .builder()
            .addModelEarlyTransformer(DefaultModelTransformerTask.builder()
                                                                 .taskId(Identifier.of(SynthesizeServiceTransform.class))
                                                                 .transform(SynthesizeServiceTransform.transformer(serviceId))
                                                                 .build())
            .build();
    }

    @Override
    public Identifier provides() {
        return ID;
    }

    @Override
    public CodegenModuleConfig moduleConfig(ObjectNode node) {
        return newBaseConfig(configuredServiceId(node));
    }

}
