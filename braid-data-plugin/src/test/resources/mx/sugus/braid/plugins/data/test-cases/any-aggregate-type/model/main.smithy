$version: "2.0"

namespace mx.sugus.braid.test

use mx.sugus.braid.traits#useBuilderReference

/// A simple structure
@useBuilderReference
structure AllSimpleTypes {
    /// byte member
    byte: Byte
    /// short member
    short: Short
    /// int member
    int: Integer
    /// long member
    long: Long
    /// bigInteger member
    bigInteger: BigInteger
    /// float member
    float: Float
    /// double member
    double: Double
    /// bigDecimal member
    bigDecimal: BigDecimal
    /// string member
    string: String
    /// instant member
    instant: Timestamp
}

/// A union of all simple types.
union AnySimpleType {
    /// byte variant
    byte: Byte
    /// short variant
    short: Short
    /// int variant
    int: Integer
    /// long variant
    long: Long
    /// bigInteger variant
    bigInteger: BigInteger
    /// float variant
    float: Float
    /// double variant
    double: Double
    /// bigDecimal variant
    bigDecimal: BigDecimal
    /// string variant
    string: String
    /// instant variant
    instant: Timestamp
}

list ListOfAllSimpleTypes {
    member: AllSimpleTypes
}

map MapOfAnySimpleType {
    key: String
    value: AnySimpleType
}

/// A union of all aggregate types.
union AnyAggregateType {
    /// structure member
    structure: AllSimpleTypes
    /// union member
    union: AnySimpleType
    /// list member
    list: ListOfAllSimpleTypes
    /// map member
    map: MapOfAnySimpleType
}
