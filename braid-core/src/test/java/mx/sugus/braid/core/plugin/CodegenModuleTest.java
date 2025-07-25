package mx.sugus.braid.core.plugin;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;

class CodegenModuleTest {

    // Helper to create a simple shape selector
    private static ShapeSelectorTask createSimpleShapeSelector() {
        return new ShapeSelectorTask() {
            @Override
            public Identifier taskId() {
                return Identifier.of("test#simpleSelector");
            }

            @Override
            public Collection<Shape> select(Model model) {
                return List.of();
            }
        };
    }

    @Test
    void testConstructorWithNullConfig() {
        assertThrows(NullPointerException.class, () -> new CodegenModule(null));
    }

    @Test
    void testBasicModuleCreation() {
        // Create a minimal configuration using the builder
        var config = CodegenModuleConfig.builder()
            .shapeSelector(createSimpleShapeSelector())
            .build();

        // Should be able to create a module without throwing
        var module = new CodegenModule(config);
        assertNotNull(module);
    }

    @Test
    void testSelectWithEmptyModel() {
        var config = CodegenModuleConfig.builder()
            .shapeSelector(createSimpleShapeSelector())
            .build();
        
        var module = new CodegenModule(config);
        var emptyModel = Model.builder().build();
        
        var result = module.select(emptyModel);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSelectWithNullModel() {
        var config = CodegenModuleConfig.builder()
            .shapeSelector(createSimpleShapeSelector())
            .build();
        
        var module = new CodegenModule(config);
        
        assertThrows(NullPointerException.class, () -> module.select(null));
    }

    @Test
    void testEarlyPreprocessModelWithEmptyModel() {
        var config = CodegenModuleConfig.builder()
            .shapeSelector(createSimpleShapeSelector())
            .build();
        
        var module = new CodegenModule(config);
        var emptyModel = Model.builder().build();
        
        var result = module.earlyPreprocessModel(emptyModel);
        assertNotNull(result);
        // Should return the same model when no transformers are configured
        assertSame(emptyModel, result);
    }

    @Test
    void testPreprocessModelWithEmptyModel() {
        var config = CodegenModuleConfig.builder()
            .shapeSelector(createSimpleShapeSelector())
            .build();
        
        var module = new CodegenModule(config);
        var emptyModel = Model.builder().build();
        
        var result = module.preprocessModel(emptyModel);
        assertNotNull(result);
        // Should return the same model when no transformers are configured
        assertSame(emptyModel, result);
    }

    @Test
    void testDecorateSymbolProviderWithNullSymbolProvider() {
        var config = CodegenModuleConfig.builder()
            .shapeSelector(createSimpleShapeSelector())
            .build();
        
        var module = new CodegenModule(config);
        var emptyModel = Model.builder().build();
        
        assertThrows(NullPointerException.class, 
            () -> module.decorateSymbolProvider(emptyModel, null));
    }

    @Test
    void testDecorateSymbolProviderWithNullModel() {
        var config = CodegenModuleConfig.builder()
            .shapeSelector(createSimpleShapeSelector())
            .build();
        
        var module = new CodegenModule(config);
        
        // Create a minimal symbol provider implementation
        SymbolProvider symbolProvider = shape -> null;
        
        assertThrows(NullPointerException.class, 
            () -> module.decorateSymbolProvider(null, symbolProvider));
    }

    @Test
    void testDependenciesNotNull() {
        var config = CodegenModuleConfig.builder()
            .shapeSelector(createSimpleShapeSelector())
            .build();
        
        var module = new CodegenModule(config);
        
        var dependencies = module.dependencies();
        assertNotNull(dependencies);
    }

    @Test
    void testShapeReducersNotNull() {
        var config = CodegenModuleConfig.builder()
            .shapeSelector(createSimpleShapeSelector())
            .build();
        
        var module = new CodegenModule(config);
        
        var reducers = module.shapeReducers();
        assertNotNull(reducers);
    }
}