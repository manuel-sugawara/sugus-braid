package mx.sugus.braid.core.plugin;

import java.util.Collection;
import java.util.Objects;
import java.util.logging.Logger;
import mx.sugus.braid.traits.CodegenIgnoreTrait;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;

/**
 * The codegen module implements the codegen pipeline by using configuration to produce elements, transform them, and finally
 * consume them.
 *
 * <p>The module orchestrates the entire code generation process through a series of phases:
 * <ol>
 *   <li><strong>Shape Selection:</strong> Selects which shapes to process for code generation</li>
 *   <li><strong>Model Transformation:</strong> Applies early and standard model transformations</li>
 *   <li><strong>Symbol Provider Decoration:</strong> Enhances symbol providers with decorators</li>
 *   <li><strong>Shape Generation:</strong> Runs producers, transformers, and consumers for each shape</li>
 *   <li><strong>Non-Shape Generation:</strong> Generates additional artifacts not tied to specific shapes</li>
 * </ol>
 *
 * <p>This class is thread-safe as it is immutable after construction.
 */
public final class CodegenModule {
    private static final Logger LOG = Logger.getLogger(CodegenModule.class.getName());
    private final CodegenModuleConfig config;

    /**
     * Creates a new module with the given configuration.
     *
     * @param config The module configuration, must not be null
     * @throws NullPointerException if config is null
     */
    public CodegenModule(CodegenModuleConfig config) {
        this.config = Objects.requireNonNull(config, "config");
    }

    /**
     * Selects the shapes for codegen using the configured selector or a default, "includes all", selector.
     *
     * @param model The model to select shapes from, must not be null
     * @return The shapes for codegen
     * @throws NullPointerException if model is null
     */
    public Collection<Shape> select(Model model) {
        Objects.requireNonNull(model, "model");
        return config.shapeSelector().select(model);
    }

    /**
     * Runs the configured early model transformers and returns the updated model.
     *
     * @param model The model to transform, must not be null
     * @return The transformed model
     * @throws NullPointerException if model is null
     */
    public Model earlyPreprocessModel(Model model) {
        Objects.requireNonNull(model, "model");
        var result = model;
        for (var transformer : config.modelEarlyTransformers()) {
            try {
                LOG.fine(() -> String.format("Running model early transformer `%s`", transformer.taskId()));
                result = transformer.transform(result);
            } catch (Exception e) {
                var taskId = transformer.taskId();
                LOG.severe(() -> String.format("Model early transformer '%s' failed: %s", taskId, e.getMessage()));
                throw new RuntimeException(String.format("Model early transformer '%s' failed", taskId), e);
            }
        }
        return result;
    }

    /**
     * Runs the configured model transformers and returns the updated model.
     *
     * @param model The model to transform, must not be null
     * @return The transformed model
     * @throws NullPointerException if model is null
     */
    public Model preprocessModel(Model model) {
        Objects.requireNonNull(model, "model");
        var result = model;
        for (var transformer : config.modelTransformers()) {
            try {
                LOG.fine(() -> String.format("Running model transformer `%s`", transformer.taskId()));
                result = transformer.transform(result);
            } catch (Exception e) {
                var taskId = transformer.taskId();
                LOG.severe(() -> String.format("Model transformer '%s' failed: %s", taskId, e.getMessage()));
                throw new RuntimeException(String.format("Model transformer '%s' failed", taskId), e);
            }
        }
        return result;
    }

