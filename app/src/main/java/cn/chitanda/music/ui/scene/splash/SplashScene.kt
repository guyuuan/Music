package cn.chitanda.music.ui.scene.splash

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.LocalUserViewModel
import cn.chitanda.music.ui.Scene
import cn.chitanda.music.ui.scene.login.UserViewModel
import kotlinx.coroutines.delay

/**
 *@author: Chen
 *@createTime: 2021/8/31 13:22
 *@description:
 **/
@Composable
fun SplashScene(
    userViewModel: UserViewModel = LocalUserViewModel.current,
    navController: NavController = LocalNavController.current
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    )
    LaunchedEffect(key1 = Unit) {
        delay(1000L)
        Log.d(TAG, "SplashScene: ${userViewModel.cookies}")
        if (userViewModel.cookies.isNotEmpty()) {
            navController.navigate(Scene.Home.id) {
                popUpTo(Scene.Splash.id) { inclusive = true }
            }
        }else{
            navController.navigate(Scene.Login.id) {
                popUpTo(Scene.Splash.id) { inclusive = true }
            }
        }
    }
}
private const val TAG = "SplashScene"