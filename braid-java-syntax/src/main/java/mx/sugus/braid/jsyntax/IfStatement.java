package mx.sugus.braid.jsyntax;

import java.util.Objects;
import java.util.function.Consumer;
import mx.sugus.braid.jsyntax.block.AbstractBlockBuilder;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents an <code>if</code> statement.</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class IfStatement implements Statement {
    private final Expression expression;
    private final Block statement;
    private final Statement elseStatement;
    private int _hashCode = 0;

    private IfStatement(Builder builder) {
        this.expression = Objects.requireNonNull(builder.expression, "expression");
        this.statement = Objects.requireNonNull(builder.statement.asPersistent(), "statement");
        this.elseStatement = builder.elseStatement;
    }

    public StatementKind stmtKind() {
        return StatementKind.IF_STATEMENT;
    }

    /**
     * <p>The condition of the <code>if</code> statement</p>
     */
    public Expression expression() {
        return this.expression;
    }

    /**
     * <p>The body of the <code>if</code> statement</p>
     */
    public Block statement() {
        return this.statement;
    }

    /**
     * <p>An optional <code>else</code> block.</p>
     */
    public Statement elseStatement() {
        return this.elseStatement;
    }

    /**
     * Returns a new builder to modify a copy of this instance
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IfStatement that = (IfStatement) obj;
        return this.expression.equals(that.expression)
            && this.statement.equals(that.statement)
            && Objects.equals(this.elseStatement, that.elseStatement);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + this.stmtKind().hashCode();
            hashCode = 31 * hashCode + expression.hashCode();
            hashCode = 31 * hashCode + statement.hashCode();
            hashCode = 31 * hashCode + (elseStatement != null ? elseStatement.hashCode() : 0);
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "IfStatement{"
            + "stmtKind: " + stmtKind()
            + ", expression: " + expression
            + ", statement: " + statement
            + ", elseStatement: " + elseStatement + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitIfStatement(this);
    }

    public static final class Builder {
        private Expression expression;
        private BuilderReference<Block, BodyBuilder> statement;
        private Statement elseStatement;

        Builder() {
            this.statement = BodyBuilder.fromPersistent(null);
        }

        Builder(IfStatement data) {
            this.expression = data.expression;
            this.statement = BodyBuilder.fromPersistent(data.statement);
            this.elseStatement = data.elseStatement;
        }

        /**
         * <p>Sets the value for <code>expression</code></p>
         * <p>The condition of the <code>if</code> statement</p>
         */
        public Builder expression(Expression expression) {
            this.expression = expression;
            return this;
        }

        public Builder statement(Consumer<BodyBuilder> mutator) {
            mutator.accept(this.statement.asTransient());
            return this;
        }

        public Builder addStatement(String format, Object... args) {
            this.statement.asTransient().addStatement(format, args);
            return this;
        }

        public Builder addStatement(Statement stmt) {
            this.statement.asTransient().addStatement(stmt);
            return this;
        }

        public Builder ifStatement(String format, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then) {
            this.statement.asTransient().ifStatement(format, then);
            return this;
        }

        public Builder ifStatement(String format, Object arg, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then) {
            this.statement.asTransient().ifStatement(format, arg, then);
            return this;
        }

        public Builder ifStatement(String format, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> otherwise) {
            this.statement.asTransient().ifStatement(format, then, otherwise);
            return this;
        }

        public Builder ifStatement(String format, Object arg, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> otherwise) {
            this.statement.asTransient().ifStatement(format, arg, then, otherwise);
            return this;
        }

        public Builder beginControlFlow(String format, Object... args) {
            this.statement.asTransient().beginControlFlow(format, args);
            return this;
        }

        public Builder nextControlFlow(String format, Object... args) {
            this.statement.asTransient().nextControlFlow(format, args);
            return this;
        }

        public Builder endControlFlow() {
            this.statement.asTransient().endControlFlow();
            return this;
        }

        /**
         * <p>Sets the value for <code>statement</code></p>
         * <p>The body of the <code>if</code> statement</p>
         */
        public Builder statement(Block statement) {
            this.statement.setPersistent(statement);
            return this;
        }

        /**
         * <p>Sets the value for <code>elseStatement</code></p>
         * <p>An optional <code>else</code> block.</p>
         */
        public Builder elseStatement(Statement elseStatement) {
            this.elseStatement = elseStatement;
            return this;
        }

        public IfStatement build() {
            return new IfStatement(this);
        }
    }
}
