package cn.chitanda.music.repository

import cn.chitanda.music.http.api.SongsApi
import cn.chitanda.music.ui.scene.playlist.PlaylistViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: Chen
 * @createTime: 2022/1/17 16:21
 * @description:
 **/
class SongsRepository(private val api: SongsApi) : BaseRemoteRepository() {
    suspend fun getPlaylistDetail(id: String) = withContext(Dispatchers.IO) {
        val response = api.getPlaylistDetail(id)
        val viewState = PlaylistViewState()
        if (response.code == 200) {
            response.data?.let { data ->
                data.shareCount
                data.commentCount
                data.subscribedCount
                data.subscribed
                return@withContext viewState.copy(
                    playlist = PlaylistViewState.PlaylistDetail(
                        name = data.name.toString(),
                        id = data.id.toString(),
                        creator = data.creator,
                        description = data.description,
                        coverUrl = data.coverImgUrl ?: "",
                        songsCount = data.trackCount?.toInt() ?: 0,
                        shareCount = data.shareCount ?: 0,
                        commentCount = data.commentCount ?: 0,
                        subscribedCount = data.subscribedCount ?: 0,
                        subscribed = data.subscribed ?: false
                    )
                )
            }
        }
        throw RuntimeException("load playlist detail failed")
    }

    suspend fun getPlaylistSongs(id: String, offset: Int, pageSize: Int) =
        withContext(Dispatchers.IO) {
            api.getPlaylistSongs(id, offset = offset, limit = pageSize)
        }

    suspend fun getPlaylistAllSongs(id: String) = withContext(Dispatchers.IO) {
        api.getPlaylistAllSongs(id)
    }

    suspend fun getSongUrl(id: String, br: Long = 999000L) = withContext(Dispatchers.IO) {
        api.getSongUrl(id, br)
    }
}