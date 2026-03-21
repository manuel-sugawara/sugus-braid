$version: "2.0"

namespace mx.sugus.braid.test

use mx.sugus.braid.traits#java

@java("software.amazon.smithy.model.node.Node")
structure Node {}

list NodeList {
    member: Node
}

structure StructureShape {
    node: Node
    @required
    anotherNode: Node
    nodeList: NodeList
}
