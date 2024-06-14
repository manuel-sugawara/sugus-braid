package mx.sugus.braid.traits;

import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.AnnotationTrait;

public class AddBuilderReference extends AnnotationTrait {

    public static final ShapeId ID = ShapeId.from("mx.sugus.braid.traits#addBuilderReference");

    public AddBuilderReference(ObjectNode node) {
        super(ID, node);
    }

    public AddBuilderReference() {
        this(Node.objectNode());
    }

    public static final class Provider extends AnnotationTrait.Provider<AddBuilderReference> {
        public Provider() {
            super(ID, AddBuilderReference::new);
        }
    }
}

