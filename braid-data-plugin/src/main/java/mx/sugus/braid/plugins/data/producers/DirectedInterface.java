package mx.sugus.braid.plugins.data.producers;

import java.util.List;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.TypeSyntax;
import software.amazon.smithy.model.shapes.MemberShape;

/**
 * Interface for directing the generation of Java interface syntax from Smithy shapes.
 *
 * <p>This interface provides a template method pattern for generating Java interfaces
 * from Smithy shapes, allowing implementations to customize different aspects of interface generation while following a
 * consistent structure. It extends {@link DirectiveToTypeSyntax} to provide the overall generation framework.
 *
 * <p>The interface generation process follows this template:
 * <ol>
 *   <li><strong>Interface Declaration</strong> - Define the interface signature, modifiers, and extends clause</li>
 *   <li><strong>Field Generation</strong> - Add constant fields and default values</li>
 *   <li><strong>Abstract Method Generation</strong> - Create abstract method declarations for each member</li>
 *   <li><strong>Default Method Generation</strong> - Add default implementations and utility methods</li>
 *   <li><strong>Inner Type Generation</strong> - Generate nested interfaces, classes, or enums</li>
 * </ol>
 *
 * <p>Implementations can override specific methods to customize particular aspects:
 * <ul>
 *   <li>{@link #typeSpec(ShapeCodegenState)} - Customize interface declaration and inheritance</li>
 *   <li>{@link #abstractMethodsFor(ShapeCodegenState, MemberShape)} - Generate abstract methods for members</li>
 *   <li>{@link #extraAbstractMethods(ShapeCodegenState)} - Add additional abstract methods</li>
 *   <li>{@link #extraMethods(ShapeCodegenState)} - Add default implementations and utilities</li>
 *   <li>{@link #extraFields(ShapeCodegenState)} - Add constant fields</li>
 *   <li>{@link #innerTypes(ShapeCodegenState)} - Generate nested types</li>
 * </ul>
 *
 * <p>Java interface generation patterns supported:
 * <ul>
 *   <li><strong>Contract interfaces</strong> - Pure abstract contracts with no implementations</li>
 *   <li><strong>Mixin interfaces</strong> - With default method implementations</li>
 *   <li><strong>Marker interfaces</strong> - Empty interfaces for type classification</li>
 *   <li><strong>Functional interfaces</strong> - Single abstract method interfaces</li>
 *   <li><strong>Sealed interfaces</strong> - With permits clause for restricted inheritance</li>
 *   <li><strong>Generic interfaces</strong> - With type parameters and bounds</li>
 * </ul>
 *
 * <p>This interface is complementary to {@link DirectedClass} but focuses on contract
 * definition rather than data structure implementation. It's typically used for:
 * <ul>
 *   <li>Service interfaces from Smithy service shapes</li>
 *   <li>Abstract data contracts from structure shapes with @interface trait</li>
 *   <li>Union visitor interfaces for pattern matching</li>
 *   <li>Plugin extension points and SPI definitions</li>
 * </ul>
 *
 * @see DirectiveToTypeSyntax for the base type generation interface
 * @see InterfaceSyntax for the Java interface syntax representation
 * @see DirectedClass for class generation
 * @see ShapeCodegenState for the generation context
 */
public interface DirectedInterface extends DirectiveToTypeSyntax {

    /**
     * Creates the base interface syntax builder with interface declaration details.
     *
     * <p>Implementations should configure the interface builder with:
     * <ul>
     *   <li>Interface name and modifiers (public, sealed, etc.)</li>
     *   <li>Extended interfaces and type hierarchies</li>
     *   <li>Generic type parameters and bounds</li>
     *   <li>Annotations and documentation</li>
     *   <li>Permits clause for sealed interfaces</li>
     * </ul>
     *
     * <p>This method defines the overall interface contract before fields and methods are added.
     *
     * @param state the shape generation context containing the Smithy shape and dependencies
     * @return an interface syntax builder configured with the interface declaration
     */
    InterfaceSyntax.Builder typeSpec(ShapeCodegenState state);

    /**
     * Generates additional constant fields for the interface.
     *
     * <p>This method allows implementations to add constant fields such as:
     * <ul>
     *   <li>Static final constants for default values</li>
     *   <li>Interface-level configuration constants</li>
     *   <li>Shared utility instances</li>
     *   <li>Version or identifier constants</li>
     * </ul>
     *
     * <p>All fields in interfaces are implicitly public, static, and final.
     *
     * <p>The default implementation returns an empty list.
     *
     * @param state the shape generation context
     * @return a list of additional constant field syntax declarations
     */
    default List<FieldSyntax> extraFields(ShapeCodegenState state) {
        return List.of();
    }

