package mx.sugus.braid.plugins.serde.node;

import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.ImplementsKnowledgeIndex;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.core.plugin.TypeSyntaxResult;
import mx.sugus.braid.jsyntax.CaseClause;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.DefaultCaseClause;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.SwitchStatement;
import mx.sugus.braid.plugins.data.producers.StructureInterfaceJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.traits.ConstTrait;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.ShapeId;

public final class InterfaceAddFromNodeTransformer implements ShapeTaskTransformer<TypeSyntaxResult> {

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
            .addMethod(fromNodeMethodForInterface(state))
            .build();
        return result.toBuilder()
                     .syntax(syntax)
                     .build();
    }

    private MethodSyntax fromNodeMethodForInterface(ShapeCodegenState state) {
        var isaKnowledgeIndex = ImplementsKnowledgeIndex.of(state.model());
        var shape = state.shape().asStructureShape().orElseThrow();
        var dispatchMember = isaKnowledgeIndex.polymorphicDispatchMember(shape);
        var className = Utils.toJavaTypeName(state, state.shape());
        var builder = MethodSyntax.builder("fromNode")
                                  .addModifier(Modifier.STATIC)
                                  .addParameter(Node.class, "node")
                                  .returns(className);
        if (dispatchMember == null) {
            return withoutDispatchMember(state, builder);
        }
        var table = isaKnowledgeIndex.polymorphicDispatchTable(shape);
        var symbolProvider = state.symbolProvider();
        var dispatchTypeName = Utils.toJavaTypeName(state, dispatchMember);
        var memberName = Utils.toJavaName(state, dispatchMember);
        builder.addStatement("$T objectNode = node.expectObjectNode()", ObjectNode.class);
        builder.addStatement("$1T $2L = $1T.from(objectNode.expectStringMember($2S).getValue())", dispatchTypeName,
                             memberName);
        var switchStatement = SwitchStatement.builder()
                                             .expression(CodeBlock.from("$L", memberName));
        table.forEach((member, structureShape) -> {
            var refId = member.getTrait(ConstTrait.class).map(ConstTrait::getValue).orElse("");
            var shapeId = ShapeId.from(refId);
            var constMember = state.model().expectShape(shapeId, MemberShape.class);
            var caseType = Utils.toJavaTypeName(state, structureShape);
            symbolProvider.toMemberName(constMember);
            switchStatement.addCase(CaseClause.builder()
                                              .addLabel(CodeBlock.from("$L", symbolProvider.toMemberName(constMember)))
                                              .body(b -> b.addStatement("return $T.fromNode(node)", caseType))
                                              .build());
        });
        switchStatement.defaultCase(DefaultCaseClause.builder()
                                                     .addStatement("throw new $T($S + $L)",
                                                                   IllegalArgumentException.class,
                                                                   "Unknown enum variant: ",
                                                                   memberName)
                                                     .build());
        builder.addStatement(switchStatement.build());
        return builder.build();
    }

    private MethodSyntax withoutDispatchMember(ShapeCodegenState state, MethodSyntax.Builder builder) {
        var implementsKnowledgeIndex = ImplementsKnowledgeIndex.of(state.model());
        var implementers = implementsKnowledgeIndex.implementers(state.shape().asStructureShape().orElseThrow());
        if (implementers != null && implementers.size() == 1) {
            var implementer = implementers.iterator().next();
            builder.addStatement("return $T.fromNode(node)", Utils.toJavaTypeName(state, implementer));

        } else {
            builder.addStatement("throw new $T()", UnsupportedOperationException.class);
        }
        return builder.build();
    }

}
