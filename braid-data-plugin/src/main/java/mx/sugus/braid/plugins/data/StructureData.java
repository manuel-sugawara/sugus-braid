package mx.sugus.braid.plugins.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.ImplementsKnowledgeIndex;
import mx.sugus.braid.core.SensitiveKnowledgeIndex;
import mx.sugus.braid.core.SymbolConstants;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.block.AbstractBlockBuilder;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.traits.ConstTrait;
import software.amazon.smithy.model.shapes.EnumShape;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.traits.DocumentationTrait;

public final class StructureData implements DirectedClass {
    static final DirectedClass INSTANCE = new CompositeDirectedClass(new StructureData(),
                                                                     DataBuilderOverrides.INSTANCE,
                                                                     DataFromFactoryOverrides.INSTANCE);
    private static final List<DirectiveToTypeSyntax> INNER_TYPES =
        List.of(new CompositeDirectedClass(StructureDataBuilder.INSTANCE,
                                           BuilderAdderOverrides.INSTANCE,
                                           BuilderSetterOverrides.INSTANCE));

    private StructureData() {
    }

    @Override
    public ClassSyntax.Builder typeSpec(ShapeCodegenState state) {
        var result = ClassSyntax.builder(state.symbol().getName())
                                .addAnnotation(DataPlugin.generatedBy())
                                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        var shape = state.shape().asStructureShape().orElseThrow();
        var superInterfaces = ImplementsKnowledgeIndex.of(state.model()).superInterfaces(shape);
        for (var superInterface : superInterfaces) {
            var superInterfaceClass = state.symbolProvider().toJavaTypeName(superInterface);
            result.addSuperInterface(superInterfaceClass);
        }
        if (shape.hasTrait(DocumentationTrait.class)) {
            var doc = shape.expectTrait(DocumentationTrait.class).getValue();
            result.javadoc("$L", JavadocExt.document(doc));
        }
        return result;
    }

    @Override
    public List<FieldSyntax> fieldsFor(ShapeCodegenState state, MemberShape member) {
        if (member.hasTrait(ConstTrait.class)) {
            return List.of();
        }
        return List.of(fieldFor(state, member));
    }

    public FieldSyntax fieldFor(ShapeCodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toJavaName(member);
        var type = symbolProvider.toJavaTypeName(member);
        return FieldSyntax.builder()
                          .name(name.toString())
                          .type(type)
                          .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                          .build();
    }

