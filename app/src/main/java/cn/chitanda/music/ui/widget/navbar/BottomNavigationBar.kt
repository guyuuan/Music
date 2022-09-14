package cn.chitanda.music.ui.widget.navbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @author: Chen
 * @createTime: 2022/1/11 17:08
 * @description:
 **/
@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    tonalElevation: Dp = 4.dp,
    content: @Composable RowScope.() -> Unit
) {
    Surface(tonalElevation = tonalElevation, color = containerColor) {
        Column {
            NavigationBar(
                modifier = modifier,
                containerColor = containerColor,
                content = content, tonalElevation = 0.dp
            )
            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }

}