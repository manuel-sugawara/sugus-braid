package mx.sugus.braid.core.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeType;

/**
 * Configuration used to run the smithy code generation build. Code or plugins can have their own configurations and those are
 * merged with each other to create the final configuration that the code generator uses. The configuration consists of the
 * following elements
 *
 * <dl>
 *     <dt>Model Early Transformers</dt>
 *     <dd>Instances of the {@link ModelTransformerTask} class that transform the model <em>before</em> any other
 *     transformation is performed, including any model simplifications for codegen.</dd>
 *
 *     <dt>Model Transformers</dt>
 *     <dd>Instances of the {@link ModelTransformerTask} class that transform the model <em>after</em> the default codegen
 *     transformations are done.</dd>
 *
 *     <dt>Shape Producers</dt>
 *     <dd>Per {@link ShapeType} instances of {@link ShapeProducerTask} that for each shape of the expected type produce an
 *     instance of its parametric type. For instance, a producer can consume structure shapes and
 *     produce a in memory representation of its Java class</dd>
 *
 *     <dt>Transformers</dt>
 *     <dd>Transformers are also instances of {@link ShapeTaskTransformer} that can consume the instances of a specific producer
 *     task and
 *     return instances of the same class potentially modified. For instance, a transformer can take an in memory
 *     representation of a Java class for a structure shape and add methods to it.</dd>
 *
 *     <dt>Consumers</dt>
 *     <dd>Consumers are the last step in the codegen pipeline, those are instances of {@link ConsumerTask} that consume a
 *     specific type of instances produced by any producer. This final step usually serializes the result as an output of the
 *     build process.</dd>
 * </dl>
 */
@SuppressWarnings("unchecked")
public final class CodegenModuleConfig {
    private final Set<ShapeSelectorTask> shapeSelectors;
    private final Set<ModelTransformerTask> earlyModelTransformers;
    private final Set<ModelTransformerTask> modelTransformers;
    private final Map<ShapeType, Set<ShapeProducerTask<?>>> shapeProducers;
    private final Map<Identifier, Set<ShapeTaskTransformer<?>>> shapeTaskTransformers;
    private final Set<NonShapeProducerTask<?>> nonShapeProducers;
    private final Map<Identifier, Set<NonShapeTaskTransformer<?>>> nonShapeTaskTransformers;
    private final Map<Class<?>, Set<ConsumerTask<?>>> consumers;
    private final Set<ShapeReducer<?>> shapeReducers;
    private final List<SymbolProviderDecorator> symbolProviderDecorators;

    CodegenModuleConfig(Builder builder) {
        // shape
        var shapeProducers2 = new LinkedHashMap<ShapeType, Set<ShapeProducerTask<?>>>();
        for (var kvp : builder.shapeProducers.entrySet()) {
            shapeProducers2.put(kvp.getKey(), Collections.unmodifiableSet(new LinkedHashSet<>(kvp.getValue())));
        }
        this.shapeProducers = Collections.unmodifiableMap(shapeProducers2);
        var shapeTaskTransformers2 = new LinkedHashMap<Identifier, Set<ShapeTaskTransformer<?>>>();
        for (var kvp : builder.shapeTaskTransformers.entrySet()) {
            shapeTaskTransformers2.put(kvp.getKey(), Collections.unmodifiableSet(new LinkedHashSet<>(kvp.getValue())));
        }
        this.shapeTaskTransformers = Collections.unmodifiableMap(shapeTaskTransformers2);
        this.shapeReducers = Collections.unmodifiableSet(builder.shapeReducers);

        // Non-shape
        this.nonShapeProducers = new LinkedHashSet<>(builder.nonShapeProducers);
        var nonShapeTaskTransformers2 = new LinkedHashMap<Identifier, Set<NonShapeTaskTransformer<?>>>();
        for (var kvp : builder.nonShapeTaskTransformers.entrySet()) {
            nonShapeTaskTransformers2.put(kvp.getKey(), Collections.unmodifiableSet(new LinkedHashSet<>(kvp.getValue())));
        }
        this.nonShapeTaskTransformers = Collections.unmodifiableMap(nonShapeTaskTransformers2);

        // consumers
        var consumers2 = new LinkedHashMap<Class<?>, Set<ConsumerTask<?>>>();
        for (var kvp : builder.consumers.entrySet()) {
            consumers2.put(kvp.getKey(), Collections.unmodifiableSet(new LinkedHashSet<>(kvp.getValue())));
        }
        this.consumers = Collections.unmodifiableMap(consumers2);

        // Model processing
        this.shapeSelectors = Collections.unmodifiableSet(new LinkedHashSet<>(builder.shapeSelectors));
        this.modelTransformers = Collections.unmodifiableSet(builder.modelTransformers);
        this.earlyModelTransformers = Collections.unmodifiableSet(builder.earlyModelTransformers);
        this.symbolProviderDecorators = Collections.unmodifiableList(builder.symbolProviderDecorators);
    }

