package cn.chitanda.music.di

import cn.chitanda.music.http.api.HomeApi
import cn.chitanda.music.http.api.LoginApi
import cn.chitanda.music.http.api.UserApi
import cn.chitanda.music.http.api.VideoApi
import cn.chitanda.music.preference.PreferenceManager
import cn.chitanda.music.repository.HomeRepository
import cn.chitanda.music.repository.UserRepository
import cn.chitanda.music.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *@author: Chen
 *@createTime: 2021/8/14 20:47
 *@description:
 **/
@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        loginApi: LoginApi,
        userApi: UserApi,
        preferenceManager: PreferenceManager
    ) = UserRepository(
        loginApi = loginApi,
        userApi = userApi,
        preferenceManager = preferenceManager
    )

    @Provides
    @Singleton
    fun provideFindRepository(findApi: HomeApi) = HomeRepository(findApi)

    @Provides
    @Singleton
    fun provideVideoRepository(videoApi: VideoApi) = VideoRepository(videoApi)
}