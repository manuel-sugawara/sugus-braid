$version: "2.0"

namespace mx.sugus.braid.test

structure Child {
    stringValue: String
    intValue: Integer
}

map ChildMap {
    key: String
    value: Child
}

map BooleanMap {
    key: String
    value: Boolean
}

map ByteMap {
    key: String
    value: Byte
}

map ShortMap {
    key: String
    value: Short
}

map IntegerMap {
    key: String
    value: Integer
}

map LongMap {
    key: String
    value: Long
}

map BigIntegerlMap {
    key: String
    value: BigInteger
}

map FloatMap {
    key: String
    value: Float
}

map DoubleMap {
    key: String
    value: Double
}

map BigDecimalMap {
    key: String
    value: BigDecimal
}

map StringMap {
    key: String
    value: String
}

enum EnumValue {
    FOO, BAR, BAZ
}

map EnumValueMap {
    key: String
    value: EnumValue
}

list IntegerList {
    member: Integer
}

map IntegerListMap {
    key: String
    value: IntegerList
}

map NestedIntegerListMap {
    key: String
    value: IntegerListMap
}

list IntegerMapList {
    member: IntegerMap
}

map NestedNestedIntegerMap {
    key: String
    value: IntegerMapList
}

structure Parent {
    stringMember: String
    children: ChildMap
    booleans: BooleanMap
    bytes: ByteMap
    shorts: ShortMap
    integers: IntegerMap
    bigIntegers: BigIntegerlMap
    longs: LongMap
    floats: FloatMap
    doubles: DoubleMap
    strings: StringMap
    bigDecimals: BigDecimalMap
    enumValues: EnumValueMap
    integerListMap: IntegerListMap
    nestedIntegerListMap: NestedIntegerListMap
    nestedNestedIntegerMap: NestedNestedIntegerMap
}
