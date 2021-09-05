package cn.chitanda.music.ui.scene.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import cn.chitanda.music.R
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.LocalUserViewModel
import cn.chitanda.music.ui.scene.discovery.DiscoveryScene
import cn.chitanda.music.ui.scene.login.UserViewModel
import cn.chitanda.music.ui.scene.message.MessageScene
import cn.chitanda.music.ui.scene.mine.MineScene
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsHeight
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

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun HomeScene(
    userViewModel: UserViewModel = LocalUserViewModel.current,
    navController: NavController = LocalNavController.current
) {
    val list = listOf(PageItem.Found, PageItem.Message, PageItem.Mine)
    val homeNavController = rememberAnimatedNavController()
    Column(Modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .statusBarsHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.primarySurface)
        )
        Scaffold(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            topBar = {
                TopAppBar(contentPadding = PaddingValues(horizontal = 8.dp)) {
                    Icon(Icons.Default.Menu, contentDescription = "")
                    BasicTextField(
                        value = "", onValueChange = {}, modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(0.5f)
                            .padding(horizontal = 16.dp)
                            .background(Color.White, shape = RoundedCornerShape(16.dp))
                    )
                    Icon(painter = painterResource(id = R.drawable.ic_mic), contentDescription = "")
                }
            },
            bottomBar = {
                var currentPage by remember {
                    mutableStateOf<PageItem>(PageItem.Found)
                }
                BottomNavigation {
                    list.forEach { scene ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = scene.icon),
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = stringResource(id = scene.label))
                            },
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
            }) {
            AnimatedNavHost(
                navController = homeNavController,
                startDestination = PageItem.Found.route
            ) {
                composable(PageItem.Found.route) {
                    DiscoveryScene()
                }
                composable(PageItem.Message.route) {
                    MessageScene()
                }
                composable(PageItem.Mine.route) {
                    MineScene()
                }
            }
        }
        Spacer(
            modifier = Modifier
                .navigationBarsHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.primarySurface)
        )
    }
}

sealed class PageItem(val route: String, @StringRes val label: Int, @DrawableRes val icon: Int) {
    object Found : PageItem("found", R.string.label_found, R.drawable.ic_found)
    object Mine : PageItem("mine", R.string.label_mine, R.drawable.ic_me)
    object Message : PageItem("message", R.string.label_message, R.drawable.ic_chat)
}



