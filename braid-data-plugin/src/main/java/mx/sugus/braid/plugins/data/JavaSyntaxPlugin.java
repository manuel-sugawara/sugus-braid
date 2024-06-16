package mx.sugus.braid.plugins.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.CodegenState;
import mx.sugus.braid.core.plugin.ConsumerTask;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeReducer;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.writer.CodeRenderer;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.traits.JavaTrait;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.ShapeType;

/**
 * Provides serialization for instances of {@link TypeSyntaxResult}.
 */
public final class JavaSyntaxPlugin implements SmithyGeneratorPlugin<ObjectNode> {
    public static final Identifier ID = Identifier.of(JavaSyntaxPlugin.class);
    private static final TypeSyntaxResultSerializer SERIALIZER = new TypeSyntaxResultSerializer();
    private static final PackageImplicitNamesReducer PACKAGE_IMPLICIT_NAMES_REDUCER = new PackageImplicitNamesReducer();

    public JavaSyntaxPlugin() {
    }

    @Override
    public Identifier provides() {
        return ID;
    }

    @Override
    public ObjectNode fromNode(ObjectNode node) {
        return node;
    }

    @Override
    public CodegenModuleConfig moduleConfig(ObjectNode node) {
        return newBaseConfig();
    }

    static CodegenModuleConfig newBaseConfig() {
        return CodegenModuleConfig
            .builder()
            .addConsumer(SERIALIZER)
            .addShapeReducer(PACKAGE_IMPLICIT_NAMES_REDUCER)
            .build();
    }

    static class TypeSyntaxResultSerializer implements ConsumerTask<TypeSyntaxResult> {
        static final Identifier ID = Identifier.of(TypeSyntaxResultSerializer.class);

        @Override
        public Identifier taskId() {
            return ID;
        }

        @Override
        public Class<TypeSyntaxResult> input() {
            return TypeSyntaxResult.class;
        }

        @Override
        public void consume(TypeSyntaxResult result, CodegenState state) {
            @SuppressWarnings("unchecked")
            var packageImplicitNames =
                (Map<String, ClassName>) state.properties().get(PackageImplicitNamesReducer.ID);
            var syntax = result.syntax();
            if (syntax != null) {
                var file = syntax.packageName().replace(".", "/") + "/" + result.syntax().type().name() + ".java";
                state.fileManifest()
                     .writeFile(file, CodeRenderer.render(syntax.packageName(), packageImplicitNames, result.syntax()));
            }
        }
    }

    static class PackageImplicitNamesReducer implements ShapeReducer<Map<String, ClassName>> {
        static Identifier ID = Identifier.of(PackageImplicitNamesReducer.class);

        @Override
        public Identifier taskId() {
            return ID;
        }

        @Override
        public ReducerState<Map<String, ClassName>> init() {
            return new PackageImplicitNamesReducerState();
        }
    }

    static class PackageImplicitNamesReducerState implements ShapeReducer.ReducerState<Map<String, ClassName>> {
        private final Map<String, ClassName> packageImports = new HashMap<>();

        @Override
        public void consume(ShapeCodegenState state) {
            var shape = state.shape();
            var type = shape.getType();
            if (type != ShapeType.ENUM
                && type != ShapeType.INT_ENUM
                && type != ShapeType.STRUCTURE
                && type != ShapeType.UNION) {
                return;
            }
            if (shape.hasTrait(JavaTrait.class)) {
                return;
            }

            var typeName = Utils.toJavaTypeName(state, state.shape());
            var className = ClassName.toClassName(typeName);
            packageImports.put(className.name(), className);
        }

        @Override
        public Map<String, ClassName> finalizeJob() {
            return Collections.unmodifiableMap(packageImports);
        }
    }
}
