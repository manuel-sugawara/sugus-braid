package mx.sugus.braid.plugins.data.transformers;

import static mx.sugus.braid.core.util.Utils.coalesce;
import static mx.sugus.braid.plugins.data.producers.CodegenUtils.getTargetListMember;
import static mx.sugus.braid.plugins.data.producers.CodegenUtils.getTargetListMemberTrait;
import static mx.sugus.braid.plugins.data.producers.CodegenUtils.getTargetTrait;
import static mx.sugus.braid.plugins.data.producers.CodegenUtils.toParameters;

import java.util.ArrayList;
import java.util.List;
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
import mx.sugus.braid.jsyntax.transforms.AddMethodsTransform;
import mx.sugus.braid.jsyntax.transforms.MethodMatcher;
import mx.sugus.braid.jsyntax.transforms.TypeMatcher;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.CodegenUtils;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.plugins.data.symbols.SymbolConstants;
import mx.sugus.braid.traits.AddAllOverridesTrait;
import mx.sugus.braid.traits.AdderOverridesTrait;
import mx.sugus.braid.traits.BuilderOverride;
import mx.sugus.braid.traits.FromFactoriesTrait;
import mx.sugus.braid.traits.MultiAddOverridesTrait;
import software.amazon.smithy.model.shapes.MemberShape;

