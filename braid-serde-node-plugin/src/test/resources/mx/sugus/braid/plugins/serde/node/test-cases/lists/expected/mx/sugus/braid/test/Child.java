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

    /**
     * 
     * @return The value of the {@code stringValue} member
     */
    public String stringValue() {
        return this.stringValue;
    }

    /**
     * 
     * @return The value of the {@code intValue} member
     */
    public Integer intValue() {
        return this.intValue;
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
        Child that = (Child) other;
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
        builder.withMember("stringValue", Node.from(stringValue()));
        builder.withMember("intValue", Node.from(intValue()));
        return builder.build();
    }

    /**
     * Deserialize a Child from a {@link Node}.
     * 
     * @param node The node to deserialize from.
     * @return The deserialized instance.
     */
    public static Child fromNode(Node node) {
        return fromNode(SinkValidator.instance(), node);
    }

    /**
     * Deserialize a Child from a {@link Node}.
     * 
     * @param validator A validator to collect any issues found during deserialization.
     * @param node The node to deserialize from.
     * @return The deserialized instance.
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

    /**
     * A class to build instances of Child
     */
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
         * Sets the value for {@code stringValue}.
         * 
         * @param stringValue The value to be set.
         * @return This instance for chain calling.
         */
        public Builder stringValue(String stringValue) {
            this.stringValue = stringValue;
            return this;
        }

        /**
         * Sets the value for {@code intValue}.
         * 
         * @param intValue The value to be set.
         * @return This instance for chain calling.
         */
        public Builder intValue(Integer intValue) {
            this.intValue = intValue;
            return this;
        }

        /**
         * Returns a new instance of {@link Child}
         * 
         * @return A new instance of {@link Child}
         */
        public Child build() {
            return new Child(this);
        }
    }
}
