package mx.sugus.braid.jsyntax.plugins;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.CompilationUnit;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.Parameter;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.Statement;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.block.AbstractBlockBuilder;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.transforms.AddMethodsTransform;
import mx.sugus.braid.jsyntax.transforms.MethodMatcher;
import mx.sugus.braid.jsyntax.transforms.TypeMatcher;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.StructureShape;

public final class BlockBuilderTransformer implements ShapeTaskTransformer<TypeSyntaxResult> {

    private static final Identifier ID = Identifier.of(BlockBuilderTransformer.class);
    private static final ShapeId BLOCK_SHAPE_ID = ShapeId.fromParts("mx.sugus.braid.jsyntax", "Block");

    private final TypeName blockConsumer = blockConsumer();

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
        var blockMemberName = blockMemberName(state, state.shape().asStructureShape().orElseThrow());
        if (blockMemberName == null) {
            return result;
        }
        var compilationUnit = (CompilationUnit)
            AddMethodsTransform.builder()
                               .addAfter()
                               .methodMatcher(MethodMatcher.byName(blockMemberName))
                               .typeMatcher(TypeMatcher.byName("Builder"))
                               .methods(methods(blockMemberName))
                               .build()
                               .transform(result.syntax());
        return result.toBuilder().syntax(compilationUnit).build();
    }

    private List<MethodSyntax> methods(String blockMemberName) {
        return Arrays.asList(addStatement1(blockMemberName),
                             addStatement2(blockMemberName),
                             ifStatement1(blockMemberName),
                             ifStatement2(blockMemberName),
                             ifElseStatement1(blockMemberName),
                             ifElseStatement2(blockMemberName),
                             beginControlFlow(blockMemberName),
                             nextControlFlow(blockMemberName),
                             endControlFlow(blockMemberName));
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
        for (var member : shape.members()) {
            if (member.getTarget().equals(BLOCK_SHAPE_ID)) {
                return Utils.toJavaName(state, member).toString();
            }
        }
        return null;
    }

    private static TypeName blockConsumer() {
        return ParameterizedTypeName.from(Consumer.class,
                                          ParameterizedTypeName.from(AbstractBlockBuilder.class,
                                                                     BodyBuilder.class,
                                                                     Block.class));
    }
}
