plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Version.compileSdk
    defaultConfig {
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk

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
//        useIR = true
    }
}

dependencies {

    implementation(Libs.androidx_core)
    implementation(Libs.androidx_appcompat)
    implementation(Libs.kotlin_coroutines_android)
    //media session
    api(Libs.media)

    //exo player
    api(Libs.exo_core)
    api(Libs.exo_ui)
    api(Libs.exo_media_session)
    api(Libs.exo_cast)

    api(Libs.coil)

    implementation(Libs.hilt)
    kapt(Libs.hilt_compiler)
    kapt(Libs.hilt_kapt)
}