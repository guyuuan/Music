// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.agp)
        classpath(Libs.kgp)
        classpath(Libs.hgp)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
//task clean(type: Delete) {
//    delete rootProject.buildDir
//}