    /**
     * Returns the configured symbol provider decorators.
     *
     * @return the configured symbol provider decorators.
     */
    public List<SymbolProviderDecorator> symbolProviderDecorators() {
        return symbolProviderDecorators;
    }

    /**
     * Returns the shape selector for codegen.
     */
    public ShapeSelectorTask shapeSelector() {
        if (shapeSelectors.isEmpty()) {
            return DefaultShapeSelector.get();
        }
        if (shapeSelectors.size() > 1) {
            // TODO: is there a better way to validate/error out for this case?, e.g.,
            //  at build time/ merge time.
            throw new RuntimeException("Multiple shape selectors defined, only one must be defined: " +
                                       shapeSelectors.stream()
                                                     .map(ShapeSelectorTask::taskId)
                                                     .map(Object::toString)
                                                     .collect(Collectors.joining(", ", "[", "]")));
        }
        return shapeSelectors.iterator().next();
    }

    /**
     * Returns the collection of the configured producers for the given shape type.
     *
     * @param shape The shape for which type the producers are returned.
     * @return The collection of the configured producers for the given shape type.
     */
    public Collection<ShapeProducerTask<?>> shapeProducers(Shape shape) {
        return shapeProducers.getOrDefault(shape.getType(), Set.of());
    }

    /**
     * Returns the collection of the configured transformers for the given task.
     *
     * @param task The task for which the transformers are returned.
     * @param <T>  The type that the task produces and the transformers take
     * @return The collection of the configured transformers for the given task.
     */
    public <T> Collection<ShapeTaskTransformer<T>> shapeTaskTransformers(ShapeProducerTask<T> task) {
        return shapeTaskTransformers.getOrDefault(task.taskId(), Set.of())
                                    .stream()
                                    .map(x -> (ShapeTaskTransformer<T>) x)
                                    .collect(Collectors.toList());
    }

    /**
     * Returns the collection of the configured producers.
     *
     * @return The collection of the configured producers for the given shape type.
     */
    public Collection<NonShapeProducerTask<?>> nonShapeProducers() {
        return nonShapeProducers;
    }

    /**
     * Returns the collection of the configured transformers for the given task.
     *
     * @param task The task for which the transformers are returned.
     * @param <T>  The type that the task produces and the transformers take
     * @return The collection of the configured transformers for the given task.
     */
    public <T> Collection<NonShapeTaskTransformer<T>> nonShapeTaskTransformers(NonShapeProducerTask<T> task) {
        return nonShapeTaskTransformers.getOrDefault(task.taskId(), Set.of())
                                       .stream()
                                       .map(x -> (NonShapeTaskTransformer<T>) x)
                                       .collect(Collectors.toList());
    }

