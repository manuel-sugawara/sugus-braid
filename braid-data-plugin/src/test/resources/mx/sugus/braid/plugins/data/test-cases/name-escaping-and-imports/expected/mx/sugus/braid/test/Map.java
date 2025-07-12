package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class Map {
    private final List member;

    private Map(Builder builder) {
        this.member = builder.member;
    }

    /**
     * 
     * @return The value of the {@code member} member
     */
    public List member() {
        return this.member;
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
        Map that = (Map) obj;
        return Objects.equals(this.member, that.member);
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
     * Creates a new builder to create instances of this class.
     * 
     * @return A new builder to create instances of this class.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A class to build instances of Map
     */
    public static final class Builder {
        private List member;

        Builder() {
        }

        Builder(Map data) {
            this.member = data.member;
        }

        /**
         * Sets the value for {@code member}.
         * 
         * @param member The value to be set.
         * @return This instance for chain calling.
         */
        public Builder member(List member) {
            this.member = member;
            return this;
        }

        /**
         * Returns a new instance of {@link Map}
         * 
         * @return A new instance of {@link Map}
         */
        public Map build() {
            return new Map(this);
        }
    }
}
