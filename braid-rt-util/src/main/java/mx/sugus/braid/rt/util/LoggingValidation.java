package mx.sugus.braid.rt.util;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggingValidation implements Validation {
    private static final Validation INSTANCE = new LoggingValidation();
    private final Logger LOG = Logger.getLogger(Validation.class.getName());

    private final Path path;

    private LoggingValidation() {
        path = new Path(null, ".");
    }

    private LoggingValidation(Path path) {
        this.path = path;
    }

    @Override
    public Validation with(String segment) {
        return new LoggingValidation(this.path.with(segment));
    }

    @Override
    public void report(Severity severity, String segment, Supplier<String> message) {
        switch (severity) {
            case ERROR -> LOG.log(Level.SEVERE, () -> String.format("at `%s` %s", path.with(segment), message.get()));
            case WARNING -> LOG.log(Level.WARNING, () -> String.format("at `%s` %s", path.with(segment), message.get()));
        }
    }

    public static Validation instance() {
        return INSTANCE;
    }
}
