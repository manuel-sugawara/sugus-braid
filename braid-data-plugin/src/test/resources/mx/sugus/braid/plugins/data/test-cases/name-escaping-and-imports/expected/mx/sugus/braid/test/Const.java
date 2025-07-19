package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class Const {
    private final String aConst;

    private Const(Builder builder) {
        this.aConst = builder.aConst;
    }

    /**
     * 
     * @return The value of the {@code const} member
     */
    public String aConst() {
        return this.aConst;
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
        Const that = (Const) obj;
        return Objects.equals(this.aConst, that.aConst);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (aConst != null ? aConst.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "Const{"
            + "const: " + aConst + "}";
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
     * A class to build instances of Const
     */
    public static final class Builder {
        private String aConst;

        Builder() {
        }

        Builder(Const data) {
            this.aConst = data.aConst;
        }

        /**
         * Sets the value for {@code const}.
         * 
         * @param aConst The value to be set.
         * @return This instance for chain calling.
         */
        public Builder aConst(String aConst) {
            this.aConst = aConst;
            return this;
        }

        /**
         * Returns a new instance of {@link Const}
         * 
         * @return A new instance of {@link Const}
         */
        public Const build() {
            return new Const(this);
        }
    }
}
