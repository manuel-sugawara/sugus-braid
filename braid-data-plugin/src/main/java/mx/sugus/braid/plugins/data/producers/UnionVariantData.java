package mx.sugus.braid.plugins.data.producers;

import static mx.sugus.braid.plugins.data.producers.CodegenUtils.equalsTemplate;
import static mx.sugus.braid.plugins.data.producers.CodegenUtils.hashCodeTemplate;
import static mx.sugus.braid.plugins.data.producers.StructureData.accessor;

import java.util.List;
import java.util.Objects;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.core.SensitiveKnowledgeIndex;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.core.util.Name;
import mx.sugus.braid.jsyntax.Annotation;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.MemberValue;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.PrimitiveTypeName;
import mx.sugus.braid.jsyntax.TypeKind;
import mx.sugus.braid.jsyntax.TypeVariableTypeName;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.plugins.data.DataPlugin;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.traits.DocumentationTrait;

public final class UnionVariantData implements DirectedClass {
    private final MemberShape member;

    public UnionVariantData(MemberShape member) {
        this.member = member;
    }

    @Override
    public ClassSyntax.Builder typeSpec(ShapeCodegenState state) {
        var name = memberVariantName(state, member);
        var superClass = Utils.toJavaTypeName(state, state.shape());
        var builder = ClassSyntax.builder(name.toString())
                                 .superClass(superClass)
                                 .addAnnotation(Utils.generatedBy(DataPlugin.ID))
                                 .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);
        member.getTrait(DocumentationTrait.class)
              .map(DocumentationTrait::getValue)
              .map(JavadocExt::document)
              .map(builder::javadoc);
        return builder;
    }

    @Override
    public List<FieldSyntax> fieldsFor(ShapeCodegenState state, MemberShape member) {
        if (this.member != member) {
            return List.of();
        }
        return List.of(fieldFor(state, member));
    }

    @Override
    public List<ConstructorMethodSyntax> constructors(ShapeCodegenState state) {
        return List.of(constructorFromValue(state));
    }

    public ConstructorMethodSyntax constructorFromValue(ShapeCodegenState state) {
        var name = Utils.toJavaName(state, member);
        var type = Utils.toJavaTypeName(state, member);
        var builder = ConstructorMethodSyntax.builder()
                                             .addModifier(Modifier.PRIVATE)
                                             .addParameter(type, name.toString());

        if (type.kind() == TypeKind.PRIMITIVE) {
            builder.addStatement("this.$1L = $1L", name);
        } else {
            builder.addStatement("this.$1L = $2T.requireNonNull($1L, $1S)", name, Objects.class);
        }
        return builder.build();
    }

    @Override
    public List<MethodSyntax> methodsFor(ShapeCodegenState state, MemberShape member) {
        if (this.member != member) {
            return List.of();
        }
        return List.of(accessor(state, member),
                       variantValue(state, member),
                       variantTag(state, member),
                       toStringMethod(state),
                       equalsMethod(state),
                       hashCodeMethod(state));
    }

    MethodSyntax variantValue(ShapeCodegenState state, MemberShape member) {
        var name = Utils.toJavaName(state, member);
        var type = Utils.toJavaTypeName(state, member);
        var builder = MethodSyntax.builder("variantValue")
                                  .addAnnotation(CodegenUtils.override())
                                  .addAnnotation(CodegenUtils.suppressUnchecked())
                                  .addModifier(Modifier.PUBLIC)
                                  .addTypeParam("T")
                                  .returns(TypeVariableTypeName.from("T"));

        if (type.kind() == TypeKind.PRIMITIVE) {
            builder.addStatement("return (T) (Object) this.$L", name);
        } else {
            builder.addStatement("return (T) this.$L", name);
        }
        return builder.build();
    }

    MethodSyntax variantTag(ShapeCodegenState state, MemberShape member) {
        var unionVariant = Utils.toSourceName(state, member, Name.Convention.SCREAM_CASE).toString();
        return MethodSyntax.builder("variantTag")
                           .addAnnotation(CodegenUtils.override())
                           .addModifier(Modifier.PUBLIC)
                           .returns(UnionVariantTagEnumData.VARIANT_TAG_NAME)
                           .addStatement("return $T.$L", UnionVariantTagEnumData.VARIANT_TAG_NAME, unionVariant)
                           .build();
    }

    MethodSyntax toStringMethod(ShapeCodegenState state) {
        var sensitiveIndex = SensitiveKnowledgeIndex.of(state.model());
        if (sensitiveIndex.isSensitive(state.shape())) {
            return CodegenUtils.toStringForSensitive();
        }
        var builder = CodegenUtils.toStringTemplate();
        var toStringReturn = CodeBlock.builder();
        String literalName = member.getMemberName() + ": ";

        if (sensitiveIndex.isSensitive(member)) {
            toStringReturn.addCode("return $S",
                                   state.shape().getId().getName() + "{" +
                                   literalName + "<*** REDACTED ***>}");
        } else {
            var name = Utils.toJavaName(state, member);
            toStringReturn.addCode("return $S + $L + $S",
                                   state.shape().getId().getName() + "{" +
                                   literalName, name, "}");
        }
        builder.addStatement(toStringReturn.build());
        return builder.build();
    }

    MethodSyntax equalsMethod(ShapeCodegenState state) {
        var builder = equalsTemplate();
        var className = Utils.toSourceName(state, member)
                             .withSuffix("member")
                             .toNameConvention(Name.Convention.PASCAL_CASE);

        builder.addStatement("$1L that = ($1L) other", className);
        var name = Utils.toJavaName(state, member);
        var type = Utils.toJavaTypeName(state, member);
        if (type instanceof PrimitiveTypeName c) {
            switch (c.name()) {
                case CHAR, BYTE, SHORT, INT, LONG, BOOLEAN:
                    builder.addStatement("return this.$1L == that.$1L", name);
                    break;
                case FLOAT:
                    builder.addStatement("return Float.compare(this.$1L, that.$1L) == 0", name);
                    break;
                case DOUBLE:
                    builder.addStatement("return Double.compare(this.$1L, that.$1L) == 0", name);
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported primitive: " + c);
            }
        } else {
            builder.addStatement("return this.$1L.equals(that.$1L)", name);
        }
        return builder.build();
    }

    MethodSyntax hashCodeMethod(ShapeCodegenState state) {
        var builder = hashCodeTemplate();
        var name = Utils.toJavaName(state, member);
        var type = Utils.toJavaTypeName(state, member);
        if (type instanceof PrimitiveTypeName c) {
            switch (c.name()) {
                case CHAR, BYTE, SHORT, INT:
                    builder.addStatement("return $L", name);
                    break;
                case LONG:
                    builder.addStatement("return Long.hashCode($L)", name);
                    break;
                case FLOAT:
                    builder.addStatement("return Float.hashCode($L)", name);
                    break;
                case DOUBLE:
                    builder.addStatement("return Double.hashCode($L)", name);
                    break;
                case BOOLEAN:
                    builder.addStatement("return Boolean.hashCode($L)", name);
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported primitive: " + c);
            }
        } else {
            builder.addStatement("return this.$L.hashCode()", name);
        }
        return builder.build();
    }

    FieldSyntax fieldFor(ShapeCodegenState state, MemberShape member) {
        var name = Utils.toJavaName(state, member);
        var type = Utils.toJavaTypeName(state, member);
        return FieldSyntax.from(type, name.toString());
    }

    public static Name memberVariantName(ShapeCodegenState state, MemberShape member) {
        return Utils.toSourceName(state, member)
                    .withSuffix("member")
                    .toNameConvention(Name.Convention.PASCAL_CASE);
    }
}
