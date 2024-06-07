package mx.sugus.braid.plugins.serde.node;

import java.util.Map;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.core.plugin.TypeSyntaxResult;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.plugins.data.StructureJavaProducer;
import mx.sugus.braid.plugins.data.Utils;
import mx.sugus.braid.traits.ConstTrait;
import mx.sugus.braid.traits.JavaTrait;
import software.amazon.smithy.model.node.ArrayNode;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.ToNode;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;

public final class ClassAddToNodeTransformer implements ShapeTaskTransformer<TypeSyntaxResult> {

    public static Identifier ID = Identifier.of(ClassAddToNodeTransformer.class);

    @Override
    public Identifier taskId() {
        return ID;
    }

    @Override
    public Identifier transformsId() {
        return StructureJavaProducer.ID;
    }

    @Override
    public TypeSyntaxResult transform(TypeSyntaxResult result, ShapeCodegenState state) {
        var syntax = ((ClassSyntax) result.syntax())
            .toBuilder()
            .addSuperInterface(ToNode.class)
            .addMethod(toNodeMethod(state))
            .build();
        return result.toBuilder()
                     .syntax(Utils.addGeneratedBy(syntax, NodeSerdePlugin.ID))
                     .build();
    }

    private MethodSyntax toNodeMethod(ShapeCodegenState state) {
        var javadoc = "Converts this instance to Node";
        var builder = MethodSyntax.builder("toNode")
                                  .javadoc(JavadocExt.document(javadoc))
                                  .addAnnotation(Override.class)
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(Node.class);
        var body = new BodyBuilder();
        body.addStatement("$T.Builder builder = $T.objectNodeBuilder()", ObjectNode.class, Node.class);
        for (var member : state.shape().members()) {
            var target = state.model().expectShape(member.getTarget());
            var category = target.getType().getCategory();
            switch (category) {
                case AGGREGATE -> addAggregateMember(state, member, body);
                case SIMPLE -> addSimpleMember(state, member, body);
                default -> throw new RuntimeException("unsupported category: " + category);
            }
        }
        body.addStatement("return builder.build()");
        builder.body(body.build());
        return builder.build();
    }

    private void addAggregateMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var target = state.model().expectShape(member.getTarget());
        switch (target.getType()) {
            case STRUCTURE -> addStructureMember(state, member, body);
            case LIST -> addListMember(state, member, body);
            case MAP -> addMapMember(state, member, body);
            default -> throw new RuntimeException("unsupported aggregated type: " + target.getType());
        }
    }

    private void addStructureMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var symbolProvider = state.symbolProvider();
        var memberName = Utils.toJavaName(state, member);
        if (Utils.isMemberRequired(state, member)) {
            body.addStatement("builder.withMember($S, this.$L.toNode())", member.getMemberName(), memberName);
        } else {
            body.ifStatement("$L != null", memberName, then ->
                then.addStatement("builder.withMember($S, this.$L.toNode())", member.getMemberName(), memberName));
        }
    }

    private void addListMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var listShape = state.model().expectShape(member.getTarget()).asListShape().orElseThrow();
        var target = state.model().expectShape(listShape.getMember().getTarget());
        var targetType = Utils.toJavaTypeName(state, target);
        var memberField = Utils.toJavaName(state, member);
        body.ifStatement("!this.$L.isEmpty()", memberField, then -> {
            then.addStatement("$1T.Builder $2LBuilder = $1T.builder()", ArrayNode.class, memberField);
            then.forStatement("$T item : this.$L", targetType, memberField, b -> {
                b.addStatement("$LBuilder.withValue($C)", memberField, valueToNode("item", state, target));
            });
            then.addStatement("builder.withMember($S, $LBuilder.build())", member.getMemberName(), memberField);
        });
    }

    private void addMapMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var listShape = state.model().expectShape(member.getTarget()).asMapShape().orElseThrow();
        var target = state.model().expectShape(listShape.getValue().getTarget());
        var targetType = Utils.toJavaTypeName(state, target);
        var memberField = Utils.toJavaName(state, member);
        body.addStatement("$1T.Builder $2LBuilder = $1T.builder()", ObjectNode.class, memberField);
        var forInit = CodeBlock.from("$T<$T, $T> kvp : this.$L.entrySet()",
                                     Map.Entry.class, String.class, targetType, memberField);
        body.forStatement(forInit, b -> {
            b.addStatement("$LBuilder.withMember(kvp.getKey(), $C)",
                           memberField,
                           valueToNode("kvp.getValue()", state, target));
        });

        body.addStatement("builder.withMember($S, $LBuilder.build())", member.getMemberName(), memberField);
    }

    private void addSimpleMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var symbolProvider = state.symbolProvider();
        var target = state.model().expectShape(member.getTarget());
        var memberField = "this." + Utils.toJavaName(state, member);

        if (Utils.isMemberRequired(state, member)) {
            if (member.hasTrait(ConstTrait.class)) {
                body.addStatement("builder.withMember($S, $C)",
                                  member.getMemberName(), valueToNode(memberField + "()", state, target));
            } else {
                body.addStatement("builder.withMember($S, $C)",
                                  member.getMemberName(), valueToNode(memberField, state, target));
            }
        } else {
            body.ifStatement("$L != null", memberField, then -> {
                then.addStatement("builder.withMember($S, $C)",
                                  member.getMemberName(), valueToNode(memberField, state, target));
            });
        }
    }

    private CodeBlock valueToNode(String source, ShapeCodegenState state, Shape target) {
        var type = target.getType();
        return switch (type) {
            case STRUCTURE -> structureValueToNode(source, state, target);
            case STRING,
                 BYTE, SHORT, INTEGER, INT_ENUM, LONG,
                 FLOAT, DOUBLE,
                 BOOLEAN -> CodeBlock.from("$T.from($L)", Node.class, source);
            case BIG_INTEGER,
                 BIG_DECIMAL,
                 ENUM -> CodeBlock.from("$T.from($L.toString())", Node.class, source);
            default -> CodeBlock.from("null /* $L */", target.getType());
        };
    }

    private CodeBlock structureValueToNode(String source, ShapeCodegenState state, Shape target) {
        if (target.hasTrait(JavaTrait.class)) {
            var targetType = ClassName.toClassName(Utils.toJavaTypeName(state, target));
            var actualClass = ClassAddFromNodeTransformer.toActualJavaClass(targetType);
            if (!actualClass.isEnum()) {
                throw new RuntimeException("Node serde of non-enum types is not currently supported: " + actualClass);
            }
            return CodeBlock.from("$L.toString()", source);
        }

        return CodeBlock.from("$L.toNode()", source);
    }
}
