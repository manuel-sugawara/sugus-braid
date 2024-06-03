package mx.sugus.braid.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class NameTest {

    @ParameterizedTest
    @MethodSource("testCases")
    public void testCase(TestCase testCase) {
        var name = Name.of(testCase.sourceName, Name.Convention.CAMEL_CASE);
        assertEquals(testCase.expectedPascalCase, name.toPascalCase().toString());
        assertEquals(testCase.expectedCamelCase, name.toCamelCase().toString());
        assertEquals(testCase.expectedScramCase, name.toScreamCase().toString());
        assertEquals(testCase.expectedPascalCase, name.toNameConvention(Name.Convention.PASCAL_CASE).toString());
        assertEquals(testCase.expectedCamelCase, name.toNameConvention(Name.Convention.CAMEL_CASE).toString());
        assertEquals(testCase.expectedScramCase, name.toNameConvention(Name.Convention.SCREAM_CASE).toString());
        assertEquals(testCase.expectSingularSpelling, name.toSingularSpelling().toString());
        assertEquals(testCase.expectPrefixWithArticle, name.prefixWithArticle().toString());
        assertEquals(testCase.expectedWithPrefix, name.withPrefix("FOO").toString());
        assertEquals(testCase.expectedWithSuffix, name.withSuffix("Bar").toString());
    }

    @Test
    public void emptyNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> Name.of(""));
    }

    @Test
    public void defaultNameKeepsWording() {
        var name = Name.of("non_alpha-num@last");
        assertEquals("nonalphanumlast", name.toString());
        assertEquals(Name.Convention.UNKNOWN, name.convention());
    }

    public static Collection<TestCase> testCases() {
        return Arrays.asList(
            TestCase.builder()
                    .sourceName("simple")
                    .expectedPascalCase("Simple")
                    .expectedCamelCase("simple")
                    .expectedScramCase("SIMPLE")
                    .expectSingularSpelling("simple")
                    .expectPrefixWithArticle("aSimple")
                    .expectedWithPrefix("fooSimple")
                    .expectedWithSuffix("simpleBar")
                    .build()
            , TestCase.builder()
                      .sourceName("SimpleAndSimple")
                      .expectedPascalCase("SimpleAndSimple")
                      .expectedCamelCase("simpleAndSimple")
                      .expectedScramCase("SIMPLE_AND_SIMPLE")
                      .expectSingularSpelling("simpleAndSimple")
                      .expectPrefixWithArticle("aSimpleAndSimple")
                      .expectedWithPrefix("fooSimpleAndSimple")
                      .expectedWithSuffix("simpleAndSimpleBar")
                      .build()
            , TestCase.builder()
                      .sourceName("non_alpha-num@last")
                      .expectedPascalCase("NonAlphaNumLast")
                      .expectedCamelCase("nonAlphaNumLast")
                      .expectedScramCase("NON_ALPHA_NUM_LAST")
                      .expectSingularSpelling("nonAlphaNumLast")
                      .expectPrefixWithArticle("aNonAlphaNumLast")
                      .expectedWithPrefix("fooNonAlphaNumLast")
                      .expectedWithSuffix("nonAlphaNumLastBar")
                      .build()
            , TestCase.builder()
                      .sourceName("versionV1")
                      .expectedPascalCase("VersionV1")
                      .expectedCamelCase("versionV1")
                      .expectedScramCase("VERSION_V1")
                      .expectSingularSpelling("versionV1")
                      .expectPrefixWithArticle("aVersionV1")
                      .expectedWithPrefix("fooVersionV1")
                      .expectedWithSuffix("versionV1Bar")
                      .build()
            , TestCase.builder()
                      .sourceName("AMZNAcronym")
                      .expectedPascalCase("AmznAcronym")
                      .expectedCamelCase("amznAcronym")
                      .expectedScramCase("AMZN_ACRONYM")
                      .expectSingularSpelling("amznAcronym")
                      .expectPrefixWithArticle("anAmznAcronym")
                      .expectedWithPrefix("fooAmznAcronym")
                      .expectedWithSuffix("amznAcronymBar")
                      .build()
            , TestCase.builder()
                      .sourceName("s3ec2names")
                      .expectedPascalCase("S3Ec2Names")
                      .expectedCamelCase("s3Ec2Names")
                      .expectedScramCase("S3_EC2_NAMES")
                      .expectSingularSpelling("s3Ec2Name")
                      .expectPrefixWithArticle("aS3Ec2Names")
                      .expectedWithPrefix("fooS3Ec2Names")
                      .expectedWithSuffix("s3Ec2NamesBar")
                      .build()
            , TestCase.builder()
                      .sourceName("camelCase")
                      .expectedPascalCase("CamelCase")
                      .expectedCamelCase("camelCase")
                      .expectedScramCase("CAMEL_CASE")
                      .expectSingularSpelling("camelCase")
                      .expectPrefixWithArticle("aCamelCase")
                      .expectedWithPrefix("fooCamelCase")
                      .expectedWithSuffix("camelCaseBar")
                      .build()
            , TestCase.builder()
                      .sourceName("PascalCase")
                      .expectedPascalCase("PascalCase")
                      .expectedCamelCase("pascalCase")
                      .expectedScramCase("PASCAL_CASE")
                      .expectSingularSpelling("pascalCase")
                      .expectPrefixWithArticle("aPascalCase")
                      .expectedWithPrefix("fooPascalCase")
                      .expectedWithSuffix("pascalCaseBar")
                      .build()
            , TestCase.builder()
                      .sourceName("snake_case")
                      .expectedPascalCase("SnakeCase")
                      .expectedCamelCase("snakeCase")
                      .expectedScramCase("SNAKE_CASE")
                      .expectSingularSpelling("snakeCase")
                      .expectPrefixWithArticle("aSnakeCase")
                      .expectedWithPrefix("fooSnakeCase")
                      .expectedWithSuffix("snakeCaseBar")
                      .build()
            , TestCase.builder()
                      .sourceName("kebab-case")
                      .expectedPascalCase("KebabCase")
                      .expectedCamelCase("kebabCase")
                      .expectedScramCase("KEBAB_CASE")
                      .expectSingularSpelling("kebabCase")
                      .expectPrefixWithArticle("aKebabCase")
                      .expectedWithPrefix("fooKebabCase")
                      .expectedWithSuffix("kebabCaseBar")
                      .build()
            , TestCase.builder()
                      .sourceName("Buses")
                      .expectedPascalCase("Buses")
                      .expectedCamelCase("buses")
                      .expectedScramCase("BUSES")
                      // 😬the algorithm is naive
                      .expectSingularSpelling("buse")
                      .expectPrefixWithArticle("aBuses")
                      .expectedWithPrefix("fooBuses")
                      .expectedWithSuffix("busesBar")
                      .build()
        );
    }


    static class TestCase {
        private final String sourceName;
        private final String expectedCamelCase;
        private final String expectedPascalCase;
        private final String expectedScramCase;
        private final String expectSingularSpelling;
        private final String expectPrefixWithArticle;
        private final String expectedWithPrefix;
        private final String expectedWithSuffix;


        TestCase(TestCaseBuilder builder) {
            this.sourceName = Objects.requireNonNull(builder.sourceName, "sourceName");
            this.expectedCamelCase = Objects.requireNonNull(builder.expectedCamelCase, "expectedCamelCase");
            this.expectedPascalCase = Objects.requireNonNull(builder.expectedPascalCase, "expectedPascalCase");
            this.expectedScramCase = Objects.requireNonNull(builder.expectedScramCase, "expectedScramCase");
            this.expectSingularSpelling = Objects.requireNonNull(builder.expectSingularSpelling, "expectSingularSpelling");
            this.expectPrefixWithArticle = Objects.requireNonNull(builder.expectPrefixWithArticle, "expectPrefixWithArticle");
            this.expectedWithPrefix = Objects.requireNonNull(builder.expectedWithPrefix, "expectedWithPrefix");
            this.expectedWithSuffix = Objects.requireNonNull(builder.expectedWithSuffix, "expectedWithPrefix");
        }

        static TestCaseBuilder builder() {
            return new TestCaseBuilder();
        }
    }

    static class TestCaseBuilder {
        private String sourceName;
        private String expectedCamelCase;
        private String expectedPascalCase;
        private String expectedScramCase;
        private String expectSingularSpelling;
        private String expectPrefixWithArticle;
        private String expectedWithPrefix;
        private String expectedWithSuffix;

        public TestCaseBuilder sourceName(String sourceName) {
            this.sourceName = sourceName;
            return this;
        }

        public TestCaseBuilder expectedCamelCase(String expectedCamelCase) {
            this.expectedCamelCase = expectedCamelCase;
            return this;
        }

        public TestCaseBuilder expectedPascalCase(String expectedPascalCase) {
            this.expectedPascalCase = expectedPascalCase;
            return this;
        }

        public TestCaseBuilder expectedScramCase(String expectedScramCase) {
            this.expectedScramCase = expectedScramCase;
            return this;
        }

        public TestCaseBuilder expectSingularSpelling(String expectSingularSpelling) {
            this.expectSingularSpelling = expectSingularSpelling;
            return this;
        }

        public TestCaseBuilder expectPrefixWithArticle(String expectPrefixWithArticle) {
            this.expectPrefixWithArticle = expectPrefixWithArticle;
            return this;
        }

        public TestCaseBuilder expectedWithPrefix(String expectedWithPrefix) {
            this.expectedWithPrefix = expectedWithPrefix;
            return this;
        }

        public TestCaseBuilder expectedWithSuffix(String expectedWithSuffix) {
            this.expectedWithSuffix = expectedWithSuffix;
            return this;
        }

        public TestCase build() {
            return new TestCase(this);
        }
    }
}