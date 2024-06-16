package mx.sugus.braid.jsyntax;

import java.util.Objects;
import java.util.function.Consumer;
import mx.sugus.braid.jsyntax.block.AbstractBlockBuilder;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>Represents a <code>default</code> clause inside a switch statement.</p>
 */
@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class DefaultCaseClause implements SyntaxNode {
    private final Block body;

    private DefaultCaseClause(Builder builder) {
        this.body = Objects.requireNonNull(builder.body.asPersistent(), "body");
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
        return visitor.visitDefaultCaseClause(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DefaultCaseClause that = (DefaultCaseClause) obj;
        return this.body.equals(that.body);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + body.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "DefaultCaseClause{"
            + "body: " + body + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private BuilderReference<Block, BodyBuilder> body;

        Builder() {
            this.body = BodyBuilder.fromPersistent(null);
        }

        Builder(DefaultCaseClause data) {
            this.body = BodyBuilder.fromPersistent(data.body);
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

        public DefaultCaseClause build() {
            return new DefaultCaseClause(this);
        }
    }
}
