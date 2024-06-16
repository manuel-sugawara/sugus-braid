package mx.sugus.braid.plugins.serde.node;

import java.util.Locale;
import java.util.Map;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.traits.ConstTrait;
import mx.sugus.braid.traits.JavaTrait;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;

public final class ClassAddFromNodeTransformer implements ShapeTaskTransformer<TypeSyntaxResult> {

    public static final Identifier ID = Identifier.of(ClassAddFromNodeTransformer.class);

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
        var syntax = result.syntax();
        var classSyntax = ((ClassSyntax) syntax.type())
            .toBuilder()
            .addMethod(fromNodeMethod(state))
            .build();
        return result.toBuilder()
                     .syntax(syntax.toBuilder().type(classSyntax).build())
                     .build();
    }

    private MethodSyntax fromNodeMethod(ShapeCodegenState state) {
        var className = Utils.toJavaTypeName(state, state.shape());
        var javadoc = "Converts a Node to " + ClassName.toClassName(className).name();
        var builder = MethodSyntax.builder("fromNode")
                                  .javadoc(JavadocExt.document(javadoc))
                                  .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                  .addParameter(Node.class, "node")
                                  .returns(className);
        BodyBuilder body = new BodyBuilder();
        body.addStatement("$T.Builder builder = builder()", className);
        body.addStatement("$T obj = node.expectObjectNode()", ObjectNode.class);
        for (var member : state.shape().members()) {
            if (member.hasTrait(ConstTrait.class)) {
                continue;
            }
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
        var target = state.model().expectShape(member.getTarget());
        if (target.hasTrait(JavaTrait.class)) {
            addJavaMember(state, member, body);
            return;
        }
        var targetType = Utils.toJavaTypeName(state, target);
        if (Utils.isExplicitlyRequired(state, member)) {
            body.addStatement("builder.$L($T.fromNode(obj.expectMember($S).expectObjectNode()))",
                              Utils.toSetterName(state, member), targetType, member.getMemberName());
        } else {
            body.addStatement("obj.getMember($S).map($T::fromNode).ifPresent(builder::$L)",
                              member.getMemberName(), targetType, Utils.toSetterName(state, member));
        }
    }

    private void addJavaMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var target = state.model().expectShape(member.getTarget());
        var targetType = Utils.toJavaTypeName(state, target);
        var actualClass = toActualJavaClass(ClassName.toClassName(targetType));
        if (!actualClass.isEnum()) {
            throw new RuntimeException("Node serde of non-enum types is not currently supported: " + actualClass);
        }
        if (Utils.isRequired(state, member)) {
            body.addStatement("builder.$L($T.valueOf(obj.expectMember($S).expectStringNode().getValue()))",
                              Utils.toJavaName(state, member),
                              Utils.toJavaTypeName(state, target),
                              member.getMemberName());
        } else {
            body.addStatement("obj.getMember($S)"
                              + ".map(n -> n.expectStringNode().getValue())"
                              + ".map($T::valueOf).ifPresent(builder::$L)",
                              member.getMemberName(), targetType, Utils.toSetterName(state, member));
        }
    }

    private void addListMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var listShape = state.model().expectShape(member.getTarget()).asListShape().orElseThrow();
        var target = state.model().expectShape(listShape.getMember().getTarget());
        var adder = Utils.toAdderName(state, member);
        body.addStatement("obj.getArrayMember($S, nodes -> $B)",
                          member.getMemberName(),
                          BodyBuilder.create()
                                     .forStatement("$T item : nodes", Node.class, b ->
                                         b.addStatement("builder.$L($C)", adder, valueFromNode("item", state, target)))
                                     .build());
    }

    private void addMapMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var listShape = state.model().expectShape(member.getTarget()).asMapShape().orElseThrow();
        var target = state.model().expectShape(listShape.getValue().getTarget());
        var putter = Utils.toAdderName(state, member);
        var forInit = CodeBlock.from("$T kvp : objectNode.getMembers().entrySet()",
                                     ParameterizedTypeName.from(Map.Entry.class, StringNode.class, Node.class));
        body.addStatement("obj.getObjectMember($S, objectNode -> $B)",
                          member.getMemberName(),
                          BodyBuilder.create()
                                     .forStatement(forInit, b -> {
                                         b.addStatement("$T valueNode = kvp.getValue()", Node.class);
                                         b.addStatement("builder.$L(kvp.getKey().getValue(), $C)",
                                                        putter, valueFromNode("valueNode", state, target));
                                     })
                                     .build());
    }

    private void addSimpleMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var target = state.model().expectShape(member.getTarget());
        if (Utils.isRequired(state, member)) {
            if (target.isEnumShape()) {
                addRequiredEnumMember(state, member, body);
                return;
            }
            body.addStatement("builder.$L(obj.expectMember($S)$C)",
                              Utils.toSetterName(state, member),
                              member.getMemberName(),
                              valueFromNode("", state, target));
        } else {
            body.addStatement("obj.getMember($S).map(n -> $C).ifPresent(builder::$L)",
                              member.getMemberName(),
                              valueFromNode("n", state, target),
                              Utils.toSetterName(state, member));
        }
    }

    private void addRequiredEnumMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var target = state.model().expectShape(member.getTarget());
        body.addStatement("builder.$L($T.from(obj.expectMember($S).expectStringNode().getValue()))",
                          Utils.toSetterName(state, member),
                          Utils.toJavaTypeName(state, target),
                          member.getMemberName());
    }

    private CodeBlock valueFromNode(String nodeVar, ShapeCodegenState state, Shape target) {
        var type = target.getType();
        return switch (type) {
            case STRUCTURE -> valueFromStructureNode(nodeVar, state, target);
            case STRING -> CodeBlock.from("$L.expectStringNode().getValue()", nodeVar);
            case BYTE -> CodeBlock.from("$L.expectNumberNode().getValue().byteValue()", nodeVar);
            case SHORT -> CodeBlock.from("$L.expectNumberNode().getValue().shortValue()", nodeVar);
            case INTEGER, INT_ENUM -> CodeBlock.from("$L.expectNumberNode().getValue().intValue()", nodeVar);
            case LONG -> CodeBlock.from("$L.expectNumberNode().getValue().longValue()", nodeVar);
            case BIG_INTEGER -> CodeBlock.from("$L.expectNumberNode().asBigDecimal().get().toBigInteger()", nodeVar);
            case FLOAT -> CodeBlock.from("$L.expectNumberNode().getValue().floatValue()", nodeVar);
            case DOUBLE -> CodeBlock.from("$L.expectNumberNode().getValue().doubleValue()", nodeVar);
            case BIG_DECIMAL -> CodeBlock.from("$L.expectNumberNode().asBigDecimal().get()", nodeVar);
            case BOOLEAN -> CodeBlock.from("$L.expectBooleanNode().getValue()", nodeVar);
            case ENUM -> CodeBlock.from("$T.from($L.expectStringNode().getValue())",
                                        Utils.toJavaTypeName(state, target), nodeVar);
            default -> CodeBlock.from("null /* $L */", target.getType());
        };
    }

    private CodeBlock valueFromStructureNode(String nodeVar, ShapeCodegenState state, Shape target) {
        if (target.hasTrait(JavaTrait.class)) {
            var targetType = ClassName.toClassName(Utils.toJavaTypeName(state, target));
            var actualClass = toActualJavaClass(targetType);
            if (!actualClass.isEnum()) {
                throw new RuntimeException("Node serde of non-enum types is not currently supported: " + actualClass);
            }
            return CodeBlock.from("$T.valueOf($L.expectStringNode().getValue().toUpperCase($T.US))",
                                  Utils.toJavaTypeName(state, target), nodeVar, Locale.class);
        }
        return CodeBlock.from("$T.fromNode($L)", Utils.toJavaTypeName(state, target), nodeVar);
    }

    static Class<?> toActualJavaClass(ClassName className) {
        try {
            return Class.forName(className.packageName() + "." + className.name());
        } catch (Exception e) {
            throw new RuntimeException("Cannot find the actual java class for: " + className);
        }
    }
}
