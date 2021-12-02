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
import cn.chitanda.music.ui.scene.UserViewModel
import cn.chitanda.music.ui.widget.CoilImage
import cn.chitanda.music.ui.widget.nestedscroll.rememberNestedScrollAppBarConnection
import cn.chitanda.music.ui.widget.nestedscroll.rememberNestedScrollAppBarState
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.delay
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
//    val user by viewModel.user.collectAsState()
}
