package mx.sugus.braid.core.plugin;

import java.util.Collection;
import java.util.Objects;
import mx.sugus.braid.core.BraidCodegenSettings;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.codegen.core.Symbol;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;


/**
 * Specialized codegen state that provides context for processing a specific Smithy shape.
 *
 * <p>This class extends the base {@link CodegenState} interface with shape-specific
 * information, making it the primary context object used by {@link ShapeProducerTask}s and {@link ShapeTaskTransformer}s when
 * generating code for individual shapes.
 *
 * <p>In addition to the common state elements (model, settings, file manifest, etc.),
 * this class provides direct access to:
 * <ul>
 *   <li>The specific {@link Shape} being processed</li>
 *   <li>The {@link Symbol} representation of that shape</li>
 * </ul>
 *
 * <p>The shape and its symbol are fundamental to most code generation tasks, as they
 * provide both the structural information needed to generate appropriate code and the
 * naming/typing information required for proper integration with the target language.
 *
 * <p>This class is immutable and thread-safe.
 *
 * @see CodegenState
 * @see NonShapeCodegenState
 */
public final class ShapeCodegenState implements CodegenState {
    private final Model model;
    private final Shape shape;
    private final Collection<Shape> selectedShapes;
    private final SymbolProvider symbolProvider;
    private final BraidCodegenSettings settings;
    private final FileManifest fileManifest;
    private final Dependencies dependencies;

    ShapeCodegenState(Builder builder) {
        this.model = Objects.requireNonNull(builder.model, "model");
        this.shape = Objects.requireNonNull(builder.shape, "shape");
        this.selectedShapes = Objects.requireNonNull(builder.selectedShapes, "selectedShapes");
        this.symbolProvider = Objects.requireNonNull(builder.symbolProvider, "symbolProvider");
        this.settings = Objects.requireNonNull(builder.settings, "settings");
        this.fileManifest = Objects.requireNonNull(builder.fileManifest, "fileManifest");
        this.dependencies = Objects.requireNonNull(builder.dependencies, "dependencies");
    }

    @Override
    public Model model() {
        return model;
    }

    @Override
    public SymbolProvider symbolProvider() {
        return symbolProvider;
    }

    @Override
    public FileManifest fileManifest() {
        return fileManifest;
    }

    @Override
    public BraidCodegenSettings settings() {
        return settings;
    }

    @Override
    public Dependencies dependencies() {
        return dependencies;
    }

    @Override
    public Collection<Shape> selectedShapes() {
        return selectedShapes;
    }

    /**
     * Returns the symbol representation of the shape being processed.
     *
     * <p>The symbol provides target language-specific information about the shape,
     * including its name, type, imports, and other metadata needed for code generation. This is a convenience method that
     * delegates to the symbol provider.
     *
     * @return The symbol representation of this state's shape
     */
    public Symbol symbol() {
        return symbolProvider.toSymbol(shape);
    }

    /**
     * Returns the Smithy shape being processed in this generation context.
     *
     * @return The shape being processed
     */
    public Shape shape() {
        return shape;
    }

    /**
     * Creates a new builder for constructing ShapeCodegenState instances.
     *
     * @return A new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Model model;
        private Shape shape;
        private Collection<Shape> selectedShapes;
        private SymbolProvider symbolProvider;
        private BraidCodegenSettings settings;
        private FileManifest fileManifest;
        private Dependencies dependencies;

        /**
         * Sets the Smithy model for this state.
         *
         * @param model The Smithy model containing all shapes
         * @return This builder for method chaining
         */
        public Builder model(Model model) {
            this.model = model;
            return this;
        }


        /**
         * Sets all the selected shapes to be processed.
         *
         * @param selectedShapes The Smithy shape to process
         * @return This builder for method chaining
         */
        public Builder selectedShapes(Collection<Shape> selectedShapes) {
            this.selectedShapes = selectedShapes;
            return this;
        }

        /**
         * Sets the specific shape to be processed.
         *
         * @param shape The Smithy shape to process
         * @return This builder for method chaining
         */
        public Builder shape(Shape shape) {
            this.shape = shape;
            return this;
        }

        /**
         * Sets the symbol provider for mapping shapes to target language symbols.
         *
         * @param symbolProvider The symbol provider to use
         * @return This builder for method chaining
         */
        public Builder symbolProvider(SymbolProvider symbolProvider) {
            this.symbolProvider = symbolProvider;
            return this;
        }

        /**
         * Sets the file manifest for writing generated files.
         *
         * @param fileManifest The file manifest to use for output
         * @return This builder for method chaining
         */
        public Builder fileManifest(FileManifest fileManifest) {
            this.fileManifest = fileManifest;
            return this;
        }

        /**
         * Sets the code generation settings.
         *
         * @param settings The Braid codegen settings
         * @return This builder for method chaining
         */
        public Builder settings(BraidCodegenSettings settings) {
            this.settings = settings;
            return this;
        }

        /**
         * Sets the dependency container for accessing shared dependencies.
         *
         * @param dependencies The dependencies container
         * @return This builder for method chaining
         */
        public Builder dependencies(Dependencies dependencies) {
            this.dependencies = dependencies;
            return this;
        }

        /**
         * Builds and returns a new ShapeCodegenState instance.
         *
         * @return A new ShapeCodegenState with the configured values
         * @throws NullPointerException if any required field is null
         */
        public ShapeCodegenState build() {
            return new ShapeCodegenState(this);
        }
    }
}
