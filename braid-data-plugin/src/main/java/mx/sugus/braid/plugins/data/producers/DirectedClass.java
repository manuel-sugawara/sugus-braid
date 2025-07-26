package mx.sugus.braid.plugins.data.producers;

import java.util.List;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.TypeSyntax;
import software.amazon.smithy.model.shapes.MemberShape;

/**
 * Interface for directing the generation of Java class syntax from Smithy shapes.
 *
 * <p>This interface provides a template method pattern for generating Java classes
 * from Smithy shapes, allowing implementations to customize different aspects of
 * class generation while following a consistent structure. It extends
 * {@link DirectiveToTypeSyntax} to provide the overall generation framework.
 *
 * <p>The class generation process follows this template:
 * <ol>
 *   <li><strong>Class Declaration</strong> - Define the class signature, modifiers, and inheritance</li>
 *   <li><strong>Field Generation</strong> - Create fields for each Smithy shape member plus any extra fields</li>
 *   <li><strong>Constructor Generation</strong> - Generate constructors for object initialization</li>
 *   <li><strong>Method Generation</strong> - Create accessor methods for members plus extra methods</li>
 *   <li><strong>Abstract Method Generation</strong> - Add abstract methods if generating interfaces</li>
 *   <li><strong>Inner Type Generation</strong> - Generate nested classes, enums, or interfaces</li>
 * </ol>
 *
 * <p>Implementations can override specific methods to customize particular aspects:
 * <ul>
 *   <li>{@link #typeSpec(ShapeCodegenState)} - Customize class declaration and modifiers</li>
 *   <li>{@link #fieldsFor(ShapeCodegenState, MemberShape)} - Generate fields for each member</li>
 *   <li>{@link #methodsFor(ShapeCodegenState, MemberShape)} - Generate methods for each member</li>
 *   <li>{@link #extraFields(ShapeCodegenState)} - Add additional fields beyond shape members</li>
 *   <li>{@link #extraMethods(ShapeCodegenState)} - Add utility methods, builders, etc.</li>
 *   <li>{@link #innerTypes(ShapeCodegenState)} - Generate nested types like builder classes</li>
 * </ul>
 *
 * <p>This interface is typically implemented by shape-specific generators such as:
 * <ul>
 *   <li>Structure generators for data classes with builders</li>
 *   <li>Union generators for sealed interfaces and variant classes</li>
 *   <li>Interface generators for abstract contracts</li>
 * </ul>
 *
 * <p>Example usage pattern:
 * <pre>{@code
 * public class MyStructureGenerator implements DirectedClass {
 *     @Override
 *     public ClassSyntax.Builder typeSpec(ShapeCodegenState state) {
 *         return ClassSyntax.builder()
 *             .name(className(state))
 *             .addModifier(PUBLIC, FINAL);
 *     }
 *
 *     @Override
 *     public List<FieldSyntax> fieldsFor(ShapeCodegenState state, MemberShape member) {
 *         return List.of(generateFieldForMember(member));
 *     }
 *     // ... other methods
 * }
 * }</pre>
 *
 * @see DirectiveToTypeSyntax for the base type generation interface
 * @see ClassSyntax for the Java class syntax representation
 * @see ShapeCodegenState for the generation context
 */
public interface DirectedClass extends DirectiveToTypeSyntax {
    /**
     * Returns the Java class name for the given Smithy shape.
     *
     * <p>This default implementation converts the Smithy shape name to a Java-compatible
     * class name using standard naming conventions (PascalCase, reserved word handling, etc.).
     *
     * @param state the shape generation context
     * @return the Java class name for the shape
     */
    @Override
    default ClassName className(ShapeCodegenState state) {
        return ClassName.toClassName(Utils.toJavaTypeName(state, state.shape()));
    }

    /**
     * Creates the base class syntax builder with class declaration details.
     *
     * <p>Implementations should configure the class builder with:
     * <ul>
     *   <li>Class name and modifiers (public, final, abstract, etc.)</li>
     *   <li>Superclass and interface implementations</li>
     *   <li>Generic type parameters</li>
     *   <li>Annotations and documentation</li>
     * </ul>
     *
     * <p>This method defines the overall class structure before fields and methods are added.
     *
     * @param state the shape generation context containing the Smithy shape and dependencies
     * @return a class syntax builder configured with the class declaration
     */
    ClassSyntax.Builder typeSpec(ShapeCodegenState state);

