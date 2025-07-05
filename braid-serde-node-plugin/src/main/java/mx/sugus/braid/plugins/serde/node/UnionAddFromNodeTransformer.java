package mx.sugus.braid.plugins.serde.node;

import static mx.sugus.braid.plugins.serde.node.ClassAddFromNodeTransformer.addAggregateMember;
import static mx.sugus.braid.plugins.serde.node.ClassAddFromNodeTransformer.addSimpleMember;
import static mx.sugus.braid.plugins.serde.node.ClassAddFromNodeTransformer.defaultFromNodeMethod;

import java.util.Map;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.CaseClause;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.DefaultCaseClause;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.SwitchStatement;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.UnionJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.rt.util.Validation;
import mx.sugus.braid.traits.ConstTrait;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;

public final class UnionAddFromNodeTransformer implements ShapeTaskTransformer<TypeSyntaxResult> {

    public static final Identifier ID = Identifier.of(UnionAddFromNodeTransformer.class);

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
            .addMethod(defaultFromNodeMethod(state))
            .addMethod(fromNodeMethod(state))
            .build();
        return result.toBuilder()
                     .syntax(syntax.toBuilder().type(classSyntax).build())
                     .build();
    }

    static MethodSyntax fromNodeMethod(ShapeCodegenState state) {
        var className = Utils.toJavaTypeName(state, state.shape());
        var javadoc = "Converts a {@link Node} to " + ClassName.toClassName(className).name() + ".";
        var builder = MethodSyntax.builder("fromNode")
                                  .javadoc(JavadocExt.document(javadoc))
                                  .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                  .addParameter(Validation.class, "validator")
                                  .addParameter(Node.class, "node")
                                  .returns(className);
        var body = new BodyBuilder();
        body.addStatement("String variantSet = null");
        body.addStatement("validator = validator.with($S)", state.shape().getId().getName());
        body.addStatement("$T.Builder builder = builder()", className);
        body.addStatement("$T obj = node.expectObjectNode()", ObjectNode.class);

        var switchBuilder = SwitchStatement.builder()
                                           .expression(CodeBlock.from("key"));
        for (var member : state.shape().members()) {
            var caseBuilder = CaseClause
                .builder()
                .addLabel(CodeBlock.from("$S", member.getMemberName()));

            var target = state.model().expectShape(member.getTarget());
            var category = target.getType().getCategory();
            caseBuilder.beginControlFlow("if (variantSet == null)");
            switch (category) {
                case AGGREGATE -> addAggregateMember(state, member, caseBuilder);
                case SIMPLE -> addSimpleMember(state, member, caseBuilder);
                default -> throw new RuntimeException("unsupported category: " + category);
            }
            caseBuilder.addStatement("variantSet = $S", member.getMemberName());
            caseBuilder.nextControlFlow("else");
            caseBuilder.addStatement("$T variant = variantSet", String.class);
            caseBuilder.addStatement("validator.report($T.ERROR, key, () -> $T.format($S, key, value, variant))",
                                     Validation.Severity.class, String.class,
                                     "ignoring extra variant `%s` for union with value `%s`, keeping `%s`");
            caseBuilder.endControlFlow();
            switchBuilder.addCase(caseBuilder
                                      .addStatement("break")
                                      .build());
        }
        switchBuilder.defaultCase(DefaultCaseClause
                                      .builder()
                                      .addStatement("validator.report($T.WARNING, key, () -> $T.format($S, key, value))",
                                                    Validation.Severity.class, String.class,
                                                    "unknown key `%s` with value `%s`")
                                      .addStatement("break")
                                      .build());
        body.forStatement("$T kvp : obj.getMembers().entrySet()",
                          ParameterizedTypeName.from(Map.Entry.class, StringNode.class, Node.class), b -> {
                b.addStatement("$T value = kvp.getValue()", Node.class);
                b.addStatement("$T key = kvp.getKey().getValue()", String.class);
                b.addStatement(switchBuilder.build());
            });
        body.addStatement("return builder.build()");
        builder.body(body.build());
        return builder.build();
    }


}
