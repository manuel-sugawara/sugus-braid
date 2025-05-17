package mx.sugus.braid.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.SinkValidator;
import mx.sugus.braid.rt.util.Validation;
import mx.sugus.braid.rt.util.annotations.Generated;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.node.ToNode;

@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
public final class Parent implements ToNode {
    private final String stringMember;
    private final Map<String, Child> children;
    private final Map<String, Boolean> booleans;
    private final Map<String, Byte> bytes;
    private final Map<String, Short> shorts;
    private final Map<String, Integer> integers;
    private final Map<String, BigInteger> bigIntegers;
    private final Map<String, Long> longs;
    private final Map<String, Float> floats;
    private final Map<String, Double> doubles;
    private final Map<String, String> strings;
    private final Map<String, BigDecimal> bigDecimals;
    private final Map<String, EnumValue> enumValues;
    private int _hashCode = 0;

    private Parent(Builder builder) {
        this.stringMember = builder.stringMember;
        this.children = Objects.requireNonNull(builder.children.asPersistent(), "children");
        this.booleans = Objects.requireNonNull(builder.booleans.asPersistent(), "booleans");
        this.bytes = Objects.requireNonNull(builder.bytes.asPersistent(), "bytes");
        this.shorts = Objects.requireNonNull(builder.shorts.asPersistent(), "shorts");
        this.integers = Objects.requireNonNull(builder.integers.asPersistent(), "integers");
        this.bigIntegers = Objects.requireNonNull(builder.bigIntegers.asPersistent(), "bigIntegers");
        this.longs = Objects.requireNonNull(builder.longs.asPersistent(), "longs");
        this.floats = Objects.requireNonNull(builder.floats.asPersistent(), "floats");
        this.doubles = Objects.requireNonNull(builder.doubles.asPersistent(), "doubles");
        this.strings = Objects.requireNonNull(builder.strings.asPersistent(), "strings");
        this.bigDecimals = Objects.requireNonNull(builder.bigDecimals.asPersistent(), "bigDecimals");
        this.enumValues = Objects.requireNonNull(builder.enumValues.asPersistent(), "enumValues");
    }

    public String stringMember() {
        return this.stringMember;
    }

    public Map<String, Child> children() {
        return this.children;
    }

    public Map<String, Boolean> booleans() {
        return this.booleans;
    }

    public Map<String, Byte> bytes() {
        return this.bytes;
    }

    public Map<String, Short> shorts() {
        return this.shorts;
    }

    public Map<String, Integer> integers() {
        return this.integers;
    }

    public Map<String, BigInteger> bigIntegers() {
        return this.bigIntegers;
    }

    public Map<String, Long> longs() {
        return this.longs;
    }

    public Map<String, Float> floats() {
        return this.floats;
    }

    public Map<String, Double> doubles() {
        return this.doubles;
    }

    public Map<String, String> strings() {
        return this.strings;
    }

    public Map<String, BigDecimal> bigDecimals() {
        return this.bigDecimals;
    }

