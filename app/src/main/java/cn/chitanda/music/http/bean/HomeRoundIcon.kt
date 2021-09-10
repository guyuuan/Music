package cn.chitanda.music.http.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 *@author: Chen
 *@createTime: 2021/9/8 17:42
 *@description:
 **/
@JsonClass(generateAdapter = true)
data class HomeRoundIconList(
    override val code: Int,
    override val message: String?,
    override val msg: String?,
    val data: List<HomeRoundIcon>
) : BaseJson()

@JsonClass(generateAdapter = true)
data class HomeRoundIcon(
    @Json(name = "homepageMode")
    val homepageMode: String,
    @Json(name = "iconUrl")
    val iconUrl: String,
    @Json(name = "id")
    val type: Type,
    @Json(name = "name")
    val name: String,
    @Json(name = "skinSupport")
    val skinSupport: Boolean,
    @Json(name = "url")
    val url: String
) {
    sealed class Type(val id: Int) {
        object DailyRCMD : Type(-1)
        object PersonalFM : Type(-6)
        object PlayListCollection : Type(-2)
        object Leaderboard : Type(-3)
        object DigitalAlbum : Type(13000)
        object Meditation : Type(1024000)
        object SongRoom : Type(19000)
        object Game : Type(18000)
        object Unknown : Type(Int.MIN_VALUE)
    }
}