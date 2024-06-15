package mx.sugus.braid.test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class StructureWithAggregates {
    private final Long simpleMember;
    private final List<StructureOne> structuresOne;
    private final Map<String, StructureTwo> structureTwoMap;
    private final Set<StructureThree> structureThreeSet;
    private int _hashCode = 0;

    private StructureWithAggregates(Builder builder) {
        this.simpleMember = builder.simpleMember;
        this.structuresOne = Objects.requireNonNull(builder.structuresOne.asPersistent(), "structuresOne");
        this.structureTwoMap = Objects.requireNonNull(builder.structureTwoMap.asPersistent(), "structureTwoMap");
        this.structureThreeSet = Objects.requireNonNull(builder.structureThreeSet.asPersistent(), "structureThreeSet");
    }

    public Long simpleMember() {
        return this.simpleMember;
    }

    public List<StructureOne> structuresOne() {
        return this.structuresOne;
    }

    public Map<String, StructureTwo> structureTwoMap() {
        return this.structureTwoMap;
    }

    public Set<StructureThree> structureThreeSet() {
        return this.structureThreeSet;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StructureWithAggregates that = (StructureWithAggregates) obj;
        return Objects.equals(this.simpleMember, that.simpleMember)
            && this.structuresOne.equals(that.structuresOne)
            && this.structureTwoMap.equals(that.structureTwoMap)
            && this.structureThreeSet.equals(that.structureThreeSet);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + (simpleMember != null ? simpleMember.hashCode() : 0);
            hashCode = 31 * hashCode + structuresOne.hashCode();
            hashCode = 31 * hashCode + structureTwoMap.hashCode();
            hashCode = 31 * hashCode + structureThreeSet.hashCode();
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "StructureWithAggregates{"
            + "simpleMember: " + simpleMember
            + ", structuresOne: " + structuresOne
            + ", structureTwoMap: " + structureTwoMap
            + ", structureThreeSet: " + structureThreeSet + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long simpleMember;
        private CollectionBuilderReference<List<StructureOne>> structuresOne;
        private CollectionBuilderReference<Map<String, StructureTwo>> structureTwoMap;
        private CollectionBuilderReference<Set<StructureThree>> structureThreeSet;

        Builder() {
            this.structuresOne = CollectionBuilderReference.forList();
            this.structureTwoMap = CollectionBuilderReference.forOrderedMap();
            this.structureThreeSet = CollectionBuilderReference.forOrderedSet();
        }

        Builder(StructureWithAggregates data) {
            this.simpleMember = data.simpleMember;
            this.structuresOne = CollectionBuilderReference.fromPersistentList(data.structuresOne);
            this.structureTwoMap = CollectionBuilderReference.fromPersistentOrderedMap(data.structureTwoMap);
            this.structureThreeSet = CollectionBuilderReference.fromPersistentOrderedSet(data.structureThreeSet);
        }

        /**
         * <p>Sets the value for <code>simpleMember</code></p>
         */
        public Builder simpleMember(Long simpleMember) {
            this.simpleMember = simpleMember;
            return this;
        }

        /**
         * <p>Sets the value for <code>structuresOne</code></p>
         */
        public Builder structuresOne(List<StructureOne> structuresOne) {
            this.structuresOne.clear();
            this.structuresOne.asTransient().addAll(structuresOne);
            return this;
        }

        /**
         * <p>Adds a single value for <code>structuresOne</code></p>
         */
        public Builder addStructuresOne(StructureOne structuresOne) {
            this.structuresOne.asTransient().add(structuresOne);
            return this;
        }

        /**
         * <p>Sets the value for <code>structureTwoMap</code></p>
         */
        public Builder structureTwoMap(Map<String, StructureTwo> structureTwoMap) {
            this.structureTwoMap.clear();
            this.structureTwoMap.asTransient().putAll(structureTwoMap);
            return this;
        }

        public Builder putStructureTwoMap(String key, StructureTwo structureTwoMap) {
            this.structureTwoMap.asTransient().put(key, structureTwoMap);
            return this;
        }

        /**
         * <p>Sets the value for <code>structureThreeSet</code></p>
         */
        public Builder structureThreeSet(Set<StructureThree> structureThreeSet) {
            this.structureThreeSet.clear();
            this.structureThreeSet.asTransient().addAll(structureThreeSet);
            return this;
        }

        /**
         * <p>Adds a single value for <code>structureThreeSet</code></p>
         */
        public Builder addStructureThreeSet(StructureThree structureThreeSet) {
            this.structureThreeSet.asTransient().add(structureThreeSet);
            return this;
        }

        public StructureWithAggregates build() {
            return new StructureWithAggregates(this);
        }
    }
}
