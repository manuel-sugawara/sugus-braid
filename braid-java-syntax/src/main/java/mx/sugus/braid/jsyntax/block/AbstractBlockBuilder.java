package mx.sugus.braid.jsyntax.block;

import java.io.Serial;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Consumer;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.Expression;
import mx.sugus.braid.jsyntax.Statement;
import mx.sugus.braid.jsyntax.SyntaxNode;

/**
 * Helper class that allows to build other "block" alike statements that can be composed and nested. Provides similar
 * functionality to the way methods are built using JavaPoet but enhances that by using methods for if and for statements that
 * take lambdas which removes the need to remember to close a previously opened statement.
 *
 * @param <B> The builder class for the statement
 * @param <T> The final statement class
 */
@SuppressWarnings("unchecked")
public abstract class AbstractBlockBuilder<B extends AbstractBlockBuilder<B, T>, T extends Statement> {
    private final Deque<AbstractBlockBuilder<?, ?>> stack = new ArrayDeque<>();
    protected List<Statement> statements = new ArrayList<>();

    AbstractBlockBuilder() {
        stack.push(this);
    }

    /**
     * Builds the resulting statement.
     *
     * @return the resulting statement.
     */
    public abstract T build();

    /**
     * Adds a new literal string statement to this block builder.
     *
     * @param statement The statement to be added.
     * @return This instance for call chaining
     */
    public B addStatement(String statement) {
        return addStatement(CodeBlock.from(statement));
    }

    /**
     * Adds a new statement to this block builder formatted using {@link CodeBlock#from(String, Object...)}.
     *
     * @param format The format
     * @param args   The arguments to the format
     * @return This instance for call chaining
     */
    public B addStatement(String format, Object... args) {
        return addStatement(CodeBlock.from(format, args));
    }

    /**
     * Adds a new statement to this block builder.
     *
     * @param node The statement to be added.
     * @return This instance for call chaining
     */
    public B addStatement(Statement node) {
        assert stack.peekFirst() != null;
        assert node != null;
        stack.peekFirst().statements.add(node);
        return (B) this;
    }

    // --- Abstract control flow ---

    /**
     * Begins an abstract control flow, using the format an arguments as a prefix for it. The format is converted to a
     * {@link SyntaxNode} using {@link CodeBlock#from(String, Object...)}.
     *
     * @param format The format for the prefix
     * @param args   The arguments for the format
     * @return This instance for call chaining
     */
    public B beginControlFlow(String format, Object... args) {
        stack.push(new AbstractControlFlowBuilder(CodeBlock.from(format, args)));
        return (B) this;
    }

    /**
     * Begins a next abstract control flow, using the format an arguments as a prefix for it. The format is converted to a
     * {@link SyntaxNode} using {@link CodeBlock#from(String, Object...)}. The previous block is closed with a closing curly
     * bracket followed by the prefix followed by a opening curly bracket.
     *
     * @param format The format for the prefix
     * @param args   The arguments for the format
     * @return This instance for call chaining
     */
    public B nextControlFlow(String format, Object... args) {
        var stmt = peekExpecting(AbstractControlFlowBuilder.class, AbstractControlFlowBuilder.NextControlFlowBuilder.class);
        if (stmt instanceof AbstractControlFlowBuilder controlFlow) {
            stack.push(controlFlow.addNext(CodeBlock.from(format, args)));
        } else {
            stack.pop();
            var controlFlow = peekExpecting(AbstractControlFlowBuilder.class);
            stack.push(controlFlow.addNext(CodeBlock.from(format, args)));
        }
        return (B) this;
    }

    /**
     * Ends a previous abstract control flow opened either with {@link #beginControlFlow(String, Object...)} or
     * {@link #nextControlFlow(String, Object...)}.
     *
     * @return This instance for call chaining
     */
    public B endControlFlow() {
        var last = popExpecting(AbstractControlFlowBuilder.class,
                                AbstractControlFlowBuilder.NextControlFlowBuilder.class);
        if (!(last instanceof AbstractControlFlowBuilder)) {
            last = popExpecting(AbstractControlFlowBuilder.class);
        }
        return addStatement(last.build());
    }

    // --- If Statements ---

    /**
     * Begins a new if statement using the given expression as its condition.
     *
     * @param condition The condition for the if statement.
     * @return This instance for call chaining
     */
    public B beginIfStatement(Expression condition) {
        stack.push(new IfStatementBuilder(condition));
        return (B) this;
    }

