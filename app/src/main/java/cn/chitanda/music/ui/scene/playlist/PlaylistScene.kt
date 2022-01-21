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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cn.chitanda.dynamicstatusbar.DynamicStatusBar
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
import kotlinx.coroutines.delay

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
    Box {
        Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
            Column {
                FoldableTopAppBar(
                    scrollBehavior = scrollBehavior, viewState = viewState
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(100) { i ->
                        Text(text = i.toString())
                    }
                }
            }
            LaunchedEffect(key1 = playlist) {
                if (!playlist.isNullOrEmpty()) {
                    viewModel.getPlaylistDetail(playlist)
                }
            }
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
        CoilImage(
            modifier = Modifier
                .padding(bottom = 16.dp * (1f - scrollBehavior.scrollFraction))
                .fillMaxSize()
                .clip(
                    shape = DownArcShape(8.dp * (1f - scrollBehavior.scrollFraction))
                ),
            url = viewState.playlist?.coverUrl,
            onLoading = {},
            builder = {
                crossfade(true)
                transformations(
                    BlurTransformation(
                        context = LocalContext.current,
                        radius = 25f,
                        sampling = 10f
                    )
                )
            })
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
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .graphicsLayer {
                        alpha = contentAlpha
                    },
                tonalElevation = 8.dp,
                shadowElevation = 1.dp,
                shape = CircleShape
            ) {
                Row(
                    modifier = Modifier
                        .width(240.dp)
                        .height(50.dp),
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
            LaunchedEffect(key1 = Unit) {
                delay(400)
                isLight = DynamicStatusBar.isLight
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