package mx.sugus.braid.test;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.lang.model.element.Modifier;
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
    private final Modifier modifier;
    private final Modifier anotherModifier;
    private final List<Modifier> modifierList;
    private int _hashCode = 0;

    private StructureShape(Builder builder) {
        this.modifier = builder.modifier;
        this.anotherModifier = Objects.requireNonNull(builder.anotherModifier, "anotherModifier");
        this.modifierList = Objects.requireNonNull(builder.modifierList.asPersistent(), "modifierList");
    }

    /**
     *
     * @return The value of the {@code modifier} member
     */
    public Modifier modifier() {
        return this.modifier;
    }

    /**
     *
     * @return The value of the {@code anotherModifier} member
     */
    public Modifier anotherModifier() {
        return this.anotherModifier;
    }

    /**
     *
     * @return The value of the {@code modifierList} member
     */
    public List<Modifier> modifierList() {
        return this.modifierList;
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
        return Objects.equals(this.modifier, that.modifier)
               && this.anotherModifier.equals(that.anotherModifier)
               && this.modifierList.equals(that.modifierList);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + (modifier != null ? modifier.hashCode() : 0);
            hashCode = 31 * hashCode + anotherModifier.hashCode();
            hashCode = 31 * hashCode + modifierList.hashCode();
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "StructureShape{"
               + "modifier: " + modifier
               + ", anotherModifier: " + anotherModifier
               + ", modifierList: " + modifierList + "}";
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
        if (modifier != null) {
            builder.withMember("modifier", modifier().toString());
        }
        builder.withMember("anotherModifier", anotherModifier().toString());
        if (!modifierList().isEmpty()) {
            ArrayNode.Builder modifierListBuilder = ArrayNode.builder();
            for (Modifier item : modifierList()) {
                modifierListBuilder.withValue(item.toString());
            }
            builder.withMember("modifierList", modifierListBuilder.build());
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
                case "modifier":
                    builder.modifier(Modifier.valueOf(item.expectStringNode().getValue().toUpperCase(Locale.US)));
                    break;
                case "anotherModifier":
                    builder.anotherModifier(Modifier.valueOf(item.expectStringNode().getValue().toUpperCase(Locale.US)));
                    break;
                case "modifierList":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addModifierList(Modifier.valueOf(lstNodeValue.expectStringNode().getValue().toUpperCase(Locale.US)));
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
        private Modifier modifier;
        private Modifier anotherModifier;
        private CollectionBuilderReference<List<Modifier>> modifierList;

        Builder() {
            this.modifierList = CollectionBuilderReference.forList();
        }

        Builder(StructureShape data) {
            this.modifier = data.modifier;
            this.anotherModifier = data.anotherModifier;
            this.modifierList = CollectionBuilderReference.fromPersistentList(data.modifierList);
        }

        /**
         * Sets the value for {@code modifier}.
         *
         * @param modifier The value to be set.
         * @return This instance for chain calling.
         */
        public Builder modifier(Modifier modifier) {
            this.modifier = modifier;
            return this;
        }

        /**
         * Sets the value for {@code anotherModifier}.
         *
         * @param anotherModifier The value to be set.
         * @return This instance for chain calling.
         */
        public Builder anotherModifier(Modifier anotherModifier) {
            this.anotherModifier = Objects.requireNonNull(anotherModifier, "anotherModifier");
            return this;
        }

        /**
         * Sets the value for {@code modifierList}.
         *
         * @param modifierList The value to be set.
         * @return This instance for chain calling.
         */
        public Builder modifierList(List<Modifier> modifierList) {
            this.modifierList.clear();
            this.modifierList.asTransient().addAll(modifierList);
            return this;
        }

        /**
         * Adds a value to {@code modifierList}.
         *
         * @param modifierList The value tp add
         * @return This instance for chain calling.
         */
        public Builder addModifierList(Modifier modifierList) {
            this.modifierList.asTransient().add(modifierList);
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
