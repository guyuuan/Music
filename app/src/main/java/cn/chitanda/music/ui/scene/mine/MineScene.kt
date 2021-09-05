package cn.chitanda.music.ui.scene.mine

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import cn.chitanda.music.ui.LocalNavController

/**
 *@author: Chen
 *@createTime: 2021/9/2 10:18
 *@description:
 **/
@Composable
fun MineScene(navController: NavController = LocalNavController.current) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Mine")
    }
}