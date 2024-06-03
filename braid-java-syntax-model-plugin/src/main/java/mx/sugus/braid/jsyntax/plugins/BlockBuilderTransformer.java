package mx.sugus.braid.jsyntax.plugins;

import java.util.ArrayList;
import java.util.function.Consumer;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.core.plugin.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.StructureJavaProducer;
import mx.sugus.braid.jsyntax.BaseMethodSyntax;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.Parameter;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.Statement;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.TypeSyntax;
import mx.sugus.braid.jsyntax.block.AbstractBlockBuilder;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.StructureShape;

public final class BlockBuilderTransformer implements ShapeTaskTransformer<TypeSyntaxResult> {

    private static final Identifier ID = Identifier.of(BlockBuilderTransformer.class);
    private static final ShapeId BLOCK_SHAPE_ID = ShapeId.fromParts("mx.sugus.braid.jsyntax", "Block");
    private static final ShapeId METHOD_SHAPE_ID = ShapeId.fromParts("mx.sugus.braid.jsyntax", "MethodSyntax");

    private TypeName blockConsumer = blockConsumer();

    @Override
    public Identifier taskId() {
        return ID;
    }

    @Override
    public Identifier transformsId() {
        return StructureJavaProducer.ID;
    }

    @Override
    public TypeSyntaxResult transform(TypeSyntaxResult result, ShapeCodegenState directive) {
        var type = result.syntax();
        var blockMemberName = blockMemberName(directive, directive.shape().asStructureShape().orElseThrow());
        if (blockMemberName == null) {
            return result;
        }
        var enhanced = enhanceType((ClassSyntax) type, blockMemberName, directive);
        return result.toBuilder().syntax(enhanced).build();
    }

    private TypeSyntax enhanceType(ClassSyntax type, String blockMemberName, ShapeCodegenState directive) {
        var innerTypes = new ArrayList<>(type.innerTypes());
        for (var idx = 0; idx < innerTypes.size(); idx++) {
            var innerType = innerTypes.get(idx);
            if (innerType.name().equals("Builder")) {
                var enhancedBuilder = enhanceBuilder((ClassSyntax) innerType, blockMemberName, directive);
                innerTypes.set(idx, enhancedBuilder);
            }
        }
        return type.toBuilder().innerTypes(innerTypes).build();
    }

    private TypeSyntax enhanceBuilder(ClassSyntax type, String blockMemberName, ShapeCodegenState directive) {
        var methods = new ArrayList<BaseMethodSyntax>();
        var notAdded = true;
        for (var method : type.methods()) {
            methods.add(method);
            if (method instanceof MethodSyntax m
                && m.name().equals(blockMemberName)
                && notAdded
            ) {
                methods.add(addStatement1(blockMemberName));
                methods.add(addStatement2(blockMemberName));
                methods.add(ifStatement1(blockMemberName));
                methods.add(ifStatement2(blockMemberName));
                methods.add(ifElseStatement1(blockMemberName));
                methods.add(ifElseStatement2(blockMemberName));
                methods.add(beginControlFlow(blockMemberName));
                methods.add(nextControlFlow(blockMemberName));
                methods.add(endControlFlow(blockMemberName));
                notAdded = false;
            }
        }
        return type.toBuilder()
                   .methods(methods)
                   .build();
    }

    private MethodSyntax addStatement1(String blockName) {
        return MethodSyntax.builder("addStatement")
                           .returns(ClassName.from("Builder"))
                           .addModifier(Modifier.PUBLIC)
                           .addParameter(String.class, "format")
                           .addParameter(Parameter.builder()
                                                  .type(Object.class)
                                                  .varargs(true)
                                                  .name("args")
                                                  .build())
                           .addStatement("this.$L.asTransient().addStatement(format, args)", blockName)
                           .addStatement("return this")
                           .build();
    }

    private MethodSyntax addStatement2(String blockName) {
        return MethodSyntax.builder("addStatement")
                           .returns(ClassName.from("Builder"))
                           .addModifier(Modifier.PUBLIC)
                           .addParameter(Statement.class, "stmt")
                           .addStatement("this.$L.asTransient().addStatement(stmt)", blockName)
                           .addStatement("return this")
                           .build();
    }

