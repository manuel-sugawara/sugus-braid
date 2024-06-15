package mx.sugus.braid.core.plugin;

import java.util.Collection;
import java.util.logging.Logger;
import mx.sugus.braid.traits.CodegenIgnoreTrait;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;

/**
 * The codegen module implements the codegen pipeline by using configuration to produce elements, transforms them, and, finally
 * consuming them.
 */
public final class CodegenModule {
    private static final Logger LOG = Logger.getLogger(CodegenModule.class.getName());
    private final CodegenModuleConfig config;

    /**
     * Creates a new module with the given configuration.
     *
     * @param config The module configuration
     */
    public CodegenModule(CodegenModuleConfig config) {
        this.config = config;
    }

    /**
     * Selects the shapes for codegen using the configured selector or a default, "includes all", selector.
     *
     * @return The shapes for codegen
     */
    public Collection<Shape> select(Model model) {
        return config.shapeSelector().select(model);
    }

    /**
     * Runs the configured early model transformers and returns the updated model.
     *
     * @param model The model to transform
     * @return The transformed model
     */
    public Model earlyPreprocessModel(Model model) {
        var result = model;
        for (var transformer : config.modelEarlyTransformers()) {
            LOG.fine(() -> String.format("Running model early transformer `%s`", transformer.taskId()));
            result = transformer.transform(result);
        }
        return result;
    }

    /**
     * Runs the configured model transformers and returns the updated model.
     *
     * @param model The model to transform
     * @return The transformed model
     */
    public Model preprocessModel(Model model) {
        var result = model;
        for (var transformer : config.modelTransformers()) {
            LOG.fine(() -> String.format("Running model transformer `%s`", transformer.taskId()));
            result = transformer.transform(result);
        }
        return result;
    }

    /**
     * Applies all the configured symbol provider decorators and returns the final decorated symbol provider.
     *
     * @param symbolProvider The source symbol provider
     * @return the decorated symbol provider
     */
    public SymbolProvider decorateSymbolProvider(Model model, SymbolProvider symbolProvider) {
        var result = symbolProvider;
        for (var decorator : config.symbolProviderDecorators()) {
            result = decorator.decorate(model, result, config.dependencies());
        }
        return result;
    }

    /**
     * Returns the collection of shape task reducers.
     *
     * @return the collection of shape task reducers.
     */
    public Collection<ShapeReducer<?>> shapeReducers() {
        return config.shapeReducers();
    }

    /**
     * Runs the generation pipeline for the given shape in the state by,
     *
     * <ol>
     *     <li>Running the configured producers for the shape type, then</li>
     *     <li>For each produced object it runs the configured transformers on them, and, finally</li>
     *     <li>For each produced and transformed object runs the configured consumers for the produced type.</li>
     * </ol>
     *
     * @param state The state that contains the shape and adjacent types used for code generation
     */
    public void generateShape(ShapeCodegenState state) {
        var shape = state.shape();
        if (shape.hasTrait(CodegenIgnoreTrait.class)) {
            LOG.fine(() -> String.format("Skipping shape `%s` marked with `CodegenIgnoreTrait`", shape.getId()));
            return;
        }
        for (var task : config.shapeProducers(shape)) {
            var result = runShapeTask(state, task);
            if (result != null) {
                consumeResult(state, task, result);
            }
        }
    }

    /**
     * Runs the generation pipeline for configured non-shape producers,
     *
     * <ol>
     *     <li>Running the configured producers, then</li>
     *     <li>For each produced object it runs the configured transformers on them, and, finally</li>
     *     <li>For each produced and transformed object runs the configured consumers for the produced type.</li>
     * </ol>
     *
     * @param state The state that contains the shape and adjacent types used for code generation
     */
    public void generateNonShape(CodegenState state) {
        for (var task : config.nonShapeProducers()) {
            var result = runNonShapeTask(state, task);
            if (result != null) {
                consumeNonShapeResult(state, task, result);
            }
        }
    }

    /**
     * Returns the configured dependencies.
     *
     * @return The configured dependencies.
     */
    public Dependencies dependencies() {
        return config.dependencies();
    }

    private <T> T runShapeTask(ShapeCodegenState state, ShapeProducerTask<T> task) {
        LOG.fine(() -> String.format("Running producer `%s` on shape `%s",
                                     task.taskId(), state.shape().getId()));
        var result = task.produce(state);
        if (result != null) {
            for (var transformer : config.shapeTaskTransformers(task)) {
                LOG.fine(() -> String.format("Running transformer `%s` for producer `%s` on shape `%s",
                                             transformer.taskId(), task.taskId(), state.shape().getId()));
                result = transformer.transform(result, state);
                // Transformers return null to break the pipeline.
                if (result == null) {
                    return null;
                }
            }
        }
        return result;
    }

    private <T> T runNonShapeTask(CodegenState state, NonShapeProducerTask<T> task) {
        LOG.fine(() -> String.format("Running non-shape producer `%s`",
                                     task.taskId()));
        var result = task.produce(state);
        if (result != null) {
            for (var transformer : config.nonShapeTaskTransformers(task)) {
                LOG.fine(() -> String.format("Running non-shape transformer `%s` for producer `%s`",
                                             transformer.taskId(), task.taskId()));
                result = transformer.transform(result, state);
                // Transformers return null to break the pipeline.
                if (result == null) {
                    return null;
                }
            }
        }
        return result;
    }

    private <T> void consumeResult(ShapeCodegenState state, ShapeProducerTask<?> task, T result) {
        @SuppressWarnings("unchecked")
        var producerTask = (ShapeProducerTask<T>) task;
        for (var consumer : config.consumers(producerTask)) {
            LOG.fine(() -> String.format("Running consumer `%s` for producer `%s` on shape `%s",
                                         consumer.taskId(), task.taskId(), state.shape().getId()));
            consumer.consume(result, state);
        }
    }

    private <T> void consumeNonShapeResult(CodegenState state, NonShapeProducerTask<?> task, T result) {
        @SuppressWarnings("unchecked")
        var producerTask = (NonShapeProducerTask<T>) task;
        for (var consumer : config.nonShapeConsumers(producerTask)) {
            LOG.fine(() -> String.format("Running consumer `%s` for producer `%s`",
                                         consumer.taskId(), task.taskId()));
            consumer.consume(result, state);
        }
    }
}
