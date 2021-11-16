package cn.chitanda.music.ui.widget

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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

private const val TAG = "CoilImage"

/**
 *@author: Chen
 *@createTime: 2021/8/31 16:55
 *@description:
 **/
@ExperimentalCoilApi
@Composable
fun CoilImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = DefaultAlpha,
    shape: Shape? = null,
    colorFilter: ColorFilter? = null,
    imageShape: Shape? = null,
    onError: @Composable () -> Unit = {
    },
    onLoading: @Composable () -> Unit = {
        CircularProgressIndicator()
    }

) {
    val painter = rememberImagePainter(data = url)
    Box(
        modifier = modifier then if (shape != null) Modifier.clip(shape) else Modifier,
        contentAlignment = Alignment.Center
    ) {
        val imageModifier =
            Modifier.fillMaxSize() then if (imageShape != null) Modifier.clip(imageShape) else Modifier
        Image(
            painter = painter, contentDescription = contentDescription,
            modifier = imageModifier,
            alignment, contentScale,
            alpha, colorFilter
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