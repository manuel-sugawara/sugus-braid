package mx.sugus.braid.traits;

import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.AnnotationTrait;

public final class InterfaceTrait extends AnnotationTrait {

    public static final ShapeId ID = ShapeId.from("mx.sugus.braid.traits#interface");

    public InterfaceTrait(ObjectNode node) {
        super(ID, node);
    }

    public InterfaceTrait() {
        this(Node.objectNode());
    }

    public static final class Provider extends AnnotationTrait.Provider<InterfaceTrait> {
        public Provider() {
            super(ID, InterfaceTrait::new);
        }
    }
}
