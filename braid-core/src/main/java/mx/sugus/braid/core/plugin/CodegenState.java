package mx.sugus.braid.core.plugin;

import mx.sugus.braid.core.BraidCodegenSettings;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;

public interface CodegenState {
    Model model();

    SymbolProvider symbolProvider();

    FileManifest fileManifest();

    BraidCodegenSettings settings();

    Dependencies dependencies();
}
