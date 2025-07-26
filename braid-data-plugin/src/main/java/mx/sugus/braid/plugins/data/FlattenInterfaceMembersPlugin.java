package mx.sugus.braid.plugins.data;

import mx.sugus.braid.core.plugin.CodegenModuleConfig;
import mx.sugus.braid.core.plugin.DefaultModelTransformerTask;
import mx.sugus.braid.core.plugin.Identifier;
import mx.sugus.braid.core.plugin.SmithyGeneratorPlugin;
import mx.sugus.braid.plugins.data.model.FlattenInterfaceMembers;
import software.amazon.smithy.model.node.ObjectNode;

/**
 * A Smithy generator plugin that flattens interface members into implementing structures.
 *
 * <p>This plugin performs a model transformation that copies all members from structures
 * marked with the {@code @interface} trait to all structures that implement them via the
 * {@code @implements} trait. This enables interface-like inheritance patterns in Smithy
 * models while generating concrete Java classes with all inherited members.
 *
 * <p>The transformation process:
 * <ol>
 *   <li>Identifies structures marked with {@code @interface}</li>
 *   <li>Finds all structures that implement these interfaces via {@code @implements}</li>
 *   <li>Copies interface members to implementing structures, handling name conflicts</li>
 *   <li>Preserves member traits and documentation during the copy process</li>
 * </ol>
 *
 * <p>Example transformation:
 * <pre>{@code
 * // Before transformation:
 * @interface
 * structure BaseInterface {
 *     commonField: String
 * }
 *
 * @implements([BaseInterface])
 * structure ConcreteStruct {
 *     specificField: Integer
 * }
 *
 * // After transformation:
 * structure ConcreteStruct {
 *     commonField: String        // Copied from BaseInterface
 *     specificField: Integer     // Original member
 * }
 * }</pre>
 *
 * <p>This plugin has no configuration and simply applies the flattening transformation
 * to any loaded Smithy model during the early model preprocessing phase.
 *
 * @see FlattenInterfaceMembers for the actual transformation implementation
 */
public final class FlattenInterfaceMembersPlugin implements SmithyGeneratorPlugin<ObjectNode> {
    /**
     * The unique identifier for this plugin.
     */
    public static final Identifier ID = Identifier.of(FlattenInterfaceMembersPlugin.class);

    /**
     * Creates a new instance of the FlattenInterfaceMembersPlugin.
     */
    public FlattenInterfaceMembersPlugin() {
    }

    /**
     * Returns the unique identifier for this plugin.
     *
     * @return the plugin identifier
     */
    @Override
    public Identifier provides() {
        return ID;
    }

    /**
     * Returns the configuration node as-is since this plugin requires no configuration.
     *
     * @param node the configuration node (unused)
     * @return the same configuration node
     */
    @Override
    public ObjectNode fromNode(ObjectNode node) {
        return node;
    }

    /**
     * Builds the code generation module configuration with the interface flattening transformer.
     *
     * <p>This method registers a single model transformer that applies the interface member
     * flattening logic during the early model preprocessing phase. The transformer runs
     * before any code generation to ensure that all implementing structures have the
     * correct set of members inherited from their interfaces.
     *
     * @param node the configuration node (unused)
     * @return the module configuration with the flattening transformer registered
     */
    @Override
    public CodegenModuleConfig moduleConfig(ObjectNode node) {
        return CodegenModuleConfig
            .builder()
            .addModelTransformer(DefaultModelTransformerTask
                                     .builder()
                                     .taskId(ID)
                                     .transform(FlattenInterfaceMembers::transform)
                                     .build())
            .build();
    }
}
