description = "Generates code from Smithy models"
extra["displayName"] = "Smithy :: Codegen Smithy Plugin"
extra["moduleName"] = "mx.sugus.codegen.plugin"

val smithyVersion: String by project

buildscript {
    val smithyVersion: String by project

    repositories {
        mavenCentral()
    }
    dependencies {
        "classpath"("software.amazon.smithy:smithy-cli:$smithyVersion")
        "classpath"("software.amazon.smithy:smithy-model:$smithyVersion")
        "classpath"("software.amazon.smithy:smithy-trait-codegen:$smithyVersion")
    }
}

plugins {
    val smithyGradleVersion: String by project

    id("software.amazon.smithy").version(smithyGradleVersion)
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("software.amazon.smithy:smithy-model:$smithyVersion")
    implementation("software.amazon.smithy:smithy-trait-codegen:$smithyVersion")
    testImplementation("org.mockito:mockito-core:3.+")
}

tasks.withType<JavaCompile> {
    dependsOn(tasks["smithyBuildJar"])
}

java.sourceSets["main"].java {
    srcDirs("model")
}

java.sourceSets["main"].resources {
    srcDirs("$buildDir/smithyprojections/braid-traits/source/trait-codegen")
}
