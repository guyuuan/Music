package cn.chitanda.music.http.bean


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaylistJson(
    override val code: Int,
    override val message: String?,
    override val msg: String?,
    @Json(name = "more")
    val more: Boolean?,
    @Json(name = "playlist")
    override val data: List<Playlist>?,
    @Json(name = "version")
    val version: String?
) : BaseJson<List<PlaylistJson.Playlist>?>() {
    @JsonClass(generateAdapter = true)
    data class Playlist(
        @Json(name = "adType")
        val adType: Int?,
        @Json(name = "anonimous")
        val anonimous: Boolean?,
        @Json(name = "backgroundCoverUrl")
        val backgroundCoverUrl: String?,
        @Json(name = "cloudTrackCount")
        val cloudTrackCount: Int?,
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
        val creator: Creator?,
        @Json(name = "description")
        val description: Any?,
        @Json(name = "englishTitle")
        val englishTitle: Any?,
        @Json(name = "highQuality")
        val highQuality: Boolean?,
        @Json(name = "id")
        val id: Int?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "newImported")
        val newImported: Boolean?,
        @Json(name = "opRecommend")
        val opRecommend: Boolean?,
        @Json(name = "ordered")
        val ordered: Boolean?,
        @Json(name = "playCount")
        val playCount: Int?,
        @Json(name = "privacy")
        val privacy: Int?,
        @Json(name = "recommendInfo")
        val recommendInfo: Any?,
        @Json(name = "shareStatus")
        val shareStatus: Any?,
        @Json(name = "sharedUsers")
        val sharedUsers: Any?,
        @Json(name = "specialType")
        val specialType: Int?,
        @Json(name = "status")
        val status: Int?,
        @Json(name = "subscribed")
        val subscribed: Boolean?,
        @Json(name = "subscribedCount")
        val subscribedCount: Int?,
        @Json(name = "subscribers")
        val subscribers: List<Any?>?,
        @Json(name = "tags")
        val tags: List<String?>?,
        @Json(name = "titleImage")
        val titleImage: Int?,
        @Json(name = "titleImageUrl")
        val titleImageUrl: Any?,
        @Json(name = "totalDuration")
        val totalDuration: Int?,
        @Json(name = "trackCount")
        val trackCount: Int?,
        @Json(name = "trackNumberUpdateTime")
        val trackNumberUpdateTime: Long?,
        @Json(name = "trackUpdateTime")
        val trackUpdateTime: Long?,
        @Json(name = "tracks")
        val tracks: Any?,
        @Json(name = "updateFrequency")
        val updateFrequency: Any?,
        @Json(name = "updateTime")
        val updateTime: Long?,
        @Json(name = "userId")
        val userId: Int?
    ) {
        @JsonClass(generateAdapter = true)
        data class Creator(
            @Json(name = "accountStatus")
            val accountStatus: Int?,
            @Json(name = "anchor")
            val anchor: Boolean?,
            @Json(name = "authStatus")
            val authStatus: Int?,
            @Json(name = "authenticationTypes")
            val authenticationTypes: Int?,
            @Json(name = "authority")
            val authority: Int?,
            @Json(name = "avatarUrl")
            val avatarUrl: String?,
            @Json(name = "backgroundUrl")
            val backgroundUrl: String?,
            @Json(name = "description")
            val description: String?,
            @Json(name = "detailDescription")
            val detailDescription: String?,
            @Json(name = "nickname")
            val nickname: String?,
            @Json(name = "signature")
            val signature: String?,
            @Json(name = "userId")
            val userId: Int?,
            @Json(name = "userType")
            val userType: Int?,
            @Json(name = "vipType")
            val vipType: Int?
        )
    }
}