$version: "2.0"

namespace mx.sugus.braid.test

enum EnumShape {
    FOO, BAR, BAZ
}

structure SimpleStructure {
    value: String
}

structure StructureShape {
    @required
    enumValue: EnumShape
    @required
    stringMember: String
    @required
    structureShape: SimpleStructure
}
