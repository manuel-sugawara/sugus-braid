package mx.sugus.braid.plugins.data.producers;

import static mx.sugus.braid.plugins.data.producers.CodegenUtils.equalsTemplate;
import static mx.sugus.braid.plugins.data.producers.CodegenUtils.hashCodeTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mx.sugus.braid.core.ImplementsKnowledgeIndex;
import mx.sugus.braid.core.SensitiveKnowledgeIndex;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.util.Lazy;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.Javadoc;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.Modifier;
import mx.sugus.braid.jsyntax.PrimitiveTypeName;
import mx.sugus.braid.jsyntax.block.AbstractBlockBuilder;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.plugins.data.DataPlugin;
import mx.sugus.braid.traits.ConstTrait;
import software.amazon.smithy.model.shapes.EnumShape;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.ShapeType;
import software.amazon.smithy.model.traits.DocumentationTrait;

public final class StructureData implements DirectedClass {
    static final Lazy<Javadoc> TO_BUILDER_DOC = new Lazy<>(
        () -> Javadoc.builder()
                     .body("Returns a new builder to modify a copy of this instance.")
                     .returns("A new builder to modify a copy of this instance.")
                     .build());
    static final Lazy<Javadoc> BUILDER_DOC = new Lazy<>(
        () -> Javadoc.builder()
                     .body("Creates a new builder to create instances of this class.")
                     .returns("A new builder to create instances of this class.")
                     .build());
    static final DirectedClass INSTANCE = new StructureData();

    private static final List<DirectiveToTypeSyntax> INNER_TYPES =
        List.of(StructureDataBuilder.INSTANCE);

    private StructureData() {
    }

    @Override
    public ClassSyntax.Builder typeSpec(ShapeCodegenState state) {
        var builder = ClassSyntax.builder(state.symbol().getName())
                                 .addAnnotation(Utils.generatedBy(DataPlugin.ID))
                                 .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        var shape = state.shape().asStructureShape().orElseThrow();
        var superInterfaces = ImplementsKnowledgeIndex.of(state.model()).superInterfaces(shape);
        for (var superInterface : superInterfaces) {
            var superInterfaceClass = Utils.toJavaTypeName(state, superInterface);
            builder.addSuperInterface(superInterfaceClass);
        }
        shape.getTrait(DocumentationTrait.class)
             .map(DocumentationTrait::getValue)
             .map(JavadocExt::document)
             .map(builder::javadoc);
        return builder;
    }

    @Override
    public List<FieldSyntax> fieldsFor(ShapeCodegenState state, MemberShape member) {
        if (member.hasTrait(ConstTrait.class)) {
            return List.of();
        }
        return List.of(fieldFor(state, member));
    }

