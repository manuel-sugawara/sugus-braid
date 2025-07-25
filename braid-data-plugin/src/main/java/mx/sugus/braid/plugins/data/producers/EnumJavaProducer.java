package mx.sugus.braid.plugins.data.producers;

import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeProducerTask;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.traits.JavaTrait;
import software.amazon.smithy.model.shapes.ShapeType;

/**
 * A shape producer that generates Java enum classes from Smithy enum shapes.
 *
 * <p>This producer converts Smithy enum shapes into Java enum declarations with proper
 * value mappings, documentation, and metadata. The generated enums include:
 * <ul>
 *   <li><strong>Enum constants</strong> - One for each Smithy enum member</li>
 *   <li><strong>Value mapping</strong> - String values associated with each constant</li>
 *   <li><strong>Utility methods</strong> - For converting between strings and enum constants</li>
 *   <li><strong>Documentation</strong> - Javadoc comments from Smithy documentation traits</li>
 *   <li><strong>Metadata</strong> - Generated annotations and sensitivity handling</li>
 * </ul>
 *
 * <p>The producer handles various Smithy enum patterns:
 * <ul>
 *   <li><strong>Simple enums</strong> - Constants without explicit values</li>
 *   <li><strong>Value enums</strong> - Constants with custom string values</li>
 *   <li><strong>Documented enums</strong> - With member-level documentation</li>
 *   <li><strong>Sensitive enums</strong> - With appropriate security annotations</li>
 * </ul>
 *
 * <p>Example transformation:
 * <pre>{@code
 * // Smithy enum:
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
 *     // ... constructor and utility methods
 * }
 * }</pre>
 *
 * <p>The producer skips shapes that have the {@code @java} trait, allowing for
 * custom Java implementations to be used instead of generated code.
 *
 * @see EnumData for the actual enum generation logic
 * @see TypeSyntaxResult for the output format
 */
public final class EnumJavaProducer implements ShapeProducerTask<TypeSyntaxResult> {
    /**
     * The unique identifier for this producer task.
     */
    public static final Identifier ID = Identifier.of(EnumJavaProducer.class);

    /**
     * Creates a new EnumJavaProducer instance.
     */
    public EnumJavaProducer() {
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
     * @return ShapeType.ENUM
     */
    @Override
    public ShapeType type() {
        return ShapeType.ENUM;
    }

    /**
     * Produces Java enum code for the given Smithy enum shape.
     *
     * <p>This method generates a complete Java enum declaration including:
     * <ul>
     *   <li>Enum constants with proper naming and values</li>
     *   <li>Constructor and value field for string mapping</li>
     *   <li>Utility methods for string conversion</li>
     *   <li>Documentation from Smithy traits</li>
     *   <li>Sensitivity annotations if applicable</li>
     * </ul>
     *
     * <p>If the shape has the {@code @java} trait, this method returns null
     * to indicate that custom Java code should be used instead.
     *
     * @param directive the shape generation context containing the enum shape and dependencies
     * @return the generated enum syntax result, or null if the shape has custom Java code
     */
    @Override
    public TypeSyntaxResult produce(ShapeCodegenState directive) {
        var shape = directive.shape();
        if (shape.hasTrait(JavaTrait.class)) {
            return null;
        }
        var spec = new EnumData().buildCompilationUnit(directive);
        return TypeSyntaxResult.builder()
                               .syntax(spec)
                               .build();
    }
}
