package cn.chitanda.music.http.api

import cn.chitanda.music.http.bean.HomeData
import cn.chitanda.music.http.bean.HomeRoundIconList
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *@author: Chen
 *@createTime: 2021/9/8 16:32
 *@description:
 **/
interface FindApi {
    @GET("/homepage/block/page")
    suspend fun fetchHomeData(
        @Query("refresh") refresh: Boolean = false,
//        @Query("timestamp") timestamp: Long = System.currentTimeMillis()
    ): HomeData

    @GET("/homepage/dragon/ball")
    suspend fun fetchHomeRoundIconList(): HomeRoundIconList
}