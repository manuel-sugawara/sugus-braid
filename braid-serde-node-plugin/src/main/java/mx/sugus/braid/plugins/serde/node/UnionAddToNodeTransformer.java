package mx.sugus.braid.plugins.serde.node;

import static mx.sugus.braid.plugins.serde.node.ClassAddToNodeTransformer.addAggregateMember;
import static mx.sugus.braid.plugins.serde.node.ClassAddToNodeTransformer.addSimpleMember;

import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.jsyntax.CaseClause;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.SwitchStatement;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.UnionJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.ToNode;


public final class UnionAddToNodeTransformer implements ShapeTaskTransformer<TypeSyntaxResult> {

    public static final Identifier ID = Identifier.of(UnionAddToNodeTransformer.class);

    @Override
    public Identifier taskId() {
        return ID;
    }

    @Override
    public Identifier transformsId() {
        return UnionJavaProducer.ID;
    }

    @Override
    public TypeSyntaxResult transform(TypeSyntaxResult result, ShapeCodegenState state) {
        var syntax = result.syntax();
        var classSyntax = ((ClassSyntax) syntax.type())
                .toBuilder()
                .addSuperInterface(ToNode.class)
                .addMethod(toNodeMethod(state))
                .build();
        return result.toBuilder()
                .syntax(syntax.toBuilder().type(Utils.addGeneratedBy(classSyntax, NodeSerdePlugin.ID)).build())
                .build();
    }

    static MethodSyntax toNodeMethod(ShapeCodegenState state) {
        var javadoc = "Converts this instance to Node.";
        var builder = MethodSyntax.builder("toNode")
                                  .javadoc(JavadocExt.document(javadoc))
                                  .addAnnotation(Override.class)
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(Node.class);
        var body = new BodyBuilder();
        body.addStatement("$T.Builder builder = $T.objectNodeBuilder()", ObjectNode.class, Node.class);
        var memberSwitch = SwitchStatement.builder()
                                          .expression(CodeBlock.from("this.variantTag"));
        for (var member : state.shape().members()) {
            var unionVariant = Utils.toSourceName(state, member, Name.Convention.SCREAM_CASE).toString();
            var memberCase = CaseClause.builder()
                      .addLabel(CodeBlock.from("$L", unionVariant));
            var memberBody = new BodyBuilder();
            var target = state.model().expectShape(member.getTarget());
            var category = target.getType().getCategory();
            switch (category) {
                case AGGREGATE -> addAggregateMember(state, member, memberBody);
                case SIMPLE -> addSimpleMember(state, member, memberBody);
                default -> throw new RuntimeException("unsupported category: " + category);
            }
            memberBody.addStatement("break");
            memberCase.body(memberBody.build());
            memberSwitch.addCase(memberCase.build());
        }
        body.addStatement(memberSwitch.build());
        body.addStatement("return builder.build()");
        builder.body(body.build());
        return builder.build();
    }

}
