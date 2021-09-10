package cn.chitanda.music.http.bean


import androidx.compose.ui.graphics.Color
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeBanner(
    @Json(name = "banners")
    val banners: List<Banner>? = listOf(),
) {
    @JsonClass(generateAdapter = true)
    data class Banner(
        @Json(name = "adDispatchJson")
        val adDispatchJson: Any? = Any(),
        @Json(name = "adLocation")
        val adLocation: Any? = Any(),
        @Json(name = "adSource")
        val adSource: Any? = Any(),
        @Json(name = "adid")
        val adid: Any? = Any(),
        @Json(name = "adurlV2")
        val adurlV2: Any? = Any(),
        @Json(name = "alg")
        val alg: Any? = Any(),
        @Json(name = "bannerId")
        val bannerId: String? = "",
        @Json(name = "dynamicVideoData")
        val dynamicVideoData: Any? = Any(),
        @Json(name = "encodeId")
        val encodeId: String? = "",
        @Json(name = "event")
        val event: Any? = Any(),
        @Json(name = "exclusive")
        val exclusive: Boolean? = false,
        @Json(name = "extMonitor")
        val extMonitor: Any? = Any(),
        @Json(name = "extMonitorInfo")
        val extMonitorInfo: Any? = Any(),
        @Json(name = "monitorBlackList")
        val monitorBlackList: Any? = Any(),
        @Json(name = "monitorClick")
        val monitorClick: Any? = Any(),
        @Json(name = "monitorClickList")
        val monitorClickList: List<Any?>? = listOf(),
        @Json(name = "monitorImpress")
        val monitorImpress: Any? = Any(),
        @Json(name = "monitorImpressList")
        val monitorImpressList: List<Any?>? = listOf(),
        @Json(name = "monitorType")
        val monitorType: Any? = Any(),
        @Json(name = "pic")
        val pic: String? = "",
        @Json(name = "pid")
        val pid: Any? = Any(),
        @Json(name = "program")
        val program: Any? = Any(),
        @Json(name = "requestId")
        val requestId: String? = "",
        @Json(name = "scm")
        val scm: String? = "",
        @Json(name = "showAdTag")
        val showAdTag: Boolean? = false,
        @Json(name = "showContext")
        val showContext: Any? = Any(),
        @Json(name = "song")
        val song: Song? = Song(),
        @Json(name = "targetId")
        val targetId: String ,
        @Json(name = "targetType")
        val targetType: Int? = 0,
        @Json(name = "titleColor")
        val titleColor: TagColor = TagColor.Red,
        @Json(name = "typeTitle")
        val typeTitle: String? = "",
        @Json(name = "url")
        val url: Any? = Any(),
        @Json(name = "video")
        val video: Any? = Any()
    ) {
        sealed class TagColor(val color: Color) {
            object Red : TagColor(Color.Red)
            object Blue : TagColor(Color.Blue)
        }

        @JsonClass(generateAdapter = true)
        data class Song(
            @Json(name = "a")
            val a: Any? = Any(),
            @Json(name = "al")
            val al: Al? = Al(),
            @Json(name = "alia")
            val alia: List<Any?>? = listOf(),
            @Json(name = "ar")
            val ar: List<Ar?>? = listOf(),
            @Json(name = "cd")
            val cd: String? = "",
            @Json(name = "cf")
            val cf: String? = "",
            @Json(name = "copyright")
            val copyright: Int? = 0,
            @Json(name = "cp")
            val cp: Int? = 0,
            @Json(name = "crbt")
            val crbt: Any? = Any(),
            @Json(name = "djId")
            val djId: Int? = 0,
            @Json(name = "dt")
            val dt: Int? = 0,
            @Json(name = "fee")
            val fee: Int? = 0,
            @Json(name = "ftype")
            val ftype: Int? = 0,
            @Json(name = "h")
            val h: H? = H(),
            @Json(name = "id")
            val id: Int? = 0,
            @Json(name = "l")
            val l: L? = L(),
            @Json(name = "m")
            val m: M? = M(),
            @Json(name = "mark")
            val mark: Int? = 0,
            @Json(name = "mst")
            val mst: Int? = 0,
            @Json(name = "mv")
            val mv: Int? = 0,
            @Json(name = "name")
            val name: String? = "",
            @Json(name = "no")
            val no: Int? = 0,
            @Json(name = "noCopyrightRcmd")
            val noCopyrightRcmd: Any? = Any(),
            @Json(name = "originCoverType")
            val originCoverType: Int? = 0,
            @Json(name = "originSongSimpleData")
            val originSongSimpleData: Any? = Any(),
            @Json(name = "pop")
            val pop: Int? = 0,
            @Json(name = "privilege")
            val privilege: Privilege? = Privilege(),
            @Json(name = "pst")
            val pst: Int? = 0,
            @Json(name = "publishTime")
            val publishTime: Long? = 0,
            @Json(name = "rt")
            val rt: String? = "",
            @Json(name = "rtUrl")
            val rtUrl: Any? = Any(),
            @Json(name = "rtUrls")
            val rtUrls: List<Any?>? = listOf(),
            @Json(name = "rtype")
            val rtype: Int? = 0,
            @Json(name = "rurl")
            val rurl: Any? = Any(),
            @Json(name = "s_id")
            val sId: Int? = 0,
            @Json(name = "single")
            val single: Int? = 0,
            @Json(name = "st")
            val st: Int? = 0,
            @Json(name = "t")
            val t: Int? = 0,
            @Json(name = "v")
            val v: Int? = 0
        ) {
            @JsonClass(generateAdapter = true)
            data class Al(
                @Json(name = "id")
                val id: Int? = 0,
                @Json(name = "name")
                val name: String? = "",
                @Json(name = "pic")
                val pic: Long? = 0,
                @Json(name = "pic_str")
                val picStr: String? = "",
                @Json(name = "picUrl")
                val picUrl: String? = "",
                @Json(name = "tns")
                val tns: List<Any?>? = listOf()
            )

            @JsonClass(generateAdapter = true)
            data class Ar(
                @Json(name = "alias")
                val alias: List<Any?>? = listOf(),
                @Json(name = "id")
                val id: Int? = 0,
                @Json(name = "name")
                val name: String? = "",
                @Json(name = "tns")
                val tns: List<Any?>? = listOf()
            )

            @JsonClass(generateAdapter = true)
            data class H(
                @Json(name = "br")
                val br: Int? = 0,
                @Json(name = "fid")
                val fid: Int? = 0,
                @Json(name = "size")
                val size: Int? = 0,
                @Json(name = "vd")
                val vd: Int? = 0
            )

            @JsonClass(generateAdapter = true)
            data class L(
                @Json(name = "br")
                val br: Int? = 0,
                @Json(name = "fid")
                val fid: Int? = 0,
                @Json(name = "size")
                val size: Int? = 0,
                @Json(name = "vd")
                val vd: Int? = 0
            )

            @JsonClass(generateAdapter = true)
            data class M(
                @Json(name = "br")
                val br: Int? = 0,
                @Json(name = "fid")
                val fid: Int? = 0,
                @Json(name = "size")
                val size: Int? = 0,
                @Json(name = "vd")
                val vd: Int? = 0
            )

            @JsonClass(generateAdapter = true)
            data class Privilege(
                @Json(name = "chargeInfoList")
                val chargeInfoList: List<ChargeInfo?>? = listOf(),
                @Json(name = "cp")
                val cp: Int? = 0,
                @Json(name = "cs")
                val cs: Boolean? = false,
                @Json(name = "dl")
                val dl: Int? = 0,
                @Json(name = "downloadMaxbr")
                val downloadMaxbr: Int? = 0,
                @Json(name = "fee")
                val fee: Int? = 0,
                @Json(name = "fl")
                val fl: Int? = 0,
                @Json(name = "flag")
                val flag: Int? = 0,
                @Json(name = "freeTrialPrivilege")
                val freeTrialPrivilege: FreeTrialPrivilege? = FreeTrialPrivilege(),
                @Json(name = "id")
                val id: Int? = 0,
                @Json(name = "maxbr")
                val maxbr: Int? = 0,
                @Json(name = "payed")
                val payed: Int? = 0,
                @Json(name = "pl")
                val pl: Int? = 0,
                @Json(name = "playMaxbr")
                val playMaxbr: Int? = 0,
                @Json(name = "preSell")
                val preSell: Boolean? = false,
                @Json(name = "rscl")
                val rscl: Any? = Any(),
                @Json(name = "sp")
                val sp: Int? = 0,
                @Json(name = "st")
                val st: Int? = 0,
                @Json(name = "subp")
                val subp: Int? = 0,
                @Json(name = "toast")
                val toast: Boolean? = false
            ) {
                @JsonClass(generateAdapter = true)
                data class ChargeInfo(
                    @Json(name = "chargeMessage")
                    val chargeMessage: Any? = Any(),
                    @Json(name = "chargeType")
                    val chargeType: Int? = 0,
                    @Json(name = "chargeUrl")
                    val chargeUrl: Any? = Any(),
                    @Json(name = "rate")
                    val rate: Int? = 0
                )

                @JsonClass(generateAdapter = true)
                data class FreeTrialPrivilege(
                    @Json(name = "resConsumable")
                    val resConsumable: Boolean? = false,
                    @Json(name = "userConsumable")
                    val userConsumable: Boolean? = false
                )
            }
        }
    }
}