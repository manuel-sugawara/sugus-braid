package mx.sugus.braid.traits;

import java.util.List;
import software.amazon.smithy.model.node.ArrayNode;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.node.ToNode;
import software.amazon.smithy.utils.BuilderRef;
import software.amazon.smithy.utils.SmithyBuilder;
import software.amazon.smithy.utils.SmithyGenerated;
import software.amazon.smithy.utils.ToSmithyBuilder;

@SmithyGenerated
public final class BuilderOverride implements ToNode, ToSmithyBuilder<BuilderOverride> {
    private final String name;
    private final String javadoc;
    private final List<Argument> args;
    private final List<String> body;

    private BuilderOverride(Builder builder) {
        this.name = builder.name;
        this.javadoc = builder.javadoc;
        this.args = builder.args.copy();
        this.body = builder.body.copy();
    }

    @Override
    public Node toNode() {
        var builder = Node.objectNodeBuilder()
                          .withMember("args", getArgs().stream().map(Argument::toNode).collect(ArrayNode.collect()))
                          .withMember("body", getBody().stream().map(Node::from).collect(ArrayNode.collect()));
        if (name != null) {
            builder.withMember("name", name);
        }
        if (javadoc != null) {
            builder.withMember("javadoc", javadoc);
        }
        return builder.build();
    }

    public String getName() {
        return name;
    }

    public String getJavadoc() {
        return javadoc;
    }

    public List<Argument> getArgs() {
        return args;
    }

    public List<String> getBody() {
        return body;
    }

    /**
     * Creates a builder used to build a {@link BuilderOverride}.
     */
    @Override
    public SmithyBuilder<BuilderOverride> toBuilder() {
        return builder()
            .args(args)

            .body(body);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof BuilderOverride b)) {
            return false;
        }
        return toNode().equals(b.toNode());
    }

    @Override
    public int hashCode() {
        return toNode().hashCode();
    }

    /**
     * Creates a {@link BuilderOverride} from a {@link Node}.
     *
     * @param node Node to create the BuilderOverride from.
     * @return Returns the created BuilderOverride.
     * @throws software.amazon.smithy.model.node.ExpectationNotMetException if the given Node is invalid.
     */
    public static BuilderOverride fromNode(Node node) {
        Builder builder = builder();
        var objectNode = node.expectObjectNode();
        objectNode
            .getStringMember("name").map(StringNode::getValue).map(builder::name);
        objectNode
            .getStringMember("javadoc").map(StringNode::getValue).map(builder::javadoc);
        objectNode
            .getArrayMember("args", Argument::fromNode, builder::args)
            .getArrayMember("body", n -> n.expectStringNode().getValue(), builder::body);

        return builder.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link BuilderOverride}.
     */
    public static final class Builder implements SmithyBuilder<BuilderOverride> {
        private final BuilderRef<List<Argument>> args = BuilderRef.forList();
        private final BuilderRef<List<String>> body = BuilderRef.forList();
        private String name;
        private String javadoc;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder javadoc(String javadoc) {
            this.javadoc = javadoc;
            return this;
        }

        public Builder args(List<Argument> args) {
            clearArgs();
            this.args.get().addAll(args);
            return this;
        }

        public Builder clearArgs() {
            args.get().clear();
            return this;
        }

        public Builder addArgs(Argument value) {
            args.get().add(value);
            return this;
        }

        public Builder removeArgs(Argument value) {
            args.get().remove(value);
            return this;
        }

        public Builder body(List<String> body) {
            clearBody();
            this.body.get().addAll(body);
            return this;
        }

        public Builder clearBody() {
            body.get().clear();
            return this;
        }

        public Builder addBody(String value) {
            body.get().add(value);
            return this;
        }

        public Builder removeBody(String value) {
            body.get().remove(value);
            return this;
        }


        @Override
        public BuilderOverride build() {
            return new BuilderOverride(this);
        }
    }
}
