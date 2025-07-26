package mx.sugus.braid.plugins.serde.node;

import static mx.sugus.braid.plugins.data.producers.UnionVariantData.memberVariantName;
import static mx.sugus.braid.plugins.serde.node.ClassAddToNodeTransformer.addAggregateMember;
import static mx.sugus.braid.plugins.serde.node.ClassAddToNodeTransformer.addSimpleMember;

import java.util.ArrayList;
import java.util.HashMap;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.Modifier;
import mx.sugus.braid.jsyntax.TypeSyntax;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.CodegenUtils;
import mx.sugus.braid.plugins.data.producers.UnionJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.ToNode;
import software.amazon.smithy.model.shapes.MemberShape;


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
        var classSyntax = (ClassSyntax) syntax.type();
        var classNameToMember = new HashMap<String, MemberShape>();
        for (var member : state.shape().members()) {
            classNameToMember.put(memberVariantName(state, member).toString(), member);
        }
        var innerTypes = new ArrayList<TypeSyntax>();
        for (var innerType : classSyntax.innerTypes()) {
            var innerTypeName = innerType.name();
            MemberShape member = classNameToMember.get(innerTypeName);
            if (member != null) {
                innerTypes.add(transformClass((ClassSyntax) innerType, member, state));
            } else if (innerTypeName.equals("$UnknownVariant")) {
                innerTypes.add(transformUnknownVariantClass((ClassSyntax) innerType, state));
            } else {
                innerTypes.add(innerType);
            }
        }
        classSyntax = classSyntax
            .toBuilder()
            .addSuperInterface(ToNode.class)
            .innerTypes(innerTypes)
            .build();
        return result.toBuilder()
                     .syntax(syntax.toBuilder().type(Utils.addGeneratedBy(classSyntax, NodeSerdePlugin.ID)).build())
                     .build();
    }

    public ClassSyntax transformClass(ClassSyntax classSyntax, MemberShape member, ShapeCodegenState state) {
        var typeSyntax = classSyntax.toBuilder()
                                    .addMethod(toNodeMethod(state, member))
                                    .build();
        return (ClassSyntax) Utils.addGeneratedBy(typeSyntax, NodeSerdePlugin.ID);
    }

    private TypeSyntax transformUnknownVariantClass(ClassSyntax innerType, ShapeCodegenState state) {
        var javadoc = "Converts this instance to Node.";
        var toNode = MethodSyntax.builder("toNode")
                                 .javadoc(JavadocExt.document(javadoc))
                                 .addAnnotation(CodegenUtils.override())
                                 .addModifier(Modifier.PUBLIC)
                                 .returns(Node.class)
                                 .addStatement("throw new $T($S)",
                                               UnsupportedOperationException.class,
                                               "Unknown variant cannot be serialized")
                                 .build();
        var typeSyntax = innerType.toBuilder()
                                  .addMethod(toNode)
                                  .build();
        return Utils.addGeneratedBy(typeSyntax, NodeSerdePlugin.ID);
    }

    static MethodSyntax toNodeMethod(ShapeCodegenState state, MemberShape member) {
        var javadoc = "Converts this instance to Node.";
        var builder = MethodSyntax.builder("toNode")
                                  .javadoc(JavadocExt.document(javadoc))
                                  .addAnnotation(CodegenUtils.override())
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(Node.class);
        var body = new BodyBuilder();
        body.addStatement("$T.Builder builder = $T.objectNodeBuilder()", ObjectNode.class, Node.class);
        var target = state.model().expectShape(member.getTarget());
        var category = target.getType().getCategory();
        switch (category) {
            case AGGREGATE -> addAggregateMember(state, member, body);
            case SIMPLE -> addSimpleMember(state, member, body);
            default -> throw new RuntimeException("unsupported category: " + category);
        }
        body.addStatement("return builder.build()");
        builder.body(body.build());
        return builder.build();
    }

}
