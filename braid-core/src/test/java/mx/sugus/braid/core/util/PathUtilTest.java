package mx.sugus.braid.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.Test;

class PathUtilTest {

    @Test
    void testFromWithSingleComponent() {
        var result = PathUtil.from("component");

        assertEquals("component", result);
    }

    @Test
    void testFromWithMultipleComponents() {
        var result = PathUtil.from("first", "second", "third");

        var expected = "first" + File.separatorChar + "second" + File.separatorChar + "third";
        assertEquals(expected, result);
    }

    @Test
    void testFromWithTwoComponents() {
        var result = PathUtil.from("parent", "child");

        var expected = "parent" + File.separatorChar + "child";
        assertEquals(expected, result);
    }

    @Test
    void testFromWithEmptyArray() {
        var result = PathUtil.from();

        assertEquals("", result);
    }

    @Test
    void testFromWithEmptyStrings() {
        var result = PathUtil.from("", "middle", "");

        var expected = "" + File.separatorChar + "middle" + File.separatorChar + "";
        assertEquals(expected, result);
    }

    @Test
    void testFromWithSpacesInComponents() {
        var result = PathUtil.from("path with spaces", "another path", "final component");

        var expected = "path with spaces" + File.separatorChar + "another path" + File.separatorChar + "final component";
        assertEquals(expected, result);
    }

    @Test
    void testFromWithSpecialCharacters() {
        var result = PathUtil.from("path-with-dashes", "path_with_underscores", "path.with.dots");

        var expected = "path-with-dashes" + File.separatorChar + "path_with_underscores" + File.separatorChar + "path.with.dots";
        assertEquals(expected, result);
    }

    @Test
    void testFromWithUnicodeCharacters() {
        var result = PathUtil.from("路径", "пуь", "パス");

        var expected = "路径" + File.separatorChar + "пуь" + File.separatorChar + "パス";
        assertEquals(expected, result);
    }

    @Test
    void testFromWithNullComponents() {
        // Note: This tests the current behavior - the method doesn't explicitly handle nulls
        // In practice, this would result in "null" being included in the path
        var result = PathUtil.from("first", null, "third");

        var expected = "first" + File.separatorChar + "null" + File.separatorChar + "third";
        assertEquals(expected, result);
    }

    @Test
    void testFromWithVeryLongPath() {
        var longComponent = "a".repeat(100);
        var result = PathUtil.from(longComponent, "middle", longComponent);

        var expected = longComponent + File.separatorChar + "middle" + File.separatorChar + longComponent;
        assertEquals(expected, result);
    }

    @Test
    void testFromUsesSystemSeparator() {
        var result = PathUtil.from("a", "b");

        // Verify it uses the system separator, not hardcoded / or \
        assertTrue(result.contains(String.valueOf(File.separatorChar)));
        assertFalse(result.contains("/") && File.separatorChar != '/');
        assertFalse(result.contains("\\") && File.separatorChar != '\\');
    }

    @Test
    void testFromWithPathLikeComponents() {
        // Test components that already contain separators
        var result = PathUtil.from("path/with/slashes", "path\\with\\backslashes");

        var expected = "path/with/slashes" + File.separatorChar + "path\\with\\backslashes";
        assertEquals(expected, result);
    }

    @Test
    void testFromBuildsCorrectLength() {
        var components = new String[]{"a", "b", "c", "d", "e"};
        var result = PathUtil.from(components);

        // Length should be sum of components + (n-1) separators
        var expectedLength = components.length - 1; // separators
        for (var component : components) {
            expectedLength += component.length();
        }

        assertEquals(expectedLength, result.length());
    }
}