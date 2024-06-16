package mx.sugus.braid.plugins.data;

import java.util.Collection;
import java.util.List;
import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.jsyntax.Annotation;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.plugins.data.config.DataPluginConfig;
import mx.sugus.braid.plugins.data.dependencies.DataPluginDependencies;
import mx.sugus.braid.plugins.data.producers.EnumJavaProducer;
import mx.sugus.braid.plugins.data.producers.StructureInterfaceJavaProducer;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;
import mx.sugus.braid.plugins.data.producers.UnionJavaProducer;
import mx.sugus.braid.plugins.data.symbols.DataSymbolProviderDecorator;
import mx.sugus.braid.plugins.data.transformers.BuilderAdderOverridesTransform;
import mx.sugus.braid.plugins.data.transformers.BuilderSetterOverridesTransform;
import mx.sugus.braid.plugins.data.transformers.ClassAddBuilderReferenceTransform;
import mx.sugus.braid.plugins.data.transformers.ClassBuilderOverridesTransform;
import mx.sugus.braid.plugins.data.transformers.ClassFromFactoryOverridesTransform;
import mx.sugus.braid.plugins.data.transformers.InterfaceFromFactoryOverridesTransform;
import mx.sugus.braid.rt.util.annotations.Generated;
import software.amazon.smithy.model.node.ObjectNode;

public final class DataPlugin implements SmithyGeneratorPlugin<DataPluginConfig> {
    public static final Identifier ID = Identifier.of(DataPlugin.class);
    private static final Annotation GENERATED_BY = Annotation.builder(Generated.class)
                                                             .value(CodeBlock.from("$S", ID.toString()))
                                                             .build();

    @Override
    public Identifier provides() {
        return ID;
    }

    @Override
    public Collection<Identifier> requires() {
        return List.of(JavaSyntaxPlugin.ID);
    }

    @Override
    public DataPluginConfig fromNode(ObjectNode node) {
        if (node == null) {
            return DataPluginConfig.builder().build();
        }
        return DataPluginConfig.fromNode(node);
    }

    @Override
    public CodegenModuleConfig moduleConfig(DataPluginConfig config) {
        return CodegenModuleConfig
            .builder()
            .addProducer(new StructureJavaProducer())
            .addProducer(new StructureInterfaceJavaProducer())
            .addProducer(new EnumJavaProducer())
            .addProducer(new UnionJavaProducer())
            .addTransformer(new BuilderSetterOverridesTransform())
            .addTransformer(new BuilderAdderOverridesTransform())
            .addTransformer(new ClassBuilderOverridesTransform())
            .addTransformer(new ClassFromFactoryOverridesTransform())
            .addTransformer(new InterfaceFromFactoryOverridesTransform())
            .addTransformer(new ClassAddBuilderReferenceTransform())
            .addSymbolProviderDecorator(DataSymbolProviderDecorator.get())
            .putDependency(DataPluginDependencies.DATA_PLUGIN_CONFIG, config)
            .build();
    }

    public static Annotation generatedBy() {
        return GENERATED_BY;
    }
}
