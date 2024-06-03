package mx.sugus.braid.jsyntax;

import java.util.List;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class CodeBlock implements SyntaxNode, Expression, EnumBody, Statement, Javadoc {
    private final List<FormatterNode> parts;

    private CodeBlock(Builder builder) {
        this.parts = builder.parts.asPersistent();
    }

    public StatementKind stmtKind() {
        return StatementKind.FORMAT;
    }

    public List<FormatterNode> parts() {
        return this.parts;
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
        CodeBlock that = (CodeBlock) obj;
        return this.parts.equals(that.parts);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.stmtKind().hashCode();
        hashCode = 31 * hashCode + parts.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "CodeBlock{"
            + "stmtKind: " + stmtKind()
            + ", parts: " + parts + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static CodeBlock from(String format, Object... args) {
        return builder().parts(FormatParser.parseFormat(format, args)).build();
    }

    @Override
    public <VisitorR> VisitorR accept(SyntaxNodeVisitor<VisitorR> visitor) {
        return visitor.visitCodeBlock(this);
    }

    public static final class Builder {
        private CollectionBuilderReference<List<FormatterNode>> parts;

        Builder() {
            this.parts = CollectionBuilderReference.forList();
        }

        Builder(CodeBlock data) {
            this.parts = CollectionBuilderReference.fromPersistentList(data.parts);
        }

        /**
         * <p>Sets the value for <code>parts</code></p>
         */
        public Builder parts(List<FormatterNode> parts) {
            this.parts.clear();
            this.parts.asTransient().addAll(parts);
            return this;
        }

        /**
         * <p>Adds a single value for <code>parts</code></p>
         */
        public Builder addPart(FormatterNode part) {
            this.parts.asTransient().add(part);
            return this;
        }

        /**
         * <p>Adds the formatted code to the block builder</p>
         */
        public Builder addCode(String format, Object... args) {
            this.parts.asTransient().addAll(FormatParser.parseFormat(format, args));
            return this;
        }

        public CodeBlock build() {
            return new CodeBlock(this);
        }
    }
}
