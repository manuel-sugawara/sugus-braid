$version: "2.0"

namespace mx.sugus.braid.test

@sensitive
string secretString

structure StructureWithSensitiveMember {
    stringSecretMember: secretString
}

@sensitive
structure SensitiveStructure {
    stringSecretMember: secretString
    intSecretMember: Integer
}