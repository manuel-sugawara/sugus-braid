package mx.sugus.braid.jsyntax;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import mx.sugus.braid.jsyntax.block.AbstractBlockBuilder;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a <code>case</code> clause inside a switch statement.</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class CaseClause implements SyntaxNode {
    private final List<Expression> label;
    private final Block body;

    private CaseClause(Builder builder) {
        this.label = Objects.requireNonNull(builder.label.asPersistent(), "label");
        this.body = Objects.requireNonNull(builder.body.asPersistent(), "body");
    }

    public List<Expression> label() {
        return this.label;
    }

    public Block body() {
        return this.body;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitCaseClause(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CaseClause that = (CaseClause) obj;
        return this.label.equals(that.label)
            && this.body.equals(that.body);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + label.hashCode();
        hashCode = 31 * hashCode + body.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "CaseClause{"
            + "label: " + label
            + ", body: " + body + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements SyntaxNode.Builder {
        private CollectionBuilderReference<List<Expression>> label;
        private BuilderReference<Block, BodyBuilder> body;

        Builder() {
            this.label = CollectionBuilderReference.forList();
            this.body = BodyBuilder.fromPersistent(null);
        }

        Builder(CaseClause data) {
            this.label = CollectionBuilderReference.fromPersistentList(data.label);
            this.body = BodyBuilder.fromPersistent(data.body);
        }

        /**
         * <p>Sets the value for <code>label</code></p>
         */
        public Builder label(List<Expression> label) {
            this.label.clear();
            this.label.asTransient().addAll(label);
            return this;
        }

        /**
         * <p>Adds a single value for <code>label</code></p>
         */
        public Builder addLabel(Expression label) {
            this.label.asTransient().add(label);
            return this;
        }

        public Builder body(Consumer<BodyBuilder> mutator) {
            mutator.accept(this.body.asTransient());
            return this;
        }

        /**
         * <p>Sets the value for <code>body</code></p>
         */
        public Builder body(Block body) {
            this.body.setPersistent(body);
            return this;
        }

        public Builder addStatement(String format, Object... args) {
            this.body.asTransient().addStatement(format, args);
            return this;
        }

        public Builder addStatement(Statement stmt) {
            this.body.asTransient().addStatement(stmt);
            return this;
        }

        public Builder ifStatement(String format, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then) {
            this.body.asTransient().ifStatement(format, then);
            return this;
        }

        public Builder ifStatement(String format, Object arg, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then) {
            this.body.asTransient().ifStatement(format, arg, then);
            return this;
        }

        public Builder ifStatement(String format, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> otherwise) {
            this.body.asTransient().ifStatement(format, then, otherwise);
            return this;
        }

        public Builder ifStatement(String format, Object arg, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> otherwise) {
            this.body.asTransient().ifStatement(format, arg, then, otherwise);
            return this;
        }

        public Builder beginControlFlow(String format, Object... args) {
            this.body.asTransient().beginControlFlow(format, args);
            return this;
        }

        public Builder nextControlFlow(String format, Object... args) {
            this.body.asTransient().nextControlFlow(format, args);
            return this;
        }

        public Builder endControlFlow() {
            this.body.asTransient().endControlFlow();
            return this;
        }

        public CaseClause build() {
            return new CaseClause(this);
        }
    }
}
