package mx.sugus.braid.core.plugin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IdentifierTest {

    @Test
    public void testAcceptsSimpleIdentifier() {
        var id = Identifier.of("foo#bar");
        assertEquals("foo", id.namespace());
        assertEquals("bar", id.name());
    }

    @Test
    public void testAcceptsIdentifierWithTwoSegmentsNamespace() {
        var id = Identifier.of("foo.bar#baz");
        assertEquals("foo.bar", id.namespace());
        assertEquals("baz", id.name());
    }

    @Test
    public void testRejectsEmptyString() {
        assertThrows(Identifier.IdentifierSyntaxException.class, () -> Identifier.of(""));
    }

    @Test
    public void testRejectsMissingNamespace() {
        assertThrows(Identifier.IdentifierSyntaxException.class, () -> Identifier.of("foo"));
    }

    @Test
    public void testRejectsEmptyNamespace() {
        assertThrows(Identifier.IdentifierSyntaxException.class, () -> Identifier.of("#foo"));
    }

    @Test
    public void testRejectsEmptyName() {
        assertThrows(Identifier.IdentifierSyntaxException.class, () -> Identifier.of("foo#"));
    }


    @Test
    public void testNamespaceOneSegmentIsValid() {
        assertEquals(-1, Identifier.firstInvalidCharPosition("foo"));
    }

    @Test
    public void testNamespaceTwoSegmentsIsValid() {
        assertEquals(-1, Identifier.firstInvalidCharPosition("foo.bar"));
    }

    @Test
    public void testNamespaceSingleCharIsValid() {
        assertEquals(-1, Identifier.firstInvalidCharPosition("x"));
    }

    @Test
    public void testNamespaceEmptyIsValid() {
        assertEquals(-1, Identifier.firstInvalidCharPosition(""));
    }

    @Test
    public void testNamespaceFindsDoubleDots() {
        assertEquals(4, Identifier.firstInvalidCharPosition("foo..bar"));
    }

    @Test
    public void testNamespaceFindsTrailingDots() {
        assertEquals(7, Identifier.firstInvalidCharPosition("foo.bar."));
    }

    @Test
    public void testNamespaceFindsLeadingDots() {
        assertEquals(0, Identifier.firstInvalidCharPosition(".foo.bar"));
    }

    @Test
    public void testNamespaceFindsNonIdentifierChars() {
        assertEquals(3, Identifier.firstInvalidCharPosition("foo…bar"));
    }
}