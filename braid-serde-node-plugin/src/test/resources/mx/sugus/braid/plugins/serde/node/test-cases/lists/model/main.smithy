$version: "2.0"

namespace mx.sugus.braid.test

structure Child {
    stringValue: String
    intValue: Integer
}

list ChildList {
    member: Child
}

list BooleanList {
    member: Boolean
}

list ByteList {
    member: Byte
}

list ShortList {
    member: Short
}

list IntegerList {
    member: Integer
}

list LongList {
    member: Long
}

list BigIntegerlList {
    member: BigInteger
}

list FloatList {
    member: Float
}

list DoubleList {
    member: Double
}

list BigDecimalList {
    member: BigDecimal
}

list StringList {
    member: String
}

enum EnumValue {
    FOO, BAR, BAZ
}

list EnumValueList {
    member: EnumValue
}

structure Parent {
    stringMember: String
    children: ChildList
    booleans: BooleanList
    bytes: ByteList
    shorts: ShortList
    integers: IntegerList
    bigIntegers: BigIntegerlList
    longs: LongList
    floats: FloatList
    doubles: DoubleList
    strings: StringList
    bigDecimals: BigDecimalList
    enumValues: EnumValueList
}
