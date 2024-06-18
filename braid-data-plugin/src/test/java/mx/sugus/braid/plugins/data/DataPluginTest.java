package mx.sugus.braid.plugins.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.build.MockManifest;
import software.amazon.smithy.build.SmithyBuild;
import software.amazon.smithy.build.model.SmithyBuildConfig;
import software.amazon.smithy.model.Model;

public class DataPluginTest {

    private static final Predicate<String> NOT_COMMENT = Predicate.not(Pattern.compile("^ *//.*$").asMatchPredicate());

    @ParameterizedTest(name = "[{index}] => {0}")
    @MethodSource("testCases")
    public void runTestCase(TestCase test) {

        test.builder.build();
        var got = test.manifests.stream().flatMap(x -> x.getFiles().stream()).collect(Collectors.toSet());
        for (var expected : test.expectedToContents.keySet()) {
            var found = findExpected(expected, got);
            assertNotNull(found);
            var contents = findGotContent(found, test);
            assertTrue(contents.isPresent());
            assertEquals(test.expectedToContents.get(expected), contents.get().trim());
        }
    }

    private Optional<String> findGotContent(Path found, TestCase test) {
        for (var manifest : test.manifests) {
            var fileInsideBaseDir = new File(found.toString().replace(manifest.getBaseDir().toString() + "/", "")).toPath();
            var contents = manifest.getFileString(fileInsideBaseDir);
            if (contents.isPresent()) {
                return contents;
            }
        }
        return Optional.empty();
    }

    private Path findExpected(String expected, Set<Path> manifestFiles) {
        return manifestFiles.stream().filter(path -> path.toString().contains(expected)).findFirst().orElse(null);
    }

    public static Collection<TestCase> testCases() {
        return addTestCasesFromUrl(DataPluginTest.class.getResource("test-cases"));
    }

    private static List<TestCase> addTestCasesFromUrl(URL url) {
        if (!url.getProtocol().equals("file")) {
            throw new IllegalArgumentException("Only file URLs are supported: " + url);
        }
        try {
            return addTestCasesFromDirectory(Paths.get(url.toURI()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<TestCase> addTestCasesFromDirectory(Path rootDir) {
        try (Stream<Path> files = Files.walk(rootDir, 1)) {
            var testCases = new ArrayList<TestCase>();
            files.map(Path::toFile)
                 .filter(File::isDirectory)
                 .filter(dir -> new File(dir, "smithy-build.json").exists())
                 .map(DataPluginTest::fromDirectory)
                 .forEach(testCases::add);
            return testCases;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static TestCase fromDirectory(File dir) {
        var configFile = new File(dir, "smithy-build.json");
        var modelDir = new File(dir, "model");
        var model = Model.assembler()
                         .addImport(modelDir.toPath())
                         .discoverModels()
                         .assemble()
                         .unwrap();
        var manifests = new ArrayList<MockManifest>();
        Function<Path, FileManifest> fileManifestFactory = pluginBaseDir -> {
            var fileManifest = new MockManifest(pluginBaseDir);
            manifests.add(fileManifest);
            return fileManifest;
        };
        var config = SmithyBuildConfig.builder()
                                      .load(configFile.toPath())
                                      .outputDirectory("build")
                                      .build();
        var builder = new SmithyBuild()
            .fileManifestFactory(fileManifestFactory)
            .config(config)
            .model(model);
        var javaFiles = new ArrayList<Path>();
        var expectedDir = new File(dir, "expected").toPath();

        try {
            Files.walkFileTree(expectedDir, new JavaFileVisitor(javaFiles));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        var expectedToContents = getExpectedContents(expectedDir, javaFiles);
        return builder()
            .name(dir.toPath().getFileName().toString())
            .builder(builder)
            .manifests(manifests)
            .expectedToContents(expectedToContents)
            .expected(javaFiles)
            .build();
    }

    private static Map<String, String> getExpectedContents(Path base, List<Path> paths) {
        var prefix = base.toString();
        var result = new HashMap<String, String>();
        try {
            for (var path : paths) {
                var relative = path.toString().replace(prefix, "");
                var contents = Files.readString(path);
                result.put(relative, removeSingleLineComments(contents));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return result;
    }

    /**
     * Removes single line comments that can be added to the expected class to annotate specific behavior.
     */
    private static String removeSingleLineComments(String contents) {
        return Arrays.asList(contents.split("\\n")).stream()
                     .filter(NOT_COMMENT)
                     .collect(Collectors.joining("\n"))
                     .trim();
    }

    static TestCaseBuilder builder() {
        return new TestCaseBuilder();
    }

    static class TestCase {
        private final String name;
        private final SmithyBuild builder;
        private final List<MockManifest> manifests;
        private final Map<String, String> expectedToContents;
        private final List<Path> expected;

        TestCase(TestCaseBuilder builder) {
            this.name = Objects.requireNonNull(builder.name, "name");
            this.builder = Objects.requireNonNull(builder.builder, "builder");
            this.manifests = Objects.requireNonNull(builder.manifests, "manifest");
            this.expectedToContents = Objects.requireNonNull(builder.expectedToContents, "expectedToContents");
            this.expected = Objects.requireNonNull(builder.expected, "expected");
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class TestCaseBuilder {
        private String name;
        private SmithyBuild builder;
        private List<MockManifest> manifests;
        private List<Path> expected;
        private Map<String, String> expectedToContents;

        public TestCaseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TestCaseBuilder expected(List<Path> expected) {
            this.expected = expected;
            return this;
        }

        public TestCaseBuilder manifests(List<MockManifest> manifests) {
            this.manifests = manifests;
            return this;
        }

        public TestCaseBuilder builder(SmithyBuild builder) {
            this.builder = builder;
            return this;
        }

        public TestCaseBuilder expectedToContents(Map<String, String> expectedToContents) {
            this.expectedToContents = expectedToContents;
            return this;
        }

        public TestCase build() {
            return new TestCase(this);
        }
    }

    static class JavaFileVisitor extends SimpleFileVisitor<Path> {
        private final List<Path> javaFiles;

        JavaFileVisitor(List<Path> javaFiles) {
            this.javaFiles = javaFiles;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (Files.isRegularFile(file) && file.toString().endsWith(".java")) {
                javaFiles.add(file);
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            // Handle the error and continue
            exc.printStackTrace();
            return FileVisitResult.CONTINUE;
        }
    }
}
