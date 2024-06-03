description = "Java poet clone"
extra["displayName"] = "Smithy :: Syntax Java"
extra["moduleName"] = "mx.sugus.syntax.java"

val smithyVersion: String by project

plugins {
    `java-library`
}

repositories {
    mavenLocal()
    mavenCentral()
}


dependencies {
    implementation(project(":braid-rt-util"))
    implementation("software.amazon.smithy:smithy-model:$smithyVersion")
    implementation("org.commonmark:commonmark:0.21.0")
    testImplementation("org.mockito:mockito-core:3.+")
}