    public Map<String, EnumValue> enumValues() {
        return this.enumValues;
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
        return Objects.equals(this.stringMember, that.stringMember)
               && this.children.equals(that.children)
               && this.booleans.equals(that.booleans)
               && this.bytes.equals(that.bytes)
               && this.shorts.equals(that.shorts)
               && this.integers.equals(that.integers)
               && this.bigIntegers.equals(that.bigIntegers)
               && this.longs.equals(that.longs)
               && this.floats.equals(that.floats)
               && this.doubles.equals(that.doubles)
               && this.strings.equals(that.strings)
               && this.bigDecimals.equals(that.bigDecimals)
               && this.enumValues.equals(that.enumValues);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + (stringMember != null ? stringMember.hashCode() : 0);
            hashCode = 31 * hashCode + children.hashCode();
            hashCode = 31 * hashCode + booleans.hashCode();
            hashCode = 31 * hashCode + bytes.hashCode();
            hashCode = 31 * hashCode + shorts.hashCode();
            hashCode = 31 * hashCode + integers.hashCode();
            hashCode = 31 * hashCode + bigIntegers.hashCode();
            hashCode = 31 * hashCode + longs.hashCode();
            hashCode = 31 * hashCode + floats.hashCode();
            hashCode = 31 * hashCode + doubles.hashCode();
            hashCode = 31 * hashCode + strings.hashCode();
            hashCode = 31 * hashCode + bigDecimals.hashCode();
            hashCode = 31 * hashCode + enumValues.hashCode();
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "Parent{"
               + "stringMember: " + stringMember
               + ", children: " + children
               + ", booleans: " + booleans
               + ", bytes: " + bytes
               + ", shorts: " + shorts
               + ", integers: " + integers
               + ", bigIntegers: " + bigIntegers
               + ", longs: " + longs
               + ", floats: " + floats
               + ", doubles: " + doubles
               + ", strings: " + strings
               + ", bigDecimals: " + bigDecimals
               + ", enumValues: " + enumValues + "}";
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
        if (this.stringMember != null) {
            builder.withMember("stringMember", Node.from(this.stringMember));
        }
        if (!this.children.isEmpty()) {
            ObjectNode.Builder childrenBuilder = ObjectNode.builder();
            for (Map.Entry<String, Child> kvp : this.children.entrySet()) {
                childrenBuilder.withMember(kvp.getKey(), kvp.getValue().toNode());
            }
            builder.withMember("children", childrenBuilder.build());
        }
        if (!this.booleans.isEmpty()) {
            ObjectNode.Builder booleansBuilder = ObjectNode.builder();
            for (Map.Entry<String, Boolean> kvp : this.booleans.entrySet()) {
                booleansBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("booleans", booleansBuilder.build());
        }
        if (!this.bytes.isEmpty()) {
            ObjectNode.Builder bytesBuilder = ObjectNode.builder();
            for (Map.Entry<String, Byte> kvp : this.bytes.entrySet()) {
                bytesBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("bytes", bytesBuilder.build());
        }
        if (!this.shorts.isEmpty()) {
            ObjectNode.Builder shortsBuilder = ObjectNode.builder();
            for (Map.Entry<String, Short> kvp : this.shorts.entrySet()) {
                shortsBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("shorts", shortsBuilder.build());
        }
        if (!this.integers.isEmpty()) {
            ObjectNode.Builder integersBuilder = ObjectNode.builder();
            for (Map.Entry<String, Integer> kvp : this.integers.entrySet()) {
                integersBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("integers", integersBuilder.build());
        }
        if (!this.bigIntegers.isEmpty()) {
            ObjectNode.Builder bigIntegersBuilder = ObjectNode.builder();
            for (Map.Entry<String, BigInteger> kvp : this.bigIntegers.entrySet()) {
                bigIntegersBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue().toString()));
            }
            builder.withMember("bigIntegers", bigIntegersBuilder.build());
        }
        if (!this.longs.isEmpty()) {
            ObjectNode.Builder longsBuilder = ObjectNode.builder();
            for (Map.Entry<String, Long> kvp : this.longs.entrySet()) {
                longsBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("longs", longsBuilder.build());
        }
        if (!this.floats.isEmpty()) {
            ObjectNode.Builder floatsBuilder = ObjectNode.builder();
            for (Map.Entry<String, Float> kvp : this.floats.entrySet()) {
                floatsBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("floats", floatsBuilder.build());
        }
        if (!this.doubles.isEmpty()) {
            ObjectNode.Builder doublesBuilder = ObjectNode.builder();
            for (Map.Entry<String, Double> kvp : this.doubles.entrySet()) {
                doublesBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("doubles", doublesBuilder.build());
        }
        if (!this.strings.isEmpty()) {
            ObjectNode.Builder stringsBuilder = ObjectNode.builder();
            for (Map.Entry<String, String> kvp : this.strings.entrySet()) {
                stringsBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("strings", stringsBuilder.build());
        }
        if (!this.bigDecimals.isEmpty()) {
            ObjectNode.Builder bigDecimalsBuilder = ObjectNode.builder();
            for (Map.Entry<String, BigDecimal> kvp : this.bigDecimals.entrySet()) {
                bigDecimalsBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue().toString()));
            }
            builder.withMember("bigDecimals", bigDecimalsBuilder.build());
        }
        if (!this.enumValues.isEmpty()) {
            ObjectNode.Builder enumValuesBuilder = ObjectNode.builder();
            for (Map.Entry<String, EnumValue> kvp : this.enumValues.entrySet()) {
                enumValuesBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue().toString()));
            }
            builder.withMember("enumValues", enumValuesBuilder.build());
        }
        return builder.build();
    }

    /**
     * <p>Converts a {@link Node} to Parent</p>
     */
    public static Parent fromNode(Node node) {
        return fromNode(SinkValidator.instance(), node);
    }

    /**
     * <p>Converts a {@link Node} to Parent</p>
     */
    public static Parent fromNode(Validation validator, Node node) {
        Parent.Builder builder = builder();
        ObjectNode obj = node.expectObjectNode();
        for (Map.Entry<StringNode, Node> kvp : obj.getMembers().entrySet()) {
            Node value = kvp.getValue();
            String key = kvp.getKey().getValue();
            switch (key) {
                case "stringMember":
                    builder.stringMember(value.expectStringNode().getValue());
                    break;
                case "children":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        builder.putChildren(memberKvp.getKey().getValue(), Child.fromNode(validator, valueNode));
                    }
                    break;
                case "booleans":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        builder.putBoolean(memberKvp.getKey().getValue(), valueNode.expectBooleanNode().getValue());
                    }
                    break;
                case "bytes":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        builder.putByte(memberKvp.getKey().getValue(), valueNode.expectNumberNode().getValue().byteValue());
                    }
                    break;
                case "shorts":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        builder.putShort(memberKvp.getKey().getValue(), valueNode.expectNumberNode().getValue().shortValue());
                    }
                    break;
                case "integers":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        builder.putInteger(memberKvp.getKey().getValue(), valueNode.expectNumberNode().getValue().intValue());
                    }
                    break;
                case "bigIntegers":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        builder.putBigInteger(memberKvp.getKey().getValue(), valueNode.expectNumberNode().asBigDecimal().get().toBigInteger());
                    }
                    break;
                case "longs":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        builder.putLong(memberKvp.getKey().getValue(), valueNode.expectNumberNode().getValue().longValue());
                    }
                    break;
                case "floats":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        builder.putFloat(memberKvp.getKey().getValue(), valueNode.expectNumberNode().getValue().floatValue());
                    }
                    break;
                case "doubles":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        builder.putDouble(memberKvp.getKey().getValue(), valueNode.expectNumberNode().getValue().doubleValue());
                    }
                    break;
                case "strings":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        builder.putString(memberKvp.getKey().getValue(), valueNode.expectStringNode().getValue());
                    }
                    break;
                case "bigDecimals":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        builder.putBigDecimal(memberKvp.getKey().getValue(), valueNode.expectNumberNode().asBigDecimal().get());
                    }
                    break;
                case "enumValues":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        builder.putEnumValue(memberKvp.getKey().getValue(), EnumValue.from(valueNode.expectStringNode().getValue()));
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
        private String stringMember;
        private CollectionBuilderReference<Map<String, Child>> children;
        private CollectionBuilderReference<Map<String, Boolean>> booleans;
        private CollectionBuilderReference<Map<String, Byte>> bytes;
        private CollectionBuilderReference<Map<String, Short>> shorts;
        private CollectionBuilderReference<Map<String, Integer>> integers;
        private CollectionBuilderReference<Map<String, BigInteger>> bigIntegers;
        private CollectionBuilderReference<Map<String, Long>> longs;
        private CollectionBuilderReference<Map<String, Float>> floats;
        private CollectionBuilderReference<Map<String, Double>> doubles;
        private CollectionBuilderReference<Map<String, String>> strings;
        private CollectionBuilderReference<Map<String, BigDecimal>> bigDecimals;
        private CollectionBuilderReference<Map<String, EnumValue>> enumValues;

        Builder() {
            this.children = CollectionBuilderReference.forUnorderedMap();
            this.booleans = CollectionBuilderReference.forUnorderedMap();
            this.bytes = CollectionBuilderReference.forUnorderedMap();
            this.shorts = CollectionBuilderReference.forUnorderedMap();
            this.integers = CollectionBuilderReference.forUnorderedMap();
            this.bigIntegers = CollectionBuilderReference.forUnorderedMap();
            this.longs = CollectionBuilderReference.forUnorderedMap();
            this.floats = CollectionBuilderReference.forUnorderedMap();
            this.doubles = CollectionBuilderReference.forUnorderedMap();
            this.strings = CollectionBuilderReference.forUnorderedMap();
            this.bigDecimals = CollectionBuilderReference.forUnorderedMap();
            this.enumValues = CollectionBuilderReference.forUnorderedMap();
        }

        Builder(Parent data) {
            this.stringMember = data.stringMember;
            this.children = CollectionBuilderReference.fromPersistentUnorderedMap(data.children);
            this.booleans = CollectionBuilderReference.fromPersistentUnorderedMap(data.booleans);
            this.bytes = CollectionBuilderReference.fromPersistentUnorderedMap(data.bytes);
            this.shorts = CollectionBuilderReference.fromPersistentUnorderedMap(data.shorts);
            this.integers = CollectionBuilderReference.fromPersistentUnorderedMap(data.integers);
            this.bigIntegers = CollectionBuilderReference.fromPersistentUnorderedMap(data.bigIntegers);
            this.longs = CollectionBuilderReference.fromPersistentUnorderedMap(data.longs);
            this.floats = CollectionBuilderReference.fromPersistentUnorderedMap(data.floats);
            this.doubles = CollectionBuilderReference.fromPersistentUnorderedMap(data.doubles);
            this.strings = CollectionBuilderReference.fromPersistentUnorderedMap(data.strings);
            this.bigDecimals = CollectionBuilderReference.fromPersistentUnorderedMap(data.bigDecimals);
            this.enumValues = CollectionBuilderReference.fromPersistentUnorderedMap(data.enumValues);
        }

        /**
         * <p>Sets the value for <code>stringMember</code></p>
         */
        public Builder stringMember(String stringMember) {
            this.stringMember = stringMember;
            return this;
        }

        /**
         * <p>Sets the value for <code>children</code></p>
         */
        public Builder children(Map<String, Child> children) {
            this.children.clear();
            this.children.asTransient().putAll(children);
            return this;
        }

        public Builder putChildren(String key, Child children) {
            this.children.asTransient().put(key, children);
            return this;
        }

        /**
         * <p>Sets the value for <code>booleans</code></p>
         */
        public Builder booleans(Map<String, Boolean> booleans) {
            this.booleans.clear();
            this.booleans.asTransient().putAll(booleans);
            return this;
        }

        public Builder putBoolean(String key, Boolean aBoolean) {
            this.booleans.asTransient().put(key, aBoolean);
            return this;
        }

        /**
         * <p>Sets the value for <code>bytes</code></p>
         */
        public Builder bytes(Map<String, Byte> bytes) {
            this.bytes.clear();
            this.bytes.asTransient().putAll(bytes);
            return this;
        }

        public Builder putByte(String key, Byte aByte) {
            this.bytes.asTransient().put(key, aByte);
            return this;
        }

        /**
         * <p>Sets the value for <code>shorts</code></p>
         */
        public Builder shorts(Map<String, Short> shorts) {
            this.shorts.clear();
            this.shorts.asTransient().putAll(shorts);
            return this;
        }

        public Builder putShort(String key, Short aShort) {
            this.shorts.asTransient().put(key, aShort);
            return this;
        }

        /**
         * <p>Sets the value for <code>integers</code></p>
         */
        public Builder integers(Map<String, Integer> integers) {
            this.integers.clear();
            this.integers.asTransient().putAll(integers);
            return this;
        }

        public Builder putInteger(String key, Integer integer) {
            this.integers.asTransient().put(key, integer);
            return this;
        }

        /**
         * <p>Sets the value for <code>bigIntegers</code></p>
         */
        public Builder bigIntegers(Map<String, BigInteger> bigIntegers) {
            this.bigIntegers.clear();
            this.bigIntegers.asTransient().putAll(bigIntegers);
            return this;
        }

        public Builder putBigInteger(String key, BigInteger bigInteger) {
            this.bigIntegers.asTransient().put(key, bigInteger);
            return this;
        }

        /**
         * <p>Sets the value for <code>longs</code></p>
         */
        public Builder longs(Map<String, Long> longs) {
            this.longs.clear();
            this.longs.asTransient().putAll(longs);
            return this;
        }

        public Builder putLong(String key, Long aLong) {
            this.longs.asTransient().put(key, aLong);
            return this;
        }

        /**
         * <p>Sets the value for <code>floats</code></p>
         */
        public Builder floats(Map<String, Float> floats) {
            this.floats.clear();
            this.floats.asTransient().putAll(floats);
            return this;
        }

        public Builder putFloat(String key, Float aFloat) {
            this.floats.asTransient().put(key, aFloat);
            return this;
        }

        /**
         * <p>Sets the value for <code>doubles</code></p>
         */
        public Builder doubles(Map<String, Double> doubles) {
            this.doubles.clear();
            this.doubles.asTransient().putAll(doubles);
            return this;
        }

        public Builder putDouble(String key, Double aDouble) {
            this.doubles.asTransient().put(key, aDouble);
            return this;
        }

        /**
         * <p>Sets the value for <code>strings</code></p>
         */
        public Builder strings(Map<String, String> strings) {
            this.strings.clear();
            this.strings.asTransient().putAll(strings);
            return this;
        }

        public Builder putString(String key, String string) {
            this.strings.asTransient().put(key, string);
            return this;
        }

        /**
         * <p>Sets the value for <code>bigDecimals</code></p>
         */
        public Builder bigDecimals(Map<String, BigDecimal> bigDecimals) {
            this.bigDecimals.clear();
            this.bigDecimals.asTransient().putAll(bigDecimals);
            return this;
        }

        public Builder putBigDecimal(String key, BigDecimal bigDecimal) {
            this.bigDecimals.asTransient().put(key, bigDecimal);
            return this;
        }

        /**
         * <p>Sets the value for <code>enumValues</code></p>
         */
        public Builder enumValues(Map<String, EnumValue> enumValues) {
            this.enumValues.clear();
            this.enumValues.asTransient().putAll(enumValues);
            return this;
        }

        public Builder putEnumValue(String key, EnumValue enumValue) {
            this.enumValues.asTransient().put(key, enumValue);
            return this;
        }

        public Parent build() {
            return new Parent(this);
        }
    }
}