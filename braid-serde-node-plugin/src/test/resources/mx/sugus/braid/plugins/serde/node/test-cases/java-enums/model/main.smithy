$version: "2.0"

namespace mx.sugus.braid.test

use mx.sugus.braid.traits#java

@java("javax.lang.model.element.Modifier")
structure Modifier {}

list ModifierList {
    member: Modifier
}

structure StructureShape {
    modifier: Modifier
    @required
    anotherModifier: Modifier
    modifierList: ModifierList
}
