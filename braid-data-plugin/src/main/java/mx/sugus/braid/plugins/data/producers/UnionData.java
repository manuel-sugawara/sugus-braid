package mx.sugus.braid.plugins.data.producers;

import static mx.sugus.braid.plugins.data.producers.CodegenUtils.BUILDER_TYPE;
import static mx.sugus.braid.plugins.data.producers.StructureData.builderDoc;
import static mx.sugus.braid.plugins.data.producers.StructureData.toBuilderDoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.Annotation;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.Javadoc;
import mx.sugus.braid.jsyntax.MemberValue;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.Modifier;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.TypeVariableTypeName;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.plugins.data.DataPlugin;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.traits.DocumentationTrait;

public final class UnionData implements DirectedClass {

    @Override
    public ClassSyntax.Builder typeSpec(ShapeCodegenState state) {
        var builder = ClassSyntax.builder(state.symbol().getName())
                                 .addAnnotation(Utils.generatedBy(DataPlugin.ID))
                                 .addAnnotation(CodegenUtils.suppressUnchecked())
                                 .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
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
        return List.of();
    }

    @Override
    public List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
        return List.of();
    }

    ClassName builderJavaClassName() {
        return BUILDER_TYPE;
    }

    @Override
    public List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        var result = new ArrayList<MethodSyntax>();
        result.add(toBuilderMethod(state));
        result.add(asMember(state));
        result.addAll(builderMethods(state));
        return result;
    }

    @Override
    public List<AbstractMethodSyntax> extraAbstractMethods(ShapeCodegenState state) {
        return List.of(accessorForTag(), accessorForValue());
    }

    private AbstractMethodSyntax accessorForTag() {
        var body = "Returns the enum value representing which member of this object is populated.\n\n"
                   + "This will be {@link VariantTag#UNKNOWN_TO_VERSION} if no member is set.";
        var doc = Javadoc.builder()
                         .body(body)
                         .returns("The enum value representing which member of this object is populated")
                         .build();
        return AbstractMethodSyntax.builder("variantTag")
                                   .javadoc(doc)
                                   .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                                   .returns(UnionVariantTagEnumData.VARIANT_TAG_NAME)
                                   .build();
    }

    private AbstractMethodSyntax accessorForValue() {
        return AbstractMethodSyntax.builder("variantValue")
                                   .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                                   .addTypeParam("T")
                                   .returns(TypeVariableTypeName.from("T"))
                                   .build();
    }

    private MethodSyntax asMember(ShapeCodegenState state) {
        var body = "Returns the specific member type.";
        var doc = Javadoc.builder()
                         .body(body)
                         .returns("The specific member type")
                         .build();
        var type = TypeVariableTypeName.builder()
                                       .name("T")
                                       .addBound(className(state))
                                       .build();
        var typeVariableName = TypeVariableTypeName.from("T");
        var builder = MethodSyntax.builder("asMember")
                                  .javadoc(doc)
                                  .addAnnotation(CodegenUtils.suppressUnchecked())
                                  .addModifier(Modifier.PUBLIC)
                                  .addTypeParam(type)
                                  .returns(typeVariableName)
                                  .addParameter(ParameterizedTypeName.from(Class.class, typeVariableName), "memberType");
        builder.ifStatement("memberType != getClass()", then -> {
            then.addStatement("throw new $T(\"Member of class: \" + getClass().getName() + \" cannot be casted to: \" + "
                              + "memberType.getName())",
                              ClassCastException.class);
        });
        builder.addStatement("return (T) this");
        return builder.build();
    }

    public MethodSyntax toBuilderMethod(ShapeCodegenState state) {
        var dataType = builderJavaClassName();
        return MethodSyntax.builder("toBuilder")
                           .addModifier(Modifier.PUBLIC)
                           .javadoc(toBuilderDoc())
                           .returns(dataType)
                           .body(b -> b.addStatement("return new $T(this)", dataType))
                           .build();
    }

    List<MethodSyntax> builderMethods(ShapeCodegenState state) {
        var dataType = builderJavaClassName();
        var defaultBuilder = MethodSyntax.builder("builder")
                                         .javadoc(builderDoc())
                                         .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                         .returns(dataType)
                                         .body(b -> b.addStatement("return new $T()", dataType))
                                         .build();
        return List.of(defaultBuilder);
    }

    @Override
    public List<DirectiveToTypeSyntax> innerTypes(ShapeCodegenState state) {
        var innerTypes = new ArrayList<DirectiveToTypeSyntax>();
        innerTypes.add(new UnionVariantTagEnumData());
        for (var member : state.shape().asUnionShape().orElseThrow().members()) {
            innerTypes.add(new UnionVariantData(member));
        }
        innerTypes.add(new UnknownVariant());
        innerTypes.add(new UnionDataBuilder());

        return innerTypes;
    }

    static class UnknownVariant implements DirectedClass {

        @Override
        public ClassSyntax.Builder typeSpec(ShapeCodegenState state) {
            var superClass = Utils.toJavaTypeName(state, state.shape());
            var builder = ClassSyntax.builder("$UnknownVariant")
                                     .superClass(superClass)
                                     .addAnnotation(Utils.generatedBy(DataPlugin.ID))
                                     .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                                     .javadoc(Javadoc.builder()
                                                     .body(CodeBlock.from("Unknown variant type."))
                                                     .build());
            return builder;
        }

        @Override
        public List<FieldSyntax> fieldsFor(ShapeCodegenState state, MemberShape member) {
            return List.of();
        }

        @Override
        public List<FieldSyntax> extraFields(ShapeCodegenState state) {
            return List.of(FieldSyntax.from(ClassName.from(String.class), "unknownVariantName"));
        }

        @Override
        public List<MethodSyntax> extraMethods(ShapeCodegenState state) {
            return List.of(variantTag(state), variantValue(state));
        }

        @Override
        public List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
            return List.of(constructor(state));
        }

        public ConstructorMethodSyntax constructor(ShapeCodegenState state) {
            return ConstructorMethodSyntax.builder()
                                          .addModifier(Modifier.PRIVATE)
                                          .addParameter(String.class, "name")
                                          .body(b -> {
                                              b.addStatement("this.unknownVariantName = $T.requireNonNull(name)", Objects.class);
                                          })
                                          .build();
        }


        MethodSyntax variantTag(ShapeCodegenState state) {
            return MethodSyntax.builder("variantTag")
                               .addAnnotation(CodegenUtils.override())
                               .addModifier(Modifier.PUBLIC)
                               .returns(UnionVariantTagEnumData.VARIANT_TAG_NAME)
                               .addStatement("return $T.UNKNOWN_TO_VERSION", UnionVariantTagEnumData.VARIANT_TAG_NAME)
                               .build();
        }

        MethodSyntax variantValue(ShapeCodegenState state) {
            return MethodSyntax.builder("variantValue")
                               .addAnnotation(CodegenUtils.override())
                               .addAnnotation(CodegenUtils.suppressUnchecked())
                               .addModifier(Modifier.PUBLIC)
                               .addTypeParam("T")
                               .returns(TypeVariableTypeName.from("T"))
                               .addStatement("return (T) this.unknownVariantName")
                               .build();
        }
    }
}
