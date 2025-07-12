package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.Validation;
import mx.sugus.braid.rt.util.annotations.Generated;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.ToNode;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface SyntaxNodeChild extends ToNode {

    ChildKind kind();

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    Builder toBuilder();

    /**
     * Deserialize a SyntaxNodeChild from a {@link Node}.
     * 
     * @param node The node to deserialize from.
     * @return The deserialized instance.
     */
    static SyntaxNodeChild fromNode(Validation validation, Node node) {
        ObjectNode objectNode = node.expectObjectNode();
        ChildKind kind = ChildKind.from(objectNode.expectStringMember("kind").getValue());
        switch (kind) {
            case BAR:
                return ChildBar.fromNode(validation, node);
            case BAZ:
                return ChildBaz.fromNode(validation, node);
            case FOO:
                return ChildFoo.fromNode(validation, node);
            default:
                throw new IllegalArgumentException("Unknown enum variant: " + kind);
        }
    }

    interface Builder {

        /**
         * Builds a new instance of {@link SyntaxNodeChild}
         * 
         * @return The new instance of of {@link SyntaxNodeChild}
         */
        SyntaxNodeChild build();
    }
}
