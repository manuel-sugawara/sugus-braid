package mx.sugus.braid.core;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
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
import software.amazon.smithy.utils.Pair;

/**
 * A knowledge index that tracks inheritance relationships and polymorphic dispatch patterns in Smithy models.
 *
 * <p>This index analyzes structures that use the custom {@code @implements} trait to model
 * inheritance relationships and interface implementations. It provides utilities for navigating these relationships and setting
 * up polymorphic dispatch tables for code generation.
 *
 * <p>Key capabilities include:
 * <ul>
 *   <li>Tracking which structures implement which interfaces (marked with {@code @interface})</li>
 *   <li>Finding all implementers of a given interface structure</li>
 *   <li>Computing recursive inheritance hierarchies</li>
 *   <li>Building polymorphic dispatch tables based on constant enum members</li>
 * </ul>
 *
 * <p>Polymorphic dispatch is supported through enum members marked with the {@code @const} trait,
 * allowing runtime type discrimination in generated code.
 *
 * @see mx.sugus.braid.traits.ImplementsTrait
 * @see mx.sugus.braid.traits.InterfaceTrait
 * @see mx.sugus.braid.traits.ConstTrait
 */
public final class ImplementsKnowledgeIndex implements KnowledgeIndex {
    private final Map<StructureShape, Set<StructureShape>> shapeToSuperInterfaces;
    private final Map<StructureShape, Set<StructureShape>> shapeToImplementers;
    private final Model model;

    ImplementsKnowledgeIndex(Model model) {
        this.shapeToSuperInterfaces = structureToSuperInterfaces(model);
        this.shapeToImplementers = structureToImplementers(shapeToSuperInterfaces);
        this.model = model;
    }

    /**
     * Returns the set of interface structures that the given structure directly implements.
     *
     * <p>This method returns only the immediate super-interfaces declared through the
     * {@code @implements} trait, not transitive relationships through the inheritance hierarchy.
     *
     * @param shape The structure shape to get super-interfaces for
     * @return A set of structure shapes that this shape directly implements, or empty set if none
     */
    public Set<StructureShape> superInterfaces(StructureShape shape) {
        return shapeToSuperInterfaces.getOrDefault(shape, Collections.emptySet());
    }

    /**
     * Returns the set of structures that directly implement the given interface structure.
     *
     * <p>This method returns only direct implementers, not those that implement through
     * transitive inheritance relationships. For complete inheritance hierarchies, use
     * {@link #recursiveImplementers(StructureShape)}.
     *
     * @param shape The interface structure to get implementers for
     * @return A set of structure shapes that directly implement this interface, or empty set if none
     */
    public Set<StructureShape> implementers(StructureShape shape) {
        return shapeToImplementers.getOrDefault(shape, Collections.emptySet());
    }

    /**
     * Returns all structures that implement the given interface, including transitive relationships.
     *
     * <p>This method performs a recursive traversal to find all structures that implement
     * the given interface either directly or through inheritance from other interfaces. The result includes the target shape
     * itself if it's an interface.
     *
     * @param shape The interface structure to get all implementers for
     * @return A set containing all structures in the inheritance hierarchy that implement this interface
     */
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

    /**
     * Builds a polymorphic dispatch table for the given parent interface structure.
     *
     * <p>This method constructs a mapping from enum member shapes to their corresponding
     * implementer structures, enabling runtime polymorphic dispatch based on constant enum values. The dispatch is based on enum
     * members marked with {@code @const} traits.
     *
     * <p>The dispatch table maps enum members (used as discriminators) to the structure
     * shapes that should be instantiated for each discriminator value. This enables polymorphic deserialization and type-safe
     * runtime dispatch.
     *
     * @param parent The parent interface structure to build dispatch table for
     * @return A map from discriminator enum members to their corresponding implementer structures
     * @throws RuntimeException if no suitable dispatch member is found or if implementers don't have the required constant
     *                          members
     */
    public Map<MemberShape, StructureShape> polymorphicDispatchTable(StructureShape parent) {
        var dispatchMember = polymorphicDispatchMember(parent);
        if (dispatchMember == null) {
            return Map.of();
        }
        var inheritors = implementers(parent);
        var dispatchMemberName = dispatchMember.getMemberName();
        List<Pair<String, Pair<MemberShape, StructureShape>>> allMembers = new ArrayList<>();
        for (var inheritor : inheritors) {
            var member = inheritor.getMember(dispatchMemberName).orElseThrow();
            var refId = member.getTrait(ConstTrait.class).map(ConstTrait::getValue).orElse("");
            var shapeId = ShapeId.from(refId);
            var constMember = model.expectShape(shapeId, MemberShape.class);
            var memberCase = Pair.of(constMember.getMemberName(), Pair.of(member, inheritor));
            allMembers.add(memberCase);
        }

        allMembers.sort(Map.Entry.comparingByKey());
        var result = new LinkedHashMap<MemberShape, StructureShape>();
        for (var member : allMembers) {
            var kvp = member.getValue();
            result.put(kvp.getKey(), kvp.getValue());
        }
        return result;
    }

    /**
     * Identifies the member that can be used for polymorphic dispatch on the given parent structure.
     *
     * <p>This method searches for an enum member in the parent structure where all implementer
     * structures have a corresponding member marked with a {@code @const} trait. This member can then be used as a discriminator
     * for runtime type identification.
     *
     * <p>The method examines all enum-typed members in the parent and checks if every implementer
     * has a constant value for that member. The first such member found is returned as the dispatch member.
     *
     * @param parent The parent interface structure to find dispatch member for
     * @return The member shape that can be used for polymorphic dispatch, or {@code null} if none found
     */
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

    /**
     * Creates or retrieves an ImplementsKnowledgeIndex for the given model.
     *
     * <p>This method uses Smithy's knowledge index caching mechanism to ensure
     * that only one index is created per model, improving performance when the index is accessed multiple times during code
     * generation.
     *
     * @param model The Smithy model to analyze for inheritance relationships
     * @return An ImplementsKnowledgeIndex for the model
     */
    public static ImplementsKnowledgeIndex of(Model model) {
        return model.getKnowledge(ImplementsKnowledgeIndex.class, ImplementsKnowledgeIndex::new);
    }

    private static Map<StructureShape, Set<StructureShape>> structureToImplementers(
        Map<StructureShape, Set<StructureShape>> structureToSuperInterfaces
    ) {
        var result = new LinkedHashMap<StructureShape, Set<StructureShape>>();
        for (var kvp : structureToSuperInterfaces.entrySet()) {
            for (var structure : kvp.getValue()) {
                result.computeIfAbsent(structure, x -> new LinkedHashSet<>()).add(kvp.getKey());
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
