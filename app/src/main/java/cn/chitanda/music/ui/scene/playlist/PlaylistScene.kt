package cn.chitanda.music.ui.scene.playlist

import android.widget.Toast
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
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
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.scene.PageState
import cn.chitanda.music.ui.scene.isLoading
import cn.chitanda.music.ui.theme.DownArcShape
import cn.chitanda.music.ui.theme.Shapes
import cn.chitanda.music.ui.widget.CoilImage
import coil.annotation.ExperimentalCoilApi
import coil.transform.BlurTransformation
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                                    .height(40.dp), song = it, index = index + 1,
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
    viewState: PlaylistViewState
) {
    var isLight by remember { mutableStateOf(DynamicStatusBar.isLight) }
    val color =
        if (isLight) LocalContentColor.current else Color.White
    val appBarSize =
        rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.statusBars,
            applyTop = true
        ).calculateTopPadding() + 64.dp
    val height = remember { 250.dp }
    val contentAlpha =
        if (1f - scrollBehavior.scrollFraction > 0.5f) 1f else (1f - scrollBehavior.scrollFraction) / 0.5f
    Box(
        modifier = Modifier
            .height(appBarSize + height * (1f - scrollBehavior.scrollFraction))
    ) {
        AppbarBackground(
            modifier = Modifier
                .padding(bottom = 16.dp * (1f - scrollBehavior.scrollFraction))
                .fillMaxSize()
                .clip(
                    shape = DownArcShape(8.dp * (1f - scrollBehavior.scrollFraction))
                ), url = viewState.playlist?.coverUrl
        ) {
            isLight = DynamicStatusBar.isLight
        }
        //歌单详情
        if (viewState.state.isLoading && null == viewState.playlist) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            CompositionLocalProvider(LocalContentColor provides color) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .height(100.dp)
                        .fillMaxSize()
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
                        Text(text = viewState.playlist?.name ?: "")
                        Text(text = viewState.playlist?.creator?.nickname ?: "")
                        Text(text = viewState.playlist?.description ?: "")
                    }
                }
            }
            FloatActionBar(modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.7f).height(60.dp)
                .graphicsLayer {
                    alpha = contentAlpha
                })
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
    val scope = rememberCoroutineScope()
    var job: Job? = remember(scope) {
        null
    }
    CoilImage(
        modifier = modifier.onSizeChanged { },
        url = url,
        onLoading = {},
        builder = {
            transformations(
                BlurTransformation(
                    context = LocalContext.current,
                    radius = 25f,
                    sampling = 10f
                )
            )
        })
    SideEffect {
        if (job?.isActive == true) {
            job?.cancel()
        }
        job = scope.launch {
            delay(200)
            sideEffect()
        }
    }
}

@Composable
fun FloatActionBar(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        tonalElevation = 8.dp,
        shadowElevation = 1.dp,
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
        }
    }
}

@Composable
fun SongsItem(
    modifier: Modifier = Modifier,
    song: Songs.Song,
    index: Int,
    iconColor: Color = LocalContentColor.current
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