$version: "2.0"

namespace mx.sugus.braid.test

/// A simple structure shape one
structure StructureShape1 {
    @required
    name: String

    intValue: Integer
}

/// A simple structure shape two
structure StructureShape2 {
    name: String
    shapeOne: StructureShape1
}
