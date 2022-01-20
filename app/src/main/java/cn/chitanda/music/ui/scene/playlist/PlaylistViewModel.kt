package cn.chitanda.music.ui.scene.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.music.repository.SongsRepository
import cn.chitanda.music.ui.scene.PageState
import cn.chitanda.music.utils.setStat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: Chen
 * @createTime: 2021/12/31 16:40
 * @description:
 **/
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
}