package mx.sugus.braid.test;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import mx.sugus.braid.rt.util.BuilderReference;
import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>A union of all aggregate types.</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
@SuppressWarnings("unchecked")
public final class AnyAggregateType {
    private final Object value;
    private final Type type;

    private AnyAggregateType(Builder builder) {
        this.value = builder.getValue();
        this.type = builder.type;
    }

    /**
     * <p>structure member</p>
     */
    public AllSimpleTypes structure() {
        if (this.type == Type.STRUCTURE) {
            return (AllSimpleTypes) this.value;
        }
        throw new NoSuchElementException("Union element `structure` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>union member</p>
     */
    public AnySimpleType union() {
        if (this.type == Type.UNION) {
            return (AnySimpleType) this.value;
        }
        throw new NoSuchElementException("Union element `union` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>list member</p>
     */
    public List<AllSimpleTypes> list() {
        if (this.type == Type.LIST) {
            return (List<AllSimpleTypes>) this.value;
        }
        throw new NoSuchElementException("Union element `list` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>map member</p>
     */
    public Map<String, AnySimpleType> map() {
        if (this.type == Type.MAP) {
            return (Map<String, AnySimpleType>) this.value;
        }
        throw new NoSuchElementException("Union element `map` not set, currently set `" + this.type + "`");
    }

    /**
     * <p>Returns an enum value representing which member of this object is populated.</p>
     * <p>This will be {@link Type#UNKNOWN_TO_VERSION} if no members are set.</p>
     */
    public Type type() {
        return this.type;
    }

    /**
     * <p>Returns the untyped value of the union.</p>
     * <p>Use {@link #type()} to get the member currently set.</p>
     */
    public Object value() {
        return this.value;
    }

    /**
     * <p>Returns a new builder to modify a copy of this instance</p>
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
        return this.type == that.type && this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return this.type.hashCode() + 31 * this.value.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("AnyAggregateType{type: ");
        buf.append(this.type);
        switch (this.type) {
            case STRUCTURE:
                buf.append(", structure: ").append(this.value);
                break;
            case UNION:
                buf.append(", union: ").append(this.value);
                break;
            case LIST:
                buf.append(", list: ").append(this.value);
                break;
            case MAP:
                buf.append(", map: ").append(this.value);
                break;
        }
        return buf.append("}").toString();
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public enum Type {
        STRUCTURE("structure"),
        UNION("union"),
        LIST("list"),
        MAP("map"),
        UNKNOWN_TO_VERSION(null);

        private final String value;

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static final class Builder {
        private Object value;
        private Type type;

        Builder() {
            this.type = null;
            this.value = Type.UNKNOWN_TO_VERSION;
        }

        Builder(AnyAggregateType data) {
            this.type = data.type;
            switch (data.type) {
                case STRUCTURE:
                    this.value = AllSimpleTypes.AllSimpleTypesBuilderReference.from(data.structure());
                    break;
                case LIST:
                    this.value = CollectionBuilderReference.fromPersistentList(data.list());
                    break;
                case MAP:
                    this.value = CollectionBuilderReference.fromPersistentOrderedMap(data.map());
                    break;
                default:
                    this.value = data.value;
            }
        }

        /**
         * <p>Sets the value for <code>structure</code></p>
         * <p>structure member</p>
         */
        public Builder structure(AllSimpleTypes structure) {
            structure().setPersistent(structure);
            return this;
        }

        private BuilderReference<AllSimpleTypes, AllSimpleTypes.Builder> structure() {
            if (this.type != Type.STRUCTURE) {
                this.type = Type.STRUCTURE;
                BuilderReference<AllSimpleTypes, AllSimpleTypes.Builder> structure = AllSimpleTypes.AllSimpleTypesBuilderReference.from(null);
                this.value = structure;
                return structure;
            } else {
                return (BuilderReference<AllSimpleTypes, AllSimpleTypes.Builder>) this.value;
            }
        }

        public Builder structure(Consumer<AllSimpleTypes.Builder> mutator) {
            mutator.accept(structure().asTransient());
            return this;
        }

        /**
         * <p>Sets the value for <code>union</code></p>
         * <p>union member</p>
         */
        public Builder union(AnySimpleType union) {
            this.type = Type.UNION;
            this.value = union;
            return this;
        }

        private CollectionBuilderReference<List<AllSimpleTypes>> list() {
            if (this.type != Type.LIST) {
                this.type = Type.LIST;
                CollectionBuilderReference<List<AllSimpleTypes>> list = CollectionBuilderReference.forList();
                this.value = list;
                return list;
            } else {
                return (CollectionBuilderReference<List<AllSimpleTypes>>) this.value;
            }
        }

        /**
         * <p>Sets the value for <code>list</code></p>
         * <p>list member</p>
         */
        public Builder list(List<AllSimpleTypes> list) {
            CollectionBuilderReference<List<AllSimpleTypes>> tmp = list();
            tmp.clear();
            tmp.asTransient().addAll(list);
            return this;
        }

        /**
         * <p>Adds a single value for <code>list</code></p>
         */
        public Builder addList(AllSimpleTypes list) {
            list().asTransient().add(list);
            return this;
        }

        private CollectionBuilderReference<Map<String, AnySimpleType>> map() {
            if (this.type != Type.MAP) {
                this.type = Type.MAP;
                CollectionBuilderReference<Map<String, AnySimpleType>> map = CollectionBuilderReference.forOrderedMap();
                this.value = map;
                return map;
            } else {
                return (CollectionBuilderReference<Map<String, AnySimpleType>>) this.value;
            }
        }

        /**
         * <p>Sets the value for <code>map</code></p>
         * <p>map member</p>
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
            switch (this.type) {
                case STRUCTURE:
                    return structure().asPersistent();
                case UNION:
                    return this.value;
                case LIST:
                    return list().asPersistent();
                case MAP:
                    return map().asPersistent();
                default:
                    return this.value;
            }
        }

        public AnyAggregateType build() {
            return new AnyAggregateType(this);
        }
    }
}
