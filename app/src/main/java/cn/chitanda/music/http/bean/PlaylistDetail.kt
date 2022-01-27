package cn.chitanda.music.http.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaylistDetail(
    override val code: Int,
    override val message: String?,
    override val msg: String?,
    @Json(name = "playlist")
    override val data: Data?,
) : BaseJson<PlaylistDetail.Data?>() {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "adType")
        val adType: Long?,
        @Json(name = "backgroundCoverId")
        val backgroundCoverId: Long?,
        @Json(name = "backgroundCoverUrl")
        val backgroundCoverUrl: Any?,
        @Json(name = "cloudTrackCount")
        val cloudTrackCount: Long?,
        @Json(name = "commentCount")
        val commentCount: Long?,
        @Json(name = "commentThreadId")
        val commentThreadId: String?,
        @Json(name = "coverImgId")
        val coverImgId: Long?,
        @Json(name = "coverImgId_str")
        val coverImgIdStr: String?,
        @Json(name = "coverImgUrl")
        val coverImgUrl: String?,
        @Json(name = "createTime")
        val createTime: Long?,
        @Json(name = "creator")
        val creator: UserProfile.Data?,
        @Json(name = "description")
        val description: String?,
        @Json(name = "englishTitle")
        val englishTitle: Any?,
        @Json(name = "highQuality")
        val highQuality: Boolean?,
        @Json(name = "historySharedUsers")
        val historySharedUsers: Any?,
        @Json(name = "id")
        val id: Long?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "newImported")
        val newImported: Boolean?,
        @Json(name = "officialPlaylistType")
        val officialPlaylistType: Any?,
        @Json(name = "opRecommend")
        val opRecommend: Boolean?,
        @Json(name = "ordered")
        val ordered: Boolean?,
        @Json(name = "playCount")
        val playCount: Long?,
        @Json(name = "privacy")
        val privacy: Long?,
        @Json(name = "remixVideo")
        val remixVideo: Any?,
        @Json(name = "shareCount")
        val shareCount: Long?,
        @Json(name = "sharedUsers")
        val sharedUsers: Any?,
        @Json(name = "specialType")
        val specialType: Long?,
        @Json(name = "status")
        val status: Long?,
        @Json(name = "subscribed")
        val subscribed: Boolean?,
        @Json(name = "subscribedCount")
        val subscribedCount: Long?,
        @Json(name = "subscribers")
        val subscribers: List<Any?>?,
        @Json(name = "tags")
        val tags: List<String>?,
        @Json(name = "titleImage")
        val titleImage: Long?,
        @Json(name = "titleImageUrl")
        val titleImageUrl: String?,
        @Json(name = "trackCount")
        val trackCount: Long?,
        @Json(name = "trackIds")
        val trackIds: List<TrackId>?,
        @Json(name = "trackNumberUpdateTime")
        val trackNumberUpdateTime: Long?,
        @Json(name = "trackUpdateTime")
        val trackUpdateTime: Long?,
        @Json(name = "updateFrequency")
        val updateFrequency: Any?,
        @Json(name = "updateTime")
        val updateTime: Long?,
        @Json(name = "userId")
        val userId: Long?,
        @Json(name = "videoIds")
        val videoIds: Any?,
        @Json(name = "videos")
        val videos: Any?
    ) {

        @JsonClass(generateAdapter = true)
        data class TrackId(
            @Json(name = "alg")
            val alg: Any?,
            @Json(name = "at")
            val at: Long?,
            @Json(name = "id")
            val id: Long?,
            @Json(name = "rcmdReason")
            val rcmdReason: String?,
            @Json(name = "t")
            val t: Long?,
            @Json(name = "uid")
            val uid: Long?,
            @Json(name = "v")
            val v: Long?
        )
    }

}