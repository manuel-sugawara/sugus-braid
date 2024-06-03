package mx.sugus.braid.jsyntax.transforms;

import java.util.List;
import mx.sugus.braid.jsyntax.ClassName;
import mx.sugus.braid.jsyntax.ClassSyntax;
import mx.sugus.braid.jsyntax.CompilationUnit;
import mx.sugus.braid.jsyntax.MethodSyntax;
import mx.sugus.braid.jsyntax.ParameterizedTypeName;
import mx.sugus.braid.jsyntax.writer.ImportableNames;
import org.junit.jupiter.api.Test;

class ImportableNamesTest {

    @Test
    public void test0() {

        var transform = new ImportableNames();
        var unit = compilationUnit();
        var simpleNames = transform.importableNames(unit);
        for (var ximport : unit.imports()) {
            System.out.printf("import: %s\n", ximport);
        }
        System.out.printf("==================== container\n");
        transform.importContainer().simpleNames().forEach((k, v) -> {
            System.out.printf("import: [%s -> %s]\n", k, v);
        });

    }

    static CompilationUnit compilationUnit() {
        return CompilationUnit
            .builder()
            .packageName("com.example")
            .type(ClassSyntax
                      .builder("Foo")
                      .addMethod(MethodSyntax
                                     .builder("noting")
                                     .returns(ClassName.from("org.another", "Bar"))
                                     .build())
                      .addMethod(MethodSyntax
                                     .builder("anotherList2")
                                     .returns(java.awt.List.class)
                                     .build())
                      .addMethod(MethodSyntax
                                     .builder("noting2")
                                     .returns(ParameterizedTypeName.from(List.class, String.class))
                                     .build())
                      .addMethod(MethodSyntax
                                     .builder("noting2")
                                     .returns(void.class)
                                     .build())
                      .addInnerType(ClassSyntax
                                        .builder("Bar")
                                        .addMethod(MethodSyntax
                                                       .builder("toString")
                                                       .returns(String.class)
                                                       .build())
                                        .addMethod(MethodSyntax
                                                       .builder("anotherList")
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

}