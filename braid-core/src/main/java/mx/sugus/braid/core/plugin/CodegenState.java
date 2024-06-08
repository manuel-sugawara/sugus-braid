package mx.sugus.braid.core.plugin;

import java.util.Map;
import mx.sugus.braid.core.JavaCodegenSettings;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;

public interface CodegenState {
    Model model();

    SymbolProvider symbolProvider();

    FileManifest fileManifest();

    JavaCodegenSettings settings();

    Map<Identifier, Object> properties();

}
