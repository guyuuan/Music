package cn.chitanda.music.ui.scene.mine

import android.content.res.Resources
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cn.chitanda.music.http.DataState
import cn.chitanda.music.http.bean.UserProfile
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.LocalUserViewModel
import cn.chitanda.music.ui.scene.UserViewModel
import cn.chitanda.music.ui.widget.CoilImage
import cn.chitanda.music.ui.widget.nestedscroll.rememberNestedScrollAppBarConnection
import cn.chitanda.music.ui.widget.nestedscroll.rememberNestedScrollAppBarState
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
    LaunchedEffect(key1 = user) {
        if (user.status == DataState.STATE_CREATE) {
            viewModel.fetchUserInfo()
        }
    }
}

@ExperimentalCoilApi
@Composable
private fun NestedScrollBody(user: UserProfile) {
    val max = remember { Resources.getSystem().displayMetrics.widthPixels }
    val min = with(LocalDensity.current) {
        with(LocalWindowInsets.current) {
            (statusBars.top - statusBars.bottom) + 64.dp.toPx()
        }.roundToInt()
    }
    val appBarState = rememberNestedScrollAppBarState(
        appBarHeight = max,
        minHeight = min,
        maxHeight = max
    )
    val nestedScrollConnection = rememberNestedScrollAppBarConnection(appBarState)
    val dispatcher = remember {
        NestedScrollDispatcher()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(
                connection = nestedScrollConnection,
                dispatcher = dispatcher
            )
    ) {
        Box(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .height(with(LocalDensity.current) { appBarState.height.toDp() })
        ) {
            CoilImage(
                url = user.profile?.backgroundUrl
            )
            val appBarColor by animateColorAsState(targetValue = if (appBarState.height > appBarState.minHeight) Color.Transparent else MaterialTheme.colors.primary)
            Box(
                modifier = Modifier
                    .background(color = appBarColor)
                    .padding(horizontal = 18.dp)
                    .fillMaxSize()
//                    .height(with(LocalDensity.current) { appBarState.minHeight.toDp() })
                    .statusBarsPadding(), contentAlignment = Alignment.Center
            ) {
                Text(
                    user.profile?.nickname.toString(),
                    color = Color.White,
                    fontSize = (18 + 18 * appBarState.scrollPercent).sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(top = with(LocalDensity.current) {
                (appBarState.height.toDp() - 76.dp)
                    .coerceIn(
                        appBarState.minHeight.toDp(),
                        appBarState.maxHeight.toDp() - 76.dp
                    )
            })
        ) {
            item {
                Surface(
                    modifier = Modifier.padding(16.dp),
                    elevation = 8.dp,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(16.dp)
                    )
                    {
                        CoilImage(
                            url = user.profile?.avatarUrl,
                            shape = CircleShape,
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f, false),
                        )

                        Column(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                UserInfoItem(top = user.profile?.follows.toString(), bottom = "粉丝")
                                UserInfoItem(
                                    top = user.profile?.followeds.toString(),
                                    bottom = "关注"
                                )
                                UserInfoItem(top = user.profile?.vipType.toString(), bottom = "等级")
                            }

                            TextButton(
                                onClick = {},
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(30.dp),
                                contentPadding = PaddingValues(0.dp),
                                border = BorderStroke(0.8.dp, color = Color.Gray)
                            ) {
                                Text(text = "编辑信息")
                            }
                        }
                    }
                }
            }
            items(7) { i ->
                Surface(
                    modifier = Modifier.padding(16.dp),
                    elevation = 8.dp,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = i.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun UserInfoItem(top: String?, bottom: String) {
    Column {
        Text(text = top.toString(), style = MaterialTheme.typography.body1, color = Color.Black)
        Text(text = bottom, style = MaterialTheme.typography.subtitle2)
    }
}