package mx.sugus.braid.jsyntax.plugins;

import java.util.Collection;
import java.util.List;
import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.plugins.data.DataPlugin;
import software.amazon.smithy.model.node.ObjectNode;

public class JavaSyntaxModelPlugin implements SmithyGeneratorPlugin {
    public static final Identifier ID = Identifier.of(JavaSyntaxModelPlugin.class);

    public JavaSyntaxModelPlugin() {
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
        return newBaseConfig().build();
    }

    static CodegenModuleConfig.Builder newBaseConfig() {
        var builder = CodegenModuleConfig.builder()
                                         .addTransformer(new BlockBuilderTransformer());
        return builder;
    }
}
