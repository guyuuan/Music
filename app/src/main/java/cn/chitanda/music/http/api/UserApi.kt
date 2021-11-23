package cn.chitanda.music.http.api

import cn.chitanda.music.http.bean.RefreshLogin
import cn.chitanda.music.http.bean.UserAccount
import cn.chitanda.music.http.bean.UserProfile
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *@author: Chen
 *@createTime: 2021/8/31 17:09
 *@description:
 **/
interface UserApi {
    @POST("/user/detail")
    suspend fun getUserInfo(
        @Query("uid") id: String
    ): UserProfile

    @POST("/login/refresh")
    suspend fun refreshLoginStatus(): RefreshLogin
}