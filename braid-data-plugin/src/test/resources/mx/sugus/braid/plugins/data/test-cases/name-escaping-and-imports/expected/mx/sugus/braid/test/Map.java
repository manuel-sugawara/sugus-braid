package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class Map {
    private final List member;

    private Map(Builder builder) {
        this.member = builder.member;
    }

    public List member() {
        return this.member;
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
        Map that = (Map) obj;
        return java.util.Objects.equals(this.member, that.member);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (member != null ? member.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "Map{"
            + "member: " + member + "}";
    }

    /**
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List member;

        Builder() {
        }

        Builder(Map data) {
            this.member = data.member;
        }

        /**
         * <p>Sets the value for <code>member</code></p>
         */
        public Builder member(List member) {
            this.member = member;
            return this;
        }

        public Map build() {
            return new Map(this);
        }
    }
}
