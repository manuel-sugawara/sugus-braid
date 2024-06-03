package mx.sugus.braid.jsyntax;

import java.util.Objects;
import java.util.function.Consumer;
import mx.sugus.braid.jsyntax.block.AbstractBlockBuilder;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents an <code>for</code> statement.</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class ForStatement implements Statement {
    private final CodeBlock initializer;
    private final Block statement;

    private ForStatement(Builder builder) {
        this.initializer = Objects.requireNonNull(builder.initializer, "initializer");
        this.statement = Objects.requireNonNull(builder.statement.asPersistent(), "statement");
    }

    public StatementKind stmtKind() {
        return StatementKind.FOR_STATEMENT;
    }

    /**
     * <p>Represents the initialization block of the <code>for</code> statement.</p>
     * <p>Accommodates traditional <code>for</code> and enhanced <code>for</code> statements.</p>
     */
    public CodeBlock initializer() {
        return this.initializer;
    }

    /**
     * <p>The body of the <code>for</code> statement.</p>
     */
    public Block statement() {
        return this.statement;
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
        ForStatement that = (ForStatement) obj;
        return this.initializer.equals(that.initializer)
            && this.statement.equals(that.statement);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.stmtKind().hashCode();
        hashCode = 31 * hashCode + initializer.hashCode();
        hashCode = 31 * hashCode + statement.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "ForStatement{"
            + "stmtKind: " + stmtKind()
            + ", initializer: " + initializer
            + ", statement: " + statement + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitForStatement(this);
    }

    public static final class Builder {
        private CodeBlock initializer;
        private BuilderReference<Block, BodyBuilder> statement;

        Builder() {
            this.statement = BodyBuilder.fromPersistent(null);
        }

        Builder(ForStatement data) {
            this.initializer = data.initializer;
            this.statement = BodyBuilder.fromPersistent(data.statement);
        }

        /**
         * <p>Sets the value for <code>initializer</code></p>
         * <p>Represents the initialization block of the <code>for</code> statement.</p>
         * <p>Accommodates traditional <code>for</code> and enhanced <code>for</code> statements.</p>
         */
        public Builder initializer(CodeBlock initializer) {
            this.initializer = initializer;
            return this;
        }

        /**
         * <p>Sets the value for <code>statement</code></p>
         * <p>The body of the <code>for</code> statement.</p>
         */
        public Builder statement(Block statement) {
            this.statement.setPersistent(statement);
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

        public Builder statement(Consumer<BodyBuilder> mutator) {
            mutator.accept(this.statement.asTransient());
            return this;
        }

        public ForStatement build() {
            return new ForStatement(this);
        }
    }
}
