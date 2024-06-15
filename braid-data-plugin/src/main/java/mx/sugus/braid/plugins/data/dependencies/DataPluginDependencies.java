package mx.sugus.braid.plugins.data.dependencies;

import java.util.Objects;
import java.util.function.Function;
import mx.sugus.braid.core.BraidCodegenPlugin;
import mx.sugus.braid.core.BrideCodegenSettings;
import mx.sugus.braid.core.plugin.DefaultDependencies;
import mx.sugus.braid.core.plugin.Dependencies;
import mx.sugus.braid.core.plugin.DependencyKey;
import mx.sugus.braid.core.util.Lazy;
import mx.sugus.braid.core.util.Name;
import software.amazon.smithy.codegen.core.ReservedWords;
import software.amazon.smithy.codegen.core.ReservedWordsBuilder;
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
     * The class to convert shapes to java names.
     */
    public static final DependencyKey<ShapeToJavaName> SHAPE_TO_JAVA_NAME =
        DependencyKey.from("shape->java-name", DataPluginDependencies::buildShapeToJavaName);

    /**
     * The class to escape reserved words.
     */
    public static final DependencyKey<ReservedWordsEscaper> RESERVED_WORDS_ESCAPER =
        DependencyKey.from("shape->java-name", DataPluginDependencies::buildReservedWordsEscaper);


    static ShapeToJavaName buildShapeToJavaName(Dependencies dependencies) {
        var packageName = dependencies.getOptional(DefaultDependencies.SETTINGS)
                                      .map(BrideCodegenSettings::packageName)
                                      .orElse(null);
        var escaper = dependencies.expect(RESERVED_WORDS_ESCAPER);
        return new ShapeToJavaName(packageName, escaper);
    }

    static ReservedWordsEscaper buildReservedWordsEscaper(Dependencies dependencies) {
        var reservedWords = dependencies.expect(RESERVED_WORDS);
        return new DefaultReservedWordsEscaper(reservedWords);
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
}
