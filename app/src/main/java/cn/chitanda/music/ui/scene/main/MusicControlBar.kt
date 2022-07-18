package cn.chitanda.music.ui.scene.main

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChangedIgnoreConsumed
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cn.chitanda.music.R
import cn.chitanda.music.media.extensions.displayIconUri
import cn.chitanda.music.media.extensions.displaySubtitle
import cn.chitanda.music.media.extensions.displayTitle
import cn.chitanda.music.media.extensions.duration
import cn.chitanda.music.media.extensions.isPlaying
import cn.chitanda.music.media.extensions.isSeeking
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author: Chen
 * @createTime: 2022/7/18 09:47
 * @description:
 **/
private const val TAG = "MusicControlBar"

@Composable
fun MusicControlBar(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    tonalElevation: Dp = 3.dp,
    nowPlaying: MediaMetadataCompat,
    onPause: () -> Unit = {},
    onResume: () -> Unit = {},
    toNext: () -> Unit = {},
    toPrevious: () -> Unit = {},
    seekTo: (Float) -> Unit = {},
    onClick:()->Unit,
    playbackState: PlaybackStateCompat,
    currentPosition: Long
) {
    val coroutineScope = rememberCoroutineScope()
    var width by remember {
        mutableStateOf(0)
    }
    val percent by derivedStateOf {
        currentPosition.toFloat() / nowPlaying.duration
    }

    var isDragging by remember {
        mutableStateOf(false)
    }
    var dragPosition by remember {
        mutableStateOf(0f)
    }
    Surface(
        modifier = modifier
            .onSizeChanged {
                width = it.width
            }
            .pointerInput(isDragging) {
                awaitPointerEventScope {
                    while (true) {
//                        PointerEventPass.Initial - 本控件优先处理手势，处理后再交给子组件
                        val event = awaitPointerEvent()
                        //获取到第一根按下的手指
                        val dragEvent = event.changes.firstOrNull()
                        when {
                            //当前移动手势是否已被消费
                            dragEvent!!.isConsumed -> {
                                isDragging = false
                            }

                            dragEvent.positionChangedIgnoreConsumed() -> {
                                if (!isDragging) {
                                    Log.d(TAG, "drag start: ")
                                    isDragging = true
                                }
                                dragPosition = dragEvent.position.x
                            }
                            //是否已经抬起
                            dragEvent.changedToUp() -> {
                                if (isDragging) {
                                    Log.d(TAG, "drag end: ${dragEvent.position}")
                                    dragPosition = dragEvent.position.x
                                    seekTo(dragPosition / width)
                                    coroutineScope.launch {
                                        //等待seekto完成后
                                        delay(200)
                                        isDragging = false
                                    }
                                }else{
                                    onClick()
                                }
                                return@awaitPointerEventScope
                            }
                        }
                    }
                }
            },
        shape = shape,
        tonalElevation = tonalElevation
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp), contentAlignment = Alignment.CenterStart
        ) {
            val animPercent by
            animateFloatAsState(
                targetValue = if (isDragging || playbackState.isSeeking) dragPosition / width else percent,
                animationSpec = tween(durationMillis = 100, easing = LinearEasing)
            )
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceColorAtElevation(
                            LocalAbsoluteTonalElevation.current + 30.dp
                        ),
                    )
                    .fillMaxHeight()
                    .fillMaxWidth(animPercent)
                    .align(Alignment.CenterStart)
            )
            Row(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = nowPlaying.displayIconUri,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f, true)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    contentDescription = nowPlaying.displayTitle
                )
                Column(
                    modifier = Modifier.fillMaxHeight().weight(3f),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "${nowPlaying.displayTitle}",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${nowPlaying.displaySubtitle}",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = toPrevious, modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(id =  R.drawable.ic_skip_previous),
                        contentDescription = null
                    )
                }

                IconButton(
                    onClick = {
                        if (playbackState.isPlaying) {
                            onPause()
                        } else {
                            onResume()
                        }
                    }, modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(id = if (playbackState.isPlaying) R.drawable.ic_pause else R.drawable.ic_play_arrow),
                        contentDescription = null
                    )
                }

                IconButton(
                    onClick = toNext, modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_skip_next),
                        contentDescription = null
                    )
                }

            }
        }
    }
}