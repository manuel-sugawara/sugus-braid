package mx.sugus.braid.jsyntax;

import java.util.Objects;
import java.util.function.Consumer;
import mx.sugus.braid.jsyntax.block.AbstractBlockBuilder;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * Represents any unstructured control flow statement block.
 * <p>
 * The control flows are rendered as
 * <pre><code>  prefix {
 *       statement_0
 *          ⋮
 *       statement_N
 *   } &lt;optional-next&gt;
 * </code></pre>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class AbstractControlFlow implements Statement {
    private final CodeBlock prefix;
    private final Block statement;
    private final AbstractControlFlow next;
    private int _hashCode = 0;

    private AbstractControlFlow(Builder builder) {
        this.prefix = Objects.requireNonNull(builder.prefix, "prefix");
        this.statement = Objects.requireNonNull(builder.statement.asPersistent(), "statement");
        this.next = builder.next;
    }

    public StatementKind stmtKind() {
        return StatementKind.ABSTRACT_CONTROL_FLOW;
    }

    /**
     * The prefix for this control flow.
     * 
     * @return The value of the {@code prefix} member
     */
    public CodeBlock prefix() {
        return this.prefix;
    }

    /**
     * The body of the abstract control flow
     * 
     * @return The value of the {@code statement} member
     */
    public Block statement() {
        return this.statement;
    }

    /**
     * An optional {@code next} block.
     * 
     * @return The value of the {@code next} member
     */
    public AbstractControlFlow next() {
        return this.next;
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
        return visitor.visitAbstractControlFlow(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        AbstractControlFlow that = (AbstractControlFlow) other;
        return this.prefix.equals(that.prefix)
            && this.statement.equals(that.statement)
            && Objects.equals(this.next, that.next);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + this.stmtKind().hashCode();
            hashCode = 31 * hashCode + prefix.hashCode();
            hashCode = 31 * hashCode + statement.hashCode();
            hashCode = 31 * hashCode + (next != null ? next.hashCode() : 0);
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "AbstractControlFlow{"
            + "stmtKind: " + stmtKind()
            + ", prefix: " + prefix
            + ", statement: " + statement
            + ", next: " + next + "}";
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
     * A class to build instances of AbstractControlFlow
     */
    public static final class Builder implements Statement.Builder {
        private CodeBlock prefix;
        private BuilderReference<Block, BodyBuilder> statement;
        private AbstractControlFlow next;

        Builder() {
            this.statement = BodyBuilder.fromPersistent(null);
        }

        Builder(AbstractControlFlow data) {
            this.prefix = data.prefix;
            this.statement = BodyBuilder.fromPersistent(data.statement);
            this.next = data.next;
        }

        /**
         * Sets the value for {@code prefix}.
         * 
         * @param prefix The value to be set.
         * @return This instance for chain calling.
         */
        public Builder prefix(CodeBlock prefix) {
            this.prefix = Objects.requireNonNull(prefix, "prefix");
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
         * Sets the value for {@code next}.
         * 
         * @param next The value to be set.
         * @return This instance for chain calling.
         */
        public Builder next(AbstractControlFlow next) {
            this.next = next;
            return this;
        }

        /**
         * Returns a new instance of {@link AbstractControlFlow}
         * 
         * @return A new instance of {@link AbstractControlFlow}
         */
        public AbstractControlFlow build() {
            return new AbstractControlFlow(this);
        }
    }
}
