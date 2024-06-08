package mx.sugus.braid.jsyntax.writer;

import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Modifier;
import mx.sugus.braid.jsyntax.writer.CodeWriter;
import mx.sugus.braid.jsyntax.writer.CodeWriterWalkVisitor;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CompilationUnit;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.ext.JavadocExt;
import org.junit.jupiter.api.Test;

class CodeWriterWalkVisitorTest {

    @Test
    public void test0() {
        var stringWriter = new StringWriter();
        var codeWriter = new CodeWriter(stringWriter);
        var writeVisitor = new CodeWriterWalkVisitor(codeWriter, "com.example", Collections.emptyMap());
        compilationUnit().accept(writeVisitor);
        codeWriter.close();
        System.out.printf("============== result:\n%s\n", stringWriter.toString());
    }

    static CompilationUnit compilationUnit() {
        return CompilationUnit
            .builder()
            .packageName("com.example")
            .type(ClassSyntax
                      .builder("Foo")
                      .addMethod(MethodSyntax
                                     .builder("noting")
                                     .javadoc(JavadocExt.document(javadoc()))
                                     .addModifier(Modifier.PUBLIC)
                                     .returns(ClassName.from("org.another", "Bar"))
                                     .build())
                      .addMethod(MethodSyntax
                                     .builder("anotherList2")
                                     .addModifier(Modifier.PRIVATE)
                                     .addStatement("foo = 32 + 15 * bar")
                                     .returns(java.awt.List.class)
                                     .build())
                      .addMethod(MethodSyntax
                                     .builder("noting2")
                                     .addModifier(Modifier.PRIVATE)
                                     .addStatement("foo = 32 +\n15 *\nbar")
                                     .returns(ParameterizedTypeName.from(List.class, ClassName.from(String.class)))
                                     .build())
                      .addMethod(MethodSyntax
                                     .builder("noting2")
                                     .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                     .returns(void.class)
                                     .build())
                      .addInnerType(ClassSyntax
                                        .builder("Bar")
                                        .addMethod(MethodSyntax
                                                       .builder("toString")
                                                       .addAnnotation(Override.class)
                                                       .addModifier(Modifier.PUBLIC)
                                                       .returns(String.class)
                                                       .build())
                                        .addMethod(MethodSyntax
                                                       .builder("anotherList")
                                                       .addParameter(String.class, "foobar")
                                                       .returns(ClassName.from("Baz"))
                                                       .build())
                                        .addInnerType(ClassSyntax
                                                          .builder("Baz")
                                                          .addMethod(MethodSyntax
                                                                         .builder("toString")
                                                                         .returns(String.class)
                                                                         .build())
                                                          .addMethod(MethodSyntax
                                                                         .builder("anotherList")
                                                                         .returns(java.awt.List.class)
                                                                         .build())

                                                          .build())
                                        .build())
                      .build())
            .build();
    }

    static String javadoc() {
        return """
            This is a very nice javadoc comment that includes some
            new lines and also paragraphs.
                        
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt
            ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
            laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
            voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat
            non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.            
            """;
    }

}