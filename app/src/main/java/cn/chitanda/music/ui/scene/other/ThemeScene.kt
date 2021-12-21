package cn.chitanda.music.ui.scene.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cn.chitanda.music.ui.LocalThemeViewModel
import cn.chitanda.music.ui.monet.theme.MonetColor
import cn.chitanda.music.ui.scene.ThemeViewModel
import cn.chitanda.music.ui.theme.MusicTheme
import com.google.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.delay
import java.util.*
import kotlin.math.roundToInt

/**
 * @author: Chen
 * @createTime: 2021/12/20 6:15 下午
 * @description:
 **/
@Composable
fun ThemeScene() {
    val themeViewModel = hiltViewModel<ThemeViewModel>()
    val primary by themeViewModel.monetColor
    Scaffold(Modifier.fillMaxSize(), backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.statusBarsHeight(18.dp))
            ColorPicker(themeViewModel, MaterialTheme.colorScheme.primary)
            ThemePreview(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .weight(1f)
                    .padding(vertical = 36.dp),
                color = primary
            )

        }
    }
}

@Composable
fun ThemePreview(modifier: Modifier = Modifier, color: MonetColor?) {
    MusicTheme(color) {
        Scaffold(
            modifier,
            backgroundColor = MaterialTheme.colorScheme.surface,
            topBar = {
                TopAppBar(backgroundColor = MaterialTheme.colorScheme.inversePrimary) {
                    Text(text = "ThemePreview")
                }
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
            val colors = MaterialTheme.colorScheme.toList()
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = it
            ) {
                items(colors) { item ->
                    ColorItem(color = item.first, name = item.second)
                }
            }
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
            Text(text = "RED:$red", modifier = Modifier.weight(1.3f))
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
            Text(text = "GREEN:$green", modifier = Modifier.weight(1.3f))
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
            Text(text = "BLUE:$blue", modifier = Modifier.weight(1.3f))
            Slider(
                value = blue.toFloat(),
                onValueChange = { blue = it.roundToInt() },
                valueRange = 0f..255f,
                modifier = Modifier.weight(4f)
            )
        }
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

@Composable
private fun ColorItem(color: Color, name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(color = color), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "#${
                    color.value.toString(16).subSequence(0, 7)
                }".uppercase(Locale.getDefault()),
                color = contentColorFor(backgroundColor = color)
            )
        }
        Text(text = name, Modifier.weight(1f), textAlign = TextAlign.Center)
    }
}

private fun ColorScheme.toList(): List<Pair<Color, String>> {
    return listOf(
        this.primary to "primary",
        this.onPrimary to "onPrimary",
        this.primaryContainer to "primaryContainer",
        this.onPrimaryContainer to "onPrimaryContainer",
        this.inversePrimary to "inversePrimary",
        this.secondary to "secondary",
        this.onSecondary to "onSecondary",
        this.secondaryContainer to "secondaryContainer",
        this.onSecondaryContainer to "onSecondaryContainer",
        this.tertiary to "tertiary",
        this.onTertiary to "onTertiary",
        this.tertiaryContainer to "tertiaryContainer",
        this.onTertiaryContainer to "onTertiaryContainer",
        this.background to "background",
        this.onBackground to "onBackground",
        this.surface to "surface",
        this.onSurface to "onSurface",
        this.surfaceVariant to "surfaceVariant",
        this.onSurfaceVariant to "onSurfaceVariant",
        this.inverseSurface to "inverseSurface",
        this.inverseOnSurface to "inverseOnSurface",
        this.error to "error",
        this.onError to "onError",
        this.errorContainer to "errorContainer",
        this.onErrorContainer to "onErrorContainer",
        this.outline to "outline",
    )
}