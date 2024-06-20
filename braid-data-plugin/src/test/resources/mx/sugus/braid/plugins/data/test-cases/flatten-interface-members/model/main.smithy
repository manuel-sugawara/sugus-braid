$version: "2.0"

namespace mx.sugus.braid.test

use mx.sugus.braid.traits#implements
use mx.sugus.braid.traits#interface

@interface
structure StructureBase {
    @required
    stringValue: String
}

/// A simple structure that implements base
@implements([StructureBase])
structure StructureShape2 {

    intValue: Integer
}
