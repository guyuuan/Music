package cn.chitanda.music.ui.monet.colors

import cn.chitanda.music.ui.monet.colors.Lch.Companion.calcLabA
import cn.chitanda.music.ui.monet.colors.Lch.Companion.calcLabB
import cn.chitanda.music.ui.monet.colors.Lch.Companion.calcLchC
import cn.chitanda.music.ui.monet.colors.Lch.Companion.calcLchH

data class Oklch(
    override val L: Double,
    override val C: Double,
    override val h: Double,
) : Lch {
    override fun toLinearSrgb() = toOklab().toLinearSrgb()

    fun toOklab(): Oklab {
        return Oklab(
            L = L,
            a = calcLabA(),
            b = calcLabB(),
        )
    }

    companion object {
        fun Oklab.toOklch(): Oklch {
            return Oklch(
                L = L,
                C = calcLchC(),
                h = calcLchH(),
            )
        }
    }
}
