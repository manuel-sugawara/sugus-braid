package mx.sugus.braid.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.rt.util.SinkValidator;
import mx.sugus.braid.rt.util.Validation;
import mx.sugus.braid.rt.util.annotations.Generated;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.node.ToNode;

/**
 * A union of all simple types.
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
@SuppressWarnings("unchecked")
public abstract class AnySimpleType implements ToNode {

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Returns the specific member type.
     * 
     * @return The specific member type
     */
    @SuppressWarnings("unchecked")
    public <T extends AnySimpleType> T asMember(Class<T> memberType) {
        if (memberType != getClass()) {
            throw new ClassCastException("Member of class: " + getClass().getName() + " cannot be casted to: " + memberType.getName());
        }
        return (T) this;
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
     * Returns the enum value representing which member of this object is populated.
     * <p>
     * This will be {@link VariantTag#UNKNOWN_TO_VERSION} if no member is set.
     * 
     * @return The enum value representing which member of this object is populated
     */
    public abstract VariantTag variantTag();

    public abstract <T> T variantValue();

    /**
     * Deserialize a AnySimpleType from a {@link Node}.
     * 
     * @param node The node to deserialize from.
     * @return The deserialized instance.
     */
    public static AnySimpleType fromNode(Node node) {
        return fromNode(SinkValidator.instance(), node);
    }

    /**
     * Deserialize a AnySimpleType from a {@link Node}.
     * 
     * @param validator A validator to collect any issues found during deserialization.
     * @param node The node to deserialize from.
     * @return The deserialized instance.
     */
    public static AnySimpleType fromNode(Validation validator, Node node) {
        String variantSet = null;
        validator = validator.with("AnySimpleType");
        AnySimpleType.Builder builder = builder();
        ObjectNode obj = node.expectObjectNode();
        for (Map.Entry<StringNode, Node> kvp : obj.getMembers().entrySet()) {
            Node value = kvp.getValue();
            String key = kvp.getKey().getValue();
            switch (key) {
                case "byte":
                    if (variantSet == null) {
                        builder.aByte(value.expectNumberNode().getValue().byteValue());
                        variantSet = "byte";
                    } else {
                        String variant = variantSet;
                        validator.report(Validation.Severity.ERROR, key, () -> String.format("ignoring extra variant `%s` for union with value `%s`, keeping `%s`", key, value, variant));
                    }
                    break;
                case "short":
                    if (variantSet == null) {
                        builder.aShort(value.expectNumberNode().getValue().shortValue());
                        variantSet = "short";
                    } else {
                        String variant = variantSet;
                        validator.report(Validation.Severity.ERROR, key, () -> String.format("ignoring extra variant `%s` for union with value `%s`, keeping `%s`", key, value, variant));
                    }
                    break;
                case "int":
                    if (variantSet == null) {
                        builder.anInt(value.expectNumberNode().getValue().intValue());
                        variantSet = "int";
                    } else {
                        String variant = variantSet;
                        validator.report(Validation.Severity.ERROR, key, () -> String.format("ignoring extra variant `%s` for union with value `%s`, keeping `%s`", key, value, variant));
                    }
                    break;
                case "long":
                    if (variantSet == null) {
                        builder.aLong(value.expectNumberNode().getValue().longValue());
                        variantSet = "long";
                    } else {
                        String variant = variantSet;
                        validator.report(Validation.Severity.ERROR, key, () -> String.format("ignoring extra variant `%s` for union with value `%s`, keeping `%s`", key, value, variant));
                    }
                    break;
                case "bigInteger":
                    if (variantSet == null) {
                        builder.bigInteger(value.expectNumberNode().asBigDecimal().get().toBigInteger());
                        variantSet = "bigInteger";
                    } else {
                        String variant = variantSet;
                        validator.report(Validation.Severity.ERROR, key, () -> String.format("ignoring extra variant `%s` for union with value `%s`, keeping `%s`", key, value, variant));
                    }
                    break;
                case "float":
                    if (variantSet == null) {
                        builder.aFloat(value.expectNumberNode().getValue().floatValue());
                        variantSet = "float";
                    } else {
                        String variant = variantSet;
                        validator.report(Validation.Severity.ERROR, key, () -> String.format("ignoring extra variant `%s` for union with value `%s`, keeping `%s`", key, value, variant));
                    }
                    break;
                case "double":
                    if (variantSet == null) {
                        builder.aDouble(value.expectNumberNode().getValue().doubleValue());
                        variantSet = "double";
                    } else {
                        String variant = variantSet;
                        validator.report(Validation.Severity.ERROR, key, () -> String.format("ignoring extra variant `%s` for union with value `%s`, keeping `%s`", key, value, variant));
                    }
                    break;
                case "bigDecimal":
                    if (variantSet == null) {
                        builder.bigDecimal(value.expectNumberNode().asBigDecimal().get());
                        variantSet = "bigDecimal";
                    } else {
                        String variant = variantSet;
                        validator.report(Validation.Severity.ERROR, key, () -> String.format("ignoring extra variant `%s` for union with value `%s`, keeping `%s`", key, value, variant));
                    }
                    break;
                case "string":
                    if (variantSet == null) {
                        builder.string(value.expectStringNode().getValue());
                        variantSet = "string";
                    } else {
                        String variant = variantSet;
                        validator.report(Validation.Severity.ERROR, key, () -> String.format("ignoring extra variant `%s` for union with value `%s`, keeping `%s`", key, value, variant));
                    }
                    break;
                case "instant":
                    if (variantSet == null) {
                        builder.instant(Instant.parse(value.expectStringNode().getValue()));
                        variantSet = "instant";
                    } else {
                        String variant = variantSet;
                        validator.report(Validation.Severity.ERROR, key, () -> String.format("ignoring extra variant `%s` for union with value `%s`, keeping `%s`", key, value, variant));
                    }
                    break;
                default:
                    validator.report(Validation.Severity.WARNING, key, () -> String.format("unknown key `%s` with value `%s`", key, value));
                    break;
            }
        }
        return builder.build();
    }

    public enum VariantTag {
        BYTE("byte"),
        SHORT("short"),
        INT("int"),
        LONG("long"),
        BIG_INTEGER("bigInteger"),
        FLOAT("float"),
        DOUBLE("double"),
        BIG_DECIMAL("bigDecimal"),
        STRING("string"),
        INSTANT("instant"),
        UNKNOWN_TO_VERSION(null);

        private final String value;

        VariantTag(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * byte variant
     */
    @Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
    public static final class ByteMember extends AnySimpleType {
        private final byte aByte;

        private ByteMember(byte aByte) {
            this.aByte = aByte;
        }

        /**
         * byte variant
         * 
         * @return The value of the {@code byte} member
         */
        public byte aByte() {
            return this.aByte;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) (Object) this.aByte;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.BYTE;
        }

        @Override
        public String toString() {
            return "AnySimpleType{byte: " + aByte + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            ByteMember that = (ByteMember) other;
            return this.aByte == that.aByte;
        }

        @Override
        public int hashCode() {
            return aByte;
        }

        /**
         * Converts this instance to Node.
         */
        @Override
        public Node toNode() {
            ObjectNode.Builder builder = Node.objectNodeBuilder();
            builder.withMember("byte", Node.from(aByte()));
            return builder.build();
        }
    }

    /**
     * short variant
     */
    @Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
    public static final class ShortMember extends AnySimpleType {
        private final short aShort;

        private ShortMember(short aShort) {
            this.aShort = aShort;
        }

        /**
         * short variant
         * 
         * @return The value of the {@code short} member
         */
        public short aShort() {
            return this.aShort;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) (Object) this.aShort;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.SHORT;
        }

        @Override
        public String toString() {
            return "AnySimpleType{short: " + aShort + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            ShortMember that = (ShortMember) other;
            return this.aShort == that.aShort;
        }

        @Override
        public int hashCode() {
            return aShort;
        }

        /**
         * Converts this instance to Node.
         */
        @Override
        public Node toNode() {
            ObjectNode.Builder builder = Node.objectNodeBuilder();
            builder.withMember("short", Node.from(aShort()));
            return builder.build();
        }
    }

    /**
     * int variant
     */
    @Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
    public static final class IntMember extends AnySimpleType {
        private final int anInt;

        private IntMember(int anInt) {
            this.anInt = anInt;
        }

        /**
         * int variant
         * 
         * @return The value of the {@code int} member
         */
        public int anInt() {
            return this.anInt;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) (Object) this.anInt;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.INT;
        }

        @Override
        public String toString() {
            return "AnySimpleType{int: " + anInt + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            IntMember that = (IntMember) other;
            return this.anInt == that.anInt;
        }

        @Override
        public int hashCode() {
            return anInt;
        }

        /**
         * Converts this instance to Node.
         */
        @Override
        public Node toNode() {
            ObjectNode.Builder builder = Node.objectNodeBuilder();
            builder.withMember("int", Node.from(anInt()));
            return builder.build();
        }
    }

    /**
     * long variant
     */
    @Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
    public static final class LongMember extends AnySimpleType {
        private final long aLong;

        private LongMember(long aLong) {
            this.aLong = aLong;
        }

        /**
         * long variant
         * 
         * @return The value of the {@code long} member
         */
        public long aLong() {
            return this.aLong;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) (Object) this.aLong;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.LONG;
        }

        @Override
        public String toString() {
            return "AnySimpleType{long: " + aLong + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            LongMember that = (LongMember) other;
            return this.aLong == that.aLong;
        }

        @Override
        public int hashCode() {
            return Long.hashCode(aLong);
        }

        /**
         * Converts this instance to Node.
         */
        @Override
        public Node toNode() {
            ObjectNode.Builder builder = Node.objectNodeBuilder();
            builder.withMember("long", Node.from(aLong()));
            return builder.build();
        }
    }

    /**
     * bigInteger variant
     */
    @Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
    public static final class BigIntegerMember extends AnySimpleType {
        private final BigInteger bigInteger;

        private BigIntegerMember(BigInteger bigInteger) {
            this.bigInteger = Objects.requireNonNull(bigInteger, "bigInteger");
        }

        /**
         * bigInteger variant
         * 
         * @return The value of the {@code bigInteger} member
         */
        public BigInteger bigInteger() {
            return this.bigInteger;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.bigInteger;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.BIG_INTEGER;
        }

        @Override
        public String toString() {
            return "AnySimpleType{bigInteger: " + bigInteger + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            BigIntegerMember that = (BigIntegerMember) other;
            return this.bigInteger.equals(that.bigInteger);
        }

        @Override
        public int hashCode() {
            return this.bigInteger.hashCode();
        }

        /**
         * Converts this instance to Node.
         */
        @Override
        public Node toNode() {
            ObjectNode.Builder builder = Node.objectNodeBuilder();
            builder.withMember("bigInteger", Node.from(bigInteger().toString()));
            return builder.build();
        }
    }

    /**
     * float variant
     */
    @Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
    public static final class FloatMember extends AnySimpleType {
        private final float aFloat;

        private FloatMember(float aFloat) {
            this.aFloat = aFloat;
        }

        /**
         * float variant
         * 
         * @return The value of the {@code float} member
         */
        public float aFloat() {
            return this.aFloat;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) (Object) this.aFloat;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.FLOAT;
        }

        @Override
        public String toString() {
            return "AnySimpleType{float: " + aFloat + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            FloatMember that = (FloatMember) other;
            return Float.compare(this.aFloat, that.aFloat) == 0;
        }

        @Override
        public int hashCode() {
            return Float.hashCode(aFloat);
        }

        /**
         * Converts this instance to Node.
         */
        @Override
        public Node toNode() {
            ObjectNode.Builder builder = Node.objectNodeBuilder();
            builder.withMember("float", Node.from(aFloat()));
            return builder.build();
        }
    }

    /**
     * double variant
     */
    @Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
    public static final class DoubleMember extends AnySimpleType {
        private final double aDouble;

        private DoubleMember(double aDouble) {
            this.aDouble = aDouble;
        }

        /**
         * double variant
         * 
         * @return The value of the {@code double} member
         */
        public double aDouble() {
            return this.aDouble;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) (Object) this.aDouble;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.DOUBLE;
        }

        @Override
        public String toString() {
            return "AnySimpleType{double: " + aDouble + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            DoubleMember that = (DoubleMember) other;
            return Double.compare(this.aDouble, that.aDouble) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(aDouble);
        }

        /**
         * Converts this instance to Node.
         */
        @Override
        public Node toNode() {
            ObjectNode.Builder builder = Node.objectNodeBuilder();
            builder.withMember("double", Node.from(aDouble()));
            return builder.build();
        }
    }

    /**
     * bigDecimal variant
     */
    @Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
    public static final class BigDecimalMember extends AnySimpleType {
        private final BigDecimal bigDecimal;

        private BigDecimalMember(BigDecimal bigDecimal) {
            this.bigDecimal = Objects.requireNonNull(bigDecimal, "bigDecimal");
        }

        /**
         * bigDecimal variant
         * 
         * @return The value of the {@code bigDecimal} member
         */
        public BigDecimal bigDecimal() {
            return this.bigDecimal;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.bigDecimal;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.BIG_DECIMAL;
        }

        @Override
        public String toString() {
            return "AnySimpleType{bigDecimal: " + bigDecimal + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            BigDecimalMember that = (BigDecimalMember) other;
            return this.bigDecimal.equals(that.bigDecimal);
        }

        @Override
        public int hashCode() {
            return this.bigDecimal.hashCode();
        }

        /**
         * Converts this instance to Node.
         */
        @Override
        public Node toNode() {
            ObjectNode.Builder builder = Node.objectNodeBuilder();
            builder.withMember("bigDecimal", Node.from(bigDecimal().toString()));
            return builder.build();
        }
    }

    /**
     * string variant
     */
    @Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
    public static final class StringMember extends AnySimpleType {
        private final String string;

        private StringMember(String string) {
            this.string = Objects.requireNonNull(string, "string");
        }

        /**
         * string variant
         * 
         * @return The value of the {@code string} member
         */
        public String string() {
            return this.string;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.string;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.STRING;
        }

        @Override
        public String toString() {
            return "AnySimpleType{string: " + string + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            StringMember that = (StringMember) other;
            return this.string.equals(that.string);
        }

        @Override
        public int hashCode() {
            return this.string.hashCode();
        }

        /**
         * Converts this instance to Node.
         */
        @Override
        public Node toNode() {
            ObjectNode.Builder builder = Node.objectNodeBuilder();
            builder.withMember("string", Node.from(string()));
            return builder.build();
        }
    }

    /**
     * instant variant
     */
    @Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
    public static final class InstantMember extends AnySimpleType {
        private final Instant instant;

        private InstantMember(Instant instant) {
            this.instant = Objects.requireNonNull(instant, "instant");
        }

        /**
         * instant variant
         * 
         * @return The value of the {@code instant} member
         */
        public Instant instant() {
            return this.instant;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.instant;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.INSTANT;
        }

        @Override
        public String toString() {
            return "AnySimpleType{instant: " + instant + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            InstantMember that = (InstantMember) other;
            return this.instant.equals(that.instant);
        }

        @Override
        public int hashCode() {
            return this.instant.hashCode();
        }

        /**
         * Converts this instance to Node.
         */
        @Override
        public Node toNode() {
            ObjectNode.Builder builder = Node.objectNodeBuilder();
            builder.withMember("instant", Node.from(instant().toString()));
            return builder.build();
        }
    }

    /**
     * Unknown variant type.
     */
    @Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
    public static final class $UnknownVariant extends AnySimpleType {
        private final String unknownVariantName;

        private $UnknownVariant(String name) {
            this.unknownVariantName = Objects.requireNonNull(name);
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.UNKNOWN_TO_VERSION;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.unknownVariantName;
        }

        /**
         * Converts this instance to Node.
         */
        @Override
        public Node toNode() {
            throw new UnsupportedOperationException("Unknown variant cannot be serialized");
        }
    }

    public static final class Builder {
        private Object variantValue;
        private VariantTag variantTag;

        Builder() {
            this.variantTag = VariantTag.UNKNOWN_TO_VERSION;
            this.variantValue = null;
        }

        Builder(AnySimpleType data) {
            this.variantTag = data.variantTag();
            this.variantValue = data.variantValue();
        }

        /**
         * Sets the value for {@code aByte}
         * <p>
         * byte variant
         */
        public Builder aByte(byte aByte) {
            this.variantTag = VariantTag.BYTE;
            this.variantValue = Objects.requireNonNull(aByte);
            return this;
        }

        /**
         * Sets the value for {@code aShort}
         * <p>
         * short variant
         */
        public Builder aShort(short aShort) {
            this.variantTag = VariantTag.SHORT;
            this.variantValue = Objects.requireNonNull(aShort);
            return this;
        }

        /**
         * Sets the value for {@code anInt}
         * <p>
         * int variant
         */
        public Builder anInt(int anInt) {
            this.variantTag = VariantTag.INT;
            this.variantValue = Objects.requireNonNull(anInt);
            return this;
        }

        /**
         * Sets the value for {@code aLong}
         * <p>
         * long variant
         */
        public Builder aLong(long aLong) {
            this.variantTag = VariantTag.LONG;
            this.variantValue = Objects.requireNonNull(aLong);
            return this;
        }

        /**
         * Sets the value for {@code bigInteger}
         * <p>
         * bigInteger variant
         */
        public Builder bigInteger(BigInteger bigInteger) {
            this.variantTag = VariantTag.BIG_INTEGER;
            this.variantValue = Objects.requireNonNull(bigInteger);
            return this;
        }

        /**
         * Sets the value for {@code aFloat}
         * <p>
         * float variant
         */
        public Builder aFloat(float aFloat) {
            this.variantTag = VariantTag.FLOAT;
            this.variantValue = Objects.requireNonNull(aFloat);
            return this;
        }

        /**
         * Sets the value for {@code aDouble}
         * <p>
         * double variant
         */
        public Builder aDouble(double aDouble) {
            this.variantTag = VariantTag.DOUBLE;
            this.variantValue = Objects.requireNonNull(aDouble);
            return this;
        }

        /**
         * Sets the value for {@code bigDecimal}
         * <p>
         * bigDecimal variant
         */
        public Builder bigDecimal(BigDecimal bigDecimal) {
            this.variantTag = VariantTag.BIG_DECIMAL;
            this.variantValue = Objects.requireNonNull(bigDecimal);
            return this;
        }

        /**
         * Sets the value for {@code string}
         * <p>
         * string variant
         */
        public Builder string(String string) {
            this.variantTag = VariantTag.STRING;
            this.variantValue = Objects.requireNonNull(string);
            return this;
        }

        /**
         * Sets the value for {@code instant}
         * <p>
         * instant variant
         */
        public Builder instant(Instant instant) {
            this.variantTag = VariantTag.INSTANT;
            this.variantValue = Objects.requireNonNull(instant);
            return this;
        }

        @SuppressWarnings("unchecked")
        <T> T getValue() {
            return (T) this.variantValue;
        }

        public AnySimpleType build() {
            switch (this.variantTag) {
                case BYTE:
                    return new AByteMember(getValue());
                case SHORT:
                    return new AShortMember(getValue());
                case INT:
                    return new AnIntMember(getValue());
                case LONG:
                    return new ALongMember(getValue());
                case BIG_INTEGER:
                    return new BigIntegerMember(getValue());
                case FLOAT:
                    return new AFloatMember(getValue());
                case DOUBLE:
                    return new ADoubleMember(getValue());
                case BIG_DECIMAL:
                    return new BigDecimalMember(getValue());
                case STRING:
                    return new StringMember(getValue());
                case INSTANT:
                    return new InstantMember(getValue());
                default:
                    if (this.variantValue == null) {
                        throw new NullPointerException("no value set");
                    }
                    return new $UnknownVariant((String) this.variantValue);
            }
        }
    }
}
