$version: "2.0"

namespace mx.sugus.braid.test

structure StructureOne {
    intMember: Integer
}

list StructureOneList {
    member: StructureOne
}

structure StructureTwo {
    intMember: Integer
    stringMember: String
}

map StringToStructureTwo {
    key: String
    value: StructureTwo
}

structure StructureThree {
    structureOne: StructureOne
}

@uniqueItems
list StructureThreeSet {
    member: StructureThree
}

structure StructureWithAggregates {
    simpleMember: Long
    structuresOne: StructureOneList
    structureTwoMap: StringToStructureTwo
    structureThreeSet: StructureThreeSet
}