    /**
     * Applies all the configured symbol provider decorators and returns the final decorated symbol provider.
     *
     * @param model          The model, must not be null
     * @param symbolProvider The source symbol provider, must not be null
     * @return the decorated symbol provider
     * @throws NullPointerException if model or symbolProvider is null
     */
    public SymbolProvider decorateSymbolProvider(Model model, SymbolProvider symbolProvider) {
        Objects.requireNonNull(model, "model");
        Objects.requireNonNull(symbolProvider, "symbolProvider");
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
     * @param state The state that contains the shape and adjacent types used for code generation, must not be null
     * @throws NullPointerException if state is null
     */
    public void generateShape(ShapeCodegenState state) {
        Objects.requireNonNull(state, "state");
        var shape = state.shape();
        if (shape.hasTrait(CodegenIgnoreTrait.class)) {
            LOG.fine(() -> String.format("Skipping shape `%s` marked with `CodegenIgnoreTrait`", shape.getId()));
            return;
        }
        for (var task : config.shapeProducers(shape)) {
            processShapeTask(state, task);
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
     * @param state The state that contains the shape and adjacent types used for code generation, must not be null
     * @throws NullPointerException if state is null
     */
    public void generateNonShape(CodegenState state) {
        Objects.requireNonNull(state, "state");
        for (var task : config.nonShapeProducers()) {
            processNonShapeTask(state, task);
        }
        for (var task : config.nonShapeMultiProducers()) {
            processNonShapeMultiTask(state, task);
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

    /**
     * Processes a shape task by running it through the producer-transformer-consumer pipeline.
     */
    private <T> void processShapeTask(ShapeCodegenState state, ShapeProducerTask<T> task) {
        try {
            var result = runShapeTask(state, task);
            if (result != null) {
                consumeResult(state, task, result);
            }
        } catch (Exception e) {
            var shapeId = state.shape().getId();
            var taskId = task.taskId();
            LOG.severe(() -> String.format("Failed to process shape task '%s' for shape '%s': %s",
                                           taskId, shapeId, e.getMessage()));
            throw new RuntimeException(String.format("Failed to process shape task '%s' for shape '%s'",
                                                     taskId, shapeId), e);
        }
    }

    /**
     * Processes a non-shape task by running it through the producer-transformer-consumer pipeline.
     */
    private <T> void processNonShapeTask(CodegenState state, NonShapeProducerTask<T> task) {
        try {
            var result = runNonShapeTask(state, task);
            if (result != null) {
                consumeNonShapeResult(state, task, result);
            }
        } catch (Exception e) {
            var taskId = task.taskId();
            LOG.severe(() -> String.format("Failed to process non-shape task '%s': %s",
                                           taskId, e.getMessage()));
            throw new RuntimeException(String.format("Failed to process non-shape task '%s'", taskId), e);
        }
    }

    /**
     * Processes a non-shape task by running it through the producer-transformer-consumer pipeline.
     */
    private <T> void processNonShapeMultiTask(CodegenState state, NonShapeMultiProducerTask<T> task) {
        try {
            var result = runNonShapeMultiTask(state, task);
            if (result != null) {
                consumeNonShapeMultiResult(state, task, result);
            }
        } catch (Exception e) {
            var taskId = task.taskId();
            LOG.severe(() -> String.format("Failed to process non-shape task '%s': %s",
                                           taskId, e.getMessage()));
            throw new RuntimeException(String.format("Failed to process non-shape task '%s'", taskId), e);
        }
    }

    private <T> T runShapeTask(ShapeCodegenState state, ShapeProducerTask<T> task) {
        LOG.fine(() -> String.format("Running producer `%s` on shape `%s`",
                                     task.taskId(), state.shape().getId()));
        var result = task.produce(state);
        if (result != null) {
            for (var transformer : config.shapeTaskTransformers(task)) {
                LOG.fine(() -> String.format("Running transformer `%s` for producer `%s` on shape `%s`",
                                             transformer.taskId(), task.taskId(), state.shape().getId()));
                result = transformer.transform(result, state);
                // Transformers can return null to break the pipeline.
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

    private <T> Collection<T> runNonShapeMultiTask(CodegenState state, NonShapeMultiProducerTask<T> task) {
        LOG.fine(() -> String.format("Running non-shape producer `%s`",
                                     task.taskId()));
        var result = task.produce(state);
        if (result != null) {
            for (var transformer : config.nonShapeMultiTaskTransformers(task)) {
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

    private <T> void consumeResult(ShapeCodegenState state, ShapeProducerTask<T> task, T result) {
        for (var consumer : config.consumers(task)) {
            LOG.fine(() -> String.format("Running consumer `%s` for producer `%s` on shape `%s`",
                                         consumer.taskId(), task.taskId(), state.shape().getId()));
            consumer.consume(result, state);
        }
    }

    private <T> void consumeNonShapeResult(CodegenState state, NonShapeProducerTask<T> task, T result) {
        for (var consumer : config.consumers(task)) {
            LOG.fine(() -> String.format("Running consumer `%s` for producer `%s`",
                                         consumer.taskId(), task.taskId()));
            consumer.consume(result, state);
        }
    }

    private <T> void consumeNonShapeMultiResult(CodegenState state, NonShapeMultiProducerTask<T> task, Collection<T> result) {
        for (var consumer : config.consumers(task)) {
            LOG.fine(() -> String.format("Running consumer `%s` for producer `%s`",
                                         consumer.taskId(), task.taskId()));
            for (var item : result) {
                consumer.consume(item, state);
            }
        }
    }
}
