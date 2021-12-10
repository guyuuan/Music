package cn.chitanda.music.ui.monet.factory

import cn.chitanda.music.ui.monet.colors.CieLab
import cn.chitanda.music.ui.monet.colors.Color
import cn.chitanda.music.ui.monet.colors.Illuminants
import cn.chitanda.music.ui.monet.colors.Zcam
import cn.chitanda.music.ui.monet.theme.MonetColor
import cn.chitanda.music.ui.monet.theme.DynamicMonetColor
import cn.chitanda.music.ui.monet.theme.MaterialYouTargets
import cn.chitanda.music.ui.monet.theme.ZcamDynamicMonetColor
import cn.chitanda.music.ui.monet.theme.ZcamMaterialYouTargets

/**
 * Modified from [ColorSchemeFactory](https://github.com/kdrag0n/android12-extensions/blob/main/app/src/main/java/dev/kdrag0n/android12ext/xposed/hooks/ColorSchemeFactory.kt)
 * in the original Xposed module. Allows extending or advanced usage for creating custom color schemes,
 * or using ZCam
 */

interface ColorSchemeFactory {

    fun getColor(color: Color): MonetColor

    companion object {
        fun getFactory(
            useZcam: Boolean,
            // For all models
            chromaFactor: Double,
            accurateShades: Boolean,
            // ZCAM only
            whiteLuminance: Double,
            useLinearLightness: Boolean,
        ) = if (useZcam) {
            val cond = createZcamViewingConditions(whiteLuminance)

            object : ColorSchemeFactory {
                override fun getColor(color: Color) = ZcamDynamicMonetColor(
                    targets = ZcamMaterialYouTargets(
                        chromaFactor = chromaFactor,
                        useLinearLightness = useLinearLightness,
                        cond = cond,
                    ),
                    seedColor = color,
                    chromaFactor = chromaFactor,
                    cond = cond,
                    accurateShades = accurateShades,
                )
            }
        } else {
            object : ColorSchemeFactory {
                override fun getColor(color: Color) = DynamicMonetColor(
                    targets = MaterialYouTargets(chromaFactor),
                    seedColor = color,
                    chromaFactor = chromaFactor,
                    accurateShades = accurateShades,
                )
            }
        }

        fun createZcamViewingConditions(whiteLuminance: Double) = Zcam.ViewingConditions(
            F_s = Zcam.ViewingConditions.SURROUND_AVERAGE,
            // sRGB
            L_a = 0.4 * whiteLuminance,
            // Gray world
            Y_b = CieLab(
                L = 50.0,
                a = 0.0,
                b = 0.0,
            ).toCieXyz().y * whiteLuminance,
            referenceWhite = Illuminants.D65 * whiteLuminance,
            whiteLuminance = whiteLuminance,
        )
    }
}