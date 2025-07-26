package mx.sugus.braid.plugins.data.producers;

import java.util.List;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.EnumSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.TypeSyntax;

/**
 * Interface for directing the generation of Java enum syntax from Smithy enum shapes.
 *
 * <p>This interface provides a template method pattern for generating Java enums
 * from Smithy enum shapes, allowing implementations to customize different aspects of enum generation while following a
 * consistent structure. It extends {@link DirectiveToTypeSyntax} to provide the overall generation framework.
 *
 * <p>The enum generation process follows this template:
 * <ol>
 *   <li><strong>Enum Declaration</strong> - Define the enum signature, modifiers, and constants</li>
 *   <li><strong>Field Generation</strong> - Add fields for value storage and metadata</li>
 *   <li><strong>Constructor Generation</strong> - Create constructors for enum constant initialization</li>
 *   <li><strong>Method Generation</strong> - Add utility methods for value conversion and lookup</li>
 *   <li><strong>Inner Type Generation</strong> - Generate nested types if needed</li>
 * </ol>
 *
 * <p>Implementations can override specific methods to customize particular aspects:
 * <ul>
 *   <li>{@link #typeSpec(ShapeCodegenState)} - Customize enum declaration and constants</li>
 *   <li>{@link #extraFields(ShapeCodegenState)} - Add fields for value storage or metadata</li>
 *   <li>{@link #constructors(ShapeCodegenState)} - Generate constructors for enum initialization</li>
 *   <li>{@link #extraMethods(ShapeCodegenState)} - Add utility methods like value lookup</li>
 *   <li>{@link #innerTypes(ShapeCodegenState)} - Generate nested helper types</li>
 * </ul>
 *
 * <p>Java enum generation patterns supported:
 * <ul>
 *   <li><strong>Simple enums</strong> - Constants without explicit values</li>
 *   <li><strong>Value enums</strong> - Constants with associated string values</li>
 *   <li><strong>Rich enums</strong> - Constants with multiple fields and complex behavior</li>
 *   <li><strong>Documented enums</strong> - With javadoc from Smithy documentation traits</li>
 *   <li><strong>Serializable enums</strong> - With proper serialization support</li>
 * </ul>
 *
 * <p>Example generated enum structure:
 * <pre>{@code
 * // From Smithy enum:
 * enum Color {
 *     RED = "red"
 *     GREEN = "green"
 *     BLUE = "blue"
 * }
 *
 * // Generated Java enum:
 * public enum Color {
 *     RED("red"),
 *     GREEN("green"),
 *     BLUE("blue");
 *
 *     private final String value;
 *
 *     Color(String value) {
 *         this.value = value;
 *     }
 *
 *     public String getValue() { return value; }
 *     public static Color fromValue(String value) { ... }
 * }
 * }</pre>
 *
 * <p>This interface is specifically designed for enum generation and is simpler than
 * {@link DirectedClass} since enums have more constrained structure requirements.
 *
 * @see DirectiveToTypeSyntax for the base type generation interface
 * @see EnumSyntax for the Java enum syntax representation
 * @see ShapeCodegenState for the generation context
 */
public interface DirectedEnum extends DirectiveToTypeSyntax {
    /**
     * Returns the Java enum name for the given Smithy enum shape.
     *
     * <p>This default implementation converts the Smithy shape name to a Java-compatible
     * enum name using standard naming conventions (PascalCase, reserved word handling, etc.).
     *
     * @param state the shape generation context
     * @return the Java enum name for the shape
     */
    @Override
    default ClassName className(ShapeCodegenState state) {
        return ClassName.toClassName(Utils.toJavaTypeName(state, state.shape()));
    }

