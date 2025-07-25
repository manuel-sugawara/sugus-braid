package mx.sugus.braid.core;

import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.shapes.ShapeId;

/**
 * Configuration settings for the Braid code generation process.
 *
 * <p>This record encapsulates all the essential configuration parameters needed
 * for code generation, including service identification, package structure, and versioning information. The settings are
 * typically parsed from the Smithy build configuration file.
 *
 * @param settingsNode   The raw configuration node from the build settings
 * @param service        The shape ID of the Smithy service to generate code for
 * @param shortName      An optional short name for the service (used as an alternative to the service shape name)
 * @param packageName    The Java package name for generated classes
 * @param packageVersion The version string for the generated package
 */
public record BraidCodegenSettings(
    ObjectNode settingsNode,
    ShapeId service,
    String shortName,
    String packageName,
    String packageVersion
) {
    /**
     * Returns the effective service name to use in code generation.
     *
     * <p>If a {@code shortName} is configured, it takes precedence over the
     * service shape's natural name. This allows for customizing the generated service name without changing the Smithy model.
     *
     * @return The short name if specified, otherwise the service shape's name
     */
    public String serviceName() {
        if (shortName != null) {
            return shortName;
        }
        return service.getName();
    }

    /**
     * Returns the package name split into its component parts.
     *
     * <p>Splits the dot-separated package name into an array of individual
     * package segments. This is useful for constructing directory paths or when working with package hierarchies.
     *
     * @return An array of package name segments
     */
    public String[] packageParts() {
        return packageName.split("\\.");
    }

    /**
     * Creates a new BraidCodegenSettings instance from a configuration node.
     *
     * <p>Parses the required configuration parameters from the Smithy build
     * configuration and constructs a settings object. The configuration must include {@code service}, {@code package}, and
     * {@code packageVersion} fields. The {@code shortName} field is optional.
     *
     * @param node The configuration node from the build settings
     * @return A new settings instance with the parsed configuration
     * @throws software.amazon.smithy.model.node.ExpectationNotMetException if required fields are missing
     */
    public static BraidCodegenSettings from(ObjectNode node) {
        return new BraidCodegenSettings(
            node,
            node.expectStringMember("service").expectShapeId(),
            node.expectStringMember("shortName").asStringNode().map(StringNode::getValue).orElse(null),
            node.expectStringMember("package").getValue(),
            node.expectStringMember("packageVersion").getValue()
        );
    }
}
