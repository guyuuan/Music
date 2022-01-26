plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version (Version.ksp_version)
}

android {
    compileSdk = Version.compileSdk
    defaultConfig {
        applicationId = "cn.chitanda.music"
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Version.compose_version
    }
    packagingOptions {
        resources.excludes += "META-INF/AL2.0"
        resources.excludes += "META-INF/LGPL2.1"
    }
}

dependencies {

    implementation(Libs.androidx_core)
    implementation(Libs.androidx_appcompat)
    implementation(Libs.material)
    implementation(project(":media"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    //Compose
    implementation(Libs.compose_ui)
    implementation(Libs.compose_md)
    implementation(Libs.compose_md3)
    implementation(Libs.compose_ui_preview)
    implementation(Libs.viewmodel_compose)
    implementation(Libs.navigation_compose)
    implementation(Libs.lifecycle)
    implementation(Libs.activity_compose)
    debugImplementation(Libs.compose_ui_tooling)
    androidTestImplementation(Libs.compose_test)

    // Retrofit
    implementation(Libs.retrofit)
    implementation(Libs.retrofit_converter_moshi)
    implementation(Libs.retrofit_logging)

    //Moshi
    implementation(Libs.moshi)
    implementation(Libs.moshi_kt)
    ksp(Libs.moshi_ksp)

    //Accompanist
    implementation(Libs.accompanist_insets)
    implementation(Libs.accompanist_systemuicontroller)
    implementation(Libs.accompanist_pager)
    implementation(Libs.accompanist_navigation_animation)
    implementation(Libs.accompanist_navigation_material)
    implementation(Libs.accompanist_swiperefresh)
    implementation(Libs.accompanist_flowlayout)

    //Coil
    implementation(Libs.coil)
    implementation(Libs.coil_compose)

    //MMKV
    implementation(Libs.mmkv)

    //Hilt
    implementation(Libs.hilt)
    kapt(Libs.hilt_compiler)
    kapt(Libs.hilt_kapt)
    implementation(Libs.hilt_viewmodel)
    implementation(Libs.hilt_navigation_compose)

    //Paging3
    implementation(Libs.paging)
    implementation(Libs.paging_compose)

    //StatusBarColor
    implementation(Libs.status_bar)
//    implementation(files("./libs/lib.aar"))
    implementation(Libs.permission)

    implementation(Libs.glide)
    //MediaSession
//    api(Libs.media)
//
//    //exo player
//    api(Libs.exo_core)
//    api(Libs.exo_ui)
//    api(Libs.exo_media_session)
//    api(Libs.exo_cast)
}