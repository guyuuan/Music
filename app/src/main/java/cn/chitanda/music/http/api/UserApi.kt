package cn.chitanda.music.http.api

import cn.chitanda.music.http.bean.UserInfo
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *@author: Chen
 *@createTime: 2021/8/31 17:09
 *@description:
 **/
interface UserApi {
    @POST("/user/account")
    suspend fun getUserAccount(
        @Query("timestamp") timestamp: Long = System.currentTimeMillis()
    ): UserInfo
}