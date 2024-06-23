package mx.sugus.braid.plugins.data.producers;

import static mx.sugus.braid.plugins.data.producers.CodegenUtils.BUILDER_TYPE;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.SensitiveKnowledgeIndex;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.jsyntax.Annotation;
import mx.sugus.braid.jsyntax.CaseClause;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.SwitchStatement;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.plugins.data.DataPlugin;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.traits.DocumentationTrait;

public final class UnionData implements DirectedClass {
    static Annotation SUPPRESS_UNCHECKED = Annotation.fromStringValue(SuppressWarnings.class, "unchecked");

    @Override
    public ClassSyntax.Builder typeSpec(ShapeCodegenState state) {
        var builder = ClassSyntax.builder(state.symbol().getName())
                                 .addAnnotation(Utils.generatedBy(DataPlugin.ID))
                                 .addAnnotation(SUPPRESS_UNCHECKED)
                                 .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        var shape = state.shape();
        shape.getTrait(DocumentationTrait.class)
             .map(DocumentationTrait::getValue)
             .map(JavadocExt::document)
             .map(builder::javadoc);
        return builder;
    }

    @Override
    public List<FieldSyntax> fieldsFor(ShapeCodegenState state, MemberShape member) {
        return List.of();
    }

    @Override
    public List<FieldSyntax> extraFields(ShapeCodegenState state) {
        return List.of(FieldSyntax.from(Object.class, "variantValue"),
                       FieldSyntax.from(UnionVariantTagEnumData.VARIANT_TAG_NAME, "variantTag"));
    }

