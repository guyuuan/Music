package cn.chitanda.music.ui

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import cn.chitanda.music.R
import cn.chitanda.music.ui.scene.login.LoginScene
import cn.chitanda.music.ui.scene.main.MainScene
import cn.chitanda.music.ui.scene.other.ThemeScene
import cn.chitanda.music.ui.scene.play_detail.PlayDetailScene
import cn.chitanda.music.ui.scene.playlist.PlaylistScene
import cn.chitanda.music.ui.scene.splash.SplashScene
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi

/**
 *@author: Chen
 *@createTime: 2021/8/31 13:18
 *@description:
 **/
sealed class Scene(val id: String, @StringRes val label: Int? = null) {
    object Splash : Scene(id = "splash")
    object Main : Scene(id = "home", label = R.string.label_main)
    object Login : Scene(id = "login", label = R.string.label_login)
    object Theme : Scene(id = "theme", label = R.string.text_theme)
    object Playlist : Scene(id = "playlist/{id}", label = R.string.label_playlist)
    object  PlayDetail:Scene(id = "play_detail", label = R.string.label_login)

    fun replaceId(vararg kv: Pair<String, String>): String {
        var result = id
        kv.forEach {
            result = result.replace("{${it.first}}", it.second)
        }
        return result
    }
}

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun Router(navController: NavHostController = rememberAnimatedNavController()) {
    val userViewModel = LocalUserViewModel.current
    val themeViewModel = LocalThemeViewModel.current
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        if (userViewModel.isReady.value && themeViewModel.isReady.value) {
            AnimatedNavHost(
                navController = navController,
                startDestination = when {
                    Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> Scene.Splash.id
                    userViewModel.loginSuccess -> {
                        Scene.Main.id
                    }
                    else -> {
                        Scene.Login.id
                    }
                }
            ) {
                route()
            }
        }
    }
    LaunchedEffect(key1 = themeViewModel.isReady) {
        if (!themeViewModel.isReady.value) themeViewModel.init()
    }
}

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.route() {
    composable(Scene.Splash.id) {
        SplashScene()
    }
    composable(Scene.Login.id) {
        LoginScene()
    }
    composable(Scene.Main.id) {
        MainScene()
    }
    composable(Scene.Theme.id) {
        ThemeScene()
    }
    composable(
        Scene.Playlist.id,
        arguments = listOf(navArgument("id") { defaultValue = "" })
    ) { backStackEntry ->
        PlaylistScene(playlist = backStackEntry.arguments?.getString("id"))
    }

    composable(Scene.PlayDetail.id){
        PlayDetailScene()
    }
}