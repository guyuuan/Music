@Suppress("UnstableApiUsage")
plugins {
    id(libs.plugins.android.lib.get().pluginId)
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.ksp)
}

android {
    compileSdk =  libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk =  libs.versions.minSdk.get().toInt()
        targetSdk =  libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.kotlin.coroutines.android)
    //media session
    api(libs.androidx.media)

    //exo player
    api(libs.exo.core)
    api(libs.exo.ui)
    api(libs.exo.mediasession)
    api(libs.exo.cast)

    api(libs.coil)

    implementation(libs.dagger.android)
    kapt(libs.hilt.compiler)
    kapt(libs.dagger.compiler)
}