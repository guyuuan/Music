package cn.chitanda.music.ui.scene.playlist

import cn.chitanda.music.http.bean.UserProfile
import cn.chitanda.music.http.paging.PlaylistSongsPagingSource
import cn.chitanda.music.ui.scene.PageState

/**
 * @author: Chen
 * @createTime: 2022/1/20 10:32
 * @description:
 **/
data class PlaylistViewState(
    val playlist: PlaylistDetail? = null,
    val songs: PlaylistSongsPagingSource? = null,
    val state: PageState = PageState.Empty
) {
    data class PlaylistDetail(
        val id: String,
        val name: String,
        val coverUrl: String,
        val creator: UserProfile.Data? = null,
        val description: String? = null,
        val songsCount: Int = 0,
        val shareCount: Long = 0,
        val commentCount: Long = 0,
        val subscribedCount: Long = 0,
        val subscribed: Boolean = false
    )

}