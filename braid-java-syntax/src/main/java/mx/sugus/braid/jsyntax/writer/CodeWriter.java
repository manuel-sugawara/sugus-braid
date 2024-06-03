package mx.sugus.braid.jsyntax.writer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

/**
 * A code writer that keeps tracks of the indentation level and adds the current one to each line. Indentation level is controlled
 * by calls to {@link #indent()} and {@link #dedent()}.
 */
public class CodeWriter implements AutoCloseable {
    private final Writer writer;
    private final String indentString;
    private int indentLevel = 0;
    private boolean requiresIndent = false;
    private String newlinePrefix = "";
    private boolean indentFollowingLines = false;
    private boolean ensureNewLine = false;

    /**
     * Creates a new code writer using the given writer and the indentSpaces.
     *
     * @param writer       The underlying writer to write code to
     * @param indentSpaces The amount of spaces used to indent the code.
     */
    public CodeWriter(Writer writer, int indentSpaces) {
        this.writer = writer;
        indentString = " ".repeat(indentSpaces);
    }

    /**
     * Creates a new code writer using the given writer using four spaces for indentation.
     *
     * @param writer The underlying writer to write code to
     */
    public CodeWriter(Writer writer) {
        this(writer, 4);
    }

    /**
     * Writes the given value and then adds a new line.
     *
     * @param value The value to be written.
     * @return this instance for chain calling.
     */
    public CodeWriter writeln(String value) {
        writeLines(value);
        newLine();
        return this;
    }

    /**
     * Writes the given value.
     *
     * @param value The value to be written.
     * @return this instance for chain calling.
     */
    public CodeWriter write(String value) {
        writeLines(value);
        return this;
    }

    /**
     * Writes the given value as a string literal value surrounded by double quotes and with special chars escaped.
     *
     * @param value The value to be written.
     * @return this instance for chain calling.
     */
    public CodeWriter writeStringLiteral(String value) {
        writeValue(escapeJavaString(value));
        return this;
    }

    /**
     * Begins a new curly bracket delimited block. Writes the prefix plus an opening curly bracket, then increases the indent
     * level.
     *
     * @param prefix The prefix to add before the open bracket.
     * @return this instance for chain calling.
     */
    public CodeWriter beginControlFlow(String prefix) {
        write(prefix);
        writeln(" {");
        indent();
        return this;
    }

    /**
     * Begins a new next block.
     * <p>
     * Closes a previous block by decreasing the indent level and writing a closing curly bracket, but does not append a new
     * line.
     *
     * @return this instance for chain calling.
     */
    public CodeWriter beginNextControlFlow() {
        dedent();
        write("} ");
        return this;
    }

    /**
     * Creates contiguous next block.
     * <p>
     * Closes a previous block by decreasing the indent level and writing a closing curly bracket followed by the next prefix and
     * the opens a new block by writing an opening curly bracket followed by a new line and increments the indent.
     *
     * @return this instance for chain calling.
     */
    public CodeWriter nextControlFlow(String nextPrefix) {
        dedent();
        write("} ");
        write(nextPrefix);
        writeln(" {");
        indent();
        return this;
    }

    /**
     * Closes a block.
     * <p>
     * Closes a previous block by decreasing the indent level and writing a closing curly bracket followed by a new line.
     *
     * @return this instance for chain calling.
     */
    public CodeWriter endControlFlow() {
        dedent();
        writeln("}");
        return this;
    }

    /**
     * Increments the indentation level.
     *
     * @return this instance for chain calling.
     */
    public CodeWriter indent() {
        indentLevel += 1;
        return this;
    }

    /**
     * Decrements the indentation level.
     *
     * @return this instance for chain calling.
     */
    public CodeWriter dedent() {
        indentLevel -= 1;
        if (indentLevel < 0) {
            throw new RuntimeException("dedent() called without a corresponding indent()");
        }
        ensureNewLine = false;
        return this;
    }

    public CodeWriter separator() {
        append('\n');
        return this;
    }

    /**
     * Ensures that there is a new line written. If one was added before does nothing otherwise add one.
     *
     * @return this instance for chain calling.
     */
    public CodeWriter ensureNewline() {
        ensureNewLine = true;
        return this;
    }

