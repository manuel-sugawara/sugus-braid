package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.syntax#SyntaxModelPlugin")
public interface SyntaxNodeVisitor<T> {

    T visitAbstractControlFlow(AbstractControlFlow node);

    T visitAbstractMethodSyntax(AbstractMethodSyntax node);

    T visitAnnotation(Annotation node);

    T visitArrayTypeName(ArrayTypeName node);

    T visitBlock(Block node);

    T visitCaseClause(CaseClause node);

    T visitClassName(ClassName node);

    T visitClassSyntax(ClassSyntax node);

    T visitCodeBlock(CodeBlock node);

    T visitCompilationUnit(CompilationUnit node);

    T visitConstructorMethodSyntax(ConstructorMethodSyntax node);

    T visitDefaultCaseClause(DefaultCaseClause node);

    T visitEnumConstant(EnumConstant node);

    T visitEnumSyntax(EnumSyntax node);

    T visitFieldSyntax(FieldSyntax node);

    T visitForStatement(ForStatement node);

    T visitIfStatement(IfStatement node);

    T visitInterfaceSyntax(InterfaceSyntax node);

    T visitMethodSyntax(MethodSyntax node);

    T visitParameter(Parameter node);

    T visitParameterizedTypeName(ParameterizedTypeName node);

    T visitPrimitiveTypeName(PrimitiveTypeName node);

    T visitSwitchStatement(SwitchStatement node);

    T visitTypeVariableTypeName(TypeVariableTypeName node);

    T visitWildcardTypeName(WildcardTypeName node);

    public static abstract class Default<T> implements SyntaxNodeVisitor<T> {

        public abstract T getDefault(SyntaxNode node);

        @Override
        public T visitAbstractControlFlow(AbstractControlFlow node) {
            return getDefault(node);
        }

        @Override
        public T visitAbstractMethodSyntax(AbstractMethodSyntax node) {
            return getDefault(node);
        }

        @Override
        public T visitAnnotation(Annotation node) {
            return getDefault(node);
        }

        @Override
        public T visitArrayTypeName(ArrayTypeName node) {
            return getDefault(node);
        }

        @Override
        public T visitBlock(Block node) {
            return getDefault(node);
        }

        @Override
        public T visitCaseClause(CaseClause node) {
            return getDefault(node);
        }

        @Override
        public T visitClassName(ClassName node) {
            return getDefault(node);
        }

        @Override
        public T visitClassSyntax(ClassSyntax node) {
            return getDefault(node);
        }

        @Override
        public T visitCodeBlock(CodeBlock node) {
            return getDefault(node);
        }

        @Override
        public T visitCompilationUnit(CompilationUnit node) {
            return getDefault(node);
        }

        @Override
        public T visitConstructorMethodSyntax(ConstructorMethodSyntax node) {
            return getDefault(node);
        }

        @Override
        public T visitDefaultCaseClause(DefaultCaseClause node) {
            return getDefault(node);
        }

        @Override
        public T visitEnumConstant(EnumConstant node) {
            return getDefault(node);
        }

        @Override
        public T visitEnumSyntax(EnumSyntax node) {
            return getDefault(node);
        }

        @Override
        public T visitFieldSyntax(FieldSyntax node) {
            return getDefault(node);
        }

        @Override
        public T visitForStatement(ForStatement node) {
            return getDefault(node);
        }

        @Override
        public T visitIfStatement(IfStatement node) {
            return getDefault(node);
        }

        @Override
        public T visitInterfaceSyntax(InterfaceSyntax node) {
            return getDefault(node);
        }

        @Override
        public T visitMethodSyntax(MethodSyntax node) {
            return getDefault(node);
        }

        @Override
        public T visitParameter(Parameter node) {
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
        public T visitSwitchStatement(SwitchStatement node) {
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
