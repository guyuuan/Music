package cn.chitanda.music.ui.scene.mine

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.LocalUserViewModel
import cn.chitanda.music.ui.scene.UserViewModel
import coil.annotation.ExperimentalCoilApi

/**
 *@author: Chen
 *@createTime: 2021/9/2 10:18
 *@description:
 **/

private const val TAG = "MineScene"

@ExperimentalCoilApi
@Composable
fun MineScene(
    navController: NavController = LocalNavController.current,
    viewModel: UserViewModel = LocalUserViewModel.current
) {
    val user by viewModel.user.collectAsState()

}
