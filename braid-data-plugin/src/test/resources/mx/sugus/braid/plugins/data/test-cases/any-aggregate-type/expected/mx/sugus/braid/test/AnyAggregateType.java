package mx.sugus.braid.test;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * A union of all aggregate types.
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
@SuppressWarnings("unchecked")
public final class AnyAggregateType {
    private final Object variantValue;
    private final VariantTag variantTag;

    private AnyAggregateType(Builder builder) {
        this.variantValue = builder.getValue();
        this.variantTag = builder.variantTag;
    }

    /**
     * structure member
     */
    public AllSimpleTypes structure() {
        if (this.variantTag == VariantTag.STRUCTURE) {
            return (AllSimpleTypes) this.variantValue;
        }
        throw new NoSuchElementException("Union element `structure` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * union member
     */
    public AnySimpleType union() {
        if (this.variantTag == VariantTag.UNION) {
            return (AnySimpleType) this.variantValue;
        }
        throw new NoSuchElementException("Union element `union` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * list member
     */
    public List<AllSimpleTypes> list() {
        if (this.variantTag == VariantTag.LIST) {
            return (List<AllSimpleTypes>) this.variantValue;
        }
        throw new NoSuchElementException("Union element `list` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * map member
     */
    public Map<String, AnySimpleType> map() {
        if (this.variantTag == VariantTag.MAP) {
            return (Map<String, AnySimpleType>) this.variantValue;
        }
        throw new NoSuchElementException("Union element `map` not set, currently set `" + this.variantTag + "`");
    }

    /**
     * Returns the enum value representing which member of this object is populated.
     * <p>
     * This will be {@link Type#UNKNOWN_TO_VERSION} if no members are set.
     * 
     * @return The enum value representing which member of this object is populated
     */
    public VariantTag variantTag() {
        return this.variantTag;
    }

    /**
     * Returns the untyped value of the union.
     * <p>
     * Use {@link #type()} to get the member currently set.
     * 
     * @return The untyped value of the union.
     */
    public Object variantValue() {
        return this.variantValue;
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
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AnyAggregateType)) {
            return false;
        }
        AnyAggregateType that = (AnyAggregateType) other;
        return this.variantTag == that.variantTag && this.variantValue.equals(that.variantValue);
    }

    @Override
    public int hashCode() {
        return this.variantTag.hashCode() + 31 * this.variantValue.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("AnyAggregateType{variantTag: ");
        buf.append(this.variantTag);
        switch (this.variantTag) {
            case STRUCTURE:
                buf.append(", structure: ").append(this.variantValue);
                break;
            case UNION:
                buf.append(", union: ").append(this.variantValue);
                break;
            case LIST:
                buf.append(", list: ").append(this.variantValue);
                break;
            case MAP:
                buf.append(", map: ").append(this.variantValue);
                break;
        }
        return buf.append("}").toString();
    }

    /**
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

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

    public static final class Builder {
        private Object variantValue;
        private VariantTag variantTag;

        Builder() {
            this.variantTag = VariantTag.UNKNOWN_TO_VERSION;
            this.variantValue = null;
        }

        Builder(AnyAggregateType data) {
            this.variantTag = data.variantTag;
            switch (data.variantTag) {
                case STRUCTURE:
                    this.variantValue = AllSimpleTypes.AllSimpleTypesBuilderReference.from(data.structure());
                    break;
                case LIST:
                    this.variantValue = CollectionBuilderReference.fromPersistentList(data.list());
                    break;
                case MAP:
                    this.variantValue = CollectionBuilderReference.fromPersistentOrderedMap(data.map());
                    break;
                default:
                    this.variantValue = data.variantValue;
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
            this.variantValue = union;
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
            tmp.asTransient().addAll(list);
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
            tmp.asTransient().putAll(map);
            return this;
        }

        public Builder putMap(String key, AnySimpleType map) {
            map().asTransient().put(key, map);
            return this;
        }

        Object getValue() {
            switch (this.variantTag) {
                case STRUCTURE:
                    return structure().asPersistent();
                case UNION:
                    return this.variantValue;
                case LIST:
                    return list().asPersistent();
                case MAP:
                    return map().asPersistent();
                default:
                    return this.variantValue;
            }
        }

        public AnyAggregateType build() {
            return new AnyAggregateType(this);
        }
    }
}
