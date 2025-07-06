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

    /**
     * 
     * @return The value of the {@code simpleMember} member
     */
    public Long simpleMember() {
        return this.simpleMember;
    }

    /**
     * 
     * @return The value of the {@code structuresOne} member
     */
    public List<StructureOne> structuresOne() {
        return this.structuresOne;
    }

    /**
     * 
     * @return The value of the {@code structureTwoMap} member
     */
    public Map<String, StructureTwo> structureTwoMap() {
        return this.structureTwoMap;
    }

    /**
     * 
     * @return The value of the {@code structureThreeSet} member
     */
    public Set<StructureThree> structureThreeSet() {
        return this.structureThreeSet;
    }

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
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
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
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
            this.structureTwoMap = CollectionBuilderReference.forUnorderedMap();
            this.structureThreeSet = CollectionBuilderReference.forUnorderedSet();
        }

        Builder(StructureWithAggregates data) {
            this.simpleMember = data.simpleMember;
            this.structuresOne = CollectionBuilderReference.fromPersistentList(data.structuresOne);
            this.structureTwoMap = CollectionBuilderReference.fromPersistentUnorderedMap(data.structureTwoMap);
            this.structureThreeSet = CollectionBuilderReference.fromPersistentUnorderedSet(data.structureThreeSet);
        }

        /**
         * Sets the value for {@code simpleMember}.
         * 
         * @param simpleMember The value to be set.
         * @return This instance for chain calling.
         */
        public Builder simpleMember(Long simpleMember) {
            this.simpleMember = simpleMember;
            return this;
        }

        /**
         * Sets the value for {@code structuresOne}.
         * 
         * @param structuresOne The value to be set.
         * @return This instance for chain calling.
         */
        public Builder structuresOne(List<StructureOne> structuresOne) {
            this.structuresOne.clear();
            this.structuresOne.asTransient().addAll(structuresOne);
            return this;
        }

        /**
         * Adds a single value for {@code structuresOne}.
         */
        public Builder addStructuresOne(StructureOne structuresOne) {
            this.structuresOne.asTransient().add(structuresOne);
            return this;
        }

        /**
         * Sets the value for {@code structureTwoMap}.
         * 
         * @param structureTwoMap The value to be set.
         * @return This instance for chain calling.
         */
        public Builder structureTwoMap(Map<String, StructureTwo> structureTwoMap) {
            this.structureTwoMap.clear();
            this.structureTwoMap.asTransient().putAll(structureTwoMap);
            return this;
        }

        /**
         * Puts a new entry to the {@code structureTwoMap} map with the given key and value.
         * 
         * @param key The key for the new entry
         * @param structureTwoMap The value for the map entry
         * @return This instance for chain calling.
         */
        public Builder putStructureTwoMap(String key, StructureTwo structureTwoMap) {
            this.structureTwoMap.asTransient().put(key, structureTwoMap);
            return this;
        }

        /**
         * Sets the value for {@code structureThreeSet}.
         * 
         * @param structureThreeSet The value to be set.
         * @return This instance for chain calling.
         */
        public Builder structureThreeSet(Set<StructureThree> structureThreeSet) {
            this.structureThreeSet.clear();
            this.structureThreeSet.asTransient().addAll(structureThreeSet);
            return this;
        }

        /**
         * Adds a single value for {@code structureThreeSet}.
         */
        public Builder addStructureThreeSet(StructureThree structureThreeSet) {
            this.structureThreeSet.asTransient().add(structureThreeSet);
            return this;
        }

        /**
         * Returns a new instance of {@link StructureWithAggregates}
         * 
         * @return A new instance of {@link StructureWithAggregates}
         */
        public StructureWithAggregates build() {
            return new StructureWithAggregates(this);
        }
    }
}
