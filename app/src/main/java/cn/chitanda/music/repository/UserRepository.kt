package cn.chitanda.music.repository

import cn.chitanda.music.http.api.LoginApi
import cn.chitanda.music.http.StateLiveData
import cn.chitanda.music.http.api.UserApi
import cn.chitanda.music.http.bean.HomeData
import cn.chitanda.music.http.bean.LoginJson

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
        stateLiveData: StateLiveData<LoginJson>
    ) = load(stateLiveData) {
        loginApi.cellphoneLoginWithPassword(phone = phone, password = pw)
    }

    suspend fun fetchUserInfo(stateLiveData: StateLiveData<LoginJson>) =
        load(stateLiveData = stateLiveData) {
            userApi.getUserAccount()
        }

    suspend fun fetchHomeData(stateLiveData: StateLiveData<HomeData>, refresh: Boolean = false) =
        load(stateLiveData) {
            userApi.fetchHomeData(refresh)
        }

}

