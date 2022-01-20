package cn.chitanda.music.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)
)

class DownArcShape(private val arc: Dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val arcHeight = with(density) { arc.toPx() }
        val path = Path().apply {
            lineTo(0f, size.height - arcHeight)
            quadraticBezierTo(
                size.width / 2,
                size.height + arcHeight,
                size.width,
                size.height - arcHeight
            )
            lineTo(size.width, 0f)
            close()
        }
        return Outline.Generic(path)
    }

}