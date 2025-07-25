package mx.sugus.braid.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import mx.sugus.braid.traits.ImplementsTrait;
import mx.sugus.braid.traits.InterfaceTrait;
import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.SourceLocation;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.StructureShape;

class ImplementsKnowledgeIndexTest {

    @Test
    void testDirectSuperInterfaces() {
        var interfaceShape = StructureShape.builder()
                                           .id("com.example#MyInterface")
                                           .addTrait(new InterfaceTrait())
                                           .addMember("field", ShapeId.from("smithy.api#String"))
                                           .build();

        var implementer = StructureShape.builder()
                                        .id("com.example#MyImplementer")
                                        .addTrait(new ImplementsTrait(List.of("com.example#MyInterface"), SourceLocation.NONE))
                                        .addMember("field", ShapeId.from("smithy.api#String"))
                                        .build();

        var model = Model.builder()
                         .addShape(interfaceShape)
                         .addShape(implementer)
                         .build();

        var index = ImplementsKnowledgeIndex.of(model);

        var superInterfaces = index.superInterfaces(implementer);
        assertEquals(1, superInterfaces.size());
        assertTrue(superInterfaces.contains(interfaceShape));

        // Interface itself should have no super interfaces
        assertTrue(index.superInterfaces(interfaceShape).isEmpty());
    }

    @Test
    void testDirectImplementers() {
        var interfaceShape = StructureShape.builder()
                                           .id("com.example#MyInterface")
                                           .addTrait(new InterfaceTrait())
                                           .addMember("field", ShapeId.from("smithy.api#String"))
                                           .build();

        var implementer = StructureShape.builder()
                                        .id("com.example#MyImplementer")
                                        .addTrait(new ImplementsTrait(List.of("com.example#MyInterface"), SourceLocation.NONE))
                                        .addMember("field", ShapeId.from("smithy.api#String"))
                                        .build();

        var model = Model.builder()
                         .addShape(interfaceShape)
                         .addShape(implementer)
                         .build();

        var index = ImplementsKnowledgeIndex.of(model);

        var implementers = index.implementers(interfaceShape);
        assertEquals(1, implementers.size());
        assertTrue(implementers.contains(implementer));

        // Implementer itself should have no implementers
        assertTrue(index.implementers(implementer).isEmpty());
    }

    @Test
    void testMultipleImplementers() {
        var interfaceShape = StructureShape.builder()
                                           .id("com.example#MyInterface")
                                           .addTrait(new InterfaceTrait())
                                           .addMember("field", ShapeId.from("smithy.api#String"))
                                           .build();

        var implementer1 = StructureShape.builder()
                                         .id("com.example#Implementer1")
                                         .addTrait(new ImplementsTrait(List.of("com.example#MyInterface"), SourceLocation.NONE))
                                         .addMember("field", ShapeId.from("smithy.api#String"))
                                         .build();

        var implementer2 = StructureShape.builder()
                                         .id("com.example#Implementer2")
                                         .addTrait(new ImplementsTrait(List.of("com.example#MyInterface"), SourceLocation.NONE))
                                         .addMember("field", ShapeId.from("smithy.api#String"))
                                         .build();

        var model = Model.builder()
                         .addShape(interfaceShape)
                         .addShape(implementer1)
                         .addShape(implementer2)
                         .build();

        var index = ImplementsKnowledgeIndex.of(model);

        var implementers = index.implementers(interfaceShape);
        assertEquals(2, implementers.size());
        assertTrue(implementers.contains(implementer1));
        assertTrue(implementers.contains(implementer2));
    }

    @Test
    void testMultipleSuperInterfaces() {
        var interface1 = StructureShape.builder()
                                       .id("com.example#Interface1")
                                       .addTrait(new InterfaceTrait())
                                       .addMember("field1", ShapeId.from("smithy.api#String"))
                                       .build();

        var interface2 = StructureShape.builder()
                                       .id("com.example#Interface2")
                                       .addTrait(new InterfaceTrait())
                                       .addMember("field2", ShapeId.from("smithy.api#String"))
                                       .build();

        var implementer = StructureShape.builder()
                                        .id("com.example#MultiImplementer")
                                        .addTrait(new ImplementsTrait(List.of("com.example#Interface1", "com.example#Interface2"
                                        ), SourceLocation.NONE))
                                        .addMember("field1", ShapeId.from("smithy.api#String"))
                                        .addMember("field2", ShapeId.from("smithy.api#String"))
                                        .build();

        var model = Model.builder()
                         .addShape(interface1)
                         .addShape(interface2)
                         .addShape(implementer)
                         .build();

        var index = ImplementsKnowledgeIndex.of(model);

        var superInterfaces = index.superInterfaces(implementer);
        assertEquals(2, superInterfaces.size());
        assertTrue(superInterfaces.contains(interface1));
        assertTrue(superInterfaces.contains(interface2));
    }

