package cn.chitanda.music.http.api

import cn.chitanda.music.http.bean.HomeBanner
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *@author: Chen
 *@createTime: 2021/9/3 10:09
 *@description:
 **/
interface HomeDataApi {
    @GET("/banner")
    suspend fun getHomeBanner(@Query("type") type: Int = 1): HomeBanner
}