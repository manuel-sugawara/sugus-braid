package mx.sugus.braid.plugins.data;

import static mx.sugus.braid.plugins.data.DataFromFactoryOverrides.fromFactory;
import static mx.sugus.braid.plugins.data.Utils.toJavaName;
import static mx.sugus.braid.plugins.data.Utils.toJavaTypeName;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.ImplementsKnowledgeIndex;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.traits.FromFactoriesTrait;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.traits.DocumentationTrait;

public final class InterfaceData implements DirectedInterface {

    @Override
    public TypeName className(ShapeCodegenState state) {
        var symbol = state.symbol();
        return ClassName.from(symbol.getNamespace(), symbol.getName());
    }

    @Override
    public InterfaceSyntax.Builder typeSpec(ShapeCodegenState state) {
        var result = InterfaceSyntax.builder(toJavaName(state, state.shape()).toString())
                                    .addAnnotation(DataPlugin.generatedBy())
                                    .addModifier(Modifier.PUBLIC);
        var shape = state.shape().asStructureShape().orElseThrow();
        var superInterfaces = ImplementsKnowledgeIndex.of(state.model()).superInterfaces(shape);
        for (var superInterface : superInterfaces) {
            var parentClass = toJavaTypeName(state, superInterface);
            result.addSuperInterface(parentClass);
        }
        if (shape.hasTrait(DocumentationTrait.class)) {
            var doc = shape.getTrait(DocumentationTrait.class).orElseThrow().getValue();
            result.javadoc("$L", JavadocExt.document(doc));
        }
        return result;
    }

    @Override
    public List<AbstractMethodSyntax> abstractMethodsFor(ShapeCodegenState state, MemberShape member) {
        return List.of(accessor(state, member));
    }

    private AbstractMethodSyntax accessor(ShapeCodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberName(member);
        var type = toJavaTypeName(state, member);
        var result = AbstractMethodSyntax.builder()
                                         .name(name)
                                         .returns(type);
        if (member.hasTrait(DocumentationTrait.class)) {
            var doc = member.getTrait(DocumentationTrait.class).orElseThrow().getValue();
            result.javadoc("$L", JavadocExt.document(doc));
        }
        return result.build();
    }

    @Override
    public List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        if (state.shape().hasTrait(FromFactoriesTrait.class)) {
            var result = new ArrayList<MethodSyntax>();
            var fromFactories = state.shape().expectTrait(FromFactoriesTrait.class);
            for (var override : fromFactories.getValues()) {
                result.add(fromFactory(state, override));
            }

            return result;
        }
        return List.of();
    }
}
