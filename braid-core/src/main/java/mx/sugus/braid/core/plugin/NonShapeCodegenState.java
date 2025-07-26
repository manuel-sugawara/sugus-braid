package mx.sugus.braid.core.plugin;

import java.util.Collection;
import java.util.Objects;
import mx.sugus.braid.core.BraidCodegenSettings;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;

/**
 * Contains all the data needed for a specific codegen task for a given shape.
 */
public final class NonShapeCodegenState implements CodegenState {
    private final Model model;
    private final Collection<Shape> selectedShapes;
    private final SymbolProvider symbolProvider;
    private final BraidCodegenSettings settings;
    private final FileManifest fileManifest;
    private final Dependencies dependencies;

    NonShapeCodegenState(Builder builder) {
        this.model = Objects.requireNonNull(builder.model, "model");
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
    public Collection<Shape> selectedShapes() {
        return selectedShapes;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Model model;
        private Collection<Shape> selectedShapes;
        private SymbolProvider symbolProvider;
        private BraidCodegenSettings settings;
        private FileManifest fileManifest;
        private Dependencies dependencies;

        public Builder model(Model model) {
            this.model = model;
            return this;
        }

        public Builder selectedShapes(Collection<Shape> selectedShapes) {
            this.selectedShapes = selectedShapes;
            return this;
        }

        public Builder symbolProvider(SymbolProvider symbolProvider) {
            this.symbolProvider = symbolProvider;
            return this;
        }

        public Builder fileManifest(FileManifest fileManifest) {
            this.fileManifest = fileManifest;
            return this;
        }

        public Builder settings(BraidCodegenSettings settings) {
            this.settings = settings;
            return this;
        }

        public Builder dependencies(Dependencies dependencies) {
            this.dependencies = dependencies;
            return this;
        }

        public NonShapeCodegenState build() {
            return new NonShapeCodegenState(this);
        }
    }
}
