package mx.sugus.braid.traits;

import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.AnnotationTrait;

// nterfaceTrait.java CodegenIgnoreTrait.jav

public final class CodegenIgnoreTrait extends AnnotationTrait {

    public static final ShapeId ID = ShapeId.from("mx.sugus.braid.traits#ignore");

    public CodegenIgnoreTrait(ObjectNode node) {
        super(ID, node);
    }

    public CodegenIgnoreTrait() {
        this(Node.objectNode());
    }

    public static final class Provider extends AnnotationTrait.Provider<CodegenIgnoreTrait> {
        public Provider() {
            super(ID, CodegenIgnoreTrait::new);
        }
    }
}
