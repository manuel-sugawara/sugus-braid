description = "Generates code from Smithy models"
extra["displayName"] = "Smithy :: Codegen"
extra["moduleName"] = "mx.sugus.codegen"

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

dependencies {
    implementation(project(":braid-java-syntax"))
    implementation(project(":braid-traits"))
    implementation(project(":braid-rt-util"))
    implementation("software.amazon.smithy:smithy-codegen-core:$smithyVersion")
    implementation("software.amazon.smithy:smithy-model:$smithyVersion")
    testImplementation("org.mockito:mockito-core:3.+")
}
