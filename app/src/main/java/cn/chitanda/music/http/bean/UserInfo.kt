package cn.chitanda.music.http.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfo(
    @Json(name = "account")
    val account: Account?,
    @Json(name = "profile")
    val profile: Profile?,
    override val code: Int,
    override val message: String?, override val msg: String?
) : BaseJson() {
    @JsonClass(generateAdapter = true)
    data class Account(
        @Json(name = "anonimousUser")
        val anonimousUser: Boolean,
        @Json(name = "ban")
        val ban: Int,
        @Json(name = "baoyueVersion")
        val baoyueVersion: Int,
        @Json(name = "createTime")
        val createTime: Long,
        @Json(name = "donateVersion")
        val donateVersion: Int,
        @Json(name = "id")
        val id: Int,
        @Json(name = "paidFee")
        val paidFee: Boolean,
        @Json(name = "status")
        val status: Int,
        @Json(name = "tokenVersion")
        val tokenVersion: Int,
        @Json(name = "type")
        val type: Int,
        @Json(name = "userName")
        val userName: String,
        @Json(name = "vipType")
        val vipType: Int,
        @Json(name = "whitelistAuthority")
        val whitelistAuthority: Int
    )

    @JsonClass(generateAdapter = true)
    data class Profile(
        @Json(name = "accountStatus")
        val accountStatus: Int?,
        @Json(name = "accountType")
        val accountType: Int?,
        @Json(name = "anchor")
        val anchor: Boolean?,
        @Json(name = "authStatus")
        val authStatus: Int?,
        @Json(name = "authenticated")
        val authenticated: Boolean?,
        @Json(name = "authenticationTypes")
        val authenticationTypes: Int?,
        @Json(name = "authority")
        val authority: Int?,
        @Json(name = "avatarDetail")
        val avatarDetail: Any?,
        @Json(name = "avatarImgId")
        val avatarImgId: Long?,
        @Json(name = "avatarUrl")
        val avatarUrl: String?,
        @Json(name = "backgroundImgId")
        val backgroundImgId: Long?,
        @Json(name = "backgroundUrl")
        val backgroundUrl: String?,
        @Json(name = "birthday")
        val birthday: Long?,
        @Json(name = "city")
        val city: Int?,
        @Json(name = "createTime")
        val createTime: Long?,
        @Json(name = "defaultAvatar")
        val defaultAvatar: Boolean?,
        @Json(name = "description")
        val description: Any?,
        @Json(name = "detailDescription")
        val detailDescription: Any?,
        @Json(name = "djStatus")
        val djStatus: Int?,
        @Json(name = "expertTags")
        val expertTags: Any?,
        @Json(name = "experts")
        val experts: Any?,
        @Json(name = "followed")
        val followed: Boolean?,
        @Json(name = "gender")
        val gender: Int?,
        @Json(name = "lastLoginIP")
        val lastLoginIP: String?,
        @Json(name = "lastLoginTime")
        val lastLoginTime: Long?,
        @Json(name = "locationStatus")
        val locationStatus: Int?,
        @Json(name = "mutual")
        val mutual: Boolean?,
        @Json(name = "nickname")
        val nickname: String?,
        @Json(name = "province")
        val province: Int?,
        @Json(name = "remarkName")
        val remarkName: Any?,
        @Json(name = "shortUserName")
        val shortUserName: String?,
        @Json(name = "signature")
        val signature: String?,
        @Json(name = "userId")
        val userId: Int?,
        @Json(name = "userName")
        val userName: String?,
        @Json(name = "userType")
        val userType: Int?,
        @Json(name = "vipType")
        val vipType: Int?,
        @Json(name = "viptypeVersion")
        val viptypeVersion: Long?
    )
}