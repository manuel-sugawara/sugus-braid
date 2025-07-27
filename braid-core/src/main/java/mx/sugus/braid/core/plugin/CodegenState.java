package mx.sugus.braid.core.plugin;

import java.util.Collection;
import java.util.Map;
import mx.sugus.braid.core.BraidCodegenSettings;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;

/**
 * Encapsulates the shared state and context information needed during code generation.
 *
 * <p>This interface provides access to all the essential components required by
 * producers, transformers, and consumers during the code generation process. It serves as a data container that flows through the
 * entire pipeline, ensuring consistent access to the Smithy model, configuration, and output mechanisms.
 *
 * <p>The state includes:
 * <ul>
 *   <li><strong>Model:</strong> The processed Smithy model containing all shapes</li>
 *   <li><strong>Symbol Provider:</strong> For mapping Smithy shapes to target language symbols</li>
 *   <li><strong>File Manifest:</strong> For writing generated files to the output directory</li>
 *   <li><strong>Settings:</strong> Configuration parameters from the build settings</li>
 *   <li><strong>Dependencies:</strong> Keyed access to shared dependencies and utilities</li>
 * </ul>
 *
 * @see ShapeCodegenState
 * @see NonShapeCodegenState
 */
public interface CodegenState {
    Model model();

    Collection<Shape> selectedShapes();

    Map<Identifier, Object> reducersResults();

    SymbolProvider symbolProvider();

    FileManifest fileManifest();

    BraidCodegenSettings settings();

    Dependencies dependencies();
}
