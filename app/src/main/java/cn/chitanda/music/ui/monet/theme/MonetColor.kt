package cn.chitanda.music.ui.monet.theme

import cn.chitanda.music.ui.monet.colors.Color

typealias ColorSwatch = Map<Int, Color>

abstract class MonetColor {
    abstract val neutral1: ColorSwatch
    abstract val neutral2: ColorSwatch

    abstract val accent1: ColorSwatch
    abstract val accent2: ColorSwatch
    abstract val accent3: ColorSwatch

    // Helpers
    val neutralColors: List<ColorSwatch>
        get() = listOf(neutral1, neutral2)
    val accentColors: List<ColorSwatch>
        get() = listOf(accent1, accent2, accent3)

}
