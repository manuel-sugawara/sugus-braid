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

public final class SetterOverridesTrait extends AbstractTrait implements ToSmithyBuilder<SetterOverridesTrait> {
    public static final ShapeId ID = ShapeId.from("mx.sugus.braid.traits#setterOverrides");

    private final List<SetterOverride> values;

    private SetterOverridesTrait(Builder builder) {
        super(ID, builder.getSourceLocation());
        this.values = builder.values.copy();
    }

    /**
     * Creates a {@link SetterOverridesTrait} from a {@link Node}.
     *
     * @param node Node to create the SetterOverridesTrait from.
     * @return Returns the created SetterOverridesTrait.
     * @throws software.amazon.smithy.model.node.ExpectationNotMetException if the given Node is invalid.
     */
    public static SetterOverridesTrait fromNode(Node node) {
        Builder builder = builder();
        node.expectArrayNode()
            .getElements().stream()
            .map(n -> SetterOverride.fromNode(n))
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

    public List<SetterOverride> getValues() {
        return values;
    }

    /**
     * Creates a builder used to build a {@link SetterOverridesTrait}.
     */
    @Override
    public SmithyBuilder<SetterOverridesTrait> toBuilder() {
        return builder().sourceLocation(getSourceLocation())
                        .values(values);

    }

    /**
     * Builder for {@link SetterOverridesTrait}.
     */
    public static final class Builder extends AbstractTraitBuilder<SetterOverridesTrait, Builder> {
        private final BuilderRef<List<SetterOverride>> values = BuilderRef.forList();

        private Builder() {
        }

        public Builder values(List<SetterOverride> values) {
            clearValues();
            this.values.get().addAll(values);
            return this;
        }

        public Builder clearValues() {
            values.get().clear();
            return this;
        }

        public Builder addValues(SetterOverride value) {
            values.get().add(value);
            return this;
        }

        public Builder removeValues(SetterOverride value) {
            values.get().remove(value);
            return this;
        }

        @Override
        public SetterOverridesTrait build() {
            return new SetterOverridesTrait(this);
        }
    }

    public static final class Provider extends AbstractTrait.Provider {
        public Provider() {
            super(ID);
        }

        @Override
        public Trait createTrait(ShapeId target, Node value) {
            SetterOverridesTrait result = fromNode(value);
            result.setNodeCache(value);
            return result;
        }
    }
}
