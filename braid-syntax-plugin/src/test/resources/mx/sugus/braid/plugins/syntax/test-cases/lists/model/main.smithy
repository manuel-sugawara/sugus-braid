$version: "2.0"

namespace mx.sugus.braid.test

use mx.sugus.braid.traits#implements
use mx.sugus.braid.traits#interface
use mx.sugus.braid.traits#ordered

@interface
structure SyntaxNode {}

@implements([SyntaxNode])
structure StructureSimple {
    value: String
}

list ListShape {
    member: StructureSimple
}

@uniqueItems
@ordered
list SetShape {
    member: StructureSimple
}

@implements([SyntaxNode])
structure StructureShape {
    stringValue: String
    structureMember: StructureSimple
    listMember: ListShape
    setMember: SetShape
}