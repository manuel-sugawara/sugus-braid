$version: "2.0"

namespace mx.sugus.braid.test

use mx.sugus.braid.traits#useBuilderReference

structure StructureOne {
    stringMember: String
    intMember: Integer
}

@useBuilderReference
structure StructureTwo {
    floatMember: Float
    stringMember: String
}

structure StructureThree {
    structureOne: StructureOne
    structureTwo: StructureTwo
}