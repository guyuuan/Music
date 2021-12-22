package cn.chitanda.music.ui

import android.os.Build
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import cn.chitanda.music.R
import cn.chitanda.music.ui.scene.home.HomeScene
import cn.chitanda.music.ui.scene.login.LoginScene
import cn.chitanda.music.ui.scene.other.ThemeScene
import cn.chitanda.music.ui.scene.splash.SplashScene
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.ProvideWindowInsets
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
    object Home : Scene(id = "home", label = R.string.label_home)
    object Login : Scene(id = "login", label = R.string.label_login)
    object Theme : Scene(id = "theme", label = R.string.text_theme)
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
            Log.d("Route", "Router: ")
            ProvideWindowInsets {
                AnimatedNavHost(
                    navController = navController,
                    startDestination = when {
                        Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> Scene.Splash.id
                        userViewModel.loginSuccess -> {
                            Scene.Home.id
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
    composable(Scene.Home.id) {
        HomeScene()
    }
    composable(Scene.Theme.id) {
        ThemeScene()
    }
}