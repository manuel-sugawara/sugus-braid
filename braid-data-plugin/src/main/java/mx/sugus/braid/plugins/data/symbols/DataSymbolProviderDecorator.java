package mx.sugus.braid.plugins.data.symbols;

import mx.sugus.braid.core.plugin.Dependencies;
import mx.sugus.braid.core.plugin.SymbolProviderDecorator;
import mx.sugus.braid.plugins.data.dependencies.DataPluginDependencies;
import mx.sugus.braid.plugins.data.dependencies.DefaultShapeToJavaType;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;

public class DataSymbolProviderDecorator implements SymbolProviderDecorator {
    private static final DataSymbolProviderDecorator INSTANCE = new DataSymbolProviderDecorator();

    private DataSymbolProviderDecorator() {
    }

    @Override
    public SymbolProvider decorate(Model model, SymbolProvider decorated, Dependencies dependencies) {
        var shapeToJavaName = dependencies.get(DataPluginDependencies.SHAPE_TO_JAVA_NAME);
        var shapeToJavaType = new DefaultShapeToJavaType(shapeToJavaName, model);
        var nullabilityIndexProvider = dependencies.expect(DataPluginDependencies.NULLABILITY_INDEX_PROVIDER);
        return new BraidSymbolProvider(model, shapeToJavaName, shapeToJavaType, nullabilityIndexProvider);
    }

    public static DataSymbolProviderDecorator get() {
        return INSTANCE;
    }
}