    /**
     * Generates field declarations for a specific Smithy shape member.
     *
     * <p>This method is called once for each member of the Smithy shape to generate
     * the corresponding Java fields. Implementations typically create private final
     * fields with appropriate types and annotations.
     *
     * <p>Common field generation patterns:
     * <ul>
     *   <li>Private final fields for immutable data</li>
     *   <li>Nullable annotations based on member optionality</li>
     *   <li>Validation annotations for constraints</li>
     *   <li>Documentation from member traits</li>
     * </ul>
     *
     * @param state the shape generation context
     * @param member the Smithy shape member to generate fields for
     * @return a list of field syntax declarations for the member
     */
    List<FieldSyntax> fieldsFor(ShapeCodegenState state, MemberShape member);

    /**
     * Generates additional fields beyond those derived from Smithy shape members.
     *
     * <p>This method allows implementations to add extra fields that are not directly
     * mapped from Smithy members, such as:
     * <ul>
     *   <li>Internal state fields for builders</li>
     *   <li>Cached computed values</li>
     *   <li>Metadata or flag fields</li>
     *   <li>Static constants</li>
     * </ul>
     *
     * <p>The default implementation returns an empty list.
     *
     * @param state the shape generation context
     * @return a list of additional field syntax declarations
     */
    default List<FieldSyntax> extraFields(ShapeCodegenState state) {
        return List.of();
    }

