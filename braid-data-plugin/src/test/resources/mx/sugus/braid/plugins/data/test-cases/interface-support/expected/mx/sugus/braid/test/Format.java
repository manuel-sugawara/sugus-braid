package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * <p>A structure to test interface support</p>
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface Format {

    /**
     * <p>The format kind</p>
     */
    FormatKind kind();

    /**
     * Creates a new {@link Builder} to modify a copy of this instance
     */
    Builder toBuilder();

    interface Builder {

        /**
         * Builds a new instance of {@link Format}
         */
        Format build();
    }
}
