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

public final class MultiAddOverridesTrait extends AbstractTrait implements ToSmithyBuilder<MultiAddOverridesTrait> {
    public static final ShapeId ID = ShapeId.from("mx.sugus.braid.traits#multiAddOverrides");
    private final List<BuilderOverride> values;

    private MultiAddOverridesTrait(Builder builder) {
        super(ID, builder.getSourceLocation());
        this.values = builder.values.copy();
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
     * Creates a builder used to build a {@link AdderOverridesTrait}.
     */
    @Override
    public SmithyBuilder<MultiAddOverridesTrait> toBuilder() {
        return builder().sourceLocation(getSourceLocation())
                        .values(values);

    }

    /**
     * Creates a {@link AdderOverridesTrait} from a {@link Node}.
     *
     * @param node Node to create the AdderOverridesTrait from.
     * @return Returns the created AdderOverridesTrait.
     * @throws software.amazon.smithy.model.node.ExpectationNotMetException if the given Node is invalid.
     */
    public static MultiAddOverridesTrait fromNode(Node node) {
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

    /**
     * Builder for {@link AdderOverridesTrait}.
     */
    public static final class Builder extends AbstractTraitBuilder<MultiAddOverridesTrait, Builder> {
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
        public MultiAddOverridesTrait build() {
            return new MultiAddOverridesTrait(this);
        }
    }

    public static final class Provider extends AbstractTrait.Provider {
        public Provider() {
            super(ID);
        }

        @Override
        public Trait createTrait(ShapeId target, Node value) {
            MultiAddOverridesTrait result = fromNode(value);
            result.setNodeCache(value);
            return result;
        }
    }
}
