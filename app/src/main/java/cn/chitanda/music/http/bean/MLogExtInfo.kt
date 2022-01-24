package cn.chitanda.music.http.bean


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MLogExtInfo(
    @Json(name = "alg")
    val alg: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "logInfo")
    val logInfo: String?,
    @Json(name = "matchField")
    val matchField: Int?,
    @Json(name = "matchFieldContent")
    val matchFieldContent: Any?,
    @Json(name = "mlogBaseDataType")
    val mlogBaseDataType: Int?,
    @Json(name = "reason")
    val reason: Any?,
    @Json(name = "resource")
    val resource: Resource,
    @Json(name = "sameCity")
    val sameCity: Boolean?,
    @Json(name = "type")
    val type: Int?
) {
    @JsonClass(generateAdapter = true)
    data class Resource(
        @Json(name = "mlogBaseData")
        val mlogBaseData: MlogBaseData?,
        @Json(name = "mlogExtVO")
        val mlogExtVO: MlogExtVO?,
        @Json(name = "shareUrl")
        val shareUrl: String?,
        @Json(name = "status")
        val status: Int?,
        @Json(name = "userProfile")
        val userProfile: UserProfile?
    ) {
        @JsonClass(generateAdapter = true)
        data class MlogBaseData(
            @Json(name = "audio")
            val audio: Any?,
            @Json(name = "coverColor")
            val coverColor: Int?,
            @Json(name = "coverDynamicUrl")
            val coverDynamicUrl: Any?,
            @Json(name = "coverHeight")
            val coverHeight: Int?,
            @Json(name = "coverPicKey")
            val coverPicKey: String?,
            @Json(name = "coverUrl")
            val coverUrl: String?,
            @Json(name = "coverWidth")
            val coverWidth: Int?,
            @Json(name = "duration")
            val duration: Int?,
            @Json(name = "greatCover")
            val greatCover: Boolean?,
            @Json(name = "id")
            val id: String?,
            @Json(name = "interveneText")
            val interveneText: String?,
            @Json(name = "pubTime")
            val pubTime: Long?,
            @Json(name = "text")
            val text: String?,
            @Json(name = "threadId")
            val threadId: String?,
            @Json(name = "type")
            val type: Int?
        )

        @JsonClass(generateAdapter = true)
        data class MlogExtVO(
            @Json(name = "algSong")
            val algSong: Any?,
            @Json(name = "artistName")
            val artistName: Any?,
            @Json(name = "artists")
            val artists: List<Any?>?,
            @Json(name = "canCollect")
            val canCollect: Boolean?,
            @Json(name = "channelTag")
            val channelTag: Any?,
            @Json(name = "commentCount")
            val commentCount: Int?,
            @Json(name = "likedCount")
            val likedCount: Int?,
            @Json(name = "playCount")
            val playCount: Long?,
            @Json(name = "rcmdInfo")
            val rcmdInfo: Any?,
            @Json(name = "song")
            val song: Song?,
            @Json(name = "specialTag")
            val specialTag: String?,
            @Json(name = "strongPushIcon")
            val strongPushIcon: Any?,
            @Json(name = "strongPushMark")
            val strongPushMark: Any?,
            @Json(name = "videoStartPlayTime")
            val videoStartPlayTime: Int?
        ) {
            @JsonClass(generateAdapter = true)
            data class Song(
                @Json(name = "albumName")
                val albumName: String?,
                @Json(name = "artists")
                val artists: List<Artist?>?,
                @Json(name = "coverUrl")
                val coverUrl: String?,
                @Json(name = "duration")
                val duration: Int?,
                @Json(name = "endTime")
                val endTime: Any?,
                @Json(name = "id")
                val id: Int?,
                @Json(name = "name")
                val name: String?,
                @Json(name = "privilege")
                val privilege: Any?,
                @Json(name = "startTime")
                val startTime: Any?
            )
        }

        @JsonClass(generateAdapter = true)
        data class UserProfile(
            @Json(name = "avatarDetail")
            val avatarDetail: AvatarDetail?,
            @Json(name = "avatarUrl")
            val avatarUrl: String?,
            @Json(name = "followed")
            val followed: Boolean?,
            @Json(name = "isAnchor")
            val isAnchor: Boolean?,
            @Json(name = "nickname")
            val nickname: String?,
            @Json(name = "userId")
            val userId: Any?,
            @Json(name = "userType")
            val userType: Int?
        ) {
            @JsonClass(generateAdapter = true)
            data class AvatarDetail(
                @Json(name = "identityIconUrl")
                val identityIconUrl: String?,
                @Json(name = "identityLevel")
                val identityLevel: Int?,
                @Json(name = "userType")
                val userType: Int?
            )
        }
    }
}