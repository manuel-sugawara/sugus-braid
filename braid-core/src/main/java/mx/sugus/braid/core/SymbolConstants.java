package mx.sugus.braid.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import mx.sugus.braid.core.symbol.SymbolProperties;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.TypeName;
import software.amazon.smithy.codegen.core.Symbol;

public class SymbolConstants {

    public static final String AGGREGATE_TYPE = "::aggregateType";


    public static boolean isAggregate(Symbol symbol) {
        return symbol.getProperty(AGGREGATE_TYPE, AggregateType.class)
                     .map(t -> t != AggregateType.NONE)
                     .orElse(false);
    }

    public static AggregateType aggregateType(Symbol symbol) {
        return symbol.getProperty(AGGREGATE_TYPE, AggregateType.class)
                     .orElse(AggregateType.NONE);
    }

    public static String emptyReferenceBuilder(AggregateType type) {
        return
            switch (type) {
                case MAP -> "forOrderedMap";
                case LIST -> "forList";
                case SET -> "forOrderedSet";
                default -> null;
            };
    }

    public static String initReferenceBuilder(AggregateType type) {
        return
            switch (type) {
                case MAP -> "fromPersistentOrderedMap";
                case LIST -> "fromPersistentList";
                case SET -> "fromPersistentOrderedSet";
                default -> null;
            };
    }

    public static TypeName concreteClassFor(AggregateType type) {
        return
            switch (type) {
                case MAP -> ClassName.from(LinkedHashMap.class);
                case LIST -> ClassName.from(ArrayList.class);
                case SET -> ClassName.from(LinkedHashSet.class);
                default -> null;
            };
    }

    public static Symbol fromClass(Class<?> clazz) {
        return Symbol.builder()
                     .name(clazz.getSimpleName())
                     .namespace(clazz.getPackageName(), ".")
                     .build();
    }

    // XXX remove
    public static Symbol fromLiteralClassName(String className) {
        var lastDot = className.lastIndexOf('.');
        if (lastDot == -1) {
            return Symbol.builder()
                         .name(className)
                         .build();
        }
        // XXX substring might fail here if the given string is "foo.bar."
        return Symbol.builder()
                     .name(className.substring(lastDot + 1))
                     .namespace(className.substring(0, lastDot), ".")
                     .build();
    }

    public static Symbol toSymbol(Object arg) {
        if (arg == null) {
            throw new NullPointerException("arg");
        }
        if (arg instanceof Symbol s) {
            return s;
        }
        if (arg instanceof Class c) {
            return fromClass(c);
        }
        if (arg instanceof String s) {
            return fromLiteralClassName(s);
        }
        throw new IllegalArgumentException("cannot convert " + arg.getClass().getSimpleName() + " to Symbol");
    }


    public enum AggregateType {
        NONE, LIST, MAP, SET
    }
}
