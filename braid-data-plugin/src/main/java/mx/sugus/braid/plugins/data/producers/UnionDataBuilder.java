package mx.sugus.braid.plugins.data.producers;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.jsyntax.CaseClause;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.DefaultCaseClause;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.SwitchStatement;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.plugins.data.symbols.SymbolConstants;
import mx.sugus.braid.traits.UseBuilderReferenceTrait;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.traits.DocumentationTrait;

public final class UnionDataBuilder implements DirectedClass {

    @Override
    public ClassName className(ShapeCodegenState state) {
        return CodegenUtils.BUILDER_TYPE;
    }

    @Override
    public ClassSyntax.Builder typeSpec(ShapeCodegenState state) {
        return ClassSyntax.builder("Builder")
                          .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);
    }

    @Override
    public List<FieldSyntax> fieldsFor(ShapeCodegenState state, MemberShape member) {
        return List.of();
    }

    @Override
    public List<FieldSyntax> extraFields(ShapeCodegenState state) {
        return List.of(FieldSyntax.mutableFrom(Object.class, "variantValue"),
                       FieldSyntax.mutableFrom(UnionVariantTagEnumData.VARIANT_TAG_NAME, "variantTag"));
    }

    @Override
    public List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
        return List.of(constructor(), constructorFromData(state));
    }

    @Override
    public List<MethodSyntax> extraMethods(ShapeCodegenState state) {
        return List.of(getValueMethod(state), buildMethod(state));
    }

    private MethodSyntax getValueMethod(ShapeCodegenState state) {
        var builder = MethodSyntax.builder("getValue")
                                  .returns(Object.class)
                                  .body(b -> getValueBody(state, b));
        return builder.build();
    }

    private void getValueBody(ShapeCodegenState state, BodyBuilder body) {
        if (!usesReferenceBuilders(state)) {
            body.addStatement("return this.variantValue");
            return;
        }
        var memberSwitch = SwitchStatement.builder()
                                          .expression(CodeBlock.from("this.variantTag"));
        for (var member : state.shape().members()) {
            var unionVariant = Utils.toSourceName(state, member, Name.Convention.SCREAM_CASE).toString();
            memberSwitch.addCase(CaseClause.builder()
                                           .addLabel(CodeBlock.from("$L", unionVariant))
                                           .addStatement("return $C", getValueForMember(state, member))
                                           .build());
        }
        memberSwitch.defaultCase(DefaultCaseClause.builder()
                                                  .addStatement("return this.variantValue")
                                                  .build());
        body.addStatement(memberSwitch.build());
    }

    private CodeBlock getValueForMember(ShapeCodegenState state, MemberShape member) {
        var name = Utils.toJavaName(state, member);
        var aggregateType = Utils.aggregateType(state, member);
        var usesReference = aggregateType != SymbolConstants.AggregateType.NONE || usesBuilderReference(state, member);
        if (usesReference) {
            return CodeBlock.builder().addCode("$L().asPersistent()", name).build();
        }
        return CodeBlock.builder().addCode("this.variantValue").build();
    }

    private boolean usesReferenceBuilders(ShapeCodegenState state) {
        for (var member : state.shape().members()) {
            var aggregateType = Utils.aggregateType(state, member);
            if (aggregateType != SymbolConstants.AggregateType.NONE || usesBuilderReference(state, member)) {
                return true;
            }
        }
        return false;
    }

    private MethodSyntax buildMethod(ShapeCodegenState state) {
        var shapeType = Utils.toJavaTypeName(state, state.shape());
        return MethodSyntax.builder("build")
                           .addModifier(Modifier.PUBLIC)
                           .returns(shapeType)
                           .addStatement("return new $T(this)", shapeType)
                           .build();
    }

    @Override
    public List<MethodSyntax> methodsFor(ShapeCodegenState state, MemberShape member) {
        var aggregateType = Utils.aggregateType(state, member);
        if (aggregateType == SymbolConstants.AggregateType.NONE) {
            if (usesBuilderReference(state, member)) {
                return List.of(setter(state, member), accessor(state, member), builderReferenceMutator(state, member));
            }
            return List.of(setter(state, member));
        }
        var result = new ArrayList<MethodSyntax>();
        result.add(accessor(state, member));
        result.add(setter(state, member));
        result.addAll(adder(state, member));
        return Collections.unmodifiableList(result);
    }

    private MethodSyntax accessor(ShapeCodegenState state, MemberShape member) {
        var unionVariant = Utils.toSourceName(state, member, Name.Convention.SCREAM_CASE).toString();
        var name = Utils.toJavaName(state, member);
        var type = Utils.toBuilderTypeName(state, member);
        var accessor = MethodSyntax.builder(Utils.toGetterName(state, member).toString())
                                   .addModifier(Modifier.PRIVATE)
                                   .returns(type)
                                   .ifStatement("this.variantTag != VariantTag.$L", unionVariant, then -> {
                                       then.addStatement("this.variantTag = VariantTag.$L", unionVariant);
                                       var empty = Utils.toBuilderInitExpression(state, member);
                                       then.addStatement("$T $L = $C",
                                                         type,
                                                         name,
                                                         empty);
                                       then.addStatement("this.variantValue = $L", name);
                                       then.addStatement("return $L", name);
                                   }, otherwise -> {
                                       otherwise.addStatement("return ($T) this.variantValue", type);
                                   })
                                   .build();
        return accessor;
    }

    private MethodSyntax setter(ShapeCodegenState state, MemberShape member) {
        var name = Utils.toJavaName(state, member);
        var builder = MethodSyntax.builder(name.toString())
                                  .addModifier(Modifier.PUBLIC)
                                  .addParameter(Utils.toJavaTypeName(state, member), name.toString())
                                  .returns(className(state));
        builder.body(b -> {
            var aggregateType = Utils.aggregateType(state, member);
            switch (aggregateType) {
                case LIST, SET -> setListValue(state, member, b);
                case MAP -> setMapValue(state, member, b);
                default -> setMemberValue(state, member, b);
            }
            b.addStatement("return this");
        });

        var prefix = "Sets the value for `" + name + "`";
        var doc = member.getTrait(DocumentationTrait.class)
                        .map(DocumentationTrait::getValue)
                        .map(value -> prefix + "\n\n" + value)
                        .orElse(prefix);
        builder.javadoc(JavadocExt.document(doc));
        return builder.build();
    }

    private void setListValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder) {
        var name = Utils.toJavaName(state, member);
        var type = StructureDataBuilder.finalTypeForAggregate(state, member);
        builder.addStatement("$T tmp = $L()", type, name);
        builder.addStatement("tmp.clear()");
        builder.addStatement("tmp.asTransient().addAll($L)", name);
    }

    private void setMapValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder) {
        var name = Utils.toJavaName(state, member);
        var type = StructureDataBuilder.finalTypeForAggregate(state, member);
        builder.addStatement("$T tmp = $L()", type, name);
        builder.addStatement("tmp.clear()");
        builder.addStatement("tmp.asTransient().putAll($L)", name);
    }

    private List<MethodSyntax> adder(ShapeCodegenState state, MemberShape member) {
        var aggregateType = Utils.aggregateType(state, member);
        return switch (aggregateType) {
            case LIST, SET -> collectionAdder(state, member);
            case MAP -> mapAdder(state, member);
            default -> throw new IllegalArgumentException("cannot create adder for " + member);
        };
    }

    private List<MethodSyntax> collectionAdder(ShapeCodegenState state, MemberShape member) {
        var name = Utils.toJavaName(state, member);
        var methodName = Utils.toAdderName(state, member).toString();
        var builder = MethodSyntax.builder(methodName)
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(className(state));
        addValueParam(state, member, builder);
        var aggregateType = Utils.aggregateType(state, member);
        builder.body(body -> {
            switch (aggregateType) {
                case LIST, SET -> {
                    var valueArgument = name.toSingularSpelling().toString();
                    addValue(state, member, body, List.of(valueArgument));
                }
                default -> throw new IllegalArgumentException("cannot create adder for " + member);
            }
            body.addStatement("return this");
        });
        var doc = "Adds a single value for `" + name + "`";
        builder.javadoc(JavadocExt.document(doc));
        var defaultAdder = builder.build();
        var result = new ArrayList<MethodSyntax>();
        result.add(defaultAdder);
        return Collections.unmodifiableList(result);
    }

    private List<MethodSyntax> mapAdder(ShapeCodegenState state, MemberShape member) {
        var methodName = Utils.toAdderName(state, member).toString();
        var builder = MethodSyntax.builder(methodName)
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(className(state));
        addKeyValueParam(state, member, builder);
        builder.body(body -> {
            addKeyValue(state, member, body);
            body.addStatement("return this");
        });

        return List.of(builder.build());
    }

    private void addValueParam(ShapeCodegenState state, MemberShape member, MethodSyntax.Builder builder) {
        var paramName = Utils.toJavaSingularName(state, member).toString();
        var references = state.symbolProvider().toSymbol(member).getReferences();
        builder.addParameter(Utils.toJavaTypeName(state, references.get(0).getSymbol()), paramName);
    }

    private void addKeyValueParam(ShapeCodegenState state, MemberShape member, MethodSyntax.Builder builder) {
        var name = Utils.toJavaName(state, member);
        var paramName = name.toSingularSpelling().toString();
        var symbolProvider = state.symbolProvider();
        var symbol = symbolProvider.toSymbol(member);
        var references = symbol.getReferences();
        var keyParamType = Utils.toJavaTypeName(state, references.get(0).getSymbol());
        var valueParamType = Utils.toJavaTypeName(state, references.get(1).getSymbol());
        builder.addParameter(keyParamType, "key");
        builder.addParameter(valueParamType, paramName);
    }

    private void addValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder, List<String> values) {
        var name = Utils.toJavaName(state, member);
        for (var value : values) {
            builder.addStatement("$L().asTransient().add($L)", name, value);
        }
    }

    private MethodSyntax builderReferenceMutator(ShapeCodegenState state, MemberShape member) {
        var builderType = Utils.toRefrenceBuilderBuilderTypeName(state, member);
        var name = Utils.toJavaName(state, member);
        var builder = MethodSyntax.builder(name.toString());
        builder.addModifier(Modifier.PUBLIC)
               .addParameter(
                   ParameterizedTypeName.from(Consumer.class, builderType),
                   "mutator")
               .returns(className(state));
        builder.addStatement("mutator.accept($L().asTransient())", name);
        builder.addStatement("return this");
        return builder.build();
    }

    private void setMemberValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder) {
        var name = Utils.toJavaName(state, member);
        if (usesBuilderReference(state, member)) {
            setBuilderReferenceValue(state, member, builder);
        } else {
            var unionVariant = Utils.toSourceName(state, member, Name.Convention.SCREAM_CASE);
            builder.addStatement("this.variantTag = VariantTag.$1L", unionVariant);
            builder.addStatement("this.variantValue = $L", name);
        }
    }

    private void setBuilderReferenceValue(
        ShapeCodegenState state,
        MemberShape member,
        BodyBuilder bodyBuilder
    ) {
        var name = Utils.toJavaName(state, member);
        bodyBuilder.addStatement("$1L().setPersistent($1L)", name);
    }

    private void addKeyValue(ShapeCodegenState state, MemberShape member, BodyBuilder builder) {
        var name = Utils.toJavaName(state, member);
        var paramName = name.toSingularSpelling().toString();
        builder.addStatement("$L().asTransient().put(key, $L)", name.toString(), paramName);
    }

    ConstructorMethodSyntax constructor() {
        return ConstructorMethodSyntax.builder()
                                      .addStatement("this.variantTag = null")
                                      .addStatement("this.variantValue = VariantTag.UNKNOWN_TO_VERSION")
                                      .build();
    }

    ConstructorMethodSyntax constructorFromData(ShapeCodegenState state) {
        var symbolProvider = state.symbolProvider();
        var shapeSymbol = symbolProvider.toSymbol(state.shape());
        var paramType = ClassName.from(shapeSymbol.getNamespace(), shapeSymbol.getName());
        var builder = ConstructorMethodSyntax.builder()
                                             .addParameter(paramType, "data");
        var withReferenceMember = membersUsingBuilderReference(state);
        if (withReferenceMember.isEmpty()) {
            builder.addStatement("this.variantTag = data.variantTag")
                   .addStatement("this.variantValue = data.variantValue");
        } else {
            builder.body(b -> initBuilderReferences(state, withReferenceMember, b));
        }
        return builder.build();
    }

    private void initBuilderReferences(
        ShapeCodegenState state,
        List<MemberShape> withReferenceMember,
        BodyBuilder builder
    ) {
        builder.addStatement("this.variantTag = data.variantTag");
        var typeSwitch = SwitchStatement.builder().expression(CodeBlock.from("data.variantTag"));
        for (var member : withReferenceMember) {
            var unionVariant = Utils.toSourceName(state, member, Name.Convention.SCREAM_CASE).toString();
            typeSwitch.addCase(CaseClause.builder()
                                         .addLabel(CodeBlock.from("$L", unionVariant))
                                         .body(b -> {
                                             setValueFromPersistent(state, member, b);
                                             b.addStatement("break");
                                         })
                                         .build());
        }
        typeSwitch.defaultCase(DefaultCaseClause.builder()
                                                .addStatement("this.variantValue = data.variantValue")
                                                .build());
        builder.addStatement(typeSwitch.build());
    }

    private List<MemberShape> membersUsingBuilderReference(ShapeCodegenState state) {
        var shape = state.shape().asUnionShape().orElseThrow();
        List<MemberShape> result = new ArrayList<>();
        for (var member : shape.getAllMembers().values()) {
            var aggregateType = Utils.aggregateType(state, member);
            if (aggregateType != SymbolConstants.AggregateType.NONE) {
                result.add(member);
            }
            if (usesBuilderReference(state, member)) {
                result.add(member);
            }
        }
        return result;
    }

    static boolean usesBuilderReference(ShapeCodegenState state, MemberShape member) {
        var target = state.model().expectShape(member.getTarget());
        return target.hasTrait(UseBuilderReferenceTrait.class);
    }

    static void setValueFromPersistent(ShapeCodegenState state, MemberShape member, BodyBuilder builder) {
        builder.addStatement("this.variantValue = $C", Utils.builderUnionInitFromDataExpression(state, member));
    }
}
