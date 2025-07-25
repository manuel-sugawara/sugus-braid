package mx.sugus.braid.core;

import java.util.HashSet;
import java.util.Set;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.knowledge.KnowledgeIndex;
import software.amazon.smithy.model.shapes.ListShape;
import software.amazon.smithy.model.shapes.MapShape;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.OperationShape;
import software.amazon.smithy.model.shapes.ResourceShape;
import software.amazon.smithy.model.shapes.ServiceShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeVisitor;
import software.amazon.smithy.model.shapes.StructureShape;
import software.amazon.smithy.model.shapes.ToShapeId;
import software.amazon.smithy.model.shapes.UnionShape;
import software.amazon.smithy.model.traits.SensitiveTrait;

/**
 * A knowledge index that tracks which shapes in a Smithy model contain sensitive data.
 *
 * <p>This index analyzes the entire model to determine which shapes are marked as sensitive,
 * either directly through the {@code @sensitive} trait or transitively through their relationships with other sensitive shapes.
 * This information is crucial for generating appropriate handling code for sensitive data in the final output.
 *
 * <p>The index considers the following shapes as sensitive:
 * <ul>
 *   <li>Shapes directly annotated with the {@code @sensitive} trait</li>
 *   <li>Container shapes (lists, maps) whose members are sensitive</li>
 *   <li>Structure and union shapes that contain sensitive members</li>
 * </ul>
 *
 * <p>Service, operation, and resource shapes are never considered sensitive as they
 * represent structural elements rather than data containers.
 *
 * @see software.amazon.smithy.model.traits.SensitiveTrait
 */
public class SensitiveKnowledgeIndex implements KnowledgeIndex {

    private final Set<ShapeId> sensitiveShapes = new HashSet<>();

    SensitiveKnowledgeIndex(Model model) {
        var visitor = new ComputeSensitive(model);
        for (var shapeId : model.getShapeIds()) {
            var result = model.expectShape(shapeId).accept(visitor);
            if (result) {
                sensitiveShapes.add(shapeId);
            }
        }
    }

    /**
     * Checks whether the specified shape contains sensitive data.
     *
     * <p>This method returns {@code true} if the shape is marked as sensitive either
     * directly through the {@code @sensitive} trait or transitively through its relationship with other sensitive shapes.
     *
     * @param toShapeId The shape to check for sensitivity
     * @return {@code true} if the shape contains sensitive data, {@code false} otherwise
     */
    public boolean isSensitive(ToShapeId toShapeId) {
        return sensitiveShapes.contains(toShapeId.toShapeId());
    }

    /**
     * Creates or retrieves a SensitiveKnowledgeIndex for the given model.
     *
     * <p>This method uses Smithy's knowledge index caching mechanism to ensure
     * that only one index is created per model, improving performance when the index is accessed multiple times.
     *
     * @param model The Smithy model to analyze for sensitive shapes
     * @return A SensitiveKnowledgeIndex for the model
     */
    public static SensitiveKnowledgeIndex of(Model model) {
        return model.getKnowledge(SensitiveKnowledgeIndex.class, SensitiveKnowledgeIndex::new);
    }

    static class ComputeSensitive extends ShapeVisitor.Default<Boolean> {
        private final Model model;

        ComputeSensitive(Model model) {
            this.model = model;
        }

        @Override
        protected Boolean getDefault(Shape shape) {
            return shape.hasTrait(SensitiveTrait.class);
        }

        @Override
        public Boolean listShape(ListShape shape) {
            return hasSensitiveTrait(shape) || shape.getMember().accept(this);
        }

        @Override
        public Boolean mapShape(MapShape shape) {
            return hasSensitiveTrait(shape)
                   || shape.getKey().accept(this)
                   || shape.getValue().accept(this);
        }

        @Override
        public Boolean operationShape(OperationShape shape) {
            return false;
        }

        @Override
        public Boolean resourceShape(ResourceShape shape) {
            return false;
        }

        @Override
        public Boolean serviceShape(ServiceShape shape) {
            return false;
        }

        @Override
        public Boolean structureShape(StructureShape shape) {
            return hasSensitiveTrait(shape);
        }

        @Override
        public Boolean unionShape(UnionShape shape) {
            // We stop at the member level.
            return hasSensitiveTrait(shape);
        }

        @Override
        public Boolean memberShape(MemberShape shape) {
            return hasSensitiveTrait(shape) || model.expectShape(shape.getTarget()).accept(this);
        }

        private static boolean hasSensitiveTrait(Shape shape) {
            return shape.hasTrait(SensitiveTrait.class);
        }
    }
}
