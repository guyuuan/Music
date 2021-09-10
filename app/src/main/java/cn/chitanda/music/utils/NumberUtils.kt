package cn.chitanda.music.utils

import java.text.DecimalFormat

/**
 *@author: Chen
 *@createTime: 2021/9/10 14:41
 *@description:
 **/
//万
private val W = 10_000F

//亿
private val W_W = 100_000_000F

private val format = DecimalFormat("0.#")
fun Long.toUnitString(): String {
    return when {

        this / W_W > 1 -> "${format.format(this / W_W)}亿"
        this / W > 1 -> "${format.format(this / W)}万"
        else -> this.toString()
    }
}