    public FieldSyntax fieldFor(ShapeCodegenState state, MemberShape member) {
        var name = Utils.toJavaName(state, member);
        var type = Utils.toJavaTypeName(state, member);
        return FieldSyntax.from(type, name.toString());
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
        for (var member : state.shape().members()) {
            for (var stmt : Utils.dataInitFromBuilder(state, member).statements()) {
                builder.addStatement(stmt);
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

    static MethodSyntax accessor(ShapeCodegenState state, MemberShape member) {
        var name = Utils.toJavaName(state, member);
        var type = Utils.toJavaTypeName(state, member);
        var doc = Javadoc.builder()
                         .body(member.getTrait(DocumentationTrait.class).map(DocumentationTrait::getValue).orElse(null))
                         .returns("The value of the `$L` member", member.getMemberName())
                         .build();
        var builder = MethodSyntax.builder(Utils.toGetterName(state, member).toString())
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(type)
                                  .javadoc(doc)
                                  .addStatement("return this.$L", name);
        return builder.build();
    }

    private MethodSyntax constAccessor(ShapeCodegenState state, MemberShape member) {
        var type = Utils.toJavaTypeName(state, member);
        var builder = MethodSyntax.builder(Utils.toGetterName(state, member).toString())
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(type);
        var refId = member.expectTrait(ConstTrait.class).getValue();
        var shapeId = ShapeId.from(refId);
        var constMember = state.model().expectShape(shapeId, MemberShape.class);
        var containingSymbol = state.model().expectShape(constMember.getContainer(), EnumShape.class);
        builder.addStatement("return $T.$L", Utils.toJavaTypeName(state, containingSymbol),
                             Utils.toJavaName(state, constMember));
        return builder.build();
    }

    public ClassName builderJavaClassName() {
        return CodegenUtils.BUILDER_TYPE;
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
                                  // once but that's OK.
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
        // Totally made up, but as a heuristic skips some trivial cases.
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
                           .javadoc(toBuilderDoc())
                           .returns(dataType)
                           .addStatement("return new $T(this)", dataType)
                           .build();
    }

    public MethodSyntax equalsMethod(ShapeCodegenState state) {
        var builder = equalsTemplate();
        builder.body(body -> {
            var className = className(state);
            body.addStatement("$1T that = ($1T) other", className);
            var expressionBuilder = CodeBlock.builder();
            var isFirst = true;
            expressionBuilder.addCode("return ");
            for (var member : state.shape().members()) {
                var type = Utils.toJavaTypeName(state, member);
                if (member.hasTrait(ConstTrait.class)) {
                    continue;
                }
                var name = Utils.toJavaName(state, member);
                if (!isFirst) {
                    expressionBuilder.addCode("\n&& ");
                }
                if (type instanceof PrimitiveTypeName c) {
                    switch (c.name()) {
                        case CHAR, BYTE, SHORT, INT, LONG, BOOLEAN:
                            expressionBuilder.addCode("this.$1L == that.$1L", name);
                            break;
                        case FLOAT:
                            expressionBuilder.addCode("Float.compare(this.$1L, that.$1L) == 0", name);
                            break;
                        case DOUBLE:
                            expressionBuilder.addCode("Double.compare(this.$1L, that.$1L) == 0", name);
                            break;
                        default:
                            throw new UnsupportedOperationException("Unsupported primitive: " + c);
                    }
                } else if (Utils.isImplicitlyRequired(state, member)) {
                    expressionBuilder.addCode("this.$1L.equals(that.$1L)", name);
                } else {
                    expressionBuilder.addCode("$1T.equals(this.$2L, that.$2L)", Objects.class, name);
                }
                isFirst = false;
            }
            if (isFirst) {
                // structure without members, just return true.
                expressionBuilder.addCode("true");
            }
            body.addStatement(expressionBuilder.build());
        });
        return builder.build();
    }

    MethodSyntax hashCodeMethod(ShapeCodegenState state) {
        var builder = hashCodeTemplate();
        if (cacheHashCode(state)) {
            builder.ifStatement("_hashCode == 0", then -> {
                      addComputeHashCode(state, then);
                      then.addStatement("_hashCode = hashCode");
                  })
                  .addStatement("return _hashCode");
        } else {
            builder.body(b -> {
                addComputeHashCode(state, b);
                b.addStatement("return hashCode");
            });
        }
        return builder.build();
    }

    private AbstractBlockBuilder<?, ?> addComputeHashCode(ShapeCodegenState state, AbstractBlockBuilder<?, ?> builder) {
        builder.addStatement("int hashCode = 17");
        for (var member : state.shape().members()) {
            var name = Utils.toJavaName(state, member);
            var type = Utils.toJavaTypeName(state, member);
            if (member.hasTrait(ConstTrait.class)) {
                builder.addStatement("hashCode = 31 * hashCode + this.$L().hashCode()", Utils.toGetterName(state, member));
                continue;
            }
            if (type instanceof PrimitiveTypeName c) {
                switch (c.name()) {
                    case CHAR, BYTE, SHORT, INT:
                        builder.addStatement("hashCode = 31 * hashCode + $L", name);
                        break;
                    case LONG:
                        builder.addStatement("hashCode = 31 * hashCode + Long.hashCode($L)", name);
                        break;
                    case FLOAT:
                        builder.addStatement("hashCode = 31 * hashCode + Float.hashCode($L)", name);
                        break;
                    case DOUBLE:
                        builder.addStatement("hashCode = 31 * hashCode + Double.hashCode($L)", name);
                        break;
                    case BOOLEAN:
                        builder.addStatement("hashCode = 31 * hashCode + Boolean.hashCode($L)", name);
                        break;
                    default:
                        throw new UnsupportedOperationException("Unsupported primitive: " + c);
                }
            } else if (Utils.isImplicitlyRequired(state, member)) {
                builder.addStatement("hashCode = 31 * hashCode + $L.hashCode()", name);
            } else {
                builder.addStatement("hashCode = 31 * hashCode + ($1L != null ? $1L.hashCode() : 0)", name);
            }
        }
        return builder;
    }

    MethodSyntax toStringMethod(ShapeCodegenState state) {
        var sensitiveIndex = SensitiveKnowledgeIndex.of(state.model());
        if (sensitiveIndex.isSensitive(state.shape())) {
            return CodegenUtils.toStringForSensitive();
        }
        var builder = CodegenUtils.toStringTemplate();
        var isFirst = true;
        var toStringReturn = CodeBlock.builder();
        toStringReturn.addCode("return $S", state.shape().getId().getName() + "{");
        for (var member : state.shape().members()) {
            var literalName = new StringBuilder();
            var name = Utils.toJavaName(state, member);
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
                    toStringReturn.addCode("$S + $L()", literalName, Utils.toGetterName(state, member));
                } else {
                    toStringReturn.addCode("$S + $L", literalName, name);
                }
                isFirst = false;
            }
        }
        toStringReturn.addCode(" + $S", "}");
        builder.body(b -> b.addStatement(toStringReturn.build()));
        return builder.build();
    }

    List<MethodSyntax> builderMethods(ShapeCodegenState state) {
        var dataType = builderJavaClassName();
        var builder = MethodSyntax.builder("builder")
                                  .javadoc(builderDoc())
                                  .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                  .returns(dataType)
                                  .addStatement("return new $T()", dataType)
                                  .build();
        return List.of(builder);
    }

    @Override
    public List<DirectiveToTypeSyntax> innerTypes(ShapeCodegenState state) {
        return INNER_TYPES;
    }

    public static Javadoc toBuilderDoc() {
        return TO_BUILDER_DOC.get();
    }

    public static Javadoc builderDoc() {
        return BUILDER_DOC.get();
    }
}
