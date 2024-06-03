package mx.sugus.braid.traits;

import java.util.List;
import software.amazon.smithy.model.FromSourceLocation;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.StringListTrait;
import software.amazon.smithy.utils.ToSmithyBuilder;

public final class ImplementsTrait extends StringListTrait implements ToSmithyBuilder<ImplementsTrait> {
    public static final ShapeId ID = ShapeId.from("mx.sugus.braid.traits#implements");

    public ImplementsTrait(List<String> values, FromSourceLocation sourceLocation) {
        super(ID, values, sourceLocation);
    }

    public static ImplementsTrait.Builder builder() {
        return new ImplementsTrait.Builder();
    }

    @Override
    public ImplementsTrait.Builder toBuilder() {
        return builder().sourceLocation(getSourceLocation()).values(getValues());
    }

    public static final class Provider extends StringListTrait.Provider<ImplementsTrait> {
        public Provider() {
            super(ID, ImplementsTrait::new);
        }
    }

    public static final class Builder extends StringListTrait.Builder<ImplementsTrait, ImplementsTrait.Builder> {
        private Builder() {
        }

        @Override
        public ImplementsTrait build() {
            return new ImplementsTrait(getValues(), getSourceLocation());
        }
    }
}
