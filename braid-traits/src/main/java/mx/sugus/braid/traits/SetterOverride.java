package mx.sugus.braid.traits;

import java.util.List;
import software.amazon.smithy.model.node.ArrayNode;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ToNode;
import software.amazon.smithy.utils.BuilderRef;
import software.amazon.smithy.utils.SmithyBuilder;
import software.amazon.smithy.utils.SmithyGenerated;
import software.amazon.smithy.utils.ToSmithyBuilder;

@SmithyGenerated
public final class SetterOverride implements ToNode, ToSmithyBuilder<SetterOverride> {
    private final List<Argument> args;
    private final String body;

    private SetterOverride(Builder builder) {
        this.args = builder.args.copy();
        this.body = builder.body;
    }

    /**
     * Creates a {@link SetterOverride} from a {@link Node}.
     *
     * @param node Node to create the SetterOverride from.
     * @return Returns the created SetterOverride.
     * @throws software.amazon.smithy.model.node.ExpectationNotMetException if the given Node is invalid.
     */
    public static SetterOverride fromNode(Node node) {
        Builder builder = builder();
        node.expectObjectNode().getArrayMember("args", n -> Argument.fromNode(n), builder::args)
            .getStringMember("body", builder::body);

        return builder.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Node toNode() {
        return Node.objectNodeBuilder()
                   .withMember("args", getArgs().stream().map(s -> s.toNode()).collect(ArrayNode.collect()))
                   .withMember("body", getBody()).build();
    }

    public List<Argument> getArgs() {
        return args;
    }

    public String getBody() {
        return body;
    }

    /**
     * Creates a builder used to build a {@link SetterOverride}.
     */
    @Override
    public SmithyBuilder<SetterOverride> toBuilder() {
        return builder().args(args)
                        .body(body);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof SetterOverride)) {
            return false;
        } else {
            SetterOverride b = (SetterOverride) other;
            return toNode().equals(b.toNode());
        }
    }

    @Override
    public int hashCode() {
        return toNode().hashCode();
    }

    /**
     * Builder for {@link SetterOverride}.
     */
    public static final class Builder implements SmithyBuilder<SetterOverride> {
        private final BuilderRef<List<Argument>> args = BuilderRef.forList();
        private String body;

        private Builder() {
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

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        @Override
        public SetterOverride build() {
            return new SetterOverride(this);
        }
    }
}
