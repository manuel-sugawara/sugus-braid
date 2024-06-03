$version: "2.0"

namespace mx.sugus.braid.jsyntax

use mx.sugus.braid.traits#const
use mx.sugus.braid.traits#implements
use mx.sugus.braid.traits#interface
use mx.sugus.braid.traits#java
use mx.sugus.braid.traits#optional
use mx.sugus.braid.traits#useBuilderReference

@interface
structure SyntaxNode {}

// -- Syntax Format
enum SyntaxFormatterNodeKind {
    LITERAL = "literal"
    STRING = "string"
    TYPE_NAME = "type-name"
    BLOCK = "block"
}

@interface
structure FormatterNode {
    kind: SyntaxFormatterNodeKind
}

@implements([FormatterNode])
structure FormatterLiteral {
    @const(SyntaxFormatterNodeKind$LITERAL)
    kind: SyntaxFormatterNodeKind

    value: String
}

@implements([FormatterNode])
structure FormatterString {
    @const(SyntaxFormatterNodeKind$STRING)
    kind: SyntaxFormatterNodeKind

    value: String
}

@implements([FormatterNode])
structure FormatterTypeName {
    @const(SyntaxFormatterNodeKind$TYPE_NAME)
    kind: SyntaxFormatterNodeKind

    value: TypeName
}

@implements([FormatterNode])
structure FormatterBlock {
    @const(SyntaxFormatterNodeKind$BLOCK)
    kind: SyntaxFormatterNodeKind

    value: Block
}

list FormatterNodeList {
    member: FormatterNode
}

@implements([SyntaxNode, Expression, EnumBody, Statement, Javadoc])
structure CodeBlock {
    @const(StatementKind$FORMAT)
    stmtKind: StatementKind

    parts: FormatterNodeList
}

// --- Marker interfaces
@implements([SyntaxNode])
@interface
structure Expression {}

@implements([SyntaxNode])
@interface
structure Javadoc {}

@implements([SyntaxNode])
@interface
structure EnumBody {}

// --- Statements
/// The type of statement
/// Kinds of methods
enum StatementKind {
    FORMAT = "format"
    BLOCK = "block"
    ABSTRACT_CONTROL_FLOW = "control-flow"
    IF_STATEMENT = "if"
    FOR_STATEMENT = "for"
    SWITCH_STATEMENT = "switch"
}

/// Represents a Java statement.
@implements([SyntaxNode])
@interface
structure Statement {
    /// The concrete type of statement.
    stmtKind: StatementKind
}

list StatementList {
    member: Statement
}

/// A block is a collection of statements.
@implements([Statement])
@useBuilderReference(
    builderType: "mx.sugus.braid.jsyntax.block#BodyBuilder"
    fromPersistent: "mx.sugus.braid.jsyntax.block#BodyBuilder$fromPersistent"
)
structure Block {
    @const(StatementKind$BLOCK)
    stmtKind: StatementKind

    statements: StatementList
}

/// Represents any unstructured control flow statement block.
///
/// The control flows are rendered as
/// ```
///   prefix {
///       statement_0
///          ⋮
///       statement_N
///   } <optional-next>
/// ```
@implements([Statement])
structure AbstractControlFlow {
    @const(StatementKind$ABSTRACT_CONTROL_FLOW)
    stmtKind: StatementKind

    /// The prefix for this control flow.
    @required
    prefix: CodeBlock

    /// The body of the abstract control flow
    @required
    statement: Block

    /// An optional `next` block.
    next: AbstractControlFlow
}

/// Represents an `if` statement.
@implements([Statement])
structure IfStatement {
    @const(StatementKind$IF_STATEMENT)
    stmtKind: StatementKind

    /// The condition of the `if` statement
    @required
    expression: Expression

    /// The body of the `if` statement
    @required
    statement: Block

    /// An optional `else` block.
    @optional
    elseStatement: Statement
}

/// Represents an `for` statement.
@implements([Statement])
structure ForStatement {
    @const(StatementKind$FOR_STATEMENT)
    stmtKind: StatementKind

    /// Represents the initialization block of the `for` statement.
    ///
    /// Accommodates traditional `for` and enhanced `for` statements.
    @required
    initializer: CodeBlock

    /// The body of the `for` statement.
    @required
    statement: Block
}

