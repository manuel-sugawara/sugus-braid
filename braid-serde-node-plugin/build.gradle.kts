description = "Generates code from Smithy models"
extra["displayName"] = "Smithy :: Codegen Smithy Node Syntax Plugin"
extra["moduleName"] = "mx.sugus.codegen.plugin.serde"

val smithyVersion: String by project

buildscript {
    val smithyVersion: String by project

    repositories {
        mavenCentral()
    }
    dependencies {
        "classpath"("software.amazon.smithy:smithy-cli:$smithyVersion")
    }
}

plugins {
    `java-library`
}


dependencies {
    implementation(project(":braid-core"))
    implementation(project(":braid-traits"))
    implementation(project(":braid-data-plugin"))
    implementation(project(":braid-rt-util"))
    implementation(project(":braid-java-syntax"))
    implementation("software.amazon.smithy:smithy-codegen-core:$smithyVersion")
    implementation("software.amazon.smithy:smithy-model:$smithyVersion")
    testImplementation("org.mockito:mockito-core:3.+")
}
