package mx.sugus.braid.plugins.data;

import mx.sugus.braid.jsyntax.CompilationUnit;

/**
 * Result container for Java type syntax generation, combining the generated compilation unit
 * with its target namespace.
 *
 * <p>This class represents the output of Java code generation for a Smithy shape, containing:
 * <ul>
 *   <li><strong>Syntax</strong> - The generated Java compilation unit (class, interface, enum, etc.)</li>
 *   <li><strong>Namespace</strong> - The target Java package where the type should be placed</li>
 * </ul>
 *
 * <p>The result is used by the code generation pipeline to:
 * <ul>
 *   <li>Organize generated code into appropriate packages</li>
 *   <li>Resolve import statements and dependencies</li>
 *   <li>Write compilation units to the correct output directories</li>
 *   <li>Handle namespace conflicts and renaming</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * TypeSyntaxResult result = TypeSyntaxResult.builder()
 *     .syntax(generatedClass)
 *     .namespace("com.example.generated.models")
 *     .build();
 *
 * // Later in the pipeline:
 * String packageName = result.namespace();
 * CompilationUnit javaCode = result.syntax();
 * }</pre>
 *
 * <p>This class is immutable and uses the builder pattern for construction.
 * All instances should be created through the {@link #builder()} method.
 */
public final class TypeSyntaxResult {
    private final CompilationUnit syntax;
    private final String namespace;

    /**
     * Creates a new TypeSyntaxResult from the provided builder.
     *
     * @param builder the builder containing the syntax and namespace
     */
    TypeSyntaxResult(TypeSyntaxResult.Builder builder) {
        this.syntax = builder.syntax;
        this.namespace = builder.namespace;
    }

    /**
     * Returns the generated Java compilation unit.
     *
     * <p>The compilation unit contains the complete Java source code for the generated type,
     * including the class/interface/enum declaration, methods, fields, and inner types.
     *
     * @return the Java compilation unit containing the generated code
     */
    public CompilationUnit syntax() {
        return syntax;
    }

    /**
     * Returns the target namespace (Java package) for the generated type.
     *
     * <p>This namespace determines where the generated Java file will be placed
     * in the output directory structure and affects import resolution.
     *
     * @return the Java package name where this type should be generated
     */
    public String namespace() {
        return namespace;
    }

    /**
     * Creates a new builder initialized with the values from this result.
     *
     * <p>This method is useful for creating modified copies of existing results.
     *
     * @return a new builder with this result's values
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new builder for constructing TypeSyntaxResult instances.
     *
     * @return a new builder with default values
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing TypeSyntaxResult instances.
     *
     * <p>This builder follows the standard builder pattern, allowing for fluent
     * construction of immutable TypeSyntaxResult objects.
     */
    public static class Builder {
        private CompilationUnit syntax;
        private String namespace;

        /**
         * Creates a new builder with default values.
         */
        Builder() {
        }

        /**
         * Creates a new builder initialized with values from an existing result.
         *
         * @param result the result to copy values from
         */
        Builder(TypeSyntaxResult result) {
            this.syntax = result.syntax;
            this.namespace = result.namespace;
        }

        /**
         * Sets the Java compilation unit for the generated type.
         *
         * @param syntax the compilation unit containing the generated Java code
         * @return this builder for method chaining
         */
        public Builder syntax(CompilationUnit syntax) {
            this.syntax = syntax;
            return this;
        }

        /**
         * Sets the target namespace (Java package) for the generated type.
         *
         * @param namespace the Java package name where the type should be generated
         * @return this builder for method chaining
         */
        public Builder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        /**
         * Builds and returns a new TypeSyntaxResult instance.
         *
         * @return a new immutable TypeSyntaxResult with the configured values
         */
        public TypeSyntaxResult build() {
            return new TypeSyntaxResult(this);
        }
    }
}
