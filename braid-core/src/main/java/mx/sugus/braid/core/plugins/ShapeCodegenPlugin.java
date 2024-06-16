package mx.sugus.braid.core.plugins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ModelTransformerTask;
import mx.sugus.braid.core.plugin.ShapeSelectorTask;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import software.amazon.smithy.codegen.core.TopologicalIndex;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.loader.Prelude;
import software.amazon.smithy.model.neighbor.Walker;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.selector.Selector;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.transform.ModelTransformer;

/**
 * Plugin to configure the model shape selector and corresponding transforms for service codegen.
 */
public final class ShapeCodegenPlugin implements SmithyGeneratorPlugin<ObjectNode> {
    private final Identifier ID = Identifier.of(ShapeCodegenPlugin.class);

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
        var selectorSource = node.expectStringMember("selector").getValue();
        var selector = Selector.parse(selectorSource);
        return CodegenModuleConfig.builder()
                                  .shapeSelector(new SelectorShapeSelectorTask(selector))
                                  .addModelTransformer(new ShapeCodegenModelTransformerTask())
                                  .build();
    }

    /**
     * Selects all the shapes connected to the given service shape id for service codegen.
     */
    static class SelectorShapeSelectorTask implements ShapeSelectorTask {
        public static Identifier ID = Identifier.of(SelectorShapeSelectorTask.class);

        private final Selector selector;

        SelectorShapeSelectorTask(Selector selector) {
            this.selector = selector;
        }

        @Override
        public Identifier taskId() {
            return ID;
        }

        @Override
        public Collection<Shape> select(Model model) {
            var selected = selector.select(model);
            var selectedAndNeighbors = new HashSet<>(selected);
            var walker = new Walker(model);
            for (var shape : selected) {
                for (var neighbor : walker.walkShapes(shape)) {
                    if (!neighbor.isMemberShape() && !Prelude.isPreludeShape(neighbor)) {
                        selectedAndNeighbors.add(neighbor);
                    }
                }
            }
            var topologicalIndex = TopologicalIndex.of(model);
            var nonConnected = new HashSet<>(selectedAndNeighbors);
            var orderedShapes = new ArrayList<Shape>();
            for (var shape : topologicalIndex.getOrderedShapes()) {
                if (selectedAndNeighbors.contains(shape)) {
                    orderedShapes.add(shape);
                    nonConnected.remove(shape);
                }
            }
            // Add at the end any remaining, non-connected shape.
            orderedShapes.addAll(nonConnected);
            return orderedShapes;
        }
    }

    static class ShapeCodegenModelTransformerTask implements ModelTransformerTask {
        public static Identifier ID = Identifier.of(ShapeCodegenModelTransformerTask.class);

        @Override
        public Identifier taskId() {
            return ID;
        }

        @Override
        public Model transform(Model model) {
            var transformer = ModelTransformer.create();
            var newModel = transformer.flattenAndRemoveMixins(model);
            newModel = transformer.changeStringEnumsToEnumShapes(newModel, true);
            return newModel;
        }
    }
}
