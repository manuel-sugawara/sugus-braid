$version: "2.0"

namespace mx.sugus.braid.test

use mx.sugus.braid.traits#newBuilderOverrides

apply StructureShape1 @newBuilderOverrides([
    {
        args: [
            {
                type: "java.lang#String"
                name: "name"
            }
        ]
        body: ["return builder().name(name)"]
    }
])

