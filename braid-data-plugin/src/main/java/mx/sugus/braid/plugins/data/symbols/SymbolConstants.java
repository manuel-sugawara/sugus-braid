package mx.sugus.braid.plugins.data.symbols;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.TypeName;

public class SymbolConstants {

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

    public enum AggregateType {
        NONE, LIST, MAP, SET
    }
}
