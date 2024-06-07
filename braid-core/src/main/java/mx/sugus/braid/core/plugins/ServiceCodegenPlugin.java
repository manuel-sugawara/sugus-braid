package mx.sugus.braid.core.plugins;

import java.util.Collection;
import java.util.LinkedHashSet;
import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ModelTransformerTask;
import mx.sugus.braid.core.plugin.ShapeSelectorTask;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import software.amazon.smithy.codegen.core.TopologicalIndex;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.neighbor.Walker;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.shapes.ServiceShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.transform.ModelTransformer;

/**
 * Plugin to configure the model shape selector and corresponding transforms for service codegen.
 */
public final class ServiceCodegenPlugin implements SmithyGeneratorPlugin {
    private final Identifier ID = Identifier.of(ServiceCodegenPlugin.class);

    @Override
    public Identifier provides() {
        return ID;
    }

    @Override
    public CodegenModuleConfig moduleConfig(ObjectNode node) {
        var service = node.expectStringMember("service").expectShapeId();
        var inputSuffix = node.getStringMember("inputSuffix").map(StringNode::getValue).orElse("Request");
        var outputSuffix = node.getStringMember("outputSuffix").map(StringNode::getValue).orElse("Response");
        return CodegenModuleConfig.builder()
                                  .shapeSelector(new ServiceShapeSelectorTask(service))
                                  .addModelTransformer(new ServiceModelTransformerTask(service, inputSuffix, outputSuffix))
                                  .build();
    }

    /**
     * Selects all the shapes connected to the given service shape id for service codegen.
     */
    static class ServiceShapeSelectorTask implements ShapeSelectorTask {
        public static Identifier ID = Identifier.of(ServiceShapeSelectorTask.class);

        private final ShapeId serviceId;

        ServiceShapeSelectorTask(ShapeId serviceId) {
            this.serviceId = serviceId;
        }

        @Override
        public Identifier taskId() {
            return ID;
        }

        @Override
        public Collection<Shape> select(Model model) {
            var serviceShape = model.expectShape(serviceId);
            var shapes = new Walker(model).walkShapes(serviceShape);
            var orderedShapes = new LinkedHashSet<Shape>();
            var topologicalIndex = TopologicalIndex.of(model);
            for (var shape : topologicalIndex.getOrderedShapes()) {
                if (shapes.contains(shape)) {
                    orderedShapes.add(shape);
                }
            }
            for (var shape : topologicalIndex.getRecursiveShapes()) {
                if (shapes.contains(shape)) {
                    orderedShapes.add(shape);
                }
            }
            orderedShapes.add(serviceShape);
            return orderedShapes;
        }
    }

    static class ServiceModelTransformerTask implements ModelTransformerTask {
        public static Identifier ID = Identifier.of(ServiceModelTransformerTask.class);
        private final ShapeId serviceId;
        private final String inputSuffix;
        private final String outputSuffix;

        ServiceModelTransformerTask(ShapeId serviceId, String inputSuffix, String outputSuffix) {
            this.serviceId = serviceId;
            this.inputSuffix = inputSuffix;
            this.outputSuffix = outputSuffix;
        }

        @Override
        public Identifier taskId() {
            return ID;
        }

        @Override
        public Model transform(Model model) {
            var transformer = ModelTransformer.create();
            var serviceShape = model.expectShape(serviceId, ServiceShape.class);
            var newModel = transformer.copyServiceErrorsToOperations(model, serviceShape);
            newModel = transformer.flattenAndRemoveMixins(newModel);
            newModel = transformer.changeStringEnumsToEnumShapes(newModel, true);
            newModel = transformer.createDedicatedInputAndOutput(newModel, inputSuffix, outputSuffix);
            return newModel;
        }
    }
}
