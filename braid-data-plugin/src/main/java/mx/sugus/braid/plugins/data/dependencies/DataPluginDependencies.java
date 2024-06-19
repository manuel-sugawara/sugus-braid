package mx.sugus.braid.plugins.data.dependencies;

import java.util.Objects;
import java.util.function.Function;
import mx.sugus.braid.core.BraidCodegenPlugin;
import mx.sugus.braid.core.plugin.Dependencies;
import mx.sugus.braid.core.plugin.DependencyKey;
import mx.sugus.braid.core.util.Lazy;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.plugins.data.config.DataPluginConfig;
import mx.sugus.braid.plugins.data.config.NullabilityCheckMode;
import software.amazon.smithy.codegen.core.ReservedWords;
import software.amazon.smithy.codegen.core.ReservedWordsBuilder;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.knowledge.NullableIndex;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

/**
 * Defines dependencies for data plugin.
 */
public final class DataPluginDependencies {
    public static final Lazy<ReservedWords> LAZY_RESERVED_WORDS = new Lazy<>(DataPluginDependencies::buildReservedWords);

    /**
     * The reserved words to identify which shape names should be changed to avoid conflicts.
     */
    public static final DependencyKey<ReservedWords> RESERVED_WORDS =
        DependencyKey.from("reserved-words", x -> LAZY_RESERVED_WORDS.get());
    /**
     * The class to escape reserved words.
     */
    public static final DependencyKey<ReservedWordsEscaper> RESERVED_WORDS_ESCAPER =
        DependencyKey.from("java-name->escaped-java-name", DataPluginDependencies::defaultReservedWordsEscaper);
    /**
     * The config instance for this plugin
     */
    public static final DependencyKey<DataPluginConfig> DATA_PLUGIN_CONFIG = DependencyKey.from("data-plugin-config");
    /**
     * The class to convert shapes to java names.
     */
    public static final DependencyKey<ShapeToJavaName> SHAPE_TO_JAVA_NAME =
        DependencyKey.from("shape->java-name", DataPluginDependencies::defaultShapeToJavaName);
    /**
     * The class to create nullability indexes.
     */
    public static final DependencyKey<NullabilityIndexProvider> NULLABILITY_INDEX_PROVIDER =
        DependencyKey.from("model->nullability-index", DataPluginDependencies::defaultNullabilityIndexProvider);

    static ShapeToJavaName defaultShapeToJavaName(Dependencies dependencies) {
        var packageName = dependencies.getOptional(DATA_PLUGIN_CONFIG)
                                      .map(DataPluginConfig::packageName)
                                      .orElse(null);
        var escaper = dependencies.expect(RESERVED_WORDS_ESCAPER);
        return new DefaultShapeToJavaName(packageName, escaper);
    }

    static ReservedWordsEscaper defaultReservedWordsEscaper(Dependencies dependencies) {
        var reservedWords = dependencies.expect(RESERVED_WORDS);
        return new DefaultReservedWordsEscaper(reservedWords);
    }

    static NullabilityIndexProvider defaultNullabilityIndexProvider(Dependencies dependencies) {
        var config = dependencies.expect(DATA_PLUGIN_CONFIG);
        if (config.nullabilityMode() == NullabilityCheckMode.ALL_OPTIONAL) {
            return model -> shape -> true;
        }
        var checkMode = checkModeFrom(config.nullabilityMode());
        return new DefaultNullabilityIndexProvider(checkMode);
    }

    static ReservedWords buildReservedWords() {
        return
            new ReservedWordsBuilder()
                .loadWords(Objects.requireNonNull(BraidCodegenPlugin.class.getResource("java-reserved-words.txt")),
                           Function.identity())
                .loadWords(Objects.requireNonNull(BraidCodegenPlugin.class.getResource("java-system-type-names.txt")),
                           Function.identity())
                .build();
    }

    private static NullableIndex.CheckMode checkModeFrom(NullabilityCheckMode mode) {
        return switch (mode) {
            case CLIENT -> NullableIndex.CheckMode.CLIENT;
            case CLIENT_CAREFUL -> NullableIndex.CheckMode.CLIENT_CAREFUL;
            case SERVER -> NullableIndex.CheckMode.SERVER;
            default -> throw new IllegalArgumentException("unsupported mode: " + mode);
        };
    }

    static class DefaultReservedWordsEscaper implements ReservedWordsEscaper {
        private final ReservedWords reservedWords;

        DefaultReservedWordsEscaper(ReservedWords reservedWords) {
            this.reservedWords = reservedWords;
        }

        @Override
        public Name escape(Name name, Shape shape) {
            if (reservedWords.isReserved(name.toString())) {
                var type = shape.getType();
                if (type == ShapeType.MEMBER ||
                    (type.getCategory() != ShapeType.Category.AGGREGATE && type.getCategory() != ShapeType.Category.SERVICE)) {
                    name = name.prefixWithArticle();
                } else {
                    name = name.withSuffix(type.name());
                }
            }
            return name;
        }
    }

    static class DefaultNullabilityIndexProvider implements NullabilityIndexProvider {
        private final NullableIndex.CheckMode checkMode;

        DefaultNullabilityIndexProvider(NullableIndex.CheckMode checkMode) {
            this.checkMode = Objects.requireNonNull(checkMode, "checkMode");
        }

        @Override
        public NullabilityIndex of(Model model) {
            return new DefaultNullabilityIndex(checkMode, model);
        }
    }

    static class DefaultNullabilityIndex implements NullabilityIndex {
        private final NullableIndex.CheckMode checkMode;
        private final NullableIndex index;
        private final Model model;

        DefaultNullabilityIndex(NullableIndex.CheckMode checkMode, Model model) {
            this.checkMode = Objects.requireNonNull(checkMode, "checkMode");
            this.model = Objects.requireNonNull(model, "model");
            this.index = NullableIndex.of(model);
        }

        @Override
        public boolean isNullable(MemberShape member) {
            return index.isMemberNullable(member, checkMode);
        }
    }
}
