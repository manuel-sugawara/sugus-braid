$version: "2.0"

namespace mx.sugus.braid.test

@sensitive
integer MySensitiveInteger

/// A union of all simple types, but int is sensitive
union AnySimpleType {
    /// byte variant
    byte: Byte
    /// short variant
    short: Short
    /// int variant
    int: MySensitiveInteger
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

@sensitive
union SensitiveUnion {
    stringSecretMember: String
    intSecretMember: Integer
}