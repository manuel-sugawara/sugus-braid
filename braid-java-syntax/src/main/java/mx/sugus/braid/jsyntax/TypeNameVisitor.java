package mx.sugus.braid.jsyntax;

public interface TypeNameVisitor<T> {

    T visitArrayTypeName(ArrayTypeName node);

    T visitClassName(ClassName node);

    T visitParameterizedTypeName(ParameterizedTypeName node);

    T visitPrimitiveTypeName(PrimitiveTypeName node);

    T visitTypeVariableTypeName(TypeVariableTypeName node);

    T visitWildcardTypeName(WildcardTypeName node);

    public static abstract class Default<T> implements TypeNameVisitor<T> {

        public abstract T getDefault(TypeName node);

        @Override
        public T visitArrayTypeName(ArrayTypeName node) {
            return getDefault(node);
        }

        @Override
        public T visitClassName(ClassName node) {
            return getDefault(node);
        }

        @Override
        public T visitParameterizedTypeName(ParameterizedTypeName node) {
            return getDefault(node);
        }

        @Override
        public T visitPrimitiveTypeName(PrimitiveTypeName node) {
            return getDefault(node);
        }

        @Override
        public T visitTypeVariableTypeName(TypeVariableTypeName node) {
            return getDefault(node);
        }

        @Override
        public T visitWildcardTypeName(WildcardTypeName node) {
            return getDefault(node);
        }
    }
}
