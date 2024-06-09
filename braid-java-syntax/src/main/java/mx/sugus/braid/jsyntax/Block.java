package mx.sugus.braid.jsyntax;

import java.util.List;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>A block is a collection of statements.</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class Block implements Statement {
    private final List<Statement> statements;

    private Block(Builder builder) {
        this.statements = builder.statements.asPersistent();
    }

    public StatementKind stmtKind() {
        return StatementKind.BLOCK;
    }

    public List<Statement> statements() {
        return this.statements;
    }

    /**
     * Returns a new builder to modify a copy of this instance
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private CollectionBuilderReference<List<Statement>> statements;

        Builder() {
            this.statements = CollectionBuilderReference.forList();
        }

        Builder(Block data) {
            this.statements = CollectionBuilderReference.fromPersistentList(data.statements);
        }

        /**
         * <p>Sets the value for <code>statements</code></p>
         */
        public Builder statements(List<Statement> statements) {
            this.statements.clear();
            this.statements.asTransient().addAll(statements);
            return this;
        }

        /**
         * <p>Adds a single value for <code>statements</code></p>
         */
        public Builder addStatement(Statement statement) {
            this.statements.asTransient().add(statement);
            return this;
        }

        public Block build() {
            return new Block(this);
        }
    }
}
