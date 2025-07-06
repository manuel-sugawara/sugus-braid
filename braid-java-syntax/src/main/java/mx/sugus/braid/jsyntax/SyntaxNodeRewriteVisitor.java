package mx.sugus.braid.jsyntax;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
        boolean typeParamsChanged = false;
        int typeParamsSize = typeParams.size();
        for (int idx = 0; idx < typeParamsSize; idx++) {
            TypeVariableTypeName value = typeParams.get(idx);
            TypeVariableTypeName newValue = visitTypeVariableTypeName(value);
            if (!typeParamsChanged && value != newValue) {
                typeParamsChanged = true;
                builder = node.toBuilder();
                builder.typeParams(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addTypeParam(typeParams.get(innerIdx));
                }
            }
            if (typeParamsChanged) {
                builder.addTypeParam(newValue);
            }
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
            javadocNew = visitJavadoc(javadoc);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.javadoc(javadocNew);
        }
        List<Annotation> annotations = node.annotations();
        boolean annotationsChanged = false;
        int annotationsSize = annotations.size();
        for (int idx = 0; idx < annotationsSize; idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (!annotationsChanged && value != newValue) {
                annotationsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.annotations(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addAnnotation(annotations.get(innerIdx));
                }
            }
            if (annotationsChanged) {
                builder.addAnnotation(newValue);
            }
        }
        List<Parameter> parameters = node.parameters();
        boolean parametersChanged = false;
        int parametersSize = parameters.size();
        for (int idx = 0; idx < parametersSize; idx++) {
            Parameter value = parameters.get(idx);
            Parameter newValue = visitParameter(value);
            if (!parametersChanged && value != newValue) {
                parametersChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.parameters(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addParameter(parameters.get(innerIdx));
                }
            }
            if (parametersChanged) {
                builder.addParameter(newValue);
            }
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
        boolean statementsChanged = false;
        int statementsSize = statements.size();
        for (int idx = 0; idx < statementsSize; idx++) {
            Statement value = statements.get(idx);
            Statement newValue = (Statement) value.accept(this);
            if (!statementsChanged && value != newValue) {
                statementsChanged = true;
                builder = node.toBuilder();
                builder.statements(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addStatement(statements.get(innerIdx));
                }
            }
            if (statementsChanged) {
                builder.addStatement(newValue);
            }
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
        boolean labelChanged = false;
        int labelSize = label.size();
        for (int idx = 0; idx < labelSize; idx++) {
            Expression value = label.get(idx);
            Expression newValue = (Expression) value.accept(this);
            if (!labelChanged && value != newValue) {
                labelChanged = true;
                builder = node.toBuilder();
                builder.label(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addLabel(label.get(innerIdx));
                }
            }
            if (labelChanged) {
                builder.addLabel(newValue);
            }
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
        boolean typeParamsChanged = false;
        int typeParamsSize = typeParams.size();
        for (int idx = 0; idx < typeParamsSize; idx++) {
            TypeVariableTypeName value = typeParams.get(idx);
            TypeVariableTypeName newValue = visitTypeVariableTypeName(value);
            if (!typeParamsChanged && value != newValue) {
                typeParamsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.typeParams(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addTypeParam(typeParams.get(innerIdx));
                }
            }
            if (typeParamsChanged) {
                builder.addTypeParam(newValue);
            }
        }
        Javadoc javadoc = node.javadoc();
        Javadoc javadocNew = null;
        if (javadoc != null) {
            javadocNew = visitJavadoc(javadoc);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.javadoc(javadocNew);
        }
        List<Annotation> annotations = node.annotations();
        boolean annotationsChanged = false;
        int annotationsSize = annotations.size();
        for (int idx = 0; idx < annotationsSize; idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (!annotationsChanged && value != newValue) {
                annotationsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.annotations(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addAnnotation(annotations.get(innerIdx));
                }
            }
            if (annotationsChanged) {
                builder.addAnnotation(newValue);
            }
        }
        List<TypeName> superInterfaces = node.superInterfaces();
        boolean superInterfacesChanged = false;
        int superInterfacesSize = superInterfaces.size();
        for (int idx = 0; idx < superInterfacesSize; idx++) {
            TypeName value = superInterfaces.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (!superInterfacesChanged && value != newValue) {
                superInterfacesChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.superInterfaces(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addSuperInterface(superInterfaces.get(innerIdx));
                }
            }
            if (superInterfacesChanged) {
                builder.addSuperInterface(newValue);
            }
        }
        List<FieldSyntax> fields = node.fields();
        boolean fieldsChanged = false;
        int fieldsSize = fields.size();
        for (int idx = 0; idx < fieldsSize; idx++) {
            FieldSyntax value = fields.get(idx);
            FieldSyntax newValue = visitFieldSyntax(value);
            if (!fieldsChanged && value != newValue) {
                fieldsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.fields(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addField(fields.get(innerIdx));
                }
            }
            if (fieldsChanged) {
                builder.addField(newValue);
            }
        }
        List<BaseMethodSyntax> methods = node.methods();
        boolean methodsChanged = false;
        int methodsSize = methods.size();
        for (int idx = 0; idx < methodsSize; idx++) {
            BaseMethodSyntax value = methods.get(idx);
            BaseMethodSyntax newValue = (BaseMethodSyntax) value.accept(this);
            if (!methodsChanged && value != newValue) {
                methodsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.methods(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addMethod(methods.get(innerIdx));
                }
            }
            if (methodsChanged) {
                builder.addMethod(newValue);
            }
        }
        List<TypeSyntax> innerTypes = node.innerTypes();
        boolean innerTypesChanged = false;
        int innerTypesSize = innerTypes.size();
        for (int idx = 0; idx < innerTypesSize; idx++) {
            TypeSyntax value = innerTypes.get(idx);
            TypeSyntax newValue = (TypeSyntax) value.accept(this);
            if (!innerTypesChanged && value != newValue) {
                innerTypesChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.innerTypes(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addInnerType(innerTypes.get(innerIdx));
                }
            }
            if (innerTypesChanged) {
                builder.addInnerType(newValue);
            }
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
        boolean importsChanged = false;
        for (ClassName value : imports) {
            ClassName newValue = visitClassName(value);
            if (!importsChanged && value != newValue) {
                importsChanged = true;
                builder = node.toBuilder();
                builder.imports(Collections.emptySet());
                for (ClassName innerValue : imports) {
                    if (innerValue == value) {
                        break;
                    }
                    builder.addImport(innerValue);
                }
            }
            if (importsChanged) {
                builder.addImport(newValue);
            }
        }
        TypeSyntax type = node.type();
        TypeSyntax typeNew = (TypeSyntax) type.accept(this);
        if (!Objects.equals(type, typeNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.type(typeNew);
        }
        Map<String, ClassName> definedNames = node.definedNames();
        boolean definedNamesChanged = false;
        for (Map.Entry<String, ClassName> kvp : definedNames.entrySet()) {
            ClassName value = kvp.getValue();
            ClassName newValue = visitClassName(value);
            if (!definedNamesChanged && value != newValue) {
                definedNamesChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.definedNames(Collections.emptyMap());
                for (Map.Entry<String, ClassName> innerKvp : definedNames.entrySet()) {
                    if (innerKvp.getValue() == value) {
                        break;
                    }
                    builder.putDefinedName(innerKvp.getKey(), innerKvp.getValue());
                }
            }
            if (definedNamesChanged) {
                builder.putDefinedName(kvp.getKey(), newValue);
            }
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
            javadocNew = visitJavadoc(javadoc);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.javadoc(javadocNew);
        }
        List<Annotation> annotations = node.annotations();
        boolean annotationsChanged = false;
        int annotationsSize = annotations.size();
        for (int idx = 0; idx < annotationsSize; idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (!annotationsChanged && value != newValue) {
                annotationsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.annotations(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addAnnotation(annotations.get(innerIdx));
                }
            }
            if (annotationsChanged) {
                builder.addAnnotation(newValue);
            }
        }
        List<Parameter> parameters = node.parameters();
        boolean parametersChanged = false;
        int parametersSize = parameters.size();
        for (int idx = 0; idx < parametersSize; idx++) {
            Parameter value = parameters.get(idx);
            Parameter newValue = visitParameter(value);
            if (!parametersChanged && value != newValue) {
                parametersChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.parameters(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addParameter(parameters.get(innerIdx));
                }
            }
            if (parametersChanged) {
                builder.addParameter(newValue);
            }
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
            javadocNew = visitJavadoc(javadoc);
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
        boolean enumConstantsChanged = false;
        int enumConstantsSize = enumConstants.size();
        for (int idx = 0; idx < enumConstantsSize; idx++) {
            EnumConstant value = enumConstants.get(idx);
            EnumConstant newValue = visitEnumConstant(value);
            if (!enumConstantsChanged && value != newValue) {
                enumConstantsChanged = true;
                builder = node.toBuilder();
                builder.enumConstants(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addEnumConstant(enumConstants.get(innerIdx));
                }
            }
            if (enumConstantsChanged) {
                builder.addEnumConstant(newValue);
            }
        }
        Javadoc javadoc = node.javadoc();
        Javadoc javadocNew = null;
        if (javadoc != null) {
            javadocNew = visitJavadoc(javadoc);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.javadoc(javadocNew);
        }
        List<Annotation> annotations = node.annotations();
        boolean annotationsChanged = false;
        int annotationsSize = annotations.size();
        for (int idx = 0; idx < annotationsSize; idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (!annotationsChanged && value != newValue) {
                annotationsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.annotations(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addAnnotation(annotations.get(innerIdx));
                }
            }
            if (annotationsChanged) {
                builder.addAnnotation(newValue);
            }
        }
        List<TypeName> superInterfaces = node.superInterfaces();
        boolean superInterfacesChanged = false;
        int superInterfacesSize = superInterfaces.size();
        for (int idx = 0; idx < superInterfacesSize; idx++) {
            TypeName value = superInterfaces.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (!superInterfacesChanged && value != newValue) {
                superInterfacesChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.superInterfaces(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addSuperInterface(superInterfaces.get(innerIdx));
                }
            }
            if (superInterfacesChanged) {
                builder.addSuperInterface(newValue);
            }
        }
        List<FieldSyntax> fields = node.fields();
        boolean fieldsChanged = false;
        int fieldsSize = fields.size();
        for (int idx = 0; idx < fieldsSize; idx++) {
            FieldSyntax value = fields.get(idx);
            FieldSyntax newValue = visitFieldSyntax(value);
            if (!fieldsChanged && value != newValue) {
                fieldsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.fields(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addField(fields.get(innerIdx));
                }
            }
            if (fieldsChanged) {
                builder.addField(newValue);
            }
        }
        List<BaseMethodSyntax> methods = node.methods();
        boolean methodsChanged = false;
        int methodsSize = methods.size();
        for (int idx = 0; idx < methodsSize; idx++) {
            BaseMethodSyntax value = methods.get(idx);
            BaseMethodSyntax newValue = (BaseMethodSyntax) value.accept(this);
            if (!methodsChanged && value != newValue) {
                methodsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.methods(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addMethod(methods.get(innerIdx));
                }
            }
            if (methodsChanged) {
                builder.addMethod(newValue);
            }
        }
        List<TypeSyntax> innerTypes = node.innerTypes();
        boolean innerTypesChanged = false;
        int innerTypesSize = innerTypes.size();
        for (int idx = 0; idx < innerTypesSize; idx++) {
            TypeSyntax value = innerTypes.get(idx);
            TypeSyntax newValue = (TypeSyntax) value.accept(this);
            if (!innerTypesChanged && value != newValue) {
                innerTypesChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.innerTypes(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addInnerType(innerTypes.get(innerIdx));
                }
            }
            if (innerTypesChanged) {
                builder.addInnerType(newValue);
            }
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
            javadocNew = visitJavadoc(javadoc);
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
        boolean annotationsChanged = false;
        int annotationsSize = annotations.size();
        for (int idx = 0; idx < annotationsSize; idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (!annotationsChanged && value != newValue) {
                annotationsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.annotations(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addAnnotation(annotations.get(innerIdx));
                }
            }
            if (annotationsChanged) {
                builder.addAnnotation(newValue);
            }
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
        boolean typeParamsChanged = false;
        int typeParamsSize = typeParams.size();
        for (int idx = 0; idx < typeParamsSize; idx++) {
            TypeVariableTypeName value = typeParams.get(idx);
            TypeVariableTypeName newValue = visitTypeVariableTypeName(value);
            if (!typeParamsChanged && value != newValue) {
                typeParamsChanged = true;
                builder = node.toBuilder();
                builder.typeParams(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addTypeParam(typeParams.get(innerIdx));
                }
            }
            if (typeParamsChanged) {
                builder.addTypeParam(newValue);
            }
        }
        Javadoc javadoc = node.javadoc();
        Javadoc javadocNew = null;
        if (javadoc != null) {
            javadocNew = visitJavadoc(javadoc);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.javadoc(javadocNew);
        }
        List<Annotation> annotations = node.annotations();
        boolean annotationsChanged = false;
        int annotationsSize = annotations.size();
        for (int idx = 0; idx < annotationsSize; idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (!annotationsChanged && value != newValue) {
                annotationsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.annotations(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addAnnotation(annotations.get(innerIdx));
                }
            }
            if (annotationsChanged) {
                builder.addAnnotation(newValue);
            }
        }
        List<TypeName> superInterfaces = node.superInterfaces();
        boolean superInterfacesChanged = false;
        int superInterfacesSize = superInterfaces.size();
        for (int idx = 0; idx < superInterfacesSize; idx++) {
            TypeName value = superInterfaces.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (!superInterfacesChanged && value != newValue) {
                superInterfacesChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.superInterfaces(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addSuperInterface(superInterfaces.get(innerIdx));
                }
            }
            if (superInterfacesChanged) {
                builder.addSuperInterface(newValue);
            }
        }
        List<FieldSyntax> fields = node.fields();
        boolean fieldsChanged = false;
        int fieldsSize = fields.size();
        for (int idx = 0; idx < fieldsSize; idx++) {
            FieldSyntax value = fields.get(idx);
            FieldSyntax newValue = visitFieldSyntax(value);
            if (!fieldsChanged && value != newValue) {
                fieldsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.fields(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addField(fields.get(innerIdx));
                }
            }
            if (fieldsChanged) {
                builder.addField(newValue);
            }
        }
        List<BaseMethodSyntax> methods = node.methods();
        boolean methodsChanged = false;
        int methodsSize = methods.size();
        for (int idx = 0; idx < methodsSize; idx++) {
            BaseMethodSyntax value = methods.get(idx);
            BaseMethodSyntax newValue = (BaseMethodSyntax) value.accept(this);
            if (!methodsChanged && value != newValue) {
                methodsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.methods(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addMethod(methods.get(innerIdx));
                }
            }
            if (methodsChanged) {
                builder.addMethod(newValue);
            }
        }
        List<TypeSyntax> innerTypes = node.innerTypes();
        boolean innerTypesChanged = false;
        int innerTypesSize = innerTypes.size();
        for (int idx = 0; idx < innerTypesSize; idx++) {
            TypeSyntax value = innerTypes.get(idx);
            TypeSyntax newValue = (TypeSyntax) value.accept(this);
            if (!innerTypesChanged && value != newValue) {
                innerTypesChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.innerTypes(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addInnerType(innerTypes.get(innerIdx));
                }
            }
            if (innerTypesChanged) {
                builder.addInnerType(newValue);
            }
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }

    @Override
    public Javadoc visitJavadoc(Javadoc node) {
        Javadoc.Builder builder = null;
        CodeBlock body = node.body();
        CodeBlock bodyNew = null;
        if (body != null) {
            bodyNew = visitCodeBlock(body);
        }
        if (!Objects.equals(body, bodyNew)) {
            builder = node.toBuilder();
            builder.body(bodyNew);
        }
        Map<String, CodeBlock> params = node.params();
        boolean paramsChanged = false;
        for (Map.Entry<String, CodeBlock> kvp : params.entrySet()) {
            CodeBlock value = kvp.getValue();
            CodeBlock newValue = visitCodeBlock(value);
            if (!paramsChanged && value != newValue) {
                paramsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.params(Collections.emptyMap());
                for (Map.Entry<String, CodeBlock> innerKvp : params.entrySet()) {
                    if (innerKvp.getValue() == value) {
                        break;
                    }
                    builder.putParam(innerKvp.getKey(), innerKvp.getValue());
                }
            }
            if (paramsChanged) {
                builder.putParam(kvp.getKey(), newValue);
            }
        }
        CodeBlock returns = node.returns();
        CodeBlock returnsNew = null;
        if (returns != null) {
            returnsNew = visitCodeBlock(returns);
        }
        if (!Objects.equals(returns, returnsNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.returns(returnsNew);
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
        boolean typeParamsChanged = false;
        int typeParamsSize = typeParams.size();
        for (int idx = 0; idx < typeParamsSize; idx++) {
            TypeVariableTypeName value = typeParams.get(idx);
            TypeVariableTypeName newValue = visitTypeVariableTypeName(value);
            if (!typeParamsChanged && value != newValue) {
                typeParamsChanged = true;
                builder = node.toBuilder();
                builder.typeParams(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addTypeParam(typeParams.get(innerIdx));
                }
            }
            if (typeParamsChanged) {
                builder.addTypeParam(newValue);
            }
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
            javadocNew = visitJavadoc(javadoc);
        }
        if (!Objects.equals(javadoc, javadocNew)) {
            if (builder == null) {
                builder = node.toBuilder();
            }
            builder.javadoc(javadocNew);
        }
        List<Annotation> annotations = node.annotations();
        boolean annotationsChanged = false;
        int annotationsSize = annotations.size();
        for (int idx = 0; idx < annotationsSize; idx++) {
            Annotation value = annotations.get(idx);
            Annotation newValue = visitAnnotation(value);
            if (!annotationsChanged && value != newValue) {
                annotationsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.annotations(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addAnnotation(annotations.get(innerIdx));
                }
            }
            if (annotationsChanged) {
                builder.addAnnotation(newValue);
            }
        }
        List<Parameter> parameters = node.parameters();
        boolean parametersChanged = false;
        int parametersSize = parameters.size();
        for (int idx = 0; idx < parametersSize; idx++) {
            Parameter value = parameters.get(idx);
            Parameter newValue = visitParameter(value);
            if (!parametersChanged && value != newValue) {
                parametersChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.parameters(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addParameter(parameters.get(innerIdx));
                }
            }
            if (parametersChanged) {
                builder.addParameter(newValue);
            }
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
        boolean typeArgumentsChanged = false;
        int typeArgumentsSize = typeArguments.size();
        for (int idx = 0; idx < typeArgumentsSize; idx++) {
            TypeName value = typeArguments.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (!typeArgumentsChanged && value != newValue) {
                typeArgumentsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.typeArguments(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addTypeArgument(typeArguments.get(innerIdx));
                }
            }
            if (typeArgumentsChanged) {
                builder.addTypeArgument(newValue);
            }
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
        boolean casesChanged = false;
        int casesSize = cases.size();
        for (int idx = 0; idx < casesSize; idx++) {
            CaseClause value = cases.get(idx);
            CaseClause newValue = visitCaseClause(value);
            if (!casesChanged && value != newValue) {
                casesChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.cases(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addCase(cases.get(innerIdx));
                }
            }
            if (casesChanged) {
                builder.addCase(newValue);
            }
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
        boolean boundsChanged = false;
        int boundsSize = bounds.size();
        for (int idx = 0; idx < boundsSize; idx++) {
            TypeName value = bounds.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (!boundsChanged && value != newValue) {
                boundsChanged = true;
                builder = node.toBuilder();
                builder.bounds(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addBound(bounds.get(innerIdx));
                }
            }
            if (boundsChanged) {
                builder.addBound(newValue);
            }
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
        boolean upperBoundsChanged = false;
        int upperBoundsSize = upperBounds.size();
        for (int idx = 0; idx < upperBoundsSize; idx++) {
            TypeName value = upperBounds.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (!upperBoundsChanged && value != newValue) {
                upperBoundsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.upperBounds(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addUpperBound(upperBounds.get(innerIdx));
                }
            }
            if (upperBoundsChanged) {
                builder.addUpperBound(newValue);
            }
        }
        List<TypeName> lowerBounds = node.lowerBounds();
        boolean lowerBoundsChanged = false;
        int lowerBoundsSize = lowerBounds.size();
        for (int idx = 0; idx < lowerBoundsSize; idx++) {
            TypeName value = lowerBounds.get(idx);
            TypeName newValue = (TypeName) value.accept(this);
            if (!lowerBoundsChanged && value != newValue) {
                lowerBoundsChanged = true;
                if (builder == null) {
                    builder = node.toBuilder();
                }
                builder.lowerBounds(Collections.emptyList());
                for (int innerIdx = 0; innerIdx < idx; innerIdx++) {
                    builder.addLowerBound(lowerBounds.get(innerIdx));
                }
            }
            if (lowerBoundsChanged) {
                builder.addLowerBound(newValue);
            }
        }
        if (builder != null) {
            return builder.build();
        }
        return node;
    }
}
