package mx.sugus.braid.core.plugin;

import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.core.JavaCodegenSettings;
import mx.sugus.braid.core.JavaSymbolProvider;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.model.Model;

/**
 * Contains all the data needed for a specific codegen task for a given shape.
 */
public final class NonShapeCodegenState implements CodegenState {
    private final Model model;
    private final JavaSymbolProvider symbolProvider;
    private final JavaCodegenSettings settings;
    private final FileManifest fileManifest;
    private final Map<Identifier, Object> properties;

    NonShapeCodegenState(Builder builder) {
        this.model = Objects.requireNonNull(builder.model, "model");
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
    public JavaSymbolProvider symbolProvider() {
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Model model;
        private JavaSymbolProvider symbolProvider;
        private JavaCodegenSettings settings;
        private FileManifest fileManifest;
        private Map<Identifier, Object> properties = Map.of();


        public Builder model(Model model) {
            this.model = model;
            return this;
        }

        public Builder symbolProvider(JavaSymbolProvider symbolProvider) {
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

        public NonShapeCodegenState build() {
            return new NonShapeCodegenState(this);
        }
    }
}
