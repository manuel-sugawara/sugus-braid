package mx.sugus.braid.jsyntax.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.jsyntax.writer.CodeRenderer;
import mx.sugus.braid.jsyntax.AbstractMethodSyntax;
import mx.sugus.braid.jsyntax.Annotation;
import mx.sugus.braid.jsyntax.ArrayTypeName;
import mx.sugus.braid.jsyntax.CaseClause;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CodeBlock;
import mx.sugus.braid.jsyntax.ConstructorMethodSyntax;
import mx.sugus.braid.jsyntax.DefaultCaseClause;
import mx.sugus.braid.jsyntax.EnumConstant;
import mx.sugus.braid.jsyntax.EnumSyntax;
import mx.sugus.braid.jsyntax.FieldSyntax;
import mx.sugus.braid.jsyntax.InterfaceSyntax;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.Parameter;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.SwitchStatement;
import mx.sugus.braid.jsyntax.SyntaxNode;
import mx.sugus.braid.jsyntax.TypeVariableTypeName;
import mx.sugus.braid.jsyntax.WildcardTypeName;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import mx.sugus.braid.rt.util.annotations.Generated;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CodeRendererTest {

    @ParameterizedTest(name = "[{index}] => {0}")
    @MethodSource("testCases")
    public void runTestCase(TestCase testCase) {
        var rendered = CodeRenderer.render(testCase.node);
        assertEquals(testCase.expected, rendered);
    }

    public static List<TestCase> testCases() {
        return List.of(
            testCase("Simple class")
                .node(ClassSyntax.builder("Incrementer")
                                 .javadoc(JavadocExt.document(
                                     """
                                         Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do"""))
                                 .addAnnotation(Annotation.builder(Generated.class)
                                                          .value(CodeBlock.from("$S", "super-duper"))
                                                          .build())
                                 .addMethod(MethodSyntax.builder("increment")
                                                        .addModifier(Modifier.PUBLIC)
                                                        .returns(void.class)
                                                        .addStatement("value++")
                                                        .build())
                                 .build())
                .expected("""
                              /**
                               * <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do</p>
                               */
                              @Generated("super-duper")
                              class Incrementer {

                                  public void increment() {
                                      value++;
                                  }
                              }
                              """)
                .build()
            , testCase("Parametric class")
                .node(ClassSyntax.builder("Builder")
                                 .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                 .addTypeParam(TypeVariableTypeName.builder()
                                                                   .name("T")
                                                                   .addBound(ClassName.from("ToBuilder"))
                                                                   .build())
                                 .addTypeParam(TypeVariableTypeName.builder()
                                                                   .name("B")
                                                                   .addBound(ClassName.from("Builder"))
                                                                   .build())
                                 .build())
                .expected("""
                              public static class Builder<T extends ToBuilder, B extends Builder> {
                              }
                              """)
                .build()
            , testCase("Simple class with field and constructor")
                .node(ClassSyntax.builder("ValueHolder")
                                 .superClass(ClassName.from("ValueHolderParent"))
                                 .addSuperInterface(ClassName.from("Another"))
                                 .addField(FieldSyntax.builder()
                                                      .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                                                      .addAnnotation(ClassName.from("NotNull"))
                                                      .type(String.class)
                                                      .name("value")
                                                      .build())
                                 .addMethod(ConstructorMethodSyntax.builder()
                                                                   .addParameter(String.class, "value")
                                                                   .addStatement("this.value = value")
                                                                   .build())
                                 .build())
                .expected("""
                              class ValueHolder extends ValueHolderParent implements Another {
                                  @NotNull
                                  private final String value;

                                  ValueHolder(String value) {
                                      this.value = value;
                                  }
                              }
                              """)
                .build()
            , testCase("Simple interface with method and super interface")
                .node(InterfaceSyntax.builder("PrintableList")
                                     .addModifier(Modifier.PUBLIC)
                                     .addSuperInterface(List.class)
                                     .addSuperInterface(ClassName.from("PrintableCollection"))
                                     .addMethod(AbstractMethodSyntax.builder("printThem")
                                                                    .returns(void.class)
                                                                    .addParameter(PrintStream.class, "out")
                                                                    .build())
                                     .build())
                .expected("""
                              public interface PrintableList extends List, PrintableCollection {

                                  void printThem(PrintStream out);
                              }
                              """)
                .build()
            , testCase("Simple Enum")
                .node(EnumSyntax.builder("OneToThree")
                                .addEnumConstant(EnumConstant
                                                     .builder()
                                                     .name("ONE")
                                                     .build())
                                .addEnumConstant(EnumConstant
                                                     .builder()
                                                     .name("TWO")
                                                     .build())
                                .addEnumConstant(EnumConstant
                                                     .builder()
                                                     .name("THREE")
                                                     .build())
                                .build())
                .expected("""
                              enum OneToThree {
                                  ONE,
                                  TWO,
                                  THREE;
                              }
                              """)
                .build()
            , testCase("Simple Enum with method")
                .node(EnumSyntax.builder("OneAndTwo")
                                .addEnumConstant(EnumConstant
                                                     .builder()
                                                     .name("ONE")
                                                     .build())
                                .addEnumConstant(EnumConstant
                                                     .builder()
                                                     .name("TWO")
                                                     .build())
                                .addMethod(MethodSyntax
                                               .builder("toNumber")
                                               .addModifier(Modifier.PUBLIC)
                                               .addParameter(ClassName.from("OneAndTwo"), "value")
                                               .returns(Integer.class)
                                               .addStatement(SwitchStatement
                                                                 .builder()
                                                                 .expression(CodeBlock.from("value"))
                                                                 .addCase(CaseClause
                                                                              .builder()
                                                                              .addLabel(CodeBlock.from("ONE"))
                                                                              .addStatement("return 1")
                                                                              .build())
                                                                 .addCase(CaseClause
                                                                              .builder()
                                                                              .addLabel(CodeBlock.from("TWO"))
                                                                              .addStatement("return 2")
                                                                              .build())
                                                                 .defaultCase(DefaultCaseClause
                                                                                  .builder()
                                                                                  .addStatement("return null")
                                                                                  .build())
                                                                 .build())
                                               .build())
                                .build())
                .expected("""
                              enum OneAndTwo {
                                  ONE,
                                  TWO;
                                                            
                                  public Integer toNumber(OneAndTwo value) {
                                      switch (value) {
                                          case ONE:
                                              return 1;
                                          case TWO:
                                              return 2;
                                          default:
                                              return null;
                                      }
                                  }
                              }
                              """)
                .build()
            , testCase("Simple Enum with body")
                .node(EnumSyntax.builder("OneAndTwo")
                                .addEnumConstant(EnumConstant
                                                     .builder()
                                                     .javadoc("Value one")
                                                     .name("ONE")
                                                     .body(CodeBlock.from("1"))
                                                     .build())
                                .addEnumConstant(EnumConstant
                                                     .builder()
                                                     .javadoc("Value two")
                                                     .name("TWO")
                                                     .body(CodeBlock.from("2"))
                                                     .build())
                                .addField(FieldSyntax
                                              .builder()
                                              .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                                              .type(int.class)
                                              .name("value")
                                              .build())
                                .addMethod(ConstructorMethodSyntax
                                               .builder()
                                               .addParameter(int.class, "value")
                                               .addStatement("this.value = value")
                                               .build())
                                .build())
                .expected("""
                              enum OneAndTwo {
                                  /**
                                   * Value one
                                   */
                                  ONE(1),
                                  /**
                                   * Value two
                                   */
                                  TWO(2);

                                  private final int value;

                                  OneAndTwo(int value) {
                                      this.value = value;
                                  }
                              }
                              """)
                .build()
            , testCase("Simple method")
                .node(MethodSyntax.builder("toString")
                                  .returns(String.class)
                                  .javadoc("Returns a $T representation of the value, or none if {@code null}", String.class)
                                  .addAnnotation(Override.class)
                                  .addModifier(Modifier.PUBLIC)
                                  .ifStatement("value == null", then -> then.addStatement("return $S", "none"))
                                  .addStatement("return value")
                                  .build())
                .expected("""
                              /**
                               * Returns a String representation of the value, or none if {@code null}
                               */
                              @Override
                              public String toString() {
                                  if (value == null) {
                                      return "none";
                                  }
                                  return value;
                              }
                              """)
                .build()
            , testCase("Simple method2")
                .node(MethodSyntax.builder("toString")
                                  .returns(String.class)
                                  .addAnnotation(Override.class)
                                  .addModifier(Modifier.PUBLIC)
                                  .ifStatement("value == null", then -> then.addStatement("return $S", "none")
                                      , otherwise -> otherwise.addStatement("return value"))
                                  .build())
                .expected("""
                              @Override
                              public String toString() {
                                  if (value == null) {
                                      return "none";
                                  } else {
                                      return value;
                                  }
                              }
                              """)
                .build()
            , testCase("Simple method3")
                .node(MethodSyntax.builder("myEmptyMap")
                                  .returns(ParameterizedTypeName.from(Map.class,
                                                                      TypeVariableTypeName.from("K"),
                                                                      TypeVariableTypeName.from("V")))
                                  .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                  .addStatement("return $T.emptyMap()", Collections.class)
                                  .build())
                .expected("""
                              public static Map<K, V> myEmptyMap() {
                                  return Collections.emptyMap();
                              }
                              """)
                .build()
            , testCase("Simple method, two arguments")
                .node(MethodSyntax.builder("add")
                                  .addModifier(Modifier.PUBLIC)
                                  .returns(int.class)
                                  .addParameter(int.class, "left")
                                  .addParameter(int.class, "right")
                                  .addStatement("return left + right")
                                  .build())
                .expected("""
                              public int add(int left, int right) {
                                  return left + right;
                              }
                              """)
                .build()
            , testCase("Simple method with nested array type")
                .node(MethodSyntax.builder("arrayOfArrayOfString")
                                  .returns(ArrayTypeName
                                               .builder()
                                               .componentType(ArrayTypeName
                                                                  .builder()
                                                                  .componentType(ClassName.from(String.class))
                                                                  .build())
                                               .build())
                                  .addStatement("return null")
                                  .build())
                .expected("""
                              String[][] arrayOfArrayOfString() {
                                  return null;
                              }
                              """)
                .build()
            , testCase("Simple method with parametrized type with object wildcard")
                .node(MethodSyntax.builder("listOfObjects")
                                  .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                  .returns(ParameterizedTypeName.from(List.class,
                                                                      WildcardTypeName
                                                                          .builder()
                                                                          .addUpperBound(ClassName.from(Object.class))
                                                                          .build()))
                                  .addStatement("return $T.emptyList()", Collections.class)
                                  .build())
                .expected("""
                              public static List<?> listOfObjects() {
                                  return Collections.emptyList();
                              }
                              """)
                .build()
            , testCase("Parametric method")
                .node(MethodSyntax.builder("castIt")
                                  .returns(TypeVariableTypeName.from("T"))
                                  .addTypeParam(TypeVariableTypeName.from("T"))
                                  .addModifier(Modifier.PUBLIC)
                                  .addParameter(Object.class, "value")
                                  .addStatement("return ($T) value", TypeVariableTypeName.from("T"))
                                  .build())
                .expected("""
                              public <T> T castIt(Object value) {
                                  return (T) value;
                              }
                              """)
                .build()
            , testCase("Method with generic argument")
                .node(MethodSyntax.builder("addUpTo10")
                                  .returns(void.class)
                                  .addModifier(Modifier.PUBLIC)
                                  .addParameter(ParameterizedTypeName.builder()
                                                                     .rawType(ClassName.from(List.class))
                                                                     .addTypeArgument(WildcardTypeName.builder()
                                                                                                      .addLowerBound(ClassName.from(Integer.class))
                                                                                                      .build())
                                                                     .build(), "values")
                                  .body(b -> b.forStatement("int i = 0; i < 10; i++",
                                                            forBody -> forBody.addStatement("values.add(i)")))
                                  .build())
                .expected("""
                              public void addUpTo10(List<? super Integer> values) {
                                  for (int i = 0; i < 10; i++) {
                                      values.add(i);
                                  }
                              }
                              """)
                .build()
            , testCase("Method using varargs")
                .node(MethodSyntax.builder("format")
                                  .returns(String.class)
                                  .addModifier(Modifier.PUBLIC)
                                  .addParameter(String.class, "fmt")
                                  .addParameter(Parameter.builder()
                                                         .name("args")
                                                         .type(Object.class)
                                                         .varargs(true)
                                                         .build())
                                  .addStatement("return $T.format(fmt, args)", String.class)
                                  .build())
                .expected("""
                              public String format(String fmt, Object... args) {
                                  return String.format(fmt, args);
                              }
                              """)

                .build()
            , testCase("Method with Fizz Buzz solution")
                .node(MethodSyntax.builder("fizzBuzz")
                                  .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                  .returns(String.class)
                                  .addParameter(int.class, "value")
                                  .body(b -> {
                                      // JavaPoet style
                                      b.beginControlFlow("if (value % 3 == 0 && value % 5 == 0)");
                                      b.addStatement("return $S", "FizzBuzz");
                                      b.nextControlFlow("else if (value % 3 == 0)");
                                      b.addStatement("return $S", "Fizz");
                                      b.nextControlFlow("else if (value % 5 == 0)");
                                      b.addStatement("return $S", "Buzz");
                                      b.nextControlFlow("else");
                                      b.addStatement("return Integer.toString(value)");
                                      b.endControlFlow();
                                  })
                                  .build())
                .expected("""
                              public static String fizzBuzz(int value) {
                                  if (value % 3 == 0 && value % 5 == 0) {
                                      return "FizzBuzz";
                                  } else if (value % 3 == 0) {
                                      return "Fizz";
                                  } else if (value % 5 == 0) {
                                      return "Buzz";
                                  } else {
                                      return Integer.toString(value);
                                  }
                              }
                              """)
                .build()
            , testCase("Non-trivial types get messy rather quickly")
                .node(MethodSyntax.builder("max")
                                  .javadoc("Same interface as {@link $T.max}", Collections.class)
                                  .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                  .addTypeParam(TypeVariableTypeName
                                                    .builder()
                                                    .name("T")
                                                    .build())
                                  .returns(TypeVariableTypeName
                                               .builder()
                                               .name("T")
                                               .build())
                                  .addParameter(ParameterizedTypeName
                                                    .builder()
                                                    .rawType(ClassName.from(Collection.class))
                                                    .addTypeArgument(WildcardTypeName
                                                                         .builder()
                                                                         .addUpperBound(TypeVariableTypeName
                                                                                            .builder()
                                                                                            .name("T")
                                                                                            .build())
                                                                         .build())
                                                    .build(), "coll")
                                  .addParameter(ParameterizedTypeName
                                                    .builder()
                                                    .rawType(ClassName.from(Comparator.class))
                                                    .addTypeArgument(WildcardTypeName
                                                                         .builder()
                                                                         .addLowerBound(TypeVariableTypeName
                                                                                            .builder()
                                                                                            .name("T")
                                                                                            .build())
                                                                         .build())
                                                    .build(), "comp")
                                  .addStatement("return $T.max(coll comp)", Collections.class)
                                  .build())
                .expected("""
                              /**
                               * Same interface as {@link Collections.max}
                               */
                              public static <T> T max(Collection<? extends T> coll, Comparator<? super T> comp) {
                                  return Collections.max(coll comp);
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
        private final SyntaxNode node;
        private final String expected;

        public TestCase(TestCaseBuilder builder) {
            this.name = Objects.requireNonNull(builder.name, "name");
            this.node = Objects.requireNonNull(builder.node, "node");
            this.expected = Objects.requireNonNull(builder.expected, "expected");
        }

    }

    static class TestCaseBuilder {
        private String name;
        private SyntaxNode node;
        private String expected;

        public TestCaseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TestCaseBuilder node(SyntaxNode node) {
            this.node = node;
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