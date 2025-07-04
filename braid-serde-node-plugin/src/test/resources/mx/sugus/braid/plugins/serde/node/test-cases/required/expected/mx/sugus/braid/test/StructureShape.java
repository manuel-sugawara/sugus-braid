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

    public EnumShape enumValue() {
        return this.enumValue;
    }

    public String stringMember() {
        return this.stringMember;
    }

    public SimpleStructure structureShape() {
        return this.structureShape;
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
        StructureShape that = (StructureShape) obj;
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
        builder.withMember("enumValue", Node.from(this.enumValue.toString()));
        builder.withMember("stringMember", Node.from(this.stringMember));
        builder.withMember("structureShape", this.structureShape.toNode());
        return builder.build();
    }

    /**
     * <p>Converts a {@link Node} to StructureShape</p>
     */
    public static StructureShape fromNode(Node node) {
        return fromNode(SinkValidator.instance(), node);
    }

    /**
     * <p>Converts a {@link Node} to StructureShape</p>
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
         * <p>Sets the value for <code>enumValue</code>.</p>
         */
        public Builder enumValue(EnumShape enumValue) {
            this.enumValue = enumValue;
            return this;
        }

        /**
         * <p>Sets the value for <code>stringMember</code>.</p>
         */
        public Builder stringMember(String stringMember) {
            this.stringMember = stringMember;
            return this;
        }

        /**
         * <p>Sets the value for <code>structureShape</code>.</p>
         */
        public Builder structureShape(SimpleStructure structureShape) {
            this.structureShape = structureShape;
            return this;
        }

        public StructureShape build() {
            return new StructureShape(this);
        }
    }
}
