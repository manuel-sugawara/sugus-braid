package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.CollectionBuilderReference;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class Container {
    private final List list;
    private final String aConst;
    private final java.util.List<List> listOfLists;
    private final java.util.Map<String, Map> mapOfMaps;
    private final ObjectStructure object;
    private final Objects objects;
    private int _hashCode = 0;

    private Container(Builder builder) {
        this.list = builder.list;
        this.aConst = builder.aConst;
        this.listOfLists = java.util.Objects.requireNonNull(builder.listOfLists.asPersistent(), "listOfLists");
        this.mapOfMaps = java.util.Objects.requireNonNull(builder.mapOfMaps.asPersistent(), "mapOfMaps");
        this.object = builder.object;
        this.objects = builder.objects;
    }

    public List list() {
        return this.list;
    }

    public String aConst() {
        return this.aConst;
    }

    public java.util.List<List> listOfLists() {
        return this.listOfLists;
    }

    public java.util.Map<String, Map> mapOfMaps() {
        return this.mapOfMaps;
    }

    public ObjectStructure object() {
        return this.object;
    }

    public Objects objects() {
        return this.objects;
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
        Container that = (Container) obj;
        return java.util.Objects.equals(this.list, that.list)
            && java.util.Objects.equals(this.aConst, that.aConst)
            && this.listOfLists.equals(that.listOfLists)
            && this.mapOfMaps.equals(that.mapOfMaps)
            && java.util.Objects.equals(this.object, that.object)
            && java.util.Objects.equals(this.objects, that.objects);
    }

    @Override
    public int hashCode() {
        if (_hashCode == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + (list != null ? list.hashCode() : 0);
            hashCode = 31 * hashCode + (aConst != null ? aConst.hashCode() : 0);
            hashCode = 31 * hashCode + listOfLists.hashCode();
            hashCode = 31 * hashCode + mapOfMaps.hashCode();
            hashCode = 31 * hashCode + (object != null ? object.hashCode() : 0);
            hashCode = 31 * hashCode + (objects != null ? objects.hashCode() : 0);
            _hashCode = hashCode;
        }
        return _hashCode;
    }

    @Override
    public String toString() {
        return "Container{"
            + "list: " + list
            + ", const: " + aConst
            + ", listOfLists: " + listOfLists
            + ", mapOfMaps: " + mapOfMaps
            + ", object: " + object
            + ", objects: " + objects + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List list;
        private String aConst;
        private CollectionBuilderReference<java.util.List<List>> listOfLists;
        private CollectionBuilderReference<java.util.Map<String, Map>> mapOfMaps;
        private ObjectStructure object;
        private Objects objects;

        Builder() {
            this.listOfLists = CollectionBuilderReference.forList();
            this.mapOfMaps = CollectionBuilderReference.forUnorderedMap();
        }

        Builder(Container data) {
            this.list = data.list;
            this.aConst = data.aConst;
            this.listOfLists = CollectionBuilderReference.fromPersistentList(data.listOfLists);
            this.mapOfMaps = CollectionBuilderReference.fromPersistentUnorderedMap(data.mapOfMaps);
            this.object = data.object;
            this.objects = data.objects;
        }

        /**
         * <p>Sets the value for <code>list</code></p>
         */
        public Builder list(List list) {
            this.list = list;
            return this;
        }

        /**
         * <p>Sets the value for <code>aConst</code></p>
         */
        public Builder aConst(String aConst) {
            this.aConst = aConst;
            return this;
        }

        /**
         * <p>Sets the value for <code>listOfLists</code></p>
         */
        public Builder listOfLists(java.util.List<List> listOfLists) {
            this.listOfLists.clear();
            this.listOfLists.asTransient().addAll(listOfLists);
            return this;
        }

        /**
         * <p>Adds a single value for <code>listOfLists</code></p>
         */
        public Builder addListOfList(List listOfList) {
            this.listOfLists.asTransient().add(listOfList);
            return this;
        }

        /**
         * <p>Sets the value for <code>mapOfMaps</code></p>
         */
        public Builder mapOfMaps(java.util.Map<String, Map> mapOfMaps) {
            this.mapOfMaps.clear();
            this.mapOfMaps.asTransient().putAll(mapOfMaps);
            return this;
        }

        public Builder putMapOfMap(String key, Map mapOfMap) {
            this.mapOfMaps.asTransient().put(key, mapOfMap);
            return this;
        }

        /**
         * <p>Sets the value for <code>object</code></p>
         */
        public Builder object(ObjectStructure object) {
            this.object = object;
            return this;
        }

        /**
         * <p>Sets the value for <code>objects</code></p>
         */
        public Builder objects(Objects objects) {
            this.objects = objects;
            return this;
        }

        public Container build() {
            return new Container(this);
        }
    }
}
