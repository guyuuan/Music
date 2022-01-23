package cn.chitanda.music.http.bean


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Songs(
    override val code: Int,
    override val message: String?, override val msg: String?,
    @Json(name = "songs")
    override val data: List<Song>?
) : BaseJson<List<Songs.Song>?>() {

    @JsonClass(generateAdapter = true)
    data class Song(
        @Json(name = "a")
        val a: Any?,
        @Json(name = "al")
        val al: Al?,
        @Json(name = "alia")
        val alia: List<String?>?,
        @Json(name = "ar")
        val ar: List<Ar?>?,
        @Json(name = "cd")
        val cd: String?,
        @Json(name = "cf")
        val cf: String?,
        @Json(name = "copyright")
        val copyright: Long?,
        @Json(name = "cp")
        val cp: Long?,
        @Json(name = "entertainmentTags")
        val entertainmentTags: Any?,
        @Json(name = "fee")
        val fee: Long?,
        @Json(name = "id")
        val id: Long?,
        @Json(name = "mark")
        val mark: Long?,
        @Json(name = "mst")
        val mst: Long?,
        @Json(name = "mv")
        val mv: Long?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "no")
        val no: Long?,
        @Json(name = "noCopyrightRcmd")
        val noCopyrightRcmd: Any?,
        @Json(name = "originCoverType")
        val originCoverType: Long?,
        @Json(name = "originSongSimpleData")
        val originSongSimpleData: Any?,
        @Json(name = "pc")
        val pc: Pc?,
        @Json(name = "pop")
        val pop: Long?,
        @Json(name = "pst")
        val pst: Long?,
        @Json(name = "publishTime")
        val publishTime: Long?,
        @Json(name = "resourceState")
        val resourceState: Boolean?,
        @Json(name = "rt")
        val rt: String?,
        @Json(name = "rtUrl")
        val rtUrl: Any?,
        @Json(name = "rtUrls")
        val rtUrls: List<Any?>?,
        @Json(name = "rtype")
        val rtype: Long?,
        @Json(name = "rurl")
        val rurl: Any?,
        @Json(name = "s_id")
        val sId: Long?,
        @Json(name = "single")
        val single: Long?,
        @Json(name = "songJumpInfo")
        val songJumpInfo: Any?,
        @Json(name = "st")
        val st: Long?,
        @Json(name = "t")
        val t: Long?,
        @Json(name = "tagPicList")
        val tagPicList: Any?,
        @Json(name = "tns")
        val tns: List<String?>?,
        @Json(name = "v")
        val v: Long?,
        @Json(name = "version")
        val version: Long?
    ) {
        @JsonClass(generateAdapter = true)
        data class Al(
            @Json(name = "id")
            val id: Long?,
            @Json(name = "name")
            val name: String?,
            @Json(name = "pic")
            val pic: Long?,
            @Json(name = "pic_str")
            val picStr: String?,
            @Json(name = "picUrl")
            val picUrl: String?,
            @Json(name = "tns")
            val tns: List<String?>?
        )

        @JsonClass(generateAdapter = true)
        data class Ar(
            @Json(name = "alias")
            val alias: List<String?>?,
            @Json(name = "id")
            val id: Long?,
            @Json(name = "name")
            val name: String?,
            @Json(name = "tns")
            val tns: List<String?>?
        )

        @JsonClass(generateAdapter = true)
        data class H(
            @Json(name = "br")
            val br: Long?,
            @Json(name = "fid")
            val fid: Long?,
            @Json(name = "size")
            val size: Long?,
            @Json(name = "vd")
            val vd: Long?
        )

        @JsonClass(generateAdapter = true)
        data class L(
            @Json(name = "br")
            val br: Long?,
            @Json(name = "fid")
            val fid: Long?,
            @Json(name = "size")
            val size: Long?,
            @Json(name = "vd")
            val vd: Long?
        )

        @JsonClass(generateAdapter = true)
        data class M(
            @Json(name = "br")
            val br: Long?,
            @Json(name = "fid")
            val fid: Long?,
            @Json(name = "size")
            val size: Long?,
            @Json(name = "vd")
            val vd: Long?
        )

        @JsonClass(generateAdapter = true)
        data class Pc(
            @Json(name = "alb")
            val alb: String?,
            @Json(name = "ar")
            val ar: String?,
            @Json(name = "br")
            val br: Long?,
            @Json(name = "cid")
            val cid: String?,
            @Json(name = "fn")
            val fn: String?,
            @Json(name = "nickname")
            val nickname: String?,
            @Json(name = "sn")
            val sn: String?,
            @Json(name = "uid")
            val uid: Long?
        )
    }
}