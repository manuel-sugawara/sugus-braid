package mx.sugus.braid.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import mx.sugus.braid.core.plugin.CodegenModule;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.NonShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.traits.CodegenIgnoreTrait;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;

public class SmithyCodegenDirector {
    private static final Logger LOG = Logger.getLogger(SmithyCodegenDirector.class.getName());
    private final FileManifest fileManifest;
    private final JavaCodegenSettings settings;
    private final CodegenModule module;
    private final JavaSymbolProvider symbolProvider;
    private Model sourceModel;
    private Model model;

    SmithyCodegenDirector(Builder builder) {
        this.sourceModel = Objects.requireNonNull(builder.model, "model");
        this.fileManifest = Objects.requireNonNull(builder.fileManifest, "fileManifest");
        this.settings = Objects.requireNonNull(builder.settings, "settings");
        this.symbolProvider = Objects.requireNonNull(builder.symbolProvider, "symbolProvider");
        this.module = Objects.requireNonNull(builder.module, "module");
    }

    private Model prepareModel(Model model) {
        LOG.fine("Running module configured model early processors");
        var newModel = module.earlyPreprocessModel(model);
        LOG.fine("Running module configured model processors");
        newModel = module.preprocessModel(newModel);
        return newModel;
    }

    public void generate2() {

        execute();
    }

    public void execute() {
        LOG.fine("Begin model preparation for codegen");
        model = prepareModel(sourceModel);
        // Avoid attempts to run twice this.
        sourceModel = null;
        var sortedShapes = selectedShapes();
        var properties = new HashMap<Identifier, Object>();
        LOG.fine("Running shape reducers");
        for (var reducer : module.shapeReducers()) {
            var init = reducer.init();
            for (var shape : sortedShapes) {
                if (shape.hasTrait(CodegenIgnoreTrait.class)) {
                    continue;
                }
                var javaShapeState = stateForShape(shape);
                init.consume(javaShapeState);
            }
            properties.put(reducer.taskId(), init.finalizeJob());
        }
        LOG.fine("Beginning shape codegen");
        for (var shape : sortedShapes) {
            var javaShapeState = stateForShape(shape, properties);
            module.generateShape(javaShapeState);
        }
        LOG.fine("Beginning non-shape codegen");
        var nonShapeState = stateFor(properties);
        module.generateNonShape(nonShapeState);

    }

    private Collection<Shape> selectedShapes() {
        return module.select(model);
    }

    private ShapeCodegenState stateForShape(Shape shape) {
        var symbolProvider = new JavaSymbolProviderWrapper(this.symbolProvider);
        return ShapeCodegenState
            .builder()
            .model(model)
            .shape(shape)
            .symbolProvider(symbolProvider)
            .fileManifest(fileManifest)
            .settings(settings)
            .build();
    }

    private ShapeCodegenState stateForShape(Shape shape, Map<Identifier, Object> properties) {
        var symbolProvider = new JavaSymbolProviderWrapper(this.symbolProvider);
        return ShapeCodegenState
            .builder()
            .model(model)
            .shape(shape)
            .symbolProvider(symbolProvider)
            .fileManifest(fileManifest)
            .settings(settings)
            .properties(properties)
            .build();
    }

    private NonShapeCodegenState stateFor(Map<Identifier, Object> properties) {
        var symbolProvider = new JavaSymbolProviderWrapper(this.symbolProvider);
        return NonShapeCodegenState
            .builder()
            .model(model)
            .symbolProvider(symbolProvider)
            .fileManifest(fileManifest)
            .settings(settings)
            .properties(properties)
            .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Model model;
        private FileManifest fileManifest;
        private JavaCodegenSettings settings;
        private JavaSymbolProvider symbolProvider;
        private CodegenModule module;

        public Builder model(Model model) {
            this.model = model;
            return this;
        }

        public Builder fileManifest(FileManifest fileManifest) {
            this.fileManifest = fileManifest;
            return this;
        }

        public Builder settings(JavaCodegenSettings settings) {
            this.settings = settings;
            return this;
        }

        public Builder symbolProvider(JavaSymbolProvider symbolProvider) {
            this.symbolProvider = symbolProvider;
            return this;
        }

        public Builder module(CodegenModule module) {
            this.module = module;
            return this;
        }

        public SmithyCodegenDirector build() {
            Objects.requireNonNull(settings);
            Objects.requireNonNull(model);
            Objects.requireNonNull(fileManifest);
            if (symbolProvider == null) {
                symbolProvider = JavaSymbolProviderImpl.create(model, settings);
            }
            return new SmithyCodegenDirector(this);
        }
    }
}
