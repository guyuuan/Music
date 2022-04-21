package cn.chitanda.music.ui.scene.home

import androidx.lifecycle.ViewModel
import cn.chitanda.music.extension.launchFlow
import cn.chitanda.music.repository.HomeRepository
import cn.chitanda.music.ui.scene.PageState
import cn.chitanda.music.utils.setStat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 *@author: Chen
 *@createTime: 2021/9/2 13:47
 *@description:
 **/
private const val TAG = "HomeSceneViewModel"

@HiltViewModel
class HomeSceneViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(HomeViewState())
    val viewState= _viewState.asStateFlow()

    fun loadHomeData(refresh: Boolean = true) = launchFlow<HomeViewState> {
        onEmit = {
            delay(1000L)
            homeRepository.loadHomeData(refresh)
        }
        onStart = {
            _viewState.setStat { copy(state = PageState.Loading) }
        }
        onEach = {
            _viewState.setStat {
                copy(
                    banner = it.banner,
                    icons = it.icons,
                    playlist = it.playlist,
                    mLog = it.mLog,
                    songList = it.songList,
                    state = it.state
                )
            }
        }
        onError = { e ->
            _viewState.setStat {
                copy(state = PageState.Error(e))
            }
        }
    }
}
