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
     * <p>The format value</p>
     */
    Object value();
}