    @Override
    public List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
        return List.of(constructorFromBuilder(state));
    }

    public ConstructorMethodSyntax constructorFromBuilder(ShapeCodegenState state) {
        return ConstructorMethodSyntax.builder()
                                      .addModifier(Modifier.PRIVATE)
                                      .addParameter(builderJavaClassName(), "builder")
                                      .body(b -> constructorBody(state, b))
                                      .build();
    }

    private void constructorBody(ShapeCodegenState state, BodyBuilder builder) {
        var symbolProvider = state.symbolProvider();
        for (var member : state.shape().members()) {
            if (member.hasTrait(ConstTrait.class)) {
                continue;
            }
            var name = symbolProvider.toMemberName(member);
            var symbol = symbolProvider.toSymbol(member);
            if (StructureDataBuilder.usesBuilderReference(state, member)) {
                if (symbolProvider.isMemberRequired(member)) {
                    builder.addStatement("this.$1L = $2T.requireNonNull(builder.$1L.asPersistent(), $1S)", name, Objects.class);
                } else {
                    builder.addStatement("this.$1L = builder.$1L.asPersistent()", name);
                }
                continue;
            }
            var aggregateType = SymbolConstants.aggregateType(symbol);
            switch (aggregateType) {
                case LIST, SET, MAP -> memberValueFromBuilder(state, member, builder);
                default -> {
                    if (symbolProvider.isMemberRequired(member)) {
                        builder.addStatement("this.$L = $T.requireNonNull(builder.$L, $S)", name, Objects.class, name, name);
                    } else {
                        builder.addStatement("this.$1L = builder.$1L", name);
                    }
                }
            }
        }
    }

    @Override
    public List<MethodSyntax> methodsFor(ShapeCodegenState state, MemberShape member) {
        if (member.hasTrait(ConstTrait.class)) {
            return List.of(constAccessor(state, member));
        }
        return List.of(accessor(state, member));
    }

    private void memberValueFromBuilder(ShapeCodegenState state, MemberShape member, BodyBuilder builder) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberName(member);
        builder.addStatement("this.$1L = builder.$1L.asPersistent()", name);
    }

    private MethodSyntax accessor(ShapeCodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberName(member);
        var type = symbolProvider.toJavaTypeName(member);
        var result = MethodSyntax.builder(name)
                                 .addModifier(Modifier.PUBLIC)
                                 .returns(type)
                                 .addStatement("return this.$L", name);
        if (member.hasTrait(DocumentationTrait.class)) {
            var doc = member.expectTrait(DocumentationTrait.class).getValue();
            result.javadoc("$L", JavadocExt.document(doc));
        }
        return result.build();
    }

    private MethodSyntax constAccessor(ShapeCodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberName(member);
        var type = symbolProvider.toJavaTypeName(member);
        var builder = MethodSyntax.builder(name)
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(type);
        var refId = member.getTrait(ConstTrait.class).map(ConstTrait::getValue).orElse("");
        var shapeId = ShapeId.from(refId);
        var constMember = state.model().expectShape(shapeId, MemberShape.class);
        var containingSymbol = state.model().expectShape(constMember.getContainer(), EnumShape.class);
        builder.addStatement("return $T.$L", symbolProvider.toJavaTypeName(containingSymbol),
                             symbolProvider.toMemberName(constMember));
        return builder.build();
    }

    public ClassName builderJavaClassName() {
        return StructureCodegenUtils.BUILDER_TYPE;
    }

    @Override
    public List<FieldSyntax> extraFields(ShapeCodegenState state) {
        if (!cacheHashCode(state)) {
            return List.of();
        }
        return List.of(FieldSyntax.builder()
                                  .name("_hashCode")
                                  .type(int.class)
                                  // No need to add VOLATILE here, given that all the
                                  // values are immutable the computation will be
                                  // idempotent, and integer assignment is atomic.
                                  // Worst case the hash value will be computed more than
                                  // once but that's OK compared to having to add memory
                                  // barriers and messing the processors caches.
                                  .addModifier(Modifier.PRIVATE)
                                  .initializer(CodeBlock.from("0"))
                                  .build());
    }

    private boolean cacheHashCode(ShapeCodegenState state) {
        var aggregateCount = 0;
        var memberCount = 0;
        for (var member : state.shape().members()) {
            if (member.hasTrait(ConstTrait.class)) {
                continue;
            }
            var category = state.model()
                                .expectShape(member.getTarget())
                                .getType()
                                .getCategory();
            if (category == ShapeType.Category.AGGREGATE) {
                aggregateCount++;
            }
            memberCount++;
        }
        // Totally made up but as a heuristic skips some trivial cases.
        return (aggregateCount > 0 && memberCount > 2) || memberCount >= 5;
    }

    @Override
    public List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        var result = new ArrayList<MethodSyntax>();
        result.add(toBuilderMethod(state));
        result.add(equalsMethod(state));
        result.add(hashCodeMethod(state));
        result.add(toStringMethod(state));
        result.addAll(builderMethods(state));
        return result;
    }

    public MethodSyntax toBuilderMethod(ShapeCodegenState state) {
        var dataType = builderJavaClassName();
        return MethodSyntax.builder("toBuilder")
                           .addModifier(Modifier.PUBLIC)
                           .javadoc("Returns a new builder to modify a copy of this instance")
                           .returns(dataType)
                           .addStatement("return new $T(this)", dataType)
                           .build();
    }

    public MethodSyntax equalsMethod(ShapeCodegenState state) {
        var symbolProvider = state.symbolProvider();
        var result = MethodSyntax.builder("equals")
                                 .addAnnotation(Override.class)
                                 .addModifier(Modifier.PUBLIC)
                                 .returns(boolean.class)
                                 .addParameter(Object.class, "obj");
        result.body(body -> {
            body.ifStatement("this == obj", b -> b.addStatement("return true"));
            var className = className(state);
            body.ifStatement("obj == null || getClass() != obj.getClass()",
                             then -> then.addStatement("return false"));

            body.addStatement("$1T that = ($1T) obj", className);

            var expressionBuilder = CodeBlock.builder();
            var isFirst = true;
            expressionBuilder.addCode("return ");
            for (var member : state.shape().members()) {
                if (member.hasTrait(ConstTrait.class)) {
                    continue;
                }
                var name = symbolProvider.toMemberName(member);
                if (!isFirst) {
                    expressionBuilder.addCode("\n&& ");
                }
                if (symbolProvider.isMemberNullable(member)) {
                    expressionBuilder.addCode("$1T.equals(this.$2L, that.$2L)", Objects.class, name);
                } else {
                    expressionBuilder.addCode("this.$1L.equals(that.$1L)", name);
                }
                isFirst = false;
            }
            if (isFirst) {
                expressionBuilder.addCode("true");
            }
            body.addStatement(expressionBuilder.build());
        });
        return result.build();
    }

    MethodSyntax hashCodeMethod(ShapeCodegenState state) {
        var result = MethodSyntax.builder("hashCode")
                                 .addAnnotation(Override.class)
                                 .addModifier(Modifier.PUBLIC)
                                 .returns(int.class);

        if (cacheHashCode(state)) {
            result.ifStatement("_hashCode == 0", then -> {
                      addComputeHashCode(state, then);
                      then.addStatement("_hashCode = hashCode");
                  })
                  .addStatement("return _hashCode");
        } else {
            result.body(b -> {
                addComputeHashCode(state, b);
                b.addStatement("return hashCode");
            });
        }
        return result.build();
    }

    private AbstractBlockBuilder<?, ?> addComputeHashCode(ShapeCodegenState state, AbstractBlockBuilder<?, ?> builder) {
        var symbolProvider = state.symbolProvider();
        builder.addStatement("int hashCode = 17");
        for (var member : state.shape().members()) {
            var name = symbolProvider.toMemberName(member);
            if (member.hasTrait(ConstTrait.class)) {
                builder.addStatement("hashCode = 31 * hashCode + this.$L().hashCode()", name);
                continue;
            }
            if (symbolProvider.isMemberNullable(member)) {
                builder.addStatement("hashCode = 31 * hashCode + ($1L != null ? $1L.hashCode() : 0)", name);
            } else {
                builder.addStatement("hashCode = 31 * hashCode + $L.hashCode()", name);
            }
        }
        return builder;
    }

    MethodSyntax toStringMethod(ShapeCodegenState state) {
        var symbolProvider = state.symbolProvider();
        var methodBuilder = MethodSyntax.builder("toString")
                                        .addAnnotation(Override.class)
                                        .addModifier(Modifier.PUBLIC)
                                        .returns(String.class);
        var isFirst = true;
        var sensitiveIndex = SensitiveKnowledgeIndex.of(state.model());
        var toStringReturn = CodeBlock.builder();
        toStringReturn.addCode("return $S", state.shape().getId().getName() + "{");
        for (var member : state.shape().members()) {
            var literalName = new StringBuilder();
            var name = symbolProvider.toMemberName(member);
            toStringReturn.addCode("\n+ ");
            if (!isFirst) {
                literalName.append(", ");
            }
            literalName.append(member.getMemberName()).append(": ");
            if (sensitiveIndex.isSensitive(member)) {
                literalName.append("<*** REDACTED ***>");
                toStringReturn.addCode("$S", literalName);
            } else {
                if (member.hasTrait(ConstTrait.class)) {
                    toStringReturn.addCode("$S + $L()", literalName, name);
                } else {
                    toStringReturn.addCode("$S + $L", literalName, name);
                }
                isFirst = false;
            }
        }
        toStringReturn.addCode(" + $S", "}");
        methodBuilder.body(b -> b.addStatement(toStringReturn.build()));
        return methodBuilder.build();
    }

    List<MethodSyntax> builderMethods(ShapeCodegenState state) {
        var dataType = builderJavaClassName();
        var javadoc = CodeBlock.from("$L", JavadocExt.document("Creates a new builder"));
        var defaultBuilder = MethodSyntax.builder("builder")
                                         .javadoc(javadoc)
                                         .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                         .returns(dataType)
                                         .addStatement("return new $T()", dataType)
                                         .build();
        return List.of(defaultBuilder);
    }

    @Override
    public List<DirectiveToTypeSyntax> innerTypes(ShapeCodegenState state) {
        return INNER_TYPES;
    }
}
