package cn.chitanda.music.ui.scene.mine

import android.content.res.Resources
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cn.chitanda.music.http.bean.UserInfo
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.LocalUserViewModel
import cn.chitanda.music.ui.scene.UserViewModel
import cn.chitanda.music.ui.widget.CoilImage
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import kotlin.math.roundToInt

/**
 *@author: Chen
 *@createTime: 2021/9/2 10:18
 *@description:
 **/

private const val TAG = "MineScene"

@ExperimentalCoilApi
@Composable
fun MineScene(
    navController: NavController = LocalNavController.current,
    viewModel: UserViewModel = LocalUserViewModel.current
) {
    val user by viewModel.user.collectAsState()
    user.json?.let {
        NestedScrollBody(it)
    }
}

@ExperimentalCoilApi
@Composable
private fun NestedScrollBody(user: UserInfo) {
    val maxHeightPx = remember { Resources.getSystem().displayMetrics.widthPixels / 5 * 4 }
    val minHeightPx = with(LocalDensity.current) {
        with(LocalWindowInsets.current) {
            (statusBars.top - statusBars.bottom) + 56.dp.toPx().roundToInt()
        }
    }
    var imageHeight by rememberSaveable { mutableStateOf(maxHeightPx) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(connection = object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    imageHeight = (imageHeight + delta)
                        .roundToInt()
                        .coerceIn(
                            minimumValue = minHeightPx,
                            maximumValue = maxHeightPx
                        )
                    return Offset.Zero
                }
            })
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            contentPadding = PaddingValues(top = with(LocalDensity.current) { maxHeightPx.toDp() })
        ) {
            item {
                Row(modifier = Modifier.padding(  24.dp))
                {
                    CoilImage(
                        url = user.profile?.avatarUrl ?: "",
                        shape = CircleShape,
                        modifier = Modifier.size(width = 70.dp, height = 70.dp),
                    )
                }
            }
            items(100) { i ->
                Text(text = i.toString(), modifier = Modifier.padding(16.dp))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { imageHeight.toDp() })
        ) {
            CoilImage(
                url = user.profile?.backgroundUrl
                    ?: ""
            )
            val appBarColor by animateColorAsState(targetValue = if (imageHeight > minHeightPx) Color.Transparent else MaterialTheme.colors.primary)
            Box(
                modifier = Modifier
                    .background(color = appBarColor)
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth()
                    .height(with(LocalDensity.current) { minHeightPx.toDp() })
                    .statusBarsPadding(), contentAlignment = Alignment.CenterStart
            ) {
                Text(user.profile?.nickname.toString(), color = Color.White, fontSize = 18.sp)
            }
        }

    }
}