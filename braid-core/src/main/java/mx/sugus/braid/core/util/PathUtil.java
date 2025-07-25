package mx.sugus.braid.core.util;

import java.io.File;

/**
 * Utility class for constructing file paths using platform-appropriate separators.
 *
 * <p>This class provides helper methods for building file system paths that work
 * correctly across different operating systems by using the system's native file separator character.
 */
public class PathUtil {

    /**
     * Constructs a file path by joining the given path components with the system file separator.
     *
     * <p>The method concatenates all provided path components using the platform-specific
     * file separator ({@link File#separatorChar}). Empty argument arrays return an empty string.
     *
     * @param args The path components to join together
     * @return A file path string with components separated by the system file separator, or an empty string if no arguments are
     * provided
     */
    public static String from(String... args) {
        if (args.length == 0) {
            return "";
        }
        var buf = new StringBuilder(args[0]);
        for (var idx = 1; idx < args.length; ++idx) {
            buf.append(File.separatorChar);
            buf.append(args[idx]);
        }
        return buf.toString();
    }
}
