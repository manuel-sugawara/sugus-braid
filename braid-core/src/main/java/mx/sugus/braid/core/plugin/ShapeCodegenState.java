package mx.sugus.braid.core.plugin;

import java.util.Objects;
import mx.sugus.braid.core.BrideCodegenSettings;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.codegen.core.Symbol;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;


/**
 * Contains all the data needed for a specific codegen task for a given shape.
 */
public final class ShapeCodegenState implements CodegenState {
    private final Model model;
    private final Shape shape;
    private final SymbolProvider symbolProvider;
    private final BrideCodegenSettings settings;
    private final FileManifest fileManifest;
    private final Dependencies dependencies;

    ShapeCodegenState(Builder builder) {
        this.model = Objects.requireNonNull(builder.model, "model");
        this.shape = Objects.requireNonNull(builder.shape, "shape");
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
    public BrideCodegenSettings settings() {
        return settings;
    }

    @Override
    public Dependencies dependencies() {
        return dependencies;
    }

    public Symbol symbol() {
        return symbolProvider.toSymbol(shape);
    }

    public Shape shape() {
        return shape;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Model model;
        private Shape shape;
        private SymbolProvider symbolProvider;
        private BrideCodegenSettings settings;
        private FileManifest fileManifest;
        private Dependencies dependencies;

        public Builder model(Model model) {
            this.model = model;
            return this;
        }

        public Builder shape(Shape shape) {
            this.shape = shape;
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

        public Builder settings(BrideCodegenSettings settings) {
            this.settings = settings;
            return this;
        }

        public Builder dependencies(Dependencies dependencies) {
            this.dependencies = dependencies;
            return this;
        }

        public ShapeCodegenState build() {
            return new ShapeCodegenState(this);
        }
    }
}
