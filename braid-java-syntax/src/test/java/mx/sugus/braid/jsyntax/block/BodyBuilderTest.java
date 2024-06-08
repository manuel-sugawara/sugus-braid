package mx.sugus.braid.jsyntax.block;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import mx.sugus.braid.jsyntax.block.BodyBuilder;
import mx.sugus.braid.jsyntax.Block;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.writer.CodeRenderer;
import mx.sugus.braid.rt.util.BuilderReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class BodyBuilderTest {

    @ParameterizedTest(name = "[{index}] => {0}")
    @MethodSource("testCases")
    public void runTestCase(TestCase testCase) {
        var rendered = CodeRenderer.render(testCase.node);
        assertEquals(testCase.expected, rendered);
    }

    public static List<TestCase> testCases() {
        return List.of(
            testCase("Literal statement")
                .body(b -> b.addStatement("return true"))
                .expected("return true;\n")
                .build()
            , testCase("Consecutive statement")
                .body(b -> b.addStatement("a++").addStatement("return true"))
                .expected("a++;\nreturn true;\n")
                .build()
            , testCase("If with begin/end statement")
                .body(b -> b.beginIfStatement("x == 0")
                            .addStatement("return true")
                            .endIfStatement())
                .expected("""
                              if (x == 0) {
                                  return true;
                              }
                              """)
                .build()
            , testCase("If with begin/else/end statement")
                .body(b -> b.beginIfStatement("$L == 0", "x")
                            .addStatement("return true")
                            .nextElseStatement()
                            .addStatement("return false")
                            .endIfStatement())
                .expected("""
                              if (x == 0) {
                                  return true;
                              } else {
                                  return false;
                              }
                              """)
                .build()
            , testCase("If with begin/else-if/end statement")
                .body(b -> b.beginIfStatement("$L == 0", "x")
                            .addStatement("return true")
                            .nextElseIfStatement("x < 0")
                            .addStatement("return false")
                            .endIfStatement())
                .expected("""
                              if (x == 0) {
                                  return true;
                              } else if (x < 0) {
                                  return false;
                              }
                              """)
                .build()
            , testCase("If with begin/else-if/else-if/end statement")
                .body(b -> b.beginIfStatement("$L == 0", "x")
                            .addStatement("return true")
                            .nextElseIfStatement("x < 0")
                            .addStatement("return false")
                            .nextElseIfStatement("x > 0")
                            .addStatement("return null")
                            .endIfStatement())
                .expected("""
                              if (x == 0) {
                                  return true;
                              } else if (x < 0) {
                                  return false;
                              } else if (x > 0) {
                                  return null;
                              }
                              """)
                .build()
            , testCase("If with begin/else-if/else/end statement")
                .body(b -> b.beginIfStatement("$L == 0", "x")
                            .addStatement("return true")
                            .nextElseIfStatement("x < 0")
                            .addStatement("return false")
                            .nextElseStatement()
                            .addStatement("return null")
                            .endIfStatement())
                .expected("""
                              if (x == 0) {
                                  return true;
                              } else if (x < 0) {
                                  return false;
                              } else {
                                  return null;
                              }
                              """)
                .build()
            , testCase("If functional style")
                .body(b -> b.ifStatement("$L == 0", "x", then -> then.addStatement("return true")))
                .expected("""
                              if (x == 0) {
                                  return true;
                              }
                              """)
                .build()
            , testCase("If/else functional style")
                .body(b -> b.ifStatement("x == 0",
                                         then -> then.addStatement("return true"),
                                         otherwise -> otherwise.addStatement("return false")))
                .expected("""
                              if (x == 0) {
                                  return true;
                              } else {
                                  return false;
                              }
                              """)
                .build()
            , testCase("If/else functional style with arg")
                .body(b -> b.ifStatement("$L == 0", "x",
                                         then -> then.addStatement("return true"),
                                         otherwise -> otherwise.addStatement("return false")))
                .expected("""
                              if (x == 0) {
                                  return true;
                              } else {
                                  return false;
                              }
                              """)
                .build()
            , testCase("For with begin/end")
                .body(b -> b.beginForStatement("int $1L = 0; $1L < 10; $1L++", "x")
                            .addStatement("result += x")
                            .endForStatement())
                .expected("""
                              for (int x = 0; x < 10; x++) {
                                  result += x;
                              }
                              """)
                .build()
            , testCase("For functional")
                .body(b -> b.forStatement("int $1L = 0; $1L < 10; $1L++", "x",
                                          forBody -> forBody.addStatement("result += x")))
                .expected("""
                              for (int x = 0; x < 10; x++) {
                                  result += x;
                              }
                              """)
                .build()
            , testCase("For functional 2")
                .body(b -> b.forStatement("int $1L = 0; $1L < $2L; $1L++", "x", "y",
                                          forBody -> forBody.addStatement("result += x")))
                .expected("""
                              for (int x = 0; x < y; x++) {
                                  result += x;
                              }
                              """)
                .build()
            , testCase("For functional 3")
                .body(b -> b.forStatement(CodeBlock.from("int $1L = 0; $1L < $2L; $1L++", "x", "y"),
                                          forBody -> forBody.addStatement("result += x")))
                .expected("""
                              for (int x = 0; x < y; x++) {
                                  result += x;
                              }
                              """)
                .build()
            , testCase("For functional nested")
                .body(b -> b.forStatement(CodeBlock.from("int $1L = 0; $1L < $2L; $1L++", "x", "y"),
                                          forBody ->
                                              forBody.forStatement("int $1L = 0; $1L < $2L; $1L++", "x2", "y",
                                                                   forNested ->
                                                                       forNested.addStatement("result = x + x2"))))
                .expected("""
                              for (int x = 0; x < y; x++) {
                                  for (int x2 = 0; x2 < y; x2++) {
                                      result = x + x2;
                                  }
                              }
                              """)
                .build()

        );
    }

    static TestCaseBuilder testCase(String name) {
        return new TestCaseBuilder().name(name);
    }

    static class TestCase {
        private final String name;
        private final Block node;
        private final String expected;

        public TestCase(TestCaseBuilder builder) {
            this.name = Objects.requireNonNull(builder.name, "name");
            this.node = Objects.requireNonNull(builder.reference.asPersistent(), "node");
            this.expected = Objects.requireNonNull(builder.expected, "expected");
        }

    }

    static class TestCaseBuilder {
        private String name;
        private BuilderReference<Block, BodyBuilder> reference = BodyBuilder.fromPersistent(null);
        private String expected;

        public TestCaseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TestCaseBuilder body(Consumer<BodyBuilder> b) {
            b.accept(this.reference.asTransient());
            return this;
        }

        public TestCaseBuilder expected(String expected) {
            this.expected = expected;
            return this;
        }

        public TestCase build() {
            return new TestCase(this);
        }
    }

}