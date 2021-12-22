package cn.chitanda.music.http.bean


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoType(
    override val code: Int,
    override val message: String?,
    override val msg: String?,
    override val data: List<Data>?
) : BaseJson<List<VideoType.Data>>() {

    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "abExtInfo")
        val abExtInfo: Any?,
        @Json(name = "id")
        val id: Int,
        @Json(name = "name")
        val name: String?,
        @Json(name = "relatedVideoType")
        val relatedVideoType: Any?,
        @Json(name = "selectTab")
        val selectTab: Boolean?,
        @Json(name = "url")
        val url: String?
    )
}