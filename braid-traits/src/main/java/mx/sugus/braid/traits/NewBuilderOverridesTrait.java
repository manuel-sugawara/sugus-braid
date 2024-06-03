package mx.sugus.braid.traits;

import java.util.List;
import software.amazon.smithy.model.node.ArrayNode;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.AbstractTrait;
import software.amazon.smithy.model.traits.AbstractTraitBuilder;
import software.amazon.smithy.model.traits.Trait;
import software.amazon.smithy.utils.BuilderRef;
import software.amazon.smithy.utils.SmithyBuilder;
import software.amazon.smithy.utils.ToSmithyBuilder;

public final class NewBuilderOverridesTrait extends AbstractTrait implements ToSmithyBuilder<NewBuilderOverridesTrait> {
    public static final ShapeId ID = ShapeId.from("mx.sugus.braid.traits#newBuilderOverrides");

    private final List<BuilderOverride> values;

    private NewBuilderOverridesTrait(Builder builder) {
        super(ID, builder.getSourceLocation());
        this.values = builder.values.copy();
    }

    /**
     * Creates a {@link NewBuilderOverridesTrait} from a {@link Node}.
     *
     * @param node Node to create the NewBuilderOverridesTrait from.
     * @return Returns the created NewBuilderOverridesTrait.
     * @throws software.amazon.smithy.model.node.ExpectationNotMetException if the given Node is invalid.
     */
    public static NewBuilderOverridesTrait fromNode(Node node) {
        Builder builder = builder();
        node.expectArrayNode()
            .getElements().stream()
            .map(n -> BuilderOverride.fromNode(n))
            .forEach(builder::addValues);
        return builder.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected Node createNode() {
        return values.stream()
                     .map(s -> s.toNode())
                     .collect(ArrayNode.collect(getSourceLocation()));
    }

    public List<BuilderOverride> getValues() {
        return values;
    }

    /**
     * Creates a builder used to build a {@link NewBuilderOverridesTrait}.
     */
    @Override
    public SmithyBuilder<NewBuilderOverridesTrait> toBuilder() {
        return builder().sourceLocation(getSourceLocation())
                        .values(values);

    }

    /**
     * Builder for {@link NewBuilderOverridesTrait}.
     */
    public static final class Builder extends AbstractTraitBuilder<NewBuilderOverridesTrait, Builder> {
        private final BuilderRef<List<BuilderOverride>> values = BuilderRef.forList();

        private Builder() {
        }

        public Builder values(List<BuilderOverride> values) {
            clearValues();
            this.values.get().addAll(values);
            return this;
        }

        public Builder clearValues() {
            values.get().clear();
            return this;
        }

        public Builder addValues(BuilderOverride value) {
            values.get().add(value);
            return this;
        }

        public Builder removeValues(BuilderOverride value) {
            values.get().remove(value);
            return this;
        }

        @Override
        public NewBuilderOverridesTrait build() {
            return new NewBuilderOverridesTrait(this);
        }
    }

    public static final class Provider extends AbstractTrait.Provider {
        public Provider() {
            super(ID);
        }

        @Override
        public Trait createTrait(ShapeId target, Node value) {
            NewBuilderOverridesTrait result = fromNode(value);
            result.setNodeCache(value);
            return result;
        }
    }
}
