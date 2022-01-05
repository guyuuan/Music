package cn.chitanda.music.repository

import android.util.Log
import cn.chitanda.music.http.RequestStatus
import cn.chitanda.music.http.api.HomeApi
import cn.chitanda.music.http.bean.*
import cn.chitanda.music.http.moshi.moshi
import cn.chitanda.music.ui.scene.home.HomeViewState
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

/**
 *@author: Chen
 *@createTime: 2021/9/8 16:32
 *@description:
 **/
class HomeRepository(private val api: HomeApi) : BaseRemoteRepository() {
    suspend fun fetchHomeData(
        stateLiveData: MutableStateFlow<RequestStatus<HomeData>>,
        refresh: Boolean = false
    ) =
        httpRequest(stateLiveData) {
            api.fetchHomeData(refresh)
        }

    suspend fun fetchHomeRoundIconList(stateLiveData: MutableStateFlow<RequestStatus<HomeRoundIconList>>) =
        httpRequest(stateLiveData) {
            api.fetchHomeRoundIconList()
        }

    suspend fun loadHomeData(refresh: Boolean = false) = withContext(Dispatchers.IO) {
        val homeResponse = async { api.fetchHomeData(refresh = refresh) }
        val roundIcons = async { api.fetchHomeRoundIconList().data }
        var viewState = HomeViewState()
        homeResponse.await().data?.blocks?.forEach { block ->
            when (block.showType) {
                RCMDShowType.Banner -> {
                    getBannerData(block.extInfo)?.let { viewState = viewState.copy(banner = it) }
                }
                RCMDShowType.PlayList -> {
                    block.creatives?.map { it.resources ?: emptyList() }?.flatten()
                        ?.let {
                            viewState = viewState.copy(playlist = block.uiElement to it)
                        }
                }
                RCMDShowType.PlayableMLog -> {
                    getMLogData(block.extInfo)?.let {
                        viewState = viewState.copy(mLog = block.uiElement to it)
                    }
                }
                RCMDShowType.SongList -> {
                    block.creatives?.let {
                        viewState = viewState.copy(songList = block.uiElement to it)
                    }
                }
                RCMDShowType.Unknown, null -> {
                }
            }
        }
        viewState.copy(icons = roundIcons.await())
    }

    private fun getBannerData(extInfo: Any?) =
        if (null == extInfo) {
            null
        } else {
            val type = Types.newParameterizedType(
                Map::class.java,
                Any::class.java, Any::class.java
            )
            val adapter: JsonAdapter<Map<*, *>> =
                moshi.adapter(type)
            val str = adapter.toJson(extInfo as Map<*, *>)
            moshi.adapter(HomeBanner::class.java).fromJson(str)?.banners ?: emptyList()
        }

    private fun getMLogData(extInfo: Any?) =
        if (null == extInfo) {
            null
        } else {
            val type = Types.newParameterizedType(
                List::class.java,
                Any::class.java
            )
            val adapter: JsonAdapter<List<Any>> =
                moshi.adapter(type)
            val str = adapter.toJson(extInfo as List<Any>)
            val extInfoAdapter: JsonAdapter<List<MLogExtInfo>> =
                moshi.adapter(Types.newParameterizedType(List::class.java, MLogExtInfo::class.java))
            extInfoAdapter.fromJson(str)
        }
}