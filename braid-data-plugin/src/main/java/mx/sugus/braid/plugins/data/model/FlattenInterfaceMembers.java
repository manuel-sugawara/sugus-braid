package mx.sugus.braid.plugins.data.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import mx.sugus.braid.core.ImplementsKnowledgeIndex;
import mx.sugus.braid.traits.ImplementsTrait;
import mx.sugus.braid.traits.InterfaceTrait;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.StructureShape;
import software.amazon.smithy.model.transform.ModelTransformer;

/**
 * Copies the members of structures marked with @interface to all structures that implements it.
 */
public final class FlattenInterfaceMembers {

    public Model transform(ModelTransformer transformer, Model model) {
        var replacements = findReplacements(model);
        if (replacements.isEmpty()) {
            return model;
        }
        return transformer.replaceShapes(model, replacements);
    }

    private Set<Shape> findReplacements(Model model) {
        var appliedTraits = model.getAppliedTraits();
        if (!appliedTraits.contains(ImplementsTrait.ID) || !appliedTraits.contains(InterfaceTrait.ID)) {
            return Collections.emptySet();
        }
        var interfaces = model.getStructureShapes()
                              .stream()
                              .filter(shape -> shape.hasTrait(InterfaceTrait.class))
                              // We only record the structure if it has members
                              .filter(shape -> !shape.members().isEmpty())
                              .collect(Collectors.toSet());

        var implementsIndex = ImplementsKnowledgeIndex.of(model);
        var replacements = new HashSet<Shape>();
        for (var parent : interfaces) {
            for (var implementer : implementsIndex.implementers(parent)) {
                var merged = mergeParentFields(parent, implementer);
                if (merged != null) {
                    replacements.add(merged);
                }
            }
        }
        return replacements;
    }

    private StructureShape mergeParentFields(StructureShape parent, StructureShape child) {
        var missing = new HashSet<>(parent.getMemberNames());
        for (var childMemberName : child.getMemberNames()) {
            missing.remove(childMemberName);
        }
        if (missing.isEmpty()) {
            return null;
        }
        var builder = child.toBuilder();
        for (var fieldName : missing) {
            var parentField = parent.getMember(fieldName).orElseThrow();
            builder.addMember(parentField
                                  .toBuilder()
                                  .id(child.toShapeId().withMember(fieldName))
                                  .build());
        }
        return builder.build();
    }

    public static Model transform(Model model) {
        return new FlattenInterfaceMembers().transform(ModelTransformer.create(), model);
    }
}
