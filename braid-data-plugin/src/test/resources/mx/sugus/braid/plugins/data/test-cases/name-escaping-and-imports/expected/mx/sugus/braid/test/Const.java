package mx.sugus.braid.test;

import java.util.Objects;
import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public final class Const {
    private final String aConst;

    private Const(Builder builder) {
        this.aConst = builder.aConst;
    }

    public String aConst() {
        return this.aConst;
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
     * <p>Creates a new builder</p>
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String aConst;

        Builder() {
        }

        Builder(Const data) {
            this.aConst = data.aConst;
        }

        /**
         * <p>Sets the value for <code>aConst</code></p>
         */
        public Builder aConst(String aConst) {
            this.aConst = aConst;
            return this;
        }

        public Const build() {
            return new Const(this);
        }
    }
}
