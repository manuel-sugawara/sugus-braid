package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.ToNode;

@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
public final class Parent implements SyntaxNode, ToNode {
    private final SyntaxNodeChild child;
    private final AnotherChild anotherChild;

    private Parent(Builder builder) {
        this.child = builder.child;
        this.anotherChild = builder.anotherChild;
    }

    public SyntaxNodeChild child() {
        return this.child;
    }

    public AnotherChild anotherChild() {
        return this.anotherChild;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Parent that = (Parent) obj;
        return Objects.equals(this.child, that.child)
            && Objects.equals(this.anotherChild, that.anotherChild);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (child != null ? child.hashCode() : 0);
        hashCode = 31 * hashCode + (anotherChild != null ? anotherChild.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "Parent{"
            + "child: " + child
            + ", anotherChild: " + anotherChild + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * <p>Converts this instance to Node</p>
     */
    @Override
    public Node toNode() {
        ObjectNode.Builder builder = Node.objectNodeBuilder();
        if (child != null) {
            builder.withMember("child", this.child.toNode());
        }
        if (anotherChild != null) {
            builder.withMember("anotherChild", this.anotherChild.toNode());
        }
        return builder.build();
    }

    /**
     * <p>Converts a Node to Parent</p>
     */
    public static Parent fromNode(Node node) {
        Parent.Builder builder = builder();
        ObjectNode obj = node.expectObjectNode();
        obj.getMember("child").map(SyntaxNodeChild::fromNode).ifPresent(builder::child);
        obj.getMember("anotherChild").map(AnotherChild::fromNode).ifPresent(builder::anotherChild);
        return builder.build();
    }

    public static final class Builder implements SyntaxNode.Builder {
        private SyntaxNodeChild child;
        private AnotherChild anotherChild;

        Builder() {
        }

        Builder(Parent data) {
            this.child = data.child;
            this.anotherChild = data.anotherChild;
        }

        /**
         * <p>Sets the value for <code>child</code></p>
         */
        public Builder child(SyntaxNodeChild child) {
            this.child = child;
            return this;
        }

        /**
         * <p>Sets the value for <code>anotherChild</code></p>
         */
        public Builder anotherChild(AnotherChild anotherChild) {
            this.anotherChild = anotherChild;
            return this;
        }

        public Parent build() {
            return new Parent(this);
        }
    }
}