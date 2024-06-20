$version: "2.0"

namespace mx.sugus.braid.test

/// A simple structure shape one
structure StructureShape1 {
    @required
    name: String

    intValue: Integer
}

/// A list of StructureShape1
list StructureShape1List {
    member: StructureShape1
}

/// A simple structure shape two
structure StructureShape2 {
    name: String
    shapeOnes: StructureShape1List
}
