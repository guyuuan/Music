package cn.chitanda.music.ui.scene.main

import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
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
import cn.chitanda.music.media.extensions.displayIconUri
import cn.chitanda.music.media.extensions.displaySubtitle
import cn.chitanda.music.media.extensions.displayTitle
import cn.chitanda.music.media.extensions.duration
import cn.chitanda.music.media.extensions.isPlaying
import cn.chitanda.music.ui.LocalMusicControllerBarHeight
import cn.chitanda.music.ui.LocalMusicViewModel
import cn.chitanda.music.ui.scene.home.HomeScene
import cn.chitanda.music.ui.scene.message.MessageScene
import cn.chitanda.music.ui.scene.mine.MineScene
import cn.chitanda.music.ui.widget.CoilImage
import cn.chitanda.music.ui.widget.navbar.BottomNavigationBar
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

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun MainScene() {
    val homeNavController = rememberAnimatedNavController()
    var currentDestination by remember { mutableStateOf(homeNavController.currentDestination) }
    var musicControllerBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val musicViewModel = LocalMusicViewModel.current
    val playbackState by musicViewModel.playbackState.observeAsState()
    val nowPlaying by musicViewModel.nowPlaying.observeAsState()
    val currentPosition by musicViewModel.currentPosition.observeAsState(0)
    var currentIndex by rememberSaveable {
        mutableStateOf(0)
    }
    CompositionLocalProvider(LocalMusicControllerBarHeight provides musicControllerBarHeight) {
        Scaffold(
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
            }) {
            Box(modifier = Modifier.padding(it)) {
                AnimatedNavHost(
                    navController = homeNavController,
                    startDestination = MainPageItem.Find.route
                ) {
                    composable(
                        MainPageItem.Find.route,
                        enterTransition = enterTransition,
                        exitTransition = exitTransition
                    ) {
                        HomeScene()
                    }
                    composable(
                        MainPageItem.Message.route,
                        enterTransition = enterTransition,
                        exitTransition = exitTransition
                    ) {
                        MessageScene()
                    }
                    composable(
                        MainPageItem.Mine.route,
                        enterTransition = enterTransition,
                        exitTransition = exitTransition
                    ) {
                        MineScene()
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
                    Surface(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 6.dp),
                        shape = RoundedCornerShape(8.dp),
                        tonalElevation = 3.dp
                    ) {
                        if (nowPlaying != null && playbackState != null) {
                            MusicControlBar(
                                musicViewModel,
                                nowPlaying!!,
                                playbackState!!,
                                currentPosition
                            )
                        }
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

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MusicControlBar(
    musicViewModel: MusicViewModel,
    nowPlaying: MediaMetadataCompat,
    playbackState: PlaybackStateCompat,
    currentPosition: Long
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), contentAlignment = Alignment.CenterStart
    ) {
        val percent by derivedStateOf {
            currentPosition.toFloat() / nowPlaying.duration
        }
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        LocalAbsoluteTonalElevation.current + 30.dp
                    )
                )
                .fillMaxHeight()
                .fillMaxWidth(
                    percent
                )
                .align(Alignment.CenterStart)
        )
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CoilImage(
                url = nowPlaying.displayIconUri,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f, true),
                shape = CircleShape
            )
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "${nowPlaying.displayTitle}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${nowPlaying.displaySubtitle}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    if (playbackState.isPlaying) {
                        musicViewModel.pause()
                    } else {
                        musicViewModel.resume()
                    }
                }, modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(id = if (playbackState?.isPlaying == true) R.drawable.ic_pause else R.drawable.ic_play_arrow),
                    contentDescription = null
                )
            }


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
    BottomNavigationBar {
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



