package mx.sugus.braid.traits;

import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.AnnotationTrait;

public final class OrderedTrait extends AnnotationTrait {

    public static final ShapeId ID = ShapeId.from("mx.sugus.braid.traits#ordered");

    public OrderedTrait(ObjectNode node) {
        super(ID, node);
    }

    public OrderedTrait() {
        this(Node.objectNode());
    }

    public static final class Provider extends AnnotationTrait.Provider<OrderedTrait> {
        public Provider() {
            super(ID, OrderedTrait::new);
        }
    }
}
