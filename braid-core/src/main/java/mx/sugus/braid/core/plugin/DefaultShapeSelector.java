package mx.sugus.braid.core.plugin;

import java.util.Collection;
import java.util.List;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.loader.Prelude;
import software.amazon.smithy.model.shapes.Shape;

/**
 * Default shape selector, includes all the shapes in the model exluding only prelude shapes.
 */
public final class DefaultShapeSelector implements ShapeSelectorTask {
    private static final ShapeSelectorTask INSTANCE = new DefaultShapeSelector();
    private static final Identifier ID = Identifier.of(DefaultShapeSelector.class);

    private DefaultShapeSelector() {
    }

    @Override
    public Identifier taskId() {
        return ID;
    }

    @Override
    public Collection<Shape> select(Model model) {
        return model.shapes().filter(s -> !Prelude.isPreludeShape(s)).toList();
    }

    /**
     * Returns a default shape selector.
     */
    public static ShapeSelectorTask get() {
        return INSTANCE;
    }
}
