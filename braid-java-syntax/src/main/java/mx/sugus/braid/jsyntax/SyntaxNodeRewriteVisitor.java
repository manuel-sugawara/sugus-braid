package mx.sugus.braid.jsyntax;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.syntax#SyntaxModelPlugin")
public class SyntaxNodeRewriteVisitor implements SyntaxNodeVisitor<SyntaxNode> {

    @Override
    public AbstractControlFlow visitAbstractControlFlow(AbstractControlFlow node) {
        AbstractControlFlow.Builder builder = null;
        CodeBlock prefix = node.prefix();
        CodeBlock prefixNew = visitCodeBlock(prefix);
        if (!Objects.equals(prefix, prefixNew)) {
            builder = node.toBuilder();
            builder.prefix(prefixNew);
        }
        Block statement = node.statement();
        Block statementNew = visitBlock(statement);
        if (!Objects.equals(statement, statementNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.statement(statementNew);
        }
        AbstractControlFlow next = node.next();
        AbstractControlFlow nextNew = null;
        if (next != null) {
            nextNew = visitAbstractControlFlow(next);
        }
        if (!Objects.equals(next, nextNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.next(nextNew);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public AbstractMethodSyntax visitAbstractMethodSyntax(AbstractMethodSyntax node) {
        AbstractMethodSyntax.Builder builder = null;
        List<TypeVariableTypeName> typeParams = node.typeParams();
        List<TypeVariableTypeName> newTypeParams = null;
        for (int idx = 0; idx < typeParams.size(); idx++) {
            TypeVariableTypeName value = typeParams.get(idx);
            TypeVariableTypeName newValue = visitTypeVariableTypeName(value);
            if (newTypeParams == null && !value.equals(newValue)) {
                newTypeParams = new ArrayList<>(typeParams.size());
                newTypeParams.addAll(typeParams.subList(0, idx));
            }
            if (newTypeParams != null) {
                newTypeParams.add(newValue);
            }
        }
        if (newTypeParams != null) {
            builder = node.toBuilder();
            builder.typeParams(newTypeParams);
        }
        TypeName returns = node.returns();
        TypeName returnsNew = (TypeName) returns.accept(this);
        if (!Objects.equals(returns, returnsNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.returns(returnsNew);
        }
        Javadoc javadoc = node.javadoc();
        Javadoc javadocNew = null;
        if (javadoc != null) {
            javadocNew = (Javadoc) javadoc.accept(this);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.javadoc(javadocNew);
        }
        List<Annotation> annotations = node.annotations();
        List<Annotation> newAnnotations = null;
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (newAnnotations == null && !value.equals(newValue)) {
                newAnnotations = new ArrayList<>(annotations.size());
                newAnnotations.addAll(annotations.subList(0, idx));
            }
            if (newAnnotations != null) {
                newAnnotations.add(newValue);
            }
        }
        if (newAnnotations != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.annotations(newAnnotations);
        }
        List<Parameter> parameters = node.parameters();
        List<Parameter> newParameters = null;
        for (int idx = 0; idx < parameters.size(); idx++) {
            Parameter value = parameters.get(idx);
            Parameter newValue = visitParameter(value);
            if (newParameters == null && !value.equals(newValue)) {
                newParameters = new ArrayList<>(parameters.size());
                newParameters.addAll(parameters.subList(0, idx));
            }
            if (newParameters != null) {
                newParameters.add(newValue);
            }
        }
        if (newParameters != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.parameters(newParameters);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public Annotation visitAnnotation(Annotation node) {
        Annotation.Builder builder = null;
        ClassName type = node.type();
        ClassName typeNew = visitClassName(type);
        if (!Objects.equals(type, typeNew)) {
            builder = node.toBuilder();
            builder.type(typeNew);
        }
        CodeBlock value = node.value();
        CodeBlock valueNew = null;
        if (value != null) {
            valueNew = visitCodeBlock(value);
        }
        if (!Objects.equals(value, valueNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.value(valueNew);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public ArrayTypeName visitArrayTypeName(ArrayTypeName node) {
        ArrayTypeName.Builder builder = null;
        TypeName componentType = node.componentType();
        TypeName componentTypeNew = (TypeName) componentType.accept(this);
        if (!Objects.equals(componentType, componentTypeNew)) {
            builder = node.toBuilder();
            builder.componentType(componentTypeNew);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public Block visitBlock(Block node) {
        Block.Builder builder = null;
        List<Statement> statements = node.statements();
        List<Statement> newStatements = null;
        for (int idx = 0; idx < statements.size(); idx++) {
            Statement value = statements.get(idx);
            Statement newValue = (Statement) value.accept(this);
            if (newStatements == null && !value.equals(newValue)) {
                newStatements = new ArrayList<>(statements.size());
                newStatements.addAll(statements.subList(0, idx));
            }
            if (newStatements != null) {
                newStatements.add(newValue);
            }
        }
        if (newStatements != null) {
            builder = node.toBuilder();
            builder.statements(newStatements);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public CaseClause visitCaseClause(CaseClause node) {
        CaseClause.Builder builder = null;
        List<Expression> label = node.label();
        List<Expression> newLabel = null;
        for (int idx = 0; idx < label.size(); idx++) {
            Expression value = label.get(idx);
            Expression newValue = (Expression) value.accept(this);
            if (newLabel == null && !value.equals(newValue)) {
                newLabel = new ArrayList<>(label.size());
                newLabel.addAll(label.subList(0, idx));
            }
            if (newLabel != null) {
                newLabel.add(newValue);
            }
        }
        if (newLabel != null) {
            builder = node.toBuilder();
            builder.label(newLabel);
        }
        Block body = node.body();
        Block bodyNew = visitBlock(body);
        if (!Objects.equals(body, bodyNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.body(bodyNew);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public ClassName visitClassName(ClassName node) {
        return node;
    }

    @Override
    public ClassSyntax visitClassSyntax(ClassSyntax node) {
        ClassSyntax.Builder builder = null;
        TypeName superClass = node.superClass();
        TypeName superClassNew = null;
        if (superClass != null) {
            superClassNew = (TypeName) superClass.accept(this);
        }
        if (!Objects.equals(superClass, superClassNew)) {
            builder = node.toBuilder();
            builder.superClass(superClassNew);
        }
        List<TypeVariableTypeName> typeParams = node.typeParams();
        List<TypeVariableTypeName> newTypeParams = null;
        for (int idx = 0; idx < typeParams.size(); idx++) {
            TypeVariableTypeName value = typeParams.get(idx);
            TypeVariableTypeName newValue = visitTypeVariableTypeName(value);
            if (newTypeParams == null && !value.equals(newValue)) {
                newTypeParams = new ArrayList<>(typeParams.size());
                newTypeParams.addAll(typeParams.subList(0, idx));
            }
            if (newTypeParams != null) {
                newTypeParams.add(newValue);
            }
        }
        if (newTypeParams != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.typeParams(newTypeParams);
        }
        Javadoc javadoc = node.javadoc();
        Javadoc javadocNew = null;
        if (javadoc != null) {
            javadocNew = (Javadoc) javadoc.accept(this);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.javadoc(javadocNew);
        }
        List<BaseMethodSyntax> methods = node.methods();
        List<BaseMethodSyntax> newMethods = null;
        for (int idx = 0; idx < methods.size(); idx++) {
            BaseMethodSyntax value = methods.get(idx);
            BaseMethodSyntax newValue = (BaseMethodSyntax) value.accept(this);
            if (newMethods == null && !value.equals(newValue)) {
                newMethods = new ArrayList<>(methods.size());
                newMethods.addAll(methods.subList(0, idx));
            }
            if (newMethods != null) {
                newMethods.add(newValue);
            }
        }
        if (newMethods != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.methods(newMethods);
        }
        List<Annotation> annotations = node.annotations();
        List<Annotation> newAnnotations = null;
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (newAnnotations == null && !value.equals(newValue)) {
                newAnnotations = new ArrayList<>(annotations.size());
                newAnnotations.addAll(annotations.subList(0, idx));
            }
            if (newAnnotations != null) {
                newAnnotations.add(newValue);
            }
        }
        if (newAnnotations != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.annotations(newAnnotations);
        }
        List<FieldSyntax> fields = node.fields();
        List<FieldSyntax> newFields = null;
        for (int idx = 0; idx < fields.size(); idx++) {
            FieldSyntax value = fields.get(idx);
            FieldSyntax newValue = visitFieldSyntax(value);
            if (newFields == null && !value.equals(newValue)) {
                newFields = new ArrayList<>(fields.size());
                newFields.addAll(fields.subList(0, idx));
            }
            if (newFields != null) {
                newFields.add(newValue);
            }
        }
        if (newFields != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.fields(newFields);
        }
        List<TypeName> superInterfaces = node.superInterfaces();
        List<TypeName> newSuperInterfaces = null;
        for (int idx = 0; idx < superInterfaces.size(); idx++) {
            TypeName value = superInterfaces.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (newSuperInterfaces == null && !value.equals(newValue)) {
                newSuperInterfaces = new ArrayList<>(superInterfaces.size());
                newSuperInterfaces.addAll(superInterfaces.subList(0, idx));
            }
            if (newSuperInterfaces != null) {
                newSuperInterfaces.add(newValue);
            }
        }
        if (newSuperInterfaces != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.superInterfaces(newSuperInterfaces);
        }
        List<TypeSyntax> innerTypes = node.innerTypes();
        List<TypeSyntax> newInnerTypes = null;
        for (int idx = 0; idx < innerTypes.size(); idx++) {
            TypeSyntax value = innerTypes.get(idx);
            TypeSyntax newValue = (TypeSyntax) value.accept(this);
            if (newInnerTypes == null && !value.equals(newValue)) {
                newInnerTypes = new ArrayList<>(innerTypes.size());
                newInnerTypes.addAll(innerTypes.subList(0, idx));
            }
            if (newInnerTypes != null) {
                newInnerTypes.add(newValue);
            }
        }
        if (newInnerTypes != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.innerTypes(newInnerTypes);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public CodeBlock visitCodeBlock(CodeBlock node) {
        return node;
    }

    @Override
    public CompilationUnit visitCompilationUnit(CompilationUnit node) {
        CompilationUnit.Builder builder = null;
        Set<ClassName> imports = node.imports();
        Set<ClassName> newImports = null;
        for (ClassName value : imports) {
            ClassName newValue = visitClassName(value);
            if (newImports == null && !value.equals(newValue)) {
                newImports = new LinkedHashSet<>(imports.size());
                for (ClassName innerValue : imports) {
                    if (innerValue == value) {
                        break;
                    }
                    newImports.add(innerValue);
                }
            }
            if (newImports != null) {
                newImports.add(newValue);
            }
        }
        if (newImports != null) {
            builder = node.toBuilder();
            builder.imports(newImports);
        }
        TypeSyntax type = node.type();
        TypeSyntax typeNew = (TypeSyntax) type.accept(this);
        if (!Objects.equals(type, typeNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.type(typeNew);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public ConstructorMethodSyntax visitConstructorMethodSyntax(ConstructorMethodSyntax node) {
        ConstructorMethodSyntax.Builder builder = null;
        Block body = node.body();
        Block bodyNew = visitBlock(body);
        if (!Objects.equals(body, bodyNew)) {
            builder = node.toBuilder();
            builder.body(bodyNew);
        }
        Javadoc javadoc = node.javadoc();
        Javadoc javadocNew = null;
        if (javadoc != null) {
            javadocNew = (Javadoc) javadoc.accept(this);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.javadoc(javadocNew);
        }
        List<Annotation> annotations = node.annotations();
        List<Annotation> newAnnotations = null;
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (newAnnotations == null && !value.equals(newValue)) {
                newAnnotations = new ArrayList<>(annotations.size());
                newAnnotations.addAll(annotations.subList(0, idx));
            }
            if (newAnnotations != null) {
                newAnnotations.add(newValue);
            }
        }
        if (newAnnotations != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.annotations(newAnnotations);
        }
        List<Parameter> parameters = node.parameters();
        List<Parameter> newParameters = null;
        for (int idx = 0; idx < parameters.size(); idx++) {
            Parameter value = parameters.get(idx);
            Parameter newValue = visitParameter(value);
            if (newParameters == null && !value.equals(newValue)) {
                newParameters = new ArrayList<>(parameters.size());
                newParameters.addAll(parameters.subList(0, idx));
            }
            if (newParameters != null) {
                newParameters.add(newValue);
            }
        }
        if (newParameters != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.parameters(newParameters);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public DefaultCaseClause visitDefaultCaseClause(DefaultCaseClause node) {
        DefaultCaseClause.Builder builder = null;
        Block body = node.body();
        Block bodyNew = visitBlock(body);
        if (!Objects.equals(body, bodyNew)) {
            builder = node.toBuilder();
            builder.body(bodyNew);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public EnumConstant visitEnumConstant(EnumConstant node) {
        EnumConstant.Builder builder = null;
        Javadoc javadoc = node.javadoc();
        Javadoc javadocNew = null;
        if (javadoc != null) {
            javadocNew = (Javadoc) javadoc.accept(this);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            builder = node.toBuilder();
            builder.javadoc(javadocNew);
        }
        EnumBody body = node.body();
        EnumBody bodyNew = null;
        if (body != null) {
            bodyNew = (EnumBody) body.accept(this);
        }
        if (!Objects.equals(body, bodyNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.body(bodyNew);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public EnumSyntax visitEnumSyntax(EnumSyntax node) {
        EnumSyntax.Builder builder = null;
        List<EnumConstant> enumConstants = node.enumConstants();
        List<EnumConstant> newEnumConstants = null;
        for (int idx = 0; idx < enumConstants.size(); idx++) {
            EnumConstant value = enumConstants.get(idx);
            EnumConstant newValue = visitEnumConstant(value);
            if (newEnumConstants == null && !value.equals(newValue)) {
                newEnumConstants = new ArrayList<>(enumConstants.size());
                newEnumConstants.addAll(enumConstants.subList(0, idx));
            }
            if (newEnumConstants != null) {
                newEnumConstants.add(newValue);
            }
        }
        if (newEnumConstants != null) {
            builder = node.toBuilder();
            builder.enumConstants(newEnumConstants);
        }
        Javadoc javadoc = node.javadoc();
        Javadoc javadocNew = null;
        if (javadoc != null) {
            javadocNew = (Javadoc) javadoc.accept(this);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.javadoc(javadocNew);
        }
        List<BaseMethodSyntax> methods = node.methods();
        List<BaseMethodSyntax> newMethods = null;
        for (int idx = 0; idx < methods.size(); idx++) {
            BaseMethodSyntax value = methods.get(idx);
            BaseMethodSyntax newValue = (BaseMethodSyntax) value.accept(this);
            if (newMethods == null && !value.equals(newValue)) {
                newMethods = new ArrayList<>(methods.size());
                newMethods.addAll(methods.subList(0, idx));
            }
            if (newMethods != null) {
                newMethods.add(newValue);
            }
        }
        if (newMethods != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.methods(newMethods);
        }
        List<Annotation> annotations = node.annotations();
        List<Annotation> newAnnotations = null;
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (newAnnotations == null && !value.equals(newValue)) {
                newAnnotations = new ArrayList<>(annotations.size());
                newAnnotations.addAll(annotations.subList(0, idx));
            }
            if (newAnnotations != null) {
                newAnnotations.add(newValue);
            }
        }
        if (newAnnotations != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.annotations(newAnnotations);
        }
        List<FieldSyntax> fields = node.fields();
        List<FieldSyntax> newFields = null;
        for (int idx = 0; idx < fields.size(); idx++) {
            FieldSyntax value = fields.get(idx);
            FieldSyntax newValue = visitFieldSyntax(value);
            if (newFields == null && !value.equals(newValue)) {
                newFields = new ArrayList<>(fields.size());
                newFields.addAll(fields.subList(0, idx));
            }
            if (newFields != null) {
                newFields.add(newValue);
            }
        }
        if (newFields != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.fields(newFields);
        }
        List<TypeName> superInterfaces = node.superInterfaces();
        List<TypeName> newSuperInterfaces = null;
        for (int idx = 0; idx < superInterfaces.size(); idx++) {
            TypeName value = superInterfaces.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (newSuperInterfaces == null && !value.equals(newValue)) {
                newSuperInterfaces = new ArrayList<>(superInterfaces.size());
                newSuperInterfaces.addAll(superInterfaces.subList(0, idx));
            }
            if (newSuperInterfaces != null) {
                newSuperInterfaces.add(newValue);
            }
        }
        if (newSuperInterfaces != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.superInterfaces(newSuperInterfaces);
        }
        List<TypeSyntax> innerTypes = node.innerTypes();
        List<TypeSyntax> newInnerTypes = null;
        for (int idx = 0; idx < innerTypes.size(); idx++) {
            TypeSyntax value = innerTypes.get(idx);
            TypeSyntax newValue = (TypeSyntax) value.accept(this);
            if (newInnerTypes == null && !value.equals(newValue)) {
                newInnerTypes = new ArrayList<>(innerTypes.size());
                newInnerTypes.addAll(innerTypes.subList(0, idx));
            }
            if (newInnerTypes != null) {
                newInnerTypes.add(newValue);
            }
        }
        if (newInnerTypes != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.innerTypes(newInnerTypes);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public FieldSyntax visitFieldSyntax(FieldSyntax node) {
        FieldSyntax.Builder builder = null;
        Javadoc javadoc = node.javadoc();
        Javadoc javadocNew = null;
        if (javadoc != null) {
            javadocNew = (Javadoc) javadoc.accept(this);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            builder = node.toBuilder();
            builder.javadoc(javadocNew);
        }
        TypeName type = node.type();
        TypeName typeNew = (TypeName) type.accept(this);
        if (!Objects.equals(type, typeNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.type(typeNew);
        }
        List<Annotation> annotations = node.annotations();
        List<Annotation> newAnnotations = null;
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (newAnnotations == null && !value.equals(newValue)) {
                newAnnotations = new ArrayList<>(annotations.size());
                newAnnotations.addAll(annotations.subList(0, idx));
            }
            if (newAnnotations != null) {
                newAnnotations.add(newValue);
            }
        }
        if (newAnnotations != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.annotations(newAnnotations);
        }
        Expression initializer = node.initializer();
        Expression initializerNew = null;
        if (initializer != null) {
            initializerNew = (Expression) initializer.accept(this);
        }
        if (!Objects.equals(initializer, initializerNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.initializer(initializerNew);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public ForStatement visitForStatement(ForStatement node) {
        ForStatement.Builder builder = null;
        CodeBlock initializer = node.initializer();
        CodeBlock initializerNew = visitCodeBlock(initializer);
        if (!Objects.equals(initializer, initializerNew)) {
            builder = node.toBuilder();
            builder.initializer(initializerNew);
        }
        Block statement = node.statement();
        Block statementNew = visitBlock(statement);
        if (!Objects.equals(statement, statementNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.statement(statementNew);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public IfStatement visitIfStatement(IfStatement node) {
        IfStatement.Builder builder = null;
        Expression expression = node.expression();
        Expression expressionNew = (Expression) expression.accept(this);
        if (!Objects.equals(expression, expressionNew)) {
            builder = node.toBuilder();
            builder.expression(expressionNew);
        }
        Block statement = node.statement();
        Block statementNew = visitBlock(statement);
        if (!Objects.equals(statement, statementNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.statement(statementNew);
        }
        Statement elseStatement = node.elseStatement();
        Statement elseStatementNew = null;
        if (elseStatement != null) {
            elseStatementNew = (Statement) elseStatement.accept(this);
        }
        if (!Objects.equals(elseStatement, elseStatementNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.elseStatement(elseStatementNew);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public InterfaceSyntax visitInterfaceSyntax(InterfaceSyntax node) {
        InterfaceSyntax.Builder builder = null;
        List<TypeVariableTypeName> typeParams = node.typeParams();
        List<TypeVariableTypeName> newTypeParams = null;
        for (int idx = 0; idx < typeParams.size(); idx++) {
            TypeVariableTypeName value = typeParams.get(idx);
            TypeVariableTypeName newValue = visitTypeVariableTypeName(value);
            if (newTypeParams == null && !value.equals(newValue)) {
                newTypeParams = new ArrayList<>(typeParams.size());
                newTypeParams.addAll(typeParams.subList(0, idx));
            }
            if (newTypeParams != null) {
                newTypeParams.add(newValue);
            }
        }
        if (newTypeParams != null) {
            builder = node.toBuilder();
            builder.typeParams(newTypeParams);
        }
        Javadoc javadoc = node.javadoc();
        Javadoc javadocNew = null;
        if (javadoc != null) {
            javadocNew = (Javadoc) javadoc.accept(this);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.javadoc(javadocNew);
        }
        List<BaseMethodSyntax> methods = node.methods();
        List<BaseMethodSyntax> newMethods = null;
        for (int idx = 0; idx < methods.size(); idx++) {
            BaseMethodSyntax value = methods.get(idx);
            BaseMethodSyntax newValue = (BaseMethodSyntax) value.accept(this);
            if (newMethods == null && !value.equals(newValue)) {
                newMethods = new ArrayList<>(methods.size());
                newMethods.addAll(methods.subList(0, idx));
            }
            if (newMethods != null) {
                newMethods.add(newValue);
            }
        }
        if (newMethods != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.methods(newMethods);
        }
        List<Annotation> annotations = node.annotations();
        List<Annotation> newAnnotations = null;
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (newAnnotations == null && !value.equals(newValue)) {
                newAnnotations = new ArrayList<>(annotations.size());
                newAnnotations.addAll(annotations.subList(0, idx));
            }
            if (newAnnotations != null) {
                newAnnotations.add(newValue);
            }
        }
        if (newAnnotations != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.annotations(newAnnotations);
        }
        List<FieldSyntax> fields = node.fields();
        List<FieldSyntax> newFields = null;
        for (int idx = 0; idx < fields.size(); idx++) {
            FieldSyntax value = fields.get(idx);
            FieldSyntax newValue = visitFieldSyntax(value);
            if (newFields == null && !value.equals(newValue)) {
                newFields = new ArrayList<>(fields.size());
                newFields.addAll(fields.subList(0, idx));
            }
            if (newFields != null) {
                newFields.add(newValue);
            }
        }
        if (newFields != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.fields(newFields);
        }
        List<TypeName> superInterfaces = node.superInterfaces();
        List<TypeName> newSuperInterfaces = null;
        for (int idx = 0; idx < superInterfaces.size(); idx++) {
            TypeName value = superInterfaces.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (newSuperInterfaces == null && !value.equals(newValue)) {
                newSuperInterfaces = new ArrayList<>(superInterfaces.size());
                newSuperInterfaces.addAll(superInterfaces.subList(0, idx));
            }
            if (newSuperInterfaces != null) {
                newSuperInterfaces.add(newValue);
            }
        }
        if (newSuperInterfaces != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.superInterfaces(newSuperInterfaces);
        }
        List<TypeSyntax> innerTypes = node.innerTypes();
        List<TypeSyntax> newInnerTypes = null;
        for (int idx = 0; idx < innerTypes.size(); idx++) {
            TypeSyntax value = innerTypes.get(idx);
            TypeSyntax newValue = (TypeSyntax) value.accept(this);
            if (newInnerTypes == null && !value.equals(newValue)) {
                newInnerTypes = new ArrayList<>(innerTypes.size());
                newInnerTypes.addAll(innerTypes.subList(0, idx));
            }
            if (newInnerTypes != null) {
                newInnerTypes.add(newValue);
            }
        }
        if (newInnerTypes != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.innerTypes(newInnerTypes);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public MethodSyntax visitMethodSyntax(MethodSyntax node) {
        MethodSyntax.Builder builder = null;
        List<TypeVariableTypeName> typeParams = node.typeParams();
        List<TypeVariableTypeName> newTypeParams = null;
        for (int idx = 0; idx < typeParams.size(); idx++) {
            TypeVariableTypeName value = typeParams.get(idx);
            TypeVariableTypeName newValue = visitTypeVariableTypeName(value);
            if (newTypeParams == null && !value.equals(newValue)) {
                newTypeParams = new ArrayList<>(typeParams.size());
                newTypeParams.addAll(typeParams.subList(0, idx));
            }
            if (newTypeParams != null) {
                newTypeParams.add(newValue);
            }
        }
        if (newTypeParams != null) {
            builder = node.toBuilder();
            builder.typeParams(newTypeParams);
        }
        TypeName returns = node.returns();
        TypeName returnsNew = (TypeName) returns.accept(this);
        if (!Objects.equals(returns, returnsNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.returns(returnsNew);
        }
        Block body = node.body();
        Block bodyNew = visitBlock(body);
        if (!Objects.equals(body, bodyNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.body(bodyNew);
        }
        Javadoc javadoc = node.javadoc();
        Javadoc javadocNew = null;
        if (javadoc != null) {
            javadocNew = (Javadoc) javadoc.accept(this);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.javadoc(javadocNew);
        }
        List<Annotation> annotations = node.annotations();
        List<Annotation> newAnnotations = null;
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (newAnnotations == null && !value.equals(newValue)) {
                newAnnotations = new ArrayList<>(annotations.size());
                newAnnotations.addAll(annotations.subList(0, idx));
            }
            if (newAnnotations != null) {
                newAnnotations.add(newValue);
            }
        }
        if (newAnnotations != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.annotations(newAnnotations);
        }
        List<Parameter> parameters = node.parameters();
        List<Parameter> newParameters = null;
        for (int idx = 0; idx < parameters.size(); idx++) {
            Parameter value = parameters.get(idx);
            Parameter newValue = visitParameter(value);
            if (newParameters == null && !value.equals(newValue)) {
                newParameters = new ArrayList<>(parameters.size());
                newParameters.addAll(parameters.subList(0, idx));
            }
            if (newParameters != null) {
                newParameters.add(newValue);
            }
        }
        if (newParameters != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.parameters(newParameters);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public Parameter visitParameter(Parameter node) {
        Parameter.Builder builder = null;
        TypeName type = node.type();
        TypeName typeNew = (TypeName) type.accept(this);
        if (!Objects.equals(type, typeNew)) {
            builder = node.toBuilder();
            builder.type(typeNew);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public ParameterizedTypeName visitParameterizedTypeName(ParameterizedTypeName node) {
        ParameterizedTypeName.Builder builder = null;
        ClassName rawType = node.rawType();
        ClassName rawTypeNew = visitClassName(rawType);
        if (!Objects.equals(rawType, rawTypeNew)) {
            builder = node.toBuilder();
            builder.rawType(rawTypeNew);
        }
        List<TypeName> typeArguments = node.typeArguments();
        List<TypeName> newTypeArguments = null;
        for (int idx = 0; idx < typeArguments.size(); idx++) {
            TypeName value = typeArguments.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (newTypeArguments == null && !value.equals(newValue)) {
                newTypeArguments = new ArrayList<>(typeArguments.size());
                newTypeArguments.addAll(typeArguments.subList(0, idx));
            }
            if (newTypeArguments != null) {
                newTypeArguments.add(newValue);
            }
        }
        if (newTypeArguments != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.typeArguments(newTypeArguments);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public PrimitiveTypeName visitPrimitiveTypeName(PrimitiveTypeName node) {
        return node;
    }

    @Override
    public SwitchStatement visitSwitchStatement(SwitchStatement node) {
        SwitchStatement.Builder builder = null;
        Expression expression = node.expression();
        Expression expressionNew = (Expression) expression.accept(this);
        if (!Objects.equals(expression, expressionNew)) {
            builder = node.toBuilder();
            builder.expression(expressionNew);
        }
        List<CaseClause> cases = node.cases();
        List<CaseClause> newCases = null;
        for (int idx = 0; idx < cases.size(); idx++) {
            CaseClause value = cases.get(idx);
            CaseClause newValue = visitCaseClause(value);
            if (newCases == null && !value.equals(newValue)) {
                newCases = new ArrayList<>(cases.size());
                newCases.addAll(cases.subList(0, idx));
            }
            if (newCases != null) {
                newCases.add(newValue);
            }
        }
        if (newCases != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.cases(newCases);
        }
        DefaultCaseClause defaultCase = node.defaultCase();
        DefaultCaseClause defaultCaseNew = null;
        if (defaultCase != null) {
            defaultCaseNew = visitDefaultCaseClause(defaultCase);
        }
        if (!Objects.equals(defaultCase, defaultCaseNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.defaultCase(defaultCaseNew);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public TypeVariableTypeName visitTypeVariableTypeName(TypeVariableTypeName node) {
        TypeVariableTypeName.Builder builder = null;
        List<TypeName> bounds = node.bounds();
        List<TypeName> newBounds = null;
        for (int idx = 0; idx < bounds.size(); idx++) {
            TypeName value = bounds.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (newBounds == null && !value.equals(newValue)) {
                newBounds = new ArrayList<>(bounds.size());
                newBounds.addAll(bounds.subList(0, idx));
            }
            if (newBounds != null) {
                newBounds.add(newValue);
            }
        }
        if (newBounds != null) {
            builder = node.toBuilder();
            builder.bounds(newBounds);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public WildcardTypeName visitWildcardTypeName(WildcardTypeName node) {
        WildcardTypeName.Builder builder = null;
        ClassName rawType = node.rawType();
        ClassName rawTypeNew = null;
        if (rawType != null) {
            rawTypeNew = visitClassName(rawType);
        }
        if (!Objects.equals(rawType, rawTypeNew)) {
            builder = node.toBuilder();
            builder.rawType(rawTypeNew);
        }
        List<TypeName> upperBounds = node.upperBounds();
        List<TypeName> newUpperBounds = null;
        for (int idx = 0; idx < upperBounds.size(); idx++) {
            TypeName value = upperBounds.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (newUpperBounds == null && !value.equals(newValue)) {
                newUpperBounds = new ArrayList<>(upperBounds.size());
                newUpperBounds.addAll(upperBounds.subList(0, idx));
            }
            if (newUpperBounds != null) {
                newUpperBounds.add(newValue);
            }
        }
        if (newUpperBounds != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.upperBounds(newUpperBounds);
        }
        List<TypeName> lowerBounds = node.lowerBounds();
        List<TypeName> newLowerBounds = null;
        for (int idx = 0; idx < lowerBounds.size(); idx++) {
            TypeName value = lowerBounds.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (newLowerBounds == null && !value.equals(newValue)) {
                newLowerBounds = new ArrayList<>(lowerBounds.size());
                newLowerBounds.addAll(lowerBounds.subList(0, idx));
            }
            if (newLowerBounds != null) {
                newLowerBounds.add(newValue);
            }
        }
        if (newLowerBounds != null) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.lowerBounds(newLowerBounds);
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }
}
