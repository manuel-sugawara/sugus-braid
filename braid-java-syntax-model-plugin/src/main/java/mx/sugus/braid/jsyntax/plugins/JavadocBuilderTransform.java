package mx.sugus.braid.jsyntax.plugins;

import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.CompilationUnit;
import mx.sugus.braid.jsyntax.Javadoc;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.Parameter;
import mx.sugus.braid.jsyntax.transforms.AddMethodsTransform;
import mx.sugus.braid.jsyntax.transforms.MethodMatcher;
import mx.sugus.braid.jsyntax.transforms.TypeMatcher;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;

public class JavadocBuilderTransform implements ShapeTaskTransformer<TypeSyntaxResult> {

    private static final Identifier ID = Identifier.of(JavadocBuilderTransform.class);

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
        if (!result.syntax().type().name().equals("Javadoc")) {
            return result;
        }
        var compilationUnit = (CompilationUnit)
            AddMethodsTransform.builder()
                               .addBefore()
                               .methodMatcher(MethodMatcher.byName("build"))
                               .typeMatcher(TypeMatcher.byName("Builder"))
                               .methods(builderOverrides())
                               .build()
                               .transform(result.syntax());
        return result.toBuilder().syntax(compilationUnit).build();
    }

    private List<MethodSyntax> builderOverrides() {
        return List.of(builderBodyOverride(),
                       builderParamOverride(),
                       builderReturnsOverride());
    }

    private MethodSyntax builderBodyOverride() {
        var doc = Javadoc.builder()
                         .body("Creates and sets the `body` using the given format and arguments.")
                         .returns("This instance for chain calling.")
                         .putParam("format", "The format to create the {@link $T} body", CodeBlock.class)
                         .putParam("args", "The arguments for the format")
                         .build();
        return
            MethodSyntax.builder("body")
                        .javadoc(doc)
                        .addModifier(Modifier.PUBLIC)
                        .addParameter(String.class, "format")
                        .addParameter(Parameter.builder()
                                               .name("args")
                                               .type(Object.class)
                                               .varargs(true)
                                               .build())
                        .returns(ClassName.from("Builder"))
                        .beginControlFlow("if (format != null)")
                        .addStatement("body($T.from(format, args))", CodeBlock.class)
                        .nextControlFlow("else")
                        .addStatement("body(null)")
                        .endControlFlow()
                        .addStatement("return this")
                        .build();
    }

    private MethodSyntax builderParamOverride() {
        var doc = Javadoc.builder()
                         .body(CodeBlock.from("Creates and puts a `param` with the name using the given format and arguments."))
                         .returns(CodeBlock.from("This instance for chain calling."))
                         .putParam("name", "The name of the param")
                         .putParam("format", "The format to create the {@link $T} param", CodeBlock.class)
                         .putParam("args", "The arguments for the format")
                         .build();
        return
            MethodSyntax.builder("putParam")
                        .javadoc(doc)
                        .addModifier(Modifier.PUBLIC)
                        .addParameter(String.class, "name")
                        .addParameter(String.class, "format")
                        .addParameter(Parameter.builder()
                                               .name("args")
                                               .type(Object.class)
                                               .varargs(true)
                                               .build())
                        .returns(ClassName.from("Builder"))
                        .addStatement("putParam(name, $T.from(format, args))", CodeBlock.class)
                        .addStatement("return this")
                        .build();
    }

    private MethodSyntax builderReturnsOverride() {
        var doc = Javadoc.builder()
                         .body(CodeBlock.from("Creates and sets the `returns` using the given format and arguments."))
                         .returns(CodeBlock.from("This instance for chain calling."))
                         .putParam("format", "The format to create the {@link $T} returns", CodeBlock.class)
                         .putParam("args", "The arguments for the format")
                         .build();
        return
            MethodSyntax.builder("returns")
                        .javadoc(doc)
                        .addModifier(Modifier.PUBLIC)
                        .addParameter(String.class, "format")
                        .addParameter(Parameter.builder()
                                               .name("args")
                                               .type(Object.class)
                                               .varargs(true)
                                               .build())
                        .returns(ClassName.from("Builder"))
                        .addStatement("returns($T.from(format, args))", CodeBlock.class)
                        .addStatement("return this")
                        .build();
    }
}
