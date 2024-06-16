package mx.sugus.braid.plugins.data.symbols;

import static mx.sugus.braid.plugins.data.symbols.SymbolProperties.BUILDER_REFERENCE_FROM_PERSISTENT;
import static mx.sugus.braid.plugins.data.symbols.SymbolProperties.BUILDER_REFERENCE_JAVA_TYPE;

import java.util.NoSuchElementException;
import java.util.Objects;
import mx.sugus.braid.core.plugin.CodegenState;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.writer.CodeWriter;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import software.amazon.smithy.codegen.core.Symbol;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.traits.DefaultTrait;

public final class SymbolCodegen {
    private SymbolCodegen() {
    }

    static Block builderEmptyInitializer(ShapeCodegenState state, MemberShape member) {
        var symbol = state.symbolProvider().toSymbol(member);
        var type = symbol.getProperty(SymbolProperties.AGGREGATE_TYPE).orElse(SymbolConstants.AggregateType.NONE);
        var name = Utils.toJavaName(state, member);
        if (type == SymbolConstants.AggregateType.NONE) {
            var builderReference = symbol.getProperty(SymbolProperties.BUILDER_REFERENCE).orElse(null);
            if (builderReference == null) {
                var defaultValue = Utils.defaultValue(state, member);
                if (defaultValue == null) {
                    return BodyBuilder.emptyBlock();
                }
            }
        }
        var initExpression = builderEmptyInitializerExpression(state, member);
        return BodyBuilder.create()
                          .addStatement("this.$L = $C", name, initExpression)
                          .build();
    }

    static CodeBlock builderEmptyInitializerExpression(ShapeCodegenState state, MemberShape member) {
        var symbol = state.symbolProvider().toSymbol(member);
        var type = symbol.getProperty(SymbolProperties.AGGREGATE_TYPE).orElse(SymbolConstants.AggregateType.NONE);
        if (type == SymbolConstants.AggregateType.NONE) {
            var builderReference = symbol.getProperty(SymbolProperties.BUILDER_REFERENCE).orElse(null);
            if (builderReference == null) {
                var defaultValue = Utils.defaultValue(state, member);
                if (defaultValue != null) {
                    return CodeBlock.builder()
                                    .addCode("$C", defaultValue)
                                    .build();
                }
                return CodeBlock.builder().build();
            }
            var implementingClass = symbol.getProperty(BUILDER_REFERENCE_JAVA_TYPE).orElseThrow();
            var fromPersistent = symbol.getProperty(BUILDER_REFERENCE_FROM_PERSISTENT).orElseThrow();
            return CodeBlock.builder()
                            .addCode("$T.$L($L)", implementingClass,
                                     fromPersistent, "null")
                            .build();
        }
        var builder = CodeBlock.builder();
        var isOrdered = Utils.isOrdered(state, member);
        if (isOrdered) {
            switch (type) {
                case MAP -> builder.addCode("$T.forOrderedMap()", CollectionBuilderReference.class);
                case SET -> builder.addCode("$T.forOrderedSet()", CollectionBuilderReference.class);
                case LIST -> builder.addCode("$T.forList()", CollectionBuilderReference.class);
                default -> throw new UnsupportedOperationException("unsupported aggregate type: " + type);
            }
        } else {
            switch (type) {
                case MAP -> builder.addCode("$T.forUnorderedMap()", CollectionBuilderReference.class);
                case SET -> builder.addCode("$T.forUnorderedSet()", CollectionBuilderReference.class);
                case LIST -> builder.addCode("$T.forList()", CollectionBuilderReference.class);
                default -> throw new UnsupportedOperationException("unsupported aggregate type: " + type);
            }
        }
        return builder.build();
    }

    static Block builderDataInitializer(ShapeCodegenState state, MemberShape member) {
        if (Utils.isConstant(state, member)) {
            return BodyBuilder.emptyBlock();
        }
        var name = Utils.toJavaName(state, member);
        return BodyBuilder.create()
                          .addStatement("this.$L = $C", name, builderDataInitializerExpression(state, member))
                          .build();
    }

    static CodeBlock builderDataInitializerExpression(ShapeCodegenState state, MemberShape member) {
        return builderDataInitializerExpression(state, member, false);
    }

    static CodeBlock builderUnionDataInitializerExpression(ShapeCodegenState state, MemberShape member) {
        return builderDataInitializerExpression(state, member, true);
    }

