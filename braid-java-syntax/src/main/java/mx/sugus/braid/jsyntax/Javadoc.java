package mx.sugus.braid.jsyntax;

import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.syntax#SyntaxModelPlugin"})
public final class Javadoc implements SyntaxNode {
    private final CodeBlock body;
    private final Map<String, CodeBlock> params;
    private final CodeBlock returns;
    private int _hashCode = 0;

    private Javadoc(Builder builder) {
        this.body = builder.body;
        this.params = Objects.requireNonNull(builder.params.asPersistent(), "params");
        this.returns = builder.returns;
    }

    /**
     * 
     * @return The value of the {@code body} member
     */
    public CodeBlock body() {
        return this.body;
    }

    /**
     * 
     * @return The value of the {@code params} member
     */
    public Map<String, CodeBlock> params() {
        return this.params;
    }

    /**
     * 
     * @return The value of the {@code returns} member
     */
    public CodeBlock returns() {
        return this.returns;
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
        return visitor.visitJavadoc(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Javadoc that = (Javadoc) obj;
        return Objects.equals(this.body, that.body)
            && this.params.equals(that.params)
            && Objects.equals(this.returns, that.returns);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + (body != null ? body.hashCode() : 0);
            hashCode = 31 * hashCode + params.hashCode();
            hashCode = 31 * hashCode + (returns != null ? returns.hashCode() : 0);
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "Javadoc{"
            + "body: " + body
            + ", params: " + params
            + ", returns: " + returns + "}";
    }

    /**
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements SyntaxNode.Builder {
        private CodeBlock body;
        private CollectionBuilderReference<Map<String, CodeBlock>> params;
        private CodeBlock returns;

        Builder() {
            this.params = CollectionBuilderReference.forOrderedMap();
        }

        Builder(Javadoc data) {
            this.body = data.body;
            this.params = CollectionBuilderReference.fromPersistentOrderedMap(data.params);
            this.returns = data.returns;
        }

        /**
         * Sets the value for {@code body}.
         * 
         * @param body The value to be set.
         * @return This instance for chain calling.
         */
        public Builder body(CodeBlock body) {
            this.body = body;
            return this;
        }

        /**
         * Sets the value for {@code params}.
         * 
         * @param params The value to be set.
         * @return This instance for chain calling.
         */
        public Builder params(Map<String, CodeBlock> params) {
            this.params.clear();
            this.params.asTransient().putAll(params);
            return this;
        }

        /**
         * Puts a new entry to the {@code params} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param param The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putParam(String key, CodeBlock param) {
            this.params.asTransient().put(key, param);
            return this;
        }

        /**
         * Sets the value for {@code returns}.
         * 
         * @param returns The value to be set.
         * @return This instance for chain calling.
         */
        public Builder returns(CodeBlock returns) {
            this.returns = returns;
            return this;
        }

        /**
         * Creates and sets the {@code body} using the given format and arguments.
         * 
         * @param format The format to create the {@link CodeBlock} body
         * @param args The arguments for the format
         * @return This instance for chain calling.
         */
        public Builder body(String format, Object... args) {
            if (format != null) {
                body(CodeBlock.from(format, args));
            } else {
                body(null);
            }
            return this;
        }

        /**
         * Creates and puts a {@code param} with the name using the given format and arguments.
         * 
         * @param name The name of the param
         * @param format The format to create the {@link CodeBlock} param
         * @param args The arguments for the format
         * @return This instance for chain calling.
         */
        public Builder putParam(String name, String format, Object... args) {
            putParam(name, CodeBlock.from(format, args));
            return this;
        }

        /**
         * Creates and sets the {@code returns} using the given format and arguments.
         * 
         * @param format The format to create the {@link CodeBlock} returns
         * @param args The arguments for the format
         * @return This instance for chain calling.
         */
        public Builder returns(String format, Object... args) {
            returns(CodeBlock.from(format, args));
            return this;
        }

        /**
         * Returns a new instance of {@link Javadoc}
         * 
         * @return A new instance of {@link Javadoc}
         */
        public Javadoc build() {
            return new Javadoc(this);
        }
    }
}
