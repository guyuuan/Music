/**
 * @author: Chen
 * @createTime: 2022/1/7 11:51
 * @description:
 **/
object Libs {

    //ClassPath
    const val agp = "com.android.tools.build:gradle:7.0.4"
    const val kgp = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin_version}"
    const val hgp = "com.google.dagger:hilt-android-gradle-plugin:${Version.hilt_version}"

    const val androidx_core = "androidx.core:core-ktx:1.7.0"
    const val androidx_appcompat = "androidx.appcompat:appcompat:1.4.0"
    const val material = "com.google.android.material:material:1.4.0"

    //Compose
    const val compose_ui = "androidx.compose.ui:ui:${Version.compose_version}"
    const val compose_md = "androidx.compose.material:material:${Version.compose_version}"
    const val compose_ui_preview =
        "androidx.compose.ui:ui-tooling-preview:${Version.compose_version}"
    const val compose_ui_tooling = "androidx.compose.ui:ui-tooling:${Version.compose_version}"
    const val activity_compose =
        "androidx.activity:activity-compose:${Version.activity_compose_version}"
    const val compose_md3 = "androidx.compose.material3:material3:${Version.compose_md3_version}"
    const val compose_test = "androidx.compose.ui:ui-test-junit4:${Version.compose_version}"

    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycle_version}"
    const val viewmodel_compose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.lifecycle_version}"

    const val navigation_compose =
        "androidx.navigation:navigation-compose:${Version.navigation_version}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit_version}"
    const val retrofit_converter_moshi =
        "com.squareup.retrofit2:converter-moshi:${Version.retrofit_version}"
    const val retrofit_logging = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3"

    //Moshi
    const val moshi = "com.squareup.moshi:moshi:${Version.moshi_version}"
    const val moshi_kt = "com.squareup.moshi:moshi-kotlin:${Version.moshi_version}"
    const val moshi_ksp = "com.squareup.moshi:moshi-kotlin-codegen:${Version.moshi_version}"

    //Accompanist
    const val accompanist_insets =
        "com.google.accompanist:accompanist-insets:${Version.accompanist_version}"
    const val accompanist_systemuicontroller =
        "com.google.accompanist:accompanist-systemuicontroller:${Version.accompanist_version}"
    const val accompanist_pager =
        "com.google.accompanist:accompanist-pager:${Version.accompanist_version}"
    const val accompanist_navigation_animation =
        "com.google.accompanist:accompanist-navigation-animation:${Version.accompanist_version}"
    const val accompanist_navigation_material =
        "com.google.accompanist:accompanist-navigation-material:${Version.accompanist_version}"
    const val accompanist_swiperefresh =
        "com.google.accompanist:accompanist-swiperefresh:${Version.accompanist_version}"
    const val accompanist_flowlayout =
        "com.google.accompanist:accompanist-flowlayout:${Version.accompanist_version}"

    //Coil
    const val coil = "io.coil-kt:coil:${Version.coil_version}"
    const val coil_compose = "io.coil-kt:coil-compose:${Version.coil_version}"

    //MMKV
    const val mmkv = "com.tencent:mmkv-static:${Version.mmkv_version}"

    //Hilt
    const val hilt = "com.google.dagger:hilt-android:${Version.hilt_version}"
    const val hilt_kapt = "com.google.dagger:hilt-compiler:${Version.hilt_version}"
    const val hilt_viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
    const val hilt_navigation_compose = "androidx.hilt:hilt-navigation-compose:1.0.0-rc01"
    const val hilt_compiler = "androidx.hilt:hilt-compiler:1.0.0"

    //Paging3
    const val paging = "androidx.paging:paging-runtime-ktx:${Version.paging_version}"
    const val paging_compose = "androidx.paging:paging-compose:1.0.0-alpha14"

    //StatusBarColor
    const val status_bar = "cn.chitanda:dynamicstatusbar:2.5.1"

    const val permission = "com.guolindev.permissionx:permissionx:1.6.1"

    //media session
    const val media = "androidx.media:media:${Version.media_version}"

    //exo player
    const val exo_core = "com.google.android.exoplayer:exoplayer-core:${Version.exoplayer_version}"
    const val exo_ui = "com.google.android.exoplayer:exoplayer-ui:${Version.exoplayer_version}"
    const val exo_media_session =
        "com.google.android.exoplayer:extension-mediasession:${Version.exoplayer_version}"
    const val exo_cast = "com.google.android.exoplayer:extension-cast:${Version.exoplayer_version}"

    const val kotlin_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.kotlin_coroutines_version}"
}