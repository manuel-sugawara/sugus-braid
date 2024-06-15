package mx.sugus.braid.plugins.data.dependencies;

import java.util.Objects;
import java.util.function.Function;
import mx.sugus.braid.core.BraidCodegenPlugin;
import mx.sugus.braid.core.BrideCodegenSettings;
import mx.sugus.braid.core.plugin.DefaultDependencies;
import mx.sugus.braid.core.plugin.Dependencies;
import mx.sugus.braid.core.plugin.DependencyKey;
import mx.sugus.braid.core.util.Lazy;
import mx.sugus.braid.plugins.data.symbols.ShapeToJavaName;
import software.amazon.smithy.codegen.core.ReservedWords;
import software.amazon.smithy.codegen.core.ReservedWordsBuilder;

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
     *
     */
    //public static final DependencyKey<>


    private DataPluginDependencies() {
    }

    static ShapeToJavaName buildShapeToJavaName(Dependencies dependencies) {
        var reservedWords = dependencies.get(RESERVED_WORDS);
        var packageName = dependencies.getOptional(DefaultDependencies.SETTINGS)
                                      .map(BrideCodegenSettings::packageName)
                                      .orElse(null);
        return new ShapeToJavaName(reservedWords, packageName);
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
}
