package cn.chitanda.music.http.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class UserProfile(
    override val code: Int,
    override val message: String?,
    override val msg: String?,
    @Json(name = "profile")
    override val data: Data?,
    @Json(name = "level")
    val level: Int?
) : BaseJson<UserProfile.Data?>() {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "accountStatus")
        val accountStatus: Int?,
        @Json(name = "allSubscribedCount")
        val allSubscribedCount: Int?,
        @Json(name = "artistIdentity")
        val artistIdentity: List<Any?>?,
        @Json(name = "authStatus")
        val authStatus: Int?,
        @Json(name = "authority")
        val authority: Int?,
        @Json(name = "avatarDetail")
        val avatarDetail: Any?,
        @Json(name = "avatarImgId")
        val avatarImgId: Long?,
        @Json(name = "avatarImgIdStr")
        val avatarImgIdStr: String?,
        @Json(name = "avatarUrl")
        val avatarUrl: String?,
        @Json(name = "backgroundImgId")
        val backgroundImgId: Long?,
        @Json(name = "backgroundImgIdStr")
        val backgroundImgIdStr: String?,
        @Json(name = "backgroundUrl")
        val backgroundUrl: String?,
        @Json(name = "birthday")
        val birthday: Long?,
        @Json(name = "blacklist")
        val blacklist: Boolean?,
        @Json(name = "cCount")
        val cCount: Int?,
        @Json(name = "city")
        val city: Int?,
        @Json(name = "createTime")
        val createTime: Long?,
        @Json(name = "defaultAvatar")
        val defaultAvatar: Boolean?,
        @Json(name = "description")
        val description: String?,
        @Json(name = "detailDescription")
        val detailDescription: String?,
        @Json(name = "djStatus")
        val djStatus: Int?,
        @Json(name = "eventCount")
        val eventCount: Int?,
        @Json(name = "expertTags")
        val expertTags: Any?,
        @Json(name = "followMe")
        val followMe: Boolean?,
        @Json(name = "followTime")
        val followTime: Any?,
        @Json(name = "followed")
        val followed: Boolean?,
        @Json(name = "followeds")
        val followeds: Int?,
        @Json(name = "follows")
        val follows: Int?,
        @Json(name = "gender")
        val gender: Int?,
        @Json(name = "mutual")
        val mutual: Boolean?,
        @Json(name = "newFollows")
        val newFollows: Int?,
        @Json(name = "nickname")
        val nickname: String?,
        @Json(name = "playlistBeSubscribedCount")
        val playlistBeSubscribedCount: Int?,
        @Json(name = "playlistCount")
        val playlistCount: Int?,
        @Json(name = "province")
        val province: Int?,
        @Json(name = "remarkName")
        val remarkName: Any?,
        @Json(name = "sCount")
        val sCount: Int?,
        @Json(name = "sDJPCount")
        val sDJPCount: Int?,
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
