package cn.chitanda.music.ui.widget.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    itemSpacing: Dp = 0.dp,
    autoLooper: Boolean = true,
    looperTime: Long = 5000L,
    isVertical: Boolean = false, infiniteLoop: Boolean = true,
    indicatorPaddingValues: PaddingValues = EmptyPaddingValue,
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
    val pageCount = data.size
    val startIndex = Int.MAX_VALUE / 2
    val pagerState =
        rememberPagerState(initialPage = if (infiniteLoop) startIndex else 0)
    var isDragEvent by remember {
        mutableStateOf(false)
    }
    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(pagerState.currentPage) {
                    awaitPointerEventScope {
                        while (true) {
                            //PointerEventPass.Initial - 本控件优先处理手势，处理后再交给子组件
                            val event = awaitPointerEvent(PointerEventPass.Initial)
                            //获取到第一根按下的手指
                            val dragEvent = event.changes.firstOrNull()
                            when {
                                //当前移动手势是否已被消费
                                dragEvent!!.isConsumed -> {
                                    return@awaitPointerEventScope
                                }
                                //是否已经按下(忽略按下手势已消费标记)
                                dragEvent.changedToDownIgnoreConsumed() -> {
                                    isDragEvent = true
                                }
                                //是否已经抬起(忽略按下手势已消费标记)
                                dragEvent.changedToUpIgnoreConsumed() -> {
                                    isDragEvent = false
                                }
                            }
                        }
                    }
                },
            count = if (infiniteLoop) Int.MAX_VALUE else data.size,
            contentPadding = itemPaddingValues,
            itemSpacing = itemSpacing
        ) { index ->
            val page = if (infiniteLoop) (index - startIndex).floorMod(pageCount) else index
            Box(
                modifier = Modifier
                    .fillMaxSize()
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
                    indicatorItem(
                        it == if (infiniteLoop) (pagerState.currentPage - startIndex).floorMod(
                            pageCount
                        ) else pagerState.currentPage
                    )
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
                    indicatorItem(
                        it == if (infiniteLoop) (pagerState.currentPage - startIndex).floorMod(
                            pageCount
                        ) else pagerState.currentPage
                    )
                }
            }
        }
    }
    if (autoLooper) {
        val animScope = rememberCoroutineScope()
        LaunchedEffect(key1 = pagerState.currentPage, key2 = isDragEvent) {
            if (isDragEvent) return@LaunchedEffect
            delay(looperTime)
            animScope.launch {
                pagerState.animateScrollToPage((pagerState.currentPage + 1) % if (infiniteLoop) Int.MAX_VALUE else pageCount)
            }
        }
    }
}

private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

val EmptyPaddingValue = PaddingValues(0.dp)