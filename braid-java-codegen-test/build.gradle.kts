//extra["displayName"] = "Smithy :: Codegen :: Test"
//extra["moduleName"] = "mx.sugus.codegen.test"

val smithyVersion: String by project

buildscript {
    val smithyVersion: String by project

    repositories {
        mavenCentral()
    }
    dependencies {
        "classpath"("software.amazon.smithy:smithy-cli:$smithyVersion")
        "classpath"("software.amazon.smithy:smithy-model:$smithyVersion")
    }
}

plugins {
    val smithyGradleVersion: String by project

    id("software.amazon.smithy").version(smithyGradleVersion)
    `java-library`
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(project(":braid-rt-util"))
    implementation(project(":braid-traits"))
    implementation(project(":braid-core"))
    implementation(project(":braid-data-plugin"))
    implementation(project(":braid-syntax-plugin"))
    implementation(project(":braid-serde-node-plugin"))
    implementation("software.amazon.smithy:smithy-model:$smithyVersion")
}

tasks.withType<JavaCompile> {
    dependsOn(tasks["smithyBuildJar"])
}

java.sourceSets["main"].java {
    srcDirs("model", "$buildDir/smithyprojections/braid-java-codegen-test/source/braid-codegen")
}