    /**
     * Writes a new line.
     *
     * @return this instance for chain calling.
     */
    public CodeWriter newLine() {
        append('\n');
        requiresIndent = true;
        return this;
    }

    /**
     * Sets a prefix to be added after a new line starts. This prefix is added after the indentation.
     *
     * @param prefix The prefix to be added after each new line.
     * @return this instance for chain calling.
     */
    public CodeWriter linePrefix(String prefix) {
        this.newlinePrefix = prefix;
        return this;
    }

    /**
     * Resets the new line prefix to an empty string.
     *
     * @return this instance for chain calling.
     */
    public CodeWriter resetLinePrefix() {
        this.newlinePrefix = "";
        return this;
    }

    /**
     * Sets a flag to extra indent multi-lines writes after the first line. That is, when a multi-line string is written the first
     * one is written using the current indent level and the following ones get written using indent level + 1,
     *
     * @return this instance for chain calling.
     */
    public CodeWriter indentFollowingLines() {
        this.indentFollowingLines = true;
        return this;
    }

    /**
     * Resets the flag to extra indent multi-lines writes after the first line.
     *
     * @return this instance for chain calling.
     */
    public CodeWriter resetIndentFollowingLines() {
        this.indentFollowingLines = false;
        return this;
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        if (indentLevel != 0) {
            throw new RuntimeException(String.format("close() called while missing %d dedent()'s calls", indentLevel));
        }
    }

    private void append(String value) {
        try {
            writer.append(value);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void append(char value) {
        try {
            writer.append(value);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void writeLines(String value) {
        var start = 0;
        var part = trimTrailingNewLines(value);
        var newLineIndex = part.indexOf('\n');
        if (newLineIndex == -1) {
            writeValue(part);
            return;
        }
        writeValue(part.substring(start, newLineIndex));
        newLine();
        start = newLineIndex + 1;
        newLineIndex = part.indexOf('\n', start);
        if (indentFollowingLines) {
            indent();
        }
        while (newLineIndex != -1) {
            writeValue(part.substring(start, newLineIndex));
            newLine();
            start = newLineIndex + 1;
            newLineIndex = part.indexOf('\n', start);
        }
        var suffix = part.substring(start);
        writeValue(suffix);
        if (indentFollowingLines) {
            dedent();
        }
    }

    private String trimTrailingNewLines(String value) {
        var length = value.length();
        var result = length;
        while (result > 0 && value.charAt(result - 1) == '\n') {
            result -= 1;
        }
        if (result == length) {
            return value;
        }
        return value.substring(0, result);
    }

    private void writeValue(String value) {
        if (!newlinePrefix.isEmpty() || !value.isEmpty()) {
            if (ensureNewLine) {
                newLine();
                ensureNewLine = false;
            }
            ensureIndent();
            append(value);
        }
    }

    private void ensureIndent() {
        if (requiresIndent) {
            writeIndent();
            append(newlinePrefix);
        }
    }

    private void writeIndent() {
        for (var idx = 0; idx < indentLevel; idx++) {
            append(indentString);
        }
        requiresIndent = false;
    }

    // Adapted from https://github.com/square/javapoet/blob/master/src/main/java/com/squareup/javapoet/Util.java
    private static String escapeJavaString(String value) {
        var result = new StringBuilder(value.length() + 2);
        result.append('"');

        for (var idx = 0; idx < value.length(); idx++) {
            var ch = value.charAt(idx);
            switch (ch) {
                case '"':
                    result.append("\\\"");
                    break;
                case '\b':
                    result.append("\\b"); /* \u0008: backspace (BS) */
                    break;
                case '\t':
                    result.append("\\t"); /* \u0009: horizontal tab (HT) */
                    break;
                case '\n':
                    result.append("\\n"); /* \u000a: linefeed (LF) */
                    break;
                case '\f':
                    result.append("\\f"); /* \u000c: form feed (FF) */
                    break;
                case '\r':
                    result.append("\\r"); /* \u000d: carriage return (CR) */
                    break;
                case '\\':
                    result.append("\\\\"); /* \u005c: backslash (\) */
                    break;
                default:
                    result.append(Character.isISOControl(ch) ? String.format("\\u%04x", (int) ch) : ch);
            }
        }

        result.append('"');
        return result.toString();
    }

}