    /**
     * Generates constructor methods for the class.
     *
     * <p>Implementations can generate various constructor patterns:
     * <ul>
     *   <li>Package-private constructors for builder initialization</li>
     *   <li>Public constructors for direct instantiation</li>
     *   <li>Copy constructors for cloning</li>
     *   <li>Constructors with validation logic</li>
     * </ul>
     *
     * <p>The default implementation returns an empty list (no constructors).
     *
     * @param state the shape generation context
     * @return a list of constructor method syntax declarations
     */
    default List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
        return List.of();
    }

    /**
     * Generates methods related to a specific Smithy shape member.
     *
     * <p>This method is called once for each member of the Smithy shape to generate
     * member-specific methods such as:
     * <ul>
     *   <li>Getter methods for field access</li>
     *   <li>Builder setter methods</li>
     *   <li>Validation methods</li>
     *   <li>Transformation methods</li>
     * </ul>
     *
     * <p>The default implementation returns an empty list.
     *
     * @param state the shape generation context
     * @param member the Smithy shape member to generate methods for
     * @return a list of method syntax declarations for the member
     */
    default List<MethodSyntax> methodsFor(ShapeCodegenState state, MemberShape member) {
        return List.of();
    }

    /**
     * Generates abstract methods related to a specific Smithy shape member.
     *
     * <p>This method is used when generating interfaces or abstract classes to create
     * abstract method declarations for each member. Common patterns include:
     * <ul>
     *   <li>Abstract getter methods in interfaces</li>
     *   <li>Abstract validation methods</li>
     *   <li>Abstract transformation methods</li>
     * </ul>
     *
     * <p>The default implementation returns an empty list.
     *
     * @param state the shape generation context
     * @param member the Smithy shape member to generate abstract methods for
     * @return a list of abstract method syntax declarations for the member
     */
    default List<AbstractMethodSyntax> abstractMethodsFor(ShapeCodegenState state, MemberShape member) {
        return List.of();
    }

    /**
     * Generates additional methods beyond those derived from Smithy shape members.
     *
     * <p>This method allows implementations to add utility and lifecycle methods:
     * <ul>
     *   <li>Static factory methods</li>
     *   <li>Builder creation methods</li>
     *   <li>equals(), hashCode(), toString() implementations</li>
     *   <li>Serialization/deserialization methods</li>
     *   <li>Validation and transformation utilities</li>
     * </ul>
     *
     * <p>The default implementation returns an empty list.
     *
     * @param state the shape generation context
     * @return a list of additional method syntax declarations
     */
    default List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        return List.of();
    }

    /**
     * Generates additional abstract methods beyond those derived from Smithy shape members.
     *
     * <p>This method is used for interfaces or abstract classes to add abstract methods
     * that are not directly tied to specific shape members, such as:
     * <ul>
     *   <li>Abstract lifecycle methods</li>
     *   <li>Abstract visitor pattern methods</li>
     *   <li>Abstract validation or transformation contracts</li>
     * </ul>
     *
     * <p>The default implementation returns an empty list.
     *
     * @param state the shape generation context
     * @return a list of additional abstract method syntax declarations
     */
    default List<AbstractMethodSyntax> extraAbstractMethods(ShapeCodegenState state) {
        return List.of();
    }

    /**
     * Generates nested inner types within the class.
     *
     * <p>This method allows implementations to generate nested classes, interfaces,
     * enums, or other types within the main class:
     * <ul>
     *   <li>Builder classes for fluent construction</li>
     *   <li>Visitor interfaces for union types</li>
     *   <li>Tag enumerations for variant identification</li>
     *   <li>Helper classes or utility types</li>
     * </ul>
     *
     * <p>Each inner type is generated using its own {@link DirectiveToTypeSyntax}
     * implementation, allowing for recursive composition of complex type hierarchies.
     *
     * <p>The default implementation returns an empty list.
     *
     * @param state the shape generation context
     * @return a list of inner type generators
     */
    default List<DirectiveToTypeSyntax> innerTypes(ShapeCodegenState state) {
        return List.of();
    }

    /**
     * Builds the complete Java class syntax by orchestrating all generation steps.
     *
     * <p>This template method implements the overall class generation algorithm by:
     * <ol>
     *   <li>Creating the base class declaration using {@link #typeSpec(ShapeCodegenState)}</li>
     *   <li>Adding fields for each shape member via {@link #fieldsFor(ShapeCodegenState, MemberShape)}</li>
     *   <li>Adding extra fields via {@link #extraFields(ShapeCodegenState)}</li>
     *   <li>Adding constructors via {@link #constructors(ShapeCodegenState)}</li>
     *   <li>Adding methods for each member via {@link #methodsFor(ShapeCodegenState, MemberShape)}</li>
     *   <li>Adding abstract methods for each member via {@link #abstractMethodsFor(ShapeCodegenState, MemberShape)}</li>
     *   <li>Adding extra methods via {@link #extraMethods(ShapeCodegenState)}</li>
     *   <li>Adding extra abstract methods via {@link #extraAbstractMethods(ShapeCodegenState)}</li>
     *   <li>Adding inner types via {@link #innerTypes(ShapeCodegenState)}</li>
     *   <li>Building and returning the final class syntax</li>
     * </ol>
     *
     * <p>This method should not typically be overridden by implementations, as it
     * provides the standard template for class generation. Customization should be
     * done through the individual hook methods.
     *
     * @param state the shape generation context containing the Smithy shape and dependencies
     * @return the complete Java class syntax representation
     */
    @Override
    default TypeSyntax build(ShapeCodegenState state) {
        var builder = typeSpec(state);
        for (var member : state.shape().members()) {
            for (var field : fieldsFor(state, member)) {
                builder.addField(field);
            }
        }
        for (var field : extraFields(state)) {
            builder.addField(field);
        }

        for (var method : constructors(state)) {
            builder.addMethod(method);
        }
        for (var member : state.shape().members()) {
            for (var method : methodsFor(state, member)) {
                builder.addMethod(method);
            }
        }
        for (var member : state.shape().members()) {
            for (var method : abstractMethodsFor(state, member)) {
                builder.addMethod(method);
            }
        }
        for (var method : extraMethods(state)) {
            builder.addMethod(method);
        }
        for (var method : extraAbstractMethods(state)) {
            builder.addMethod(method);
        }
        for (var inner : innerTypes(state)) {
            var innerType = inner.build(state);
            builder.addInnerType(innerType);
        }
        return builder.build();
    }
}
