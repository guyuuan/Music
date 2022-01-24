package cn.chitanda.music.di

import android.content.ComponentName
import android.content.Context
import cn.chitanda.music.media.MusicService
import cn.chitanda.music.media.connect.MusicServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author: Chen
 * @createTime: 2022/1/24 16:11
 * @description:
 **/
@Module
@InstallIn(SingletonComponent::class)
object MediaModule {
    @Provides
    @Singleton
    fun provideMusicServiceConnection(@ApplicationContext context: Context) =
        MusicServiceConnection(context = context, ComponentName(context, MusicService::class.java))

}