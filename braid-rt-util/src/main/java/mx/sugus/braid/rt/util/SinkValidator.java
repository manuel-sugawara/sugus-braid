package mx.sugus.braid.rt.util;

import java.util.function.Supplier;

/**
 * Sink validation, does nothing with the validation events.
 */
public final class SinkValidator implements Validation {

    private static final Validation INSTANCE = new SinkValidator();

    private SinkValidator() {
    }

    @Override
    public void report(Severity severity, String path, Supplier<String> message) {
    }

    /**
     * Gets the sink validation instance.
     *
     * @return the sink validation instance.
     */
    public static Validation instance() {
        return INSTANCE;
    }
}
