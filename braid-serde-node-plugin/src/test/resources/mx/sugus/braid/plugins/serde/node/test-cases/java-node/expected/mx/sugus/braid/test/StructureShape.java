package mx.sugus.braid.test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.SinkValidator;
import mx.sugus.braid.rt.util.Validation;
import mx.sugus.braid.rt.util.annotations.Generated;
import software.amazon.smithy.model.node.ArrayNode;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.node.ToNode;

@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
public final class StructureShape implements ToNode {
    private final Node node;
    private final Node anotherNode;
    private final List<Node> nodeList;
    private int _hashCode = 0;

    private StructureShape(Builder builder) {
        this.node = builder.node;
        this.anotherNode = Objects.requireNonNull(builder.anotherNode, "anotherNode");
        this.nodeList = Objects.requireNonNull(builder.nodeList.asPersistent(), "nodeList");
    }

    /**
     *
     * @return The value of the {@code node} member
     */
    public Node node() {
        return this.node;
    }

    /**
     *
     * @return The value of the {@code anotherNode} member
     */
    public Node anotherNode() {
        return this.anotherNode;
    }

    /**
     *
     * @return The value of the {@code nodeList} member
     */
    public List<Node> nodeList() {
        return this.nodeList;
    }

    /**
     * Returns a new builder to modify a copy of this instance.
     *
     * @return A new builder to modify a copy of this instance.
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        StructureShape that = (StructureShape) other;
        return Objects.equals(this.node, that.node)
               && this.anotherNode.equals(that.anotherNode)
               && this.nodeList.equals(that.nodeList);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + (node != null ? node.hashCode() : 0);
            hashCode = 31 * hashCode + anotherNode.hashCode();
            hashCode = 31 * hashCode + nodeList.hashCode();
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "StructureShape{"
               + "node: " + node
               + ", anotherNode: " + anotherNode
               + ", nodeList: " + nodeList + "}";
    }

    /**
     * Creates a new builder to create instances of this class.
     *
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Converts this instance to Node.
     */
    @Override
    public Node toNode() {
        ObjectNode.Builder builder = Node.objectNodeBuilder();
        if (node != null) {
            builder.withMember("node", node());
        }
        builder.withMember("anotherNode", anotherNode());
        if (!nodeList().isEmpty()) {
            ArrayNode.Builder nodeListBuilder = ArrayNode.builder();
            for (Node item : nodeList()) {
                nodeListBuilder.withValue(item);
            }
            builder.withMember("nodeList", nodeListBuilder.build());
        }
        return builder.build();
    }

    /**
     * Deserialize a StructureShape from a {@link Node}.
     *
     * @param node The node to deserialize from.
     * @return The deserialized instance.
     */
    public static StructureShape fromNode(Node node) {
        return fromNode(SinkValidator.instance(), node);
    }

    /**
     * Deserialize a StructureShape from a {@link Node}.
     *
     * @param validator A validator to collect any issues found during deserialization.
     * @param node The node to deserialize from.
     * @return The deserialized instance.
     */
    public static StructureShape fromNode(Validation validator, Node node) {
        validator = validator.with("StructureShape");
        StructureShape.Builder builder = builder();
        ObjectNode obj = node.expectObjectNode();
        for (Map.Entry<StringNode, Node> kvp : obj.getMembers().entrySet()) {
            Node value = kvp.getValue();
            String key = kvp.getKey().getValue();
            switch (key) {
                case "node":
                    builder.node(value);
                    break;
                case "anotherNode":
                    builder.anotherNode(value);
                    break;
                case "nodeList":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addNodeList(lstNodeValue);
                    }
                    break;
                default:
                    validator.report(Validation.Severity.WARNING, key, () -> String.format("unknown key `%s` with value `%s`", key, value));
                    break;
            }
        }
        return builder.build();
    }

    /**
     * A class to build instances of StructureShape
     */
    public static final class Builder {
        private Node node;
        private Node anotherNode;
        private CollectionBuilderReference<List<Node>> nodeList;

        Builder() {
            this.nodeList = CollectionBuilderReference.forList();
        }

        Builder(StructureShape data) {
            this.node = data.node;
            this.anotherNode = data.anotherNode;
            this.nodeList = CollectionBuilderReference.fromPersistentList(data.nodeList);
        }

        /**
         * Sets the value for {@code node}.
         *
         * @param node The value to be set.
         * @return This instance for chain calling.
         */
        public Builder node(Node node) {
            this.node = node;
            return this;
        }

        /**
         * Sets the value for {@code anotherNode}.
         *
         * @param anotherNode The value to be set.
         * @return This instance for chain calling.
         */
        public Builder anotherNode(Node anotherNode) {
            this.anotherNode = Objects.requireNonNull(anotherNode, "anotherNode");
            return this;
        }

        /**
         * Sets the value for {@code nodeList}.
         *
         * @param nodeList The value to be set.
         * @return This instance for chain calling.
         */
        public Builder nodeList(List<Node> nodeList) {
            this.nodeList.clear();
            this.nodeList.asTransient().addAll(nodeList);
            return this;
        }

        /**
         * Adds a value to {@code nodeList}.
         *
         * @param nodeList The value tp add
         * @return This instance for chain calling.
         */
        public Builder addNodeList(Node nodeList) {
            this.nodeList.asTransient().add(nodeList);
            return this;
        }

        /**
         * Returns a new instance of {@link StructureShape}
         *
         * @return A new instance of {@link StructureShape}
         */
        public StructureShape build() {
            return new StructureShape(this);
        }
    }
}