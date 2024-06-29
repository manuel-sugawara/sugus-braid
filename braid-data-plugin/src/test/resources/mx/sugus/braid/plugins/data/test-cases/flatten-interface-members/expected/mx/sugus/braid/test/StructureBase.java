package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface StructureBase {

    String stringValue();

    /**
     * Creates a new {@link Builder} to modify a copy of this instance
     */
    Builder toBuilder();

    interface Builder {

        Builder stringValue(String stringValue);

        /**
         * Builds a new instance of {@link StructureBase}
         */
        StructureBase build();
    }
}
