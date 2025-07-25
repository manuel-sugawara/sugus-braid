package mx.sugus.braid.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import mx.sugus.braid.core.plugin.CodegenModule;
import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeSelectorTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.StringShape;

class BraidCodegenDirectorTest {

    private Model model = Model.builder().build();

    @Mock
    private FileManifest fileManifest;

    private BraidCodegenSettings settings = new BraidCodegenSettings(ObjectNode.objectNode(),
                                                                     ShapeId.from("example#Foo"),
                                                                     null,
                                                                     "package",
                                                                     "version");

    @Mock
    private SymbolProvider symbolProvider;

    private CodegenModule module;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Create a real CodegenModule instance instead of mocking
        var config = CodegenModuleConfig.builder()
            .shapeSelector(createSimpleShapeSelector())
            .build();
        
        module = new CodegenModule(config);
    }

    @Test
    void testBuilderWithSymbolProvider() {
        var director = BraidCodegenDirector.builder()
                                           .model(model)
                                           .fileManifest(fileManifest)
                                           .settings(settings)
                                           .module(module)
                                           .symbolProvider(symbolProvider)
                                           .build();

        assertNotNull(director);
    }

    @Test
    void testBuilderThrowsOnNullModel() {
        var builder = BraidCodegenDirector.builder()
                                          .fileManifest(fileManifest)
                                          .settings(settings)
                                          .module(module)
                                          .symbolProvider(symbolProvider);

        assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    void testBuilderThrowsOnNullFileManifest() {
        var builder = BraidCodegenDirector.builder()
                                          .model(model)
                                          .settings(settings)
                                          .module(module)
                                          .symbolProvider(symbolProvider);

        assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    void testBuilderThrowsOnNullSettings() {
        var builder = BraidCodegenDirector.builder()
                                          .model(model)
                                          .fileManifest(fileManifest)
                                          .module(module)
                                          .symbolProvider(symbolProvider);

        assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    void testBuilderThrowsOnNullModule() {
        var builder = BraidCodegenDirector.builder()
                                          .model(model)
                                          .fileManifest(fileManifest)
                                          .settings(settings)
                                          .symbolProvider(symbolProvider);

        assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    void testBuilderThrowsOnNeitherSymbolProviderNorFactory() {
        var builder = BraidCodegenDirector.builder()
                                          .model(model)
                                          .fileManifest(fileManifest)
                                          .settings(settings)
                                          .module(module);

        var exception = assertThrows(IllegalStateException.class, builder::build);
        assertTrue(exception.getMessage().contains("Must specify either symbolProvider or symbolProviderFactory"));
    }

    @Test
    void testExecuteWithNoShapes() {
        var director = BraidCodegenDirector.builder()
                                           .model(model)
                                           .fileManifest(fileManifest)
                                           .settings(settings)
                                           .module(module)
                                           .symbolProvider(symbolProvider)
                                           .build();

        // Should complete without throwing since no shapes are selected
        assertDoesNotThrow(director::execute);
    }

    @Test
    void testExecuteWithShapes() {
        // Create a module that returns some shapes
        var shapeReturningSelector = new ShapeSelectorTask() {
            @Override
            public Identifier taskId() {
                return Identifier.of("test#shapeReturningSelector");
            }

            @Override
            public Collection<Shape> select(Model model) {
                return List.of(StringShape.builder().id("com.example#TestString").build());
            }
        };

        var config = CodegenModuleConfig.builder()
            .shapeSelector(shapeReturningSelector)
            .build();
        
        var moduleWithShapes = new CodegenModule(config);

        var director = BraidCodegenDirector.builder()
                                           .model(model)
                                           .fileManifest(fileManifest)
                                           .settings(settings)
                                           .module(moduleWithShapes)
                                           .symbolProvider(symbolProvider)
                                           .build();

        // Should complete without throwing even with shapes
        assertDoesNotThrow(director::execute);
    }

    @Test
    void testBuilderPreparationWithValidInputs() {
        // Test that the builder correctly processes model transformations
        var originalModel = Model.builder()
            .addShape(StringShape.builder().id("com.example#OriginalString").build())
            .build();

        var director = BraidCodegenDirector.builder()
                                           .model(originalModel)
                                           .fileManifest(fileManifest)
                                           .settings(settings)
                                           .module(module)
                                           .symbolProvider(symbolProvider)
                                           .build();

        assertNotNull(director);
    }

    @Test
    void testBuilderWithSymbolProviderFactory() {
        BiFunction<Model, BraidCodegenSettings, SymbolProvider> symbolProviderFactory = (model, settings) -> symbolProvider;

        var director = BraidCodegenDirector.builder()
                                           .model(model)
                                           .fileManifest(fileManifest)
                                           .settings(settings)
                                           .module(module)
                                           .symbolProviderFactory(symbolProviderFactory)
                                           .build();

        assertNotNull(director);
    }

    @Test
    void testBuilderThrowsOnBothSymbolProviderAndFactory() {
        BiFunction<Model, BraidCodegenSettings, SymbolProvider> symbolProviderFactory = (model, settings) -> symbolProvider;

        var builder = BraidCodegenDirector.builder()
                                          .model(model)
                                          .fileManifest(fileManifest)
                                          .settings(settings)
                                          .module(module)
                                          .symbolProvider(symbolProvider)
                                          .symbolProviderFactory(symbolProviderFactory);

        var exception = assertThrows(IllegalStateException.class, builder::build);
        assertTrue(exception.getMessage().contains("Cannot specify both symbolProvider and symbolProviderFactory"));
    }
}