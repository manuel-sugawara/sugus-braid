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

    public boolean isSensitive(ToShapeId toShapeId) {
        return sensitiveShapes.contains(toShapeId.toShapeId());
    }

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
            return hasSensitiveTrait(shape) || model.getShape(shape.getTarget()).map(s -> s.accept(this)).orElse(false);
        }

        private static boolean hasSensitiveTrait(Shape shape) {
            return shape.hasTrait(SensitiveTrait.class);
        }
    }
}
