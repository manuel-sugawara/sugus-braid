package mx.sugus.braid.plugins.data;

import java.util.Collection;
import java.util.List;
import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.jsyntax.Annotation;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.plugins.data.producers.BuilderAdderOverridesTransform;
import mx.sugus.braid.plugins.data.producers.BuilderSetterOverridesTransform;
import mx.sugus.braid.plugins.data.producers.ClassBuilderOverridesTransform;
import mx.sugus.braid.plugins.data.producers.ClassFromFactoryOverridesTransform;
import mx.sugus.braid.plugins.data.producers.EnumJavaProducer;
import mx.sugus.braid.plugins.data.producers.InterfaceFromFactoryOverridesTransform;
import mx.sugus.braid.plugins.data.producers.StructureInterfaceJavaProducer;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;
import mx.sugus.braid.plugins.data.producers.UnionJavaProducer;
import mx.sugus.braid.plugins.data.symbols.DataSymbolProviderDecorator;
import mx.sugus.braid.rt.util.annotations.Generated;
import software.amazon.smithy.model.node.ObjectNode;

public final class DataPlugin implements SmithyGeneratorPlugin {
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
    public CodegenModuleConfig moduleConfig(ObjectNode node) {
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
            .addSymbolProviderDecorator(DataSymbolProviderDecorator.get())
            .build();
    }

    public static Annotation generatedBy() {
        return GENERATED_BY;
    }
}
