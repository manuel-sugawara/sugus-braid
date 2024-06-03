package mx.sugus.braid.traits;

import software.amazon.smithy.model.FromSourceLocation;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.StringTrait;

public final class JavaTrait extends StringTrait {

    public static final ShapeId ID = ShapeId.from("mx.sugus.braid.traits#java");

    private JavaTrait(String action, FromSourceLocation sourceLocation) {
        super(ID, action, sourceLocation);
    }

    public static final class Provider extends StringTrait.Provider<JavaTrait> {
        public Provider() {
            super(ID, JavaTrait::new);
        }
    }
}
