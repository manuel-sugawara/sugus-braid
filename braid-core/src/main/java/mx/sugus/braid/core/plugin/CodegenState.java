package mx.sugus.braid.core.plugin;

import java.util.Map;
import mx.sugus.braid.core.JavaCodegenSettings;
import mx.sugus.braid.core.JavaSymbolProvider;
import mx.sugus.braid.jsyntax.TypeName;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.ShapeId;

public interface CodegenState {
    Model model();

    JavaSymbolProvider symbolProvider();

    FileManifest fileManifest();

    JavaCodegenSettings settings();

    Map<Identifier, Object> properties();

    // XXX this one should not be here
    default TypeName toJavaTypeNameClass(String unparsedShapeId) {
        var shapeId = ShapeId.from(unparsedShapeId);
        var symbolProvider = symbolProvider();
        var shape = model().expectShape(shapeId);
        return symbolProvider.toJavaTypeName(shape);
    }
}