    private MethodSyntax ifStatement1(String blockName) {
        return MethodSyntax.builder("ifStatement")
                           .returns(ClassName.from("Builder"))
                           .addModifier(Modifier.PUBLIC)
                           .addParameter(String.class, "format")
                           .addParameter(Parameter.builder()
                                                  .type(blockConsumer)
                                                  .name("then")
                                                  .build())
                           .addStatement("this.$L.asTransient().ifStatement(format, then)", blockName)
                           .addStatement("return this")
                           .build();
    }

    private MethodSyntax ifStatement2(String blockName) {
        return MethodSyntax.builder("ifStatement")
                           .returns(ClassName.from("Builder"))
                           .addModifier(Modifier.PUBLIC)
                           .addParameter(String.class, "format")
                           .addParameter(Object.class, "arg")
                           .addParameter(Parameter.builder()
                                                  .type(blockConsumer)
                                                  .name("then")
                                                  .build())
                           .addStatement("this.$L.asTransient().ifStatement(format, arg, then)", blockName)
                           .addStatement("return this")
                           .build();
    }

    private MethodSyntax ifElseStatement1(String blockName) {
        return MethodSyntax.builder("ifStatement")
                           .returns(ClassName.from("Builder"))
                           .addModifier(Modifier.PUBLIC)
                           .addParameter(String.class, "format")
                           .addParameter(Parameter.builder()
                                                  .type(blockConsumer)
                                                  .name("then")
                                                  .build())
                           .addParameter(Parameter.builder()
                                                  .type(blockConsumer)
                                                  .name("otherwise")
                                                  .build())
                           .addStatement("this.$L.asTransient().ifStatement(format, then, otherwise)", blockName)
                           .addStatement("return this")
                           .build();
    }

    private MethodSyntax ifElseStatement2(String blockName) {
        return MethodSyntax.builder("ifStatement")
                           .returns(ClassName.from("Builder"))
                           .addModifier(Modifier.PUBLIC)
                           .addParameter(String.class, "format")
                           .addParameter(Object.class, "arg")
                           .addParameter(Parameter.builder()
                                                  .type(blockConsumer)
                                                  .name("then")
                                                  .build())
                           .addParameter(Parameter.builder()
                                                  .type(blockConsumer)
                                                  .name("otherwise")
                                                  .build())
                           .addStatement("this.$L.asTransient().ifStatement(format, arg, then, otherwise)", blockName)
                           .addStatement("return this")
                           .build();
    }

    private MethodSyntax beginControlFlow(String blockName) {
        return MethodSyntax.builder("beginControlFlow")
                           .returns(ClassName.from("Builder"))
                           .addModifier(Modifier.PUBLIC)
                           .addParameter(String.class, "format")
                           .addParameter(Parameter.builder()
                                                  .type(Object.class)
                                                  .varargs(true)
                                                  .name("args")
                                                  .build())
                           .addStatement("this.$L.asTransient().beginControlFlow(format, args)", blockName)
                           .addStatement("return this")
                           .build();
    }

    private MethodSyntax nextControlFlow(String blockName) {
        return MethodSyntax.builder("nextControlFlow")
                           .returns(ClassName.from("Builder"))
                           .addModifier(Modifier.PUBLIC)
                           .addParameter(String.class, "format")
                           .addParameter(Parameter.builder()
                                                  .type(Object.class)
                                                  .varargs(true)
                                                  .name("args")
                                                  .build())
                           .addStatement("this.$L.asTransient().nextControlFlow(format, args)", blockName)
                           .addStatement("return this")
                           .build();
    }

    private MethodSyntax endControlFlow(String blockName) {
        return MethodSyntax.builder("endControlFlow")
                           .returns(ClassName.from("Builder"))
                           .addModifier(Modifier.PUBLIC)
                           .addStatement("this.$L.asTransient().endControlFlow()", blockName)
                           .addStatement("return this")
                           .build();
    }

    private static String blockMemberName(ShapeCodegenState state, StructureShape shape) {
        // Should we restrict for now to methods, not sure if we want this for all blocks,
        //if (shape.getId().equals(METHOD_SHAPE_ID)) {
        for (var member : shape.members()) {
            if (member.getTarget().equals(BLOCK_SHAPE_ID)) {
                return state.symbolProvider().toMemberName(member);
            }
        }
        //}
        return null;
    }

    private static TypeName blockConsumer() {
        return ParameterizedTypeName.from(Consumer.class,
                                          ParameterizedTypeName.from(AbstractBlockBuilder.class,
                                                                     BodyBuilder.class,
                                                                     Block.class));
    }
}
