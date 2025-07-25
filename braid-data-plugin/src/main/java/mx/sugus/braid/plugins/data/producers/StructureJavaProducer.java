package mx.sugus.braid.plugins.data.producers;

import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeProducerTask;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.traits.InterfaceTrait;
import mx.sugus.braid.traits.JavaTrait;
import software.amazon.smithy.model.shapes.ShapeType;

/**
 * A shape producer that generates Java classes from Smithy structure shapes.
 *
 * <p>This producer converts Smithy structure shapes into Java classes with immutable
 * data representation and builder patterns. The generated classes include:
 * <ul>
 *   <li><strong>Immutable fields</strong> - Private final fields for all structure members</li>
 *   <li><strong>Constructor</strong> - Package-private constructor for builder initialization</li>
 *   <li><strong>Accessor methods</strong> - Public getter methods for all fields</li>
 *   <li><strong>Builder class</strong> - Nested static builder for fluent construction</li>
 *   <li><strong>Utility methods</strong> - equals(), hashCode(), toString() implementations</li>
 *   <li><strong>Factory methods</strong> - Static methods for common construction patterns</li>
 * </ul>
 *
 * <p>The producer handles various structure patterns:
 * <ul>
 *   <li><strong>Simple structures</strong> - With basic field types</li>
 *   <li><strong>Nested structures</strong> - With complex field types and references</li>
 *   <li><strong>Optional fields</strong> - With appropriate nullability handling</li>
 *   <li><strong>Documented structures</strong> - With javadoc from Smithy documentation</li>
 *   <li><strong>Sensitive structures</strong> - With security annotations and special handling</li>
 * </ul>
 *
 * <p>Example transformation:
 * <pre>{@code
 * // Smithy structure:
 * structure Person {
 *     @required
 *     name: String
 *     
 *     age: Integer
 *     
 *     @documentation("Email address")
 *     email: String
 * }
 *
 * // Generated Java class:
 * public final class Person {
 *     private final String name;
 *     private final Integer age;
 *     private final String email;
 *
 *     // Constructor, getters, builder, equals/hashCode/toString...
 * }
 * }</pre>
 *
 * <p>The producer skips shapes that have:
 * <ul>
 *   <li>The {@code @java} trait (custom Java implementation)</li>
 *   <li>The {@code @interface} trait (handled by StructureInterfaceJavaProducer)</li>
 * </ul>
 *
 * @see StructureData for the actual class generation logic
 * @see StructureInterfaceJavaProducer for interface generation
 * @see TypeSyntaxResult for the output format
 */
public final class StructureJavaProducer implements ShapeProducerTask<TypeSyntaxResult> {
    /**
     * The unique identifier for this producer task.
     */
    public static final Identifier ID = Identifier.of(StructureJavaProducer.class);

    /**
     * Creates a new StructureJavaProducer instance.
     */
    public StructureJavaProducer() {
    }

    /**
     * Returns the unique identifier for this producer task.
     *
     * @return the task identifier
     */
    @Override
    public Identifier taskId() {
        return ID;
    }

    /**
     * Returns the output type produced by this task.
     *
     * @return TypeSyntaxResult.class
     */
    @Override
    public Class<TypeSyntaxResult> output() {
        return TypeSyntaxResult.class;
    }

    /**
     * Returns the Smithy shape type that this producer handles.
     *
     * @return ShapeType.STRUCTURE
     */
    @Override
    public ShapeType type() {
        return ShapeType.STRUCTURE;
    }

    /**
     * Produces Java class code for the given Smithy structure shape.
     *
     * <p>This method generates a complete Java class declaration including:
     * <ul>
     *   <li>Immutable fields with appropriate types and nullability</li>
     *   <li>Package-private constructor for builder initialization</li>
     *   <li>Public accessor methods with proper naming</li>
     *   <li>Nested builder class with fluent API</li>
     *   <li>Standard object methods (equals, hashCode, toString)</li>
     *   <li>Factory methods for common construction patterns</li>
     *   <li>Documentation from Smithy traits</li>
     *   <li>Sensitivity annotations where applicable</li>
     * </ul>
     *
     * <p>This method returns null in the following cases:
     * <ul>
     *   <li>The shape has the {@code @java} trait (custom implementation)</li>
     *   <li>The shape has the {@code @interface} trait (handled by another producer)</li>
     * </ul>
     *
     * @param directive the shape generation context containing the structure shape and dependencies
     * @return the generated class syntax result, or null if the shape should be skipped
     */
    @Override
    public TypeSyntaxResult produce(ShapeCodegenState directive) {
        var shape = directive.shape();
        if (shape.hasTrait(JavaTrait.class)) {
            return null;
        }
        if (shape.hasTrait(InterfaceTrait.class)) {
            return null;
        }
        var syntax = StructureData.INSTANCE.buildCompilationUnit(directive);
        return TypeSyntaxResult.builder()
                               .syntax(syntax)
                               .build();
    }
}
