package mx.sugus.braid.rt.util;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingValidation implements Validation {
    private static final Validation INSTANCE = new LoggingValidation();
    private final Logger LOG = Logger.getLogger(Validation.class.getName());

    private LoggingValidation() {
    }

    @Override
    public void report(Severity severity, String path, Supplier<String> message) {
        switch (severity) {
            case ERROR -> LOG.log(Level.SEVERE, message.get());
            case WARNING -> LOG.log(Level.WARNING, message.get());
        }
    }

    public static Validation instance() {
        return INSTANCE;
    }
}
