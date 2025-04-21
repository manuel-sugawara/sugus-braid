$version: "2.0"

namespace mx.sugus.braid.test

use mx.sugus.braid.traits#implements
use mx.sugus.braid.traits#interface

@interface
structure SyntaxNode {}

@implements([SyntaxNode])
structure StructureSimple {
    value: String
}

map StringToSimple {
    key: String
    value: StructureSimple
}

@implements([SyntaxNode])
structure StructureShape {
    members: StringToSimple
}

@implements([SyntaxNode])
structure StructureShape2 {
    structureShape: StructureShape
    members: StringToSimple
}