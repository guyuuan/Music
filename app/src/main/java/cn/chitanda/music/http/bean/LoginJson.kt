package cn.chitanda.music.http.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginJson(
    @Json(name = "account")
    val account: Account?,
    @Json(name = "bindings")
    val bindings: List<Binding?>?,
    @Json(name = "code")
    override val code: Int,
    @Json(name = "message")
    override val message: String,
    @Json(name = "msg")
    override val msg: String,
    @Json(name = "cookie")
    val cookie: String?,
    @Json(name = "loginType")
    val loginType: Int?,
    @Json(name = "profile")
    val profile: Profile?,
) : BaseJson() {
    @JsonClass(generateAdapter = true)
    data class Account(
        @Json(name = "anonimousUser")
        val anonimousUser: Boolean?,
        @Json(name = "ban")
        val ban: Int?,
        @Json(name = "baoyueVersion")
        val baoyueVersion: Int?,
        @Json(name = "createTime")
        val createTime: Long?,
        @Json(name = "donateVersion")
        val donateVersion: Int?,
        @Json(name = "id")
        val id: Int?,
        @Json(name = "salt")
        val salt: String?,
        @Json(name = "status")
        val status: Int?,
        @Json(name = "tokenVersion")
        val tokenVersion: Int?,
        @Json(name = "type")
        val type: Int?,
        @Json(name = "userName")
        val userName: String?,
        @Json(name = "vipType")
        val vipType: Int?,
        @Json(name = "viptypeVersion")
        val viptypeVersion: Long?,
        @Json(name = "whitelistAuthority")
        val whitelistAuthority: Int?
    )

    @JsonClass(generateAdapter = true)
    data class Binding(
        @Json(name = "bindingTime")
        val bindingTime: Long?,
        @Json(name = "expired")
        val expired: Boolean?,
        @Json(name = "expiresIn")
        val expiresIn: Int?,
        @Json(name = "id")
        val id: Long?,
        @Json(name = "refreshTime")
        val refreshTime: Int?,
        @Json(name = "tokenJsonStr")
        val tokenJsonStr: String?,
        @Json(name = "type")
        val type: Int?,
        @Json(name = "url")
        val url: String?,
        @Json(name = "userId")
        val userId: Int?
    )

    @JsonClass(generateAdapter = true)
    data class Profile(
        @Json(name = "accountStatus")
        val accountStatus: Int?,
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
        @Json(name = "city")
        val city: Int?,
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
        @Json(name = "experts")
        val experts: Experts?,
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
        @Json(name = "signature")
        val signature: String?,
        @Json(name = "userId")
        val userId: Int?,
        @Json(name = "userType")
        val userType: Int?,
        @Json(name = "vipType")
        val vipType: Int?
    ) {
        @JsonClass(generateAdapter = true)
        class Experts
    }
}