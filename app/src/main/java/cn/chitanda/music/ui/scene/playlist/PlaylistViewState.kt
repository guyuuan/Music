package cn.chitanda.music.ui.scene.playlist

import cn.chitanda.music.http.bean.UserProfile
import cn.chitanda.music.ui.scene.PageState

/**
 * @author: Chen
 * @createTime: 2022/1/20 10:32
 * @description:
 **/
data class PlaylistViewState(
    val playlist: PlaylistDetail? = null,
    val state: PageState = PageState.Empty
) {
    data class PlaylistDetail(
        val id: String,
        val name: String ,
        val coverUrl :String ,
        val creator: UserProfile.Data? = null,
        val description: String? = null
    )

}