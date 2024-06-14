$version: "2.0"

namespace mx.sugus.braid.test

/// Enum with one constant, no value
enum EnumWithoutValue1_1 {
    /// Lower case
    one
}

/// Enum with one constant, no value
enum EnumWithoutValue1_2 {
    /// Pascal case
    One
}

/// Enum with one constant, no value
enum EnumWithoutValue1_3 {
    /// Screaming case
    ONE
}

/// Enum with one constant, no value
enum EnumWithValue1_1 {
    /// Lower case
    one = "1"
}

/// Enum with one constant, no value
enum EnumWithValue1_2 {
    /// Pascal case
    One = "1"
}

/// Enum with one constant, no value
enum EnumWithValue1_3 {
    /// Screaming case
    ONE = "1"
}

/// Enum with one constant, no value
enum EnumWithoutValue2_1 {
    /// Lower case
    one
    /// Two
    two
}

/// Enum with one constant, no value
enum EnumWithoutValue2_2 {
    /// Pascal case
    One
    /// Two
    Two
}

/// Enum with one constant, no value
enum EnumWithoutValue2_3 {
    /// Screaming case
    ONE
    /// Two
    TWO
}

/// Enum with one constant, no value
enum EnumWithValue2_1 {
    /// Lower case
    one = "1"
    /// Two
    two = "2"
}

/// Enum with one constant, no value
enum EnumWithValue2_2 {
    /// Pascal case
    One = "1"
    /// Two
    Two = "2"
}

/// Enum with one constant, no value
enum EnumWithValue2_3 {
    /// Screaming case
    ONE = "1"
    /// Two
    TWO = "2"
}

/// This enum is sensitive
@sensitive
enum SensitiveEnum {
    SECRET1 = "secret one"
    SECRET2 = "secret two"
}
