package mx.sugus.braid.plugins.data.utils;

import java.util.Objects;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.plugins.data.producers.Utils;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import software.amazon.smithy.codegen.core.Symbol;

public final class SymbolCodegen {
    private SymbolCodegen() {
    }

    static Block builderEmptyInitializer(Symbol symbol) {
        var type = symbol.getProperty(SymbolProperties.AGGREGATE_TYPE).orElse(SymbolConstants.AggregateType.NONE);
        var name = Utils.toJavaName(symbol);
        if (type == SymbolConstants.AggregateType.NONE) {
            var builderReference = symbol.getProperty(SymbolProperties.BUILDER_REFERENCE).orElse(null);
            if (builderReference == null) {
                var defaultValue = Utils.defaultValue(symbol);
                if (defaultValue != null) {
                    return BodyBuilder.create()
                                      .addStatement("this.$L = $L", name, defaultValue)
                                      .build();
                }
                return BodyBuilder.emptyBlock();
            }
            var fromPersistent = builderReference.fromPersistent();
            var implementingClass = ClassName.from(fromPersistent.getNamespace(), fromPersistent.getName());
            return BodyBuilder.create()
                              .addStatement("this.$L = $T.$L($L)", name, implementingClass,
                                            fromPersistent.getMember().orElseThrow(), "null")
                              .build();
        }
        var builder = BodyBuilder.create();
        var isOrdered = Utils.isOrdered(symbol);
        if (isOrdered) {
            switch (type) {
                case MAP -> builder.addStatement("this.$L = $T.forOrderedMap()", name, CollectionBuilderReference.class);
                case SET -> builder.addStatement("this.$L = $T.forOrderedSet()", name, CollectionBuilderReference.class);
                case LIST -> builder.addStatement("this.$L = $T.forList()", name, CollectionBuilderReference.class);
                default -> throw new UnsupportedOperationException("unsupported aggregate type: " + type);
            }
        } else {
            switch (type) {
                case MAP -> builder.addStatement("this.$L = $T.forUnorderedMap()", name, CollectionBuilderReference.class);
                case SET -> builder.addStatement("this.$L = $T.forUnorderedSet()", name, CollectionBuilderReference.class);
                case LIST -> builder.addStatement("this.$L = $T.forList()", name, CollectionBuilderReference.class);
                default -> throw new UnsupportedOperationException("unsupported aggregate type: " + type);
            }
        }
        return builder.build();
    }

    static Block builderDataInitializer(Symbol symbol) {
        if (Utils.isConstant(symbol)) {
            return BodyBuilder.emptyBlock();
        }
        var type = Utils.aggregateType(symbol);
        var name = Utils.toJavaName(symbol);
        if (type == SymbolConstants.AggregateType.NONE) {
            var builderReference = Utils.builderReference(symbol);
            if (builderReference == null) {
                return BodyBuilder.create()
                                  .addStatement("this.$1L = data.$1L", name)
                                  .build();
            }
            var fromPersistent = builderReference.fromPersistent();
            var implementingClass = ClassName.from(fromPersistent.getNamespace(), fromPersistent.getName());
            return BodyBuilder.create()
                              .addStatement("this.$L = $T.$L(data.$L)", name, implementingClass,
                                            fromPersistent.getMember().orElseThrow(), name)
                              .build();
        }
        var builder = BodyBuilder.create();
        var isOrdered = Utils.isOrdered(symbol);
        if (isOrdered) {
            switch (type) {
                case MAP -> builder.addStatement("this.$1L = $2T.fromPersistentOrderedMap(data.$1L)",
                                                 name, CollectionBuilderReference.class);
                case SET -> builder.addStatement("this.$1L = $2T.fromPersistentOrderedSet(data.$1L)",
                                                 name, CollectionBuilderReference.class);
                case LIST -> builder.addStatement("this.$1L = $2T.fromPersistentList(data.$1L)",
                                                  name, CollectionBuilderReference.class);
                default -> throw new UnsupportedOperationException("unsupported aggregate type: " + type);
            }
        } else {
            switch (type) {
                case MAP -> builder.addStatement("this.$1L = $2T.fromPersistentUnorderedMap(data.$1L)",
                                                 name, CollectionBuilderReference.class);
                case SET -> builder.addStatement("this.$1L = $2T.fromPersistentUnorderedSet(data.$1L)",
                                                 name, CollectionBuilderReference.class);
                case LIST -> builder.addStatement("this.$1L = $2T.fromPersistentList(data.$1L)",
                                                  name, CollectionBuilderReference.class);
                default -> throw new UnsupportedOperationException("unsupported aggregate type: " + type);
            }
        }
        return builder.build();
    }

    static Block dataBuilderInitializer(Symbol symbol) {
        if (Utils.isConstant(symbol)) {
            return BodyBuilder.emptyBlock();
        }
        var name = Utils.toJavaName(symbol);
        var builderReference = Utils.builderReference(symbol);
        var builderProperty = CodeBlock.builder().addCode("builder.$1L", name);
        var type = Utils.aggregateType(symbol);
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
                          .addStatement("this.$1L = $2C",
                                        name, builderProperty.build())
                          .build();
    }
}
