package cn.chitanda.music.ui.scene.mine

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.LocalUserViewModel
import cn.chitanda.music.ui.scene.UserViewModel

/**
 *@author: Chen
 *@createTime: 2021/9/2 10:18
 *@description:
 **/
@Composable
fun MineScene(
    navController: NavController = LocalNavController.current,
    viewModel: UserViewModel = LocalUserViewModel.current
) {
    val user by viewModel.user.collectAsState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "${user.json?.profile?.nickname}")
    }
}