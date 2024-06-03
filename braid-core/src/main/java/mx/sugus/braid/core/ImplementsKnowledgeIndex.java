package mx.sugus.braid.core;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import mx.sugus.braid.traits.ConstTrait;
import mx.sugus.braid.traits.ImplementsTrait;
import mx.sugus.braid.traits.InterfaceTrait;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.knowledge.KnowledgeIndex;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.shapes.StructureShape;

public final class ImplementsKnowledgeIndex implements KnowledgeIndex {
    private final Map<StructureShape, Set<StructureShape>> shapeToSuperInterfaces;
    private final Map<StructureShape, Set<StructureShape>> shapeToImplementers;
    private final Model model;

    ImplementsKnowledgeIndex(Model model) {
        this.shapeToSuperInterfaces = structureToSuperInterfaces(model);
        this.shapeToImplementers = structureToImplementers(shapeToSuperInterfaces);
        this.model = model;
    }

    public Set<StructureShape> superInterfaces(StructureShape shape) {
        return shapeToSuperInterfaces.getOrDefault(shape, Collections.emptySet());
    }

    public Set<StructureShape> implementers(StructureShape shape) {
        return shapeToImplementers.getOrDefault(shape, Collections.emptySet());
    }

    public Set<StructureShape> recursiveImplementers(StructureShape shape) {
        var inheritors = shapeToImplementers.getOrDefault(shape, Collections.emptySet());
        var recursiveImplementers = new TreeSet<>(inheritors);
        for (var structure : inheritors) {
            if (structure.hasTrait(InterfaceTrait.class)) {
                recursiveImplementers.addAll(recursiveImplementers(structure));
            }
        }
        recursiveImplementers.add(shape);
        return recursiveImplementers;
    }

    public Map<MemberShape, StructureShape> polymorphicDispatchTable(StructureShape parent) {
        var dispatchMember = polymorphicDispatchMember(parent);
        var inheritors = implementers(parent);

        return inheritors.stream()
                         .collect(toMap(x -> x.getMember(dispatchMember.getMemberName()).orElseThrow(),
                                        Function.identity()));
    }

    public MemberShape polymorphicDispatchMember(StructureShape parent) {
        var candidates = polymorphicDispatchCandidates(parent);
        var inheritors = implementers(parent);
        for (var candidate : candidates) {
            if (inheritors.stream().allMatch(inheritor -> isMemberConstant(inheritor, candidate.getMemberName()))) {
                return candidate;
            }
        }
        return null;
    }

    private List<MemberShape> polymorphicDispatchCandidates(StructureShape parent) {
        return parent.members()
                     .stream()
                     .filter(member -> model.expectShape(member.getTarget()).asEnumShape().isPresent())
                     .collect(toList());
    }

    private boolean isMemberConstant(StructureShape shape, String name) {
        return shape.getMember(name).map(x -> x.hasTrait(ConstTrait.class)).orElse(false);
    }

    public static ImplementsKnowledgeIndex of(Model model) {
        return model.getKnowledge(ImplementsKnowledgeIndex.class, ImplementsKnowledgeIndex::new);
    }

    private static Map<StructureShape, Set<StructureShape>> structureToImplementers(
        Map<StructureShape, Set<StructureShape>> structureToSuperInterfaces
    ) {
        var result = new HashMap<StructureShape, Set<StructureShape>>();
        for (var kvp : structureToSuperInterfaces.entrySet()) {
            for (var structure : kvp.getValue()) {
                result.computeIfAbsent(structure, x -> new HashSet<>()).add(kvp.getKey());
            }
        }
        return result;
    }

    private static Map<StructureShape, Set<StructureShape>> structureToSuperInterfaces(Model model) {
        return model.getStructureShapes()
                    .stream()
                    .filter(s -> s.hasTrait(ImplementsTrait.class))
                    .collect(toMap(Function.identity(), s -> superInterfaces(s, model)));
    }

    private static Set<StructureShape> toStructures(List<ShapeId> shapeIds, Model model) {
        return shapeIds.stream()
                       .map(model::expectShape)
                       .map(s -> s.asStructureShape().orElseThrow())
                       .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static Set<StructureShape> superInterfaces(StructureShape shape, Model model) {
        return toStructures(superTypesIds(shape), model);
    }

    private static List<ShapeId> superTypesIds(StructureShape shape) {
        var superTypes = new ArrayList<ShapeId>();
        if (shape.hasTrait(ImplementsTrait.class)) {
            for (var shapeId : shape.expectTrait(ImplementsTrait.class).getValues()) {
                superTypes.add(ShapeId.from(shapeId));
            }
        }
        return superTypes;
    }
}
