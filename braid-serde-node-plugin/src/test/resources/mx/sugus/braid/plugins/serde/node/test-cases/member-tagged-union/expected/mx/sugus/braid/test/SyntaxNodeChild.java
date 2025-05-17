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
     * Creates a new {@link Builder} to modify a copy of this instance
     */
    Builder toBuilder();

    static SyntaxNodeChild fromNode(Validation validation, Node node) {
        ObjectNode objectNode = node.expectObjectNode();
        ChildKind kind = ChildKind.from(objectNode.expectStringMember("kind").getValue());
        switch (kind) {
            case BAR:
                return ChildBar.fromNode(validation, node);
            case FOO:
                return ChildFoo.fromNode(validation, node);
            case BAZ:
                return ChildBaz.fromNode(validation, node);
            default:
                throw new IllegalArgumentException("Unknown enum variant: " + kind);
        }
    }

    interface Builder {

        /**
         * Builds a new instance of {@link SyntaxNodeChild}
         */
        SyntaxNodeChild build();
    }
}