    @Override
    public List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
        return List.of(constructorFromBuilder(state));
    }

    public ConstructorMethodSyntax constructorFromBuilder(ShapeCodegenState state) {
        return ConstructorMethodSyntax.builder()
                                      .addModifier(Modifier.PRIVATE)
                                      .addParameter(builderJavaClassName(), "builder")
                                      .addStatement("this.variantValue = builder.getValue()")
                                      .addStatement("this.variantTag = builder.variantTag")
                                      .build();
    }

    @Override
    public List<MethodSyntax> methodsFor(ShapeCodegenState state, MemberShape member) {
        return List.of(accessor(state, member));
    }

    private MethodSyntax accessor(ShapeCodegenState state, MemberShape member) {
        var getterName = Utils.toGetterName(state, member);
        var type = Utils.toJavaTypeName(state, member);
        var memberName = member.getMemberName();
        var unionVariant = Utils.toSourceName(state, member, Name.Convention.SCREAM_CASE).toString();
        var builder = MethodSyntax.builder(getterName.toString())
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(type);
        builder.ifStatement("this.variantTag == VariantTag.$L", unionVariant, then -> {
            then.addStatement("return ($T) this.variantValue", type);
        });
        builder.addStatement("throw new $T($S + this.variantTag + $S)", NoSuchElementException.class,
                             "Union element `" + memberName + "` not set, currently set `", "`");
        member.getTrait(DocumentationTrait.class)
              .map(DocumentationTrait::getValue)
              .map(JavadocExt::document)
              .map(builder::javadoc);
        return builder.build();
    }

    ClassName builderJavaClassName() {
        return BUILDER_TYPE;
    }

    @Override
    public List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        var result = new ArrayList<MethodSyntax>();
        result.add(accessorForType());
        result.add(accessorForValue());
        result.add(toBuilderMethod(state));
        result.add(equalsMethod(state));
        result.add(hashCodeMethod(state));
        result.add(toStringMethod(state));
        result.addAll(builderMethods(state));
        return result;
    }

    private MethodSyntax accessorForType() {
        var doc = "Returns an enum value representing which member of this object is populated.\n\n"
                  + "This will be {@link Type#UNKNOWN_TO_VERSION} if no members are set.";
        return MethodSyntax.builder("variantTag")
                           .javadoc(JavadocExt.document(doc))
                           .addModifier(Modifier.PUBLIC)
                           .returns(UnionVariantTagEnumData.VARIANT_TAG_NAME)
                           .addStatement("return this.variantTag")
                           .build();
    }

    private MethodSyntax accessorForValue() {
        var doc = "Returns the untyped value of the union.\n\n"
                  + "Use {@link #type()} to get the member currently set.";

        return MethodSyntax.builder("variantValue")
                           .javadoc(JavadocExt.document(doc))
                           .addModifier(Modifier.PUBLIC)
                           .returns(Object.class)
                           .body(b -> b.addStatement("return this.variantValue"))
                           .build();
    }

    public MethodSyntax toBuilderMethod(ShapeCodegenState state) {
        var dataType = builderJavaClassName();
        return MethodSyntax.builder("toBuilder")
                           .addModifier(Modifier.PUBLIC)
                           .javadoc(JavadocExt.document("Returns a new builder to modify a copy of this instance"))
                           .returns(dataType)
                           .body(b -> b.addStatement("return new $T(this)", dataType))
                           .build();
    }

    public MethodSyntax equalsMethod(ShapeCodegenState state) {
        var builder = MethodSyntax.builder("equals")
                                  .addAnnotation(Override.class)
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(boolean.class)
                                  .addParameter(Object.class, "other");
        builder.ifStatement("this == other", b -> b.addStatement("return true"));
        var className = className(state);
        builder.ifStatement("!(other instanceof $T)", className, b -> b.addStatement("return false"));
        builder.addStatement("$1T that = ($1T) other", className);
        builder.addStatement("return this.variantTag == that.variantTag && this.variantValue.equals(that.variantValue)");
        return builder.build();
    }

    MethodSyntax hashCodeMethod(ShapeCodegenState state) {
        return MethodSyntax.builder("hashCode")
                           .addAnnotation(Override.class)
                           .addModifier(Modifier.PUBLIC)
                           .returns(int.class)
                           .addStatement("return this.variantTag.hashCode() + 31 * this.variantValue.hashCode()")
                           .build();
    }

    MethodSyntax toStringMethod(ShapeCodegenState state) {
        var sensitiveIndex = SensitiveKnowledgeIndex.of(state.model());
        if (sensitiveIndex.isSensitive(state.shape())) {
            return CodegenUtils.toStringForSensitive();
        }
        return CodegenUtils.toStringTemplate()
                           .body(b -> toStringMethodBody(state, b))
                           .build();
    }

    private void toStringMethodBody(ShapeCodegenState state, BodyBuilder body) {
        var sensitiveIndex = SensitiveKnowledgeIndex.of(state.model());
        body.addStatement("$1T buf = new $1T($2S)", StringBuilder.class, state.shape().getId().getName() + "{variantTag: ");
        body.addStatement("buf.append($L)", "this.variantTag");
        var memberSwitch = SwitchStatement.builder()
                                          .expression(CodeBlock.from("this.variantTag"));
        for (var member : state.shape().members()) {
            var memberName = member.getMemberName();
            var literalName = ", " + memberName + ": ";
            var unionVariant = Utils.toSourceName(state, member, Name.Convention.SCREAM_CASE).toString();
            memberSwitch.addCase(CaseClause.builder()
                                           .addLabel(CodeBlock.from("$L", unionVariant))
                                           .body(b -> {
                                               if (sensitiveIndex.isSensitive(member.getTarget())) {
                                                   b.addStatement("buf.append($S)", literalName + "<*** REDACTED ***>");
                                               } else {
                                                   b.addStatement("buf.append($S).append(this.variantValue)", literalName);
                                               }
                                               b.addStatement("break");
                                           })
                                           .build());
        }
        body.addStatement(memberSwitch.build());
        body.addStatement("return buf.append($S).toString()", "}");
    }

    List<MethodSyntax> builderMethods(ShapeCodegenState state) {
        var dataType = builderJavaClassName();
        var defaultBuilder = MethodSyntax.builder("builder")
                                         .javadoc(JavadocExt.document("Creates a new builder"))
                                         .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                         .returns(dataType)
                                         .body(b -> b.addStatement("return new $T()", dataType))
                                         .build();
        return List.of(defaultBuilder);
    }

    @Override
    public List<DirectiveToTypeSyntax> innerTypes(ShapeCodegenState state) {
        return List.of(new UnionVariantTagEnumData(), new UnionDataBuilder());
    }
}
