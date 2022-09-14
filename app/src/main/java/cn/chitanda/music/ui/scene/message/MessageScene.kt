package cn.chitanda.music.ui.scene.message

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun MessageScene(
    navController: NavController = LocalNavController.current,
    padding: PaddingValues
) {
    Box(modifier = Modifier.fillMaxSize())
}

