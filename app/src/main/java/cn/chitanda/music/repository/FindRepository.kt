package cn.chitanda.music.repository

import cn.chitanda.music.http.StateLiveData
import cn.chitanda.music.http.api.FindApi
import cn.chitanda.music.http.bean.HomeData
import cn.chitanda.music.http.bean.HomeRoundIconList

/**
 *@author: Chen
 *@createTime: 2021/9/8 16:32
 *@description:
 **/
class FindRepository(private val findApi: FindApi) : BaseRemoteRepository() {
    suspend fun fetchHomeData(stateLiveData: StateLiveData<HomeData>, refresh: Boolean = false) =
        load(stateLiveData) {
            findApi.fetchHomeData(refresh)
        }
    suspend fun fetchHomeRoundIconList(stateLiveData: StateLiveData<HomeRoundIconList>) =
        load(stateLiveData) {
            findApi.fetchHomeRoundIconList()
        }
}