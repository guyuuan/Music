package cn.chitanda.music.ui.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

/**
 *@author: Chen
 *@createTime: 2021/9/2 13:23
 *@description:
 **/
@ExperimentalPagerApi
@Composable
fun <T> Banner(
    data: List<T>,
    modifier: Modifier = Modifier,
    itemPaddingValues: PaddingValues = EmptyPaddingValue,
    autoLooper: Boolean = true,
    looperTime: Long = 5000L,
    isVertical: Boolean = false,
    indicatorPaddingValues: PaddingValues = EmptyPaddingValue,
    initialOffscreenLimit: Int = 1,
    indicatorItem: @Composable (Boolean) -> Unit = {
        Box(
            modifier = Modifier
                .alpha(if (it) 0.9f else 0.5f)
                .padding(4.dp)
                .size(6.dp)
                .background(Color.White, shape = CircleShape)
        )
    },
    indicatorAlignment: Alignment = Alignment.BottomCenter,
    contents: @Composable (T) -> Unit
) {
    val pagerState =
        rememberPagerState(
            pageCount = data.size,
            initialOffscreenLimit = initialOffscreenLimit,
            infiniteLoop = true
        )
    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(itemPaddingValues)
            ) {
                contents(data[page])
            }
        }
        if (isVertical) {
            Column(
                Modifier
                    .padding(indicatorPaddingValues)
                    .fillMaxHeight(0.6f)
                    .align(indicatorAlignment),
                verticalArrangement = Arrangement.Center
            ) {
                repeat(data.size) {
                    indicatorItem(it == pagerState.currentPage)
                }
            }
        } else {
            Row(
                Modifier
                    .padding(indicatorPaddingValues)
                    .fillMaxWidth(0.6f)
                    .align(indicatorAlignment),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(data.size) {
                    indicatorItem(it == pagerState.currentPage)
                }
            }
        }
    }
    if (autoLooper) {
        LaunchedEffect(key1 = pagerState.currentPage) {
            delay(looperTime)
            pagerState.animateScrollToPage((pagerState.currentPage + 1))
        }
    }
}

private val EmptyPaddingValue = PaddingValues(0.dp)