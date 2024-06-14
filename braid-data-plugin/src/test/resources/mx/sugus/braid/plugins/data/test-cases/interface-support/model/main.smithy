$version: "2.0"

namespace mx.sugus.braid.test

use mx.sugus.braid.traits#const
use mx.sugus.braid.traits#implements
use mx.sugus.braid.traits#interface
use mx.sugus.braid.traits#java

enum FormatKind {
    STRING, NUMBER, BOOLEAN
}

@java("java.lang.Object")
structure Object {}

/// A structure to test interface support
@interface
structure Format {
    /// The format kind
    kind: FormatKind
    /// The format value
    value: Object
}

/// Format string
@implements([Format])
structure FormatString {
    @const(FormatKind$STRING)
    kind: FormatKind
    /// The string value
    value: String
}

/// Format number
@implements([Format])
structure FormatNumber {
    @const(FormatKind$NUMBER)
    kind: FormatKind
    /// The number value
    value: BigDecimal
}

/// Format boolean
@implements([Format])
structure FormatBoolean {
    @const(FormatKind$BOOLEAN)
    kind: FormatKind
    /// The boolean value
    value: Boolean
}