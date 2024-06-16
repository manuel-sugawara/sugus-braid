package mx.sugus.braid.plugins.serde.node;

import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.StructureInterfaceJavaProducer;
import software.amazon.smithy.model.node.ToNode;

public final class InterfaceAddToNodeTransformer implements ShapeTaskTransformer<TypeSyntaxResult> {

    public static final Identifier ID = Identifier.of(ClassAddFromNodeTransformer.class);

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
        var syntax = result.syntax();
        var interfaceSyntax = ((InterfaceSyntax) syntax.type())
            .toBuilder()
            .addSuperInterface(ToNode.class)
            .build();
        return result.toBuilder()
                     .syntax(syntax.toBuilder().type(interfaceSyntax).build())
                     .build();
    }
}
