package mx.sugus.braid.plugins.data.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import mx.sugus.braid.core.ImplementsKnowledgeIndex;
import mx.sugus.braid.traits.ImplementsTrait;
import mx.sugus.braid.traits.InterfaceTrait;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.ShapeId;
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
        var replacements = new LinkedHashMap<ShapeId, StructureShape>();
        for (var parent : interfaces) {
            for (var implementer : implementsIndex.recursiveImplementers(parent)) {
                var current = replacements.getOrDefault(implementer.toShapeId(), implementer);
                var merged = mergeParentFields(parent, current);
                if (merged != null) {
                    replacements.put(merged.toShapeId(), merged);
                }
            }
        }
        return new LinkedHashSet<>(replacements.values());
    }

    private StructureShape mergeParentFields(StructureShape parent, StructureShape child) {
        if (child.hasTrait(InterfaceTrait.class)) {
            return null;
        }
        var missing = new LinkedHashSet<>(parent.getMemberNames());
        var changed = new ArrayList<MemberShape>();

        for (var childMemberName : child.getMemberNames()) {
            var childMember = child.getMember(childMemberName).get();
            if (missing.remove(childMemberName)) {
                if (!child.getAllTraits().equals(parent.getMember(childMemberName).get().getAllTraits())) {
                    childMember = childMember.toBuilder().addTraits(parent.getAllTraits().values()).build();
                    changed.add(childMember);
                }
            }
        }
        if (changed.isEmpty() && missing.isEmpty()) {
            return null;
        }
        var builder = child.toBuilder();
        for (var member : changed) {
            builder.addMember(member);
        }
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
