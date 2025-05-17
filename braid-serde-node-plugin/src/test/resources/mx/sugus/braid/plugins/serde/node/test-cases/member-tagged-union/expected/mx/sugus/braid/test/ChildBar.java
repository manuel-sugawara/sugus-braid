package mx.sugus.braid.test;

import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.rt.util.SinkValidator;
import mx.sugus.braid.rt.util.Validation;
import mx.sugus.braid.rt.util.annotations.Generated;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.node.ToNode;

@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
public final class ChildBar implements SyntaxNodeChild, SyntaxNode, ToNode {
    private final String bar;

    private ChildBar(Builder builder) {
        this.bar = builder.bar;
    }

    public ChildKind kind() {
        return ChildKind.BAR;
    }

    public String bar() {
        return this.bar;
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
        ChildBar that = (ChildBar) obj;
        return Objects.equals(this.bar, that.bar);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.kind().hashCode();
        hashCode = 31 * hashCode + (bar != null ? bar.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "ChildBar{"
               + "kind: " + kind()
               + ", bar: " + bar + "}";
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
        builder.withMember("kind", Node.from(this.kind().toString()));
        if (this.bar != null) {
            builder.withMember("bar", Node.from(this.bar));
        }
        return builder.build();
    }

    /**
     * <p>Converts a {@link Node} to ChildBar</p>
     */
    public static ChildBar fromNode(Node node) {
        return fromNode(SinkValidator.instance(), node);
    }

    /**
     * <p>Converts a {@link Node} to ChildBar</p>
     */
    public static ChildBar fromNode(Validation validator, Node node) {
        validator = validator.with("ChildBar");
        ChildBar.Builder builder = builder();
        ObjectNode obj = node.expectObjectNode();
        for (Map.Entry<StringNode, Node> kvp : obj.getMembers().entrySet()) {
            Node value = kvp.getValue();
            String key = kvp.getKey().getValue();
            switch (key) {
                case "kind":
                    break;
                case "bar":
                    builder.bar(value.expectStringNode().getValue());
                    break;
                default:
                    validator.report(Validation.Severity.WARNING, key, () -> String.format("unknown key `%s` with value `%s`", key, value));
                    break;
            }
        }
        return builder.build();
    }

    public static final class Builder implements SyntaxNodeChild.Builder, SyntaxNode.Builder {
        private String bar;

        Builder() {
        }

        Builder(ChildBar data) {
            this.bar = data.bar;
        }

        /**
         * <p>Sets the value for <code>bar</code></p>
         */
        public Builder bar(String bar) {
            this.bar = bar;
            return this;
        }

        public ChildBar build() {
            return new ChildBar(this);
        }
    }
}