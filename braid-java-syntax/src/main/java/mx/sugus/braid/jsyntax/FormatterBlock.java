package mx.sugus.braid.jsyntax;

import java.util.Objects;
import java.util.function.Consumer;
import mx.sugus.braid.jsyntax.block.AbstractBlockBuilder;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class FormatterBlock implements FormatterNode {
    private final Block value;

    private FormatterBlock(Builder builder) {
        this.value = builder.value.asPersistent();
    }

    public SyntaxFormatterNodeKind kind() {
        return SyntaxFormatterNodeKind.BLOCK;
    }

    public Block value() {
        return this.value;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
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
        FormatterBlock that = (FormatterBlock) obj;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.kind().hashCode();
        hashCode = 31 * hashCode + (value != null ? value.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "FormatterBlock{"
            + "kind: " + kind()
            + ", value: " + value + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private BuilderReference<Block, BodyBuilder> value;

        Builder() {
            this.value = BodyBuilder.fromPersistent(null);
        }

        Builder(FormatterBlock data) {
            this.value = BodyBuilder.fromPersistent(data.value);
        }

        public Builder value(Consumer<BodyBuilder> mutator) {
            mutator.accept(this.value.asTransient());
            return this;
        }

        /**
         * <p>Sets the value for <code>value</code></p>
         */
        public Builder value(Block value) {
            this.value.setPersistent(value);
            return this;
        }

        public Builder addStatement(String format, Object... args) {
            this.value.asTransient().addStatement(format, args);
            return this;
        }

        public Builder addStatement(Statement stmt) {
            this.value.asTransient().addStatement(stmt);
            return this;
        }

        public Builder ifStatement(String format, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then) {
            this.value.asTransient().ifStatement(format, then);
            return this;
        }

        public Builder ifStatement(String format, Object arg, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then) {
            this.value.asTransient().ifStatement(format, arg, then);
            return this;
        }

        public Builder ifStatement(String format, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> otherwise) {
            this.value.asTransient().ifStatement(format, then, otherwise);
            return this;
        }

        public Builder ifStatement(String format, Object arg, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> then, Consumer<AbstractBlockBuilder<BodyBuilder, Block>> otherwise) {
            this.value.asTransient().ifStatement(format, arg, then, otherwise);
            return this;
        }

        public Builder beginControlFlow(String format, Object... args) {
            this.value.asTransient().beginControlFlow(format, args);
            return this;
        }

        public Builder nextControlFlow(String format, Object... args) {
            this.value.asTransient().nextControlFlow(format, args);
            return this;
        }

        public Builder endControlFlow() {
            this.value.asTransient().endControlFlow();
            return this;
        }

        public FormatterBlock build() {
            return new FormatterBlock(this);
        }
    }
}
