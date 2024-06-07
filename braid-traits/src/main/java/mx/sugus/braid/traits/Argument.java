package mx.sugus.braid.traits;

import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ToNode;
import software.amazon.smithy.utils.SmithyBuilder;
import software.amazon.smithy.utils.ToSmithyBuilder;

public final class Argument implements ToNode, ToSmithyBuilder<Argument> {
    private final String type;
    private final String name;

    private Argument(Builder builder) {
        this.type = SmithyBuilder.requiredState("type", builder.type);
        this.name = SmithyBuilder.requiredState("name", builder.name);
    }

    @Override
    public Node toNode() {
        return Node.objectNodeBuilder()
                   .withMember("type", Node.from(type))
                   .withMember("name", Node.from(name))
                   .build();
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    /**
     * Creates a builder used to build a {@link Argument}.
     */
    @Override
    public SmithyBuilder<Argument> toBuilder() {
        return builder()
            .type(type)

            .name(name);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Argument b)) {
            return false;
        }
        return toNode().equals(b.toNode());
    }

    @Override
    public int hashCode() {
        return toNode().hashCode();
    }

    /**
     * Creates a {@link Argument} from a {@link Node}.
     *
     * @param node Node to create the Argument from.
     * @return Returns the created Argument.
     * @throws software.amazon.smithy.model.node.ExpectationNotMetException if the given Node is invalid.
     */
    public static Argument fromNode(Node node) {
        Builder builder = builder();
        node.expectObjectNode()
            .expectStringMember("type", builder::type)
            .expectStringMember("name", builder::name);

        return builder.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link Argument}.
     */
    public static final class Builder implements SmithyBuilder<Argument> {
        private String type;
        private String name;

        private Builder() {
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }


        @Override
        public Argument build() {
            return new Argument(this);
        }
    }
}

