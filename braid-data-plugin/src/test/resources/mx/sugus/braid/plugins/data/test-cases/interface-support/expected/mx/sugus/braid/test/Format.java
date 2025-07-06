package mx.sugus.braid.test;

import mx.sugus.braid.rt.util.annotations.Generated;

/**
 * A structure to test interface support
 */
@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface Format {

    /**
     * The format kind
     */
    FormatKind kind();

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    Builder toBuilder();

    interface Builder {

        /**
         * Builds a new instance of {@link Format}
         * 
         * @return The new instance of of {@link Format}
         */
        Format build();
    }
}
