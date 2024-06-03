package mx.sugus.braid.jsyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public final class FormatParser {
    private FormatParser() {
    }

    public static List<FormatterNode> parseFormat(String format, Object... args) {
        var parts = new ArrayList<FormatterNode>();
        FormatterLiteral prev = null;
        for (var part : formatToNodes(format, args)) {
            if (part instanceof FormatterLiteral literal) {
                if (prev == null) {
                    prev = literal;
                } else {
                    prev = combine(prev, literal);
                }
            } else {
                if (prev != null) {
                    parts.add(prev);
                    prev = null;
                }
                parts.add(part);
            }
        }
        if (prev != null) {
            parts.add(prev);
        }
        return parts;
    }

    private static FormatterLiteral combine(FormatterLiteral left, FormatterLiteral right) {
        return FormatterLiteral.builder()
            .value(left.value() + right.value())
            .build();
    }

    public static List<FormatterNode> formatToNodes(String format, Object... args) {
        var tokens = tokenize(format);
        validateTokens(tokens, args);
        List<FormatterNode> parts = new ArrayList<>(tokens.size());
        for (var token : tokens) {
            if (token.value != null) {
                parts.add(FormatterLiteral.builder().value(token.value).build());
            } else {
                parts.addAll(toNode(token, args));
            }
        }
        return parts;
    }

    private static List<FormatterNode> toNode(Token token, Object[] args) {
        var idx = token.getIndex();
        return
            switch (token.format) {
                case '$' -> List.of(FormatterLiteral.builder().value("$").build());
                case 'L' -> List.of(FormatterLiteral.builder().value(args[idx].toString()).build());
                case 'S' -> List.of(FormatterString.builder().value(args[idx].toString()).build());
                case 'T' -> List.of(FormatterTypeName.builder().value(toTypeName(args[idx])).build());
                case 'C' -> expectType(token.format, CodeBlock.class, args[idx]).parts();
                case 'B' -> List.of(FormatterBlock.builder().value(expectType(token.format, Block.class, args[idx])).build());
                default -> throw from("unknown format %s", token.format);
            };
    }

    private static <T> T expectType(Character format, Class<T> kclass, Object obj) {
        if (kclass.isInstance(obj)) {
            return kclass.cast(obj);
        }
        throw from("Format `%s` takes objects of class %s, got instead an instance of class %s",
                   format,
                   kclass.getName(),
                   obj.getClass().getName());
    }

    private static TypeName toTypeName(Object arg) {
        if (arg instanceof Class c) {
            return TypeName.from(c);
        }
        if (arg instanceof TypeName t) {
            return t;
        }
        throw from("Cannot convert object of class `%s` to TypeName", arg.getClass());
    }

    private static void validateTokens(List<Token> tokens, Object[] args) {
        var implicit = 0;
        var explicit = 0;
        var explicitIndexes = new TreeSet<Integer>();
        for (var token : tokens) {
            if (token.value != null) {
                continue;
            }
            if (token.format == null) {
                throw from("leading $ without format, use $$ to add an single dollar sign");
            }
            if (!isKnownFormat(token.format)) {
                throw from("unknown format '%s' starting at %d", token.format, token.position);
            }
            if (token.explicitIndex != null) {
                if (formatTakesArgument(token.format)) {
                    explicitIndexes.add(token.explicitIndex);
                    explicit++;
                } else {
                    throw from("format '%s' at starting at %d with position %d does not take positions",
                               token.format, token.position, token.explicitIndex);

                }
            } else {
                if (formatTakesArgument(token.format)) {
                    implicit++;
                }
            }
        }
        if (explicit != 0 && implicit != 0) {
            throw from("mixed use of implicit and positional arguments (%d %d)", explicit, implicit);
        }
        if (explicit != 0) {
            var explicitSize = explicitIndexes.size();
            if (args.length < explicitSize) {
                throw from("there are more positional indexes than arguments given");
            }
            if (args.length > explicitSize) {
                throw from("there are fewer positional indexes than arguments given");
            }
            if (explicitIndexes.first() != 1) {
                throw from("positional indexes must start at index 1, min given %d",
                           explicitIndexes.first());
            }
        }
    }

    private static IllegalArgumentException from(String fmt, Object... args) {
        return new IllegalArgumentException(String.format(fmt, args));
    }

    private static boolean isKnownFormat(Character format) {
        return format == 'L'
               || format == 'S'
               || format == 'T'
               || format == 'C'
               || format == 'B'
               || format == '$';
    }

    private static boolean formatTakesArgument(Character format) {
        return format == 'L'
               || format == 'S'
               || format == 'T'
               || format == 'B'
               || format == 'C';
    }

    static List<Token> tokenize(String source) {
        var length = source.length();
        var start = 0;
        var tokens = new ArrayList<Token>();
        var implicit = 0;
        while (start < length) {
            var next = nextFormatStart(source, start);
            if (next != -1) {
                if (start != next) {
                    var value = source.substring(start, next);
                    tokens.add(literal(value));
                }
                start = next + 1;
                var unparsedIndex = consumeIndex(source, start);
                Integer position = null;
                if (unparsedIndex != null) {
                    position = Integer.parseInt(unparsedIndex);
                    start += unparsedIndex.length();
                }
                var format = consumeFormat(source, start);
                tokens.add(format(format, next, position, implicit));
                implicit++;
                if (format != null) {
                    start += 1;
                }
            } else {
                var value = source.substring(start);
                tokens.add(literal(value));
                break;
            }
        }
        return tokens;
    }

    private static Character consumeFormat(String source, int start) {
        if (start < source.length()) {
            return source.charAt(start);
        }
        return null;
    }

    private static int nextFormatStart(String source, int start) {
        var length = source.length();
        for (var idx = start; idx < length; idx++) {
            if (source.charAt(idx) == '$') {
                return idx;
            }
        }
        return -1;
    }

    private static String consumeIndex(String source, int start) {
        var length = source.length();
        var index = start;
        while (index < length && Character.isDigit(source.charAt(index))) {
            index++;
        }
        if (index != start) {
            return source.substring(start, index);
        }
        return null;
    }

    static Token literal(String value) {
        return new Token(null, value, null, -1, -1);
    }

    static Token format(Character format, int position, Integer explicit, int implicit) {
        return new Token(format, null, position, implicit, explicit);
    }

    public static class Token {
        private final Character format;
        private final String value;
        private final Integer position;
        private final int implicitIndex;
        private final Integer explicitIndex;

        public Token(Character format, String value, Integer position, int implicitIndex, Integer explicitIndex) {
            this.format = format;
            this.value = value;
            this.position = position;
            this.implicitIndex = implicitIndex;
            this.explicitIndex = explicitIndex;
        }

        public int getIndex() {
            if (explicitIndex != null) {
                return explicitIndex - 1;
            }
            return implicitIndex;
        }

        @Override
        public String toString() {
            if (value != null) {
                return "'" + value + "'";
            }
            if (explicitIndex != null) {
                return "'$" + explicitIndex + format + "'";
            }
            return "'$" + format + "'";
        }
    }
}
