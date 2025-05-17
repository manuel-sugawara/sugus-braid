package mx.sugus.braid.plugins.serde.node;

import static mx.sugus.braid.test.PluginTestRunner.TestCase;
import static mx.sugus.braid.test.PluginTestRunner.addTestCasesFromUrl;
import static mx.sugus.braid.test.PluginTestRunner.assertContentEquals;
import static mx.sugus.braid.test.PluginTestRunner.findGotContent;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class NodeSerdePluginTest {

    @ParameterizedTest(name = "[{index}] => {0}")
    @MethodSource("testCases")
    public void runTestCase(TestCase test) {
        test.builder().build();
        var got = test.manifests().stream().flatMap(x -> x.getFiles().stream()).collect(Collectors.toSet());
        for (var expected : test.expectedToContents().keySet()) {
            var found = findExpected(expected, got);
            assertNotNull(found);
            var contents = findGotContent(found, test);
            assertTrue(contents.isPresent());
            assertContentEquals(test.expectedToContents().get(expected), contents.get().trim());
        }
    }

    private Path findExpected(String expected, Set<Path> manifestFiles) {
        return manifestFiles.stream().filter(path -> path.toString().contains(expected)).findFirst().orElse(null);
    }

    public static Collection<TestCase> testCases() {
        return addTestCasesFromUrl(NodeSerdePluginTest.class.getResource("test-cases"));
    }
}
