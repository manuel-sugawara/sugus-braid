package mx.sugus.braid.rt.util;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Records validation events.
 */
public interface Validation {

    void report(Severity severity, String path, Supplier<String> message);

    default List<Event> events() {
        return Collections.emptyList();
    }

    enum Severity {ERROR, WARNING}

    class Event {
        private final Severity severity;
        private final Path path;
        private final String message;

        Event(Severity severity, Path path, String message) {
            this.path = path;
            this.severity = severity;
            this.message = message;
        }
    }

    class Path {
        private final Path parent;
        private final String segment;

        Path(Path parent, String segment) {
            this.segment = segment;
            this.parent = parent;
        }

        public Path with(String segment) {
            return new Path(this, segment);
        }
    }
}
