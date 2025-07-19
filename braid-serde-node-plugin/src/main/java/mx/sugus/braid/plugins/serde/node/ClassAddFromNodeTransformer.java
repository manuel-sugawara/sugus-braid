package mx.sugus.braid.plugins.serde.node;

import java.time.Instant;
import java.util.Locale;
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
import mx.sugus.braid.jsyntax.Javadoc;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.SwitchStatement;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.rt.util.SinkValidator;
import mx.sugus.braid.rt.util.Validation;
import mx.sugus.braid.traits.ConstTrait;
import mx.sugus.braid.traits.JavaTrait;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.shapes.ListShape;
import software.amazon.smithy.model.shapes.MapShape;
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
            .addMethod(defaultFromNodeMethod(state))
            .addMethod(fromNodeMethod(state))
            .build();
        return result.toBuilder()
                     .syntax(syntax.toBuilder().type(classSyntax).build())
                     .build();
    }

    static MethodSyntax defaultFromNodeMethod(ShapeCodegenState state) {
        var className = Utils.toJavaTypeName(state, state.shape());
        var doc = Javadoc.builder()
                         .body("Deserialize a $T from a {@link Node}.", ClassName.toClassName(className))
                         .putParam("node", "The node to deserialize from.")
                         .returns("The deserialized instance.")
                         .build();
        var builder = MethodSyntax.builder("fromNode")
                                  .javadoc(doc)
                                  .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                  .addParameter(Node.class, "node")
                                  .returns(className);
        builder.addStatement("return fromNode($T.instance(), node)", SinkValidator.class);
        return builder.build();
    }

    static MethodSyntax fromNodeMethod(ShapeCodegenState state) {
        var className = Utils.toJavaTypeName(state, state.shape());
        var doc = Javadoc.builder()
                         .body("Deserialize a $T from a {@link Node}.", ClassName.toClassName(className))
                         .putParam("validator", "A validator to collect any issues found during deserialization.")
                         .putParam("node", "The node to deserialize from.")
                         .returns("The deserialized instance.")
                         .build();
        var builder = MethodSyntax.builder("fromNode")
                                  .javadoc(doc)
                                  .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                  .addParameter(Validation.class, "validator")
                                  .addParameter(Node.class, "node")
                                  .returns(className);
        var body = new BodyBuilder();
        body.addStatement("validator = validator.with($S)", state.shape().getId().getName());
        body.addStatement("$T.Builder builder = builder()", className);
        body.addStatement("$T obj = node.expectObjectNode()", ObjectNode.class);

        var switchBuilder = SwitchStatement.builder()
                                           .expression(CodeBlock.from("key"));
        for (var member : state.shape().members()) {
            var caseBuilder = CaseClause
                .builder()
                .addLabel(CodeBlock.from("$S", member.getMemberName()));

            if (member.hasTrait(ConstTrait.class)) {
                switchBuilder.addCase(caseBuilder
                                          .addStatement("break")
                                          .build());
                continue;
            }

            var target = state.model().expectShape(member.getTarget());
            var category = target.getType().getCategory();
            switch (category) {
                case AGGREGATE -> addAggregateMember(state, member, caseBuilder);
                case SIMPLE -> addSimpleMember(state, member, caseBuilder);
                default -> throw new RuntimeException("unsupported category: " + category);
            }
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

    static void addAggregateMember(ShapeCodegenState state, MemberShape member, CaseClause.Builder caseBuilder) {
        var target = state.model().expectShape(member.getTarget());
        switch (target.getType()) {
            case STRUCTURE, UNION -> addStructureMember(state, member, caseBuilder);
            case LIST -> addListMember(state, member, caseBuilder);
            case MAP -> addMapMember(state, member, caseBuilder);
            default -> throw new RuntimeException("unsupported aggregated type: " + target.getType());
        }
    }

    private static void addStructureMember(ShapeCodegenState state, MemberShape member, CaseClause.Builder body) {
        var target = state.model().expectShape(member.getTarget());
        if (target.hasTrait(JavaTrait.class)) {
            addJavaMember(state, member, body);
            return;
        }
        var targetType = Utils.toJavaTypeName(state, target);
        body.addStatement("builder.$L($T.fromNode(validator.with($S), value.expectObjectNode()))",
                          Utils.toSetterName(state, member), targetType, member.getMemberName());
    }

    private static void addJavaMember(ShapeCodegenState state, MemberShape member, CaseClause.Builder body) {
        var target = state.model().expectShape(member.getTarget());
        var targetType = Utils.toJavaTypeName(state, target);
        var actualClass = toActualJavaClass(ClassName.toClassName(targetType));
        if (Node.class.isAssignableFrom(actualClass)) {
            body.addStatement("builder.$L(value)",
                              Utils.toJavaName(state, member),
                              member.getMemberName());
            return;
        }
        if (!actualClass.isEnum()) {
            throw new RuntimeException("Node serde of non-enum types is not currently supported: " + actualClass);
        }
        body.addStatement("builder.$L($C)", Utils.toSetterName(state, member), valueFromNode("item", state, target, member));
    }

    private static void addListMember(ShapeCodegenState state, MemberShape member, CaseClause.Builder body) {
        var listShape = state.model().expectShape(member.getTarget()).asListShape().orElseThrow();
        var target = state.model().expectShape(listShape.getMember().getTarget());
        var aggregateType = Utils.aggregateType(state, target);
        var adder = Utils.toAdderName(state, member);
        body.beginControlFlow("for ($T lstNodeValue : value.expectArrayNode())", Node.class);
        switch (aggregateType) {
            case NONE -> {
                body.addStatement("builder.$L($C)", adder, valueFromNode("lstNodeValue", state, target, member));
            }
            case LIST, SET -> {
                var value = addNestedList(state, target.asListShape().orElseThrow(), body);
                body.addStatement("builder.$L($L)", adder, value);
            }
            case MAP -> {
                var value = addNestedMap(state, target.asMapShape().orElseThrow(), body);
                body.addStatement("builder.$L($L)", adder, value);
            }
        }
        body.endControlFlow();
    }

    private static String addNestedList(ShapeCodegenState state, ListShape target, CaseClause.Builder body) {
        return addNestedList(state, target, "lstNodeValue", 0, body);
    }

    private static String addNestedList(ShapeCodegenState state, ListShape shape, String source, int depth,
                                        CaseClause.Builder body) {
        var name = "lstMember" + (depth == 0 ? "" : Integer.toString(depth));
        var elementName = depth == 0 ? "innerNodeValue" : "innerNodeValue" + depth;
        var aggregateType = Utils.aggregateType(state, shape);
        var type = Utils.toJavaTypeName(state, shape);
        var member = shape.getMember();
        var target = state.model().expectShape(member.getTarget());
        var targetAggregateType = Utils.aggregateType(state, target);
        body.addStatement("$T $L = new $T<>()", type, name, Utils.concreteClassFor(aggregateType));
        body.beginControlFlow("for ($T $L : $L.expectArrayNode())", Node.class, elementName, source);
        switch (targetAggregateType) {
            case NONE -> {
                body.addStatement("$L.add($C)", name, valueFromNode(elementName, state, target, member));
            }
            case LIST, SET -> {
                var value = addNestedList(state, target.asListShape().orElseThrow(), elementName, depth + 1, body);
                body.addStatement("$L.add($L)", name, value);
            }
            case MAP -> {
                var value = addNestedMap(state, target.asMapShape().orElseThrow(), elementName, depth + 1, body);
                body.addStatement("$L.add($L)", name, value);
            }
        }
        body.endControlFlow();
        return name;
    }

    private static void addMapMember(ShapeCodegenState state, MemberShape member, CaseClause.Builder body) {
        var mapShape = state.model().expectShape(member.getTarget()).asMapShape().orElseThrow();
        var target = state.model().expectShape(mapShape.getValue().getTarget());
        var putter = Utils.toAdderName(state, member);
        body.beginControlFlow("for ($T memberKvp : value.expectObjectNode().getMembers().entrySet())",
                              ParameterizedTypeName.from(Map.Entry.class, StringNode.class, Node.class));
        body.addStatement("$T valueNode = memberKvp.getValue()", Node.class);
        var aggregateType = Utils.aggregateType(state, target);
        switch (aggregateType) {
            case NONE -> {
                body.addStatement("builder.$L(memberKvp.getKey().getValue(), $C)",
                                  putter, valueFromNode("valueNode", state, target, member));
            }
            case LIST, SET -> {
                var value = addNestedList(state, target.asListShape().orElseThrow(), "valueNode", 0, body);
                body.addStatement("builder.$L(memberKvp.getKey().getValue(), $L)",
                                  putter, value);
            }
            case MAP -> {
                var value = addNestedMap(state, target.asMapShape().orElseThrow(), body);
                body.addStatement("builder.$L(memberKvp.getKey().getValue(), $L)",
                                  putter, value);
            }
        }
        body.endControlFlow();
    }


    private static String addNestedMap(ShapeCodegenState state, MapShape target, CaseClause.Builder body) {
        return addNestedMap(state, target, "valueNode", 0, body);
    }

    private static String addNestedMap(ShapeCodegenState state, MapShape shape, String source, int depth,
                                       CaseClause.Builder body) {
        String suffix = depth == 0 ? "" : Integer.toString(depth);
        var name = "mapValue" + suffix;
        var entryName = "innerKvp" + suffix;
        var valueName = "innerValue" + suffix;
        var aggregateType = Utils.aggregateType(state, shape);
        var type = Utils.toJavaTypeName(state, shape);
        var member = shape.getValue();
        var target = state.model().expectShape(member.getTarget());
        var targetAggregateType = Utils.aggregateType(state, target);
        body.addStatement("$T $L = new $T<>()", type, name, Utils.concreteClassFor(aggregateType));
        body.beginControlFlow("for ($T $L : $L.expectObjectNode().getMembers().entrySet())",
                              ParameterizedTypeName.from(Map.Entry.class, StringNode.class, Node.class),
                              entryName, source);
        body.addStatement("$T $L = $L.getValue()", Node.class, valueName, entryName);
        switch (targetAggregateType) {
            case NONE -> {
                body.addStatement("$L.put($L.getKey().getValue(), $C)",
                                  name, entryName, valueFromNode("valueNode", state, target, member));
            }
            case LIST, SET -> {
                var value = addNestedList(state, target.asListShape().orElseThrow(), valueName, depth + 1, body);
                body.addStatement("$L.put($L.getKey().getValue(), $L)", name, entryName, value);
            }
            case MAP -> {
                var value = addNestedMap(state, target.asMapShape().orElseThrow(), valueName, depth + 1, body);
                body.addStatement("$L.put($L.getKey().getValue(), $L)", name, entryName, value);
            }
        }
        body.endControlFlow();
        return name;
    }

    static void addSimpleMember(ShapeCodegenState state, MemberShape member, CaseClause.Builder caseBuilder) {
        var target = state.model().expectShape(member.getTarget());

        if (target.isEnumShape()) {
            caseBuilder.addStatement("builder.$L($T.from(value.expectStringNode().getValue()))",
                                     Utils.toSetterName(state, member),
                                     Utils.toJavaTypeName(state, target),
                                     member.getMemberName());
        } else {
            caseBuilder.addStatement("builder.$L($C)",
                                     Utils.toSetterName(state, member),
                                     valueFromNode("value", state, target, member));
        }
    }

    private static CodeBlock valueFromNode(String nodeVar, ShapeCodegenState state, Shape target, MemberShape member) {
        var type = target.getType();
        return switch (type) {
            case STRUCTURE -> valueFromStructureNode(nodeVar, state, target, member);
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
            case TIMESTAMP -> CodeBlock.from("$T.parse($L.expectStringNode().getValue())",
                                             Instant.class, nodeVar);
            default -> CodeBlock.from("null /* $L */", target.getType());
        };
    }

    private static CodeBlock valueFromStructureNode(String nodeVar, ShapeCodegenState state, Shape target, MemberShape member) {
        if (target.hasTrait(JavaTrait.class)) {
            var targetType = ClassName.toClassName(Utils.toJavaTypeName(state, target));
            var actualClass = toActualJavaClass(targetType);
            if (!actualClass.isEnum()) {
                throw new RuntimeException("Node serde of non-enum types is not currently supported: " + actualClass);
            }
            return CodeBlock.from("$T.valueOf($L.expectStringNode().getValue().toUpperCase($T.US))",
                                  Utils.toJavaTypeName(state, target), nodeVar, Locale.class);
        }
        return CodeBlock.from("$T.fromNode(validator.with($S), $L)",
                              Utils.toJavaTypeName(state, target), member.getMemberName(), nodeVar);
    }

    static Class<?> toActualJavaClass(ClassName className) {
        try {
            return Class.forName(className.packageName() + "." + className.name());
        } catch (Exception e) {
            throw new RuntimeException("Cannot find the actual java class for: " + className);
        }
    }
}