    public B beginIfStatement(String condition) {
        return beginIfStatement(CodeBlock.from(condition));
    }

    /**
     * Begins a new if statement using the given format and arguments to create its condition using
     * {@link CodeBlock#from(String, Object...)}
     *
     * @param format The format for the condition expression.
     * @param args   The arguments for the format
     * @return This instance for call chaining
     */
    public B beginIfStatement(String format, Object... args) {
        return beginIfStatement(CodeBlock.from(format, args));
    }

    /**
     * Begins an else-if statement using the given expression as its condition. Calling this method <b>must</b> be preceded by a
     * call to {@link #beginIfStatement}.
     *
     * @param condition The condition for the else-if statement.
     * @return This instance for call chaining
     */
    public B nextElseIfStatement(Expression condition) {
        var stmt = peekExpecting(IfStatementBuilder.class, IfStatementBuilder.ElseIfBuilder.class);
        if (stmt instanceof IfStatementBuilder ifStatement) {
            stack.push(ifStatement.addElseIf(condition));
        } else {
            stack.pop();
            var ifStatement = peekExpecting(IfStatementBuilder.class);
            stack.push(ifStatement.addElseIf(condition));
        }
        return (B) this;
    }

    /**
     * Begins an else-if statement using the given format and arguments to create its condition using
     * {@link CodeBlock#from(String, Object...)}. Calling this method <b>must</b> be preceded by a call to
     * {@link #beginIfStatement}.
     *
     * @param condition The condition for the else-if statement.
     * @return This instance for call chaining
     */
    public B nextElseIfStatement(String condition, Object... args) {
        return nextElseIfStatement(CodeBlock.from(condition, args));
    }

    /**
     * Begins an else statement. Calling this method <b>must</b> be preceded by a call to either {@link #beginIfStatement} or
     * {@link #nextElseIfStatement}.
     *
     * @return This instance for call chaining
     */
    public B nextElseStatement() {
        var stmt = peekExpecting(IfStatementBuilder.class, IfStatementBuilder.ElseIfBuilder.class);
        if (stmt instanceof IfStatementBuilder.ElseIfBuilder) {
            stack.pop();
        }
        var ifStatement = peekExpecting(IfStatementBuilder.class);
        stack.push(ifStatement.addElse());
        return (B) this;
    }

    /**
     * Ends an if statement. Calling this method <b>must</b> be preceded by a call to either {@link #beginIfStatement} or
     * {@link #nextElseIfStatement}  or {@link #nextElseStatement}.
     *
     * @return This instance for call chaining
     */
    public B endIfStatement() {
        var last = popExpecting(IfStatementBuilder.class,
                                IfStatementBuilder.ElseIfBuilder.class,
                                IfStatementBuilder.ElseBuilder.class);
        if (!(last instanceof IfStatementBuilder)) {
            last = popExpecting(IfStatementBuilder.class);
        }
        return addStatement(last.build());
    }

    /**
     * Creates and adds to the block an if statement using the given condition and the consumer to create its body.
     *
     * @param condition The condition for the else-if statement.
     * @param ifBody    The {@code AbstractBlockBuilder} consumer to create the body of the if statement.
     * @return This instance for call chaining
     */
    public B ifStatement(Expression condition,
                         Consumer<AbstractBlockBuilder<B, T>> ifBody) {
        beginIfStatement(condition);
        ifBody.accept(this);
        return endIfStatement();
    }

    /**
     * Creates and adds to the block an if statement using the given condition and the consumer to create its body.
     *
     * @param condition The condition for the else-if statement.
     * @param ifBody    The {@code AbstractBlockBuilder} consumer to create the body of the if statement.
     * @return This instance for call chaining
     */
    public B ifStatement(String condition,
                         Consumer<AbstractBlockBuilder<B, T>> ifBody) {
        beginIfStatement(condition);
        ifBody.accept(this);
        return endIfStatement();
    }

    /**
     * Creates and adds to the block an if statement using the given format and argument to format the condition and the consumer
     * to create its body.
     *
     * @param format The format for the condition
     * @param arg    The single argument for the format
     * @param ifBody The {@code AbstractBlockBuilder} consumer to create the body of the if statement.
     * @return This instance for call chaining
     */
    public B ifStatement(String format,
                         Object arg,
                         Consumer<AbstractBlockBuilder<B, T>> ifBody) {
        beginIfStatement(format, arg);
        ifBody.accept(this);
        return endIfStatement();
    }

