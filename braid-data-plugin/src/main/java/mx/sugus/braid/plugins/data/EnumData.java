package mx.sugus.braid.plugins.data;

import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.CaseClause;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.DefaultCaseClause;
import mx.sugus.braid.jsyntax.EnumConstant;
import mx.sugus.braid.jsyntax.EnumSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.SwitchStatement;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import software.amazon.smithy.model.traits.DocumentationTrait;

public final class EnumData implements DirectedEnum {
    private static final ConstructorMethodSyntax CONSTRUCTOR = ConstructorMethodSyntax.builder()
                                                                                      .addParameter(String.class, "value")
                                                                                      .addStatement("this.value = value")
                                                                                      .build();
    static final List<ConstructorMethodSyntax> CONSTRUCTORS = List.of(CONSTRUCTOR);

    @Override
    public EnumSyntax.Builder typeSpec(ShapeCodegenState state) {
        var result = EnumSyntax.builder(state.symbol().getName())
                               .addAnnotation(DataPlugin.generatedBy())
                               .addModifier(Modifier.PUBLIC);
        var shape = state.shape().asEnumShape().orElseThrow();
        for (var kvp : shape.getAllMembers().entrySet()) {
            var enumMember = kvp.getValue();
            var enumValueBuilder = EnumConstant.builder()
                                               .name(state.symbolProvider().toMemberName(enumMember))
                                               .body(CodeBlock.from("$S",
                                                                    shape.getEnumValues().get(kvp.getKey())));
            if (enumMember.hasTrait(DocumentationTrait.class)) {
                var doc = enumMember.getTrait(DocumentationTrait.class).orElseThrow().getValue();
                enumValueBuilder.javadoc("$L", JavadocExt.document(doc));
            }
            result.addEnumConstant(enumValueBuilder.build());
        }
        var unknownValueBuilder = EnumConstant.builder()
                                              .body(CodeBlock.from("$L", "null"))
                                              .name("UNKNOWN_TO_VERSION");
        unknownValueBuilder.javadoc("$L", JavadocExt.document("Unknown enum constant"));
        result.addEnumConstant(unknownValueBuilder.build());

        if (shape.hasTrait(DocumentationTrait.class)) {
            var doc = shape.getTrait(DocumentationTrait.class).orElseThrow().getValue();
            result.javadoc("$L", JavadocExt.document(doc));
        }
        return result;
    }

    @Override
    public ClassName className(ShapeCodegenState state) {
        return ClassName.toClassName(state.symbolProvider().toJavaTypeName(state.shape()));
    }

    @Override
    public List<FieldSyntax> extraFields(ShapeCodegenState state) {
        return List.of(FieldSyntax.builder()
                                  .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                                  .name("value")
                                  .type(String.class)
                                  .build());
    }

    @Override
    public List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
        return CONSTRUCTORS;
    }

    @Override
    public List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        return List.of(fromValueMethod(state), toStringMethod(state));
    }

    private MethodSyntax fromValueMethod(ShapeCodegenState state) {
        var shapeType = state.symbolProvider().toJavaTypeName(state.shape());
        var javadoc = "Returns the corresponding enum constant from the given value.\n\n"
                      + "If the value is unknown it returns `UNKNOWN_TO_VERSION`.";
        var result = MethodSyntax.builder("from")
                                 .javadoc(JavadocExt.document(javadoc))
                                 .returns(shapeType)
                                 .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                 .addParameter(String.class, "value");
        result.body(body -> {
            body.ifStatement("value == null", b -> b.addStatement("return null"));
            var switchStatement = SwitchStatement.builder()
                                                 .expression(CodeBlock.from("value"));
            var shape = state.shape().asEnumShape().orElseThrow();
            for (var kvp : state.shape().getAllMembers().entrySet()) {
                var enumMember = kvp.getValue();
                var value = state.symbolProvider().toMemberName(enumMember);
                var label = CodeBlock.from("$S", shape.getEnumValues().get(kvp.getKey()));
                switchStatement.addCase(CaseClause.builder()
                                                  .addLabel(label)
                                                  .body(b -> b.addStatement("return $L", value))
                                                  .build());
            }
            switchStatement.defaultCase(DefaultCaseClause.builder()
                                                         .body(b -> b.addStatement("return UNKNOWN_TO_VERSION"))
                                                         .build());
            body.addStatement(switchStatement.build());
        });
        return result.build();
    }

    private MethodSyntax toStringMethod(ShapeCodegenState state) {
        var result = MethodSyntax.builder("toString")
                                 .addAnnotation(Override.class)
                                 .returns(String.class)
                                 .addModifier(Modifier.PUBLIC);
        result.addStatement("return value");
        return result.build();
    }
}
