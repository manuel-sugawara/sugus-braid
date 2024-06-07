package mx.sugus.braid.traits;

import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.AbstractTrait;
import software.amazon.smithy.model.traits.AbstractTraitBuilder;
import software.amazon.smithy.model.traits.Trait;
import software.amazon.smithy.utils.SmithyBuilder;
import software.amazon.smithy.utils.ToSmithyBuilder;

public final class UseBuilderReferenceTrait extends AbstractTrait implements ToSmithyBuilder<UseBuilderReferenceTrait> {
    public static final ShapeId ID = ShapeId.from("mx.sugus.braid.traits#useBuilderReference");
    private final ShapeId builderType;
    private final ShapeId fromPersistent;


    private UseBuilderReferenceTrait(Builder builder) {
        super(ID, builder.getSourceLocation());
        this.builderType = builder.builderType;
        this.fromPersistent = builder.fromPersistent;
    }

    @Override
    protected Node createNode() {
        return Node.objectNodeBuilder()
                   .withMember("builderType", Node.from(builderType.toString()))
                   .withMember("fromPersistent", Node.from(fromPersistent.toString()))
                   .build();
    }

    public ShapeId builderType() {
        return builderType;
    }

    public ShapeId fromPersistent() {
        return fromPersistent;
    }

    /**
     * Creates a builder used to build a {@link UseBuilderReferenceTrait}.
     */
    @Override
    public SmithyBuilder<UseBuilderReferenceTrait> toBuilder() {
        return builder().sourceLocation(getSourceLocation())
                        .builderType(builderType.toString())
                        .fromPersistent(fromPersistent.toString());
    }

    public static UseBuilderReferenceTrait fromNode(Node node) {
        Builder builder = builder();
        node.expectObjectNode()
            .expectStringMember("builderType", builder::builderType)
            .expectStringMember("fromPersistent", builder::fromPersistent);
        return builder.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link UseBuilderReferenceTrait}.
     */
    public static final class Builder extends AbstractTraitBuilder<UseBuilderReferenceTrait, Builder> {
        private ShapeId builderType;
        private ShapeId fromPersistent;

        private Builder() {
        }

        public Builder builderType(String builderType) {
            this.builderType = ShapeId.from(builderType);
            return this;
        }

        public Builder fromPersistent(String fromPersistent) {
            this.fromPersistent = ShapeId.from(fromPersistent);
            return this;
        }

        @Override
        public UseBuilderReferenceTrait build() {
            return new UseBuilderReferenceTrait(this);
        }
    }

    public static final class Provider extends AbstractTrait.Provider {
        public Provider() {
            super(ID);
        }

        @Override
        public Trait createTrait(ShapeId target, Node value) {
            UseBuilderReferenceTrait result = fromNode(value);
            result.setNodeCache(value);
            return result;
        }
    }
}
