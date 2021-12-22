package cn.chitanda.music.http.bean


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class VideoList(
    override val code: Int,
    override val message: String?,
    override val msg: String?, @Json(name = "hasmore")
    val hasMore: Boolean,
    @Json(name = "datas")
    override val data: List<Data>?
) : BaseJson<List<VideoList.Data>?>() {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "alg")
        val alg: String?,
        @Json(name = "data")
        val info: Info?,
        @Json(name = "displayed")
        val displayed: Boolean?,
        @Json(name = "extAlg")
        val extAlg: Any?,
        @Json(name = "type")
        val type: Int?
    ) {
        @JsonClass(generateAdapter = true)
        data class Info(
            @Json(name = "alg")
            val alg: String?,
            @Json(name = "commentCount")
            val commentCount: Int?,
            @Json(name = "coverUrl")
            val coverUrl: String?,
            @Json(name = "creator")
            val creator: UserProfile.Data?,
            @Json(name = "description")
            val description: String?,
            @Json(name = "durationms")
            val durationms: Int?,
            @Json(name = "hasRelatedGameAd")
            val hasRelatedGameAd: Boolean?,
            @Json(name = "height")
            val height: Int?,
            @Json(name = "markTypes")
            val markTypes: List<Int?>?,
            @Json(name = "playTime")
            val playTime: Int?,
            @Json(name = "praised")
            val praised: Boolean?,
            @Json(name = "praisedCount")
            val praisedCount: Int?,
            @Json(name = "previewDurationms")
            val previewDurationms: Int?,
            @Json(name = "previewUrl")
            val previewUrl: String?,
            @Json(name = "relateSong")
            val relateSong: List<Any?>?,
            @Json(name = "relatedInfo")
            val relatedInfo: Any?,
            @Json(name = "scm")
            val scm: String?,
            @Json(name = "shareCount")
            val shareCount: Int?,
            @Json(name = "subscribed")
            val subscribed: Boolean?,
            @Json(name = "threadId")
            val threadId: String?,
            @Json(name = "title")
            val title: String?,
            @Json(name = "urlInfo")
            val urlInfo: Any?,
            @Json(name = "vid")
            val vid: String?,
            @Json(name = "videoUserLiveInfo")
            val videoUserLiveInfo: Any?,
            @Json(name = "width")
            val width: Int?
        )

    }
}