    @Test
    void testRecursiveImplementers() {
        var baseInterface = StructureShape.builder()
                                          .id("com.example#BaseInterface")
                                          .addTrait(new InterfaceTrait())
                                          .addMember("baseField", ShapeId.from("smithy.api#String"))
                                          .build();

        var childInterface = StructureShape.builder()
                                           .id("com.example#ChildInterface")
                                           .addTrait(new InterfaceTrait())
                                           .addTrait(new ImplementsTrait(List.of("com.example#BaseInterface"),
                                                                         SourceLocation.NONE))
                                           .addMember("baseField", ShapeId.from("smithy.api#String"))
                                           .addMember("childField", ShapeId.from("smithy.api#String"))
                                           .build();

        var implementer = StructureShape.builder()
                                        .id("com.example#ConcreteImplementer")
                                        .addTrait(new ImplementsTrait(List.of("com.example#ChildInterface"), SourceLocation.NONE))
                                        .addMember("baseField", ShapeId.from("smithy.api#String"))
                                        .addMember("childField", ShapeId.from("smithy.api#String"))
                                        .build();

        var model = Model.assembler()
                         .addShape(baseInterface)
                         .addShape(childInterface)
                         .addShape(implementer)
                         .assemble()
                         .unwrap();

        var index = ImplementsKnowledgeIndex.of(model);

        // Direct implementers should only include direct relationships
        var directImplementers = index.implementers(baseInterface);
        assertEquals(1, directImplementers.size());
        assertTrue(directImplementers.contains(childInterface));

        // Recursive implementers should include all in the hierarchy
        var recursiveImplementers = index.recursiveImplementers(baseInterface);
        assertEquals(3, recursiveImplementers.size()); // includes itself
        assertTrue(recursiveImplementers.contains(baseInterface));
        assertTrue(recursiveImplementers.contains(childInterface));
        assertTrue(recursiveImplementers.contains(implementer));
    }

    @Test
    void testPolymorphicDispatchMemberWithNoValidCandidates() {
        var baseInterface = StructureShape.builder()
                                          .id("com.example#BaseInterface")
                                          .addTrait(new InterfaceTrait())
                                          .addMember("regularField", ShapeId.from("smithy.api#String"))
                                          .build();

        var implementer = StructureShape.builder()
                                        .id("com.example#Implementer")
                                        .addTrait(new ImplementsTrait(List.of("com.example#BaseInterface"), SourceLocation.NONE))
                                        .addMember("regularField", ShapeId.from("smithy.api#String"))
                                        .build();

        var model = Model.assembler()
                         .addShape(baseInterface)
                         .addShape(implementer)
                         .assemble()
                         .unwrap();

        var index = ImplementsKnowledgeIndex.of(model);

        var dispatchMember = index.polymorphicDispatchMember(baseInterface);
        assertNull(dispatchMember);
    }

    @Test
    void testPolymorphicDispatchTableWithNoValidCandidates() {
        var baseInterface = StructureShape.builder()
                                          .id("com.example#BaseInterface")
                                          .addTrait(new InterfaceTrait())
                                          .addMember("regularField", ShapeId.from("smithy.api#String"))
                                          .build();

        var implementer = StructureShape.builder()
                                        .id("com.example#Implementer")
                                        .addTrait(new ImplementsTrait(List.of("com.example#BaseInterface"), SourceLocation.NONE))
                                        .addMember("regularField", ShapeId.from("smithy.api#String"))
                                        .build();

        var model = Model.assembler()
                         .addShape(baseInterface)
                         .addShape(implementer)
                         .assemble()
                         .unwrap();

        var index = ImplementsKnowledgeIndex.of(model);

        // Should return empty dispatch table when no valid enum dispatch member exists
        var dispatchTable = index.polymorphicDispatchTable(baseInterface);
        assertTrue(dispatchTable.isEmpty());
    }

    @Test
    void testNoImplementsTraitReturnsEmptyCollections() {
        var regularStruct = StructureShape.builder()
                                          .id("com.example#RegularStruct")
                                          .addMember("field", ShapeId.from("smithy.api#String"))
                                          .build();

        var model = Model.builder()
                         .addShape(regularStruct)
                         .build();

        var index = ImplementsKnowledgeIndex.of(model);

        assertTrue(index.superInterfaces(regularStruct).isEmpty());
        assertTrue(index.implementers(regularStruct).isEmpty());
        assertEquals(Set.of(regularStruct), index.recursiveImplementers(regularStruct));
    }

    @Test
    void testIndexCaching() {
        var interfaceShape = StructureShape.builder()
                                           .id("com.example#MyInterface")
                                           .addTrait(new InterfaceTrait())
                                           .build();

        var model = Model.assembler()
                         .addShape(interfaceShape)
                         .assemble()
                         .unwrap();

        var index1 = ImplementsKnowledgeIndex.of(model);
        var index2 = ImplementsKnowledgeIndex.of(model);

        // Should return the same cached instance
        assertSame(index1, index2);
    }

    @Test
    void testPolymorphicDispatchMemberWithSimpleInterface() {
        var baseInterface = StructureShape.builder()
                                          .id("com.example#BaseInterface")
                                          .addTrait(new InterfaceTrait())
                                          .addMember("name", ShapeId.from("smithy.api#String"))
                                          .build();

        var implementer = StructureShape.builder()
                                        .id("com.example#Implementer")
                                        .addTrait(new ImplementsTrait(List.of("com.example#BaseInterface"), SourceLocation.NONE))
                                        .addMember("name", ShapeId.from("smithy.api#String"))
                                        .build();
        var model = Model.assembler()
                         .addShape(baseInterface)
                         .addShape(implementer)
                         .assemble()
                         .unwrap();

        var index = ImplementsKnowledgeIndex.of(model);

        // Should return null when no valid dispatch member exists (no enum with const traits)
        var dispatchMember = index.polymorphicDispatchMember(baseInterface);
        assertNull(dispatchMember);
    }
}