package mx.sugus.braid.plugins.data.producers;

import static mx.sugus.braid.plugins.data.producers.CodegenUtils.BUILDER_TYPE;

import java.util.List;
import mx.sugus.braid.core.ImplementsKnowledgeIndex;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.traits.ConstTrait;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.traits.DocumentationTrait;

public class InterfaceDataBuilder implements DirectedInterface {

    @Override
    public InterfaceSyntax.Builder typeSpec(ShapeCodegenState state) {
        var builder = InterfaceSyntax.builder("Builder");
        var shape = state.shape().asStructureShape().orElseThrow();
        var superInterfaces = ImplementsKnowledgeIndex.of(state.model()).superInterfaces(shape);
        for (var superInterface : superInterfaces) {
            var superInterfaceClass = ClassName.toClassName(Utils.toJavaTypeName(state, superInterface));
            builder.addSuperInterface(superInterfaceClass
                                          .toBuilder()
                                          .name(superInterfaceClass.name() + "." + BUILDER_TYPE.name())
                                          .build());
        }
        return builder;
    }

    @Override
    public TypeName className(ShapeCodegenState state) {
        return BUILDER_TYPE;
    }

    @Override
    public List<AbstractMethodSyntax> abstractMethodsFor(ShapeCodegenState state, MemberShape member) {
        if (member.hasTrait(ConstTrait.class)) {
            return List.of();
        }
        return List.of(setter(state, member));
    }

    @Override
    public List<AbstractMethodSyntax> extraAbstractMethods(ShapeCodegenState state) {
        return List.of(buildMethod(state));
    }

    private AbstractMethodSyntax buildMethod(ShapeCodegenState state) {
        var type = Utils.toJavaTypeName(state, state.shape());
        return AbstractMethodSyntax.builder()
                                   .javadoc("Builds a new instance of {@link $T}", type)
                                   .name("build")
                                   .returns(type)
                                   .build();
    }

    private AbstractMethodSyntax setter(ShapeCodegenState state, MemberShape member) {
        var type = Utils.toJavaTypeName(state, member);
        var builder = AbstractMethodSyntax.builder()
                                          .name(Utils.toSetterName(state, member).toString())
                                          .addParameter(type, Utils.toJavaName(state, member).toString())
                                          .returns(BUILDER_TYPE);
        member.getTrait(DocumentationTrait.class)
              .map(DocumentationTrait::getValue)
              .map(JavadocExt::document)
              .map(builder::javadoc);
        return builder.build();
    }

}
