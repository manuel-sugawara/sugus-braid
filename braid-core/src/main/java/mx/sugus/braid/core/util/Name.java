package mx.sugus.braid.core.util;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import software.amazon.smithy.utils.StringUtils;

/**
 * Represents a Java name that can be converted between naming conventions according to the use case
 */
public final class Name {
    private static final Pattern SPLIT_ALPHANUM = Pattern.compile("[^A-Za-z\\d]+");
    private static final Pattern SPLIT_NUMERIC_VERSION = Pattern.compile("([^a-z]{2,})([vV])([0-9]+)");
    private static final Pattern SPLIT_CAMEL_CASE = Pattern.compile("(?<=[a-z])(?=[A-Z]([a-zA-Z]|[0-9]))");
    private static final Pattern SPLIT_ACRONYMS = Pattern.compile("([A-Z]+)([A-Z][a-z])");
    private static final Pattern SPLIT_IN_NUMBERS = Pattern.compile("([0-9])([a-zA-Z])");
    private static final Pattern COLLAPSE_WHITESPACE = Pattern.compile(" +");

    private final String[] parts;
    private final Convention convention;
    private volatile String valueCache;

    private Name(String[] parts, Convention convention) {
        this.parts = Objects.requireNonNull(parts, "parts");
        if (parts.length == 1 && parts[0].isEmpty()) {
            throw new IllegalArgumentException("invalid empty name");
        }
        this.convention = Objects.requireNonNull(convention, "convention");
        parts[0] = convention.processFirst(parts[0]);
        for (int idx = 1; idx < parts.length; ++idx) {
            parts[idx] = convention.processRest(parts[idx]);
        }
    }

    public Convention convention() {
        return convention;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Name name)) {
            return false;
        }
        if (convention != name.convention) {
            return false;
        }
        return Arrays.equals(parts, name.parts);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + Arrays.hashCode(parts);
        hashCode = 31 * hashCode + convention.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        if (valueCache == null) {
            valueCache = String.join(convention.delimiter(), parts);
        }
        return valueCache;
    }

    public Name toPascalCase() {
        if (convention == Convention.PASCAL_CASE) {
            return this;
        }
        return new Name(parts.clone(), Convention.PASCAL_CASE);
    }

    public Name toCamelCase() {
        if (convention == Convention.CAMEL_CASE) {
            return this;
        }
        return new Name(parts.clone(), Convention.CAMEL_CASE);
    }

    public Name toScreamCase() {
        if (convention == Convention.SCREAM_CASE) {
            return this;
        }
        return new Name(parts.clone(), Convention.SCREAM_CASE);
    }

    public Name toNameConvention(Convention newKind) {
        if (convention == newKind) {
            return this;
        }
        return new Name(parts.clone(), newKind);
    }

    public Name concat(Name other) {
        var newParts = new String[parts.length + other.parts.length];
        System.arraycopy(parts, 0, newParts, 0, parts.length);
        System.arraycopy(other.parts, 0, newParts, parts.length, other.parts.length);
        return new Name(newParts, convention);
    }

    /**
     * Naively transforms the name to "singular" spelling by removing a leading 's' character.
     */
    public Name toSingularSpelling() {
        // This is a naive algorithm that only removes leading 's'.
        var lastIndex = parts.length - 1;
        var lastPart = parts[lastIndex];
        if (lastPart.endsWith("s")) {
            var newParts = parts.clone();
            // XXX if the lastPart = "s" this will yield "".
            newParts[parts.length - 1] = lastPart.substring(0, lastPart.length() - 1);
            var result = new Name(newParts, convention);
            return result;
        }
        return this;
    }

    /**
     * Naively adds an article to the name, either 'an' or 'a', by checking if the prefix of the name is a vowel.
     */
    public Name prefixWithArticle() {
        // This is a naive algorithm, does not cover "sounds" like
        var firstPart = parts[0];
        var firstChar = firstPart.charAt(0);
        if ("aeiouAEIOU".indexOf(firstChar) != -1) {
            return withPrefix("an");
        }
        return withPrefix("a");
    }

    public Name withPrefix(String prefix) {
        var prefixName = of(prefix, this.convention);
        return prefixName.concat(this);
    }

    public Name withSuffix(String suffix) {
        var suffixName = of(suffix);
        return concat(suffixName);
    }

    public static Name of(String value) {
        return of(value, Convention.UNKNOWN);
    }

    public static Name of(String value, Convention kind) {
        return new Name(splitOnWordBoundaries(value), kind);
    }

    // Adapted from
    // https://github.com/aws/aws-sdk-java-v2/blob/5dd15f74beb6b5e50a22de26355047b80bf170c3/utils/src/main/java/software/amazon/awssdk/utils/internal/CodegenNamingUtils.java#L36
    public static String[] splitOnWordBoundaries(String toSplit) {
        var result = toSplit;

        // All non-alphanumeric characters are spaces
        result = SPLIT_ALPHANUM.matcher(result).replaceAll(" "); // acm-success -> "acm success"

        // If a number has a standalone v in front of it, separate it out (version).
        result = SPLIT_NUMERIC_VERSION.matcher(result).replaceAll("$1 $2$3 "); // TESTv4 -> "TEST v4 " or  TestV4 -> "Test V4 "

        // Add a space between camelCased words
        result = String.join(" ", SPLIT_CAMEL_CASE.split(result)); // AcmSuccess -> // "Acm Success"

        // Add a space after acronyms
        result = SPLIT_ACRONYMS.matcher(result).replaceAll("$1 $2"); // ACMSuccess -> "ACM Success"

        // Add space after a number in the middle of a word
        result = SPLIT_IN_NUMBERS.matcher(result).replaceAll("$1 $2"); // s3ec2 -> "s3 ec2"

        // Remove extra spaces - multiple consecutive ones or those and the beginning/end of words
        result = COLLAPSE_WHITESPACE.matcher(result).replaceAll(" ") // "Foo  Bar" -> "Foo Bar"
                                    .trim(); // " Foo " -> Foo

        return result.split(" ");
    }

    public enum Convention {
        // PascalCase
        PASCAL_CASE {
            @Override
            public String processRest(String name) {
                return StringUtils.capitalize(name.toLowerCase(Locale.US));
            }
        },
        // camelCase
        CAMEL_CASE {
            @Override
            public String processFirst(String name) {
                return name.toLowerCase(Locale.US);
            }

            @Override
            public String processRest(String name) {
                return StringUtils.capitalize(name.toLowerCase(Locale.US));
            }
        },
        // SCREAM_CASE
        SCREAM_CASE {
            @Override
            public String delimiter() {
                return "_";
            }

            @Override
            public String processRest(String name) {
                return name.toUpperCase(Locale.US);
            }
        },
        UNKNOWN {
            @Override
            public String processRest(String name) {
                return name;
            }
        };

        public String delimiter() {
            return "";
        }

        public String processFirst(String name) {
            return processRest(name);
        }

        public abstract String processRest(String name);
    }

}
