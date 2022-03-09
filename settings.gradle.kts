rootProject.name = "Music"
include(":app")
include(":media")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

}
enableFeaturePreview("VERSION_CATALOGS")