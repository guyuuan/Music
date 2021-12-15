package cn.chitanda.music.utils

import java.security.MessageDigest

/**
 * @author: Chen
 * @createTime: 2021/12/15 11:15 上午
 * @description:
 **/
private val digest by lazy {
    MessageDigest.getInstance("MD5")
}

fun String.md5(): String {
    val result = digest.digest(this.toByteArray())
    return toHex(result)
}

private fun toHex(byteArray: ByteArray): String {
    return buildString {
        byteArray.forEach {
            append(it.toUByte().toString(16).also { hex ->
                if (hex.length == 1) append("0")
            })
        }
    }
}