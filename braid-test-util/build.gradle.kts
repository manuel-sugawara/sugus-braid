description = "Braid test utils"
extra["displayName"] = "Smithy :: Java Test Utils"
extra["moduleName"] = "mx.sugus.braid.test.utils"

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
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "mx.sugus.braid"
            artifactId = "braid-test-util"
            version = "0.1"

            from(components["java"])
        }
    }
}

dependencies {
    implementation("software.amazon.smithy:smithy-codegen-core:$smithyVersion")
    implementation("software.amazon.smithy:smithy-model:$smithyVersion")
}
