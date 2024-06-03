package mx.sugus.braid.jsyntax.ext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mx.sugus.braid.jsyntax.ArrayTypeName;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.PrimitiveTypeName;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.TypePrimitiveName;

public final class TypeNameExt {
    public static final ClassName BIG_DECIMAL = ClassName.from(BigDecimal.class);
    public static final ClassName BIG_INTEGER = ClassName.from(BigInteger.class);
    public static final ClassName BOOLEAN = ClassName.from(Boolean.class);
    public static final ClassName BYTE = ClassName.from(Byte.class);
    public static final ClassName CHAR = ClassName.from(Character.class);
    public static final ClassName DOUBLE = ClassName.from(Double.class);
    public static final ClassName FLOAT = ClassName.from(Float.class);
    public static final ClassName INSTANT = ClassName.from(Instant.class);
    public static final ClassName INT = ClassName.from(Integer.class);
    public static final ClassName LIST = ClassName.from(List.class);
    public static final ClassName LONG = ClassName.from(Long.class);
    public static final ClassName MAP = ClassName.from(Map.class);
    public static final ClassName OBJECT = ClassName.from(Object.class);
    public static final ClassName SET = ClassName.from(Set.class);
    public static final ClassName SHORT = ClassName.from(Short.class);
    public static final ClassName STRING = ClassName.from(String.class);
    public static final ClassName VOID = ClassName.from(Void.class);

    public static final PrimitiveTypeName P_VOID = PrimitiveTypeName.builder().name(TypePrimitiveName.VOID).build();
    public static final PrimitiveTypeName P_BOOLEAN = PrimitiveTypeName.builder().name(TypePrimitiveName.BOOLEAN).build();
    public static final PrimitiveTypeName P_BYTE = PrimitiveTypeName.builder().name(TypePrimitiveName.BYTE).build();
    public static final PrimitiveTypeName P_SHORT = PrimitiveTypeName.builder().name(TypePrimitiveName.SHORT).build();
    public static final PrimitiveTypeName P_INT = PrimitiveTypeName.builder().name(TypePrimitiveName.INT).build();
    public static final PrimitiveTypeName P_LONG = PrimitiveTypeName.builder().name(TypePrimitiveName.LONG).build();
    public static final PrimitiveTypeName P_CHAR = PrimitiveTypeName.builder().name(TypePrimitiveName.CHAR).build();
    public static final PrimitiveTypeName P_FLOAT = PrimitiveTypeName.builder().name(TypePrimitiveName.FLOAT).build();
    public static final PrimitiveTypeName P_DOUBLE = PrimitiveTypeName.builder().name(TypePrimitiveName.DOUBLE).build();

    private TypeNameExt() {
    }

    public static TypeName listOf(TypeName typeParam) {
        return ParameterizedTypeName.from(LIST, typeParam);
    }

    public static TypeName setOf(TypeName typeParam) {
        return ParameterizedTypeName.from(SET, typeParam);
    }

    public static TypeName mapOf(TypeName keyType, TypeName valueType) {
        return ParameterizedTypeName.from(MAP, keyType, valueType);
    }

    public static TypeName from(Class<?> kclass) {
        if (kclass.isArray()) {
            var arrayClass = kclass;
            var componentType = arrayClass.getComponentType();
            var nesting = 0;
            while (componentType.isArray()) {
                nesting++;
                componentType = componentType.getComponentType();
            }
            var result = ArrayTypeName.builder().componentType(componentType).build();
            for (var idx = 0; idx < nesting; idx++) {
                result = ArrayTypeName.builder().componentType(result).build();
            }
            return result;
        }
        if (kclass.isPrimitive()) {
            return fromPrimitive(kclass);
        }
        return ClassName.from(kclass);
    }

    private static TypeName fromPrimitive(Class<?> kclass) {
        switch (kclass.getSimpleName()) {
            case "void":
                return P_VOID;
            case "boolean":
                return P_BOOLEAN;
            case "byte":
                return P_BYTE;
            case "short":
                return P_SHORT;
            case "int":
                return P_INT;
            case "long":
                return P_LONG;
            case "char":
                return P_CHAR;
            case "float":
                return P_FLOAT;
            case "double":
                return P_DOUBLE;
            default:
                throw new IllegalArgumentException("Unknown primitive type: " + kclass);
        }
    }
}
