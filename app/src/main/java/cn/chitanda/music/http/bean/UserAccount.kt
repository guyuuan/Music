package cn.chitanda.music.http.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAccount(
    @Json(name = "account")
    val account: Data?,
    override val code: Int,
    override val message: String?, override val msg: String?
) : BaseJson() {
    @JsonClass(generateAdapter = true)
    data class Data(
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
}