$version: "2.0"

namespace mx.sugus.braid.test

/// A simple structure shape one
structure StructureShape1 {
    @required
    name: String

    intValue: Integer
}

/// A simple structure shape two
@sensitive
structure StructureShape2 {
    name: String
}

/// A union shape with two structure members
union UnionShape1 {

    /// The number one structure
    structureOne: StructureShape1

    /// The other structure, aka, number two structure
    structureTwo: StructureShape2
}

/// A string to string map shape.
map MapShape1 {
    key: String
    value: String
}

list ListShape1 {
    member: String
}

/// A union shape with two structure members and two collections
union UnionShape2 {

    /// The number one structure
    structureOne: StructureShape1

    /// The other structure, aka, number two structure
    structureTwo: StructureShape2

    /// A member with map value
    map1: MapShape1

    /// A member with list value
    list1: ListShape1
}

map MapShape2 {
    key: String
    value: StructureShape2
}

list ListShape2 {
    member: StructureShape2
}

/// A structure shape with two structure members and two collections
structure StructureShape3 {

    /// A byte field
    byteField: Byte

    /// A short field
    shortField: Short

    /// An integer field
    intField: Integer

    /// A required int field
    @required
    requiredIntField: Integer

    /// A long field
    longField: Long

    /// A big integer field
    bigInteger: BigInteger

    /// A float field
    floatField: Float

    /// A double field
    doubleField: Double

    /// A big decimal field
    bigDecimal: BigDecimal

    /// A big required decimal field
    @required
    requiredBigDecimal: BigDecimal

    /// The number one structure
    structureOne: StructureShape1

    /// The other structure, aka, number two structure
    @required
    structureTwo: StructureShape2

    /// A member with map value
    map1: MapShape1

    /// A member with list value
    list1: ListShape1

    /// Another member with a map with structure values
    map2: MapShape2

    /// Another member with a map with structure values
    list2: ListShape2

    /// Enum member
    enumShape: EnumShape1

    /// Map with enum member
    stringToEnumShape1: StringToEnumShape1

}

/// A enum shape, just constants, no values attached.
enum EnumShape1 {
    /// The member one.
    MEMBER_ONE

    /// The member two.
    MEMBER_TWO

    /// The member three.
    MEMBER_THREE

    /// The member four.
    MEMBER_FOUR
}

map StringToEnumShape1 {
    key: String
    value: EnumShape1
}

/// A enum shape with values
enum EnumShape2 {
    /// The member one.
    MEMBER_ONE = "one"

    /// The member two.
    MEMBER_TWO = "two"

    /// The member three.
    MEMBER_THREE = "three"

    /// The member four.
    MEMBER_FOUR = "four"
}
