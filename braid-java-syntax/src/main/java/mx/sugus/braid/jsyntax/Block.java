package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * A block is a collection of statements.
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class Block implements Statement {
    private final List<Statement> statements;

    private Block(Builder builder) {
        this.statements = Objects.requireNonNull(builder.statements.asPersistent(), "statements");
    }

    public StatementKind stmtKind() {
        return StatementKind.BLOCK;
    }

    /**
     * 
     * @return The value of the {@code statements} member
     */
    public List<Statement> statements() {
        return this.statements;
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
        return visitor.visitBlock(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Block that = (Block) obj;
        return this.statements.equals(that.statements);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.stmtKind().hashCode();
        hashCode = 31 * hashCode + statements.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "Block{"
            + "stmtKind: " + stmtKind()
            + ", statements: " + statements + "}";
    }

    /**
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements Statement.Builder {
        private CollectionBuilderReference<List<Statement>> statements;

        Builder() {
            this.statements = CollectionBuilderReference.forList();
        }

        Builder(Block data) {
            this.statements = CollectionBuilderReference.fromPersistentList(data.statements);
        }

        /**
         * Sets the value for {@code statements}.
         * 
         * @param statements The value to be set.
         * @return This instance for chain calling.
         */
        public Builder statements(List<Statement> statements) {
            this.statements.clear();
            this.statements.asTransient().addAll(statements);
            return this;
        }

        /**
         * Adds a single value for {@code statements}.
         */
        public Builder addStatement(Statement statement) {
            this.statements.asTransient().add(statement);
            return this;
        }

        /**
         * Returns a new instance of {@link Block}
         * 
         * @return A new instance of {@link Block}
         */
        public Block build() {
            return new Block(this);
        }
    }
}
