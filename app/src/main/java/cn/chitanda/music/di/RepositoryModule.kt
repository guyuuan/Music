package cn.chitanda.music.di

import cn.chitanda.music.http.api.FindApi
import cn.chitanda.music.http.api.LoginApi
import cn.chitanda.music.http.api.UserApi
import cn.chitanda.music.repository.FindRepository
import cn.chitanda.music.repository.UserRepository
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
    ) = UserRepository(
        loginApi = loginApi,
        userApi = userApi,
    )

    @Provides
    @Singleton
    fun provideFindRepository(findApi: FindApi) = FindRepository(findApi)
}