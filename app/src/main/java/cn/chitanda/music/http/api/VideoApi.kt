package cn.chitanda.music.http.api

import cn.chitanda.music.http.bean.VideoList
import cn.chitanda.music.http.bean.VideoType
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApi {

    @GET("/video/group/list")
    suspend fun getVideoType(): VideoType

    @GET("/video/group")
    suspend fun getVideoByType(
        @Query("id") typeId: Int,
        @Query("offset") offset: Int
    ): VideoList

    @GET("/mlog/url")
    suspend fun getMLogVideoUrl(@Query("id") id: String)
}