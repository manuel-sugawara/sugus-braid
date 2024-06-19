package mx.sugus.braid.core;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.logging.Logger;
import mx.sugus.braid.core.plugin.CodegenModule;
import mx.sugus.braid.core.plugin.NonShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;

public final class BraidCodegenDirector {
    private static final Logger LOG = Logger.getLogger(BraidCodegenDirector.class.getName());
    private final FileManifest fileManifest;
    private final BrideCodegenSettings settings;
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

    public void execute() {
        var sortedShapes = selectedShapes();
        LOG.fine("Beginning shape codegen");
        for (var shape : sortedShapes) {
            var javaShapeState = stateForShape(shape);
            module.generateShape(javaShapeState);
        }
        LOG.fine("Beginning non-shape codegen");
        var nonShapeState = stateFor();
        module.generateNonShape(nonShapeState);
    }

    private Collection<Shape> selectedShapes() {
        return module.select(model);
    }

    private ShapeCodegenState stateForShape(Shape shape) {
        return ShapeCodegenState
            .builder()
            .model(model)
            .shape(shape)
            .symbolProvider(symbolProvider)
            .fileManifest(fileManifest)
            .settings(settings)
            .dependencies(module.dependencies())
            .build();
    }

    private NonShapeCodegenState stateFor() {
        return NonShapeCodegenState
            .builder()
            .model(model)
            .symbolProvider(symbolProvider)
            .fileManifest(fileManifest)
            .settings(settings)
            .dependencies(module.dependencies())
            .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Model model;
        private FileManifest fileManifest;
        private BrideCodegenSettings settings;
        private SymbolProvider symbolProvider;
        private BiFunction<Model, BrideCodegenSettings, SymbolProvider> symbolProviderFactory;
        private CodegenModule module;

        public Builder model(Model model) {
            this.model = model;
            return this;
        }

        public Builder fileManifest(FileManifest fileManifest) {
            this.fileManifest = fileManifest;
            return this;
        }

        public Builder settings(BrideCodegenSettings settings) {
            this.settings = settings;
            return this;
        }

        public Builder symbolProvider(SymbolProvider symbolProvider) {
            this.symbolProvider = symbolProvider;
            return this;
        }

        public Builder module(CodegenModule module) {
            this.module = module;
            return this;
        }

        public Builder symbolProviderFactory(BiFunction<Model, BrideCodegenSettings, SymbolProvider> symbolProviderFactory) {
            this.symbolProviderFactory = symbolProviderFactory;
            return this;
        }

        public BraidCodegenDirector build() {
            Objects.requireNonNull(settings, "settings");
            Objects.requireNonNull(model, "model");
            Objects.requireNonNull(module, "module");
            Objects.requireNonNull(fileManifest, "fileManifest");
            Objects.requireNonNull(symbolProviderFactory, "symbolProviderFactory");
            // We prepare here such that afterward the director can be fully
            // immutable.
            prepare();
            return new BraidCodegenDirector(this);
        }

        private void prepare() {
            LOG.fine("Running module configured model early processors");
            var newModel = module.earlyPreprocessModel(model);
            LOG.fine("Running module configured model processors");
            newModel = module.preprocessModel(newModel);
            this.model = newModel;
            //this.module.
            LOG.fine("Running symbol provider decorators");
            var sourceSymbolProvider = symbolProviderFactory.apply(model, settings);
            // For small models using the cache does not seem to add any measurable value.
            this.symbolProvider = SymbolProvider.cache(module.decorateSymbolProvider(this.model, sourceSymbolProvider));
        }
    }
}
