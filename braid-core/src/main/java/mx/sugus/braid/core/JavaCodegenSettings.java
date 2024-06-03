package mx.sugus.braid.core;

import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.node.StringNode;
import software.amazon.smithy.model.shapes.ShapeId;

public record JavaCodegenSettings(
    ObjectNode settingsNode,
    ShapeId service,
    String shortName,
    String packageName,
    String packageVersion
) {
    public String serviceName() {
        if (shortName != null) {
            return shortName;
        }
        return service.getName();
    }

    public String[] packageParts() {
        return packageName.split("\\.");
    }

    public static JavaCodegenSettings from(ObjectNode node) {
        return new JavaCodegenSettings(
            node,
            node.expectStringMember("service").expectShapeId(),
            node.expectStringMember("shortName").asStringNode().map(StringNode::getValue).orElse(null),
            node.expectStringMember("package").getValue(),
            node.expectStringMember("packageVersion").getValue()
        );
    }
}
