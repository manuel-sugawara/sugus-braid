package mx.sugus.braid.core.plugin;

import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.core.JavaCodegenSettings;
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
    private final JavaCodegenSettings settings;
    private final FileManifest fileManifest;
    private final Map<Identifier, Object> properties;

    ShapeCodegenState(Builder builder) {
        this.model = Objects.requireNonNull(builder.model, "model");
        this.shape = Objects.requireNonNull(builder.shape, "shape");
        this.symbolProvider = Objects.requireNonNull(builder.symbolProvider, "symbolProvider");
        this.settings = Objects.requireNonNull(builder.settings, "settings");
        this.fileManifest = Objects.requireNonNull(builder.fileManifest, "fileManifest");
        this.properties = Objects.requireNonNull(builder.properties, "properties");
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
    public JavaCodegenSettings settings() {
        return settings;
    }

    @Override
    public Map<Identifier, Object> properties() {
        return properties;
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
        private JavaCodegenSettings settings;
        private FileManifest fileManifest;
        private Map<Identifier, Object> properties = Map.of();


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

        public Builder settings(JavaCodegenSettings settings) {
            this.settings = settings;
            return this;
        }

        public Builder properties(Map<Identifier, Object> properties) {
            this.properties = Map.copyOf(properties);
            return this;
        }

        public ShapeCodegenState build() {
            return new ShapeCodegenState(this);
        }
    }
}
