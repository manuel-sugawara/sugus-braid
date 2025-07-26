package mx.sugus.braid.plugins.data.producers;

import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeProducerTask;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import software.amazon.smithy.model.shapes.ShapeType;

/**
 * A shape producer that generates Java sealed interfaces and variant classes from Smithy union shapes.
 *
 * <p>This producer converts Smithy union shapes into Java sealed interface hierarchies with
 * variant implementations. The generated code follows modern Java sealed class patterns
 * and includes:
 * <ul>
 *   <li><strong>Variant classes</strong> - One implementation per union member</li>
 *   <li><strong>Tag enumeration</strong> - Runtime type identification</li>
 *   <li><strong>Utility methods</strong> - Common operations and conversions</li>
 *   <li><strong>Builder class</strong>   - Nested static builder for fluent construction</li>
 * </ul>
 *
 * <p>Union patterns supported:
 * <ul>
 *   <li><strong>Tagged unions</strong> - Each variant has a distinct type and value</li>
 *   <li><strong>Simple unions</strong> - Variants with primitive or basic types</li>
 *   <li><strong>Complex unions</strong> - Variants with structure or collection types</li>
 *   <li><strong>Nested unions</strong> - Unions containing other union types</li>
 *   <li><strong>Documented unions</strong> - With javadoc from Smithy documentation</li>
 *   <li><strong>Sensitive unions</strong> - With appropriate security handling</li>
 * </ul>
 *
 *
 * @see UnionData for the actual union generation logic
 * @see TypeSyntaxResult for the output format
 */
public class UnionJavaProducer implements ShapeProducerTask<TypeSyntaxResult> {
    /**
     * The unique identifier for this producer task.
     */
    public static final Identifier ID = Identifier.of(UnionJavaProducer.class);

    /**
     * Creates a new UnionJavaProducer instance.
     */
    public UnionJavaProducer() {
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
     * @return ShapeType.UNION
     */
    @Override
    public ShapeType type() {
        return ShapeType.UNION;
    }

    /**
     * Produces Java sealed interface code for the given Smithy union shape.
     *
     * <p>This method generates a complete Java sealed interface hierarchy including:
     * <ul>
     *   <li>Base sealed interface with proper permits clause</li>
     *   <li>Variant record classes for each union member</li>
     *   <li>Static factory methods for variant construction</li>
     *   <li>Visitor interface and accept method for pattern matching</li>
     *   <li>Tag enumeration for runtime type identification</li>
     *   <li>Standard object methods and toString implementations</li>
     *   <li>Documentation from Smithy traits</li>
     *   <li>Sensitivity annotations where applicable</li>
     * </ul>
     *
     * <p>The generated sealed interface provides type-safe access to union variants
     * while supporting modern Java pattern matching and maintaining serialization
     * compatibility.
     *
     * @param directive the shape generation context containing the union shape and dependencies
     * @return the generated sealed interface syntax result
     */
    @Override
    public TypeSyntaxResult produce(ShapeCodegenState directive) {
        var syntax = new UnionData().buildCompilationUnit(directive);
        return TypeSyntaxResult.builder()
                               .syntax(syntax)
                               .build();
    }
}
