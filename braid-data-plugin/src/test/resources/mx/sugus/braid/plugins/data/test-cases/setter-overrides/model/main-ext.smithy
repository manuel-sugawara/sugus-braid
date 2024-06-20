$version: "2.0"

namespace mx.sugus.braid.test

use mx.sugus.braid.traits#setterOverrides

apply StructureShape1 @setterOverrides([
    {
        args: [
            {
                type: "java.lang#String"
                name: "stringValue"
            }
            {
                type: "java.lang#Integer"
                name: "intValue"
            }
        ]
        body: "StructureShape1.builder().name(stringValue).intValue(intValue).build()"
    }
])
