package cn.chitanda.music.ui.scene.video

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cn.chitanda.music.http.bean.VideoList
import cn.chitanda.music.http.bean.VideoType
import cn.chitanda.music.ui.LocalNavController
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

/**
 * @author: Chen
 * @createTime: 2021/11/24 4:07 下午
 * @description:
 **/
private const val TAG = "VideoScene"

@ExperimentalPagerApi
@Composable
fun MessageScene(navController: NavController = LocalNavController.current) {
    val viewModel = hiltViewModel<VideoViewModel>()
    val pages by viewModel.type.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    Column(modifier = Modifier.fillMaxSize()) {
        pages.json?.data?.let { types ->
            if (types.isEmpty()) return@let
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                Modifier.statusBarsPadding()
            ) {
                types.forEachIndexed { index, type ->
                    Tab(
                        text = { Text(type.name.toString()) },
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    )
                }
            }
            HorizontalPager(
                count = types.size,
                state = pagerState,
            ) { i ->
                Box(modifier = Modifier.fillMaxSize()) {
                    VideoPageItem(
                        type = types[0],
                        index = i,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun VideoPageItem(type: VideoType.Data, index: Int, viewModel: VideoViewModel) {
    var offset by remember {
        mutableStateOf(0)
    }
    val data by viewModel.getVideoData(type = type.id)
    val listState = rememberLazyListState()
    LazyColumn(
        Modifier.fillMaxSize(),
        state = listState
    ) {
        itemsIndexed(data) { i, data ->
            VideoItem(index = i, data = data)
        }
    }

    LaunchedEffect(key1 = data) {
        if (data.isNullOrEmpty()) {
            viewModel.loadVideoByType(type.id, 0.also { offset = it })
        }
    }

    LaunchedEffect(key1 = listState.firstVisibleItemIndex) {
        Log.d(TAG, "VideoPage: ${listState.firstVisibleItemIndex}")
        if (listState.firstVisibleItemIndex > data.size - 20) {
            viewModel.loadVideoByType(type.id, ++offset)
        }
    }
}

@Composable
fun VideoItem(index: Int, data: VideoList.Data) {
    Text(
        text = "$index >>> ${data.info?.title}",
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}