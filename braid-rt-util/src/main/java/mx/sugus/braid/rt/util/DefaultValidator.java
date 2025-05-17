package mx.sugus.braid.rt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DefaultValidator implements Validation {

    private final Path path;
    private final List<Event> events;

    public DefaultValidator() {
        this.path = new Path(null, ".");
        events = new ArrayList<>();
    }

    @Override
    public List<Event> events() {
        return events;
    }

    @Override
    public void report(Severity severity, String segment, Supplier<String> message) {
        events.add(new Event(severity, path.with(segment), message.get()));
    }
}
