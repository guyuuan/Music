package cn.chitanda.music.ui.scene.find

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.primarySurface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cn.chitanda.music.R
import cn.chitanda.music.http.DataState
import cn.chitanda.music.http.bean.HomeBanner
import cn.chitanda.music.http.bean.HomeData
import cn.chitanda.music.http.bean.HomeRoundIcon
import cn.chitanda.music.http.bean.RCMDShowType
import cn.chitanda.music.http.bean.SubTitleType
import cn.chitanda.music.http.moshi.moshi
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.widget.banner.Banner
import cn.chitanda.music.ui.widget.CoilImage
import cn.chitanda.music.ui.theme.PoHeLv
import cn.chitanda.music.ui.theme.RCMDTagColor
import cn.chitanda.music.utils.toUnitString
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types

/**
 *@author: Chen
 *@createTime: 2021/9/2 10:19
 *@description:
 **/
private const val TAG = "FindScene"

@ExperimentalMaterialApi
@SuppressLint("CheckResult")
@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun FindScene(navController: NavController = LocalNavController.current) {
    val viewModel: DiscoverySceneViewModel = hiltViewModel()
    val data by viewModel.homeData.collectAsState()
    val iconList by viewModel.homeIconList.collectAsState()
    Surface(color = MaterialTheme.colors.primarySurface) {
        Scaffold(topBar = {
            TopAppBar(contentPadding = PaddingValues(horizontal = 8.dp)) {
                Icon(Icons.Default.Menu, contentDescription = "")
                BasicTextField(
                    value = "", onValueChange = {}, modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(0.5f)
                        .padding(horizontal = 16.dp)
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                )
                Icon(painter = painterResource(id = R.drawable.ic_mic), contentDescription = "")
            }
        }, modifier = Modifier.statusBarsPadding()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (data.status) {
                    DataState.STATE_LOADING -> CircularProgressIndicator()
                    DataState.STATE_SUCCESS -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            itemsIndexed(data.json?.data?.blocks ?: emptyList()) { i, block ->
                                when (block.showType) {
                                    RCMDShowType.Banner -> HomeBanners(
                                        data = block, modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .align(Alignment.TopCenter)
                                    )
                                    RCMDShowType.PlayList -> {
                                        RecommendPlayList(
                                            data = block,
                                            modifier = Modifier
                                                .padding(vertical = 16.dp)
                                                .fillMaxWidth(),
                                            contentPadding = PaddingValues(horizontal = 16.dp)
                                        )
                                    }
                                    RCMDShowType.PlayableMLog -> {
                                    }
                                    RCMDShowType.SongList -> {
                                        RecommendSongList(
                                            data = block,
                                            modifier = Modifier
                                                .padding(vertical = 16.dp)
                                                .fillMaxWidth(),
                                            contentPadding = PaddingValues(horizontal = 16.dp)
                                        )
                                    }
                                    RCMDShowType.Unknown, null -> {
                                    }
                                }
                                if (i == 0) {
                                    HomeRoundIconList(
                                        data = iconList.json?.data ?: emptyList(),
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        contentPadding = PaddingValues(horizontal = 16.dp)
                                    )

                                }
                            }
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }
}


@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
private fun HomeBanners(data: HomeData.Data.Block, modifier: Modifier = Modifier) {
    val banners = getBannerData(data.extInfo)
    if (banners != null) {
        Banner(
            data = banners,
            modifier = modifier,
            itemPaddingValues = PaddingValues(16.dp),
            indicatorPaddingValues = PaddingValues(24.dp)
        ) { item ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.BottomEnd
            ) {
                CoilImage(
                    url = item.pic.toString(),
                    modifier = Modifier
                        .fillMaxSize(),
                    imageShape = RoundedCornerShape(8.dp),
                    contentDescription = null
                )
                Surface(
                    color = item.titleColor.color,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(topStart = 8.dp)
                ) {
                    Text(
                        text = item.typeTitle.toString(),
                        style = MaterialTheme.typography.overline,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                    )
                }
            }
        }
    }

}

private fun getBannerData(extInfo: Any?) = try {
    val type = Types.newParameterizedType(
        Map::class.java,
        Any::class.java, Any::class.java
    )
    val adapter: JsonAdapter<Map<*, *>> =
        moshi.adapter(type)
    val str = adapter.toJson(extInfo as Map<*, *>)
    moshi.adapter(HomeBanner::class.java).fromJson(str)?.banners
} catch (e: Exception) {
    Log.e(TAG, "DiscoveryScene: ", e)
    null
}

@ExperimentalCoilApi
@Composable
fun HomeRoundIconList(
    data: List<HomeRoundIcon>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyRow(modifier = modifier, contentPadding = contentPadding) {
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
                            MaterialTheme.colors.primary.copy(alpha = 0.2f),
                            shape = CircleShape
                        ),
                    contentDescription = item.name,
                    colorFilter = ColorFilter.tint(PoHeLv), onLoading = {}
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = item.name, style = MaterialTheme.typography.caption)
            }
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun RecommendPlayList(
    data: HomeData.Data.Block,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    TitleColumn(
        title = data.uiElement?.subTitle?.title.toString(),
        buttonText = data.uiElement?.button?.text,
        modifier = modifier, contentPadding = contentPadding
    ) {
        LazyRow(modifier = Modifier.fillMaxWidth(), contentPadding = contentPadding) {
            val list = data.creatives?.map { it.resources ?: emptyList() }?.flatten() ?: emptyList()
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
                            url = resource.uiElement?.image?.imageUrl.toString(),
                            contentDescription = resource.resourceType,
                            modifier = Modifier.fillMaxSize(),
                            shape = RoundedCornerShape(8.dp)
                        )
                        Surface(
                            modifier = Modifier
                                .padding(top = 2.dp, end = 2.dp)
                                .align(Alignment.TopEnd),
                            shape = RoundedCornerShape(16.dp),
                            color = Color.Black.copy(alpha = 0.3f),
                            contentColor = Color.White,
                        ) {
                            val playCount = resource.resourceExtInfo?.playCount
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
                                    text = playCount?.toUnitString() ?: playCount.toString(),
                                    style = MaterialTheme.typography.overline
                                )
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .size(8.dp)
                    )
                    Text(
                        text = resource.uiElement?.mainTitle?.title.toString(),
                        style = MaterialTheme.typography.body2, maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun RecommendSongList(
    data: HomeData.Data.Block,
    modifier: Modifier,
    contentPadding: PaddingValues
) {
    TitleColumn(
        title = data.uiElement?.subTitle?.title.toString(),
        buttonText = data.uiElement?.button?.text.toString(),
        modifier = modifier, contentPadding = contentPadding
    ) {
        val songs = data.creatives ?: emptyList()
        var itemWidth by remember {
            mutableStateOf(IntSize.Zero.width)
        }
        LazyRow(
            Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    itemWidth = it.width
                }, contentPadding = PaddingValues(horizontal = 16.dp)
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
                url = song.uiElement?.image?.imageUrl.toString(),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = song.uiElement?.mainTitle?.title.toString(),
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    val artists =
                        song.resourceExtInfo?.artists?.joinToString(separator = "/") { it.name.toString() }
                    val style = MaterialTheme.typography.caption
                    Text(
                        text = "- $artists",
                        style = style.copy(style.color.copy(alpha = 0.4f)),
                        maxLines = 1
                    )
                }
                song.uiElement?.subTitle?.title?.let {
                    val style = MaterialTheme.typography.subtitle2
                    when (song.uiElement.subTitle.titleType) {
                        SubTitleType.FromComment -> {
                            Text(
                                text = it,
                                style = style.copy(style.color.copy(alpha = 0.6f)),
                                maxLines = 1, overflow = TextOverflow.Ellipsis
                            )
                        }
                        SubTitleType.TAG -> {
                            Text(
                                text = it,
                                style = style.copy(color = RCMDTagColor, fontSize = 8.sp),
                                maxLines = 1, overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.background(
                                    color = RCMDTagColor.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(4.dp)
                                ).padding(vertical = 2.dp,horizontal = 4.dp)
                            )
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

@ExperimentalMaterialApi
@Composable
fun TitleColumn(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    title: String,
    buttonText: String?,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(
                    start = contentPadding.calculateLeftPadding(LayoutDirection.Ltr),
                    end = contentPadding.calculateRightPadding(LayoutDirection.Ltr)
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )
            buttonText?.let {
                Surface(
                    border = BorderStroke(
                        0.5.dp,
                        MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {},
                    role = Role.Button,
                    indication = rememberRipple()
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )

                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        content()
    }
}