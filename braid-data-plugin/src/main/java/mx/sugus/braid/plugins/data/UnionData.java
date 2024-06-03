package mx.sugus.braid.plugins.data;

import static mx.sugus.braid.plugins.data.StructureCodegenUtils.BUILDER_TYPE;
import static mx.sugus.braid.plugins.data.StructureCodegenUtils.toParameters;

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
import mx.sugus.braid.traits.NewBuilderOverridesTrait;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.traits.DocumentationTrait;

public final class UnionData implements DirectedClass {
    static Annotation SUPPRESS_UNCHECKED = Annotation.builder(SuppressWarnings.class)
                                                     .value(CodeBlock.from("$S", "unchecked"))
                                                     .build();

    @Override
    public ClassSyntax.Builder typeSpec(ShapeCodegenState state) {
        var result = ClassSyntax.builder(state.symbol().getName())
                                .addAnnotation(DataPlugin.generatedBy())
                                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        var shape = state.shape();
        if (shape.hasTrait(DocumentationTrait.class)) {
            var doc = shape.getTrait(DocumentationTrait.class).orElseThrow().getValue();
            result.javadoc(CodeBlock.from("$L", JavadocExt.document(doc)));
        }
        return result;
    }

    @Override
    public List<FieldSyntax> fieldsFor(ShapeCodegenState state, MemberShape member) {
        return List.of();
    }

    @Override
    public List<FieldSyntax> extraFields(ShapeCodegenState state) {
        return List.of(FieldSyntax.builder()
                                  .name("value")
                                  .type(Object.class)
                                  .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                                  .build(),
                       FieldSyntax.builder()
                                  .name("type")
                                  .type(ClassName.from("Type"))
                                  .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                                  .build());
    }

