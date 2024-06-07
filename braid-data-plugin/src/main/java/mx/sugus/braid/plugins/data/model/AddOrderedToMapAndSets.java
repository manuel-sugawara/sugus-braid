package mx.sugus.braid.plugins.data.model;

import java.util.HashSet;
import java.util.Set;
import mx.sugus.braid.traits.OrderedTrait;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.traits.UniqueItemsTrait;
import software.amazon.smithy.model.transform.ModelTransformer;

public class AddOrderedToMapAndSets {

    public static Model transform(Model model) {
        ModelTransformer transformer = ModelTransformer.create();
        var replacements = findReplacements(model);
        if (replacements.isEmpty()) {
            return model;
        }
        return transformer.replaceShapes(model, replacements);
    }

    private static Set<Shape> findReplacements(Model model) {
        var result = new HashSet<Shape>();
        for (var shape : model.getListShapes()) {
            if (shape.hasTrait(UniqueItemsTrait.class) && !shape.hasTrait(OrderedTrait.class)) {
                result.add(shape.toBuilder().addTrait(new OrderedTrait()).build());
            }
        }
        for (var shape : model.getMapShapes()) {
            if (!shape.hasTrait(OrderedTrait.class)) {
                result.add(shape.toBuilder().addTrait(new OrderedTrait()).build());
            }
        }
        return result;
    }

}
