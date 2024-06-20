$version: "2.0"

namespace mx.sugus.braid.test

use mx.sugus.braid.traits#fromFactories

apply StructureShape1 @fromFactories([
    {
        javadoc: """
        Creates a new structure with the given value.
        """
        args: [
            {
                type: "java.lang#String"
                name: "value"
            }
        ]
        body: ["return builder().name(value).build()"]
    }
])

