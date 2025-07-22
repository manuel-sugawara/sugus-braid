package mx.sugus.braid.plugins.serde.node;

import java.util.Map;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.CodegenUtils;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.traits.ConstTrait;
import mx.sugus.braid.traits.JavaTrait;
import software.amazon.smithy.model.node.ArrayNode;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.ToNode;
import software.amazon.smithy.model.shapes.ListShape;
import software.amazon.smithy.model.shapes.MapShape;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;

public final class ClassAddToNodeTransformer implements ShapeTaskTransformer<TypeSyntaxResult> {

    public static final Identifier ID = Identifier.of(ClassAddToNodeTransformer.class);

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
                                  .addAnnotation(CodegenUtils.override())
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

    static void addAggregateMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var target = state.model().expectShape(member.getTarget());
        switch (target.getType()) {
            case STRUCTURE, UNION -> addStructureMember(state, member, body);
            case LIST -> addListMember(state, member, body);
            case MAP -> addMapMember(state, member, body);
            default -> throw new RuntimeException("unsupported aggregated type: " + target.getType());
        }
    }

    private static void addStructureMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var memberName = Utils.toJavaName(state, member);
        var target = state.model().expectShape(member.getTarget());
        var getterName = Utils.toGetterName(state, member);
        if (isNotNullable(state, member)) {
            body.addStatement("builder.withMember($S, $C)", member.getMemberName(),
                              valueToNode(getterName + "()", state, target));
        } else {
            body.ifStatement("$L != null", memberName, then ->
                then.addStatement("builder.withMember($S, $C)", member.getMemberName(),
                                  valueToNode(getterName + "()", state, target)));
        }
    }

    private static void addListMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var listShape = state.model().expectShape(member.getTarget()).asListShape().orElseThrow();
        var target = state.model().expectShape(listShape.getMember().getTarget());
        var targetType = Utils.toJavaTypeName(state, target);
        var memberField = Utils.toJavaName(state, member);
        var getterName = Utils.toGetterName(state, member) + "()";
        if (shouldSerializeEmptyCollections(state, member)) {
            body.beginControlFlow("if (!$L.isEmpty())", getterName);
        }
        body.addStatement("$1T.Builder $2LBuilder = $1T.builder()", ArrayNode.class, memberField);
        body.beginControlFlow("for ($T item : $L)", targetType, getterName);
        var aggregateType = Utils.aggregateType(state, target);
        switch (aggregateType) {
            case NONE -> {
                body.addStatement("$LBuilder.withValue($C)", memberField, valueToNode("item", state, target));
            }
            case LIST, SET -> {
                var element = addNestedListMember(state, target.asListShape().orElseThrow(), body);
                body.addStatement("$LBuilder.withValue($L)", memberField, element);
            }
            case MAP -> {
                var element = addNestedMapMember(state, target.asMapShape().orElseThrow(), body);
                body.addStatement("$LBuilder.withValue($L)", memberField, element);
            }
        }
        body.endControlFlow();
        body.addStatement("builder.withMember($S, $LBuilder.build())", member.getMemberName(), memberField);
        if (shouldSerializeEmptyCollections(state, member)) {
            body.endControlFlow();
        }
    }

    private static String addNestedListMember(ShapeCodegenState state, ListShape listShape, BodyBuilder body) {
        return addNestedListMember(state, listShape, "item", 0, body);
    }

    private static String addNestedListMember(ShapeCodegenState state, ListShape listShape, String source, int depth,
                                              BodyBuilder body) {
        var suffix = depth == 0 ? "" : Integer.toString(depth);
        var innerBuilderName = "innerBuilder" + suffix;
        var innerItemName = "innerItem" + suffix;
        var target = state.model().expectShape(listShape.getMember().getTarget());
        var targetType = Utils.toJavaTypeName(state, target);
        var aggregateType = Utils.aggregateType(state, target);
        body.addStatement("$1T.Builder $2L = $1T.builder()", ArrayNode.class, innerBuilderName);
        body.beginControlFlow("for ($T $L : $L)", targetType, innerItemName, source);
        switch (aggregateType) {
            case NONE -> {
                body.addStatement("$L.withValue($C)", innerBuilderName, valueToNode(innerItemName, state, target));
            }
            case LIST, SET -> {
                var element = addNestedListMember(state, target.asListShape().orElseThrow(), innerItemName, depth + 1, body);
                body.addStatement("$L.withValue($L)", innerBuilderName, element);
            }
            case MAP -> {
                var element = addNestedMapMember(state, target.asMapShape().orElseThrow(), innerItemName, depth + 1, body);
                body.addStatement("$L.withValue($L)", innerBuilderName, element);
            }
        }
        body.endControlFlow();
        return innerBuilderName + ".build()";
    }

    private static void addMapMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var mapShape = state.model().expectShape(member.getTarget()).asMapShape().orElseThrow();
        var target = state.model().expectShape(mapShape.getValue().getTarget());
        var targetType = Utils.toJavaTypeName(state, target);
        var memberField = Utils.toJavaName(state, member);
        var getterName = Utils.toGetterName(state, member) + "()";
        if (shouldSerializeEmptyCollections(state, member)) {
            body.beginControlFlow("if (!this.$L.isEmpty())", memberField);
        }
        body.addStatement("$1T.Builder $2LBuilder = $1T.builder()", ObjectNode.class, memberField);
        body.beginControlFlow("for ($T<$T, $T> kvp : $L.entrySet())",
                              Map.Entry.class, String.class, targetType, getterName);
        var aggregateType = Utils.aggregateType(state, target);
        switch (aggregateType) {
            case NONE -> {
                body.addStatement("$LBuilder.withMember(kvp.getKey(), $C)", memberField,
                                  valueToNode("kvp.getValue()", state, target));
            }
            case LIST, SET -> {
                var element = addNestedListMember(state, target.asListShape().orElseThrow(), "kvp.getValue()", 0, body);
                body.addStatement("$LBuilder.withMember(kvp.getKey(), $L)", memberField, element);
            }
            case MAP -> {
                var element = addNestedMapMember(state, target.asMapShape().orElseThrow(), body);
                body.addStatement("$LBuilder.withMember(kvp.getKey(), $L)", memberField, element);
            }
        }
        body.endControlFlow();
        body.addStatement("builder.withMember($S, $LBuilder.build())", member.getMemberName(), memberField);
        if (shouldSerializeEmptyCollections(state, member)) {
            body.endControlFlow();
        }
    }

    private static String addNestedMapMember(ShapeCodegenState state, MapShape mapShape, BodyBuilder body) {
        return addNestedMapMember(state, mapShape, "kvp.getValue()", 0, body);
    }

    private static String addNestedMapMember(ShapeCodegenState state, MapShape mapShape, String source, int depth,
                                             BodyBuilder body) {
        var suffix = depth == 0 ? "" : Integer.toString(depth);
        var innerBuilderName = "innerBuilder" + suffix;;
        var innerKvpName = "innerKvp" + suffix;;
        var target = state.model().expectShape(mapShape.getValue().getTarget());
        var targetType = Utils.toJavaTypeName(state, target);
        var aggregateType = Utils.aggregateType(state, target);
        body.addStatement("$1T.Builder $2L = $1T.builder()", ObjectNode.class, innerBuilderName);
        body.beginControlFlow("for ($T<$T, $T> $L : $L.entrySet())",
                              Map.Entry.class, String.class, targetType, innerKvpName, source);
        var innerValue = innerKvpName + ".getValue()";
        switch (aggregateType) {
            case NONE -> {
                body.addStatement("$L.withMember(kvp.getKey(), $C)", innerBuilderName,
                                  valueToNode("kvp.getValue()", state, target));
            }
            case LIST, SET -> {
                var element = addNestedListMember(state, target.asListShape().orElseThrow(), innerValue, depth + 1, body);
                body.addStatement("$L.withMember($L.getKey(), $L)", innerBuilderName, innerKvpName, element);
            }
            case MAP -> {
                var element = addNestedMapMember(state, target.asMapShape().orElseThrow(), innerValue, depth + 1, body);
                body.addStatement("$L.withMember($L.getKey(), $L)", innerBuilderName, innerKvpName, element);
            }
        }
        body.endControlFlow();
        return innerBuilderName + ".build()";
    }

    static void addSimpleMember(ShapeCodegenState state, MemberShape member, BodyBuilder body) {
        var target = state.model().expectShape(member.getTarget());
        var getterName = Utils.toGetterName(state, member) + "()";
        if (isNotNullable(state, member)) {
            body.addStatement("builder.withMember($S, $C)",
                    member.getMemberName(), valueToNode(getterName, state, target));
        } else {
            var memberName = Utils.toJavaName(state, member);
            body.ifStatement("$L != null", memberName, then ->
                    then.addStatement("builder.withMember($S, $C)",
                            member.getMemberName(), valueToNode(getterName, state, target)));
        }
    }

    private static CodeBlock valueToNode(String source, ShapeCodegenState state, Shape target) {
        var type = target.getType();
        return switch (type) {
            case STRUCTURE, UNION -> structureValueToNode(source, state, target);
            case STRING,
                 BYTE, SHORT, INTEGER, INT_ENUM, LONG,
                 FLOAT, DOUBLE,
                 BOOLEAN -> CodeBlock.from("$T.from($L)", Node.class, source);
            case BIG_INTEGER,
                 BIG_DECIMAL, TIMESTAMP,
                 ENUM -> CodeBlock.from("$T.from($L.toString())", Node.class, source);
            default -> CodeBlock.from("null /* $L */", target.getType());
        };
    }

    private static CodeBlock structureValueToNode(String source, ShapeCodegenState state, Shape target) {
        if (target.hasTrait(JavaTrait.class)) {
            var targetType = ClassName.toClassName(Utils.toJavaTypeName(state, target));
            var actualClass = ClassAddFromNodeTransformer.toActualJavaClass(targetType);
            if (Node.class.isAssignableFrom(actualClass)) {
                return CodeBlock.from("$L", source);
            }
            if (!actualClass.isEnum()) {
                throw new RuntimeException("Node serde of non-enum types is not currently supported: " + actualClass);
            }
            return CodeBlock.from("$L.toString()", source);
        }

        return CodeBlock.from("$L.toNode()", source);
    }

    private static boolean isNotNullable(ShapeCodegenState state, MemberShape member) {
        return Utils.isRequired(state, member)
               || member.hasTrait(ConstTrait.class)
               || state.shape().isUnionShape();
    }

    private static boolean shouldSerializeEmptyCollections(ShapeCodegenState state, MemberShape member) {
        return !state.shape().isUnionShape();
    }
}
