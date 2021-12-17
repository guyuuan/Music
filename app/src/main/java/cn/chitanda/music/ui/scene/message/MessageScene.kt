package cn.chitanda.music.ui.scene.message

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cn.chitanda.music.ui.LocalNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.pager.ExperimentalPagerApi
import java.util.*

/**
 *@author: Chen
 *@createTime: 2021/9/2 10:19
 *@description:
 **/

private const val TAG = "MessageScene"

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun MessageScene(navController: NavController = LocalNavController.current) {
    val colors = MaterialTheme.colorScheme.toList()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(), contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.statusBars
        )
    ) {
        items(colors) { item ->
            ColorItem(color = item.first, name = item.second)
        }
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