    /**
     * Creates and adds to the block an if-else statement using the given the literal string condition and the consumer to create
     * the if body and the next consumer to create the else body.
     *
     * @param condition The literal string representing the condition.
     * @param then      The lambda used to create the "then" branch of the if statement
     * @param otherwise The lambda used to create the "else" branch of the if statement
     * @return This instance for call chaining
     */
    public B ifStatement(String condition,
                         Consumer<AbstractBlockBuilder<B, T>> then,
                         Consumer<AbstractBlockBuilder<B, T>> otherwise) {
        beginIfStatement(condition);
        then.accept(this);
        nextElseStatement();
        otherwise.accept(this);
        return endIfStatement();
    }

    /**
     * Creates and adds to the block an if-else statement using the given the literal string condition and the consumer to create
     * the if body and the next consumer to create the else body.
     *
     * @param format    The format to create the condition
     * @param arg       The single argument for the format
     * @param then      The lambda used to create the "then" branch of the if statement
     * @param otherwise The lambda used to create the "else" branch of the if statement
     * @return This instance for call chaining
     */
    public B ifStatement(String format, Object arg,
                         Consumer<AbstractBlockBuilder<B, T>> then,
                         Consumer<AbstractBlockBuilder<B, T>> otherwise) {
        beginIfStatement(format, arg);
        then.accept(this);
        nextElseStatement();
        otherwise.accept(this);
        return endIfStatement();
    }

    // --- For statements ---

    /**
     * Begins a new for statement using the node as its initializer. Can be used for both, traditional and enhanced foreach
     * statements.
     *
     * @param initializer The initializer for the for statement.
     * @return This instance for call chaining
     */
    public B beginForStatement(CodeBlock initializer) {
        stack.push(new ForStatementBuilder(initializer));
        return (B) this;
    }

    /**
     * Begins a new for statement using the node as its initializer. Can be used for both, traditional and enhanced foreach
     * statements.
     *
     * @param initializer The initializer for the for statement.
     * @return This instance for call chaining
     */
    public B beginForStatement(String initializer) {
        return beginForStatement(CodeBlock.from(initializer));
    }

    /**
     * Begins a new for statement using the given format and arguments to create its initializer using
     * {@link CodeBlock#from(String, Object...)}. Can be used for both, traditional and enhanced foreach statements.
     *
     * @param initializer The initializer for the for statement.
     * @return This instance for call chaining
     */
    public B beginForStatement(String initializer, Object... args) {
        return beginForStatement(CodeBlock.from(initializer, args));
    }

    /**
     * Ends a for statement. Calls to this method <b>must</b> be preceded by a call to {@link #beginForStatement}.
     *
     * @return This instance for call chaining
     */
    public B endForStatement() {
        var last = popExpecting(ForStatementBuilder.class);
        return addStatement(last.build());
    }

    /**
     * Creates a for statement using the given lambda to create its body.
     *
     * @param initializer The initializer for the for statement
     * @param forBody     The lambda used to create the body of the for statement.
     * @return This instance for call chaining
     */
    public B forStatement(CodeBlock initializer, Consumer<AbstractBlockBuilder<B, T>> forBody) {
        beginForStatement(initializer);
        forBody.accept(this);
        return endForStatement();
    }

    /**
     * Creates a for statement using the given lambda to create its body.
     *
     * @param initializer The initializer for the for statement
     * @param forBody     The lambda used to create the body of the for statement.
     * @return This instance for call chaining
     */
    public B forStatement(String initializer, Consumer<AbstractBlockBuilder<B, T>> forBody) {
        beginForStatement(initializer);
        forBody.accept(this);
        return endForStatement();
    }

    /**
     * Creates a for statement using the given format and its single argument to create its initializer using *
     * {@link CodeBlock#from(String, Object...)}. Can be used for both, traditional and enhanced foreach statements.
     *
     * @param format  The format for the initializer
     * @param arg0    The single argument for the format
     * @param forBody The lambda used to create the body of the for statement.
     * @return This instance for call chaining
     */
    public B forStatement(String format, Object arg0, Consumer<AbstractBlockBuilder<B, T>> forBody) {
        beginForStatement(format, arg0);
        forBody.accept(this);
        return endForStatement();
    }

