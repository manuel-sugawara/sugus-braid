package mx.sugus.braid.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.logging.Logger;
import mx.sugus.braid.core.plugin.CodegenModule;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.NonShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;

/**
 * The Braid code generation director orchestrates the entire code generation process.
 *
 * <p>The director manages the complete workflow for transforming Smithy models into Java code:
 * <ol>
 *   <li><strong>Model Preparation:</strong> Applies early and standard model transformations</li>
 *   <li><strong>Symbol Provider Setup:</strong> Creates and decorates symbol providers for type mapping</li>
 *   <li><strong>Shape Selection:</strong> Determines which shapes to process for code generation</li>
 *   <li><strong>Shape Code Generation:</strong> Processes each selected shape through the pipeline</li>
 *   <li><strong>Non-Shape Code Generation:</strong> Generates additional artifacts not tied to specific shapes</li>
 * </ol>
 *
 * <p>The director is immutable after construction and is built using the {@link Builder} pattern.
 * All model transformations and symbol provider decorations are applied during the build phase,
 * ensuring the director is ready for immediate execution.
 *
 * <p>Thread Safety: This class is thread-safe as it is immutable after construction.
 */
public final class BraidCodegenDirector {
    private static final Logger LOG = Logger.getLogger(BraidCodegenDirector.class.getName());
    private final FileManifest fileManifest;
    private final BraidCodegenSettings settings;
    private final CodegenModule module;
    private final SymbolProvider symbolProvider;
    private final Model model;

    BraidCodegenDirector(Builder builder) {
        this.model = Objects.requireNonNull(builder.model, "model");
        this.fileManifest = Objects.requireNonNull(builder.fileManifest, "fileManifest");
        this.settings = Objects.requireNonNull(builder.settings, "settings");
        this.symbolProvider = Objects.requireNonNull(builder.symbolProvider, "symbolProvider");
        this.module = Objects.requireNonNull(builder.module, "module");
    }

    /**
     * Executes the complete code generation process.
     *
     * <p>This method orchestrates the generation workflow by:
     * <ol>
     *   <li>Selecting shapes to process using the configured shape selector</li>
     *   <li>Generating code for each selected shape through the producer-transformer-consumer pipeline</li>
     *   <li>Generating additional non-shape artifacts</li>
     * </ol>
     *
     * <p>All model transformations and symbol provider decorations have already been applied
     * during the director's construction, so this method focuses solely on code generation.
     */
    public void execute() {
        try {
            var selectedShapes = selectedShapes();
            var reducers = module.shapeReducers();
            LOG.fine(() -> "Beginning running reducers");
            Map<Identifier, Object> reducersResults = Map.of();
            if (!reducers.isEmpty()) {
                var results = new HashMap<Identifier, Object>(reducers.size());
                for (var reducer : module.shapeReducers()) {
                    var state = reducer.init();
                    for (var shape : selectedShapes) {
                        var javaShapeState = stateForShape(selectedShapes, reducersResults, shape);
                        state.consume(javaShapeState);
                    }
                    results.put(reducer.taskId(), state.finalizeJob());
                }
                reducersResults = Map.copyOf(results);
            }
            LOG.fine(() -> String.format("Beginning shape codegen for %d shapes", selectedShapes.size()));
            for (var shape : selectedShapes) {
                var javaShapeState = stateForShape(selectedShapes, reducersResults, shape);
                module.generateShape(javaShapeState);
            }
            LOG.fine("Beginning non-shape codegen");
            var nonShapeState = stateFor(selectedShapes, reducersResults);
            module.generateNonShape(nonShapeState);
            LOG.fine("Code generation completed successfully");
        } catch (Exception e) {
            LOG.severe(() -> String.format("Code generation failed: %s", e.getMessage()));
            throw new RuntimeException("Code generation failed", e);
        }
    }

    private Collection<Shape> selectedShapes() {
        return module.select(model);
    }

