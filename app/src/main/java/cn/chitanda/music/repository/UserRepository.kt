package cn.chitanda.music.repository

import cn.chitanda.music.http.RequestStatus
import cn.chitanda.music.http.api.LoginApi
import cn.chitanda.music.http.api.UserApi
import cn.chitanda.music.http.bean.LoginJson
import cn.chitanda.music.http.bean.UserProfile
import cn.chitanda.music.preference.PreferenceManager
import cn.chitanda.music.utils.md5
import kotlinx.coroutines.flow.MutableStateFlow

/**
 *@author: Chen
 *@createTime: 2021/8/31 14:30
 *@description:
 **/
class UserRepository constructor(
    private val loginApi: LoginApi,
    private val userApi: UserApi,
    private val preferenceManager: PreferenceManager
) : BaseRemoteRepository() {
    suspend fun loginWithPassword(
        phone: String,
        pw: String,
        stateFlow: MutableStateFlow<RequestStatus<LoginJson>>? = null
    ) = httpRequest(stateFlow) {
        loginApi.cellphoneLoginWithPassword(phone = phone, password = pw.md5()).also {
            it.account?.id?.let { id ->
                preferenceManager.uid = id.toString()
            }
        }
    }

    suspend fun fetchUserInfo(stateFlow: MutableStateFlow<RequestStatus<UserProfile>>) =
        httpRequest(stateFlow = stateFlow) {
            userApi.getUserInfo(id = preferenceManager.uid)
        }

    suspend fun refreshLoginStatus() = httpRequest {
        userApi.refreshLoginStatus()
    }
}

