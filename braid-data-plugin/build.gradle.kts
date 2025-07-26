description = "Generates code from Smithy models"
extra["displayName"] = "Smithy :: Codegen Smithy Data Plugin 2"
extra["moduleName"] = "mx.sugus.codegen.plugin.data"

plugins {
    `maven-publish`
}


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
    implementation(project(":braid-traits"))
    implementation(project(":braid-rt-util"))
    implementation(project(":braid-java-syntax"))
    implementation("software.amazon.smithy:smithy-codegen-core:$smithyVersion")
    implementation("software.amazon.smithy:smithy-model:$smithyVersion")
    testImplementation(project(":braid-test-util"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "mx.sugus.braid"
            artifactId = "braid-data-plugin"
            version = "0.1"

            from(components["java"])
        }
    }
}
