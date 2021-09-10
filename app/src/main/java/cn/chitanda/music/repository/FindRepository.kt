package cn.chitanda.music.repository

import cn.chitanda.music.http.RequestStatus
import cn.chitanda.music.http.StateLiveData
import cn.chitanda.music.http.api.FindApi
import cn.chitanda.music.http.bean.HomeData
import cn.chitanda.music.http.bean.HomeRoundIconList
import kotlinx.coroutines.flow.MutableStateFlow

/**
 *@author: Chen
 *@createTime: 2021/9/8 16:32
 *@description:
 **/
class FindRepository(private val findApi: FindApi) : BaseRemoteRepository() {
    suspend fun fetchHomeData(stateLiveData: MutableStateFlow<RequestStatus<HomeData>>, refresh: Boolean = false) =
        httpRequest(stateLiveData) {
            findApi.fetchHomeData(refresh)
        }
    suspend fun fetchHomeRoundIconList(stateLiveData: MutableStateFlow<RequestStatus<HomeRoundIconList>>) =
        httpRequest(stateLiveData) {
            findApi.fetchHomeRoundIconList()
        }
}