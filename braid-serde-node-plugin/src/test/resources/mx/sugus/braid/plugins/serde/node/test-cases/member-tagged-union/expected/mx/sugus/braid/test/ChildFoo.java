package mx.sugus.braid.test;

import java.util.Map;
import java.util.Objects;
import mx.sugus.braid.rt.util.SinkValidator;
import mx.sugus.braid.rt.util.Validation;
import mx.sugus.braid.rt.util.annotations.Generated;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.node.ToNode;

@Generated({"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
public final class ChildFoo implements SyntaxNodeChild, SyntaxNode, ToNode {
    private final String foo;

    private ChildFoo(Builder builder) {
        this.foo = builder.foo;
    }

    public ChildKind kind() {
        return ChildKind.FOO;
    }

    /**
     * 
     * @return The value of the {@code foo} member
     */
    public String foo() {
        return this.foo;
    }

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
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
        ChildFoo that = (ChildFoo) obj;
        return Objects.equals(this.foo, that.foo);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.kind().hashCode();
        hashCode = 31 * hashCode + (foo != null ? foo.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "ChildFoo{"
            + "kind: " + kind()
            + ", foo: " + foo + "}";
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
     * Converts this instance to Node.
     */
    @Override
    public Node toNode() {
        ObjectNode.Builder builder = Node.objectNodeBuilder();
        builder.withMember("kind", Node.from(kind().toString()));
        if (this.foo != null) {
            builder.withMember("foo", Node.from(foo()));
        }
        return builder.build();
    }

    /**
     * Converts a {@link Node} to ChildFoo.
     */
    public static ChildFoo fromNode(Node node) {
        return fromNode(SinkValidator.instance(), node);
    }

    /**
     * Converts a {@link Node} to ChildFoo.
     */
    public static ChildFoo fromNode(Validation validator, Node node) {
        validator = validator.with("ChildFoo");
        ChildFoo.Builder builder = builder();
        ObjectNode obj = node.expectObjectNode();
        for (Map.Entry<StringNode, Node> kvp : obj.getMembers().entrySet()) {
            Node value = kvp.getValue();
            String key = kvp.getKey().getValue();
            switch (key) {
                case "kind":
                    break;
                case "foo":
                    builder.foo(value.expectStringNode().getValue());
                    break;
                default:
                    validator.report(Validation.Severity.WARNING, key, () -> String.format("unknown key `%s` with value `%s`", key, value));
                    break;
            }
        }
        return builder.build();
    }

    public static final class Builder implements SyntaxNodeChild.Builder, SyntaxNode.Builder {
        private String foo;

        Builder() {
        }

        Builder(ChildFoo data) {
            this.foo = data.foo;
        }

        /**
         * Sets the value for {@code foo}.
         * 
         * @param foo The value to be set.
         * @return This instance for chain calling.
         */
        public Builder foo(String foo) {
            this.foo = foo;
            return this;
        }

        /**
         * Returns a new instance of {@link ChildFoo}
         * 
         * @return A new instance of {@link ChildFoo}
         */
        public ChildFoo build() {
            return new ChildFoo(this);
        }
    }
}
