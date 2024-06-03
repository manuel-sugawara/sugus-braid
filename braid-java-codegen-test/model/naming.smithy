$version: "2.0"

namespace mx.sugus.braid.test2

// Smithy is language agnostic, therefore it's not concerned about Java reserved or special names,
// we need to support any modeled name even if it hurts the java side usability of the result.
// For some cases we will rename internal fields or accessors but we should avoid doing that in as
// much as possible.

structure Enum {
    int: Integer
    void: String
}

structure Void {
    enum: Enum
}

structure List {
    void: Void
}

list ListOfList {
    member: List
}

structure Map {
    member: List
}

map MapOfMap {
    key: String
    value: Map
}

structure Const {
    const: String
}

structure Objects {
    object: String
}

structure Object {
    object: String
}

structure Container {
    list: List
    const: String
    listOfLists: ListOfList
    mapOfMaps: MapOfMap
    object: Object
    objects: Objects
}