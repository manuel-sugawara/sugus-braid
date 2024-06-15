package mx.sugus.braid.plugins.data.producers;

import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.ImplementsKnowledgeIndex;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.plugins.data.DataPlugin;
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
        var builder = InterfaceSyntax.builder(Utils.toJavaName(state, state.shape()).toString())
                                     .addAnnotation(DataPlugin.generatedBy())
                                     .addModifier(Modifier.PUBLIC);
        var shape = state.shape().asStructureShape().orElseThrow();
        var superInterfaces = ImplementsKnowledgeIndex.of(state.model()).superInterfaces(shape);
        for (var superInterface : superInterfaces) {
            var parentClass = Utils.toJavaTypeName(state, superInterface);
            builder.addSuperInterface(parentClass);
        }
        shape.getTrait(DocumentationTrait.class)
             .map(DocumentationTrait::getValue)
             .map(JavadocExt::document)
             .map(builder::javadoc);
        return builder;
    }

    @Override
    public List<AbstractMethodSyntax> abstractMethodsFor(ShapeCodegenState state, MemberShape member) {
        return List.of(accessor(state, member));
    }

    private AbstractMethodSyntax accessor(ShapeCodegenState state, MemberShape member) {
        var type = Utils.toJavaTypeName(state, member);
        var builder = AbstractMethodSyntax.builder()
                                          .name(Utils.toGetterName(state, member).toString())
                                          .returns(type);
        member.getTrait(DocumentationTrait.class)
              .map(DocumentationTrait::getValue)
              .map(JavadocExt::document)
              .map(builder::javadoc);
        return builder.build();
    }
}
