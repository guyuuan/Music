package cn.chitanda.music.di

import android.util.Log
import cn.chitanda.music.BuildConfig
import cn.chitanda.music.http.api.FindApi
import cn.chitanda.music.http.api.LoginApi
import cn.chitanda.music.http.api.UserApi
import cn.chitanda.music.http.moshi.moshi
import cn.chitanda.music.preference.CookiesPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 *@author: Chen
 *@createTime: 2021/8/14 14:20
 *@description:
 **/
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private fun getOkHttpClient(cookiesPreference: CookiesPreference): OkHttpClient {
        var cookie by cookiesPreference
        return OkHttpClient.Builder().apply {
//            authenticator { _, response ->
//                val token by accessTokenPreference
//                response.request.newBuilder().header("Authorization", "token $token").build()
//            }
            addInterceptor { chain ->
                val request = chain.request().newBuilder()
//                cookie.forEach {
                request.addHeader("Cookie", cookie.joinToString { str -> "$str;" }.also{
                    Log.d("SetCookie", "local cookie: $it")
                })
//                }
                chain.proceed(request.build())
            }
            addInterceptor { chain ->
                //拦截的cookie保存在originalResponse中
                val originalResponse = chain.proceed(chain.request())
                //打印cookie信息
                val cookieSet = originalResponse.headers("Set-Cookie").toSet()
                Log.d("SetCookie", "set cookie: $cookieSet")
                if (cookieSet.isNotEmpty()) {
                    for(s in cookieSet){
                        if(s.startsWith("MUSIC_U=")){
                            cookie = cookieSet
                            break
                        }
                    }
                }
                originalResponse
            }
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor {
                    Log.d("Retrofit", "log: $it")
                }.also { it.level = HttpLoggingInterceptor.Level.BODY })
            }
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(cookiesPreference: CookiesPreference): Retrofit = Retrofit.Builder()
        .baseUrl(" https://music.chitanda.cn")
        .client(getOkHttpClient(cookiesPreference))
        .addConverterFactory(
            MoshiConverterFactory.create(
                moshi
            )
        ).build()

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi = retrofit.create(LoginApi::class.java)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideFindApi(retrofit: Retrofit): FindApi = retrofit.create(FindApi::class.java)
}
