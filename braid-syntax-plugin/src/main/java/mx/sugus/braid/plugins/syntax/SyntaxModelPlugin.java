package mx.sugus.braid.plugins.syntax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.plugins.data.DataPlugin;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;

public final class SyntaxModelPlugin implements SmithyGeneratorPlugin {
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
    public CodegenModuleConfig moduleConfig(ObjectNode node) {
        return newBaseConfig(configuredSyntaxNodes(node)).build();
    }

    static CodegenModuleConfig.Builder newBaseConfig(List<String> syntaxNodes) {
        var builder = CodegenModuleConfig.builder();
        for (var syntaxNode : syntaxNodes) {
            builder.addProducer(new SyntaxVisitorJavaProducer(syntaxNode))
                   .addProducer(new SyntaxWalkVisitorJavaProducer(syntaxNode))
                   .addTransformer(new InterfaceSyntaxAddAcceptVisitorTransformer(syntaxNode))
                   .addTransformer(new SyntaxAddAcceptVisitorTransformer(syntaxNode));
        }
        return builder;
    }

    static List<String> configuredSyntaxNodes(ObjectNode node) {
        var syntaxNode = node.getStringMember("syntaxNode").map(StringNode::getValue).orElse(null);
        var syntaxNodes = new ArrayList<String>();
        if (syntaxNode != null) {
            syntaxNodes.add(syntaxNode);
        }
        node.getArrayMember("syntaxNodes").ifPresent(n -> {
            for (var element : n.getElements()) {
                syntaxNodes.add(element.expectStringNode().getValue());
            }
        });
        return Collections.unmodifiableList(syntaxNodes);
    }
}
