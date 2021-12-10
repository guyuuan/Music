package cn.chitanda.music.ui.monet.colors

interface Color {
    // All colors should have a conversion path to linear sRGB
    fun toLinearSrgb(): LinearSrgb

    fun toArgb(): Int {
        return toLinearSrgb().toSrgb().quantize8()
    }
}
