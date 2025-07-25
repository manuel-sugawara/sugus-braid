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
 * A plugin that configures code generation for shape-based Smithy models using selector queries.
 *
 * <p>This plugin provides a flexible alternative to service-oriented code generation by allowing
 * the use of Smithy selector expressions to choose exactly which shapes should be processed. This approach is ideal for
 * generating data type libraries, utility classes, or any scenario where fine-grained control over shape selection is needed.
 *
 * <p>Key features:
 * <ul>
 *   <li><strong>Selector-based shape selection:</strong> Uses Smithy selector syntax to
 *       precisely control which shapes are included in code generation</li>
 *   <li><strong>Neighbor discovery:</strong> Automatically includes shapes referenced by
 *       selected shapes to ensure complete type definitions</li>
 *   <li><strong>Topological ordering:</strong> Ensures shapes are processed in dependency order</li>
 *   <li><strong>Standard transformations:</strong> Applies common model preprocessing including
 *       mixin flattening and enum conversion</li>
 * </ul>
 *
 * <p>Configuration:
 * <ul>
 *   <li>{@code selector}: A Smithy selector expression defining which shapes to process</li>
 * </ul>
 *
 * <p>Example selectors:
 * <ul>
 *   <li>{@code structure}: Select all structure shapes</li>
 *   <li>{@code [namespace = 'com.example.types']}: Select shapes in a specific namespace</li>
 *   <li>{@code structure:not([trait|service])}: Select structures not used by services</li>
 * </ul>
 *
 * @see software.amazon.smithy.model.selector.Selector
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
