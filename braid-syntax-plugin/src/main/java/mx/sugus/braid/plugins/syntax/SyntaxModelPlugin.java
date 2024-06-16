package mx.sugus.braid.plugins.syntax;

import java.util.Collection;
import java.util.List;
import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.plugins.data.DataPlugin;
import mx.sugus.braid.plugins.syntax.config.SyntaxModelPluginConfig;
import software.amazon.smithy.model.node.ObjectNode;

public final class SyntaxModelPlugin implements SmithyGeneratorPlugin<SyntaxModelPluginConfig> {
    public static final Identifier ID = Identifier.of(SyntaxModelPlugin.class);

    public SyntaxModelPlugin() {
    }

    @Override
    public Identifier provides() {
        return ID;
    }

    @Override
    public Collection<Identifier> requires() {
        return List.of(DataPlugin.ID);
    }

    @Override
    public SyntaxModelPluginConfig fromNode(ObjectNode node) {
        return SyntaxModelPluginConfig.fromNode(node);
    }

    @Override
    public CodegenModuleConfig moduleConfig(SyntaxModelPluginConfig config) {
        var builder = CodegenModuleConfig.builder();
        for (var syntaxNode : config.syntaxNodes()) {
            builder.addProducer(new SyntaxVisitorJavaProducer(syntaxNode))
                   .addProducer(new SyntaxWalkVisitorJavaProducer(syntaxNode))
                   .addProducer(new SyntaxRewriteVisitorJavaProducer(syntaxNode))
                   .addTransformer(new InterfaceSyntaxAddAcceptVisitorTransformer(syntaxNode))
                   .addTransformer(new SyntaxAddAcceptVisitorTransformer(syntaxNode));
        }
        return builder.build();
    }
}
