package mx.sugus.braid.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.ShapeId;

class BraidCodegenSettingsTest {

    @Test
    void testFromNodeWithValidConfiguration() {
        var node = Node.objectNode()
            .withMember("service", Node.from("com.example#MyService"))
            .withMember("shortName", Node.from("MyAPI"))
            .withMember("package", Node.from("com.example.generated"))
            .withMember("packageVersion", Node.from("1.0.0"));

        var settings = BraidCodegenSettings.from(node);

        assertEquals(ShapeId.from("com.example#MyService"), settings.service());
        assertEquals("MyAPI", settings.shortName());
        assertEquals("com.example.generated", settings.packageName());
        assertEquals("1.0.0", settings.packageVersion());
        assertEquals(node, settings.settingsNode());
    }

    @Test
    void testFromNodeWithoutOptionalShortName() {
        var node = Node.objectNode()
            .withMember("service", Node.from("com.example#MyService"))
            .withMember("package", Node.from("com.example.generated"))
            .withMember("packageVersion", Node.from("1.0.0"));

        var settings = BraidCodegenSettings.from(node);

        assertEquals(ShapeId.from("com.example#MyService"), settings.service());
        assertNull(settings.shortName());
        assertEquals("com.example.generated", settings.packageName());
        assertEquals("1.0.0", settings.packageVersion());
    }

    @Test
    void testFromNodeThrowsOnMissingService() {
        var node = Node.objectNode()
            .withMember("package", Node.from("com.example.generated"))
            .withMember("packageVersion", Node.from("1.0.0"));

        assertThrows(Exception.class, () -> BraidCodegenSettings.from(node));
    }

    @Test
    void testFromNodeThrowsOnMissingPackage() {
        var node = Node.objectNode()
            .withMember("service", Node.from("com.example#MyService"))
            .withMember("packageVersion", Node.from("1.0.0"));

        assertThrows(Exception.class, () -> BraidCodegenSettings.from(node));
    }

    @Test
    void testFromNodeThrowsOnMissingPackageVersion() {
        var node = Node.objectNode()
            .withMember("service", Node.from("com.example#MyService"))
            .withMember("package", Node.from("com.example.generated"));

        assertThrows(Exception.class, () -> BraidCodegenSettings.from(node));
    }

    @Test
    void testFromNodeThrowsOnInvalidServiceId() {
        var node = Node.objectNode()
            .withMember("service", Node.from("invalid-service-id"))
            .withMember("package", Node.from("com.example.generated"))
            .withMember("packageVersion", Node.from("1.0.0"));

        assertThrows(Exception.class, () -> BraidCodegenSettings.from(node));
    }

    @Test
    void testServiceNameWithShortName() {
        var settings = new BraidCodegenSettings(
            Node.objectNode(),
            ShapeId.from("com.example#MyService"),
            "CustomName",
            "com.example.generated",
            "1.0.0"
        );

        assertEquals("CustomName", settings.serviceName());
    }

    @Test
    void testServiceNameWithoutShortName() {
        var settings = new BraidCodegenSettings(
            Node.objectNode(),
            ShapeId.from("com.example#MyService"),
            null,
            "com.example.generated",
            "1.0.0"
        );

        assertEquals("MyService", settings.serviceName());
    }

    @Test
    void testPackagePartsWithSimplePackage() {
        var settings = new BraidCodegenSettings(
            Node.objectNode(),
            ShapeId.from("com.example#MyService"),
            null,
            "com.example.generated",
            "1.0.0"
        );

        assertArrayEquals(new String[]{"com", "example", "generated"}, settings.packageParts());
    }

    @Test
    void testPackagePartsWithSingleSegment() {
        var settings = new BraidCodegenSettings(
            Node.objectNode(),
            ShapeId.from("com.example#MyService"),
            null,
            "generated",
            "1.0.0"
        );

        assertArrayEquals(new String[]{"generated"}, settings.packageParts());
    }

    @Test
    void testPackagePartsWithDeepPackage() {
        var settings = new BraidCodegenSettings(
            Node.objectNode(),
            ShapeId.from("com.example#MyService"),
            null,
            "org.springframework.boot.autoconfigure.generated",
            "1.0.0"
        );

        assertArrayEquals(new String[]{"org", "springframework", "boot", "autoconfigure", "generated"}, settings.packageParts());
    }

    @Test
    void testFromNodeWithEmptyStringValues() {
        var node = Node.objectNode()
            .withMember("service", Node.from("com.example#MyService"))
            .withMember("shortName", Node.from(""))
            .withMember("package", Node.from("com.example.generated"))
            .withMember("packageVersion", Node.from("1.0.0"));

        var settings = BraidCodegenSettings.from(node);

        // Empty string should be preserved as is, not converted to null
        assertEquals("", settings.shortName());
    }

    @Test
    void testFromNodeWithComplexServiceId() {
        var node = Node.objectNode()
            .withMember("service", Node.from("mx.sugus.braid.test.nested.deeply#ComplexServiceName"))
            .withMember("package", Node.from("com.example.generated"))
            .withMember("packageVersion", Node.from("2.1.0-SNAPSHOT"));

        var settings = BraidCodegenSettings.from(node);

        assertEquals(ShapeId.from("mx.sugus.braid.test.nested.deeply#ComplexServiceName"), settings.service());
        assertEquals("ComplexServiceName", settings.serviceName());
        assertEquals("2.1.0-SNAPSHOT", settings.packageVersion());
    }
}