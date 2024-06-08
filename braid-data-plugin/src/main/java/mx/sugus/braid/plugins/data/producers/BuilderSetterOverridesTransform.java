package mx.sugus.braid.plugins.data.producers;

import static mx.sugus.braid.plugins.data.producers.StructureCodegenUtils.getTargetTrait;
import static mx.sugus.braid.plugins.data.producers.StructureCodegenUtils.toParameters;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.transforms.AddMethodsTransform;
import mx.sugus.braid.jsyntax.transforms.MethodMatcher;
import mx.sugus.braid.jsyntax.transforms.TypeMatcher;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.traits.SetterOverride;
import mx.sugus.braid.traits.SetterOverridesTrait;
import software.amazon.smithy.model.shapes.MemberShape;

public class BuilderSetterOverridesTransform implements ShapeTaskTransformer<TypeSyntaxResult> {
    public static final Identifier ID = Identifier.of(BuilderSetterOverridesTransform.class);

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
        var symbolProvider = state.symbolProvider();
        for (var member : state.shape().asStructureShape().orElseThrow().members()) {
            var symbol = symbolProvider.toSymbol(member);
            var methods = methodsFor(state, member);
            if (!methods.isEmpty()) {
                syntax = (ClassSyntax)
                    AddMethodsTransform.builder()
                                       .addAfter()
                                       .methodMatcher(MethodMatcher.byName(Utils.toSetterName(symbol).toString()))
                                       .typeMatcher(TypeMatcher.byName("Builder"))
                                       .methods(methods)
                                       .build()
                                       .transform(syntax);
            }
        }
        return result.toBuilder().syntax(syntax).build();
    }

    public List<MethodSyntax> methodsFor(ShapeCodegenState state, MemberShape member) {
        var setterOverrides = getTargetTrait(SetterOverridesTrait.class, state, member);
        if (setterOverrides == null) {
            return List.of();
        }
        var result = new ArrayList<MethodSyntax>();
        for (var override : setterOverrides.getValues()) {
            result.add(settersOverride(state, member, override));
        }
        return result;
    }

    private MethodSyntax settersOverride(ShapeCodegenState state, MemberShape member, SetterOverride override) {
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(member);
        var setterName = Utils.toSetterName(symbol);
        var builder = MethodSyntax.builder(setterName.toString())
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(className(state));
        builder.parameters(toParameters(override.getArgs()));
        return builder.addStatement("this.$1L = $2L", Utils.toJavaName(symbol), override.getBody())
                      .addStatement("return this")
                      .build();
    }

    private ClassName className(ShapeCodegenState state) {
        return StructureCodegenUtils.BUILDER_TYPE;
    }
}