list LabelList {
    member: Expression
}

/// Represents a `case` clause inside a switch statement.
@implements([SyntaxNode])
structure CaseClause {
    label: LabelList

    @required
    body: Block
}

list CaseClauseList {
    member: CaseClause
}

/// Represents a `default` clause inside a switch statement.
@implements([SyntaxNode])
structure DefaultCaseClause {
    @required
    body: Block
}

/// Represents a `switch` statement.
@implements([Statement])
structure SwitchStatement {
    @const(StatementKind$SWITCH_STATEMENT)
    stmtKind: StatementKind

    @required
    expression: Expression

    cases: CaseClauseList

    @optional
    defaultCase: DefaultCaseClause
}

// --- Java Types
@java("javax.lang.model.element.Modifier")
structure Modifier {}

@uniqueItems
list ModifierList {
    member: Modifier
}

/// Represent an `annotation`.
@implements([SyntaxNode])
structure Annotation {
    @required
    type: ClassName

    member: String

    value: CodeBlock
}

list AnnotationList {
    member: Annotation
}

/// Represents a parameter of a method.
@implements([SyntaxNode])
structure Parameter {
    @required
    name: String

    @required
    type: TypeName

    @required
    @default(false)
    varargs: Boolean
}

list ParameterList {
    member: Parameter
}

/// Kinds of methods
enum MethodKind {
    CONCRETE = "concrete"
    ABSTRACT = "abstract"
    CONSTRUCTOR = "constructor"
}

/// Base type for all methods.
@implements([SyntaxNode])
@interface
structure BaseMethodSyntax {
    /// The concrete kind for this method
    kind: MethodKind

    /// The javadoc for the type.
    javadoc: Javadoc

    /// A list of annotations for this method
    annotations: AnnotationList

    /// A list of modifiers for this method
    modifiers: ModifierList

    /// A list of parameters method
    parameters: ParameterList
}

/// Represents a concrete method
@implements([BaseMethodSyntax])
structure MethodSyntax {
    @const(MethodKind$CONCRETE)
    kind: MethodKind

    /// The name of the method
    @required
    name: String

    /// An opetional set of type params for this method
    typeParams: TypeVariableTypeNames

    /// The return type for the method
    @required
    returns: TypeName

    /// The body of the method.
    @required
    body: Block
}

/// Represents an abstract method.
@implements([BaseMethodSyntax])
structure AbstractMethodSyntax {
    @const(MethodKind$ABSTRACT)
    kind: MethodKind

    /// The name of the method
    @required
    name: String

    /// An optional set of type params for this method
    typeParams: TypeVariableTypeNames

    /// The retrun type for the method
    @required
    returns: TypeName
}

/// Represents a constructor method
@implements([BaseMethodSyntax])
structure ConstructorMethodSyntax {
    @const(MethodKind$CONSTRUCTOR)
    kind: MethodKind

    @required
    body: Block
}

/// Represents a class field.
@implements([SyntaxNode])
structure FieldSyntax {
    /// The javadoc for the type.
    javadoc: Javadoc

    /// The name of the field.
    @required
    name: String

    /// The type of the field.
    @required
    type: TypeName

    /// A list of modifiers for the field.
    modifiers: ModifierList

    /// A list of annotations for the field.
    annotations: AnnotationList

    /// A initialization expression
    initializer: Expression
}

list FieldSyntaxList {
    member: FieldSyntax
}

list BaseMethodSyntaxList {
    member: BaseMethodSyntax
}

// -- Class Syntax
/// Kind of the supported Java types
enum TypeSyntaxKind {
    /// A class type
    CLASS = "class"

    /// A interface
    INTERFACE = "interface"

    /// A enum type
    ENUM = "enum"
}

/// Base type for other Java types.
@implements([SyntaxNode])
@interface
structure TypeSyntax {
    kind: TypeSyntaxKind

    /// The javadoc for the type.
    javadoc: Javadoc

    /// The simple name for the type.
    @required
    name: String

    /// A list of modifiers for this type.
    modifiers: ModifierList

    /// A list of annotations for this type.
    annotations: AnnotationList

    /// A list of super interfaces for this type.
    superInterfaces: TypeNameList

    /// A list of fields for this type.
    fields: FieldSyntaxList

    /// A list of methods for this type.
    methods: BaseMethodSyntaxList

    /// A list of inner types enclosed by this type.
    innerTypes: TypeSyntaxList
}

