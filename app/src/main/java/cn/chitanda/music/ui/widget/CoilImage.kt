package cn.chitanda.music.ui.widget

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest

private const val TAG = "CoilImage"

/**
 *@author: Chen
 *@createTime: 2021/8/31 16:55
 *@description:
 **/
@ExperimentalCoilApi
@Composable
inline fun CoilImage(
    url: Any?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = DefaultAlpha,
    shape: Shape? = null,
    colorFilter: ColorFilter? = null,
    builder: ImageRequest.Builder.() -> Unit = {},
    crossinline onError: @Composable () -> Unit = {
    },
    crossinline onLoading: @Composable () -> Unit = {
        CircularProgressIndicator()
    }
) {
    val painter = rememberImagePainter(data = url, builder = builder)
    Box(
        modifier = modifier then if (shape != null) Modifier.clip(shape) else Modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter, contentDescription = contentDescription,
            modifier = Modifier.matchParentSize(),
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha, colorFilter = colorFilter
        )
        Crossfade(targetState = painter.state) { state ->
            when (state) {
                is ImagePainter.State.Empty -> {

                }
                is ImagePainter.State.Loading -> {
                    onLoading()
                }
                is ImagePainter.State.Success -> {
                }
                is ImagePainter.State.Error -> {
                    onError()
                }
            }
        }
    }
}