package cn.chitanda.music.ui.scene.mine

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cn.chitanda.music.R
import cn.chitanda.music.http.DataState
import cn.chitanda.music.http.bean.PlaylistJson
import cn.chitanda.music.http.bean.UserProfile
import cn.chitanda.music.http.isLoading
import cn.chitanda.music.ui.LocalMusicControllerBarHeight
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.LocalUserViewModel
import cn.chitanda.music.ui.Scene
import cn.chitanda.music.ui.scene.LocaleUserViewModel
import cn.chitanda.music.ui.theme.Shapes
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 *@author: Chen
 *@createTime: 2021/9/2 10:18
 *@description:
 **/

private const val TAG = "MineScene"

@ExperimentalMaterial3Api
@ExperimentalCoilApi
@Composable
fun MineScene(
    navController: NavController = LocalNavController.current,
    viewModel: LocaleUserViewModel = LocalUserViewModel.current,
    padding: PaddingValues
) {
    val playlist by viewModel.playlist.collectAsState()
    val user by viewModel.user.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
//    Scaffold { padding ->
        SwipeRefresh(
            state = swipeRefreshState,
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
            onRefresh = { viewModel.getUserPlayList() }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = LocalMusicControllerBarHeight.current
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                user.json?.let {
                    item {
                        UserInfo(data = it, modifier = Modifier.padding(top = 56.dp))
                    }
                }
                playlist.json?.data?.let { list ->
                    list.firstOrNull()?.let {
                        item {
                            MyFavoritePlaylist(
                                playlist = it,
                                modifier = Modifier
                                    .fillMaxWidth().clickable {
                                        navController.navigate(
                                            Scene.Playlist.replaceId(
                                                "id" to it.id.toString()
                                            )
                                        )
                                    }
                            )
                        }
                    }
                    item {
                        SubscribedPlayList(
                            playlist = try {
                                list.subList(fromIndex = 1, toIndex = list.size)
                            } catch (e: Exception) {
                                emptyList()
                            },
                            modifier = Modifier.padding(bottom = 16.dp),
                            navController = navController
                        )
                    }
                    item {
                        Button(onClick = { navController.navigate(Scene.Theme.id) }) {
                            Text(text = "Theme")
                        }
                    }

                    item {
                        Button(onClick = {
                            viewModel.logout()
                            navController.navigate(Scene.Login.id){
                                popUpTo(Scene.Main.id){inclusive = true}
                            }
                        }) {
                            Text(text = "Logout")
                        }
                    }
                }
            }
        }
//    }
    LaunchedEffect(key1 = playlist) {
        if (playlist.status == DataState.STATE_CREATE) {
            viewModel.getUserPlayList()
        }
        swipeRefreshState.isRefreshing = playlist.status.isLoading
    }
}

@ExperimentalCoilApi
@Composable
fun UserInfo(data: UserProfile, modifier: Modifier = Modifier, avatarSize: Dp = 80.dp) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        Surface(
            modifier = Modifier.padding(top = avatarSize / 2),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = Shapes.medium
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .padding(top = (avatarSize - 16.dp) / 2)
            ) {
                Text(
                    text = data.data?.nickname.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth(0.5f), horizontalArrangement = Arrangement.SpaceAround) {
                    Text(
                        text = "${data.data?.follows} ${stringResource(id = R.string.text_followeds)}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "${data.data?.followeds} ${stringResource(id = R.string.text_fans)}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "${stringResource(id = R.string.text_levels)} ${data.level}",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
        AsyncImage(
            model = data.data?.avatarUrl,
            modifier = Modifier.size(avatarSize).clip(CircleShape),
            contentDescription = null
        )
    }
}

@ExperimentalCoilApi
@Composable
fun MyFavoritePlaylist(modifier: Modifier = Modifier, playlist: PlaylistJson.Playlist) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = Shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = playlist.coverImgUrl,
                modifier = Modifier.size(45.dp).clip(CircleShape),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = playlist.name.toString(), style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${playlist.trackCount}首",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun SubscribedPlayList(
    modifier: Modifier = Modifier,
    playlist: List<PlaylistJson.Playlist>,
    navController: NavController
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = Shapes.medium
    ) {
        if (playlist.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp), contentAlignment = Alignment.Center
            ) {
                Text(text = "未收藏歌单", style = MaterialTheme.typography.bodyMedium)
            }
            return@Surface
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = "收藏歌单(${playlist.size}个)",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            playlist.forEachIndexed { i, item ->
                Row(
                    modifier = Modifier
                        .padding(bottom = if (i == playlist.lastIndex) 0.dp else 16.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(
                                Scene.Playlist.replaceId(
                                    "id" to item.id.toString()
                                )
                            )
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = item.coverImgUrl,
                        modifier = Modifier.size(45.dp).clip(Shapes.small),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = item.name.toString(),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${item.trackCount.toString()}首, by ${item.creator?.nickname}",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}


