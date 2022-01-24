package cn.chitanda.music.http.bean
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Artist(
    @Json(name = "albumSize")
    val albumSize: Int? = 0,
    @Json(name = "alias")
    val alias: List<Any?>? = listOf(),
    @Json(name = "briefDesc")
    val briefDesc: String? = "",
    @Json(name = "id")
    val id: Int? = 0,
    @Json(name = "img1v1Id")
    val img1v1Id: Int? = 0,
    @Json(name = "img1v1Url")
    val img1v1Url: String? = "",
    @Json(name = "musicSize")
    val musicSize: Int? = 0,
    @Json(name = "name")
    val name: String? = "",
    @Json(name = "picId")
    val picId: Int? = 0,
    @Json(name = "picUrl")
    val picUrl: String? = "",
    @Json(name = "topicPerson")
    val topicPerson: Int? = 0,
    @Json(name = "trans")
    val trans: String? = "",
    @Json(name = "artistId")
    val artistId: Long?=null,
    @Json(name = "artistName")
    val artistName: String?=null
)