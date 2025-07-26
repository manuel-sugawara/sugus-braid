package mx.sugus.braid.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.rt.util.AbstractBuilderReference;
import mx.sugus.braid.rt.util.SinkValidator;
import mx.sugus.braid.rt.util.Validation;
import mx.sugus.braid.rt.util.annotations.Generated;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.node.ToNode;

/**
 * A simple structure
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
public final class AllSimpleTypes implements ToNode {
    private final Byte aByte;
    private final Short aShort;
    private final Integer anInt;
    private final Long aLong;
    private final BigInteger bigInteger;
    private final Float aFloat;
    private final Double aDouble;
    private final BigDecimal bigDecimal;
    private final String string;
    private final Instant instant;
    private int _hashCode = 0;

    private AllSimpleTypes(Builder builder) {
        this.aByte = builder.aByte;
        this.aShort = builder.aShort;
        this.anInt = builder.anInt;
        this.aLong = builder.aLong;
        this.bigInteger = builder.bigInteger;
        this.aFloat = builder.aFloat;
        this.aDouble = builder.aDouble;
        this.bigDecimal = builder.bigDecimal;
        this.string = builder.string;
        this.instant = builder.instant;
    }

    /**
     * byte member
     * 
     * @return The value of the {@code byte} member
     */
    public Byte aByte() {
        return this.aByte;
    }

    /**
     * short member
     * 
     * @return The value of the {@code short} member
     */
    public Short aShort() {
        return this.aShort;
    }

    /**
     * int member
     * 
     * @return The value of the {@code int} member
     */
    public Integer anInt() {
        return this.anInt;
    }

    /**
     * long member
     * 
     * @return The value of the {@code long} member
     */
    public Long aLong() {
        return this.aLong;
    }

    /**
     * bigInteger member
     * 
     * @return The value of the {@code bigInteger} member
     */
    public BigInteger bigInteger() {
        return this.bigInteger;
    }

    /**
     * float member
     * 
     * @return The value of the {@code float} member
     */
    public Float aFloat() {
        return this.aFloat;
    }

    /**
     * double member
     * 
     * @return The value of the {@code double} member
     */
    public Double aDouble() {
        return this.aDouble;
    }

    /**
     * bigDecimal member
     * 
     * @return The value of the {@code bigDecimal} member
     */
    public BigDecimal bigDecimal() {
        return this.bigDecimal;
    }

    /**
     * string member
     * 
     * @return The value of the {@code string} member
     */
    public String string() {
        return this.string;
    }

    /**
     * instant member
     * 
     * @return The value of the {@code instant} member
     */
    public Instant instant() {
        return this.instant;
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
        AllSimpleTypes that = (AllSimpleTypes) other;
        return Objects.equals(this.aByte, that.aByte)
            && Objects.equals(this.aShort, that.aShort)
            && Objects.equals(this.anInt, that.anInt)
            && Objects.equals(this.aLong, that.aLong)
            && Objects.equals(this.bigInteger, that.bigInteger)
            && Objects.equals(this.aFloat, that.aFloat)
            && Objects.equals(this.aDouble, that.aDouble)
            && Objects.equals(this.bigDecimal, that.bigDecimal)
            && Objects.equals(this.string, that.string)
            && Objects.equals(this.instant, that.instant);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + (aByte != null ? aByte.hashCode() : 0);
            hashCode = 31 * hashCode + (aShort != null ? aShort.hashCode() : 0);
            hashCode = 31 * hashCode + (anInt != null ? anInt.hashCode() : 0);
            hashCode = 31 * hashCode + (aLong != null ? aLong.hashCode() : 0);
            hashCode = 31 * hashCode + (bigInteger != null ? bigInteger.hashCode() : 0);
            hashCode = 31 * hashCode + (aFloat != null ? aFloat.hashCode() : 0);
            hashCode = 31 * hashCode + (aDouble != null ? aDouble.hashCode() : 0);
            hashCode = 31 * hashCode + (bigDecimal != null ? bigDecimal.hashCode() : 0);
            hashCode = 31 * hashCode + (string != null ? string.hashCode() : 0);
            hashCode = 31 * hashCode + (instant != null ? instant.hashCode() : 0);
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "AllSimpleTypes{"
            + "byte: " + aByte
            + ", short: " + aShort
            + ", int: " + anInt
            + ", long: " + aLong
            + ", bigInteger: " + bigInteger
            + ", float: " + aFloat
            + ", double: " + aDouble
            + ", bigDecimal: " + bigDecimal
            + ", string: " + string
            + ", instant: " + instant + "}";
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
        builder.withMember("byte", Node.from(aByte()));
        builder.withMember("short", Node.from(aShort()));
        builder.withMember("int", Node.from(anInt()));
        builder.withMember("long", Node.from(aLong()));
        builder.withMember("bigInteger", Node.from(bigInteger().toString()));
        builder.withMember("float", Node.from(aFloat()));
        builder.withMember("double", Node.from(aDouble()));
        builder.withMember("bigDecimal", Node.from(bigDecimal().toString()));
        builder.withMember("string", Node.from(string()));
        builder.withMember("instant", Node.from(instant().toString()));
        return builder.build();
    }

    /**
     * Deserialize a AllSimpleTypes from a {@link Node}.
     * 
     * @param node The node to deserialize from.
     * @return The deserialized instance.
     */
    public static AllSimpleTypes fromNode(Node node) {
        return fromNode(SinkValidator.instance(), node);
    }

    /**
     * Deserialize a AllSimpleTypes from a {@link Node}.
     * 
     * @param validator A validator to collect any issues found during deserialization.
     * @param node The node to deserialize from.
     * @return The deserialized instance.
     */
    public static AllSimpleTypes fromNode(Validation validator, Node node) {
        validator = validator.with("AllSimpleTypes");
        AllSimpleTypes.Builder builder = builder();
        ObjectNode obj = node.expectObjectNode();
        for (Map.Entry<StringNode, Node> kvp : obj.getMembers().entrySet()) {
            Node value = kvp.getValue();
            String key = kvp.getKey().getValue();
            switch (key) {
                case "byte":
                    builder.aByte(value.expectNumberNode().getValue().byteValue());
                    break;
                case "short":
                    builder.aShort(value.expectNumberNode().getValue().shortValue());
                    break;
                case "int":
                    builder.anInt(value.expectNumberNode().getValue().intValue());
                    break;
                case "long":
                    builder.aLong(value.expectNumberNode().getValue().longValue());
                    break;
                case "bigInteger":
                    builder.bigInteger(value.expectNumberNode().asBigDecimal().get().toBigInteger());
                    break;
                case "float":
                    builder.aFloat(value.expectNumberNode().getValue().floatValue());
                    break;
                case "double":
                    builder.aDouble(value.expectNumberNode().getValue().doubleValue());
                    break;
                case "bigDecimal":
                    builder.bigDecimal(value.expectNumberNode().asBigDecimal().get());
                    break;
                case "string":
                    builder.string(value.expectStringNode().getValue());
                    break;
                case "instant":
                    builder.instant(Instant.parse(value.expectStringNode().getValue()));
                    break;
                default:
                    validator.report(Validation.Severity.WARNING, key, () -> String.format("unknown key `%s` with value `%s`", key, value));
                    break;
            }
        }
        return builder.build();
    }

    /**
     * A class to build instances of AllSimpleTypes
     */
    public static final class Builder {
        private Byte aByte;
        private Short aShort;
        private Integer anInt;
        private Long aLong;
        private BigInteger bigInteger;
        private Float aFloat;
        private Double aDouble;
        private BigDecimal bigDecimal;
        private String string;
        private Instant instant;

        Builder() {
        }

        Builder(AllSimpleTypes data) {
            this.aByte = data.aByte;
            this.aShort = data.aShort;
            this.anInt = data.anInt;
            this.aLong = data.aLong;
            this.bigInteger = data.bigInteger;
            this.aFloat = data.aFloat;
            this.aDouble = data.aDouble;
            this.bigDecimal = data.bigDecimal;
            this.string = data.string;
            this.instant = data.instant;
        }

        /**
         * Sets the value for {@code byte}.
         * 
         * @param aByte The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aByte(Byte aByte) {
            this.aByte = aByte;
            return this;
        }

        /**
         * Sets the value for {@code short}.
         * 
         * @param aShort The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aShort(Short aShort) {
            this.aShort = aShort;
            return this;
        }

        /**
         * Sets the value for {@code int}.
         * 
         * @param anInt The value to be set.
         * @return This instance for chain calling.
         */
        public Builder anInt(Integer anInt) {
            this.anInt = anInt;
            return this;
        }

        /**
         * Sets the value for {@code long}.
         * 
         * @param aLong The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aLong(Long aLong) {
            this.aLong = aLong;
            return this;
        }

        /**
         * Sets the value for {@code bigInteger}.
         * 
         * @param bigInteger The value to be set.
         * @return This instance for chain calling.
         */
        public Builder bigInteger(BigInteger bigInteger) {
            this.bigInteger = bigInteger;
            return this;
        }

        /**
         * Sets the value for {@code float}.
         * 
         * @param aFloat The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aFloat(Float aFloat) {
            this.aFloat = aFloat;
            return this;
        }

        /**
         * Sets the value for {@code double}.
         * 
         * @param aDouble The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aDouble(Double aDouble) {
            this.aDouble = aDouble;
            return this;
        }

        /**
         * Sets the value for {@code bigDecimal}.
         * 
         * @param bigDecimal The value to be set.
         * @return This instance for chain calling.
         */
        public Builder bigDecimal(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
            return this;
        }

        /**
         * Sets the value for {@code string}.
         * 
         * @param string The value to be set.
         * @return This instance for chain calling.
         */
        public Builder string(String string) {
            this.string = string;
            return this;
        }

        /**
         * Sets the value for {@code instant}.
         * 
         * @param instant The value to be set.
         * @return This instance for chain calling.
         */
        public Builder instant(Instant instant) {
            this.instant = instant;
            return this;
        }

        /**
         * Returns a new instance of {@link AllSimpleTypes}
         * 
         * @return A new instance of {@link AllSimpleTypes}
         */
        public AllSimpleTypes build() {
            return new AllSimpleTypes(this);
        }
    }

    public static class AllSimpleTypesBuilderReference extends AbstractBuilderReference<AllSimpleTypes, Builder> {

        AllSimpleTypesBuilderReference(AllSimpleTypes source) {
            super(source);
        }

        @Override
        protected Builder emptyTransient() {
            return AllSimpleTypes.builder();
        }

        @Override
        protected AllSimpleTypes transientToPersistent(Builder builder) {
            return builder.build();
        }

        @Override
        protected Builder persistentToTransient(AllSimpleTypes source) {
            return source.toBuilder();
        }

        @Override
        protected Builder clearTransient(Builder builder) {
            return AllSimpleTypes.builder();
        }

        public static AllSimpleTypesBuilderReference from(AllSimpleTypes source) {
            return new AllSimpleTypesBuilderReference(source);
        }
    }
}
