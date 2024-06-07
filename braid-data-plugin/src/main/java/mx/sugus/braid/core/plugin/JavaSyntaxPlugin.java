package mx.sugus.braid.core.plugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.writer.CodeRenderer;
import mx.sugus.braid.plugins.data.Utils;
import mx.sugus.braid.traits.JavaTrait;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.ShapeType;

/**
 * Provides serialization for instances of {@link TypeSyntaxResult}.
 * <p>
 * TODO: move onto its own plugin module.
 */
public final class JavaSyntaxPlugin implements SmithyGeneratorPlugin {
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
            var ns = state.settings().packageName();
            if (result.namespace() != null) {
                ns = ns + "." +
                     result.namespace();
            }

            @SuppressWarnings("unchecked")
            var packageImplicitNames =
                (Map<String, ClassName>) state.properties().get(PackageImplicitNamesReducer.ID);
            if (result.syntax() != null) {
                var file = ns.replace(".", "/") + "/" + result.syntax().name() + ".java";
                state.fileManifest()
                     .writeFile(file, CodeRenderer.render(ns, packageImplicitNames, result.syntax()));
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
