package cn.chitanda.music.ui.scene.message

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import cn.chitanda.music.ui.LocalNavController
import com.google.accompanist.pager.ExperimentalPagerApi

/**
 *@author: Chen
 *@createTime: 2021/9/2 10:19
 *@description:
 **/

private const val TAG = "MessageScene"

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun MessageScene(navController: NavController = LocalNavController.current) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    )
}

