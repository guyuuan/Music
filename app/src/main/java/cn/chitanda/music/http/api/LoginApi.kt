package cn.chitanda.music.http.api

import cn.chitanda.music.http.bean.BaseJson
import cn.chitanda.music.http.bean.LoginJson
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *@author: Chen
 *@createTime: 2021/8/31 13:42
 *@description:
 **/
interface LoginApi {
    @POST("/captcha/sent")
    suspend fun captchaSent(
        @Query("phone") phone: String,
        @Query("ctcode") ctcode: Int? = null
    ): BaseJson<Any>

    @POST("/login/cellphone")
    suspend fun cellphoneLoginWithCaptcha(
        @Query("phone") phone: String,
        @Query("captcha") captcha: String,
        @Query("countrycode") countryCode: Int? = null
    ): LoginJson

    @POST("/login/cellphone")
    suspend fun cellphoneLoginWithPassword(
        @Query("phone") phone: String,
        @Query("md5_password") password: String,
        @Query("countrycode") countryCode: Int? = null
    ): LoginJson
}