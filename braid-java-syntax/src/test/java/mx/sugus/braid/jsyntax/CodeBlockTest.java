package mx.sugus.braid.jsyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.writer.CodeRenderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CodeBlockTest {

    @ParameterizedTest(name = "[{index}] => {0}")
    @MethodSource("testCases")
    public void runTestCase(TestCase testCase) {
        var block = CodeBlock.from(testCase.format, testCase.args);
        assertEquals(testCase.rendered, CodeRenderer.render(block));
    }

    @Test
    public void unknownFormatThrows() {
        assertThrows(IllegalArgumentException.class, () -> CodeBlock.from("$A", Object.class));
    }

    @Test
    public void nonConvertibleToTypeNameArgumentThrows() {
        assertThrows(IllegalArgumentException.class, () -> CodeBlock.from("$T", new ArrayList<String>()));
    }

    @Test
    public void mixedImplicitAndExplicitArgumentsThrows() {
        assertThrows(IllegalArgumentException.class, () -> CodeBlock.from("$1T = $L", List.class, "foo"));
    }

    @Test
    public void nonFormatDollarSignThrows() {
        assertThrows(IllegalArgumentException.class, () -> CodeBlock.from("// $L cost $ 10USD", "foo"));
    }

    @Test
    public void leadingDollarSignThrows() {
        assertThrows(IllegalArgumentException.class, () -> CodeBlock.from("$T = $L$", List.class, "foo"));
    }

    @Test
    public void dollarEscapeWithArgumentsThrows() {
        assertThrows(IllegalArgumentException.class, () -> CodeBlock.from("$1T = $2L $3$", List.class, "foo"));
    }

    @Test
    public void lessThanExpectedExplicitArgumentsThrows() {
        assertThrows(IllegalArgumentException.class, () -> CodeBlock.from("$1T = $2L", String.class));
    }

    @Test
    public void moreThanExpectedExplicitArgumentsThrows() {
        assertThrows(IllegalArgumentException.class, () -> CodeBlock.from("$1T $2L = $2S", String.class, "foo", "bar"));
    }

    @Test
    public void explicitArgumentsIndexedAtZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> CodeBlock.from("$0T $1L = $2S", String.class, "foo", "bar"));
    }

    @Test
    public void nonBlockArgumentForBlockFormatThrows() {
        assertThrows(IllegalArgumentException.class, () -> CodeBlock.from("stream.map(x -> $B).toList()", String.class));

    }


    public static List<TestCase> testCases() {
        return List.of(
            testCase("literal expression")
                .format("1 + 2 * 3")
                .expected("1 + 2 * 3")
                .build()
            , testCase("literal expression")
                .format("1 + 2 * 3")
                .expected("1 + 2 * 3")
                .build()
            , testCase("expression with explicit arguments")
                .format("$$amount = $1L + $2L * $2L")
                .args(1, 2)
                .expected("$amount = 1 + 2 * 2")
                .build()
            , testCase("string support")
                .format("printf($S)")
                .args("hello world")
                .expected("printf(\"hello world\")")
                .build()
            , testCase("string support with escaped chars")
                .format("printf($S)")
                .args("hello\nworld")
                .expected("printf(\"hello\\nworld\")")
                .build()
            , testCase("types are imported")
                .format("$T foo = $L")
                .args(String.class, "null")
                .expected("String foo = null")
                .build()
            , testCase("ambiguous type names are qualified")
                .format("$T<$T<$T>> foo = $L")
                .args(List.class, java.awt.List.class, String.class, "null")
                .expected("List<java.awt.List<String>> foo = null")
                .build()
            , testCase("java.util has higher precedence than java.awt")
                .format("$T<$T<$T>> foo = $L")
                .args(java.awt.List.class, List.class, String.class, "null")
                .expected("java.awt.List<List<String>> foo = null")
                .build()
            , testCase("other ambiguous type names are imported in order of discovery")
                .format("$T<$T<$T>> foo = $L")
                .args(ClassName.from("first", "List"), ClassName.from("second", "List"), String.class, "null")
                .expected("List<second.List<String>> foo = null")
                .build()
            , testCase("CodeBlock can be embedded using $C")
                .format("$T.out.$C")
                .args(System.class, CodeBlock.from("printf($S)", "hello\nworld"))
                .expected("System.out.printf(\"hello\\nworld\")")
                .build()
            , testCase("Dollar sign without arguments works")
                .format("$1T $$bar = $2L")
                .args(List.class, "foo")
                .expected("List $bar = foo")
                .build()
            , testCase("CodeBlock can be embedded using $C")
                .format("stream.map(x -> $B).toList()")
                .args(BodyBuilder.create()
                                 .ifStatement("(x % 2) == 0", b -> b.addStatement("return $S", "foo"))
                                 .addStatement("return $S", "bar")
                                 .build())
                .expected("""
                              stream.map(x -> {
                                  if ((x % 2) == 0) {
                                      return "foo";
                                  }
                                  return "bar";
                              }).toList()""")
                .build()
        );
    }

    static TestCaseBuilder testCase(String name) {
        return new TestCaseBuilder()
            .name(name);
    }

    record TestCase(String name, String format, Object[] args, String rendered) {
        @Override
        public String toString() {
            return name;
        }
    }

    static class TestCaseBuilder {
        String name;
        String format;
        Object[] args;
        String rendered;

        public TestCaseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TestCaseBuilder format(String format) {
            this.format = format;
            return this;
        }

        public TestCaseBuilder args(Object... args) {
            this.args = args;
            return this;
        }

        public TestCaseBuilder expected(String rendered) {
            this.rendered = rendered;
            return this;
        }

        public TestCase build() {
            return new TestCase(name, format, args, rendered);
        }
    }
}