    static CodeBlock builderDataInitializerExpression(ShapeCodegenState state, MemberShape member, boolean useGetters) {
        var symbol = state.symbolProvider().toSymbol(member);
        var type = Utils.aggregateType(state, member);
        String name;
        if (useGetters) {
            name = Utils.toGetterName(state, member).toString() + "()";
        } else {
            name = Utils.toJavaName(state, member).toString();
        }
        if (type == SymbolConstants.AggregateType.NONE) {
            var builderReference = Utils.builderReference(state, member);
            if (builderReference == null) {
                return CodeBlock.builder()
                                .addCode("data.$L", name)
                                .build();
            }
            var implementingClass = symbol.getProperty(BUILDER_REFERENCE_JAVA_TYPE).orElseThrow();
            var fromPersistent = symbol.getProperty(BUILDER_REFERENCE_FROM_PERSISTENT).orElseThrow();
            return CodeBlock.builder()
                            .addCode("$T.$L(data.$L)", implementingClass,
                                     fromPersistent, name)
                            .build();
        }
        var builder = CodeBlock.builder();
        var isOrdered = Utils.isOrdered(state, member);
        if (isOrdered) {
            switch (type) {
                case MAP -> builder.addCode("$T.fromPersistentOrderedMap(data.$L)",
                                            CollectionBuilderReference.class, name);
                case SET -> builder.addCode("$T.fromPersistentOrderedSet(data.$L)",
                                            CollectionBuilderReference.class, name);
                case LIST -> builder.addCode("$T.fromPersistentList(data.$L)",
                                             CollectionBuilderReference.class, name);
                default -> throw new UnsupportedOperationException("unsupported aggregate type: " + type);
            }
        } else {
            switch (type) {
                case MAP -> builder.addCode("$T.fromPersistentUnorderedMap(data.$L)",
                                            CollectionBuilderReference.class, name);
                case SET -> builder.addCode("$T.fromPersistentUnorderedSet(data.$L)",
                                            CollectionBuilderReference.class, name);
                case LIST -> builder.addCode("$T.fromPersistentList(data.$L)",
                                             CollectionBuilderReference.class, name);
                default -> throw new UnsupportedOperationException("unsupported aggregate type: " + type);
            }
        }
        return builder.build();
    }

    static Block dataBuilderInitializer(ShapeCodegenState state, MemberShape member) {
        if (Utils.isConstant(state, member)) {
            return BodyBuilder.emptyBlock();
        }
        var name = Utils.toJavaName(state, member);
        var builderReference = Utils.builderReference(state, member);
        var type = Utils.aggregateType(state, member);
        var builderProperty = CodeBlock.builder().addCode("builder.$L", name);
        if (type != SymbolConstants.AggregateType.NONE) {
            builderProperty.addCode(".asPersistent()");
        }
        if (builderReference != null) {
            builderProperty.addCode(".asPersistent()");
        }
        if (Utils.isRequired(state, member)) {
            return BodyBuilder.create()
                              .addStatement("this.$1L = $2T.requireNonNull($3C, $1S)",
                                            name, Objects.class, builderProperty.build())
                              .build();
        }
        return BodyBuilder.create()
                          .addStatement("this.$L = $C", name, builderProperty.build())
                          .build();
    }

    static Block builderSetterForMember(ShapeCodegenState state, MemberShape member) {
        var name = Utils.toJavaName(state, member);
        var type = Utils.aggregateType(state, member);
        var builder = BodyBuilder.create();
        if (type == SymbolConstants.AggregateType.MAP) {
            return builder.addStatement("this.$L.clear()", name)
                          .addStatement("this.$1L.asTransient().putAll($1L)", name)
                          .build();
        }
        if (type != SymbolConstants.AggregateType.NONE) {
            return builder.addStatement("this.$L.clear()", name)
                          .addStatement("this.$1L.asTransient().addAll($1L)", name)
                          .build();
        }
        var builderReference = Utils.builderReference(state, member);
        if (builderReference != null) {
            return builder.addStatement("this.$1L.setPersistent($1L)", name)
                          .build();
        }
        return builder.addStatement("this.$1L = $1L", name)
                      .build();
    }

    public static CodeBlock defaultValue(CodegenState state, MemberShape member) {
        var defaultValue = member.getTrait(DefaultTrait.class).orElse(null);
        if (defaultValue == null) {
            return null;
        }
        var target = state.model().expectShape(member.getTarget());
        var defaultValueNode = defaultValue.toNode();
        var shapeType = target.getType();
        switch (shapeType) {
            case BYTE, SHORT, INTEGER, DOUBLE:
                return CodeBlock.from("$L", defaultValueNode.expectNumberNode().getValue().toString());
            case LONG:
                return CodeBlock.from("$LL",  defaultValueNode.expectNumberNode().getValue().toString());
            case FLOAT:
                return CodeBlock.from("$LF", defaultValueNode.expectNumberNode().getValue().toString());
            case STRING:
                 return CodeBlock.from("$S", defaultValueNode.expectStringNode().getValue());
            case BOOLEAN:
                return CodeBlock.from("$L", defaultValueNode.expectBooleanNode().getValue());
            case ENUM:
                var enumMember = findEnumMember(target, defaultValueNode.expectStringNode().getValue());
                return CodeBlock.from("$T.$L", Utils.toJavaTypeName(state, target), Utils.toJavaName(state, enumMember));
            default:
                throw new UnsupportedOperationException("Unsupported type: " + shapeType + " for default value");
        }
    }

    private static MemberShape findEnumMember(Shape target, String value) {
        var enumShape = target.asEnumShape().orElseThrow();
        var values = enumShape.getEnumValues();
        for (var kvp : enumShape.getAllMembers().entrySet()) {
            var enumValue = values.get(kvp.getKey());
            if (enumValue.equals(value)) {
                return kvp.getValue();
            }
        }
        throw new NoSuchElementException("cannot find enum member with value: " + value);
    }
}
