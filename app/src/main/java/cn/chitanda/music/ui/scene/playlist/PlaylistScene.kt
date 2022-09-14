package cn.chitanda.music.ui.scene.playlist

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import cn.chitanda.dynamicstatusbar.DynamicStatusBar
import cn.chitanda.music.R
import cn.chitanda.music.http.bean.Songs
import cn.chitanda.music.http.bean.artists
import cn.chitanda.music.http.paging.PlaylistSongsPagingSource
import cn.chitanda.music.ui.LocalMusicViewModel
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.scene.PageState
import cn.chitanda.music.ui.scene.isLoading
import cn.chitanda.music.ui.theme.DownArcShape
import cn.chitanda.music.ui.theme.Shapes
import cn.chitanda.music.utils.toUnitString
import coil.compose.AsyncImage

/**
 * @author: Chen
 * @createTime: 2021/12/31 16:34
 * @description:
 **/
private const val TAG = "PlaylistScene"

@ExperimentalMaterial3Api
@Composable
fun PlaylistScene(navController: NavController = LocalNavController.current, playlist: String?) {
    if (playlist.isNullOrEmpty()) {
        navController.navigateUp()
        return
    }
    val musicViewModel = LocalMusicViewModel.current
    val viewModel = hiltViewModel<PlaylistViewModel>()
    val viewState by viewModel.viewState.collectAsState()
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
//    val topAppBarScrollState = rememberTopAppBarState()
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val cxt = LocalContext.current
    LaunchedEffect(key1 = viewState) {
        if (viewState.state is PageState.Error) {
            Toast.makeText(cxt, (viewState.state as PageState.Error).tr.message, Toast.LENGTH_SHORT)
                .show()
        }
    }

    Surface {
        Column(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            FoldableTopAppBar(
                scrollBehavior = scrollBehavior, viewState = viewState
            )
            //歌曲列表
            if (!viewState.state.isLoading && viewState.songs != null) {
                val pager = remember(viewState.songs) {
                    Log.d(TAG, "PlaylistScene: pager create")
                    Pager(
                        config = PagingConfig(pageSize = PlaylistSongsPagingSource.PageSize),
                        initialKey = 0,
                        pagingSourceFactory = { viewState.songs!! }
                    )
                }
                val lazyPagingItems = pager.flow.collectAsLazyPagingItems()
                val lazyState = rememberLazyListState()
                LazyColumn(
                    state = lazyState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = WindowInsets.navigationBars.union(
                        WindowInsets(top = 16.dp, bottom = 16.dp)
                    ).asPaddingValues()
                ) {
                    if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
                        item {
                            Text(
                                text = "Waiting for items to load from the backend",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    itemsIndexed(lazyPagingItems) { index, item ->
//                        Text("Index=$index: ${item?.name}", fontSize = 14.sp)
                        item?.let {
                            SongsItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .clickable {
                                        musicViewModel.play(playlist, it.id.toString())
                                    }, song = it, index = index + 1,
                                iconColor = LocalContentColor.current.copy(alpha = 0.6f).copy(0.5f)
                            )
                        }
                    }

                    if (lazyPagingItems.loadState.append == LoadState.Loading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }

            }
            LaunchedEffect(key1 = viewState) {
                if (viewState.state == PageState.Success && viewState.songs == null) {
                    viewState.playlist?.let {
                        viewModel.getPlaylistSongsPagingSource(it.id, maxSize = it.songsCount)
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = playlist) {
        if (!playlist.isNullOrEmpty()) {
            viewModel.getPlaylistDetail(playlist)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FoldableTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    viewState: PlaylistViewState,
    navController: NavController = LocalNavController.current
) {
    var isLight by remember { mutableStateOf(DynamicStatusBar.isLight) }
    val color =
        if (isLight) LocalContentColor.current else Color.White
    val appBarSize =
        WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + FoldableTopAppBarHeight
    val height = remember { 200.dp }
    val fraction = 1f - scrollBehavior.state.collapsedFraction
    val contentAlpha =
        if (fraction > 0.5f) 1f else (fraction) / 0.5f
    CompositionLocalProvider(LocalContentColor provides color) {
        Box(
            modifier = Modifier
                .height(appBarSize + height * (fraction))
        ) {
            AppbarBackground(
                modifier = Modifier
                    .padding(bottom = 16.dp * (fraction))
                    .matchParentSize()
                    .clip(
                        shape = DownArcShape(8.dp * (fraction))
                    ), url = viewState.playlist?.coverUrl
            ) {
                isLight = DynamicStatusBar.isLight
            }
            //歌单详情
            if (viewState.state.isLoading && null == viewState.playlist) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                if (contentAlpha > 0.2f) {
                    viewState.playlist?.let {
                        PlaylistInfo(modifier = Modifier
                            .graphicsLayer {
                                alpha = contentAlpha
                            }
                            .padding(horizontal = 20.dp)
                            .padding(top = (appBarSize - 65.dp) * contentAlpha)
                            .height(110.dp)
                            .fillMaxWidth()
                            .align(Alignment.Center), playlist = it)
                        FloatActionBar(it, modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth(0.7f)
                            .height(50.dp)
                            .graphicsLayer {
                                alpha = contentAlpha
                            })
                    }
                }
            }

            Row(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .height(54.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                }
                Text(text = "歌单", style = MaterialTheme.typography.titleLarge)
            }

        }
    }
    val offsetLimit = with(LocalDensity.current) { -appBarSize.toPx() }
    SideEffect {
        if (scrollBehavior.state.heightOffsetLimit != offsetLimit) scrollBehavior.state.heightOffsetLimit =
            offsetLimit
    }
}

@Composable
fun PlaylistInfo(modifier: Modifier, playlist: PlaylistViewState.PlaylistDetail) {
    val color = LocalContentColor.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = playlist.coverUrl,
            modifier = Modifier
                .aspectRatio(1f, true)
                .clip(Shapes.small),
            contentDescription = null
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = playlist.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = playlist.creator?.avatarUrl,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                    contentDescription = null
                )
                Text(
                    text = "${playlist.creator?.nickname ?: ""} >",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            if (playlist.description.isNullOrEmpty()) {
                Box(modifier = Modifier
                    .clip(Shapes.small)
                    .clickable { }
                    .padding(4.dp)
                ) {
                    Text(
                        text = " ${playlist.description ?: ""} >",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall,
                        color = color,
                    )
                }

            } else {
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
private inline fun AppbarBackground(
    modifier: Modifier,
    url: String?,
    crossinline sideEffect: () -> Unit
) {
    AsyncImage(
        modifier = modifier.blur(radius = 50.dp),
        model = url,
        contentScale = ContentScale.Crop,
        contentDescription = null,
        onSuccess = {
            sideEffect()
        })
}

@Composable
fun FloatActionBar(playlist: PlaylistViewState.PlaylistDetail, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        tonalElevation = 8.dp,
        shadowElevation = 1.dp,
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(if (playlist.subscribed) R.drawable.ic_subscribed else R.drawable.ic_add_subscribed),
                        contentDescription = null, modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = playlist.subscribedCount.toUnitString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxHeight(0.35f)
                    .width(1.dp)
                    .background(LocalContentColor.current.copy(alpha = 0.5f))
            )
            IconButton(onClick = { }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_comment),
                        contentDescription = null, modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = playlist.commentCount.toUnitString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .width(1.dp)
                    .background(LocalContentColor.current.copy(alpha = 0.5f))
            )

            IconButton(onClick = { }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = null, modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = playlist.shareCount.toUnitString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

        }
    }
}

@Composable
fun SongsItem(
    modifier: Modifier = Modifier,
    song: Songs.Song,
    index: Int,
    iconColor: Color = LocalContentColor.current,
) {
    Row(modifier = modifier) {
        Text(
            text = index.toString(), modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f, true)
                .wrapContentSize(),
            style = MaterialTheme.typography.bodyMedium
                .copy(color = iconColor)
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround, modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Text(
                text = song.name.toString(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${song.artists ?: ""} - ${song.al?.name}",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        song.mv?.takeIf { it != 0L }?.let {
            IconButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f, true)
                    .wrapContentSize(Alignment.Center), onClick = {}
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_music_video),
                    contentDescription = "mv",
                    tint = iconColor
                )
            }
        }
        IconButton(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f, true)
                .wrapContentSize(Alignment.Center), onClick = {}
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more_vert),
                contentDescription = "more",
                tint = iconColor
            )
        }
    }
}

val FoldableTopAppBarHeight = 64.dp