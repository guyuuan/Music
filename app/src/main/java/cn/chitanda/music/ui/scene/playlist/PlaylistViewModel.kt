package cn.chitanda.music.ui.scene.playlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.music.http.paging.PlaylistSongsPagingSource
import cn.chitanda.music.repository.SongsRepository
import cn.chitanda.music.ui.scene.PageState
import cn.chitanda.music.utils.setStat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: Chen
 * @createTime: 2021/12/31 16:40
 * @description:
 **/
private const val TAG = "PlaylistViewModel"

@HiltViewModel
class PlaylistViewModel @Inject constructor(private val songsRepository: SongsRepository) :
    ViewModel() {
    private val _viewState = MutableStateFlow(PlaylistViewState())
    val viewState: StateFlow<PlaylistViewState> get() = _viewState
    fun getPlaylistDetail(id: String) {
        viewModelScope.launch {
            flow {
                emit(songsRepository.getPlaylistDetail(id))
            }.onStart {
                _viewState.setStat {
                    copy(state = PageState.Loading)
                }
            }.onEach {
                _viewState.setStat {
                    copy(playlist = it.playlist, state = PageState.Success)
                }
            }.catch { e ->
                _viewState.setStat {
                    copy(state = PageState.Error(e))
                }
            }.collect()
        }
    }

    suspend fun getPlaylistSongsPagingSource(id: String, maxSize: Int) {
        Log.d(TAG, "getPlaylistSongsPagingSource: ")
        _viewState.setStat {
            copy(songs = PlaylistSongsPagingSource(maxSize) { offset, pageSize ->
                songsRepository.getPlaylistSongs(id = id, offset = offset, pageSize = pageSize )
            })
        }
    }
}