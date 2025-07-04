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
public final class Child implements ToNode {
    private final String stringValue;
    private final Integer intValue;

    private Child(Builder builder) {
        this.stringValue = builder.stringValue;
        this.intValue = builder.intValue;
    }

    public String stringValue() {
        return this.stringValue;
    }

    public Integer intValue() {
        return this.intValue;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance.</p>
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
        Child that = (Child) obj;
        return Objects.equals(this.stringValue, that.stringValue)
            && Objects.equals(this.intValue, that.intValue);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (stringValue != null ? stringValue.hashCode() : 0);
        hashCode = 31 * hashCode + (intValue != null ? intValue.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "Child{"
            + "stringValue: " + stringValue
            + ", intValue: " + intValue + "}";
    }

    /**
     * <p>Creates a new builder.</p>
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
        if (this.stringValue != null) {
            builder.withMember("stringValue", Node.from(this.stringValue));
        }
        if (this.intValue != null) {
            builder.withMember("intValue", Node.from(this.intValue));
        }
        return builder.build();
    }

    /**
     * <p>Converts a {@link Node} to Child</p>
     */
    public static Child fromNode(Node node) {
        return fromNode(SinkValidator.instance(), node);
    }

    /**
     * <p>Converts a {@link Node} to Child</p>
     */
    public static Child fromNode(Validation validator, Node node) {
        validator = validator.with("Child");
        Child.Builder builder = builder();
        ObjectNode obj = node.expectObjectNode();
        for (Map.Entry<StringNode, Node> kvp : obj.getMembers().entrySet()) {
            Node value = kvp.getValue();
            String key = kvp.getKey().getValue();
            switch (key) {
                case "stringValue":
                    builder.stringValue(value.expectStringNode().getValue());
                    break;
                case "intValue":
                    builder.intValue(value.expectNumberNode().getValue().intValue());
                    break;
                default:
                    validator.report(Validation.Severity.WARNING, key, () -> String.format("unknown key `%s` with value `%s`", key, value));
                    break;
            }
        }
        return builder.build();
    }

    public static final class Builder {
        private String stringValue;
        private Integer intValue;

        Builder() {
        }

        Builder(Child data) {
            this.stringValue = data.stringValue;
            this.intValue = data.intValue;
        }

        /**
         * <p>Sets the value for <code>stringValue</code>.</p>
         */
        public Builder stringValue(String stringValue) {
            this.stringValue = stringValue;
            return this;
        }

        /**
         * <p>Sets the value for <code>intValue</code>.</p>
         */
        public Builder intValue(Integer intValue) {
            this.intValue = intValue;
            return this;
        }

        public Child build() {
            return new Child(this);
        }
    }
}
