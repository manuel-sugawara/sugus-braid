$version: "2.0"

namespace mx.sugus.braid.traits

/// Marks a member as constant with the given value.
@trait(selector: "structure > member")
string const

/// Marks a structure as an interface
@trait(selector: "structure")
structure interface {}

/// Marks any structure with a java type name.
@trait(selector: "*")
string java

/// Marks a member as optional.
@trait(selector: "structure > member")
structure optional {}

/// Marks any structure with a java type name.
@trait(selector: ":is(structure, union)")
list implements {
    member: String
}

//--- Extensions
structure Argument {
    @required
    type: String
    @required
    name: String
}

list ArgumentList {
    member: Argument
}

list StringList {
    member: String
}

structure SetterOverride {
    args: ArgumentList
    body: String
}

@trait(selector: "structure")
list setterOverrides {
    member: SetterOverride
}

structure BuilderOverride {
    name: String
    javadoc: String
    args: ArgumentList
    body: StringList
}

@trait(selector: ":is(structure, list)")
list adderOverrides {
    member: BuilderOverride
}

@trait(selector: ":is(structure, list)")
list addAllOverrides {
    member: BuilderOverride
}

@trait
list newBuilderOverrides {
    member: BuilderOverride
}

@trait(selector: ":is(structure, list)")
list multiAddOverrides {
    member: BuilderOverride
}

@trait(selector: "structure")
structure useBuilderReference {
    builderType: String
    fromPersistent: String
}

@trait(selector: "structure")
list fromFactories {
    member: BuilderOverride
}
