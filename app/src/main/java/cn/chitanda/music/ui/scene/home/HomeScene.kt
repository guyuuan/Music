package cn.chitanda.music.ui.scene.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import cn.chitanda.music.R
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.LocalUserViewModel
import cn.chitanda.music.ui.scene.UserViewModel
import cn.chitanda.music.ui.scene.find.FindScene
import cn.chitanda.music.ui.scene.message.MessageScene
import cn.chitanda.music.ui.scene.mine.MineScene
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.navigationBarsHeight
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

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun HomeScene(
    userViewModel: UserViewModel = LocalUserViewModel.current,
    navController: NavController = LocalNavController.current
) {
    val homeNavController = rememberAnimatedNavController()
    Scaffold(
        bottomBar = {
            BottomBar(homeNavController)
        }) {
        AnimatedNavHost(
            modifier = Modifier.padding(it),
            navController = homeNavController,
            startDestination = HomePageItem.Find.route
        ) {
            composable(HomePageItem.Find.route) {
                FindScene()
            }
            composable(HomePageItem.Message.route) {
                MessageScene()
            }
            composable(HomePageItem.Mine.route) {
                MineScene()
            }
        }
    }
}

@Composable
private fun BottomBar(homeNavController: NavController) {
    val list = remember { listOf(HomePageItem.Find, HomePageItem.Message, HomePageItem.Mine) }
    var currentPage by remember {
        mutableStateOf<HomePageItem>(HomePageItem.Find)
    }
    Column {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.inversePrimary
        ) {
            list.forEach { scene ->
                BottomNavigationItem(
                    selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                    icon = {
                        Icon(
                            painter = painterResource(id = scene.icon),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = stringResource(id = scene.label))
                    },
                    alwaysShowLabel = false,
                    selected = currentPage == scene,
                    onClick = {
                        currentPage = scene
                        homeNavController.navigate(scene.route) {
                            popUpTo(homeNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsHeight()
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
        )
    }
}

sealed class HomePageItem(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    object Find : HomePageItem("found", R.string.label_found, R.drawable.ic_found)
    object Mine : HomePageItem("mine", R.string.label_mine, R.drawable.ic_me)
    object Message : HomePageItem("message", R.string.label_message, R.drawable.ic_chat)
}



