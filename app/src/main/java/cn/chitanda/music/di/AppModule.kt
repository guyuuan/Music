package cn.chitanda.music.di

import android.util.Log
import cn.chitanda.music.BuildConfig
import cn.chitanda.music.http.api.FindApi
import cn.chitanda.music.http.api.LoginApi
import cn.chitanda.music.http.api.UserApi
import cn.chitanda.music.http.moshi.moshi
import cn.chitanda.music.preference.CookiesPreference
import cn.chitanda.music.utils.MyCookieJar
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
        return OkHttpClient.Builder().apply {
            cookieJar(MyCookieJar(cookiesPreference))
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
