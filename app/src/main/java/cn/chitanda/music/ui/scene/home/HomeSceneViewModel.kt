package cn.chitanda.music.ui.scene.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.music.http.RequestStatus
import cn.chitanda.music.http.bean.HomeData
import cn.chitanda.music.http.bean.HomeRoundIconList
import cn.chitanda.music.repository.HomeRepository
import cn.chitanda.music.ui.scene.PageState
import cn.chitanda.music.utils.setStat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *@author: Chen
 *@createTime: 2021/9/2 13:47
 *@description:
 **/
private const val TAG = "FindViewModel"

@HiltViewModel
class HomeSceneViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _homeData = MutableStateFlow(RequestStatus<HomeData>())
    val homeData: MutableStateFlow<RequestStatus<HomeData>>
        get() = _homeData

    private val _homeIconList = MutableStateFlow<RequestStatus<HomeRoundIconList>>(RequestStatus())
    val homeIconList: StateFlow<RequestStatus<HomeRoundIconList>>
        get() = _homeIconList

//    init {
//        viewModelScope.launch {
//            val a1 = async(Dispatchers.IO) {
//                findRepository.fetchHomeData(_homeData)
//            }
//            val a2 = async(Dispatchers.IO) {
//                findRepository.fetchHomeRoundIconList(_homeIconList)
//            }
//            a1.await()
//            a2.await()
//        }
//    }

    private val _viewState = MutableStateFlow(HomeViewState())
    val viewState = _viewState.asStateFlow()

    fun loadHomeData() {
        viewModelScope.launch {
            flow {
                emit(homeRepository.loadHomeData())
            }.onStart {
                _viewState.setStat {
                    copy(state = PageState.Loading)
                }
            }.onEach {
                Log.d(TAG, "loadHomeData: ${it.songList} ")
                Log.d(TAG, "loadHomeData: ${it.mLog} ")
                _viewState.setStat {
                    copy(
                        banner = it.banner,
                        icons = it.icons,
                        playlist = it.playlist,
                        mLog = it.mLog,
                        songList = it.songList, state = PageState.Success
                    )
                }
            }.catch { e ->
                _viewState.setStat {
                    copy(state = PageState.Error(e))
                }
            }.collect()
        }
    }
}