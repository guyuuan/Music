package cn.chitanda.music.ui.scene.play_detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cn.chitanda.music.MusicViewModel
import cn.chitanda.music.R
import cn.chitanda.music.media.extensions.displayIconUri
import cn.chitanda.music.ui.LocalMusicViewModel
import cn.chitanda.music.ui.LocalNavController
import coil.compose.AsyncImage

/**
 * @author: Chen
 * @createTime: 2022/7/18 14:24
 * @description:
 **/

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayDetailScene(
    navController: NavController = LocalNavController.current,
    musicViewModel: MusicViewModel = LocalMusicViewModel.current
) {
    val playbackState by musicViewModel.playbackState.observeAsState()
    val nowPlaying by musicViewModel.nowPlaying.observeAsState()

    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        nowPlaying?.let {
            AnimatedContent(
                targetState = it.displayIconUri,
                modifier = Modifier.blur(100.dp),
                transitionSpec = {
                    fadeIn(animationSpec = tween(1000), initialAlpha = 0.4f) with
                            fadeOut(animationSpec = tween(1000), targetAlpha = 0.6f)

                }) { url ->
                AsyncImage(
                    model = url, modifier = Modifier
                        .fillMaxSize()
                        .scale(scale),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        }
        IconButton(
            onClick = musicViewModel::toNext,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 80.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_skip_next),
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}

val DpToVector: TwoWayConverter<Dp, AnimationVector1D> =
    TwoWayConverter({ AnimationVector1D(it.value) }, { it.value.dp })