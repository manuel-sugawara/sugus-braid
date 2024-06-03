description = "Generates code from Smithy models"
extra["displayName"] = "Smithy :: Codegen Smithy Syntax Plugin"
extra["moduleName"] = "mx.sugus.codegen.plugin.java.syntax"

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

dependencies {
    implementation(project(":braid-core"))
    implementation(project(":braid-data-plugin"))
    implementation(project(":braid-rt-util"))
    implementation(project(":braid-java-syntax"))
    implementation("software.amazon.smithy:smithy-codegen-core:$smithyVersion")
    testImplementation("org.mockito:mockito-core:3.+")
}
