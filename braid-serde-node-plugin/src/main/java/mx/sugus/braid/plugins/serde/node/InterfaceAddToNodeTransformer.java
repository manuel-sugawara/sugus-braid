package mx.sugus.braid.plugins.serde.node;

import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.core.plugin.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.StructureInterfaceJavaProducer;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import software.amazon.smithy.model.node.ToNode;

public final class InterfaceAddToNodeTransformer implements ShapeTaskTransformer<TypeSyntaxResult> {

    public static Identifier ID = Identifier.of(ClassAddFromNodeTransformer.class);

    @Override
    public Identifier taskId() {
        return ID;
    }

    @Override
    public Identifier transformsId() {
        return StructureInterfaceJavaProducer.ID;
    }

    @Override
    public TypeSyntaxResult transform(TypeSyntaxResult result, ShapeCodegenState state) {
        var syntax = ((InterfaceSyntax) result.syntax())
            .toBuilder()
            .addSuperInterface(ToNode.class)
            .build();
        return result.toBuilder()
                     .syntax(syntax)
                     .build();
    }
}
