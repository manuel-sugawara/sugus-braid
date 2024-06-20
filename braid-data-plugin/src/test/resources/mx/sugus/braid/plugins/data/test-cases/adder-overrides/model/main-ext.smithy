$version: "2.0"

namespace mx.sugus.braid.test

use mx.sugus.braid.traits#addAllOverrides
use mx.sugus.braid.traits#adderOverrides
use mx.sugus.braid.traits#fromFactories
use mx.sugus.braid.traits#multiAddOverrides

apply StructureShape1 @fromFactories([
    {
        javadoc: """
        Creates a new structure with the given value.
        """
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
        body: ["return builder().name(stringValue).intValue(intValue).build()"]
    }
])

apply StructureShape1List @adderOverrides([
    {
        name: "addFromAdderOverride"
        args: [
            {
                type: "java.lang#String"
                name: "value"
            }
        ]
        body: ["StructureShape1.builder().name(value).build()"]
    }
    {
        name: "addFromAdderOverride"
        args: [
            {
                type: "java.lang#String"
                name: "value"
            }
            {
                type: "java.lang#Integer"
                name: "intValue"
            }
        ]
        body: ["StructureShape1.builder().name(value).intValue(intValue).build()"]
    }
])

apply StructureShape1List @multiAddOverrides([
    {
        args: [
            {
                type: "StructureShape1"
                name: "value1"
            }
            {
                type: "StructureShape1"
                name: "value2"
            }
        ]
        body: ["value1" "value2"]
    }
])

apply StructureShape1List @addAllOverrides([
    {
        name: "addsAllFromStructure2"
        javadoc: """
            Adds the values
            """
        args: [
            {
                type: "StructureShape2"
                name: "value"
            }
        ]
        body: ["value.shapeOnes()"]
    }
])

