package mx.sugus.braid.test;

import java.math.BigDecimal;
import java.math.BigInteger;
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
public final class Parent implements ToNode {
    private final String stringMember;
    private final List<Child> children;
    private final List<Boolean> booleans;
    private final List<Byte> bytes;
    private final List<Short> shorts;
    private final List<Integer> integers;
    private final List<BigInteger> bigIntegers;
    private final List<Long> longs;
    private final List<Float> floats;
    private final List<Double> doubles;
    private final List<String> strings;
    private final List<BigDecimal> bigDecimals;
    private final List<EnumValue> enumValues;
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

    public List<Child> children() {
        return this.children;
    }

    public List<Boolean> booleans() {
        return this.booleans;
    }

    public List<Byte> bytes() {
        return this.bytes;
    }

    public List<Short> shorts() {
        return this.shorts;
    }

    public List<Integer> integers() {
        return this.integers;
    }

    public List<BigInteger> bigIntegers() {
        return this.bigIntegers;
    }

    public List<Long> longs() {
        return this.longs;
    }

    public List<Float> floats() {
        return this.floats;
    }

    public List<Double> doubles() {
        return this.doubles;
    }

    public List<String> strings() {
        return this.strings;
    }

    public List<BigDecimal> bigDecimals() {
        return this.bigDecimals;
    }

