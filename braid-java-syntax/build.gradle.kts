description = "Java poet clone"
extra["displayName"] = "Smithy :: Syntax Java"
extra["moduleName"] = "mx.sugus.syntax.java"

val smithyVersion: String by project

plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "mx.sugus.braid"
            artifactId = "braid-java-syntax"
            version = "0.1"

            from(components["java"])
        }
    }
}

dependencies {
    implementation(project(":braid-rt-util"))
    implementation("software.amazon.smithy:smithy-model:$smithyVersion")
    implementation("org.commonmark:commonmark:0.21.0")
    testImplementation("org.mockito:mockito-core:3.+")
}
