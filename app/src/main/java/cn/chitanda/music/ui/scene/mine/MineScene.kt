package cn.chitanda.music.ui.scene.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cn.chitanda.music.ui.LocalNavController
import cn.chitanda.music.ui.LocalThemeViewModel
import cn.chitanda.music.ui.LocalUserViewModel
import cn.chitanda.music.ui.monet.theme.MonetColor
import cn.chitanda.music.ui.scene.ThemeViewModel
import cn.chitanda.music.ui.scene.UserViewModel
import cn.chitanda.music.ui.theme.MusicTheme
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.statusBarsHeight
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
    val themeViewModel = hiltViewModel<ThemeViewModel>()
    val primary by themeViewModel.monetColor
    Scaffold(Modifier.fillMaxSize(), backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
        Column(Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.statusBarsHeight(18.dp))
            ColorPicker(themeViewModel, MaterialTheme.colorScheme.primary)
            Box(
                modifier = Modifier.weight(1f)
            ) {
                ThemePreview(color = primary)
            }
        }
    }
}

@Composable
fun ThemePreview(color: MonetColor?) {
    MusicTheme(color) {
        Scaffold(
            Modifier
                .fillMaxSize()
                .scale(0.5f),
            backgroundColor = MaterialTheme.colorScheme.surface,
            topBar = {
                TopAppBar(backgroundColor = MaterialTheme.colorScheme.inversePrimary) {}
            },
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    onClick = { }) {

                }
            },
            bottomBar = {
                BottomNavigation(backgroundColor = MaterialTheme.colorScheme.inversePrimary) {

                }
            }
        ) {
        }
    }
}

@Composable
fun ColorPicker(themeViewModel: ThemeViewModel, themeColor: Color) {
    val globalThemeViewModel = LocalThemeViewModel.current
    var red by remember {
        mutableStateOf((255 * themeColor.red).roundToInt())
    }
    var green by remember {
        mutableStateOf((255 * themeColor.green).roundToInt())
    }
    var blue by remember {
        mutableStateOf((255 * themeColor.blue).roundToInt())
    }
    val color = Color(red, green, blue)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "RED:$red", modifier = Modifier.weight(1f))
            Slider(
                value = red.toFloat(),
                onValueChange = { red = it.roundToInt() },
                valueRange = 0f..255f,
                modifier = Modifier.weight(4f)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "GREEN:$green", modifier = Modifier.weight(1f))
            Slider(
                value = green.toFloat(),
                onValueChange = { green = it.roundToInt() },
                valueRange = 0f..255f,
                modifier = Modifier.weight(4f)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "BLUE:$blue", modifier = Modifier.weight(1f))
            Slider(
                value = blue.toFloat(),
                onValueChange = { blue = it.roundToInt() },
                valueRange = 0f..255f,
                modifier = Modifier.weight(4f)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color = color)
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextButton(onClick = {
            globalThemeViewModel.setCustomThemeColor(color.toArgb())
        }) {
            Text(text = "确认")
        }
        TextButton(onClick = {
            globalThemeViewModel.closeCustomThemeColor()
        }) {
            Text(text = "取消自定义主题色")
        }
    }
    LaunchedEffect(key1 = color) {
        delay(500)
        if (color != themeColor) themeViewModel.getMonetColor(color)
    }
}