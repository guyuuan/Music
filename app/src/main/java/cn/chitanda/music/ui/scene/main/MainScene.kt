package cn.chitanda.music.ui.scene.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import cn.chitanda.music.MusicViewModel
import cn.chitanda.music.R
import cn.chitanda.music.media.connect.NOTHING_PLAYING
import cn.chitanda.music.ui.LocalMusicControllerBarHeight
import cn.chitanda.music.ui.LocalMusicViewModel
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.Scene
import cn.chitanda.music.ui.scene.home.HomeScene
import cn.chitanda.music.ui.scene.message.MessageScene
import cn.chitanda.music.ui.scene.mine.MineScene
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi

/**
 *@author: Chen
 *@createTime: 2021/8/31 13:35
 *@description:
 **/
private const val TAG = "HomeScene"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun MainScene(
    musicViewModel: MusicViewModel = LocalMusicViewModel.current,
    navController: NavController = LocalNavController.current
) {
    val homeNavController = rememberAnimatedNavController()
    var currentDestination by remember { mutableStateOf(homeNavController.currentDestination) }
    var musicControllerBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val playbackState by musicViewModel.playbackState.observeAsState()
    val nowPlaying by musicViewModel.nowPlaying.observeAsState()
    var currentIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val currentPosition by musicViewModel.currentPosition.observeAsState(0)

    CompositionLocalProvider(LocalMusicControllerBarHeight provides musicControllerBarHeight) {
        Scaffold(
            contentWindowInsets = WindowInsets(
                left = 0.dp,
                top = 0.dp,
                right = 0.dp,
                bottom = 0.dp
            ),
            bottomBar = {
                BottomBar(currentIndex, onItemClick = { index ->
                    currentIndex = index
                    homeNavController.navigate(pages[index].route) {
                        popUpTo(homeNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }) {  scaffoldPadding ->
            Box {
                AnimatedNavHost(
                    navController = homeNavController,
                    startDestination = MainPageItem.Find.route,
                ) {
                    composable(
                        MainPageItem.Find.route,
                        enterTransition = enterTransition,
                        exitTransition = exitTransition
                    ) {
                        HomeScene(padding = scaffoldPadding)
                    }
                    composable(
                        MainPageItem.Message.route,
                        enterTransition = enterTransition,
                        exitTransition = exitTransition
                    ) {
                        MessageScene(padding = scaffoldPadding)
                    }
                    composable(
                        MainPageItem.Mine.route,
                        enterTransition = enterTransition,
                        exitTransition = exitTransition
                    ) {
                        MineScene(padding =  scaffoldPadding)
                    }
                }
                AnimatedVisibility(
                    modifier = Modifier
                        .onSizeChanged {
                            musicControllerBarHeight = with(density) { it.height.toDp() }
                        }
                        .align(Alignment.BottomCenter),
                    visible = currentDestination?.route != MainPageItem.Message.route && nowPlaying != null && nowPlaying != NOTHING_PLAYING
                ) {
                    if (nowPlaying != null && playbackState != null) {
                        MusicControlBar(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 6.dp),
                            onClick = {
                                navController.navigate(route = Scene.PlayDetail.id)
                            },
                            nowPlaying = nowPlaying!!,
                            playbackState = playbackState!!,
                            currentPosition = currentPosition,
                            onPause = musicViewModel::pause,
                            onResume = musicViewModel::resume,
                            seekTo = musicViewModel::seekTo,
                            toNext = musicViewModel::toNext,
                            toPrevious = musicViewModel::toPrevious,
                        )
                    }
                }
            }
        }
    }
    val listener = remember(homeNavController) {
        MainDestinationChangedListener {
            currentDestination = it
        }
    }
    DisposableEffect(key1 = homeNavController) {
        homeNavController.addOnDestinationChangedListener(listener)
        onDispose {
            homeNavController.removeOnDestinationChangedListener(listener)
        }
    }
}

class MainDestinationChangedListener(private val callBack: (NavDestination?) -> Unit) :
    NavController.OnDestinationChangedListener {
    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        callBack(destination)
    }
}

@ExperimentalAnimationApi
private val exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition) = {
    fadeOut()
}

@ExperimentalAnimationApi
private val enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition) = {
    fadeIn(tween(500))
}
val pages = listOf(MainPageItem.Find, MainPageItem.Message, MainPageItem.Mine)

@Composable
private fun BottomBar(
    currentIndex: Int,
    onItemClick: (Int) -> Unit
) {
    val currentPage = pages[currentIndex]
    NavigationBar(windowInsets = WindowInsets.navigationBars) {
        pages.forEachIndexed { i, scene ->
            NavigationBarItem(selected = currentPage == scene, onClick = {
                onItemClick(i)
            }, icon = {
                Icon(
                    painter = painterResource(id = scene.icon),
                    contentDescription = null
                )
            }, label = {
                Text(text = stringResource(id = scene.label))
            }, alwaysShowLabel = true)
        }
    }
}

sealed class MainPageItem(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    object Find : MainPageItem("found", R.string.label_found, R.drawable.ic_home)
    object Mine : MainPageItem("mine", R.string.label_mine, R.drawable.ic_me)
    object Message : MainPageItem("message", R.string.label_message, R.drawable.ic_chat)
}



