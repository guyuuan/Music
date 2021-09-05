package cn.chitanda.music.ui

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import cn.chitanda.music.R
import cn.chitanda.music.ui.scene.home.HomeScene
import cn.chitanda.music.ui.scene.login.LoginScene
import cn.chitanda.music.ui.scene.login.UserViewModel
import cn.chitanda.music.ui.scene.splash.SplashScene
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
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun Router(navController: NavHostController = rememberAnimatedNavController()) {
    val userViewModel = hiltViewModel<UserViewModel>()
    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalUserViewModel provides userViewModel
    ) {
        ProvideWindowInsets {
            AnimatedNavHost(
                navController = navController,
                startDestination = Scene.Splash.id
            ) {
                route()
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.route() {
//    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
    composable(Scene.Splash.id) {
        SplashScene()
    }
//    }
    composable(Scene.Login.id) {
        LoginScene()
    }
    composable(Scene.Home.id) {
        HomeScene()
    }
}