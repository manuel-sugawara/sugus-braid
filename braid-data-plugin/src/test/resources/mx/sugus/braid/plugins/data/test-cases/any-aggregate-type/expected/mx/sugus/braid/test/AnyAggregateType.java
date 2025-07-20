package mx.sugus.braid.test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * A union of all aggregate types.
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
@SuppressWarnings("unchecked")
public abstract class AnyAggregateType {

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Returns the specific member type.
     * 
     * @return The specific member type
     */
    @SuppressWarnings("unchecked")
    public <T extends AnyAggregateType> T asMember(Class<T> memberType) {
        if (memberType != getClass()) {
            throw new ClassCastException("Member of class: " + getClass().getName() + " cannot be casted to: " + memberType.getName());
        }
        return (T) this;
    }

    /**
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the enum value representing which member of this object is populated.
     * <p>
     * This will be {@link Type#UNKNOWN_TO_VERSION} if no members are set.
     * 
     * @return The enum value representing which member of this object is populated
     */
    public abstract VariantTag variantTag();

    public abstract <T> T variantValue();

    public enum VariantTag {
        STRUCTURE("structure"),
        UNION("union"),
        LIST("list"),
        MAP("map"),
        UNKNOWN_TO_VERSION(null);

        private final String value;

        VariantTag(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * structure member
     */
    @Generated("mx.sugus.braid.plugins.data#DataPlugin")
    public static final class StructureMember extends AnyAggregateType {
        private final AllSimpleTypes structure;

        private StructureMember(AllSimpleTypes structure) {
            this.structure = Objects.requireNonNull(structure, "structure");
        }

        /**
         * structure member
         * 
         * @return The value of the {@code structure} member
         */
        public AllSimpleTypes structure() {
            return this.structure;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.structure;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.STRUCTURE;
        }

        @Override
        public String toString() {
            return "AnyAggregateType{structure: " + structure + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            StructureMember that = (StructureMember) other;
            return this.structure.equals(that.structure);
        }

        @Override
        public int hashCode() {
            return this.structure.hashCode();
        }
    }

    /**
     * union member
     */
    @Generated("mx.sugus.braid.plugins.data#DataPlugin")
    public static final class UnionMember extends AnyAggregateType {
        private final AnySimpleType union;

        private UnionMember(AnySimpleType union) {
            this.union = Objects.requireNonNull(union, "union");
        }

        /**
         * union member
         * 
         * @return The value of the {@code union} member
         */
        public AnySimpleType union() {
            return this.union;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.union;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.UNION;
        }

        @Override
        public String toString() {
            return "AnyAggregateType{union: " + union + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            UnionMember that = (UnionMember) other;
            return this.union.equals(that.union);
        }

        @Override
        public int hashCode() {
            return this.union.hashCode();
        }
    }

    /**
     * list member
     */
    @Generated("mx.sugus.braid.plugins.data#DataPlugin")
    public static final class ListMember extends AnyAggregateType {
        private final List<AllSimpleTypes> list;

        private ListMember(List<AllSimpleTypes> list) {
            this.list = Objects.requireNonNull(list, "list");
        }

        /**
         * list member
         * 
         * @return The value of the {@code list} member
         */
        public List<AllSimpleTypes> list() {
            return this.list;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.list;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.LIST;
        }

        @Override
        public String toString() {
            return "AnyAggregateType{list: " + list + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            ListMember that = (ListMember) other;
            return this.list.equals(that.list);
        }

        @Override
        public int hashCode() {
            return this.list.hashCode();
        }
    }

    /**
     * map member
     */
    @Generated("mx.sugus.braid.plugins.data#DataPlugin")
    public static final class MapMember extends AnyAggregateType {
        private final Map<String, AnySimpleType> map;

        private MapMember(Map<String, AnySimpleType> map) {
            this.map = Objects.requireNonNull(map, "map");
        }

        /**
         * map member
         * 
         * @return The value of the {@code map} member
         */
        public Map<String, AnySimpleType> map() {
            return this.map;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.map;
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.MAP;
        }

        @Override
        public String toString() {
            return "AnyAggregateType{map: " + map + "}";
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            MapMember that = (MapMember) other;
            return this.map.equals(that.map);
        }

        @Override
        public int hashCode() {
            return this.map.hashCode();
        }
    }

    /**
     * Unknown variant type.
     */
    @Generated("mx.sugus.braid.plugins.data#DataPlugin")
    public static final class $UnknownVariant extends AnyAggregateType {
        private final String unknownVariantName;

        private $UnknownVariant(String name) {
            this.unknownVariantName = Objects.requireNonNull(name);
        }

        @Override
        public VariantTag variantTag() {
            return VariantTag.UNKNOWN_TO_VERSION;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T variantValue() {
            return (T) this.unknownVariantName;
        }
    }

    public static final class Builder {
        private Object variantValue;
        private VariantTag variantTag;

        Builder() {
            this.variantTag = VariantTag.UNKNOWN_TO_VERSION;
            this.variantValue = null;
        }

        Builder(AnyAggregateType data) {
            this.variantTag = data.variantTag();
            switch (this.variantTag) {
                case STRUCTURE:
                    this.variantValue = AllSimpleTypes.AllSimpleTypesBuilderReference.from(data.variantValue());
                    break;
                case LIST:
                    this.variantValue = CollectionBuilderReference.fromPersistentList(data.variantValue());
                    break;
                case MAP:
                    this.variantValue = CollectionBuilderReference.fromPersistentOrderedMap(data.variantValue());
                    break;
                default:
                    this.variantValue = data.variantValue();
            }
        }

        /**
         * Sets the value for {@code structure}
         * <p>
         * structure member
         */
        public Builder structure(AllSimpleTypes structure) {
            structure().setPersistent(structure);
            return this;
        }

        private BuilderReference<AllSimpleTypes, AllSimpleTypes.Builder> structure() {
            if (this.variantTag != VariantTag.STRUCTURE) {
                this.variantTag = VariantTag.STRUCTURE;
                BuilderReference<AllSimpleTypes, AllSimpleTypes.Builder> structure = AllSimpleTypes.AllSimpleTypesBuilderReference.from(null);
                this.variantValue = structure;
                return structure;
            } else {
                return (BuilderReference<AllSimpleTypes, AllSimpleTypes.Builder>) this.variantValue;
            }
        }

        public Builder structure(Consumer<AllSimpleTypes.Builder> mutator) {
            mutator.accept(structure().asTransient());
            return this;
        }

        /**
         * Sets the value for {@code union}
         * <p>
         * union member
         */
        public Builder union(AnySimpleType union) {
            this.variantTag = VariantTag.UNION;
            this.variantValue = Objects.requireNonNull(union);
            return this;
        }

        private CollectionBuilderReference<List<AllSimpleTypes>> list() {
            if (this.variantTag != VariantTag.LIST) {
                this.variantTag = VariantTag.LIST;
                CollectionBuilderReference<List<AllSimpleTypes>> list = CollectionBuilderReference.forList();
                this.variantValue = list;
                return list;
            } else {
                return (CollectionBuilderReference<List<AllSimpleTypes>>) this.variantValue;
            }
        }

        /**
         * Sets the value for {@code list}
         * <p>
         * list member
         */
        public Builder list(List<AllSimpleTypes> list) {
            CollectionBuilderReference<List<AllSimpleTypes>> tmp = list();
            tmp.clear();
            tmp.asTransient().addAll(Objects.requireNonNull(list));
            return this;
        }

        /**
         * Adds a single value for {@code list}
         */
        public Builder addList(AllSimpleTypes list) {
            list().asTransient().add(list);
            return this;
        }

        private CollectionBuilderReference<Map<String, AnySimpleType>> map() {
            if (this.variantTag != VariantTag.MAP) {
                this.variantTag = VariantTag.MAP;
                CollectionBuilderReference<Map<String, AnySimpleType>> map = CollectionBuilderReference.forOrderedMap();
                this.variantValue = map;
                return map;
            } else {
                return (CollectionBuilderReference<Map<String, AnySimpleType>>) this.variantValue;
            }
        }

        /**
         * Sets the value for {@code map}
         * <p>
         * map member
         */
        public Builder map(Map<String, AnySimpleType> map) {
            CollectionBuilderReference<Map<String, AnySimpleType>> tmp = map();
            tmp.clear();
            tmp.asTransient().putAll(Objects.requireNonNull(map));
            return this;
        }

        public Builder putMap(String key, AnySimpleType map) {
            map().asTransient().put(key, map);
            return this;
        }

        @SuppressWarnings("unchecked")
        <T> T getValue() {
            switch (this.variantTag) {
                case LIST:
                    return (T) list().asPersistent();
                case MAP:
                    return (T) map().asPersistent();
                default:
                    return (T) this.variantValue;
            }
        }

        public AnyAggregateType build() {
            switch (this.variantTag) {
                case STRUCTURE:
                    return new StructureMember(getValue());
                case UNION:
                    return new UnionMember(getValue());
                case LIST:
                    return new ListMember(getValue());
                case MAP:
                    return new MapMember(getValue());
                default:
                    if (this.variantValue == null) {
                        throw new NullPointerException("no value set");
                    }
                    return new $UnknownVariant((String) this.variantValue);
            }
        }
    }
}
