package mx.sugus.braid.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
    private final List<List<Integer>> nestedIntegers;
    private final List<List<List<Integer>>> nestedNestedIntegers;
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
        this.nestedIntegers = Objects.requireNonNull(builder.nestedIntegers.asPersistent(), "nestedIntegers");
        this.nestedNestedIntegers = Objects.requireNonNull(builder.nestedNestedIntegers.asPersistent(), "nestedNestedIntegers");
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
     * @return The value of the {@code children} member
     */
    public List<Child> children() {
        return this.children;
    }

    /**
     * 
     * @return The value of the {@code booleans} member
     */
    public List<Boolean> booleans() {
        return this.booleans;
    }

    /**
     * 
     * @return The value of the {@code bytes} member
     */
    public List<Byte> bytes() {
        return this.bytes;
    }

    /**
     * 
     * @return The value of the {@code shorts} member
     */
    public List<Short> shorts() {
        return this.shorts;
    }

    /**
     * 
     * @return The value of the {@code integers} member
     */
    public List<Integer> integers() {
        return this.integers;
    }

    /**
     * 
     * @return The value of the {@code bigIntegers} member
     */
    public List<BigInteger> bigIntegers() {
        return this.bigIntegers;
    }

    /**
     * 
     * @return The value of the {@code longs} member
     */
    public List<Long> longs() {
        return this.longs;
    }

    /**
     * 
     * @return The value of the {@code floats} member
     */
    public List<Float> floats() {
        return this.floats;
    }

    /**
     * 
     * @return The value of the {@code doubles} member
     */
    public List<Double> doubles() {
        return this.doubles;
    }

    /**
     * 
     * @return The value of the {@code strings} member
     */
    public List<String> strings() {
        return this.strings;
    }

    /**
     * 
     * @return The value of the {@code bigDecimals} member
     */
    public List<BigDecimal> bigDecimals() {
        return this.bigDecimals;
    }

    /**
     * 
     * @return The value of the {@code enumValues} member
     */
    public List<EnumValue> enumValues() {
        return this.enumValues;
    }

    /**
     * 
     * @return The value of the {@code nestedIntegers} member
     */
    public List<List<Integer>> nestedIntegers() {
        return this.nestedIntegers;
    }

    /**
     * 
     * @return The value of the {@code nestedNestedIntegers} member
     */
    public List<List<List<Integer>>> nestedNestedIntegers() {
        return this.nestedNestedIntegers;
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
            && this.enumValues.equals(that.enumValues)
            && this.nestedIntegers.equals(that.nestedIntegers)
            && this.nestedNestedIntegers.equals(that.nestedNestedIntegers);
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
            hashCode = 31 * hashCode + nestedIntegers.hashCode();
            hashCode = 31 * hashCode + nestedNestedIntegers.hashCode();
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
            + ", enumValues: " + enumValues
            + ", nestedIntegers: " + nestedIntegers
            + ", nestedNestedIntegers: " + nestedNestedIntegers + "}";
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
        if (this.stringMember != null) {
            builder.withMember("stringMember", Node.from(stringMember()));
        }
        if (!children().isEmpty()) {
            ArrayNode.Builder childrenBuilder = ArrayNode.builder();
            for (Child item : children()) {
                childrenBuilder.withValue(item.toNode());
            }
            builder.withMember("children", childrenBuilder.build());
        }
        if (!booleans().isEmpty()) {
            ArrayNode.Builder booleansBuilder = ArrayNode.builder();
            for (Boolean item : booleans()) {
                booleansBuilder.withValue(Node.from(item));
            }
            builder.withMember("booleans", booleansBuilder.build());
        }
        if (!bytes().isEmpty()) {
            ArrayNode.Builder bytesBuilder = ArrayNode.builder();
            for (Byte item : bytes()) {
                bytesBuilder.withValue(Node.from(item));
            }
            builder.withMember("bytes", bytesBuilder.build());
        }
        if (!shorts().isEmpty()) {
            ArrayNode.Builder shortsBuilder = ArrayNode.builder();
            for (Short item : shorts()) {
                shortsBuilder.withValue(Node.from(item));
            }
            builder.withMember("shorts", shortsBuilder.build());
        }
        if (!integers().isEmpty()) {
            ArrayNode.Builder integersBuilder = ArrayNode.builder();
            for (Integer item : integers()) {
                integersBuilder.withValue(Node.from(item));
            }
            builder.withMember("integers", integersBuilder.build());
        }
        if (!bigIntegers().isEmpty()) {
            ArrayNode.Builder bigIntegersBuilder = ArrayNode.builder();
            for (BigInteger item : bigIntegers()) {
                bigIntegersBuilder.withValue(Node.from(item.toString()));
            }
            builder.withMember("bigIntegers", bigIntegersBuilder.build());
        }
        if (!longs().isEmpty()) {
            ArrayNode.Builder longsBuilder = ArrayNode.builder();
            for (Long item : longs()) {
                longsBuilder.withValue(Node.from(item));
            }
            builder.withMember("longs", longsBuilder.build());
        }
        if (!floats().isEmpty()) {
            ArrayNode.Builder floatsBuilder = ArrayNode.builder();
            for (Float item : floats()) {
                floatsBuilder.withValue(Node.from(item));
            }
            builder.withMember("floats", floatsBuilder.build());
        }
        if (!doubles().isEmpty()) {
            ArrayNode.Builder doublesBuilder = ArrayNode.builder();
            for (Double item : doubles()) {
                doublesBuilder.withValue(Node.from(item));
            }
            builder.withMember("doubles", doublesBuilder.build());
        }
        if (!strings().isEmpty()) {
            ArrayNode.Builder stringsBuilder = ArrayNode.builder();
            for (String item : strings()) {
                stringsBuilder.withValue(Node.from(item));
            }
            builder.withMember("strings", stringsBuilder.build());
        }
        if (!bigDecimals().isEmpty()) {
            ArrayNode.Builder bigDecimalsBuilder = ArrayNode.builder();
            for (BigDecimal item : bigDecimals()) {
                bigDecimalsBuilder.withValue(Node.from(item.toString()));
            }
            builder.withMember("bigDecimals", bigDecimalsBuilder.build());
        }
        if (!enumValues().isEmpty()) {
            ArrayNode.Builder enumValuesBuilder = ArrayNode.builder();
            for (EnumValue item : enumValues()) {
                enumValuesBuilder.withValue(Node.from(item.toString()));
            }
            builder.withMember("enumValues", enumValuesBuilder.build());
        }
        if (!nestedIntegers().isEmpty()) {
            ArrayNode.Builder nestedIntegersBuilder = ArrayNode.builder();
            for (List<Integer> item : nestedIntegers()) {
                ArrayNode.Builder innerBuilder = ArrayNode.builder();
                for (Integer innerItem : item) {
                    innerBuilder.withValue(Node.from(innerItem));
                }
                nestedIntegersBuilder.withValue(innerBuilder.build());
            }
            builder.withMember("nestedIntegers", nestedIntegersBuilder.build());
        }
        if (!nestedNestedIntegers().isEmpty()) {
            ArrayNode.Builder nestedNestedIntegersBuilder = ArrayNode.builder();
            for (List<List<Integer>> item : nestedNestedIntegers()) {
                ArrayNode.Builder innerBuilder = ArrayNode.builder();
                for (List<Integer> innerItem : item) {
                    ArrayNode.Builder innerBuilder1 = ArrayNode.builder();
                    for (Integer innerItem1 : innerItem) {
                        innerBuilder1.withValue(Node.from(innerItem1));
                    }
                    innerBuilder.withValue(innerBuilder1.build());
                }
                nestedNestedIntegersBuilder.withValue(innerBuilder.build());
            }
            builder.withMember("nestedNestedIntegers", nestedNestedIntegersBuilder.build());
        }
        return builder.build();
    }

    /**
     * Converts a {@link Node} to Parent.
     */
    public static Parent fromNode(Node node) {
        return fromNode(SinkValidator.instance(), node);
    }

    /**
     * Converts a {@link Node} to Parent.
     */
    public static Parent fromNode(Validation validator, Node node) {
        validator = validator.with("Parent");
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
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addChildren(Child.fromNode(validator.with("children"), lstNodeValue));
                    }
                    break;
                case "booleans":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addBoolean(lstNodeValue.expectBooleanNode().getValue());
                    }
                    break;
                case "bytes":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addByte(lstNodeValue.expectNumberNode().getValue().byteValue());
                    }
                    break;
                case "shorts":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addShort(lstNodeValue.expectNumberNode().getValue().shortValue());
                    }
                    break;
                case "integers":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addInteger(lstNodeValue.expectNumberNode().getValue().intValue());
                    }
                    break;
                case "bigIntegers":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addBigInteger(lstNodeValue.expectNumberNode().asBigDecimal().get().toBigInteger());
                    }
                    break;
                case "longs":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addLong(lstNodeValue.expectNumberNode().getValue().longValue());
                    }
                    break;
                case "floats":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addFloat(lstNodeValue.expectNumberNode().getValue().floatValue());
                    }
                    break;
                case "doubles":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addDouble(lstNodeValue.expectNumberNode().getValue().doubleValue());
                    }
                    break;
                case "strings":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addString(lstNodeValue.expectStringNode().getValue());
                    }
                    break;
                case "bigDecimals":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addBigDecimal(lstNodeValue.expectNumberNode().asBigDecimal().get());
                    }
                    break;
                case "enumValues":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        builder.addEnumValue(EnumValue.from(lstNodeValue.expectStringNode().getValue()));
                    }
                    break;
                case "nestedIntegers":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        List<Integer> lstMember = new ArrayList<>();
                        for (Node innerNodeValue : lstNodeValue.expectArrayNode()) {
                            lstMember.add(innerNodeValue.expectNumberNode().getValue().intValue());
                        }
                        builder.addNestedInteger(lstMember);
                    }
                    break;
                case "nestedNestedIntegers":
                    for (Node lstNodeValue : value.expectArrayNode()) {
                        List<List<Integer>> lstMember = new ArrayList<>();
                        for (Node innerNodeValue : lstNodeValue.expectArrayNode()) {
                            List<Integer> lstMember1 = new ArrayList<>();
                            for (Node innerNodeValue1 : innerNodeValue.expectArrayNode()) {
                                lstMember1.add(innerNodeValue1.expectNumberNode().getValue().intValue());
                            }
                            lstMember.add(lstMember1);
                        }
                        builder.addNestedNestedInteger(lstMember);
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
        private CollectionBuilderReference<List<List<Integer>>> nestedIntegers;
        private CollectionBuilderReference<List<List<List<Integer>>>> nestedNestedIntegers;

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
            this.nestedIntegers = CollectionBuilderReference.forList();
            this.nestedNestedIntegers = CollectionBuilderReference.forList();
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
            this.nestedIntegers = CollectionBuilderReference.fromPersistentList(data.nestedIntegers);
            this.nestedNestedIntegers = CollectionBuilderReference.fromPersistentList(data.nestedNestedIntegers);
        }

        /**
         * Sets the value for {@code stringMember}.
         * 
         * @param stringMember The value to be set.
         * @return This instance for chain calling.
         */
        public Builder stringMember(String stringMember) {
            this.stringMember = stringMember;
            return this;
        }

        /**
         * Sets the value for {@code children}.
         * 
         * @param children The value to be set.
         * @return This instance for chain calling.
         */
        public Builder children(List<Child> children) {
            this.children.clear();
            this.children.asTransient().addAll(children);
            return this;
        }

        /**
         * Adds a single value for {@code children}.
         */
        public Builder addChildren(Child children) {
            this.children.asTransient().add(children);
            return this;
        }

        /**
         * Sets the value for {@code booleans}.
         * 
         * @param booleans The value to be set.
         * @return This instance for chain calling.
         */
        public Builder booleans(List<Boolean> booleans) {
            this.booleans.clear();
            this.booleans.asTransient().addAll(booleans);
            return this;
        }

        /**
         * Adds a single value for {@code booleans}.
         */
        public Builder addBoolean(Boolean aBoolean) {
            this.booleans.asTransient().add(aBoolean);
            return this;
        }

        /**
         * Sets the value for {@code bytes}.
         * 
         * @param bytes The value to be set.
         * @return This instance for chain calling.
         */
        public Builder bytes(List<Byte> bytes) {
            this.bytes.clear();
            this.bytes.asTransient().addAll(bytes);
            return this;
        }

        /**
         * Adds a single value for {@code bytes}.
         */
        public Builder addByte(Byte aByte) {
            this.bytes.asTransient().add(aByte);
            return this;
        }

        /**
         * Sets the value for {@code shorts}.
         * 
         * @param shorts The value to be set.
         * @return This instance for chain calling.
         */
        public Builder shorts(List<Short> shorts) {
            this.shorts.clear();
            this.shorts.asTransient().addAll(shorts);
            return this;
        }

        /**
         * Adds a single value for {@code shorts}.
         */
        public Builder addShort(Short aShort) {
            this.shorts.asTransient().add(aShort);
            return this;
        }

        /**
         * Sets the value for {@code integers}.
         * 
         * @param integers The value to be set.
         * @return This instance for chain calling.
         */
        public Builder integers(List<Integer> integers) {
            this.integers.clear();
            this.integers.asTransient().addAll(integers);
            return this;
        }

        /**
         * Adds a single value for {@code integers}.
         */
        public Builder addInteger(Integer integer) {
            this.integers.asTransient().add(integer);
            return this;
        }

        /**
         * Sets the value for {@code bigIntegers}.
         * 
         * @param bigIntegers The value to be set.
         * @return This instance for chain calling.
         */
        public Builder bigIntegers(List<BigInteger> bigIntegers) {
            this.bigIntegers.clear();
            this.bigIntegers.asTransient().addAll(bigIntegers);
            return this;
        }

        /**
         * Adds a single value for {@code bigIntegers}.
         */
        public Builder addBigInteger(BigInteger bigInteger) {
            this.bigIntegers.asTransient().add(bigInteger);
            return this;
        }

        /**
         * Sets the value for {@code longs}.
         * 
         * @param longs The value to be set.
         * @return This instance for chain calling.
         */
        public Builder longs(List<Long> longs) {
            this.longs.clear();
            this.longs.asTransient().addAll(longs);
            return this;
        }

        /**
         * Adds a single value for {@code longs}.
         */
        public Builder addLong(Long aLong) {
            this.longs.asTransient().add(aLong);
            return this;
        }

        /**
         * Sets the value for {@code floats}.
         * 
         * @param floats The value to be set.
         * @return This instance for chain calling.
         */
        public Builder floats(List<Float> floats) {
            this.floats.clear();
            this.floats.asTransient().addAll(floats);
            return this;
        }

        /**
         * Adds a single value for {@code floats}.
         */
        public Builder addFloat(Float aFloat) {
            this.floats.asTransient().add(aFloat);
            return this;
        }

        /**
         * Sets the value for {@code doubles}.
         * 
         * @param doubles The value to be set.
         * @return This instance for chain calling.
         */
        public Builder doubles(List<Double> doubles) {
            this.doubles.clear();
            this.doubles.asTransient().addAll(doubles);
            return this;
        }

        /**
         * Adds a single value for {@code doubles}.
         */
        public Builder addDouble(Double aDouble) {
            this.doubles.asTransient().add(aDouble);
            return this;
        }

        /**
         * Sets the value for {@code strings}.
         * 
         * @param strings The value to be set.
         * @return This instance for chain calling.
         */
        public Builder strings(List<String> strings) {
            this.strings.clear();
            this.strings.asTransient().addAll(strings);
            return this;
        }

        /**
         * Adds a single value for {@code strings}.
         */
        public Builder addString(String string) {
            this.strings.asTransient().add(string);
            return this;
        }

        /**
         * Sets the value for {@code bigDecimals}.
         * 
         * @param bigDecimals The value to be set.
         * @return This instance for chain calling.
         */
        public Builder bigDecimals(List<BigDecimal> bigDecimals) {
            this.bigDecimals.clear();
            this.bigDecimals.asTransient().addAll(bigDecimals);
            return this;
        }

        /**
         * Adds a single value for {@code bigDecimals}.
         */
        public Builder addBigDecimal(BigDecimal bigDecimal) {
            this.bigDecimals.asTransient().add(bigDecimal);
            return this;
        }

        /**
         * Sets the value for {@code enumValues}.
         * 
         * @param enumValues The value to be set.
         * @return This instance for chain calling.
         */
        public Builder enumValues(List<EnumValue> enumValues) {
            this.enumValues.clear();
            this.enumValues.asTransient().addAll(enumValues);
            return this;
        }

        /**
         * Adds a single value for {@code enumValues}.
         */
        public Builder addEnumValue(EnumValue enumValue) {
            this.enumValues.asTransient().add(enumValue);
            return this;
        }

        /**
         * Sets the value for {@code nestedIntegers}.
         * 
         * @param nestedIntegers The value to be set.
         * @return This instance for chain calling.
         */
        public Builder nestedIntegers(List<List<Integer>> nestedIntegers) {
            this.nestedIntegers.clear();
            this.nestedIntegers.asTransient().addAll(nestedIntegers);
            return this;
        }

        /**
         * Adds a single value for {@code nestedIntegers}.
         */
        public Builder addNestedInteger(List<Integer> nestedInteger) {
            this.nestedIntegers.asTransient().add(nestedInteger);
            return this;
        }

        /**
         * Sets the value for {@code nestedNestedIntegers}.
         * 
         * @param nestedNestedIntegers The value to be set.
         * @return This instance for chain calling.
         */
        public Builder nestedNestedIntegers(List<List<List<Integer>>> nestedNestedIntegers) {
            this.nestedNestedIntegers.clear();
            this.nestedNestedIntegers.asTransient().addAll(nestedNestedIntegers);
            return this;
        }

        /**
         * Adds a single value for {@code nestedNestedIntegers}.
         */
        public Builder addNestedNestedInteger(List<List<Integer>> nestedNestedInteger) {
            this.nestedNestedIntegers.asTransient().add(nestedNestedInteger);
            return this;
        }

        /**
         * Returns a new instance of {@link Parent}
         * 
         * @return A new instance of {@link Parent}
         */
        public Parent build() {
            return new Parent(this);
        }
    }
}
