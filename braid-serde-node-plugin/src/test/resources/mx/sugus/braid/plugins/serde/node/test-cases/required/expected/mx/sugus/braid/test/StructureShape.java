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
public final class StructureShape implements ToNode {
    private final EnumShape enumValue;
    private final String stringMember;
    private final SimpleStructure structureShape;
    private int _hashCode = 0;

    private StructureShape(Builder builder) {
        this.enumValue = Objects.requireNonNull(builder.enumValue, "enumValue");
        this.stringMember = Objects.requireNonNull(builder.stringMember, "stringMember");
        this.structureShape = Objects.requireNonNull(builder.structureShape, "structureShape");
    }

    /**
     * 
     * @return The value of the {@code enumValue} member
     */
    public EnumShape enumValue() {
        return this.enumValue;
    }

    /**
     * 
     * @return The value of the {@code stringMember} member
     */
    public String stringMember() {
        return this.stringMember;
    }

    /**
     * 
     * @return The value of the {@code structureShape} member
     */
    public SimpleStructure structureShape() {
        return this.structureShape;
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
        return this.enumValue.equals(that.enumValue)
            && this.stringMember.equals(that.stringMember)
            && this.structureShape.equals(that.structureShape);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + enumValue.hashCode();
            hashCode = 31 * hashCode + stringMember.hashCode();
            hashCode = 31 * hashCode + structureShape.hashCode();
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "StructureShape{"
            + "enumValue: " + enumValue
            + ", stringMember: " + stringMember
            + ", structureShape: " + structureShape + "}";
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
        builder.withMember("enumValue", Node.from(enumValue().toString()));
        builder.withMember("stringMember", Node.from(stringMember()));
        builder.withMember("structureShape", structureShape().toNode());
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
                case "enumValue":
                    builder.enumValue(EnumShape.from(value.expectStringNode().getValue()));
                    break;
                case "stringMember":
                    builder.stringMember(value.expectStringNode().getValue());
                    break;
                case "structureShape":
                    builder.structureShape(SimpleStructure.fromNode(validator.with("structureShape"), value.expectObjectNode()));
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
        private EnumShape enumValue;
        private String stringMember;
        private SimpleStructure structureShape;

        Builder() {
        }

        Builder(StructureShape data) {
            this.enumValue = data.enumValue;
            this.stringMember = data.stringMember;
            this.structureShape = data.structureShape;
        }

        /**
         * Sets the value for {@code enumValue}.
         * 
         * @param enumValue The value to be set.
         * @return This instance for chain calling.
         */
        public Builder enumValue(EnumShape enumValue) {
            this.enumValue = Objects.requireNonNull(enumValue, "enumValue");
            return this;
        }

        /**
         * Sets the value for {@code stringMember}.
         * 
         * @param stringMember The value to be set.
         * @return This instance for chain calling.
         */
        public Builder stringMember(String stringMember) {
            this.stringMember = Objects.requireNonNull(stringMember, "stringMember");
            return this;
        }

        /**
         * Sets the value for {@code structureShape}.
         * 
         * @param structureShape The value to be set.
         * @return This instance for chain calling.
         */
        public Builder structureShape(SimpleStructure structureShape) {
            this.structureShape = Objects.requireNonNull(structureShape, "structureShape");
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
