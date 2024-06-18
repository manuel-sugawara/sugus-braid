package mx.sugus.braid.jsyntax.writer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import mx.sugus.braid.jsyntax.AbstractControlFlow;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.Annotation;
import mx.sugus.braid.jsyntax.ArrayTypeName;
import mx.sugus.braid.jsyntax.BaseMethodSyntax;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.CaseClause;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.DefaultCaseClause;
import mx.sugus.braid.jsyntax.EnumConstant;
import mx.sugus.braid.jsyntax.EnumSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.ForStatement;
import mx.sugus.braid.jsyntax.FormatterBlock;
import mx.sugus.braid.jsyntax.FormatterLiteral;
import mx.sugus.braid.jsyntax.FormatterNode;
import mx.sugus.braid.jsyntax.FormatterString;
import mx.sugus.braid.jsyntax.FormatterTypeName;
import mx.sugus.braid.jsyntax.IfStatement;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.Javadoc;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.Parameter;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.PrimitiveTypeName;
import mx.sugus.braid.jsyntax.Statement;
import mx.sugus.braid.jsyntax.SwitchStatement;
import mx.sugus.braid.jsyntax.SyntaxNode;
import mx.sugus.braid.jsyntax.SyntaxNodeWalkVisitor;
import mx.sugus.braid.jsyntax.TypeName;
import mx.sugus.braid.jsyntax.TypeSyntax;
import mx.sugus.braid.jsyntax.TypeVariableTypeName;
import mx.sugus.braid.jsyntax.WildcardTypeName;
import mx.sugus.braid.jsyntax.ext.TypeNameExt;

public final class CodeWriterWalkVisitor extends SyntaxNodeWalkVisitor {
    private final CodeWriter writer;
    private final Deque<TypeSyntax> types;
    private final Deque<CodeBlockContext> codeBlockContexts;
    private final Map<String, ClassName> simpleNames;
    private final String containingPackage;

    public CodeWriterWalkVisitor(CodeWriter writer, String containingPackage, Map<String, ClassName> simpleNames) {
        this.writer = writer;
        this.containingPackage = containingPackage;
        this.simpleNames = simpleNames;
        this.types = new ArrayDeque<>();
        this.codeBlockContexts = new ArrayDeque<>();
        codeBlockContexts.push(CodeBlockContext.NONE);
    }

    @Override
    public String toString() {
        return writer.toString();
    }

    @Override
    public SyntaxNode visitAbstractControlFlow(AbstractControlFlow node) {
        withExpressionContext(() -> node.prefix().accept(this));
        writer.beginControlFlow("");
        node.statement().accept(this);
        AbstractControlFlow next = node.next();
        while (next != null) {
            writer.beginNextControlFlow();
            var finalNext = next;
            withExpressionContext(() -> finalNext.prefix().accept(this));
            writer.beginControlFlow("");
            next.statement().accept(this);
            next = next.next();
        }
        writer.endControlFlow();
        return node;
    }

    @Override
    public SyntaxNode visitIfStatement(IfStatement node) {
        writer.write("if (");
        withExpressionContext(() -> node.expression().accept(this));
        writer.beginControlFlow(")");
        node.statement().accept(this);
        Statement elseStatement = node.elseStatement();
        if (elseStatement != null) {
            if (elseStatement instanceof IfStatement ifs) {
                writer.beginNextControlFlow();
                writer.write("else ");
                ifs.accept(this);
            } else {
                writer.nextControlFlow("else");
                withStatementContext(() -> elseStatement.accept(this));
                writer.endControlFlow();
            }
        } else {
            writer.endControlFlow();
        }
        return node;
    }

    @Override
    public SyntaxNode visitForStatement(ForStatement node) {
        writer.write("for (");
        withExpressionContext(() -> node.initializer().accept(this));
        writer.write(")");
        writer.beginControlFlow("");
        withStatementContext(() -> node.statement().accept(this));
        writer.endControlFlow();
        return node;
    }