    /**
     * Returns the collection of configured consumers for the type returned by the given shape task.
     *
     * @param task The task for which the consumers are returned.
     * @param <T>  The type that the task produces and the consumers take
     * @return The collection of configured consumers for the type returned by the given task.
     */
    public <T> Collection<ConsumerTask<T>> consumers(ShapeProducerTask<T> task) {
        return consumers.getOrDefault(task.output(), Set.of())
                        .stream()
                        .map(x -> (ConsumerTask<T>) x)
                        .collect(Collectors.toSet());
    }

    /**
     * Returns the collection of configured consumers for the type returned by the given task.
     *
     * @param task The task for which the consumers are returned.
     * @param <T>  The type that the task produces and the consumers take
     * @return The collection of configured consumers for the type returned by the given task.
     */
    public <T> Collection<ConsumerTask<T>> nonShapeConsumers(NonShapeProducerTask<T> task) {
        return consumers.getOrDefault(task.output(), Set.of())
                        .stream()
                        .map(x -> (ConsumerTask<T>) x)
                        .collect(Collectors.toSet());
    }

    /**
     * Returns the collection of the configured model transformers.
     *
     * @return The collection of the configured model transformers
     */
    public Collection<ModelTransformerTask> modelTransformers() {
        return modelTransformers;
    }

    /**
     * Returns the collection of the configured model early transformers.
     *
     * @return The collection of the configured model early transformers.
     */
    public Collection<ModelTransformerTask> modelEarlyTransformers() {
        return earlyModelTransformers;
    }

    /**
     * Returns the collection of the configured shape reducers.
     *
     * @return The collection of the configured shape reducers.
     */
    public Collection<ShapeReducer<?>> shapeReducers() {
        return shapeReducers;
    }

    /**
     * Creates a new {@link Builder} to build instances of {@link CodegenModuleConfig}.
     *
     * @return A new builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Set<ShapeSelectorTask> shapeSelectors = new LinkedHashSet<>();
        private final Set<ModelTransformerTask> earlyModelTransformers = new LinkedHashSet<>();
        private final Set<ModelTransformerTask> modelTransformers = new LinkedHashSet<>();
        private final Map<ShapeType, Set<ShapeProducerTask<?>>> shapeProducers = new LinkedHashMap<>();
        private final Map<Identifier, Set<ShapeTaskTransformer<?>>> shapeTaskTransformers = new LinkedHashMap<>();
        private final Set<NonShapeProducerTask<?>> nonShapeProducers = new LinkedHashSet<>();
        private final Map<Identifier, Set<NonShapeTaskTransformer<?>>> nonShapeTaskTransformers = new LinkedHashMap<>();
        private final Map<Class<?>, Set<ConsumerTask<?>>> consumers = new LinkedHashMap<>();
        private final Set<ShapeReducer<?>> shapeReducers = new LinkedHashSet<>();
        private final List<SymbolProviderDecorator> symbolProviderDecorators = new ArrayList<>();

        Builder() {
        }

        /**
         * Configures a shape selector task.
         *
         * @param shapeSelectorTask the shape selector task
         * @return This instance for method chaining.
         */
        public Builder shapeSelector(ShapeSelectorTask shapeSelectorTask) {
            shapeSelectors.add(shapeSelectorTask);
            return this;
        }

        /**
         * Adds a shape producer to the builder
         *
         * @param producer The producer to be added
         * @return This instance for method chaining.
         */
        public Builder addProducer(ShapeProducerTask<?> producer) {
            shapeProducers.computeIfAbsent(producer.type(), t -> new LinkedHashSet<>())
                          .add(producer);
            return this;
        }

        /**
         * Adds a producers to the builder
         *
         * @param producer The producer to be added
         * @return This instance for method chaining.
         */
        public Builder addProducer(NonShapeProducerTask<?> producer) {
            nonShapeProducers.add(producer);
            return this;
        }

