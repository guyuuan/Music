package cn.chitanda.music.ui.widget

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest

private const val TAG = "AsyncImage"

/**
 *@author: Chen
 *@createTime: 2021/8/31 16:55
 *@description:
 **/
//@ExperimentalCoilApi
//@Composable
//fun CoilImage(
//    url: Any?,
//    modifier: Modifier = Modifier,
//    contentDescription: String? = null,
//    alignment: Alignment = Alignment.Center,
//    contentScale: ContentScale = ContentScale.Crop,
//    alpha: Float = DefaultAlpha,
//    shape: Shape? = null,
//    colorFilter: ColorFilter? = null,
//    builder: ImageRequest.Builder.() -> Unit = {},
//    onSuccess: (() -> Unit)? = null,
//    onError: @Composable () -> Unit = {
//    },
//    onLoading: @Composable () -> Unit = {
//        CircularProgressIndicator()
//    }
//) {
//
//}