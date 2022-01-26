package cn.chitanda.music.http.bean


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SongUrl(
    val code: Int,
    @Json(name = "data")
    val data: List<Url>? = null,
    val message: String?,
    val msg: String?
) {
    @JsonClass(generateAdapter = true)
    data class Url(
        @Json(name = "br")
        val br: Long? = null,
        @Json(name = "encodeType")
        val encodeType: String? = null,
        @Json(name = "flag")
        val flag: Long? = null,
        @Json(name = "id")
        val id: Long? = null,
        @Json(name = "md5")
        val md5: String? = null,
        @Json(name = "size")
        val size: Long? = null,
        @Json(name = "type")
        val type: String? = null,
        @Json(name = "url")
        val url: String? = null
    )
}