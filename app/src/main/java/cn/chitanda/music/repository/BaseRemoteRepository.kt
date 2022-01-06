package cn.chitanda.music.repository

import cn.chitanda.music.http.DataState
import cn.chitanda.music.http.RequestStatus
import cn.chitanda.music.http.bean.BaseJson
import kotlinx.coroutines.flow.MutableStateFlow

/**
 *@author: Chen
 *@createTime: 2021/8/31 15:05
 *@description:
 **/
open class BaseRemoteRepository {

    protected suspend fun <T : BaseJson<*>> httpRequest(
        stateFlow: MutableStateFlow<RequestStatus<T>>? = null,
        block: suspend () -> T?
    ): T? {
        return try {
            stateFlow?.emit(RequestStatus(status = DataState.STATE_LOADING))
            val data = block()
            stateFlow?.emit(
                if (data != null) {
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
            )
            data
        } catch (e: Exception) {
            e.printStackTrace()
            stateFlow?.emit(RequestStatus(status = DataState.STATE_ERROR, error = e))
            null
        }
    }
}