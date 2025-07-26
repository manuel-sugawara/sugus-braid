description = "Generates code from Smithy models"
extra["displayName"] = "Smithy :: Codegen"
extra["moduleName"] = "mx.sugus.codegen"

val smithyVersion: String by project

plugins {
    `java-library`
    `maven-publish`
}

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

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "mx.sugus.braid"
            artifactId = "braid-core"
            version = "0.1"

            from(components["java"])
        }
    }
}

dependencies {
    implementation(project(":braid-traits"))
    implementation(project(":braid-rt-util"))
    implementation("software.amazon.smithy:smithy-codegen-core:$smithyVersion")
    implementation("software.amazon.smithy:smithy-model:$smithyVersion")
    testImplementation("org.mockito:mockito-core:5.14.2")
}
