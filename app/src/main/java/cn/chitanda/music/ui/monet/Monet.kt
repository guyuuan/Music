package cn.chitanda.music.ui.monet

import androidx.annotation.ColorInt
import cn.chitanda.music.ui.monet.colors.Srgb
import cn.chitanda.music.ui.monet.theme.MonetColor
import cn.chitanda.music.ui.monet.theme.DynamicMonetColor
import cn.chitanda.music.ui.monet.theme.MaterialYouTargets

/**
 * @author: Chen
 * @createTime: 2021/12/10 5:00 下午
 * @description:
 **/
object Monet {
    //    var factory:ColorSchemeFactory = ColorSchemeFactory.getFactory()
    fun getMonetColor(@ColorInt seed: Int,chromaFactor:Double =1.0): MonetColor {
        return  DynamicMonetColor(
            MaterialYouTargets(chromaFactor),
            seedColor = Srgb(seed),
            chromaFactor= chromaFactor,
        )
    }
}