    /**
     * Creates a for statement using the given format and two arguments to create its initializer using *
     * {@link CodeBlock#from(String, Object...)}. Can be used for both, traditional and enhanced foreach statements.
     *
     * @param format  The format for the initializer
     * @param arg0    The first argument for the c
     * @param arg1    The second argument for the format
     * @param forBody The lambda used to create the body of the for statement.
     * @return This instance for call chaining
     */
    public B forStatement(String format, Object arg0, Object arg1, Consumer<AbstractBlockBuilder<B, T>> forBody) {
        beginForStatement(format, arg0, arg1);
        forBody.accept(this);
        return endForStatement();
    }

    /**
     * Creates a block containing the statements in the builder.
     *
     * @return a block containing the statements in the builder
     */
    protected Block toBlock() {
        var last = stack.peekFirst();
        if (last != this) {
            var errors = new ArrayList<String>();
            do {
                errors.add(reportMissingClosing(stack.pop()));
            } while (stack.peekFirst() != this);
            throw invalidState("Unterminated state: " + String.join(",", errors));
        }
        return Block.builder()
                    .statements(statements)
                    .build();
    }

    // -- Utils
    // TODO, Improve the wording, as "missing beginIfStatement()", instead of expecting class ...
    <E> E popExpecting(Class<E> clazz) {
        var last = stack.pop();
        if (!clazz.isInstance(last)) {
            throw invalidState("Expected to have a class on top instanceof " + clazz.getName() + ", but got "
                               + "instead: " +
                               last.getClass().getName());
        }
        return (E) last;
    }

    AbstractBlockBuilder<?, ?> popExpecting(
        Class<? extends AbstractBlockBuilder<?, ?>> clazz0,
        Class<? extends AbstractBlockBuilder<?, ?>> clazz1
    ) {
        var last = stack.pop();
        if (!(clazz0.isInstance(last) || clazz1.isInstance(last))) {
            throw invalidState("Expected to have a class on top instanceof " + clazz0.getName() +
                               " or " + clazz1.getName());
        }
        return last;
    }

    AbstractBlockBuilder<?, ?> popExpecting(
        Class<? extends AbstractBlockBuilder<?, ?>> clazz0,
        Class<? extends AbstractBlockBuilder<?, ?>> clazz1,
        Class<? extends AbstractBlockBuilder<?, ?>> clazz2
    ) {
        var last = stack.pop();
        if (!(clazz0.isInstance(last) || clazz1.isInstance(last) || clazz2.isInstance(last))) {
            throw invalidState("Expected to have a class on top instanceof " + clazz0.getName() +
                               ", but got instead: " + last.getClass().getName());
        }
        return last;
    }

    <E> E peekExpecting(Class<E> clazz) {
        var last = stack.peekFirst();
        if (!clazz.isInstance(last)) {
            throw invalidState("Expected to have a class on top instanceof " + clazz.getName());
        }
        return (E) last;
    }

    AbstractBlockBuilder<?, ?> peekExpecting(
        Class<? extends AbstractBlockBuilder<?, ?>> clazz0,
        Class<? extends AbstractBlockBuilder<?, ?>> clazz1
    ) {
        var last = stack.peekFirst();
        if (!(clazz0.isInstance(last) || clazz1.isInstance(last))) {
            throw invalidState("Expected to have a class on top instanceof " + clazz0.getName() +
                               " or " + clazz1.getName() + ", but got instead: " +
                               last.getClass().getName());
        }
        return last;
    }

    static String reportMissingClosing(AbstractBlockBuilder<?, ?> last) {
        if (last instanceof IfStatementBuilder ||
            last instanceof IfStatementBuilder.ElseIfBuilder ||
            last instanceof IfStatementBuilder.ElseBuilder
        ) {
            return "missing endIfStatement()";
        }
        if (last instanceof ForStatementBuilder) {
            return "missing endForStatement()";
        }
        if (last instanceof AbstractControlFlowBuilder ||
            last instanceof AbstractControlFlowBuilder.NextControlFlowBuilder
        ) {
            return "missing endControlFlow()";
        }
        return "missing closing element of: " + last.getClass().getName();
    }

    static AbstractBlockInvalidStateException invalidState(String fmt, Object... args) {
        return new AbstractBlockInvalidStateException(String.format(fmt, args));
    }

    static class AbstractBlockInvalidStateException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 1L;

        public AbstractBlockInvalidStateException(String msg) {
            super(msg);
        }
    }
}
