package mx.sugus.braid.plugins.data;

import static mx.sugus.braid.plugins.data.StructureCodegenUtils.BUILDER_TYPE;
import static mx.sugus.braid.plugins.data.StructureCodegenUtils.getTargetTrait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.SymbolConstants;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.traits.ConstTrait;
import mx.sugus.braid.traits.UseBuilderReferenceTrait;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.traits.DefaultTrait;
import software.amazon.smithy.model.traits.DocumentationTrait;

public final class StructureDataBuilder implements DirectedClass {
    static final StructureDataBuilder INSTANCE = new StructureDataBuilder();

    private StructureDataBuilder() {
    }

    @Override
    public ClassName className(ShapeCodegenState state) {
        return BUILDER_TYPE;
    }

    @Override
    public ClassSyntax.Builder typeSpec(ShapeCodegenState state) {
        return ClassSyntax.builder("Builder")
                          .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);
    }

    @Override
    public List<FieldSyntax> fieldsFor(ShapeCodegenState state, MemberShape member) {
        if (member.hasTrait(ConstTrait.class)) {
            return Collections.emptyList();
        }
        return List.of(fieldFor(state, member));
    }

    @Override
    public List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
        return List.of(constructor(state), constructorFromData(state));
    }

    @Override
    public List<MethodSyntax> methodsFor(ShapeCodegenState state, MemberShape member) {
        if (member.hasTrait(ConstTrait.class)) {
            return Collections.emptyList();
        }
        var symbol = state.symbolProvider().toSymbol(member);
        var aggregateType = SymbolConstants.aggregateType(symbol);
        if (aggregateType == SymbolConstants.AggregateType.NONE) {
            if (usesBuilderReference(state, member)) {
                return List.of(setter(state, member), builderReferenceMutator(state, member));
            }
            return setters(state, member);
        }
        var result = new ArrayList<MethodSyntax>();
        result.addAll(setters(state, member));
        result.addAll(adder(state, member));
        return Collections.unmodifiableList(result);
    }

    @Override
    public List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        return List.of(buildMethod(state));
    }

    private MethodSyntax buildMethod(ShapeCodegenState state) {
        var symbolProvider = state.symbolProvider();
        var shapeType = symbolProvider.toJavaTypeName(state.shape());
        return MethodSyntax.builder("build")
                           .addModifier(Modifier.PUBLIC)
                           .returns(shapeType)
                           .body(b -> b.addStatement("return new $T(this)", shapeType))
                           .build();
    }

    private FieldSyntax fieldFor(ShapeCodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var type = symbolProvider.toJavaTypeName(member);
        var symbol = symbolProvider.toSymbol(member);
        var aggregateType = SymbolConstants.aggregateType(symbol);
        var finalType = switch (aggregateType) {
            case LIST, SET, MAP -> finalTypeForAggregate(type);
            default -> typeForMember(state, member);
        };
        var name = symbolProvider.toJavaName(member);
        return FieldSyntax.builder()
                          .type(finalType)
                          .name(name.toString())
                          .addModifier(Modifier.PRIVATE)
                          .build();
    }

    private TypeName finalTypeForAggregate(TypeName innerType) {
        return ParameterizedTypeName.from(CollectionBuilderReference.class, innerType);
    }

    private MethodSyntax builderReferenceMutator(ShapeCodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var target = state.model().expectShape(member.getTarget());
        var useReferenceTrait = target.getTrait(UseBuilderReferenceTrait.class).orElseThrow();
        var builderTypeId = useReferenceTrait.builderType();
        var builderType = ClassName.from(builderTypeId.getNamespace(), builderTypeId.getName());
        var name = symbolProvider.toMemberName(member);
        return MethodSyntax.builder(name)
                           .addModifier(Modifier.PUBLIC)
                           .addParameter(
                               ParameterizedTypeName.from(Consumer.class, builderType),
                               "mutator")
                           .returns(className(state))
                           .addStatement("mutator.accept(this.$L.asTransient())", name)
                           .addStatement("return this")
                           .build();
    }

    private ConstructorMethodSyntax constructor(ShapeCodegenState state) {
        var symbolProvider = state.symbolProvider();
        var builder = ConstructorMethodSyntax.builder();
        builder.body(b -> {
            for (var member : state.shape().members()) {
                var symbol = symbolProvider.toSymbol(member);
                var aggregateType = SymbolConstants.aggregateType(symbol);
                if (usesBuilderReference(state, member)) {
                    initializeBuilderReference(state, member, b, "null");
                } else if (member.hasTrait(DefaultTrait.class)) {
                    setDefaultValue(state, member, b);
                } else {
                    if (aggregateType != SymbolConstants.AggregateType.NONE) {
                        setEmptyValue(state, member, b);
                    }
                }
            }
        });
        return builder.build();
    }

    private ConstructorMethodSyntax constructorFromData(ShapeCodegenState state) {
        var symbolProvider = state.symbolProvider();
        var paramType = ClassName.toClassName(symbolProvider.toJavaTypeName(state.shape()));
        var builder = ConstructorMethodSyntax.builder()
                                             .addParameter(paramType, "data");

        builder.body(b -> {
            for (var member : state.shape().members()) {
                if (member.hasTrait(ConstTrait.class)) {
                    continue;
                }
                var name = symbolProvider.toMemberName(member);
                if (usesBuilderReference(state, member)) {
                    initializeBuilderReference(state, member, b, "data." + name);
                    continue;
                }
                var symbol = symbolProvider.toSymbol(member);
                var aggregateType = SymbolConstants.aggregateType(symbol);
                if (aggregateType == SymbolConstants.AggregateType.NONE) {
                    b.addStatement("this.$1L = data.$1L", name);
                } else {
                    setValueFromPersistent(state, member, b);
                }
            }
        });
        return builder.build();
    }

    private void setBuilderReferenceValue(
        ShapeCodegenState state,
        MemberShape member,
        BodyBuilder bodyBuilder
    ) {
        var name = state.symbolProvider().toMemberName(member);
        bodyBuilder.addStatement("this.$1L.setPersistent($1L)", name);
    }

    private List<MethodSyntax> setters(ShapeCodegenState state, MemberShape member) {
        return List.of(setter(state, member));
    }

    private MethodSyntax setter(ShapeCodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberName(member);
        var symbol = symbolProvider.toSymbol(member);
        var builder = MethodSyntax.builder(name)
                                  .addModifier(Modifier.PUBLIC)
                                  .addParameter(symbolProvider.toJavaTypeName(member), name)
                                  .returns(className(state));
        builder.body(b -> {
            var aggregateType = SymbolConstants.aggregateType(symbol);
            switch (aggregateType) {
                case LIST, SET -> setListValue(state, member, b);
                case MAP -> setMapValue(state, member, b);
                default -> setMemberValue(state, member, b);
            }
            b.addStatement("return this");
        });

        var doc = "Sets the value for `" + name + "`";
        if (member.hasTrait(DocumentationTrait.class)) {
            doc += "\n\n" + member.getTrait(DocumentationTrait.class).orElseThrow().getValue();
        }
        builder.javadoc("$L", JavadocExt.document(doc));
        return builder.build();
    }

    private void setMemberValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberName(member);
        if (usesBuilderReference(state, member)) {
            setBuilderReferenceValue(state, member, builder);
        } else {
            builder.addStatement("this.$1L = $1L", name);
        }
    }

    private void setListValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberJavaName(member);
        builder.addStatement("this.$L.clear()", name);
        builder.addStatement("this.$1L.asTransient().addAll($1L)", name);
    }

    private void setMapValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberJavaName(member);
        builder.addStatement("this.$L.clear()", name);
        builder.addStatement("this.$1L.asTransient().putAll($1L)", name);
    }

    private List<MethodSyntax> adder(ShapeCodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(member);
        var aggregateType = SymbolConstants.aggregateType(symbol);
        return switch (aggregateType) {
            case LIST, SET -> collectionAdder(state, member);
            case MAP -> mapAdder(state, member);
            default -> throw new IllegalArgumentException("cannot create adder for " + member);
        };
    }

    private List<MethodSyntax> collectionAdder(ShapeCodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var methodName = symbolProvider.toJavaSingularName(member, "add");
        var builder = MethodSyntax.builder(methodName.toString())
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(className(state));
        addValueParam(state, member, builder);
        var name = symbolProvider.toMemberName(member);
        if (member.hasTrait(ConstTrait.class)) {
            builder.body(b -> b.addStatement("throw new $T($S)", UnsupportedOperationException.class,
                                             "Member " + name + " value is constant"));
            return List.of(builder.build());
        }
        var symbol = symbolProvider.toSymbol(member);
        var aggregateType = SymbolConstants.aggregateType(symbol);
        builder.body(body -> {
            switch (aggregateType) {
                case LIST, SET -> {
                    var valueArgument = symbolProvider.toJavaSingularName(member).toString();
                    addValue(state, member, body, List.of(valueArgument));
                }
                default -> throw new IllegalArgumentException("cannot create adder for " + member);
            }
            body.addStatement("return this");
        });
        var doc = "Adds a single value for `" + name + "`";
        builder.javadoc("$L", JavadocExt.document(doc));
        var defaultAdder = builder.build();
        var result = new ArrayList<MethodSyntax>();
        result.add(defaultAdder);
        return result;
    }

    private List<MethodSyntax> mapAdder(ShapeCodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberJavaName(member);
        var methodName = symbolProvider.toJavaSingularName(member, "put").toString();
        var builder = MethodSyntax.builder(methodName)
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(className(state));
        addKeyValueParam(state, member, builder);
        if (member.hasTrait(ConstTrait.class)) {
            builder.addStatement("throw new $T($S)", UnsupportedOperationException.class,
                                 "Member `" + name + "` value is constant");
            return List.of(builder.build());

        }
        builder.body(body -> {
            addKeyValue(state, member, body);
            body.addStatement("return this");
        });

        return List.of(builder.build());
    }

    private void addValueParam(ShapeCodegenState state, MemberShape member, MethodSyntax.Builder builder) {
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(member).getReferences().get(0).getSymbol();
        var paramName = symbolProvider.toJavaSingularName(member).toString();
        builder.addParameter(symbolProvider.toJavaTypeName(symbol), paramName);
    }

    private void addKeyValueParam(ShapeCodegenState state, MemberShape member, MethodSyntax.Builder builder) {
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(member);
        var paramName = symbolProvider.toJavaSingularName(member).toString();
        var references = symbol.getReferences();
        builder.addParameter(symbolProvider.toJavaTypeName(references.get(0).getSymbol()), "key");
        builder.addParameter(symbolProvider.toJavaTypeName(references.get(1).getSymbol()), paramName);
    }

    private void addValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder, List<String> values) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberJavaName(member);
        for (var value : values) {
            builder.addStatement("this.$L.asTransient().add($L)", name.toString(), value);
        }
    }

    private void addKeyValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder) {
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberJavaName(member);
        var paramName = symbolProvider.toJavaSingularName(member).toString();
        builder.addStatement("this.$L.asTransient().put(key, $L)", name.toString(), paramName);
    }

    static TypeName typeForMember(ShapeCodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var target = state.model().expectShape(member.getTarget());
        var type = symbolProvider.toJavaTypeName(member);
        var useReferenceTraitOpt = target.getTrait(UseBuilderReferenceTrait.class);
        if (useReferenceTraitOpt.isPresent()) {
            var useReferenceTrait = useReferenceTraitOpt.orElseThrow();
            var builderTypeId = useReferenceTrait.builderType();
            var builderType = ClassName.from(builderTypeId.getNamespace(), builderTypeId.getName());
            return ParameterizedTypeName.from(BuilderReference.class, type, builderType);
        }
        return type;
    }

    static boolean usesBuilderReference(ShapeCodegenState state, MemberShape member) {
        return getTargetTrait(UseBuilderReferenceTrait.class, state, member) != null;
    }

    static void initializeBuilderReference(
        ShapeCodegenState state,
        MemberShape member,
        BodyBuilder bodyBuilder,
        String initializer
    ) {
        var target = state.model().expectShape(member.getTarget());
        var useReferenceTrait = target.getTrait(UseBuilderReferenceTrait.class).orElseThrow();
        var fromPersistent = useReferenceTrait.fromPersistent();

        var implementingClass = ClassName.from(fromPersistent.getNamespace(), fromPersistent.getName());
        var name = state.symbolProvider().toMemberName(member);
        bodyBuilder.addStatement("this.$L = $T.$L($L)", name, implementingClass,
                                 fromPersistent.getMember().orElseThrow(), initializer);
    }

    static void setDefaultValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder) {
        var defaultValue = member.expectTrait(DefaultTrait.class);
        var defaultValueNode = defaultValue.toNode();
        var symbolProvider = state.symbolProvider();
        var name = symbolProvider.toMemberName(member);
        var shapeType = state.model().expectShape(member.getTarget()).getType();
        switch (shapeType) {
            case BYTE, SHORT, INTEGER, LONG, FLOAT, DOUBLE:
                builder.addStatement("this.$L = $L", name, defaultValueNode.expectNumberNode().getValue());
                break;
            case STRING:
                builder.addStatement("this.$L = $S", name, defaultValueNode.expectStringNode().getValue());
                break;
            case BOOLEAN:
                builder.addStatement("this.$L = $L", name, defaultValueNode.expectBooleanNode().getValue());
                break;
            default:
                throw new UnsupportedOperationException("Unsupported type: " + shapeType + " for default value");
        }
    }

    static void setEmptyValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder) {
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(member);
        var name = symbolProvider.toMemberName(member);
        var aggregateType = SymbolConstants.aggregateType(symbol);
        var emptyReferenceBuilder = symbolProvider.emptyReferenceBuilder(aggregateType);
        builder.addStatement("this.$L = $T.$L()", name, CollectionBuilderReference.class, emptyReferenceBuilder);
    }

    static void setValueFromPersistent(ShapeCodegenState state, MemberShape member, BodyBuilder builder) {
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(member);
        var name = symbolProvider.toMemberName(member);
        var aggregateType = SymbolConstants.aggregateType(symbol);
        var init = symbolProvider.initReferenceBuilder(aggregateType);
        builder.addStatement("this.$1L = $2T.$3L(data.$1L)", name, CollectionBuilderReference.class,
                             init);
    }

    static TypeName finalTypeForAggregate(ShapeCodegenState state, MemberShape member) {
        var symbolProvider = state.symbolProvider();
        var innerType = symbolProvider.toJavaTypeName(member);
        return ParameterizedTypeName.from(CollectionBuilderReference.class, innerType);
    }
}