    public List<EnumValue> enumValues() {
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
            ArrayNode.Builder childrenBuilder = ArrayNode.builder();
            for (Child item : this.children) {
                childrenBuilder.withValue(item.toNode());
            }
            builder.withMember("children", childrenBuilder.build());
        }
        if (!this.booleans.isEmpty()) {
            ArrayNode.Builder booleansBuilder = ArrayNode.builder();
            for (Boolean item : this.booleans) {
                booleansBuilder.withValue(Node.from(item));
            }
            builder.withMember("booleans", booleansBuilder.build());
        }
        if (!this.bytes.isEmpty()) {
            ArrayNode.Builder bytesBuilder = ArrayNode.builder();
            for (Byte item : this.bytes) {
                bytesBuilder.withValue(Node.from(item));
            }
            builder.withMember("bytes", bytesBuilder.build());
        }
        if (!this.shorts.isEmpty()) {
            ArrayNode.Builder shortsBuilder = ArrayNode.builder();
            for (Short item : this.shorts) {
                shortsBuilder.withValue(Node.from(item));
            }
            builder.withMember("shorts", shortsBuilder.build());
        }
        if (!this.integers.isEmpty()) {
            ArrayNode.Builder integersBuilder = ArrayNode.builder();
            for (Integer item : this.integers) {
                integersBuilder.withValue(Node.from(item));
            }
            builder.withMember("integers", integersBuilder.build());
        }
        if (!this.bigIntegers.isEmpty()) {
            ArrayNode.Builder bigIntegersBuilder = ArrayNode.builder();
            for (BigInteger item : this.bigIntegers) {
                bigIntegersBuilder.withValue(Node.from(item.toString()));
            }
            builder.withMember("bigIntegers", bigIntegersBuilder.build());
        }
        if (!this.longs.isEmpty()) {
            ArrayNode.Builder longsBuilder = ArrayNode.builder();
            for (Long item : this.longs) {
                longsBuilder.withValue(Node.from(item));
            }
            builder.withMember("longs", longsBuilder.build());
        }
        if (!this.floats.isEmpty()) {
            ArrayNode.Builder floatsBuilder = ArrayNode.builder();
            for (Float item : this.floats) {
                floatsBuilder.withValue(Node.from(item));
            }
            builder.withMember("floats", floatsBuilder.build());
        }
        if (!this.doubles.isEmpty()) {
            ArrayNode.Builder doublesBuilder = ArrayNode.builder();
            for (Double item : this.doubles) {
                doublesBuilder.withValue(Node.from(item));
            }
            builder.withMember("doubles", doublesBuilder.build());
        }
        if (!this.strings.isEmpty()) {
            ArrayNode.Builder stringsBuilder = ArrayNode.builder();
            for (String item : this.strings) {
                stringsBuilder.withValue(Node.from(item));
            }
            builder.withMember("strings", stringsBuilder.build());
        }
        if (!this.bigDecimals.isEmpty()) {
            ArrayNode.Builder bigDecimalsBuilder = ArrayNode.builder();
            for (BigDecimal item : this.bigDecimals) {
                bigDecimalsBuilder.withValue(Node.from(item.toString()));
            }
            builder.withMember("bigDecimals", bigDecimalsBuilder.build());
        }
        if (!this.enumValues.isEmpty()) {
            ArrayNode.Builder enumValuesBuilder = ArrayNode.builder();
            for (EnumValue item : this.enumValues) {
                enumValuesBuilder.withValue(Node.from(item.toString()));
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
                    value.expectArrayNode().forEach(item -> {
                        builder.addChildren(Child.fromNode(validator, item));
                    });
                    break;
                case "booleans":
                    value.expectArrayNode().forEach(item -> {
                        builder.addBoolean(item.expectBooleanNode().getValue());
                    });
                    break;
                case "bytes":
                    value.expectArrayNode().forEach(item -> {
                        builder.addByte(item.expectNumberNode().getValue().byteValue());
                    });
                    break;
                case "shorts":
                    value.expectArrayNode().forEach(item -> {
                        builder.addShort(item.expectNumberNode().getValue().shortValue());
                    });
                    break;
                case "integers":
                    value.expectArrayNode().forEach(item -> {
                        builder.addInteger(item.expectNumberNode().getValue().intValue());
                    });
                    break;
                case "bigIntegers":
                    value.expectArrayNode().forEach(item -> {
                        builder.addBigInteger(item.expectNumberNode().asBigDecimal().get().toBigInteger());
                    });
                    break;
                case "longs":
                    value.expectArrayNode().forEach(item -> {
                        builder.addLong(item.expectNumberNode().getValue().longValue());
                    });
                    break;
                case "floats":
                    value.expectArrayNode().forEach(item -> {
                        builder.addFloat(item.expectNumberNode().getValue().floatValue());
                    });
                    break;
                case "doubles":
                    value.expectArrayNode().forEach(item -> {
                        builder.addDouble(item.expectNumberNode().getValue().doubleValue());
                    });
                    break;
                case "strings":
                    value.expectArrayNode().forEach(item -> {
                        builder.addString(item.expectStringNode().getValue());
                    });
                    break;
                case "bigDecimals":
                    value.expectArrayNode().forEach(item -> {
                        builder.addBigDecimal(item.expectNumberNode().asBigDecimal().get());
                    });
                    break;
                case "enumValues":
                    value.expectArrayNode().forEach(item -> {
                        builder.addEnumValue(EnumValue.from(item.expectStringNode().getValue()));
                    });
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
        private CollectionBuilderReference<List<Child>> children;
        private CollectionBuilderReference<List<Boolean>> booleans;
        private CollectionBuilderReference<List<Byte>> bytes;
        private CollectionBuilderReference<List<Short>> shorts;
        private CollectionBuilderReference<List<Integer>> integers;
        private CollectionBuilderReference<List<BigInteger>> bigIntegers;
        private CollectionBuilderReference<List<Long>> longs;
        private CollectionBuilderReference<List<Float>> floats;
        private CollectionBuilderReference<List<Double>> doubles;
        private CollectionBuilderReference<List<String>> strings;
        private CollectionBuilderReference<List<BigDecimal>> bigDecimals;
        private CollectionBuilderReference<List<EnumValue>> enumValues;

        Builder() {
            this.children = CollectionBuilderReference.forList();
            this.booleans = CollectionBuilderReference.forList();
            this.bytes = CollectionBuilderReference.forList();
            this.shorts = CollectionBuilderReference.forList();
            this.integers = CollectionBuilderReference.forList();
            this.bigIntegers = CollectionBuilderReference.forList();
            this.longs = CollectionBuilderReference.forList();
            this.floats = CollectionBuilderReference.forList();
            this.doubles = CollectionBuilderReference.forList();
            this.strings = CollectionBuilderReference.forList();
            this.bigDecimals = CollectionBuilderReference.forList();
            this.enumValues = CollectionBuilderReference.forList();
        }

        Builder(Parent data) {
            this.stringMember = data.stringMember;
            this.children = CollectionBuilderReference.fromPersistentList(data.children);
            this.booleans = CollectionBuilderReference.fromPersistentList(data.booleans);
            this.bytes = CollectionBuilderReference.fromPersistentList(data.bytes);
            this.shorts = CollectionBuilderReference.fromPersistentList(data.shorts);
            this.integers = CollectionBuilderReference.fromPersistentList(data.integers);
            this.bigIntegers = CollectionBuilderReference.fromPersistentList(data.bigIntegers);
            this.longs = CollectionBuilderReference.fromPersistentList(data.longs);
            this.floats = CollectionBuilderReference.fromPersistentList(data.floats);
            this.doubles = CollectionBuilderReference.fromPersistentList(data.doubles);
            this.strings = CollectionBuilderReference.fromPersistentList(data.strings);
            this.bigDecimals = CollectionBuilderReference.fromPersistentList(data.bigDecimals);
            this.enumValues = CollectionBuilderReference.fromPersistentList(data.enumValues);
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
        public Builder children(List<Child> children) {
            this.children.clear();
            this.children.asTransient().addAll(children);
            return this;
        }

        /**
         * <p>Adds a single value for <code>children</code></p>
         */
        public Builder addChildren(Child children) {
            this.children.asTransient().add(children);
            return this;
        }

        /**
         * <p>Sets the value for <code>booleans</code></p>
         */
        public Builder booleans(List<Boolean> booleans) {
            this.booleans.clear();
            this.booleans.asTransient().addAll(booleans);
            return this;
        }

        /**
         * <p>Adds a single value for <code>booleans</code></p>
         */
        public Builder addBoolean(Boolean aBoolean) {
            this.booleans.asTransient().add(aBoolean);
            return this;
        }

        /**
         * <p>Sets the value for <code>bytes</code></p>
         */
        public Builder bytes(List<Byte> bytes) {
            this.bytes.clear();
            this.bytes.asTransient().addAll(bytes);
            return this;
        }

        /**
         * <p>Adds a single value for <code>bytes</code></p>
         */
        public Builder addByte(Byte aByte) {
            this.bytes.asTransient().add(aByte);
            return this;
        }

        /**
         * <p>Sets the value for <code>shorts</code></p>
         */
        public Builder shorts(List<Short> shorts) {
            this.shorts.clear();
            this.shorts.asTransient().addAll(shorts);
            return this;
        }

        /**
         * <p>Adds a single value for <code>shorts</code></p>
         */
        public Builder addShort(Short aShort) {
            this.shorts.asTransient().add(aShort);
            return this;
        }

        /**
         * <p>Sets the value for <code>integers</code></p>
         */
        public Builder integers(List<Integer> integers) {
            this.integers.clear();
            this.integers.asTransient().addAll(integers);
            return this;
        }

        /**
         * <p>Adds a single value for <code>integers</code></p>
         */
        public Builder addInteger(Integer integer) {
            this.integers.asTransient().add(integer);
            return this;
        }

        /**
         * <p>Sets the value for <code>bigIntegers</code></p>
         */
        public Builder bigIntegers(List<BigInteger> bigIntegers) {
            this.bigIntegers.clear();
            this.bigIntegers.asTransient().addAll(bigIntegers);
            return this;
        }

        /**
         * <p>Adds a single value for <code>bigIntegers</code></p>
         */
        public Builder addBigInteger(BigInteger bigInteger) {
            this.bigIntegers.asTransient().add(bigInteger);
            return this;
        }

        /**
         * <p>Sets the value for <code>longs</code></p>
         */
        public Builder longs(List<Long> longs) {
            this.longs.clear();
            this.longs.asTransient().addAll(longs);
            return this;
        }

        /**
         * <p>Adds a single value for <code>longs</code></p>
         */
        public Builder addLong(Long aLong) {
            this.longs.asTransient().add(aLong);
            return this;
        }

        /**
         * <p>Sets the value for <code>floats</code></p>
         */
        public Builder floats(List<Float> floats) {
            this.floats.clear();
            this.floats.asTransient().addAll(floats);
            return this;
        }

        /**
         * <p>Adds a single value for <code>floats</code></p>
         */
        public Builder addFloat(Float aFloat) {
            this.floats.asTransient().add(aFloat);
            return this;
        }

        /**
         * <p>Sets the value for <code>doubles</code></p>
         */
        public Builder doubles(List<Double> doubles) {
            this.doubles.clear();
            this.doubles.asTransient().addAll(doubles);
            return this;
        }

        /**
         * <p>Adds a single value for <code>doubles</code></p>
         */
        public Builder addDouble(Double aDouble) {
            this.doubles.asTransient().add(aDouble);
            return this;
        }

        /**
         * <p>Sets the value for <code>strings</code></p>
         */
        public Builder strings(List<String> strings) {
            this.strings.clear();
            this.strings.asTransient().addAll(strings);
            return this;
        }

        /**
         * <p>Adds a single value for <code>strings</code></p>
         */
        public Builder addString(String string) {
            this.strings.asTransient().add(string);
            return this;
        }

        /**
         * <p>Sets the value for <code>bigDecimals</code></p>
         */
        public Builder bigDecimals(List<BigDecimal> bigDecimals) {
            this.bigDecimals.clear();
            this.bigDecimals.asTransient().addAll(bigDecimals);
            return this;
        }

        /**
         * <p>Adds a single value for <code>bigDecimals</code></p>
         */
        public Builder addBigDecimal(BigDecimal bigDecimal) {
            this.bigDecimals.asTransient().add(bigDecimal);
            return this;
        }

        /**
         * <p>Sets the value for <code>enumValues</code></p>
         */
        public Builder enumValues(List<EnumValue> enumValues) {
            this.enumValues.clear();
            this.enumValues.asTransient().addAll(enumValues);
            return this;
        }

        /**
         * <p>Adds a single value for <code>enumValues</code></p>
         */
        public Builder addEnumValue(EnumValue enumValue) {
            this.enumValues.asTransient().add(enumValue);
            return this;
        }

        public Parent build() {
            return new Parent(this);
        }
    }
}