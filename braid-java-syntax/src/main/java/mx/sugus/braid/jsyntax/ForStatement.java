package mx.sugus.braid.jsyntax;

import java.util.Objects;
import java.util.function.Consumer;
import mx.sugus.braid.jsyntax.block.AbstractBlockBuilder;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents an {@code for} statement.
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
     * Represents the initialization block of the {@code for} statement.
     * <p>
     * Accommodates traditional {@code for} and enhanced {@code for} statements.
     * 
     * @return The value of the {@code initializer} member
     */
    public CodeBlock initializer() {
        return this.initializer;
    }

    /**
     * The body of the {@code for} statement.
     * 
     * @return The value of the {@code statement} member
     */
    public Block statement() {
        return this.statement;
    }

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Accepts a {@link SyntaxNodeVisitor<VisitorR>} visitor
     * 
     * @param visitor The visitor to accept
     * @param <VisitorR> The result type from the visitor
     * @return The result from the visitor
     */
    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitForStatement(this);
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
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A class to build instances of ForStatement
     */
    public static final class Builder implements Statement.Builder {
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
         * Sets the value for {@code initializer}.
         * 
         * @param initializer The value to be set.
         * @return This instance for chain calling.
         */
        public Builder initializer(CodeBlock initializer) {
            this.initializer = Objects.requireNonNull(initializer, "initializer");
            return this;
        }

        public Builder statement(Consumer<BodyBuilder> mutator) {
            mutator.accept(this.statement.asTransient());
            return this;
        }

        /**
         * Sets the value for {@code statement}.
         * 
         * @param statement The value to be set.
         * @return This instance for chain calling.
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

        /**
         * Returns a new instance of {@link ForStatement}
         * 
         * @return A new instance of {@link ForStatement}
         */
        public ForStatement build() {
            return new ForStatement(this);
        }
    }
}
