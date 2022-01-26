package cn.chitanda.music.ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cn.chitanda.music.MusicViewModel
import cn.chitanda.music.ui.scene.LocaleUserViewModel
import cn.chitanda.music.ui.scene.ThemeViewModel

/**
 *@author: Chen
 *@createTime: 2021/8/13 20:18
 *@description:
 **/

val LocalNavController =
    compositionLocalOf<NavHostController> { error("Can't get nacController") }
val LocalMusicControllerBarHeight = compositionLocalOf { 0.dp }
val LocalUserViewModel =
    staticCompositionLocalOf<LocaleUserViewModel> {
        error("Can't get user view model")
    }

val LocalMusicViewModel =
    staticCompositionLocalOf<MusicViewModel> {
        error("Can't get music view model")
    }
val LocalThemeViewModel =
    staticCompositionLocalOf<ThemeViewModel> { error("Can't get theme view model") }

