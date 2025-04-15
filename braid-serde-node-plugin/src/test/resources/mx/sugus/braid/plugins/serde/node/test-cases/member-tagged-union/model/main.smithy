$version: "2.0"

namespace mx.sugus.braid.test

use mx.sugus.braid.traits#const
use mx.sugus.braid.traits#implements
use mx.sugus.braid.traits#interface

@interface
structure SyntaxNode {}

// -- Syntax Format
enum ChildKind {
    FOO = "foo"
    BAR = "bar"
    BAZ = "baz"
}

@interface
structure SyntaxNodeChild {
    @const(ChildKind)
    kind: ChildKind
}


@implements([SyntaxNodeChild, SyntaxNode])
structure ChildFoo {
    @const(ChildKind$FOO)
    kind: ChildKind

    foo: String
}

@implements([SyntaxNodeChild, SyntaxNode])
structure ChildBar {
    @const(ChildKind$BAR)
    kind: ChildKind

    bar: String
}

@implements([SyntaxNodeChild, SyntaxNode])
structure ChildBaz {
    @const(ChildKind$BAZ)
    kind: ChildKind

    baz: String
}

@implements([SyntaxNode])
structure AnotherChild {
    stringValue: String
    intValue: Integer
}

@implements([SyntaxNode])
structure Parent {
    child: SyntaxNodeChild
    anotherChild: AnotherChild
}
