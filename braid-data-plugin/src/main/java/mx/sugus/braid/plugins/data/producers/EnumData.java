package mx.sugus.braid.plugins.data.producers;

import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.SensitiveKnowledgeIndex;
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
import mx.sugus.braid.plugins.data.DataPlugin;
import software.amazon.smithy.model.traits.DocumentationTrait;

public final class EnumData implements DirectedEnum {
    private static final ConstructorMethodSyntax CONSTRUCTOR = ConstructorMethodSyntax.builder()
                                                                                      .addParameter(String.class, "value")
                                                                                      .addStatement("this.value = value")
                                                                                      .build();
    static final List<ConstructorMethodSyntax> CONSTRUCTORS = List.of(CONSTRUCTOR);

    @Override
    public EnumSyntax.Builder typeSpec(ShapeCodegenState state) {
        var builder = EnumSyntax.builder(state.symbol().getName())
                                .addAnnotation(Utils.generatedBy(DataPlugin.ID))
                                .addModifier(Modifier.PUBLIC);
        var shape = state.shape().asEnumShape().orElseThrow();
        for (var kvp : shape.getAllMembers().entrySet()) {
            var enumMember = kvp.getValue();
            var enumValueBuilder = EnumConstant.builder()
                                               .name(state.symbolProvider().toMemberName(enumMember))
                                               .body(CodeBlock.from("$S",
                                                                    shape.getEnumValues().get(kvp.getKey())));
            enumMember.getTrait(DocumentationTrait.class)
                      .map(DocumentationTrait::getValue)
                      .map(JavadocExt::document)
                      .map(enumValueBuilder::javadoc);
            builder.addEnumConstant(enumValueBuilder.build());
        }
        var unknownValueBuilder = EnumConstant.builder()
                                              .body(CodeBlock.from("$L", "null"))
                                              .name("UNKNOWN_TO_VERSION");
        unknownValueBuilder.javadoc("$L", JavadocExt.document("Unknown enum constant"));
        builder.addEnumConstant(unknownValueBuilder.build());
        shape.getTrait(DocumentationTrait.class)
             .map(DocumentationTrait::getValue)
             .map(JavadocExt::document)
             .map(builder::javadoc);
        return builder;
    }

    @Override
    public ClassName className(ShapeCodegenState state) {
        return ClassName.toClassName(Utils.toJavaTypeName(state, state.shape()));
    }

    @Override
    public List<FieldSyntax> extraFields(ShapeCodegenState state) {
        return List.of(FieldSyntax.from(String.class, "value"));
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
        var shapeType = Utils.toJavaTypeName(state, state.shape());
        var javadoc = """
            Returns the corresponding enum constant from the given value.

            If the value is unknown it returns `UNKNOWN_TO_VERSION`.""";
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
        var sensitiveIndex = SensitiveKnowledgeIndex.of(state.model());
        if (sensitiveIndex.isSensitive(state.shape())) {
            return CodegenUtils.toStringForSensitive();
        }
        return CodegenUtils.toStringTemplate()
                           .addStatement("return value")
                           .build();
    }
}
