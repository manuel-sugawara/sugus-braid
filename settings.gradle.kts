rootProject.name = "sugus-smithy-codegen"

// Include subpackages
include(":braid-syntax-plugin")
include(":braid-serde-node-plugin")
include(":braid-data-plugin")
include(":braid-core")
include(":braid-traits")
include(":braid-java-codegen-test")
include(":braid-java-syntax-model")
include(":braid-java-syntax-model-plugin")
include(":braid-rt-util")
include(":braid-java-syntax")

pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}
