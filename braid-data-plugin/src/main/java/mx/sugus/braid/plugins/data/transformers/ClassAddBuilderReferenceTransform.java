package mx.sugus.braid.plugins.data.transformers;

import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.plugin.ShapeTaskTransformer;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.TypeSyntax;
import mx.sugus.braid.jsyntax.transforms.AddInnerTypesTransform;
import mx.sugus.braid.jsyntax.transforms.TypeMatcher;
import mx.sugus.braid.plugins.data.TypeSyntaxResult;
import mx.sugus.braid.plugins.data.producers.CodegenUtils;
import mx.sugus.braid.plugins.data.producers.StructureJavaProducer;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.rt.util.AbstractBuilderReference;
import mx.sugus.braid.traits.UseBuilderReferenceTrait;

public class ClassAddBuilderReferenceTransform implements ShapeTaskTransformer<TypeSyntaxResult> {
    public static final Identifier ID = Identifier.of(ClassAddBuilderReferenceTransform.class);

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
        var shape = state.shape();
        if (!shape.hasTrait(UseBuilderReferenceTrait.class)) {
            return result;
        }
        var useBuilderReference = shape.expectTrait(UseBuilderReferenceTrait.class);
        if (useBuilderReference.builderType() != null || useBuilderReference.fromPersistent() != null) {
            return result;
        }
        var syntax = (ClassSyntax) result.syntax();
        var className = Utils.toJavaName(state, shape).toString();
        var transformed = (ClassSyntax)
            AddInnerTypesTransform.builder()
                                  .types(innerTypes(state))
                                  .typeMatcher(TypeMatcher.byName(className),
                                               TypeMatcher.byName(CodegenUtils.builderType().name()))
                                  .addAfter()
                                  .build()
                                  .transform(syntax);
        return result.toBuilder().syntax(transformed).build();
    }

    private List<TypeSyntax> innerTypes(ShapeCodegenState state) {
        return List.of(builderReferenceType(state));
    }

    private TypeSyntax builderReferenceType(ShapeCodegenState state) {
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(state.shape());
        var className = ClassName.from(Utils.toJavaName(state, state.shape()).withSuffix("BuilderReference").toString());
        var builder = ClassSyntax.builder(className.name())
                                 .addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        var shapeType = Utils.toJavaTypeName(symbol);
        var builderType = CodegenUtils.builderType();
        builder.superClass(ParameterizedTypeName.from(AbstractBuilderReference.class,
                                                      shapeType,
                                                      builderType));
        builder.addMethod(ConstructorMethodSyntax.builder()
                                                 .addParameter(shapeType, "source")
                                                 .addStatement("super(source)")
                                                 .build());
        builder.addMethod(MethodSyntax.builder("emptyTransient")
                                      .addAnnotation(Override.class)
                                      .addModifier(Modifier.PROTECTED)
                                      .returns(builderType)
                                      .addStatement("return $T.builder()", shapeType)
                                      .build());
        builder.addMethod(MethodSyntax.builder("transientToPersistent")
                                      .addAnnotation(Override.class)
                                      .addModifier(Modifier.PROTECTED)
                                      .returns(shapeType)
                                      .addParameter(builderType, "builder")
                                      .addStatement("return builder.build()")
                                      .build());
        builder.addMethod(MethodSyntax.builder("persistentToTransient")
                                      .addAnnotation(Override.class)
                                      .addModifier(Modifier.PROTECTED)
                                      .returns(builderType)
                                      .addParameter(shapeType, "source")
                                      .addStatement("return source.toBuilder()")
                                      .build());
        builder.addMethod(MethodSyntax.builder("clearTransient")
                                      .addAnnotation(Override.class)
                                      .addModifier(Modifier.PROTECTED)
                                      .returns(builderType)
                                      .addParameter(builderType, "builder")
                                      .addStatement("return $T.builder()", shapeType)
                                      .build());
        builder.addMethod(MethodSyntax.builder("from")
                                      .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                      .returns(className)
                                      .addParameter(shapeType, "source")
                                      .addStatement("return new $T(source)", className)
                                      .build());
        return builder.build();
    }
}