        /**
         * Adds a transformer for a producer task with the given identifier.
         *
         * @param transformer The transformer
         * @return This instance for method chaining.
         */
        public Builder addTransformer(ShapeTaskTransformer<?> transformer) {
            shapeTaskTransformers.computeIfAbsent(transformer.transformsId(), t -> new LinkedHashSet<>())
                                 .add(transformer);
            return this;
        }

        /**
         * Adds a transformer for a producer task with the given identifier.
         *
         * @param transformer The transformer
         * @return This instance for method chaining.
         */
        public Builder addNonShapeTaskTransformer(NonShapeTaskTransformer<?> transformer) {
            nonShapeTaskTransformers.computeIfAbsent(transformer.transformsId(), t -> new LinkedHashSet<>())
                                    .add(transformer);
            return this;
        }

        /**
         * Adds a consumer to the builder.
         *
         * @param consumer The consumer to be added.
         * @return This instance for method chaining.
         */
        public Builder addConsumer(ConsumerTask<?> consumer) {
            consumers.computeIfAbsent(consumer.input(), t -> new LinkedHashSet<>())
                     .add(consumer);
            return this;
        }

        /**
         * Adds a model transformer to the builder
         *
         * @param transformer The transformer to be added
         * @return This instance for method chaining.
         */
        public Builder addModelTransformer(ModelTransformerTask transformer) {
            this.modelTransformers.add(transformer);
            return this;
        }

        /**
         * Adds an early model transformer to the builder.
         *
         * @param transformer The transformer to be added.
         * @return This instance for method chaining.
         */
        public Builder addModelEarlyTransformer(ModelTransformerTask transformer) {
            this.earlyModelTransformers.add(transformer);
            return this;
        }

        /**
         * Adds a shape reducer to the builder.
         *
         * @param reducer The reducer to be added.
         * @return This instance for method chaining.
         */
        public Builder addShapeReducer(ShapeReducer<?> reducer) {
            this.shapeReducers.add(reducer);
            return this;
        }

        /**
         * Adds a symbol provider decorator.
         *
         * @param decorator The symbol provider decorator to be added.
         * @return This instance for method chaining.
         */
        public Builder addSymbolProviderDecorator(SymbolProviderDecorator decorator) {
            this.symbolProviderDecorators.add(decorator);
            return this;
        }

        /**
         * Merges the given module configuration into this builder. The other configuration takes precedence over the already
         * configured settings in the builder.
         *
         * @param other The module config to merge
         * @return This instance for method chaining.
         */
        public Builder merge(CodegenModuleConfig other) {
            shapeSelectors.addAll(other.shapeSelectors);
            other.shapeProducers.forEach((k, v) -> {
                shapeProducers.computeIfAbsent(k, t -> new LinkedHashSet<>())
                              .addAll(v);
            });
            other.shapeTaskTransformers.forEach((k, v) -> {
                shapeTaskTransformers.computeIfAbsent(k, t -> new LinkedHashSet<>())
                                     .addAll(v);
            });
            nonShapeProducers.addAll(other.nonShapeProducers);
            other.nonShapeTaskTransformers.forEach((k, v) -> {
                nonShapeTaskTransformers.computeIfAbsent(k, t -> new LinkedHashSet<>())
                                        .addAll(v);
            });
            other.consumers.forEach((k, v) -> {
                consumers.computeIfAbsent(k, t -> new LinkedHashSet<>())
                         .addAll(v);
            });
            modelTransformers.addAll(other.modelTransformers);
            earlyModelTransformers.addAll(other.earlyModelTransformers);
            shapeReducers.addAll(other.shapeReducers);
            symbolProviderDecorators.addAll(other.symbolProviderDecorators);
            return this;
        }

        /**
         * Builds and returns a new module configuration using the configured values in the builder.
         *
         * @return A new module configuration using the configured values in the builder.
         */
        public CodegenModuleConfig build() {
            return new CodegenModuleConfig(this);
        }
    }
}
