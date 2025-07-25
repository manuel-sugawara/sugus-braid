package mx.sugus.braid.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.ListShape;
import software.amazon.smithy.model.shapes.MapShape;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.OperationShape;
import software.amazon.smithy.model.shapes.ResourceShape;
import software.amazon.smithy.model.shapes.ServiceShape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.StringShape;
import software.amazon.smithy.model.shapes.StructureShape;
import software.amazon.smithy.model.shapes.UnionShape;
import software.amazon.smithy.model.traits.SensitiveTrait;

class SensitiveKnowledgeIndexTest {

    @Test
    void testDirectlySensitiveShape() {
        var sensitiveString = StringShape.builder()
                                         .id("com.example#SensitiveString")
                                         .addTrait(new SensitiveTrait())
                                         .build();

        var model = Model.assembler()
                         .addShape(sensitiveString)
                         .assemble()
                         .unwrap();

        var index = SensitiveKnowledgeIndex.of(model);

        assertTrue(index.isSensitive(sensitiveString));
    }

    @Test
    void testNonSensitiveShape() {
        var regularString = StringShape.builder()
                                       .id("com.example#RegularString")
                                       .build();

        var model = Model.assembler()
                         .addShape(regularString)
                         .assemble()
                         .unwrap();

        var index = SensitiveKnowledgeIndex.of(model);

        assertFalse(index.isSensitive(regularString));
    }

    @Test
    void testSensitiveList() {
        var sensitiveString = StringShape.builder()
                                         .id("com.example#SensitiveString")
                                         .addTrait(new SensitiveTrait())
                                         .build();

        var sensitiveList = ListShape.builder()
                                     .id("com.example#SensitiveList")
                                     .member(MemberShape.builder()
                                                        .id("com.example#SensitiveList$member")
                                                        .target(sensitiveString.getId())
                                                        .build())
                                     .build();

        var model = Model.assembler()
                         .addShape(sensitiveString)
                         .addShape(sensitiveList)
                         .assemble()
                         .unwrap();

        var index = SensitiveKnowledgeIndex.of(model);

        assertTrue(index.isSensitive(sensitiveList));
        assertTrue(index.isSensitive(sensitiveString));
    }

    @Test
    void testNonSensitiveList() {
        var regularString = StringShape.builder()
                                       .id("com.example#RegularString")
                                       .build();

        var regularList = ListShape.builder()
                                   .id("com.example#RegularList")
                                   .member(MemberShape.builder()
                                                      .id("com.example#RegularList$member")
                                                      .target(regularString.getId())
                                                      .build())
                                   .build();

        var model = Model.assembler()
                         .addShape(regularString)
                         .addShape(regularList)
                         .assemble()
                         .unwrap();

        var index = SensitiveKnowledgeIndex.of(model);

        assertFalse(index.isSensitive(regularList));
        assertFalse(index.isSensitive(regularString));
    }

    @Test
    void testSensitiveMap() {
        var sensitiveValue = StringShape.builder()
                                        .id("com.example#SensitiveValue")
                                        .addTrait(new SensitiveTrait())
                                        .build();

        var regularKey = StringShape.builder()
                                    .id("com.example#RegularKey")
                                    .build();

        var sensitiveMap = MapShape.builder()
                                   .id("com.example#SensitiveMap")
                                   .key(MemberShape.builder()
                                                   .id("com.example#SensitiveMap$key")
                                                   .target(regularKey.getId())
                                                   .build())
                                   .value(MemberShape.builder()
                                                     .id("com.example#SensitiveMap$value")
                                                     .target(sensitiveValue.getId())
                                                     .build())
                                   .build();

        var model = Model.assembler()
                         .addShape(sensitiveValue)
                         .addShape(regularKey)
                         .addShape(sensitiveMap)
                         .assemble()
                         .unwrap();

        var index = SensitiveKnowledgeIndex.of(model);

        assertTrue(index.isSensitive(sensitiveMap));
        assertTrue(index.isSensitive(sensitiveValue));
        assertFalse(index.isSensitive(regularKey));
    }

    @Test
    void testSensitiveMapWithSensitiveKey() {
        var sensitiveKey = StringShape.builder()
                                      .id("com.example#SensitiveKey")
                                      .addTrait(new SensitiveTrait())
                                      .build();

        var regularValue = StringShape.builder()
                                      .id("com.example#RegularValue")
                                      .build();

        var sensitiveMap = MapShape.builder()
                                   .id("com.example#SensitiveMap")
                                   .key(MemberShape.builder()
                                                   .id("com.example#SensitiveMap$key")
                                                   .target(sensitiveKey.getId())
                                                   .build())
                                   .value(MemberShape.builder()
                                                     .id("com.example#SensitiveMap$value")
                                                     .target(regularValue.getId())
                                                     .build())
                                   .build();

        var model = Model.assembler()
                         .addShape(sensitiveKey)
                         .addShape(regularValue)
                         .addShape(sensitiveMap)
                         .assemble()
                         .unwrap();

        var index = SensitiveKnowledgeIndex.of(model);

        assertTrue(index.isSensitive(sensitiveMap));
        assertTrue(index.isSensitive(sensitiveKey));
        assertFalse(index.isSensitive(regularValue));
    }

    @Test
    void testDirectlySensitiveStructure() {
        var sensitiveStruct = StructureShape.builder()
                                            .id("com.example#SensitiveStruct")
                                            .addTrait(new SensitiveTrait())
                                            .addMember("field", ShapeId.from("smithy.api#String"))
                                            .build();

        var model = Model.assembler()
                         .addShape(sensitiveStruct)
                         .assemble()
                         .unwrap();

        var index = SensitiveKnowledgeIndex.of(model);

        assertTrue(index.isSensitive(sensitiveStruct));
    }

    @Test
    void testDirectlySensitiveUnion() {
        var sensitiveUnion = UnionShape.builder()
                                       .id("com.example#SensitiveUnion")
                                       .addTrait(new SensitiveTrait())
                                       .addMember("variant", ShapeId.from("smithy.api#String"))
                                       .build();

        var model = Model.assembler()
                         .addShape(sensitiveUnion)
                         .assemble()
                         .unwrap();

        var index = SensitiveKnowledgeIndex.of(model);

        assertTrue(index.isSensitive(sensitiveUnion));
    }

    @Test
    void testIndexCaching() {
        var sensitiveString = StringShape.builder()
                                         .id("com.example#SensitiveString")
                                         .addTrait(new SensitiveTrait())
                                         .build();

        var model = Model.assembler()
                         .addShape(sensitiveString)
                         .assemble()
                         .unwrap();

        var index1 = SensitiveKnowledgeIndex.of(model);
        var index2 = SensitiveKnowledgeIndex.of(model);

        // Should return the same cached instance
        assertSame(index1, index2);
    }
}