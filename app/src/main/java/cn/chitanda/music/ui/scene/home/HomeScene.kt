package cn.chitanda.music.ui.scene.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cn.chitanda.music.R
import cn.chitanda.music.http.bean.HomeBanner
import cn.chitanda.music.http.bean.HomeData
import cn.chitanda.music.http.bean.MLogExtInfo
import cn.chitanda.music.http.bean.SubTitleType
import cn.chitanda.music.http.moshi.moshi
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.LocalUserViewModel
import cn.chitanda.music.ui.scene.PageState
import cn.chitanda.music.ui.widget.CoilImage
import cn.chitanda.music.ui.widget.banner.Banner
import cn.chitanda.music.utils.collectPartAsState
import cn.chitanda.music.utils.toUnitString
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types

/**
 *@author: Chen
 *@createTime: 2021/9/2 10:19
 *@description:
 **/
private const val TAG = "HomeScene"

@ExperimentalMaterial3Api
@SuppressLint("CheckResult")
@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun HomeScene(navController: NavController = LocalNavController.current) {
    val viewModel: HomeSceneViewModel = hiltViewModel()
    val user by LocalUserViewModel.current.user.collectAsState()
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }
    val viewState by viewModel.viewState.collectAsState()
    val appBarColors = TopAppBarDefaults.smallTopAppBarColors()
    val statusBarPadding =
        rememberInsetsPaddingValues(insets = LocalWindowInsets.current.statusBars, applyTop = true)
    val swiperRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                modifier = Modifier
                    .background(color = appBarColors.containerColor(scrollFraction = scrollBehavior.scrollFraction).value)
                    .padding(top = statusBarPadding.calculateTopPadding() * (1f - scrollBehavior.scrollFraction)),
                scrollBehavior = scrollBehavior,
                colors = appBarColors,
                title = { Text("${stringResource(R.string.text_home_welcome_title)}${user.json?.data?.nickname}") },
            )
        },
    ) {
        SwipeRefresh(state = swiperRefreshState, onRefresh = {
            viewModel.loadHomeData()
        }) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                item {
                    HomeBanners(
                        viewModel, modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }

                item {
                    HomeRoundIconList(viewModel)
                }

                item {
                    RecommendPlayList(
                        viewModel,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp)
                    )
                }

                item {
                    RecommendSongList(
                        viewModel,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp)
                    )
                }

                item {
                    MLogList(
                        viewModel, modifier = Modifier
                            .fillMaxWidth(), contentPadding = PaddingValues(16.dp)
                    )
                }

            }
        }
    }
    val cxt = LocalContext.current
    LaunchedEffect(key1 = viewState) {
        when (val state = viewState.state) {
            is PageState.Success -> swiperRefreshState.isRefreshing = false
            is PageState.Loading -> swiperRefreshState.isRefreshing = true
            is PageState.Empty -> {
                swiperRefreshState.isRefreshing = true
                viewModel.loadHomeData()
            }
            is PageState.Error -> {
                swiperRefreshState.isRefreshing = false
                Toast.makeText(cxt, state.tr.toString(), Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

}


@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
private fun HomeBanners(viewModel: HomeSceneViewModel, modifier: Modifier = Modifier) {
//    val banners by remember { derivedStateOf { getBannerData(data.extInfo) } }
    val banners by viewModel.viewState.collectPartAsState(part = HomeViewState::banner)
    if (banners.isNotEmpty()) {
        Banner(
            data = banners,
            modifier = modifier,
            indicatorPaddingValues = PaddingValues(24.dp)
        ) { item ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.BottomEnd
            ) {
                CoilImage(
                    url = item.pic,
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RoundedCornerShape(8.dp),
                    contentDescription = null
                )
                Surface(
                    color = item.titleColor.color,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(topStart = 8.dp)
                ) {
                    Text(
                        text = item.typeTitle.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                    )
                }
            }
        }
    }

}

private fun getMLogData(extInfo: Any?) = try {
    if (null == extInfo) {
        emptyList()
    } else {
        val type = Types.newParameterizedType(
            List::class.java,
            Any::class.java
        )
        val adapter: JsonAdapter<List<Any>> =
            moshi.adapter(type)
        val str = adapter.toJson(extInfo as List<Any>)
        val extInfoAdapter: JsonAdapter<List<MLogExtInfo>> =
            moshi.adapter(Types.newParameterizedType(List::class.java, MLogExtInfo::class.java))
        extInfoAdapter.fromJson(str) ?: emptyList()
    }
} catch (e: Exception) {
    Log.e(TAG, "getMLogData: ", e)
    emptyList()
}

private fun getBannerData(extInfo: Any?) = try {
    if (null == extInfo) {
        emptyList()
    } else {
        val type = Types.newParameterizedType(
            Map::class.java,
            Any::class.java, Any::class.java
        )
        val adapter: JsonAdapter<Map<*, *>> =
            moshi.adapter(type)
        val str = adapter.toJson(extInfo as Map<*, *>)
        moshi.adapter(HomeBanner::class.java).fromJson(str)?.banners ?: emptyList()
    }
} catch (e: Exception) {
    Log.e(TAG, "getBannerData: ", e)
    emptyList()
}

@ExperimentalCoilApi
@Composable
fun HomeRoundIconList(
    viewModel: HomeSceneViewModel,
    modifier: Modifier = Modifier,
) {
    val data by viewModel.viewState.collectPartAsState(part = HomeViewState::icons)
    LazyRow(modifier = modifier) {
        itemsIndexed(data) { i, item ->
            Column(
                Modifier.fillMaxHeight() then if (i == data.lastIndex) Modifier else Modifier.padding(
                    end = 45.dp
                ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CoilImage(
                    url = item.iconUrl,
                    modifier = Modifier
                        .size(45.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            shape = CircleShape
                        ),
                    contentDescription = item.name,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
                    onLoading = {}
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = item.name, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun RecommendPlayList(
    viewModel: HomeSceneViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val data by viewModel.viewState.collectPartAsState(part = HomeViewState::playlist)
    val list = data.second
    if (list.isEmpty()) return
    TitleColumn(
        modifier = modifier,
        title = data.first?.subTitle?.title.toString(),
        buttonText = data.first?.button?.text,
        contentPadding = contentPadding
    ) { padding ->
        LazyRow(modifier = Modifier.fillMaxWidth(), contentPadding = padding) {
            itemsIndexed(list) { i, resource ->
                Column(
                    Modifier
                        .padding(end = if (i != list.lastIndex) 16.dp else 0.dp)
                        .width(110.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    ) {
                        CoilImage(
                            url = resource.uiElement?.image?.imageUrl,
                            contentDescription = resource.resourceType,
                            modifier = Modifier.fillMaxSize(),
                            shape = RoundedCornerShape(8.dp)
                        )
                        PlayCount(
                            modifier = Modifier
                                .padding(top = 4.dp, end = 4.dp)
                                .align(Alignment.TopEnd),
                            playCount = resource.resourceExtInfo?.playCount ?: 0
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .size(8.dp)
                    )
                    Text(
                        text = resource.uiElement?.mainTitle?.title.toString(),
                        style = MaterialTheme.typography.labelMedium, maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

        }
    }
}

@Composable
fun PlayCount(modifier: Modifier, playCount: Long) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
        contentColor = Color.White,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 1.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.PlayArrow,
                contentDescription = null,
                Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = playCount.toUnitString(),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
fun RecommendSongList(
    viewModel: HomeSceneViewModel,
    modifier: Modifier,
    contentPadding: PaddingValues
) {
    val data by viewModel.viewState.collectPartAsState(part = HomeViewState::songList)
    val songs = data.second
    if (songs.isEmpty()) return
    TitleColumn(
        modifier = modifier,
        title = data.first?.subTitle?.title.toString(),
        buttonText = data.first?.button?.text.toString(),
        contentPadding = contentPadding
    ) { padding ->
        var itemWidth by remember {
            mutableStateOf(IntSize.Zero.width)
        }
        LazyRow(
            Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    itemWidth = it.width
                },
            contentPadding = padding
        ) {
            if (itemWidth > IntSize.Zero.width) {
                songs.forEach { song ->
                    item {
                        Column(Modifier.width(with(LocalDensity.current) { itemWidth.toDp() * 0.9f })) {
                            song.resources?.forEachIndexed { i, r ->
                                SongItem(
                                    song = r,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 16.dp),
                                    i < song.resources.lastIndex
                                )
                                if (i < song.resources.lastIndex) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}


@ExperimentalCoilApi
@Composable
fun MLogList(
    viewModel: HomeSceneViewModel,
    modifier: Modifier,
    contentPadding: PaddingValues
) {
    val data by viewModel.viewState.collectPartAsState(part = HomeViewState::mLog)
    val mLogs = data.second
    if (mLogs.isEmpty()) return
    TitleColumn(
        modifier = modifier,
        title = data.first?.subTitle?.title.toString(),
        buttonText = data.first?.button?.text.toString(),
        contentPadding = contentPadding
    ) { padding ->
        LazyRow(modifier = Modifier.fillMaxWidth(), contentPadding = padding) {
            itemsIndexed(mLogs) { i, mLog ->
                MLogItem(
                    modifier = Modifier
                        .padding(end = if (i < mLogs.lastIndex) 16.dp else 0.dp)
                        .width(120.dp),
                    data = mLog.resource
                )
            }
        }

    }
}

@ExperimentalCoilApi
@Composable
fun MLogItem(
    modifier: Modifier = Modifier,
    data: MLogExtInfo.Resource,
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.75f)
        ) {
            CoilImage(url = data.mlogBaseData?.coverUrl, shape = RoundedCornerShape(8.dp))
            PlayCount(
                modifier = Modifier
                    .padding(top = 4.dp, end = 4.dp)
                    .align(Alignment.TopEnd),
                playCount = data.mlogExtVO?.playCount ?: 0
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = data.mlogBaseData?.text.toString(),
            style = MaterialTheme.typography.labelMedium,
            maxLines = if (data.mlogExtVO?.specialTag.isNullOrEmpty()) 2 else 1,
            overflow = TextOverflow.Ellipsis
        )
        data.mlogExtVO?.specialTag?.let { TagText(tag = it) }
    }
}

@ExperimentalCoilApi
@Composable
fun SongItem(
    song: HomeData.Data.Block.Creative.Resource,
    modifier: Modifier = Modifier,
    showSpacer: Boolean
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                url = song.uiElement?.image?.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.height(50.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = song.uiElement?.mainTitle?.title.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    val artists =
                        song.resourceExtInfo?.artists?.joinToString(separator = "/") { it.name.toString() }
                    val style = MaterialTheme.typography.labelSmall
                    Text(
                        text = "- $artists",
                        style = style.copy(style.color.copy(alpha = 0.4f)),
                        maxLines = 1
                    )
                }
                song.uiElement?.subTitle?.title?.let {
                    val style = MaterialTheme.typography.labelMedium
                    when (song.uiElement.subTitle.titleType) {
                        SubTitleType.FromComment -> {
                            Text(
                                text = it,
                                style = style.copy(style.color.copy(alpha = 0.6f)),
                                maxLines = 1, overflow = TextOverflow.Ellipsis
                            )
                        }
                        SubTitleType.TAG -> {
                            TagText(tag = it)
                        }
                    }
                }
            }
        }
        if (showSpacer) {
            Spacer(
                modifier = Modifier
                    .padding(start = 60.dp, top = 8.dp)
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(Color.LightGray.copy(alpha = 0.5f))
            )
        }
    }
}

@Composable
fun TagText(modifier: Modifier = Modifier, tag: String) {
    Text(
        text = tag,
        style = MaterialTheme.typography.labelSmall.copy(
            color = MaterialTheme.colorScheme.tertiary
        ),
        maxLines = 1, overflow = TextOverflow.Ellipsis,
        modifier = modifier then Modifier
            .background(
                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(vertical = 2.dp, horizontal = 4.dp)
    )
}

@Composable
fun TitleColumn(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    title: String,
    buttonText: String?,
    content: @Composable (PaddingValues) -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        elevation = 0.dp,
        modifier = modifier
    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = contentPadding.calculateStartPadding(
                            LocalLayoutDirection.current
                        )
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
                buttonText?.let {
                    TextButton(
                        modifier = Modifier.defaultMinSize(48.dp, 20.dp),
                        onClick = { },
                        border = BorderStroke(
                            0.5.dp,
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f)
                        ),
                        shape = RoundedCornerShape(16.dp),
//                    contentPadding = PaddingValues(vertical = 2.dp, horizontal = 4.dp)
                    ) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                }
            }
            content(contentPadding)
        }
    }
}