    @Override
    public List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
        return List.of(constructorFromBuilder(state));
    }

    public ConstructorMethodSyntax constructorFromBuilder(ShapeCodegenState state) {
        return ConstructorMethodSyntax.builder()
                                      .addModifier(Modifier.PRIVATE)
                                      .addParameter(builderJavaClassName(), "builder")
                                      .body(b -> b.addStatement("this.value = builder.value")
                                                  .addStatement("this.type = builder.type"))
                                      .build();
    }

    @Override
    public List<MethodSyntax> methodsFor(ShapeCodegenState state, MemberShape member) {
        return List.of(accessor(state, member));
    }

    private MethodSyntax accessor(ShapeCodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberName(member);
        var type = symbolProvider.toJavaTypeName(member);
        var memberName = member.getMemberName();
        var unionTypeName = Name.of(memberName, Name.Convention.SCREAM_CASE).toString();
        var result = MethodSyntax.builder(name)
                                 .addAnnotation(SUPPRESS_UNCHECKED)
                                 .addModifier(Modifier.PUBLIC)
                                 .returns(type)
                                 .body(b ->
                                           b.ifStatement("this.type == Type.$L", unionTypeName, then -> {
                                                then.addStatement("return ($T) this.value", type);
                                            })
                                            .addStatement("throw new $T($S + this.type + $S)",
                                                          NoSuchElementException.class,
                                                          "Union element `" + memberName + "` not set, currently set `",
                                                          "`")
                                 );
        if (member.hasTrait(DocumentationTrait.class)) {
            var doc = member.getTrait(DocumentationTrait.class).orElseThrow().getValue();
            result.javadoc(CodeBlock.from("$L", JavadocExt.document(doc)));
        }
        return result.build();
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
        var doc = """
            Retrieve an enum value representing which member of this object is populated.
                        
            This will be {@link Type#UNKNOWN_TO_VERSION} if no members are set.""";

        return MethodSyntax.builder("type")
                           .javadoc(CodeBlock.from("$L", JavadocExt.document(doc)))
                           .addModifier(Modifier.PUBLIC)
                           .returns(UnionTypeEnumData.TYPE_NAME)
                           .body(b -> b.addStatement("return this.type"))
                           .build();
    }

    private MethodSyntax accessorForValue() {
        var doc = """
            Retrieve the untyped value of the union.
                        
            Use {@link #type()} to get the member currently set.""";

        return MethodSyntax.builder("value")
                           .javadoc(CodeBlock.from("$L", JavadocExt.document(doc)))
                           .addModifier(Modifier.PUBLIC)
                           .returns(Object.class)
                           .body(b -> b.addStatement("return this.value"))
                           .build();
    }

    public MethodSyntax toBuilderMethod(ShapeCodegenState state) {
        var dataType = builderJavaClassName();
        return MethodSyntax.builder("toBuilder")
                           .addModifier(Modifier.PUBLIC)
                           .javadoc(JavadocExt.document("Returns a new builder to modify a copy of this "
                                                        + "instance"))
                           .returns(dataType)
                           .body(b -> b.addStatement("return new $T(this)", dataType))
                           .build();
    }

    public MethodSyntax equalsMethod(ShapeCodegenState state) {
        var result = MethodSyntax.builder("equals")
                                 .addAnnotation(Override.class)
                                 .addModifier(Modifier.PUBLIC)
                                 .returns(boolean.class)
                                 .addParameter(Object.class, "other");
        result.body(body -> {
            body.ifStatement("this == other", b -> b.addStatement("return true"));
            var className = className(state);
            body.ifStatement("!(other instanceof $T)", className, b -> b.addStatement("return false"));
            body.addStatement("$1T that = ($1T) other", className);
            body.addStatement("return this.type == that.type && this.value.equals(that.value)");
        });
        return result.build();
    }

    MethodSyntax hashCodeMethod(ShapeCodegenState state) {
        return MethodSyntax.builder("hashCode")
                           .addAnnotation(Override.class)
                           .addModifier(Modifier.PUBLIC)
                           .returns(int.class)
                           .addStatement("return this.type.hashCode() + 31 * this.value.hashCode()")
                           .build();
    }

    MethodSyntax toStringMethod(ShapeCodegenState state) {
        return MethodSyntax.builder("toString")
                           .addAnnotation(Override.class)
                           .addModifier(Modifier.PUBLIC)
                           .returns(String.class)
                           .body(b -> toStringMethodBody(state, b))
                           .build();
    }

    private void toStringMethodBody(ShapeCodegenState state, BodyBuilder body) {
        var sensitiveIndex = SensitiveKnowledgeIndex.of(state.model());
        body.addStatement("$1T buf = new $1T($2S)", StringBuilder.class, state.shape().getId().getName() + "{type: ");
        body.addStatement("buf.append($L)", "this.type");
        var memberSwitch = SwitchStatement.builder()
                                          .expression(CodeBlock.from("this.type"));
        for (var member : state.shape().members()) {
            var memberName = member.getMemberName();
            var literalName = ", " + memberName + ": ";
            var unionTypeName = Name.of(memberName, Name.Convention.SCREAM_CASE).toString();
            memberSwitch.addCase(CaseClause.builder()
                                           .addLabel(CodeBlock.from("$L", unionTypeName))
                                           .body(b -> {
                                               if (sensitiveIndex.isSensitive(member.getTarget())) {
                                                   b.addStatement("buf.append($S)", literalName + "<*** REDACTED ***>");
                                               } else {
                                                   b.addStatement("buf.append($S).append(this.value)", literalName);
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
                                         .javadoc("$L", JavadocExt.document("Creates a new builder"))
                                         .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                         .returns(dataType)
                                         .body(b -> b.addStatement("return new $T()", dataType))
                                         .build();
        if (state.shape().hasTrait(NewBuilderOverridesTrait.class)) {
            var result = new ArrayList<MethodSyntax>();
            result.add(defaultBuilder);
            var builderOverrides = state.shape().getTrait(NewBuilderOverridesTrait.class).orElseThrow();
            for (var override : builderOverrides.getValues()) {
                var overrideBuilder = defaultBuilder.toBuilder();
                overrideBuilder.parameters(toParameters(override.getArgs()));
                var body = new BodyBuilder();
                for (var value : override.getBody()) {
                    body.addStatement("$L", value);
                }
                overrideBuilder.body(body.build());
                result.add(overrideBuilder.build());
            }
            return result;
        }
        return List.of(defaultBuilder);
    }

    @Override
    public List<DirectiveToTypeSyntax> innerTypes(ShapeCodegenState state) {
        return List.of(new UnionTypeEnumData(), new UnionDataBuilder());
    }
}
