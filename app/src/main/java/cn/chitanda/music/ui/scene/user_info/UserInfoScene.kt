package cn.chitanda.music.ui.scene.user_info

import android.content.res.Resources
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cn.chitanda.music.R
import cn.chitanda.music.http.DataState
import cn.chitanda.music.http.bean.UserProfile
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.LocalUserViewModel
import cn.chitanda.music.ui.scene.LocaleUserViewModel
import cn.chitanda.music.ui.theme.Shapes
import cn.chitanda.music.ui.widget.nestedscroll.rememberNestedScrollAppBarConnection
import cn.chitanda.music.ui.widget.nestedscroll.rememberNestedScrollAppBarState
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage

/**
 *@author: Chen
 *@createTime: 2021/9/2 10:18
 *@description:
 **/


@ExperimentalCoilApi
@Composable
fun UserInfoScene(
    navController: NavController = LocalNavController.current,
    viewModel: LocaleUserViewModel = LocalUserViewModel.current
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
        (WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 64.dp).roundToPx()
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
            AsyncImage(
                model = user.data?.backgroundUrl,
                contentDescription = null
            )
            val appBarColor by animateColorAsState(targetValue = if (appBarState.height > appBarState.minHeight) Color.Transparent else MaterialTheme.colorScheme.primary)
            Box(
                modifier = Modifier
                    .background(color = appBarColor)
                    .padding(horizontal = 18.dp)
                    .fillMaxSize()
//                    .height(with(LocalDensity.current) { appBarState.minHeight.toDp() })
                    .statusBarsPadding(), contentAlignment = Alignment.Center
            ) {
                Text(
                    user.data?.nickname.toString(),
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
                    shape = Shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(16.dp)
                    )
                    {
                        AsyncImage(
                            model = user.data?.avatarUrl,
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f, false).clip(CircleShape),
                            contentDescription = null
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
                                UserInfoItem(
                                    top = user.data?.follows.toString(), bottom = stringResource(
                                        R.string.text_fans
                                    )
                                )
                                UserInfoItem(
                                    top = user.data?.followeds.toString(),
                                    bottom = stringResource(R.string.text_followeds)
                                )
                                UserInfoItem(
                                    top = user.data?.vipType.toString(), bottom = stringResource(
                                        R.string.text_levels
                                    )
                                )
                            }

                            TextButton(
                                onClick = {},
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(30.dp),
                                contentPadding = PaddingValues(0.dp),
                                border = BorderStroke(0.8.dp, color = Color.Gray)
                            ) {
                                Text(text = stringResource(R.string.text_edit_user_info))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UserInfoItem(top: String?, bottom: String) {
    Column {
        Text(
            text = top.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Text(text = bottom, style = MaterialTheme.typography.titleSmall)
    }
}