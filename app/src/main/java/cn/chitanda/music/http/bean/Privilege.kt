package cn.chitanda.music.http.bean


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Privilege(
    @Json(name = "chargeInfoList")
    val chargeInfoList: List<ChargeInfo>? = null,
    @Json(name = "cp")
    val cp: Int? = null,
    @Json(name = "cs")
    val cs: Boolean? = null,
    @Json(name = "dl")
    val dl: Int? = null,
    @Json(name = "downloadMaxbr")
    val downloadMaxbr: Int? = null,
    @Json(name = "fee")
    val fee: Int? = null,
    @Json(name = "fl")
    val fl: Int? = null,
    @Json(name = "flag")
    val flag: Int? = null,
    @Json(name = "freeTrialPrivilege")
    val freeTrialPrivilege: FreeTrialPrivilege? = null,
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "maxbr")
    val maxbr: Int? = null,
    @Json(name = "payed")
    val payed: Int? = null,
    @Json(name = "pl")
    val pl: Int? = null,
    @Json(name = "playMaxbr")
    val playMaxbr: Int? = null,
    @Json(name = "preSell")
    val preSell: Boolean? = null,
    @Json(name = "rscl")
    val rscl: Any? = null,
    @Json(name = "sp")
    val sp: Int? = null,
    @Json(name = "st")
    val st: Int? = null,
    @Json(name = "subp")
    val subp: Int? = null,
    @Json(name = "toast")
    val toast: Boolean? = null
) {
    @JsonClass(generateAdapter = true)
    data class ChargeInfo(
        @Json(name = "chargeMessage")
        val chargeMessage: Any? = null,
        @Json(name = "chargeType")
        val chargeType: Int? = null,
        @Json(name = "chargeUrl")
        val chargeUrl: Any? = null,
        @Json(name = "rate")
        val rate: Int? = null
    )

    @JsonClass(generateAdapter = true)
    data class FreeTrialPrivilege(
        @Json(name = "resConsumable")
        val resConsumable: Boolean? = null,
        @Json(name = "userConsumable")
        val userConsumable: Boolean? = null
    )
}