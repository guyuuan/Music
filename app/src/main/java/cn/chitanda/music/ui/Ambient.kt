package cn.chitanda.music.ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import cn.chitanda.music.ui.scene.UserViewModel

/**
 *@author: Chen
 *@createTime: 2021/8/13 20:18
 *@description:
 **/

val LocalNavController =
    compositionLocalOf<NavHostController> { error("Can't get nacController") }
val LocalUserViewModel =
    staticCompositionLocalOf<UserViewModel> { error("Can't get user view model") }

