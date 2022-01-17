package cn.chitanda.music.ui.scene.playlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cn.chitanda.music.ui.LocalNavController

/**
 * @author: Chen
 * @createTime: 2021/12/31 16:34
 * @description:
 **/
@ExperimentalMaterial3Api
@Composable
fun PlaylistScene(navController: NavController = LocalNavController.current, playlist: String?) {
    if (playlist.isNullOrEmpty()) {
        navController.navigateUp()
        return
    }
    val viewModel = hiltViewModel<PlaylistViewModel>()
    var str by remember {
        mutableStateOf(playlist)
    }
    Scaffold {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = str)
        }
        LaunchedEffect(key1 = playlist) {
            if (!playlist.isNullOrEmpty()) {
                viewModel.getPlaylistDetail(playlist){
                    str = it
                }
            }
        }
    }
}