    /**
     * Creates the base enum syntax builder with enum declaration and constants.
     *
     * <p>Implementations should configure the enum builder with:
     * <ul>
     *   <li>Enum name and modifiers (public, etc.)</li>
     *   <li>Enum constants with proper names and values</li>
     *   <li>Interface implementations if needed</li>
     *   <li>Annotations and documentation</li>
     * </ul>
     *
     * <p>This method defines the core enum structure including all constants
     * derived from the Smithy enum members.
     *
     * @param state the shape generation context containing the Smithy enum shape and dependencies
     * @return an enum syntax builder configured with the enum declaration and constants
     */
    EnumSyntax.Builder typeSpec(ShapeCodegenState state);

    /**
     * Generates additional fields for the enum beyond the default structure.
     *
     * <p>This method allows implementations to add extra fields for:
     * <ul>
     *   <li>Value storage fields (for enums with string values)</li>
     *   <li>Metadata fields (descriptions, codes, etc.)</li>
     *   <li>Static lookup maps or arrays</li>
     *   <li>Cached computed values</li>
     * </ul>
     *
     * <p>Common patterns include a private final String field for value storage
     * and static collections for efficient lookups.
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
     * Generates constructor methods for enum constant initialization.
     *
     * <p>Implementations typically generate:
     * <ul>
     *   <li>Private constructors taking value parameters</li>
     *   <li>Constructors for storing string values or metadata</li>
     *   <li>Constructors with validation logic</li>
     * </ul>
     *
     * <p>For value-based enums, this usually includes a private constructor
     * that accepts the string value and stores it in a field.
     *
     * <p>The default implementation returns an empty list.
     *
     * @param state the shape generation context
     * @return a list of constructor method syntax declarations
     */
    default List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
        return List.of();
    }

    /**
     * Generates additional utility methods for the enum.
     *
     * <p>This method allows implementations to add utility methods such as:
     * <ul>
     *   <li>Value accessor methods (getValue(), getCode(), etc.)</li>
     *   <li>Static lookup methods (fromValue(), fromString(), etc.)</li>
     *   <li>Validation methods (isValid(), etc.)</li>
     *   <li>Conversion methods (toString() overrides, etc.)</li>
     *   <li>Utility methods for serialization frameworks</li>
     * </ul>
     *
     * <p>Common patterns include getValue() for accessing stored values
     * and static fromValue() methods for reverse lookup.
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
     * Generates nested inner types within the enum.
     *
     * <p>This method allows implementations to generate nested types such as:
     * <ul>
     *   <li>Helper classes for complex enum behavior</li>
     *   <li>Visitor interfaces for enum processing</li>
     *   <li>Builder classes for enum construction</li>
     *   <li>Exception classes for validation</li>
     * </ul>
     *
     * <p>Inner types are less common in enum generation compared to class generation
     * but can be useful for complex enum patterns.
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
     * Builds the complete Java enum syntax by orchestrating all generation steps.
     *
     * <p>This template method implements the overall enum generation algorithm by:
     * <ol>
     *   <li>Creating the base enum declaration using {@link #typeSpec(ShapeCodegenState)}</li>
     *   <li>Adding extra fields via {@link #extraFields(ShapeCodegenState)}</li>
     *   <li>Adding constructors via {@link #constructors(ShapeCodegenState)}</li>
     *   <li>Adding extra methods via {@link #extraMethods(ShapeCodegenState)}</li>
     *   <li>Adding inner types via {@link #innerTypes(ShapeCodegenState)}</li>
     *   <li>Building and returning the final enum syntax</li>
     * </ol>
     *
     * <p>This method should not typically be overridden by implementations, as it
     * provides the standard template for enum generation. Customization should be
     * done through the individual hook methods.
     *
     * <p>Note that unlike {@link DirectedClass}, this template does not iterate
     * over shape members since enum constants are handled directly in the typeSpec.
     *
     * @param state the shape generation context containing the Smithy enum shape and dependencies
     * @return the complete Java enum syntax representation
     */
    @Override
    default TypeSyntax build(ShapeCodegenState state) {
        var builder = typeSpec(state);
        for (var field : extraFields(state)) {
            builder.addField(field);
        }

        for (var method : constructors(state)) {
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
