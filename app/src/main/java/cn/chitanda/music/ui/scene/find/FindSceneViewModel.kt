package cn.chitanda.music.ui.scene.find

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.music.http.RequestStatus
import cn.chitanda.music.http.bean.HomeData
import cn.chitanda.music.http.bean.HomeRoundIconList
import cn.chitanda.music.repository.FindRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *@author: Chen
 *@createTime: 2021/9/2 13:47
 *@description:
 **/
private const val TAG = "FindViewModel"

@HiltViewModel
class DiscoverySceneViewModel @Inject constructor(
    private val findRepository: FindRepository
) : ViewModel() {

    private val _homeData = MutableStateFlow(RequestStatus<HomeData>())
    val homeData: MutableStateFlow<RequestStatus<HomeData>>
        get() = _homeData

    private val _homeIconList = MutableStateFlow<RequestStatus<HomeRoundIconList>>(RequestStatus())
    val homeIconList: StateFlow<RequestStatus<HomeRoundIconList>>
        get() = _homeIconList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                findRepository.fetchHomeData(_homeData)
            }
            withContext(Dispatchers.IO) {
                findRepository.fetchHomeRoundIconList(_homeIconList)
            }
        }
    }
}