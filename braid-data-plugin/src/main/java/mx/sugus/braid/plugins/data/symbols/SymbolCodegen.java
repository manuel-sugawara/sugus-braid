package mx.sugus.braid.plugins.data.symbols;

import static mx.sugus.braid.plugins.data.symbols.SymbolProperties.BUILDER_REFERENCE_FROM_PERSISTENT;
import static mx.sugus.braid.plugins.data.symbols.SymbolProperties.BUILDER_REFERENCE_JAVA_TYPE;

import java.util.Objects;
import mx.sugus.braid.core.plugin.ShapeCodegenState;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import software.amazon.smithy.codegen.core.Symbol;
import software.amazon.smithy.model.shapes.MemberShape;

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
                var defaultValue = Utils.defaultValue(symbol);
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
                var defaultValue = Utils.defaultValue(symbol);
                if (defaultValue != null) {
                    return CodeBlock.builder()
                                    .addCode("$L", defaultValue)
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
        var isOrdered = Utils.isOrdered(symbol);
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
        var symbol = state.symbolProvider().toSymbol(member);
        if (Utils.isConstant(symbol)) {
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
        var type = Utils.aggregateType(symbol);
        String name;
        if (useGetters) {
            name = Utils.toGetterName(state, member).toString() + "()";
        } else {
            name = Utils.toJavaName(state, member).toString();
        }
        if (type == SymbolConstants.AggregateType.NONE) {
            var builderReference = Utils.builderReference(symbol);
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
        var isOrdered = Utils.isOrdered(symbol);
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
        var symbol = state.symbolProvider().toSymbol(member);
        if (Utils.isConstant(symbol)) {
            return BodyBuilder.emptyBlock();
        }
        var name = Utils.toJavaName(state, member);
        var builderReference = Utils.builderReference(symbol);
        var type = Utils.aggregateType(symbol);
        var builderProperty = CodeBlock.builder().addCode("builder.$L", name);
        if (type != SymbolConstants.AggregateType.NONE) {
            builderProperty.addCode(".asPersistent()");
        }
        if (builderReference != null) {
            builderProperty.addCode(".asPersistent()");
        }
        if (Utils.isRequired(symbol)) {
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
        var symbol = state.symbolProvider().toSymbol(member);
        var name = Utils.toJavaName(state, member);
        var type = Utils.aggregateType(symbol);
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
        var builderReference = Utils.builderReference(symbol);
        if (builderReference != null) {
            return builder.addStatement("this.$1L.setPersistent($1L)", name)
                          .build();
        }
        return builder.addStatement("this.$1L = $1L", name)
                      .build();
    }
}
