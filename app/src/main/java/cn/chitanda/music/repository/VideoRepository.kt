package cn.chitanda.music.repository

import cn.chitanda.music.http.RequestStatus
import cn.chitanda.music.http.api.VideoApi
import cn.chitanda.music.http.bean.VideoType
import kotlinx.coroutines.flow.MutableStateFlow

class VideoRepository(private val videoApi: VideoApi) : BaseRemoteRepository() {
    suspend fun getVideoType(stateFlow: MutableStateFlow<RequestStatus<VideoType>>) =
        httpRequest(stateFlow) {
            videoApi.getVideoType()
        }

    suspend fun loadVideoByType(type: Int, offset: Int) = httpRequest {
        videoApi.getVideoByType(typeId = type, offset = offset)
    }
}