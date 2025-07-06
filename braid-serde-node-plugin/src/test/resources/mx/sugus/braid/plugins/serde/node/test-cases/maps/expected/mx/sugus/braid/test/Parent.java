package mx.sugus.braid.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    private final Map<String, List<Integer>> integerListMap;
    private final Map<String, Map<String, List<Integer>>> nestedIntegerListMap;
    private final Map<String, List<Map<String, Integer>>> nestedNestedIntegerMap;
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
        this.integerListMap = Objects.requireNonNull(builder.integerListMap.asPersistent(), "integerListMap");
        this.nestedIntegerListMap = Objects.requireNonNull(builder.nestedIntegerListMap.asPersistent(), "nestedIntegerListMap");
        this.nestedNestedIntegerMap = Objects.requireNonNull(builder.nestedNestedIntegerMap.asPersistent(), "nestedNestedIntegerMap");
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
    public Map<String, Child> children() {
        return this.children;
    }

    /**
     * 
     * @return The value of the {@code booleans} member
     */
    public Map<String, Boolean> booleans() {
        return this.booleans;
    }

    /**
     * 
     * @return The value of the {@code bytes} member
     */
    public Map<String, Byte> bytes() {
        return this.bytes;
    }

    /**
     * 
     * @return The value of the {@code shorts} member
     */
    public Map<String, Short> shorts() {
        return this.shorts;
    }

    /**
     * 
     * @return The value of the {@code integers} member
     */
    public Map<String, Integer> integers() {
        return this.integers;
    }

    /**
     * 
     * @return The value of the {@code bigIntegers} member
     */
    public Map<String, BigInteger> bigIntegers() {
        return this.bigIntegers;
    }

    /**
     * 
     * @return The value of the {@code longs} member
     */
    public Map<String, Long> longs() {
        return this.longs;
    }

    /**
     * 
     * @return The value of the {@code floats} member
     */
    public Map<String, Float> floats() {
        return this.floats;
    }

    /**
     * 
     * @return The value of the {@code doubles} member
     */
    public Map<String, Double> doubles() {
        return this.doubles;
    }

    /**
     * 
     * @return The value of the {@code strings} member
     */
    public Map<String, String> strings() {
        return this.strings;
    }

    /**
     * 
     * @return The value of the {@code bigDecimals} member
     */
    public Map<String, BigDecimal> bigDecimals() {
        return this.bigDecimals;
    }

    /**
     * 
     * @return The value of the {@code enumValues} member
     */
    public Map<String, EnumValue> enumValues() {
        return this.enumValues;
    }

    /**
     * 
     * @return The value of the {@code integerListMap} member
     */
    public Map<String, List<Integer>> integerListMap() {
        return this.integerListMap;
    }

    /**
     * 
     * @return The value of the {@code nestedIntegerListMap} member
     */
    public Map<String, Map<String, List<Integer>>> nestedIntegerListMap() {
        return this.nestedIntegerListMap;
    }

    /**
     * 
     * @return The value of the {@code nestedNestedIntegerMap} member
     */
    public Map<String, List<Map<String, Integer>>> nestedNestedIntegerMap() {
        return this.nestedNestedIntegerMap;
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
            && this.integerListMap.equals(that.integerListMap)
            && this.nestedIntegerListMap.equals(that.nestedIntegerListMap)
            && this.nestedNestedIntegerMap.equals(that.nestedNestedIntegerMap);
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
            hashCode = 31 * hashCode + integerListMap.hashCode();
            hashCode = 31 * hashCode + nestedIntegerListMap.hashCode();
            hashCode = 31 * hashCode + nestedNestedIntegerMap.hashCode();
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
            + ", integerListMap: " + integerListMap
            + ", nestedIntegerListMap: " + nestedIntegerListMap
            + ", nestedNestedIntegerMap: " + nestedNestedIntegerMap + "}";
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
        if (!this.children.isEmpty()) {
            ObjectNode.Builder childrenBuilder = ObjectNode.builder();
            for (Map.Entry<String, Child> kvp : children().entrySet()) {
                childrenBuilder.withMember(kvp.getKey(), kvp.getValue().toNode());
            }
            builder.withMember("children", childrenBuilder.build());
        }
        if (!this.booleans.isEmpty()) {
            ObjectNode.Builder booleansBuilder = ObjectNode.builder();
            for (Map.Entry<String, Boolean> kvp : booleans().entrySet()) {
                booleansBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("booleans", booleansBuilder.build());
        }
        if (!this.bytes.isEmpty()) {
            ObjectNode.Builder bytesBuilder = ObjectNode.builder();
            for (Map.Entry<String, Byte> kvp : bytes().entrySet()) {
                bytesBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("bytes", bytesBuilder.build());
        }
        if (!this.shorts.isEmpty()) {
            ObjectNode.Builder shortsBuilder = ObjectNode.builder();
            for (Map.Entry<String, Short> kvp : shorts().entrySet()) {
                shortsBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("shorts", shortsBuilder.build());
        }
        if (!this.integers.isEmpty()) {
            ObjectNode.Builder integersBuilder = ObjectNode.builder();
            for (Map.Entry<String, Integer> kvp : integers().entrySet()) {
                integersBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("integers", integersBuilder.build());
        }
        if (!this.bigIntegers.isEmpty()) {
            ObjectNode.Builder bigIntegersBuilder = ObjectNode.builder();
            for (Map.Entry<String, BigInteger> kvp : bigIntegers().entrySet()) {
                bigIntegersBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue().toString()));
            }
            builder.withMember("bigIntegers", bigIntegersBuilder.build());
        }
        if (!this.longs.isEmpty()) {
            ObjectNode.Builder longsBuilder = ObjectNode.builder();
            for (Map.Entry<String, Long> kvp : longs().entrySet()) {
                longsBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("longs", longsBuilder.build());
        }
        if (!this.floats.isEmpty()) {
            ObjectNode.Builder floatsBuilder = ObjectNode.builder();
            for (Map.Entry<String, Float> kvp : floats().entrySet()) {
                floatsBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("floats", floatsBuilder.build());
        }
        if (!this.doubles.isEmpty()) {
            ObjectNode.Builder doublesBuilder = ObjectNode.builder();
            for (Map.Entry<String, Double> kvp : doubles().entrySet()) {
                doublesBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("doubles", doublesBuilder.build());
        }
        if (!this.strings.isEmpty()) {
            ObjectNode.Builder stringsBuilder = ObjectNode.builder();
            for (Map.Entry<String, String> kvp : strings().entrySet()) {
                stringsBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue()));
            }
            builder.withMember("strings", stringsBuilder.build());
        }
        if (!this.bigDecimals.isEmpty()) {
            ObjectNode.Builder bigDecimalsBuilder = ObjectNode.builder();
            for (Map.Entry<String, BigDecimal> kvp : bigDecimals().entrySet()) {
                bigDecimalsBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue().toString()));
            }
            builder.withMember("bigDecimals", bigDecimalsBuilder.build());
        }
        if (!this.enumValues.isEmpty()) {
            ObjectNode.Builder enumValuesBuilder = ObjectNode.builder();
            for (Map.Entry<String, EnumValue> kvp : enumValues().entrySet()) {
                enumValuesBuilder.withMember(kvp.getKey(), Node.from(kvp.getValue().toString()));
            }
            builder.withMember("enumValues", enumValuesBuilder.build());
        }
        if (!this.integerListMap.isEmpty()) {
            ObjectNode.Builder integerListMapBuilder = ObjectNode.builder();
            for (Map.Entry<String, List<Integer>> kvp : integerListMap().entrySet()) {
                ArrayNode.Builder innerBuilder = ArrayNode.builder();
                for (Integer innerItem : kvp.getValue()) {
                    innerBuilder.withValue(Node.from(innerItem));
                }
                integerListMapBuilder.withMember(kvp.getKey(), innerBuilder.build());
            }
            builder.withMember("integerListMap", integerListMapBuilder.build());
        }
        if (!this.nestedIntegerListMap.isEmpty()) {
            ObjectNode.Builder nestedIntegerListMapBuilder = ObjectNode.builder();
            for (Map.Entry<String, Map<String, List<Integer>>> kvp : nestedIntegerListMap().entrySet()) {
                ObjectNode.Builder innerBuilder = ObjectNode.builder();
                for (Map.Entry<String, List<Integer>> innerKvp : kvp.getValue().entrySet()) {
                    ArrayNode.Builder innerBuilder1 = ArrayNode.builder();
                    for (Integer innerItem1 : innerKvp.getValue()) {
                        innerBuilder1.withValue(Node.from(innerItem1));
                    }
                    innerBuilder.withMember(innerKvp.getKey(), innerBuilder1.build());
                }
                nestedIntegerListMapBuilder.withMember(kvp.getKey(), innerBuilder.build());
            }
            builder.withMember("nestedIntegerListMap", nestedIntegerListMapBuilder.build());
        }
        if (!this.nestedNestedIntegerMap.isEmpty()) {
            ObjectNode.Builder nestedNestedIntegerMapBuilder = ObjectNode.builder();
            for (Map.Entry<String, List<Map<String, Integer>>> kvp : nestedNestedIntegerMap().entrySet()) {
                ArrayNode.Builder innerBuilder = ArrayNode.builder();
                for (Map<String, Integer> innerItem : kvp.getValue()) {
                    ObjectNode.Builder innerBuilder1 = ObjectNode.builder();
                    for (Map.Entry<String, Integer> innerKvp1 : innerItem.entrySet()) {
                        innerBuilder1.withMember(kvp.getKey(), Node.from(kvp.getValue()));
                    }
                    innerBuilder.withValue(innerBuilder1.build());
                }
                nestedNestedIntegerMapBuilder.withMember(kvp.getKey(), innerBuilder.build());
            }
            builder.withMember("nestedNestedIntegerMap", nestedNestedIntegerMapBuilder.build());
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
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        builder.putChildren(memberKvp.getKey().getValue(), Child.fromNode(validator.with("children"), valueNode));
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
                case "integerListMap":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        List<Integer> lstMember = new ArrayList<>();
                        for (Node innerNodeValue : valueNode.expectArrayNode()) {
                            lstMember.add(innerNodeValue.expectNumberNode().getValue().intValue());
                        }
                        builder.putIntegerListMap(memberKvp.getKey().getValue(), lstMember);
                    }
                    break;
                case "nestedIntegerListMap":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        Map<String, List<Integer>> mapValue = new LinkedHashMap<>();
                        for (Map.Entry<StringNode, Node> innerKvp : valueNode.expectObjectNode().getMembers().entrySet()) {
                            Node innerValue = innerKvp.getValue();
                            List<Integer> lstMember1 = new ArrayList<>();
                            for (Node innerNodeValue1 : innerValue.expectArrayNode()) {
                                lstMember1.add(innerNodeValue1.expectNumberNode().getValue().intValue());
                            }
                            mapValue.put(innerKvp.getKey().getValue(), lstMember1);
                        }
                        builder.putNestedIntegerListMap(memberKvp.getKey().getValue(), mapValue);
                    }
                    break;
                case "nestedNestedIntegerMap":
                    for (Map.Entry<StringNode, Node> memberKvp : value.expectObjectNode().getMembers().entrySet()) {
                        Node valueNode = memberKvp.getValue();
                        List<Map<String, Integer>> lstMember = new ArrayList<>();
                        for (Node innerNodeValue : valueNode.expectArrayNode()) {
                            Map<String, Integer> mapValue1 = new LinkedHashMap<>();
                            for (Map.Entry<StringNode, Node> innerKvp1 : innerNodeValue.expectObjectNode().getMembers().entrySet()) {
                                Node innerValue1 = innerKvp1.getValue();
                                mapValue1.put(innerKvp1.getKey().getValue(), valueNode.expectNumberNode().getValue().intValue());
                            }
                            lstMember.add(mapValue1);
                        }
                        builder.putNestedNestedIntegerMap(memberKvp.getKey().getValue(), lstMember);
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
        private CollectionBuilderReference<Map<String, List<Integer>>> integerListMap;
        private CollectionBuilderReference<Map<String, Map<String, List<Integer>>>> nestedIntegerListMap;
        private CollectionBuilderReference<Map<String, List<Map<String, Integer>>>> nestedNestedIntegerMap;

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
            this.integerListMap = CollectionBuilderReference.forUnorderedMap();
            this.nestedIntegerListMap = CollectionBuilderReference.forUnorderedMap();
            this.nestedNestedIntegerMap = CollectionBuilderReference.forUnorderedMap();
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
            this.integerListMap = CollectionBuilderReference.fromPersistentUnorderedMap(data.integerListMap);
            this.nestedIntegerListMap = CollectionBuilderReference.fromPersistentUnorderedMap(data.nestedIntegerListMap);
            this.nestedNestedIntegerMap = CollectionBuilderReference.fromPersistentUnorderedMap(data.nestedNestedIntegerMap);
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
        public Builder children(Map<String, Child> children) {
            this.children.clear();
            this.children.asTransient().putAll(children);
            return this;
        }

        /**
         * Puts a new entry to the {@code children} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param children The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putChildren(String key, Child children) {
            this.children.asTransient().put(key, children);
            return this;
        }

        /**
         * Sets the value for {@code booleans}.
         * 
         * @param booleans The value to be set.
         * @return This instance for chain calling.
         */
        public Builder booleans(Map<String, Boolean> booleans) {
            this.booleans.clear();
            this.booleans.asTransient().putAll(booleans);
            return this;
        }

        /**
         * Puts a new entry to the {@code booleans} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param aBoolean The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putBoolean(String key, Boolean aBoolean) {
            this.booleans.asTransient().put(key, aBoolean);
            return this;
        }

        /**
         * Sets the value for {@code bytes}.
         * 
         * @param bytes The value to be set.
         * @return This instance for chain calling.
         */
        public Builder bytes(Map<String, Byte> bytes) {
            this.bytes.clear();
            this.bytes.asTransient().putAll(bytes);
            return this;
        }

        /**
         * Puts a new entry to the {@code bytes} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param aByte The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putByte(String key, Byte aByte) {
            this.bytes.asTransient().put(key, aByte);
            return this;
        }

        /**
         * Sets the value for {@code shorts}.
         * 
         * @param shorts The value to be set.
         * @return This instance for chain calling.
         */
        public Builder shorts(Map<String, Short> shorts) {
            this.shorts.clear();
            this.shorts.asTransient().putAll(shorts);
            return this;
        }

        /**
         * Puts a new entry to the {@code shorts} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param aShort The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putShort(String key, Short aShort) {
            this.shorts.asTransient().put(key, aShort);
            return this;
        }

        /**
         * Sets the value for {@code integers}.
         * 
         * @param integers The value to be set.
         * @return This instance for chain calling.
         */
        public Builder integers(Map<String, Integer> integers) {
            this.integers.clear();
            this.integers.asTransient().putAll(integers);
            return this;
        }

        /**
         * Puts a new entry to the {@code integers} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param integer The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putInteger(String key, Integer integer) {
            this.integers.asTransient().put(key, integer);
            return this;
        }

        /**
         * Sets the value for {@code bigIntegers}.
         * 
         * @param bigIntegers The value to be set.
         * @return This instance for chain calling.
         */
        public Builder bigIntegers(Map<String, BigInteger> bigIntegers) {
            this.bigIntegers.clear();
            this.bigIntegers.asTransient().putAll(bigIntegers);
            return this;
        }

        /**
         * Puts a new entry to the {@code bigIntegers} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param bigInteger The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putBigInteger(String key, BigInteger bigInteger) {
            this.bigIntegers.asTransient().put(key, bigInteger);
            return this;
        }

        /**
         * Sets the value for {@code longs}.
         * 
         * @param longs The value to be set.
         * @return This instance for chain calling.
         */
        public Builder longs(Map<String, Long> longs) {
            this.longs.clear();
            this.longs.asTransient().putAll(longs);
            return this;
        }

        /**
         * Puts a new entry to the {@code longs} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param aLong The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putLong(String key, Long aLong) {
            this.longs.asTransient().put(key, aLong);
            return this;
        }

        /**
         * Sets the value for {@code floats}.
         * 
         * @param floats The value to be set.
         * @return This instance for chain calling.
         */
        public Builder floats(Map<String, Float> floats) {
            this.floats.clear();
            this.floats.asTransient().putAll(floats);
            return this;
        }

        /**
         * Puts a new entry to the {@code floats} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param aFloat The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putFloat(String key, Float aFloat) {
            this.floats.asTransient().put(key, aFloat);
            return this;
        }

        /**
         * Sets the value for {@code doubles}.
         * 
         * @param doubles The value to be set.
         * @return This instance for chain calling.
         */
        public Builder doubles(Map<String, Double> doubles) {
            this.doubles.clear();
            this.doubles.asTransient().putAll(doubles);
            return this;
        }

        /**
         * Puts a new entry to the {@code doubles} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param aDouble The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putDouble(String key, Double aDouble) {
            this.doubles.asTransient().put(key, aDouble);
            return this;
        }

        /**
         * Sets the value for {@code strings}.
         * 
         * @param strings The value to be set.
         * @return This instance for chain calling.
         */
        public Builder strings(Map<String, String> strings) {
            this.strings.clear();
            this.strings.asTransient().putAll(strings);
            return this;
        }

        /**
         * Puts a new entry to the {@code strings} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param string The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putString(String key, String string) {
            this.strings.asTransient().put(key, string);
            return this;
        }

        /**
         * Sets the value for {@code bigDecimals}.
         * 
         * @param bigDecimals The value to be set.
         * @return This instance for chain calling.
         */
        public Builder bigDecimals(Map<String, BigDecimal> bigDecimals) {
            this.bigDecimals.clear();
            this.bigDecimals.asTransient().putAll(bigDecimals);
            return this;
        }

        /**
         * Puts a new entry to the {@code bigDecimals} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param bigDecimal The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putBigDecimal(String key, BigDecimal bigDecimal) {
            this.bigDecimals.asTransient().put(key, bigDecimal);
            return this;
        }

        /**
         * Sets the value for {@code enumValues}.
         * 
         * @param enumValues The value to be set.
         * @return This instance for chain calling.
         */
        public Builder enumValues(Map<String, EnumValue> enumValues) {
            this.enumValues.clear();
            this.enumValues.asTransient().putAll(enumValues);
            return this;
        }

        /**
         * Puts a new entry to the {@code enumValues} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param enumValue The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putEnumValue(String key, EnumValue enumValue) {
            this.enumValues.asTransient().put(key, enumValue);
            return this;
        }

        /**
         * Sets the value for {@code integerListMap}.
         * 
         * @param integerListMap The value to be set.
         * @return This instance for chain calling.
         */
        public Builder integerListMap(Map<String, List<Integer>> integerListMap) {
            this.integerListMap.clear();
            this.integerListMap.asTransient().putAll(integerListMap);
            return this;
        }

        /**
         * Puts a new entry to the {@code integerListMap} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param integerListMap The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putIntegerListMap(String key, List<Integer> integerListMap) {
            this.integerListMap.asTransient().put(key, integerListMap);
            return this;
        }

        /**
         * Sets the value for {@code nestedIntegerListMap}.
         * 
         * @param nestedIntegerListMap The value to be set.
         * @return This instance for chain calling.
         */
        public Builder nestedIntegerListMap(Map<String, Map<String, List<Integer>>> nestedIntegerListMap) {
            this.nestedIntegerListMap.clear();
            this.nestedIntegerListMap.asTransient().putAll(nestedIntegerListMap);
            return this;
        }

        /**
         * Puts a new entry to the {@code nestedIntegerListMap} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param nestedIntegerListMap The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putNestedIntegerListMap(String key, Map<String, List<Integer>> nestedIntegerListMap) {
            this.nestedIntegerListMap.asTransient().put(key, nestedIntegerListMap);
            return this;
        }

        /**
         * Sets the value for {@code nestedNestedIntegerMap}.
         * 
         * @param nestedNestedIntegerMap The value to be set.
         * @return This instance for chain calling.
         */
        public Builder nestedNestedIntegerMap(Map<String, List<Map<String, Integer>>> nestedNestedIntegerMap) {
            this.nestedNestedIntegerMap.clear();
            this.nestedNestedIntegerMap.asTransient().putAll(nestedNestedIntegerMap);
            return this;
        }

        /**
         * Puts a new entry to the {@code nestedNestedIntegerMap} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param nestedNestedIntegerMap The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putNestedNestedIntegerMap(String key, List<Map<String, Integer>> nestedNestedIntegerMap) {
            this.nestedNestedIntegerMap.asTransient().put(key, nestedNestedIntegerMap);
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
