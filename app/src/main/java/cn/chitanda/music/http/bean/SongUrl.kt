package cn.chitanda.music.http.bean


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SongUrl(
    override val code: Int,
    @Json(name = "data")
    override val data: List<Url>? = null,
    override val message: String?, override val msg: String?
) : BaseJson<List<SongUrl.Url>>() {
    @JsonClass(generateAdapter = true)
    data class Url(
        @Json(name = "br")
        val br: Int? = null,
        @Json(name = "encodeType")
        val encodeType: String? = null,
        @Json(name = "flag")
        val flag: Int? = null,
        @Json(name = "id")
        val id: Int? = null,
        @Json(name = "md5")
        val md5: String? = null,
        @Json(name = "size")
        val size: Int? = null,
        @Json(name = "type")
        val type: String? = null,
        @Json(name = "url")
        val url: String? = null
    )
}