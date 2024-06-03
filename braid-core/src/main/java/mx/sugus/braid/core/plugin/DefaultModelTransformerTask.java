package mx.sugus.braid.core.plugin;

import java.util.Objects;
import java.util.function.Function;
import software.amazon.smithy.model.Model;

public final class DefaultModelTransformerTask implements ModelTransformerTask {
    private final Identifier taskId;
    private final Function<Model, Model> transform;

    public DefaultModelTransformerTask(Builder builder) {
        this.taskId = Objects.requireNonNull(builder.taskId);
        this.transform = Objects.requireNonNull(builder.transform);
    }

    @Override
    public Identifier taskId() {
        return taskId;
    }

    @Override
    public Model transform(Model in) {
        return transform.apply(in);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Identifier taskId;
        private Function<Model, Model> transform;

        public Builder taskId(Identifier taskId) {
            this.taskId = taskId;
            return this;
        }

        public Builder transform(Function<Model, Model> transform) {
            this.transform = transform;
            return this;
        }

        public DefaultModelTransformerTask build() {
            return new DefaultModelTransformerTask(this);
        }
    }
}
