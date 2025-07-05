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

    public Modifier modifier() {
        return this.modifier;
    }

    public Modifier anotherModifier() {
        return this.anotherModifier;
    }

    public List<Modifier> modifierList() {
        return this.modifierList;
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
        if (modifier != null) {
            builder.withMember("modifier", this.modifier.toString());
        }
        builder.withMember("anotherModifier", this.anotherModifier.toString());
        if (!this.modifierList.isEmpty()) {
            ArrayNode.Builder modifierListBuilder = ArrayNode.builder();
            for (Modifier item : this.modifierList) {
                modifierListBuilder.withValue(item.toString());
            }
            builder.withMember("modifierList", modifierListBuilder.build());
        }
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
         * <p>Sets the value for <code>modifier</code>.</p>
         */
        public Builder modifier(Modifier modifier) {
            this.modifier = modifier;
            return this;
        }

        /**
         * <p>Sets the value for <code>anotherModifier</code>.</p>
         */
        public Builder anotherModifier(Modifier anotherModifier) {
            this.anotherModifier = anotherModifier;
            return this;
        }

        /**
         * <p>Sets the value for <code>modifierList</code>.</p>
         */
        public Builder modifierList(List<Modifier> modifierList) {
            this.modifierList.clear();
            this.modifierList.asTransient().addAll(modifierList);
            return this;
        }

        /**
         * <p>Adds a single value for <code>modifierList</code>.</p>
         */
        public Builder addModifierList(Modifier modifierList) {
            this.modifierList.asTransient().add(modifierList);
            return this;
        }

        public StructureShape build() {
            return new StructureShape(this);
        }
    }
}
