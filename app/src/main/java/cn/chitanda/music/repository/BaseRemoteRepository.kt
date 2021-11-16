package cn.chitanda.music.repository

import cn.chitanda.music.http.StateLiveData
import cn.chitanda.music.http.bean.BaseJson
import cn.chitanda.music.http.DataState
import cn.chitanda.music.http.RequestStatus
import kotlinx.coroutines.flow.MutableStateFlow

/**
 *@author: Chen
 *@createTime: 2021/8/31 15:05
 *@description:
 **/
open class BaseRemoteRepository {
    protected suspend fun <T : BaseJson> httpRequest(
        stateLiveData: StateLiveData<T>,
        block: suspend () -> T?
    ) {
        try {
            stateLiveData.postValue(RequestStatus(status = DataState.STATE_LOADING))
            val data = block()
            val response = if (data != null) {
                RequestStatus(
                    code = data.code,
                    status = when (data.code) {
                        in 200..299 -> DataState.STATE_SUCCESS
                        in 300..599 -> DataState.STATE_FAILED
                        else -> DataState.STATE_UNKNOWN
                    },
                    msg = data.msg,
                    json = data
                )
            } else {
                RequestStatus(status = DataState.STATE_EMPTY)
            }
            stateLiveData.postValue(response)
        } catch (e: Exception) {
            e.printStackTrace()
            stateLiveData.postValue(RequestStatus(status = DataState.STATE_ERROR, error = e))
        }
    }

    protected suspend fun <T : BaseJson> httpRequest(
        stateLiveData: MutableStateFlow<RequestStatus<T>>?,
        block: suspend () -> T?
    ) {
        try {
            stateLiveData?.emit(RequestStatus(status = DataState.STATE_LOADING))
            val data = block()
            val response = if (data != null) {
                RequestStatus(
                    code = data.code,
                    status = when (data.code) {
                        in 200..299 -> DataState.STATE_SUCCESS
                        in 300..599 -> DataState.STATE_FAILED
                        else -> DataState.STATE_UNKNOWN
                    },
                    msg = data.msg,
                    json = data
                )
            } else {
                RequestStatus(status = DataState.STATE_EMPTY)
            }
            stateLiveData?.emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
            stateLiveData?.emit(RequestStatus(status = DataState.STATE_ERROR, error = e))
        }
    }
}