list TypeSyntaxList {
    member: TypeSyntax
}

/// Represents a Java class.
@implements([TypeSyntax])
structure ClassSyntax {
    /// The type syntax kind with constant value of `CLASS`
    @const(TypeSyntaxKind$CLASS)
    kind: TypeSyntaxKind

    /// The super class of this class.
    superClass: TypeName

    /// A of types parameters for this type.
    typeParams: TypeVariableTypeNames
}

/// Represents a java interface.
@implements([TypeSyntax])
structure InterfaceSyntax {
    /// The type syntax kind with constant value of `INTERFACE`
    @const(TypeSyntaxKind$INTERFACE)
    kind: TypeSyntaxKind

    /// A of types parameters for this type.
    typeParams: TypeVariableTypeNames
}

// --- Enum Syntax
/// Represents a Java enum value.
@implements([SyntaxNode])
structure EnumConstant {
    /// The javadoc for the enum constant.
    javadoc: Javadoc

    /// The name for the constant.
    @required
    name: String

    /// An optional body for the constant.
    body: EnumBody
}

list EnumConstantList {
    member: EnumConstant
}

/// Represents a Java enum class.
@implements([TypeSyntax])
structure EnumSyntax {
    /// The type syntax kind with constant value of `ENUM`
    @const(TypeSyntaxKind$ENUM)
    kind: TypeSyntaxKind

    /// The list of enum constants for this enum.
    enumConstants: EnumConstantList
}

// --- Java Types ---
/// Kind of the supported Java types
enum TypeKind {
    /// A primitive type
    PRIMITIVE = "primitive"

    /// A Java Class type
    CLASS = "class"

    /// An array type
    ARRAY = "array"

    /// A generic Java class
    PARAMETERIZED = "parameterized"

    /// A type variable
    TYPE_VARIABLE = "type-variable"

    /// A wildcard type bound
    WILDCARD = "wildcard"
}

/// An marker interface for all java types.
@interface
@implements([SyntaxNode])
structure TypeName {
    kind: TypeKind
}

list TypeNameList {
    member: TypeName
}

enum TypePrimitiveName {
    VOID = "void"
    BOOLEAN = "boolean"
    BYTE = "byte"
    SHORT = "short"
    INT = "int"
    LONG = "long"
    CHAR = "char"
    FLOAT = "float"
    DOUBLE = "double"
}

/// Represents a java primitive type.
@implements([TypeName])
structure PrimitiveTypeName {
    @const(TypeKind$PRIMITIVE)
    kind: TypeKind

    @required
    name: TypePrimitiveName
}

/// Represent the name of a Java class
@implements([TypeName])
structure ClassName {
    @const(TypeKind$CLASS)
    kind: TypeKind

    @required
    name: String

    packageName: String
}

/// Represents a java array type.
@implements([TypeName])
structure ArrayTypeName {
    @const(TypeKind$ARRAY)
    kind: TypeKind

    @required
    componentType: TypeName
}

/// Represents a parametrized java type.
@implements([TypeName])
structure ParameterizedTypeName {
    @const(TypeKind$PARAMETERIZED)
    kind: TypeKind

    @required
    rawType: ClassName

    typeArguments: TypeNameList
}

/// Represents a type variable name.
@implements([TypeName])
structure TypeVariableTypeName {
    @const(TypeKind$TYPE_VARIABLE)
    kind: TypeKind

    @required
    name: String

    bounds: TypeNameList
}

list TypeVariableTypeNames {
    member: TypeVariableTypeName
}

/// Represents a wildcard type name.
@implements([TypeName])
structure WildcardTypeName {
    @const(TypeKind$WILDCARD)
    kind: TypeKind

    rawType: ClassName

    upperBounds: TypeNameList

    lowerBounds: TypeNameList
}

// --- Java File
@uniqueItems
list ImportList {
    member: ClassName
}

map SimpleNameToClassName {
    key: String
    value: ClassName
}

/// Represents a unit of compilation for the Java compiler, i.e., a single Java source file.
@implements([SyntaxNode])
structure CompilationUnit {
    @required
    packageName: String

    imports: ImportList

    @required
    type: TypeSyntax

    definedNames: SimpleNameToClassName
}
