package cn.chitanda.music.ui.scene.playlist

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import cn.chitanda.music.ui.widget.CoilImage
import cn.chitanda.music.utils.toUnitString
import coil.annotation.ExperimentalCoilApi
import coil.transform.BlurTransformation
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding

/**
 * @author: Chen
 * @createTime: 2021/12/31 16:34
 * @description:
 **/
private const val TAG = "PlaylistScene"

@OptIn(ExperimentalCoilApi::class)
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
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }
    val cxt = LocalContext.current
    LaunchedEffect(key1 = viewState) {
        if (viewState.state is PageState.Error) {
            Toast.makeText(cxt, (viewState.state as PageState.Error).tr.message, Toast.LENGTH_SHORT)
                .show()
        }
    }

    Surface {
        Column(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
            FoldableTopAppBar(
                scrollBehavior = scrollBehavior, viewState = viewState
            )
            //歌曲列表
            if (!viewState.state.isLoading && viewState.songs != null) {
                val pager = remember(viewState.songs) {
                    Pager(
                        config = PagingConfig(pageSize = PlaylistSongsPagingSource.PageSize),
                        initialKey = 0,
                        pagingSourceFactory = { viewState.songs!! }
                    )
                }
                val lazyPagingItems = pager.flow.collectAsLazyPagingItems()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
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

@OptIn(ExperimentalCoilApi::class)
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
        rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.statusBars,
            applyTop = true
        ).calculateTopPadding() + FoldableTopAppBarHeight
    val height = remember { 200.dp }
    val fraction = 1f - scrollBehavior.scrollFraction
    val contentAlpha =
        if (fraction > 0.5f) 1f else (fraction) / 0.5f
    Box(
        modifier = Modifier
            .height(appBarSize + height * (fraction))
    ) {
        AppbarBackground(
            modifier = Modifier
                .padding(bottom = 16.dp * (fraction))
                .fillMaxSize()
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
            CompositionLocalProvider(LocalContentColor provides color) {
                Box(
                    modifier = Modifier
                        .padding(top = (appBarSize - 65.dp) * contentAlpha)
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .height(110.dp)
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .graphicsLayer {
                                alpha = contentAlpha
                            },
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CoilImage(
                            url = viewState.playlist?.coverUrl,
                            modifier = Modifier.aspectRatio(1f, true),
                            shape = Shapes.small
                        )
                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(
                                text = viewState.playlist?.name ?: "",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                CoilImage(
                                    url = viewState.playlist?.creator?.avatarUrl,
                                    modifier = Modifier.size(24.dp),
                                    shape = CircleShape
                                )
                                Text(
                                    text = "${viewState.playlist?.creator?.nickname ?: ""} >",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                            if (!viewState.playlist?.description.isNullOrEmpty()) {
                                Box(modifier = Modifier
                                    .clip(Shapes.small)
                                    .clickable { }
                                    .padding(4.dp)
                                ) {
                                    Text(
                                        text = " ${viewState.playlist?.description ?: ""} >",
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
            }
            viewState.playlist?.let {
                FloatActionBar(it, modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .graphicsLayer {
                        alpha = contentAlpha
                    })
            }
        }
        CompositionLocalProvider(LocalContentColor provides color) {
            SmallTopAppBar(
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .statusBarsPadding(),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    titleContentColor = color,
                    actionIconContentColor = color,
                    navigationIconContentColor = color
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                },
                title = {
                    Text(text = "歌单")
                })
        }
    }
    val offsetLimit = with(LocalDensity.current) { -appBarSize.toPx() }
    SideEffect {
        if (scrollBehavior.offsetLimit != offsetLimit) scrollBehavior.offsetLimit = offsetLimit
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun AppbarBackground(modifier: Modifier, url: String?, sideEffect: () -> Unit) {
    val cxt = LocalContext.current
    CoilImage(
        modifier = modifier,
        url = url,
        onLoading = {},
        onSuccess = {
            sideEffect()
        },
        builder = {
            transformations(
                BlurTransformation(
                    context = cxt,
                    radius = 25f,
                    sampling = 10f
                )
            )
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