package cn.chitanda.music.ui.scene.discovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.music.http.StateLiveData
import cn.chitanda.music.http.bean.HomeData
import cn.chitanda.music.http.bean.RequestStatus
import cn.chitanda.music.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *@author: Chen
 *@createTime: 2021/9/2 13:47
 *@description:
 **/
private const val TAG = "FoundViewModel"

@HiltViewModel
class DiscoverySceneViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {


    private val _homeData = StateLiveData<HomeData>()
    val homeData: LiveData<RequestStatus<HomeData>>
        get() = _homeData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.fetchHomeData(_homeData)
        }
    }
}