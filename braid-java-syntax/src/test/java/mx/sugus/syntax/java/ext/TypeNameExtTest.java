package mx.sugus.braid.jsyntax.ext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import mx.sugus.braid.jsyntax.ext.TypeNameExt;
import mx.sugus.braid.jsyntax.writer.CodeRenderer;
import org.junit.jupiter.api.Test;

class TypeNameExtTest {

    @Test
    public void testFromUsingArrayType() {
        var result = TypeNameExt.from(String[].class);
        assertEquals("String[]", CodeRenderer.render(result));
    }

    @Test
    public void testFromUsingArray2Type() {
        var result = TypeNameExt.from(String[][].class);
        assertEquals("String[][]", CodeRenderer.render(result));
    }

    @Test
    public void testFromUsingArray3Type() {
        var result = TypeNameExt.from(String[][][].class);
        assertEquals("String[][][]", CodeRenderer.render(result));
    }
}