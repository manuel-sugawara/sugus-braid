package mx.sugus.braid.plugins.data.symbols;

import java.util.Objects;
import java.util.function.Function;
import mx.sugus.braid.core.BraidCodegenPlugin;
import mx.sugus.braid.core.plugin.SymbolProviderDecorator;
import software.amazon.smithy.codegen.core.ReservedWords;
import software.amazon.smithy.codegen.core.ReservedWordsBuilder;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;

public class DataSymbolProviderDecorator implements SymbolProviderDecorator {

    public static final ReservedWords RESERVED_WORDS = buildReservedWords();
    private final String packageName;

    public DataSymbolProviderDecorator(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public SymbolProvider decorate(Model model, SymbolProvider decorated) {
        return createSymbolProvider(model, packageName);
    }

    private static SymbolProvider createSymbolProvider(Model model, String packageName) {
        var escaper = RESERVED_WORDS;
        var shapeToJavaName = new ShapeToJavaName(model, escaper, packageName);
        var shapeToJavaType = new ShapeToJavaType(shapeToJavaName, model);
        return new BraidSymbolProvider(model, shapeToJavaName, shapeToJavaType, packageName);
    }

    private static ReservedWords buildReservedWords() {
        return
            new ReservedWordsBuilder()
                .loadWords(Objects.requireNonNull(BraidCodegenPlugin.class.getResource("java-reserved-words.txt")),
                           Function.identity())
                .loadWords(Objects.requireNonNull(BraidCodegenPlugin.class.getResource("java-system-type-names.txt")),
                           Function.identity())
                .build();
    }


}