    /**
     * Generates abstract method declarations for a specific Smithy shape member.
     *
     * <p>This method is called once for each member of the Smithy shape to generate
     * the corresponding Java abstract methods. Common patterns include:
     * <ul>
     *   <li>Getter methods for data access (getFieldName())</li>
     *   <li>Predicate methods for boolean fields (isFieldName())</li>
     *   <li>Optional accessor methods for nullable fields</li>
     *   <li>Validation methods for member constraints</li>
     * </ul>
     *
     * <p>The generated methods form the contract that implementing classes must fulfill.
     *
     * <p>The default implementation returns an empty list.
     *
     * @param state  the shape generation context
     * @param member the Smithy shape member to generate abstract methods for
     * @return a list of abstract method syntax declarations for the member
     */
    default List<AbstractMethodSyntax> abstractMethodsFor(ShapeCodegenState state, MemberShape member) {
        return List.of();
    }

    /**
     * Generates additional abstract methods beyond those derived from Smithy shape members.
     *
     * <p>This method allows implementations to add abstract methods that are not directly
     * tied to specific shape members, such as:
     * <ul>
     *   <li>Lifecycle methods (initialize(), cleanup(), etc.)</li>
     *   <li>Visitor pattern methods (accept(Visitor))</li>
     *   <li>Transformation methods (toBuilder(), copy(), etc.)</li>
     *   <li>Validation contracts (validate(), isValid())</li>
     *   <li>Serialization contracts (writeExternal(), etc.)</li>
     * </ul>
     *
     * <p>These methods define additional contracts that implementations must provide.
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
     * Generates additional default methods for the interface.
     *
     * <p>This method allows implementations to add default method implementations
     * that provide common functionality, such as:
     * <ul>
     *   <li>Convenience methods built on abstract methods</li>
     *   <li>Derived calculations (e.g., getArea() from width/height)</li>
     *   <li>Utility methods for common operations</li>
     *   <li>Default implementations for optional behavior</li>
     *   <li>Delegation methods to other interfaces</li>
     * </ul>
     *
     * <p>Default methods provide implementations that can be overridden by implementing classes.
     *
     * <p>The default implementation returns an empty list.
     *
     * @param state the shape generation context
     * @return a list of default method syntax declarations
     */
    default List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        return List.of();
    }

    /**
     * Generates nested inner types within the interface.
     *
     * <p>This method allows implementations to generate nested types such as:
     * <ul>
     *   <li>Builder interfaces for fluent construction patterns</li>
     *   <li>Visitor interfaces for pattern matching</li>
     *   <li>Tag enumerations for variant identification</li>
     *   <li>Exception classes for interface-specific errors</li>
     *   <li>Configuration classes for interface parameters</li>
     *   <li>Helper classes for common operations</li>
     * </ul>
     *
     * <p>Inner types in interfaces are implicitly public and static, making them
     * accessible as standalone types through the interface namespace.
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
     * Builds the complete Java interface syntax by orchestrating all generation steps.
     *
     * <p>This template method implements the overall interface generation algorithm by:
     * <ol>
     *   <li>Creating the base interface declaration using {@link #typeSpec(ShapeCodegenState)}</li>
     *   <li>Adding constant fields via {@link #extraFields(ShapeCodegenState)}</li>
     *   <li>Adding abstract methods for each member via {@link #abstractMethodsFor(ShapeCodegenState, MemberShape)}</li>
     *   <li>Adding extra abstract methods via {@link #extraAbstractMethods(ShapeCodegenState)}</li>
     *   <li>Adding default methods via {@link #extraMethods(ShapeCodegenState)}</li>
     *   <li>Adding inner types via {@link #innerTypes(ShapeCodegenState)}</li>
     *   <li>Building and returning the final interface syntax</li>
     * </ol>
     *
     * <p>This method should not typically be overridden by implementations, as it
     * provides the standard template for interface generation. Customization should be
     * done through the individual hook methods.
     *
     * <p>Note that the method generation order is significant:
     * <ul>
     *   <li>Abstract methods are added before default methods to ensure proper contract definition</li>
     *   <li>Member-specific methods are processed before extra methods for consistent ordering</li>
     *   <li>Inner types are added last to allow them to reference interface members</li>
     * </ul>
     *
     * @param state the shape generation context containing the Smithy shape and dependencies
     * @return the complete Java interface syntax representation
     */
    @Override
    default TypeSyntax build(ShapeCodegenState state) {
        var builder = typeSpec(state);
        for (var field : extraFields(state)) {
            builder.addField(field);
        }
        for (var member : state.shape().members()) {
            for (var method : abstractMethodsFor(state, member)) {
                builder.addMethod(method);
            }
        }
        for (var method : extraAbstractMethods(state)) {
            builder.addMethod(method);
        }
        for (var method : extraMethods(state)) {
            builder.addMethod(method);
        }
        for (var inner : innerTypes(state)) {
            var innerType = inner.build(state);
            builder.addInnerType(innerType);
        }
        return builder.build();
    }
}
