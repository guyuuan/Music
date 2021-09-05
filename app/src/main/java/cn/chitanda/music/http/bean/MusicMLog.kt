package cn.chitanda.music.http.bean


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MusicMLog(
    @Json(name = "alg")
    val alg: String? = "",
    @Json(name = "id")
    val id: String? = "",
    @Json(name = "logInfo")
    val logInfo: String? = "",
    @Json(name = "matchField")
    val matchField: Int? = 0,
    @Json(name = "matchFieldContent")
    val matchFieldContent: Any? = Any(),
    @Json(name = "mlogBaseDataType")
    val mlogBaseDataType: Int? = 0,
    @Json(name = "position")
    val position: Any? = Any(),
    @Json(name = "reason")
    val reason: String? = "",
    @Json(name = "resource")
    val resource: Resource? = Resource(),
    @Json(name = "sameCity")
    val sameCity: Boolean? = false,
    @Json(name = "type")
    val type: Int? = 0
) {
    @JsonClass(generateAdapter = true)
    data class Resource(
        @Json(name = "mlogBaseData")
        val mlogBaseData: MlogBaseData? = MlogBaseData(),
        @Json(name = "mlogExtVO")
        val mlogExtVO: MlogExtVO? = MlogExtVO(),
        @Json(name = "shareUrl")
        val shareUrl: String? = "",
        @Json(name = "status")
        val status: Int? = 0,
        @Json(name = "userProfile")
        val userProfile: UserProfile? = UserProfile()
    ) {
        @JsonClass(generateAdapter = true)
        data class MlogBaseData(
            @Json(name = "audio")
            val audio: Any? = Any(),
            @Json(name = "coverColor")
            val coverColor: Int? = 0,
            @Json(name = "coverDynamicUrl")
            val coverDynamicUrl: Any? = Any(),
            @Json(name = "coverHeight")
            val coverHeight: Int? = 0,
            @Json(name = "coverPicKey")
            val coverPicKey: String? = "",
            @Json(name = "coverUrl")
            val coverUrl: String? = "",
            @Json(name = "coverWidth")
            val coverWidth: Int? = 0,
            @Json(name = "duration")
            val duration: Int? = 0,
            @Json(name = "id")
            val id: String? = "",
            @Json(name = "interveneText")
            val interveneText: String? = "",
            @Json(name = "pubTime")
            val pubTime: Long? = 0,
            @Json(name = "text")
            val text: String? = "",
            @Json(name = "threadId")
            val threadId: String? = "",
            @Json(name = "type")
            val type: Int? = 0
        )

        @JsonClass(generateAdapter = true)
        data class MlogExtVO(
            @Json(name = "algSong")
            val algSong: AlgSong? = AlgSong(),
            @Json(name = "artistName")
            val artistName: Any? = Any(),
            @Json(name = "artists")
            val artists: List<Any?>? = listOf(),
            @Json(name = "canCollect")
            val canCollect: Boolean? = false,
            @Json(name = "channelTag")
            val channelTag: Any? = Any(),
            @Json(name = "commentCount")
            val commentCount: Int? = 0,
            @Json(name = "likedCount")
            val likedCount: Int? = 0,
            @Json(name = "playCount")
            val playCount: Int? = 0,
            @Json(name = "rcmdInfo")
            val rcmdInfo: Any? = Any(),
            @Json(name = "song")
            val song: Song? = Song(),
            @Json(name = "specialTag")
            val specialTag: String? = "",
            @Json(name = "strongPushIcon")
            val strongPushIcon: Any? = Any(),
            @Json(name = "strongPushMark")
            val strongPushMark: Any? = Any(),
            @Json(name = "videoStartPlayTime")
            val videoStartPlayTime: Int? = 0
        ) {
            @JsonClass(generateAdapter = true)
            data class AlgSong(
                @Json(name = "albumName")
                val albumName: String? = "",
                @Json(name = "artists")
                val artists: List<Artist?>? = listOf(),
                @Json(name = "coverUrl")
                val coverUrl: String? = "",
                @Json(name = "duration")
                val duration: Int? = 0,
                @Json(name = "endTime")
                val endTime: Any? = Any(),
                @Json(name = "id")
                val id: Int? = 0,
                @Json(name = "name")
                val name: String? = "",
                @Json(name = "privilege")
                val privilege: Any? = Any(),
                @Json(name = "startTime")
                val startTime: Any? = Any()
            ) {
                @JsonClass(generateAdapter = true)
                data class Artist(
                    @Json(name = "artistId")
                    val artistId: Int? = 0,
                    @Json(name = "artistName")
                    val artistName: String? = ""
                )
            }

            @JsonClass(generateAdapter = true)
            data class Song(
                @Json(name = "albumName")
                val albumName: String? = "",
                @Json(name = "artists")
                val artists: List<Artist?>? = listOf(),
                @Json(name = "coverUrl")
                val coverUrl: String? = "",
                @Json(name = "duration")
                val duration: Int? = 0,
                @Json(name = "endTime")
                val endTime: Any? = Any(),
                @Json(name = "id")
                val id: Int? = 0,
                @Json(name = "name")
                val name: String? = "",
                @Json(name = "privilege")
                val privilege: Any? = Any(),
                @Json(name = "startTime")
                val startTime: Any? = Any()
            ) {
                @JsonClass(generateAdapter = true)
                data class Artist(
                    @Json(name = "artistId")
                    val artistId: Int? = 0,
                    @Json(name = "artistName")
                    val artistName: String? = ""
                )
            }
        }

        @JsonClass(generateAdapter = true)
        data class UserProfile(
            @Json(name = "avatarDetail")
            val avatarDetail: AvatarDetail? = AvatarDetail(),
            @Json(name = "avatarUrl")
            val avatarUrl: String? = "",
            @Json(name = "followed")
            val followed: Boolean? = false,
            @Json(name = "isAnchor")
            val isAnchor: Boolean? = false,
            @Json(name = "nickname")
            val nickname: String? = "",
            @Json(name = "userId")
            val userId: Long? = 0,
            @Json(name = "userType")
            val userType: Int? = 0
        ) {
            @JsonClass(generateAdapter = true)
            data class AvatarDetail(
                @Json(name = "identityIconUrl")
                val identityIconUrl: Any? = Any(),
                @Json(name = "identityLevel")
                val identityLevel: Any? = Any(),
                @Json(name = "userType")
                val userType: Any? = Any()
            )
        }
    }
}