    @Override
    public SyntaxNode visitFieldSyntax(FieldSyntax node) {
        List<Annotation> annotations = node.annotations();
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            visitAnnotation(value);
        }
        for (var modifier : node.modifiers()) {
            writer.write(modifier.toString());
            writer.write(" ");
        }
        node.type().accept(this);
        writer.write(" ");
        writer.write(node.name());
        var init = node.initializer();
        if (init != null) {
            writer.write(" = ");
            init.accept(this);
        }
        writer.writeln(";");
        return node;
    }

    @Override
    public SyntaxNode visitEnumSyntax(EnumSyntax node) {
        visitTypeSyntax(node);
        return node;
    }

    @Override
    public SyntaxNode visitAnnotation(Annotation node) {
        writer.write("@");
        node.type().accept(this);
        SyntaxNode value = node.value();
        if (value != null) {
            writer.write("(");
            value.accept(this);
            writer.write(")");
        }
        writer.newLine();
        return node;
    }

    @Override
    public SyntaxNode visitCodeBlock(CodeBlock node) {
        if (codeBlockContexts.peekFirst() == CodeBlockContext.JAVADOC) {
            writer.writeln("/**");
            writer.linePrefix(" * ");
            for (var part : node.parts()) {
                visitFormatterNode(part);
            }
            writer.ensureNewline();
            writer.resetLinePrefix();
            writer.writeln(" */");
            return node;
        }
        if (codeBlockContexts.peekFirst() == CodeBlockContext.STATEMENT) {
            writer.indentFollowingLines();
        }
        for (var part : node.parts()) {
            visitFormatterNode(part);
        }
        if (codeBlockContexts.peekFirst() == CodeBlockContext.STATEMENT) {
            writer.writeln(";");
            writer.resetIndentFollowingLines();
        }
        return node;
    }

    @Override
    public SyntaxNode visitBlock(Block node) {
        withStatementContext(() -> {
            List<Statement> statements = node.statements();
            for (int idx = 0; idx < statements.size(); idx++) {
                Statement value = statements.get(idx);
                value.accept(this);
            }
        });
        return node;
    }

    @Override
    public SyntaxNode visitParameter(Parameter node) {
        node.type().accept(this);
        if (node.varargs()) {
            writer.write("... ");
            writer.write(node.name());
        } else {
            writer.write(" ");
            writer.write(node.name());
        }
        return node;
    }

    @Override
    public SyntaxNode visitClassSyntax(ClassSyntax node) {
        visitTypeSyntax(node);
        return node;
    }

    private SyntaxNode visitTypeSyntax(TypeSyntax node) {
        types.push(node);
        Javadoc doc = node.javadoc();
        if (doc != null) {
            withJavaDocContext(() -> doc.accept(this));
        }
        List<Annotation> annotations = node.annotations();
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            visitAnnotation(value);
        }
        for (var modifier : node.modifiers()) {
            writer.write(modifier.toString());
            writer.write(" ");
        }
        var isInterface = node instanceof InterfaceSyntax;
        var isClass = node instanceof ClassSyntax;
        var isEnum = node instanceof EnumSyntax;
        if (isClass) {
            writer.write("class ");
        } else if (isInterface) {
            writer.write("interface ");
        } else if (isEnum) {
            writer.write("enum ");
        } else {
            throw new IllegalArgumentException("unknown TypeSyntax: " + node);
        }
        writer.write(node.name());
        if (node instanceof ClassSyntax classSyntax) {
            visitTypeParams(classSyntax.typeParams());
            var superClass = classSyntax.superClass();
            if (superClass != null) {
                writer.write(" extends ");
                superClass.accept(this);
            }
        } else if (node instanceof InterfaceSyntax interfaceSyntax) {
            visitTypeParams(interfaceSyntax.typeParams());
        }
        if (!node.superInterfaces().isEmpty()) {
            if (isInterface) {
                writer.write(" extends ");
            } else {
                writer.write(" implements ");
            }
            boolean isFirst = true;
            for (TypeName superInterface : node.superInterfaces()) {
                if (!isFirst) {
                    writer.write(", ");
                }
                superInterface.accept(this);
                isFirst = false;
            }
        }
        writer.beginControlFlow("");
        if (node instanceof EnumSyntax e) {
            visitEnumConstants(e);
        }
        List<FieldSyntax> fields = node.fields();
        for (int idx = 0; idx < fields.size(); idx++) {
            FieldSyntax value = fields.get(idx);
            this.visitFieldSyntax(value);
        }
        List<BaseMethodSyntax> methods = node.methods();
        for (int idx = 0; idx < methods.size(); idx++) {
            writer.ensureNewline();
            BaseMethodSyntax value = methods.get(idx);
            value.accept(this);
        }
        List<TypeSyntax> innerTypes = node.innerTypes();
        for (int idx = 0; idx < innerTypes.size(); idx++) {
            writer.ensureNewline();
            TypeSyntax value = innerTypes.get(idx);
            value.accept(this);
        }
        writer.endControlFlow();
        types.pop();
        return node;
    }

    void visitTypeParams(List<TypeVariableTypeName> typeParams) {
        if (!typeParams.isEmpty()) {
            var isFirstTypeVariable = true;
            writer.write("<");
            for (var typeVariable : typeParams) {
                if (!isFirstTypeVariable) {
                    writer.write(", ");
                } else {
                    isFirstTypeVariable = false;
                }
                typeVariable.accept(this);
            }
            writer.write(">");
        }
    }

    void visitEnumConstants(EnumSyntax enumSyntax) {
        var isFirst = true;
        for (var enumConstant : enumSyntax.enumConstants()) {
            if (isFirst) {
                isFirst = false;
            } else {
                writer.writeln(",\n");
            }
            enumConstant.accept(this);
        }
        writer.writeln(";");
        writer.ensureNewline();
    }

    @Override
    public SyntaxNode visitInterfaceSyntax(InterfaceSyntax node) {
        visitTypeSyntax(node);
        return node;
    }

    @Override
    public SyntaxNode visitConstructorMethodSyntax(ConstructorMethodSyntax node) {
        codegenMethodSyntax(node, null, null, null, node.body());
        return node;
    }

    @Override
    public SyntaxNode visitMethodSyntax(MethodSyntax node) {
        codegenMethodSyntax(node, node.typeParams(), node.returns(), node.name(), node.body());
        return node;
    }

    @Override
    public SyntaxNode visitAbstractMethodSyntax(AbstractMethodSyntax node) {
        codegenMethodSyntax(node, node.typeParams(), node.returns(), node.name(), null);
        return node;
    }

    public void codegenMethodSyntax(
        BaseMethodSyntax node,
        List<TypeVariableTypeName> typeParams,
        TypeName returns,
        String name,
        Block body
    ) {
        Javadoc doc = node.javadoc();
        if (doc != null) {
            withJavaDocContext(() -> doc.accept(this));
        }
        List<Annotation> annotations = node.annotations();
        for (int idx = 0; idx < annotations.size(); idx++) {
            Annotation value = annotations.get(idx);
            visitAnnotation(value);
        }
        for (var modifier : node.modifiers()) {
            writer.write(modifier.toString());
            writer.write(" ");
        }
        if (typeParams != null && !typeParams.isEmpty()) {
            writer.write("<");
            for (var typeParam : typeParams) {
                typeParam.accept(this);
            }
            writer.write("> ");
        }
        if (returns != null) {
            returns.accept(this);
            writer.write(" ");
        }
        if (name != null) {
            writer.write(name);
        } else {
            // Constructors
            var className = types.peekFirst().name();
            writer.write(className);
        }
        writer.write("(");
        var isFirstParam = true;
        for (Parameter param : node.parameters()) {
            if (isFirstParam) {
                isFirstParam = false;
            } else {
                writer.write(", ");
            }
            param.accept(this);
        }
        writer.write(")");
        // if abstract omit body
        if (body == null) {
            writer.writeln(";");
        } else {
            writer.beginControlFlow("");
            body.accept(this);
            writer.endControlFlow();
        }
    }

    @Override
    public SyntaxNode visitCaseClause(CaseClause node) {
        for (var label : node.label()) {
            writer.write("case ");
            withExpressionContext(() -> label.accept(this));
            writer.writeln(":");
        }
        writer.indent();
        node.body().accept(this);
        writer.dedent();
        return node;
    }

    @Override
    public SyntaxNode visitSwitchStatement(SwitchStatement node) {
        writer.write("switch (");
        withExpressionContext(() -> node.expression().accept(this));
        writer.writeln(") {");
        writer.indent();
        List<CaseClause> cases = node.cases();
        for (int idx = 0; idx < cases.size(); idx++) {
            CaseClause value = cases.get(idx);
            value.accept(this);
        }
        var defaultCase = node.defaultCase();
        if (defaultCase != null) {
            defaultCase.accept(this);
        }
        writer.dedent();
        writer.writeln("}");
        return node;
    }

    @Override
    public SyntaxNode visitDefaultCaseClause(DefaultCaseClause node) {
        writer.writeln("default:");
        writer.indent();
        Block body = node.body();
        if (body != null) {
            body.accept(this);
        }
        writer.dedent();
        return node;
    }

    @Override
    public SyntaxNode visitEnumConstant(EnumConstant node) {
        var doc = node.javadoc();
        if (doc != null) {
            withJavaDocContext(() -> doc.accept(this));
        }
        writer.write(node.name());

        var body = node.body();
        if (body != null) {
            writer.write("(");
            withExpressionContext(() -> body.accept(this));
            writer.write(")");
        }
        return node;
    }

    @Override
    public TypeName visitClassName(ClassName node) {
        if (isClassImported(node)) {
            writer.write(node.name());
        } else {
            if (node.packageName() != null) {
                writer.write(node.packageName());
                writer.write(".");
            }
            writer.write(node.name());
        }
        return node;
    }

    @Override
    public TypeName visitParameterizedTypeName(ParameterizedTypeName node) {
        node.rawType().accept(this);
        boolean isFirst = true;
        writer.write("<");
        for (var typeArgument : node.typeArguments()) {
            if (!isFirst) {
                writer.write(", ");
            } else {
                isFirst = false;
            }
            typeArgument.accept(this);
        }
        writer.write(">");
        return node;
    }

    @Override
    public TypeName visitPrimitiveTypeName(PrimitiveTypeName node) {
        writer.write(node.name().toString());
        return node;
    }

    @Override
    public TypeName visitTypeVariableTypeName(TypeVariableTypeName node) {
        writer.write(node.name());
        if (!node.bounds().isEmpty()) {
            var upper = node.bounds().get(0);
            if (!isJavaObject(upper)) {
                writer.write(" extends ");
                upper.accept(this);
            }
        }
        return node;
    }

    @Override
    public TypeName visitArrayTypeName(ArrayTypeName node) {
        node.componentType().accept(this);
        writer.write("[]");
        return node;
    }

    @Override
    public TypeName visitWildcardTypeName(WildcardTypeName node) {
        if (node.lowerBounds().size() == 1) {
            writer.write("? super ");
            node.lowerBounds().get(0).accept(this);
            return node;
        }
        var upper = node.upperBounds().get(0);
        if (isJavaObject(upper)) {
            writer.write("?");
        } else {
            writer.write("? extends ");
            upper.accept(this);
        }
        return node;
    }

    private boolean isClassImported(ClassName node) {
        var enclosing = ClassName.toEnclosing(node);
        var imported = simpleNames.get(enclosing.name());
        if (imported != null) {
            if (enclosing.equals(imported)) {
                return true;
            }
            return enclosing.packageName() == null;
        }
        return false;
    }

    private static String toString(ClassName className) {
        if (className.packageName() == null) {
            return "#" + className.name();
        }
        return className.packageName() + "#" + className.name();
    }


    private boolean isJavaObject(TypeName upper) {
        return upper.equals(TypeNameExt.OBJECT);
    }

    private void visitFormatterNode(FormatterNode node) {
        switch (node.kind()) {
            case LITERAL -> visitFormatterNodeLiteral(node);
            case STRING -> visitFormatterNodeString(node);
            case TYPE_NAME -> visitFormatterNodeTypeName(node);
            case BLOCK -> visitFormatterNodeBlock(node);
            default -> throw new IllegalArgumentException("Unknown kind: " + node.kind());
        }
    }

    private void visitFormatterNodeLiteral(FormatterNode formatterNode) {
        var node = (FormatterLiteral) formatterNode;
        writer.write(node.value());
    }

    private void visitFormatterNodeString(FormatterNode formatterNode) {
        var node = (FormatterString) formatterNode;
        writer.writeStringLiteral(node.value());
    }

    private void visitFormatterNodeTypeName(FormatterNode formatterNode) {
        var node = (FormatterTypeName) formatterNode;
        node.value().accept(this);
    }

    private void visitFormatterNodeBlock(FormatterNode formatterNode) {
        var node = (FormatterBlock) formatterNode;
        writer.writeln("{");
        writer.indent();
        node.value().accept(this);
        writer.dedent();
        writer.write("}");
    }

    private void withJavaDocContext(Runnable r) {
        codeBlockContexts.push(CodeBlockContext.JAVADOC);
        r.run();
        codeBlockContexts.pop();
    }

    private void withStatementContext(Runnable r) {
        codeBlockContexts.push(CodeBlockContext.STATEMENT);
        r.run();
        codeBlockContexts.pop();
    }

    private void withExpressionContext(Runnable r) {
        codeBlockContexts.push(CodeBlockContext.EXPRESSION);
        r.run();
        codeBlockContexts.pop();
    }

    enum CodeBlockContext {
        NONE, EXPRESSION, JAVADOC, STATEMENT
    }
}

