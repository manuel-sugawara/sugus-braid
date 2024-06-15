package mx.sugus.braid.core.plugin;

import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.core.BrideCodegenSettings;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;

/**
 * Contains all the data needed for a specific codegen task for a given shape.
 */
public final class NonShapeCodegenState implements CodegenState {
    private final Model model;
    private final SymbolProvider symbolProvider;
    private final BrideCodegenSettings settings;
    private final FileManifest fileManifest;
    private final Map<Identifier, Object> properties;
    private final Dependencies dependencies;

    NonShapeCodegenState(Builder builder) {
        this.model = Objects.requireNonNull(builder.model, "model");
        this.symbolProvider = Objects.requireNonNull(builder.symbolProvider, "symbolProvider");
        this.settings = Objects.requireNonNull(builder.settings, "settings");
        this.fileManifest = Objects.requireNonNull(builder.fileManifest, "fileManifest");
        this.properties = Objects.requireNonNull(builder.properties, "properties");
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
    public Map<Identifier, Object> properties() {
        return properties;
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
        private SymbolProvider symbolProvider;
        private BrideCodegenSettings settings;
        private FileManifest fileManifest;
        private Map<Identifier, Object> properties = Map.of();
        private Dependencies dependencies;

        public Builder model(Model model) {
            this.model = model;
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

        public Builder properties(Map<Identifier, Object> properties) {
            this.properties = Map.copyOf(properties);
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
