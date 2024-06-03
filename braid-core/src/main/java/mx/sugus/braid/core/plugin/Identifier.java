package mx.sugus.braid.core.plugin;

/**
 * Represents a identifier, that is, a name within a namespace.
 *
 * @param namespace The namespace
 * @param name      The simple name.
 */
public record Identifier(String namespace, String name) implements Comparable<Identifier> {

    @Override
    public int compareTo(Identifier o) {
        var res = namespace.compareTo(o.namespace);
        if (res != 0) {
            return res;
        }
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return namespace + "#" + name;
    }

    /**
     * Returns a new identifier parsing out the given value. The identifier is expected to be formed using a valid namespace
     * followed by the sharp char ('#') followed by the name. The namespace is composed of segments separated by a dot (.). Each
     * segment and the name itself have to be valid java identifiers.
     *
     * @param value The string representing the identifier
     * @return The new identifier.
     */
    public static Identifier of(String value) {
        var separatorPosition = value.indexOf('#');
        if (separatorPosition == -1) {
            throw new IdentifierSyntaxException(value, 0, "namespace separator `#` not found");
        }
        var namespace = value.substring(0, separatorPosition);
        if (namespace.isEmpty()) {
            throw new IdentifierSyntaxException(value, 0, "empty namespace");
        }
        var firstInvalidChar = firstInvalidCharPosition(namespace);
        if (firstInvalidChar != -1) {
            throw new IdentifierSyntaxException(value, firstInvalidChar, "invalid char in namespace");
        }
        var simpleName = value.substring(separatorPosition + 1);
        if (simpleName.isEmpty()) {
            throw new IdentifierSyntaxException(value, separatorPosition, "empty name");
        }
        var simpleNameLength = simpleName.length();
        var validIdentifierLength = advanceIdentifierPart(simpleName, 0, simpleName.length());
        if (simpleNameLength != validIdentifierLength) {
            throw new IdentifierSyntaxException(value, namespace.length() + 1 + validIdentifierLength, "invalid char in name");
        }
        return new Identifier(namespace, simpleName);
    }

    /**
     * Returns an identifier using the class package name and the class simple name
     *
     * @param value The class to construct the identifier from.
     * @return The new identifier.
     */
    public static Identifier of(Class<?> value) {
        return new Identifier(value.getPackageName(), value.getSimpleName());
    }

    /**
     * Returns an identifier with the given namespace and name.
     *
     * @param namespace  The namespace
     * @param simpleName The name
     * @return The new identifier.
     */
    public static Identifier of(String namespace, String simpleName) {
        if (namespace.isEmpty()) {
            throw new IdentifierSyntaxException(namespace, 0, "empty namespace");
        }
        var firstInvalidChar = firstInvalidCharPosition(namespace);
        if (firstInvalidChar != -1) {
            throw new IdentifierSyntaxException(namespace, firstInvalidChar, "invalid char in namespace");
        }
        if (simpleName.isEmpty()) {
            throw new IdentifierSyntaxException(simpleName, 0, "empty name");
        }
        var simpleNameLength = simpleName.length();
        var validIdentifierLength = advanceIdentifierPart(simpleName, 0, simpleName.length());
        if (simpleNameLength != validIdentifierLength) {
            throw new IdentifierSyntaxException(simpleName, validIdentifierLength, "invalid char in name");
        }
        return new Identifier(namespace, simpleName);
    }

    static int firstInvalidCharPosition(String value) {
        var length = value.length();
        var position = 0;
        var lastKnownDotPosition = -1;
        while (position < length) {
            var offset = advanceIdentifierPart(value, position, length);
            if (offset == 0) {
                return position;
            }
            position += offset;
            if (position < length) {
                if (value.charAt(position) != '.') {
                    return position;
                }
                lastKnownDotPosition = position;
            }
            position += 1;
        }
        if (lastKnownDotPosition != -1 && (position - 1) == lastKnownDotPosition) {
            // Trailing dot is invalid
            return lastKnownDotPosition;
        }
        return -1;
    }

    static int advanceIdentifierPart(String value, int start, int length) {
        var position = start;
        if (position < length) {
            if (!Character.isJavaIdentifierStart(value.charAt(position))) {
                return 0;
            }
            position += 1;
        }
        while (position < length && Character.isJavaIdentifierPart(value.charAt(position))) {
            position += 1;
        }
        return position - start;
    }

    public static class IdentifierSyntaxException extends RuntimeException {
        private final String source;
        private final int position;

        public IdentifierSyntaxException(String source, int position, String details) {
            super(String.format("Invalid identifier `%s` starting at %d: %s", source, position, details));
            this.source = source;
            this.position = position;
        }
    }

}
