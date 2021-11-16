package cn.chitanda.music.repository

import cn.chitanda.music.http.RequestStatus
import cn.chitanda.music.http.api.LoginApi
import cn.chitanda.music.http.StateLiveData
import cn.chitanda.music.http.api.UserApi
import cn.chitanda.music.http.bean.LoginJson
import cn.chitanda.music.http.bean.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow

/**
 *@author: Chen
 *@createTime: 2021/8/31 14:30
 *@description:
 **/
class UserRepository constructor(
    private val loginApi: LoginApi,
    private val userApi: UserApi,
) : BaseRemoteRepository() {
    suspend fun loginWithPassword(
        phone: String,
        pw: String,
        stateFlow: MutableStateFlow<RequestStatus<LoginJson>>?=null
    ) = httpRequest(stateFlow) {
        loginApi.cellphoneLoginWithPassword(phone = phone, password = pw)
    }

    suspend fun fetchUserInfo( stateFlow: MutableStateFlow<RequestStatus<UserInfo>>) =
        httpRequest(stateLiveData = stateFlow) {
            userApi.getUserAccount()
        }

}

