package cn.chitanda.music.ui.scene.discovery

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cn.chitanda.music.R
import cn.chitanda.music.http.bean.HomeBanner
import cn.chitanda.music.http.bean.HomeData
import cn.chitanda.music.http.bean.RCMDShowType
import cn.chitanda.music.http.moshi.moshi
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.banner.Banner
import cn.chitanda.music.ui.scene.home.CoilImage
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
private const val TAG = "DiscoveryScene"

@SuppressLint("CheckResult")
@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun FindScene(navController: NavController = LocalNavController.current) {
    val viewModel: DiscoverySceneViewModel = hiltViewModel()
    val data by viewModel.homeData.observeAsState()
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
                if (data == null) {
                    CircularProgressIndicator()
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(data?.json?.data?.blocks ?: emptyList()) { i, block ->
                            when (block.showType) {
                                RCMDShowType.Banner -> HomeBanners(
                                    data = block, modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .align(Alignment.TopCenter)
                                )
                                RCMDShowType.PlayList -> {
                                }
                                RCMDShowType.PlayableMLog -> {
                                }
                                RCMDShowType.SongList -> {
                                }
                                RCMDShowType.Unkonw -> {
                                }
                                null -> {
                                }
                            }
                            if (i == 0) {
                                Text(text = "今日推荐")
                            }
                        }
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