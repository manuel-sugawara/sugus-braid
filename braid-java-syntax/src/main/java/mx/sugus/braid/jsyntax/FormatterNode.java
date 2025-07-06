package mx.sugus.braid.jsyntax;

import mx.sugus.braid.rt.util.annotations.Generated;

@Generated("mx.sugus.braid.plugins.data#DataPlugin")
public interface FormatterNode {

    SyntaxFormatterNodeKind kind();

    /**
     * Returns a new builder to modify a copy of this instance.
     * 
     * @return A new builder to modify a copy of this instance.
     */
    Builder toBuilder();

    interface Builder {

        /**
         * Builds a new instance of {@link FormatterNode}
         * 
         * @return The new instance of of {@link FormatterNode}
         */
        FormatterNode build();
    }
}
