package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Set;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.syntax#SyntaxModelPlugin")
public class SyntaxNodeWalkVisitor implements SyntaxNodeVisitor<SyntaxNode> {

    @Override
    public SyntaxNode visitAbstractControlFlow(AbstractControlFlow node) {
        node.prefix().accept(this);
        node.statement().accept(this);
        AbstractControlFlow next = node.next();
        if (next != null) {
            next.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitAbstractMethodSyntax(AbstractMethodSyntax node) {
        List<TypeVariableTypeName> typeParams = node.typeParams();
        for (int idx = 0; idx < typeParams.size(); idx++) {
            TypeVariableTypeName value = typeParams.get(idx);
            value.accept(this);
        }
        node.returns().accept(this);
        Javadoc javadoc = node.javadoc();
        if (javadoc != null) {
            javadoc.accept(this);
        }
        List<Annotation> annotations = node.annotations();
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            value.accept(this);
        }
        List<Parameter> parameters = node.parameters();
        for (int idx = 0; idx < parameters.size(); idx++) {
            Parameter value = parameters.get(idx);
            value.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitAnnotation(Annotation node) {
        node.type().accept(this);
        return node;
    }

    @Override
    public SyntaxNode visitArrayTypeName(ArrayTypeName node) {
        node.componentType().accept(this);
        return node;
    }

    @Override
    public SyntaxNode visitBlock(Block node) {
        List<Statement> statements = node.statements();
        for (int idx = 0; idx < statements.size(); idx++) {
            Statement value = statements.get(idx);
            value.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitCaseClause(CaseClause node) {
        List<Expression> label = node.label();
        for (int idx = 0; idx < label.size(); idx++) {
            Expression value = label.get(idx);
            value.accept(this);
        }
        node.body().accept(this);
        return node;
    }

    @Override
    public SyntaxNode visitClassName(ClassName node) {
        return node;
    }

    @Override
    public SyntaxNode visitClassSyntax(ClassSyntax node) {
        TypeName superClass = node.superClass();
        if (superClass != null) {
            superClass.accept(this);
        }
        List<TypeVariableTypeName> typeParams = node.typeParams();
        for (int idx = 0; idx < typeParams.size(); idx++) {
            TypeVariableTypeName value = typeParams.get(idx);
            value.accept(this);
        }
        Javadoc javadoc = node.javadoc();
        if (javadoc != null) {
            javadoc.accept(this);
        }
        List<BaseMethodSyntax> methods = node.methods();
        for (int idx = 0; idx < methods.size(); idx++) {
            BaseMethodSyntax value = methods.get(idx);
            value.accept(this);
        }
        List<Annotation> annotations = node.annotations();
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            value.accept(this);
        }
        List<FieldSyntax> fields = node.fields();
        for (int idx = 0; idx < fields.size(); idx++) {
            FieldSyntax value = fields.get(idx);
            value.accept(this);
        }
        List<TypeName> superInterfaces = node.superInterfaces();
        for (int idx = 0; idx < superInterfaces.size(); idx++) {
            TypeName value = superInterfaces.get(idx);
            value.accept(this);
        }
        List<TypeSyntax> innerTypes = node.innerTypes();
        for (int idx = 0; idx < innerTypes.size(); idx++) {
            TypeSyntax value = innerTypes.get(idx);
            value.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitCodeBlock(CodeBlock node) {
        return node;
    }

    @Override
    public SyntaxNode visitCompilationUnit(CompilationUnit node) {
        Set<ClassName> imports = node.imports();
        for (ClassName value : imports) {
            value.accept(this);
        }
        node.type().accept(this);
        return node;
    }

    @Override
    public SyntaxNode visitConstructorMethodSyntax(ConstructorMethodSyntax node) {
        node.body().accept(this);
        Javadoc javadoc = node.javadoc();
        if (javadoc != null) {
            javadoc.accept(this);
        }
        List<Annotation> annotations = node.annotations();
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            value.accept(this);
        }
        List<Parameter> parameters = node.parameters();
        for (int idx = 0; idx < parameters.size(); idx++) {
            Parameter value = parameters.get(idx);
            value.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitDefaultCaseClause(DefaultCaseClause node) {
        node.body().accept(this);
        return node;
    }

    @Override
    public SyntaxNode visitEnumConstant(EnumConstant node) {
        Javadoc javadoc = node.javadoc();
        if (javadoc != null) {
            javadoc.accept(this);
        }
        EnumBody body = node.body();
        if (body != null) {
            body.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitEnumSyntax(EnumSyntax node) {
        List<EnumConstant> enumConstants = node.enumConstants();
        for (int idx = 0; idx < enumConstants.size(); idx++) {
            EnumConstant value = enumConstants.get(idx);
            value.accept(this);
        }
        Javadoc javadoc = node.javadoc();
        if (javadoc != null) {
            javadoc.accept(this);
        }
        List<BaseMethodSyntax> methods = node.methods();
        for (int idx = 0; idx < methods.size(); idx++) {
            BaseMethodSyntax value = methods.get(idx);
            value.accept(this);
        }
        List<Annotation> annotations = node.annotations();
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            value.accept(this);
        }
        List<FieldSyntax> fields = node.fields();
        for (int idx = 0; idx < fields.size(); idx++) {
            FieldSyntax value = fields.get(idx);
            value.accept(this);
        }
        List<TypeName> superInterfaces = node.superInterfaces();
        for (int idx = 0; idx < superInterfaces.size(); idx++) {
            TypeName value = superInterfaces.get(idx);
            value.accept(this);
        }
        List<TypeSyntax> innerTypes = node.innerTypes();
        for (int idx = 0; idx < innerTypes.size(); idx++) {
            TypeSyntax value = innerTypes.get(idx);
            value.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitFieldSyntax(FieldSyntax node) {
        Javadoc javadoc = node.javadoc();
        if (javadoc != null) {
            javadoc.accept(this);
        }
        node.type().accept(this);
        List<Annotation> annotations = node.annotations();
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            value.accept(this);
        }
        Expression initializer = node.initializer();
        if (initializer != null) {
            initializer.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitForStatement(ForStatement node) {
        node.initializer().accept(this);
        node.statement().accept(this);
        return node;
    }

    @Override
    public SyntaxNode visitIfStatement(IfStatement node) {
        node.expression().accept(this);
        node.statement().accept(this);
        Statement elseStatement = node.elseStatement();
        if (elseStatement != null) {
            elseStatement.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitInterfaceSyntax(InterfaceSyntax node) {
        List<TypeVariableTypeName> typeParams = node.typeParams();
        for (int idx = 0; idx < typeParams.size(); idx++) {
            TypeVariableTypeName value = typeParams.get(idx);
            value.accept(this);
        }
        Javadoc javadoc = node.javadoc();
        if (javadoc != null) {
            javadoc.accept(this);
        }
        List<BaseMethodSyntax> methods = node.methods();
        for (int idx = 0; idx < methods.size(); idx++) {
            BaseMethodSyntax value = methods.get(idx);
            value.accept(this);
        }
        List<Annotation> annotations = node.annotations();
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            value.accept(this);
        }
        List<FieldSyntax> fields = node.fields();
        for (int idx = 0; idx < fields.size(); idx++) {
            FieldSyntax value = fields.get(idx);
            value.accept(this);
        }
        List<TypeName> superInterfaces = node.superInterfaces();
        for (int idx = 0; idx < superInterfaces.size(); idx++) {
            TypeName value = superInterfaces.get(idx);
            value.accept(this);
        }
        List<TypeSyntax> innerTypes = node.innerTypes();
        for (int idx = 0; idx < innerTypes.size(); idx++) {
            TypeSyntax value = innerTypes.get(idx);
            value.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitMethodSyntax(MethodSyntax node) {
        List<TypeVariableTypeName> typeParams = node.typeParams();
        for (int idx = 0; idx < typeParams.size(); idx++) {
            TypeVariableTypeName value = typeParams.get(idx);
            value.accept(this);
        }
        node.returns().accept(this);
        node.body().accept(this);
        Javadoc javadoc = node.javadoc();
        if (javadoc != null) {
            javadoc.accept(this);
        }
        List<Annotation> annotations = node.annotations();
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            value.accept(this);
        }
        List<Parameter> parameters = node.parameters();
        for (int idx = 0; idx < parameters.size(); idx++) {
            Parameter value = parameters.get(idx);
            value.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitParameter(Parameter node) {
        node.type().accept(this);
        return node;
    }

    @Override
    public SyntaxNode visitParameterizedTypeName(ParameterizedTypeName node) {
        node.rawType().accept(this);
        List<TypeName> typeArguments = node.typeArguments();
        for (int idx = 0; idx < typeArguments.size(); idx++) {
            TypeName value = typeArguments.get(idx);
            value.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitPrimitiveTypeName(PrimitiveTypeName node) {
        return node;
    }

    @Override
    public SyntaxNode visitSwitchStatement(SwitchStatement node) {
        node.expression().accept(this);
        List<CaseClause> cases = node.cases();
        for (int idx = 0; idx < cases.size(); idx++) {
            CaseClause value = cases.get(idx);
            value.accept(this);
        }
        DefaultCaseClause defaultCase = node.defaultCase();
        if (defaultCase != null) {
            defaultCase.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitTypeVariableTypeName(TypeVariableTypeName node) {
        List<TypeName> bounds = node.bounds();
        for (int idx = 0; idx < bounds.size(); idx++) {
            TypeName value = bounds.get(idx);
            value.accept(this);
        }
        return node;
    }

    @Override
    public SyntaxNode visitWildcardTypeName(WildcardTypeName node) {
        ClassName rawType = node.rawType();
        if (rawType != null) {
            rawType.accept(this);
        }
        List<TypeName> upperBounds = node.upperBounds();
        for (int idx = 0; idx < upperBounds.size(); idx++) {
            TypeName value = upperBounds.get(idx);
            value.accept(this);
        }
        List<TypeName> lowerBounds = node.lowerBounds();
        for (int idx = 0; idx < lowerBounds.size(); idx++) {
            TypeName value = lowerBounds.get(idx);
            value.accept(this);
        }
        return node;
    }
}
