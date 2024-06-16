package mx.sugus.braid.plugins.syntax.config;

import java.util.List;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;
import software.amazon.smithy.model.node.ArrayNode;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.ToNode;

/**
 * <p>Config settings for the SyntaxModelPlugin</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
public final class SyntaxModelPluginConfig implements ToNode {
    private final List<String> syntaxNodes;

    private SyntaxModelPluginConfig(Builder builder) {
        this.syntaxNodes = Objects.requireNonNull(builder.syntaxNodes.asPersistent(), "syntaxNodes");
    }

    /**
     * <p>The shape ids of the shapes to be consider roots
     * for the syntax nodes.</p>
     */
    public List<String> syntaxNodes() {
        return this.syntaxNodes;
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
        SyntaxModelPluginConfig that = (SyntaxModelPluginConfig) obj;
        return this.syntaxNodes.equals(that.syntaxNodes);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + syntaxNodes.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "SyntaxModelPluginConfig{"
               + "syntaxNodes: " + syntaxNodes + "}";
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
        if (!this.syntaxNodes.isEmpty()) {
            ArrayNode.Builder syntaxNodesBuilder = ArrayNode.builder();
            for (String item : this.syntaxNodes) {
                syntaxNodesBuilder.withValue(Node.from(item));
            }
            builder.withMember("syntaxNodes", syntaxNodesBuilder.build());
        }
        return builder.build();
    }

    /**
     * <p>Converts a Node to SyntaxModelPluginConfig</p>
     */
    public static SyntaxModelPluginConfig fromNode(Node node) {
        SyntaxModelPluginConfig.Builder builder = builder();
        ObjectNode obj = node.expectObjectNode();
        obj.getArrayMember("syntaxNodes", nodes -> {
            for (Node item : nodes) {
                builder.addSyntaxNode(item.expectStringNode().getValue());
            }
        });
        return builder.build();
    }

    public static final class Builder {
        private CollectionBuilderReference<List<String>> syntaxNodes;

        Builder() {
            this.syntaxNodes = CollectionBuilderReference.forList();
        }

        Builder(SyntaxModelPluginConfig data) {
            this.syntaxNodes = CollectionBuilderReference.fromPersistentList(data.syntaxNodes);
        }

        /**
         * <p>Sets the value for <code>syntaxNodes</code></p>
         * <p>The shape ids of the shapes to be consider roots
         * for the syntax nodes.</p>
         */
        public Builder syntaxNodes(List<String> syntaxNodes) {
            this.syntaxNodes.clear();
            this.syntaxNodes.asTransient().addAll(syntaxNodes);
            return this;
        }

        /**
         * <p>Adds a single value for <code>syntaxNodes</code></p>
         */
        public Builder addSyntaxNode(String syntaxNode) {
            this.syntaxNodes.asTransient().add(syntaxNode);
            return this;
        }

        public SyntaxModelPluginConfig build() {
            return new SyntaxModelPluginConfig(this);
        }
    }
}