    private ShapeCodegenState stateForShape(
        Collection<Shape> selectedShapes,
        Map<Identifier, Object> reducersResults,
        Shape shape
    ) {
        return ShapeCodegenState
            .builder()
            .model(model)
            .selectedShapes(selectedShapes)
            .shape(shape)
            .symbolProvider(symbolProvider)
            .fileManifest(fileManifest)
            .settings(settings)
            .dependencies(module.dependencies())
            .build();
    }

    private NonShapeCodegenState stateFor(Collection<Shape> selectedShapes, Map<Identifier, Object> reducersResults) {
        return NonShapeCodegenState
            .builder()
            .selectedShapes(selectedShapes)
            .model(model)
            .symbolProvider(symbolProvider)
            .fileManifest(fileManifest)
            .settings(settings)
            .dependencies(module.dependencies())
            .build();
    }

    /**
     * Creates a new builder for constructing a {@link BraidCodegenDirector}.
     *
     * @return A new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Model model;
        private FileManifest fileManifest;
        private BraidCodegenSettings settings;
        private SymbolProvider symbolProvider;
        private BiFunction<Model, BraidCodegenSettings, SymbolProvider> symbolProviderFactory;
        private CodegenModule module;

        public Builder model(Model model) {
            this.model = Objects.requireNonNull(model, "model");
            return this;
        }

        public Builder fileManifest(FileManifest fileManifest) {
            this.fileManifest = Objects.requireNonNull(fileManifest, "fileManifest");
            return this;
        }

        public Builder settings(BraidCodegenSettings settings) {
            this.settings = Objects.requireNonNull(settings, "settings");
            return this;
        }

        public Builder symbolProvider(SymbolProvider symbolProvider) {
            this.symbolProvider = Objects.requireNonNull(symbolProvider, "symbolProvider");
            return this;
        }

        public Builder module(CodegenModule module) {
            this.module = Objects.requireNonNull(module, "module");
            return this;
        }

        public Builder symbolProviderFactory(BiFunction<Model, BraidCodegenSettings, SymbolProvider> symbolProviderFactory) {
            this.symbolProviderFactory = Objects.requireNonNull(symbolProviderFactory, "symbolProviderFactory");
            return this;
        }

        public BraidCodegenDirector build() {
            Objects.requireNonNull(settings, "settings");
            Objects.requireNonNull(model, "model");
            Objects.requireNonNull(module, "module");
            Objects.requireNonNull(fileManifest, "fileManifest");

            // Validate symbol provider configuration
            if (symbolProvider != null && symbolProviderFactory != null) {
                throw new IllegalStateException("Cannot specify both symbolProvider and symbolProviderFactory");
            }
            if (symbolProvider == null && symbolProviderFactory == null) {
                throw new IllegalStateException("Must specify either symbolProvider or symbolProviderFactory");
            }

            // We prepare here such that afterward the director can be fully
            // immutable.
            prepare();
            return new BraidCodegenDirector(this);
        }

        private void prepare() {
            try {
                LOG.fine("Running module configured model early processors");
                var newModel = module.earlyPreprocessModel(model);
                LOG.fine("Running module configured model processors");
                newModel = module.preprocessModel(newModel);
                this.model = newModel;

                LOG.fine("Running symbol provider decorators");
                SymbolProvider sourceSymbolProvider;
                if (symbolProviderFactory != null) {
                    sourceSymbolProvider = symbolProviderFactory.apply(model, settings);
                } else {
                    sourceSymbolProvider = symbolProvider;
                }
                // For small models using the cache does not seem to add any measurable value.
                this.symbolProvider = SymbolProvider.cache(module.decorateSymbolProvider(this.model, sourceSymbolProvider));
                LOG.fine("Director preparation completed successfully");
            } catch (Exception e) {
                LOG.severe(() -> String.format("Failed to prepare code generation director: %s", e.getMessage()));
                throw new RuntimeException("Failed to prepare code generation director", e);
            }
        }
    }
}
