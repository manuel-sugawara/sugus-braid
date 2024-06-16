package mx.sugus.braid.plugins.data.config;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.ToNode;

/**
 * <p>Configuration settings for the</p>
 */
@Generated( {"mx.sugus.braid.plugins.data#DataPlugin", "mx.sugus.braid.plugins.serde.node#NodeSerdePlugin"})
public final class DataPluginConfig implements ToNode {
    private final NullabilityCheckMode nullabilityMode;
    private final String packageName;

    private DataPluginConfig(Builder builder) {
        this.nullabilityMode = builder.nullabilityMode;
        this.packageName = builder.packageName;
    }

    /**
     * <p>The nullability mode to check if the member of an aggregate shape
     * should be considered nullable. If not configured otherwise the CLIENT mode is used.</p>
     */
    public NullabilityCheckMode nullabilityMode() {
        return this.nullabilityMode;
    }

    /**
     * <p>If configured is the package name used for the codegen java classes.
     * By default the namespace of the shape is used.</p>
     */
    public String packageName() {
        return this.packageName;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
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
        DataPluginConfig that = (DataPluginConfig) obj;
        return Objects.equals(this.nullabilityMode, that.nullabilityMode)
               && Objects.equals(this.packageName, that.packageName);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (nullabilityMode != null ? nullabilityMode.hashCode() : 0);
        hashCode = 31 * hashCode + (packageName != null ? packageName.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "DataPluginConfig{"
               + "nullabilityMode: " + nullabilityMode
               + ", packageName: " + packageName + "}";
    }

    /**
     * <p>Converts this instance to Node</p>
     */
    @Override
    public Node toNode() {
        ObjectNode.Builder builder = Node.objectNodeBuilder();
        if (this.nullabilityMode != null) {
            builder.withMember("nullabilityMode", Node.from(this.nullabilityMode.toString()));
        }
        if (this.packageName != null) {
            builder.withMember("packageName", Node.from(this.packageName));
        }
        return builder.build();
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * <p>Converts a Node to DataPluginConfig</p>
     */
    public static DataPluginConfig fromNode(Node node) {
        DataPluginConfig.Builder builder = builder();
        ObjectNode obj = node.expectObjectNode();
        obj.getMember("nullabilityMode").map(n -> NullabilityCheckMode.from(n.expectStringNode().getValue())).ifPresent(builder::nullabilityMode);
        obj.getMember("packageName").map(n -> n.expectStringNode().getValue()).ifPresent(builder::packageName);
        return builder.build();
    }

    public static final class Builder {
        private NullabilityCheckMode nullabilityMode;
        private String packageName;

        Builder() {
            this.nullabilityMode = NullabilityCheckMode.CLIENT;
        }

        Builder(DataPluginConfig data) {
            this.nullabilityMode = data.nullabilityMode;
            this.packageName = data.packageName;
        }

        /**
         * <p>Sets the value for <code>nullabilityMode</code></p>
         * <p>The nullability mode to check if the member of an aggregate shape
         * should be considered nullable. If not configured otherwise the CLIENT mode is used.</p>
         */
        public Builder nullabilityMode(NullabilityCheckMode nullabilityMode) {
            this.nullabilityMode = nullabilityMode;
            return this;
        }

        /**
         * <p>Sets the value for <code>packageName</code></p>
         * <p>If configured is the package name used for the codegen java classes.
         * By default the namespace of the shape is used.</p>
         */
        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public DataPluginConfig build() {
            return new DataPluginConfig(this);
        }
    }
}