public final class BuilderAdderOverridesTransform implements ShapeTaskTransformer<TypeSyntaxResult> {
    public static final Identifier ID = Identifier.of(BuilderAdderOverridesTransform.class);

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
        var syntax = (ClassSyntax) result.syntax();
        for (var member : state.shape().asStructureShape().orElseThrow().members()) {
            if (Utils.aggregateType(state, member) != SymbolConstants.AggregateType.NONE) {
                var methods = methodsFor(state, member);
                if (!methods.isEmpty()) {
                    syntax = (ClassSyntax)
                        AddMethodsTransform.builder()
                                           .addAfter()
                                           .methodMatcher(MethodMatcher.byName(Utils.toAdderName(state, member).toString()))
                                           .typeMatcher(TypeMatcher.byName("Builder"))
                                           .methods(methods)
                                           .build()
                                           .transform(syntax);
                }
            }
        }
        return result.toBuilder().syntax(syntax).build();
    }

    private List<MethodSyntax> methodsFor(ShapeCodegenState state, MemberShape member) {
        var aggregateType = Utils.aggregateType(state, member);
        if (aggregateType == SymbolConstants.AggregateType.NONE) {
            return List.of();
        }
        var result = new ArrayList<MethodSyntax>();
        var adderOverrides = getTargetTrait(AdderOverridesTrait.class, state, member);
        if (adderOverrides != null) {
            addAdderOverrides(result, state, member, adderOverrides.getValues());
        }
        var listMemberFromFactories = getTargetListMemberTrait(FromFactoriesTrait.class, state, member);
        if (listMemberFromFactories != null) {
            addImplicitAdderOverrides(result, state, member, listMemberFromFactories.getValues());
        }
        var addAllOverrides = getTargetTrait(AddAllOverridesTrait.class, state, member);
        if (addAllOverrides != null) {
            addAddAllOverrides(result, state, member, addAllOverrides);
        }
        var multiAddOverrides = getTargetTrait(MultiAddOverridesTrait.class, state, member);
        if (multiAddOverrides != null) {
            addMultiAddOverrides(result, state, member, multiAddOverrides);
        }
        return result;
    }

    private void addAdderOverrides(
        List<MethodSyntax> methods,
        ShapeCodegenState state,
        MemberShape member,
        List<BuilderOverride> builderOverrides
    ) {
        var name = Utils.toJavaName(state, member);
        for (var override : builderOverrides) {
            var adderName = coalesce(override.getName(),
                                     () -> Utils.toAdderName(state, member).toString());
            var overrideBuilder = MethodSyntax.builder(adderName)
                                              .addModifier(Modifier.PUBLIC)
                                              .returns(className(state));
            overrideBuilder.parameters(toParameters(override.getArgs()));
            overrideBuilder.body(body -> {
                addValue(state, member, body, override.getBody());
                body.addStatement("return this");
            });
            var javadoc = coalesce(override.getJavadoc(), () -> "Adds to `" + name + "` building the value using the given "
                                                                + "arguments");
            overrideBuilder.javadoc("$L", JavadocExt.document(javadoc));
            methods.add(overrideBuilder.build());
        }
    }

    private void addImplicitAdderOverrides(
        List<MethodSyntax> methods,
        ShapeCodegenState state,
        MemberShape member,
        List<BuilderOverride> builderOverrides
    ) {
        var name = Utils.toJavaName(state, member);
        for (var override : builderOverrides) {
            // XXX This needs review, the logic over-enthusiastically adds overrides across the
            //   board.
            if (override.getName() != null) {
                continue;
            }
            var adderName = Utils.toAdderName(state, member).toString();
            var overrideBuilder = MethodSyntax.builder(adderName)
                                              .addModifier(Modifier.PUBLIC)
                                              .returns(className(state));
            overrideBuilder.parameters(toParameters(override.getArgs()));
            overrideBuilder.body(body -> {
                addValueFromImplicitOverride(state, member, body, override);
                body.addStatement("return this");
            });
            var javadoc = coalesce(override.getJavadoc(), () -> "Adds to `" + name + "` building the value using the given "
                                                                + "arguments");
            overrideBuilder.javadoc("$L", JavadocExt.document(javadoc));
            methods.add(overrideBuilder.build());
        }
    }

    private void addAddAllOverrides(
        List<MethodSyntax> methods,
        ShapeCodegenState state,
        MemberShape member,
        AddAllOverridesTrait addAllOverrides
    ) {
        var name = Utils.toJavaName(state, member);
        for (var override : addAllOverrides.getValues()) {
            var adderName = coalesce(override.getName(),
                                     () -> Utils.toAdderName(state, member).toString());
            var overrideBuilder = MethodSyntax.builder(adderName)
                                              .addModifier(Modifier.PUBLIC)
                                              .returns(className(state));
            overrideBuilder.parameters(toParameters(override.getArgs()));
            overrideBuilder.body(body -> {
                addAllValue(state, member, body, override.getBody());
                body.addStatement("return this");
            });
            var javadoc = coalesce(override.getJavadoc(), () -> "Adds to `" + name + "` building the values using the given "
                                                                + "arguments");
            overrideBuilder.javadoc("$L", JavadocExt.document(javadoc));
            methods.add(overrideBuilder.build());
        }
    }

    private void addMultiAddOverrides(
        List<MethodSyntax> methods,
        ShapeCodegenState state,
        MemberShape member,
        MultiAddOverridesTrait multiAddOverrides
    ) {
        if (multiAddOverrides == null) {
            return;
        }
        var name = Utils.toJavaName(state, member);
        for (var override : multiAddOverrides.getValues()) {
            var adderName = coalesce(override.getName(),
                                     () -> Utils.toMultiAdderName(state, member).toString());
            var overrideBuilder = MethodSyntax.builder(adderName)
                                              .addModifier(Modifier.PUBLIC)
                                              .returns(className(state));
            overrideBuilder.parameters(toParameters(override.getArgs()));
            overrideBuilder.body(body -> {
                addValue(state, member, body, override.getBody());
                body.addStatement("return this");
            });
            var javadoc = coalesce(override.getJavadoc(), () -> "Adds the given values to `" + name + "`");
            overrideBuilder.javadoc("$L", JavadocExt.document(javadoc));
            methods.add(overrideBuilder.build());
        }
    }

    private void addValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder, List<String> values) {
        var name = Utils.toJavaName(state, member);
        for (var value : values) {
            builder.addStatement("this.$L.asTransient().add($L)", name.toString(), value);
        }
    }

    private void addValueFromImplicitOverride(ShapeCodegenState state, MemberShape member, BodyBuilder builder,
                                              BuilderOverride override) {
        var name = Utils.toJavaName(state, member);
        var block = CodeBlock.builder();
        var listMemberShape = getTargetListMember(state, member);
        block.addCode("$T.", Utils.toJavaTypeName(state, listMemberShape));
        if (override.getName() == null) {
            block.addCode("from");
        } else {
            block.addCode(override.getName());
        }
        block.addCode("(");
        var isFirst = true;
        for (var arg : override.getArgs()) {
            if (isFirst) {
                block.addCode("$L", arg.getName());
            } else {
                block.addCode(", $L", arg.getName());
            }
            isFirst = false;
        }
        block.addCode(")");
        builder.addStatement("this.$L.asTransient().add($C)", name.toString(), block.build());
    }

    private void addAllValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder, List<String> values) {
        var name = Utils.toJavaName(state, member);
        for (var value : values) {
            builder.addStatement("this.$L.asTransient().addAll($L)", name.toString(), value);
        }
    }

    private ClassName className(ShapeCodegenState state) {
        return CodegenUtils.builderType();
    }

}
